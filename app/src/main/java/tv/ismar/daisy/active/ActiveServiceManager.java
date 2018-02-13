package tv.ismar.daisy.active;

import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.SPUtils;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by huibin on 29/01/2018.
 */

public class ActiveServiceManager {
    private static final String SKY_HOST = "http://sky.tvxio.com";

    private static ActiveServiceManager ourInstance;
    private final ActiveService activeService;

    private ActiveServiceManager() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(SKY_HOST)
                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        activeService = retrofit.create(ActiveService.class);

    }

    public static ActiveServiceManager getInstance() {
        if (ourInstance == null) {
            synchronized (ActiveServiceManager.class) {
                if (ourInstance == null) {
                    ourInstance = new ActiveServiceManager();
                }
            }
        }
        return ourInstance;
    }

    public String getApiDomain() {
        String apiDomain = SPUtils.getInstance().getString("api_domain");
        if (TextUtils.isEmpty(apiDomain)) {
            return securityActive().getDomain();
        } else {
            return apiDomain;
        }
    }

    public String getDeviceToken() {
        String deviceToken = SPUtils.getInstance().getString("device_token");
        if (TextUtils.isEmpty(deviceToken)) {
            return securityActive().getDevice_token();
        } else {
            return deviceToken;
        }
    }

    public String getSnToken() {
        String snToken = SPUtils.getInstance().getString("sn_token");
        if (TextUtils.isEmpty(snToken)) {
            return securityActive().getSn_token();
        } else {
            return snToken;
        }
    }


    public String getZDeviceToken() {
        String zDeviceToken = SPUtils.getInstance().getString("zdevice_token");
        if (TextUtils.isEmpty(zDeviceToken)) {
            return securityActive().getZdevice_token();
        } else {
            return zDeviceToken;
        }
    }

    public String getAuthToken() {
//        String authToken = SPUtils.getInstance().getString("auth_token");
//        if (TextUtils.isEmpty(authToken)) {
//
//        } else {
//            return authToken;
//        }

        return "";
    }


    private String getLicence() {

        try {
            Response<ResponseBody> response = activeService
                    .trustGetLicence(getFingerprint(), getSn(), getManufacture(), "1")
                    .execute();
            String licence = response.body().string();
            SPUtils.getInstance().put("licence", licence);
            return licence;
        } catch (IOException e) {
            Logger.e(e, "GetLicence Exception");
        }
        return null;
    }

    private synchronized SecurityActiveBean securityActive() {
        Logger.d("securityActive");
        String sn = getSn();
        String manufacture = getManufacture();
        String kind = getKind();
        String appVersion = getAppVersion();
        String sign = getSign();
        String fingerprint = getFingerprint();
        String apiVersion = "v4_0";
        String deviceInfo = "";
        String wifiMac = "";
        String wiredMac = "";
        try {
            SecurityActiveBean bean = activeService.trustSecurityActive(sn, manufacture, kind, appVersion, sign,
                    fingerprint, apiVersion, deviceInfo, wifiMac, wiredMac).execute().body();
            SPUtils.getInstance().put("api_domain", bean.getDomain(), true);
            SPUtils.getInstance().put("carnation_domain", bean.getCarnation(), true);
            SPUtils.getInstance().put("device_token", bean.getDevice_token(), true);
            SPUtils.getInstance().put("zdevice_token", bean.getZdevice_token(), true);
            SPUtils.getInstance().put("sn_token", bean.getSn_token(), true);
            return bean;

        } catch (IOException e) {
            Logger.e("securityActive IOException");
            e.printStackTrace();
        }
        return null;
    }

    private String getSn() {
        return "1bfbfb9cc3483c2b209dde496bc28885";
    }

    private String getFingerprint() {
        return EncryptUtils.encryptMD5ToString(getSn()).toLowerCase();
    }

    private String getManufacture() {
        return Build.BRAND.replace(" ", "_");
    }

    private String getKind() {
        return Build.PRODUCT.replaceAll(" ", "_").toLowerCase();
    }

    private String getAppVersion() {
        return "1";
    }

    public String decryptSignWithAES(String key, String content) {
        try {
            return SkyAESTool.decrypt(key.substring(0, 16), Base64.decode(content, Base64.URL_SAFE));
        } catch (Exception e) {
            SPUtils.getInstance().put("licence", "", true);
            Logger.e(e, "DecryptSignWithAES Exception");
        }
        return null;
    }


    public String encryptWithRSA(String key, String content) {
        try {
            String result = decryptSignWithAES(key, content);
            String publicKey = result.split("\\$\\$\\$")[1];
            String message = "ismartv=201415&kind=" + getKind() + "&sn=" + getSn();
            String input = EncryptUtils.encryptMD5ToString(message).toLowerCase();
            byte[] rsaResult = RSACoder.encryptByPublicKey(input.getBytes(), publicKey);
            return Base64.encodeToString(rsaResult, Base64.DEFAULT);
        } catch (Exception e) {
            Logger.e(e, "EncryptWithRSA Exception");
        }
        return null;
    }

    private String getSign() {
        String key = getSn();
        try {
            String licence = SPUtils.getInstance().getString("licence");
            if (TextUtils.isEmpty(licence)) {
                return encryptWithRSA(key, getLicence());
            } else {
                return encryptWithRSA(key, licence);
            }
        } catch (Exception e) {
            Logger.e(e, "GetSign Exception");
            return encryptWithRSA(key, getLicence());
        }
    }

    public String getCarnationDomain() {
        String carnationDomain = SPUtils.getInstance().getString("carnation_domain");
        if (TextUtils.isEmpty(carnationDomain)) {
            return securityActive().getCarnation();
        } else {
            return carnationDomain;
        }
    }

    interface ActiveService {
        @FormUrlEncoded
        @POST("/trust/security/active/")
        Call<SecurityActiveBean> trustSecurityActive(
                @Field("sn") String sn,
                @Field("manufacture") String manufacture,
                @Field("kind") String kind,
                @Field("version") String version,
                @Field("sign") String sign,
                @Field("fingerprint") String fingerprint,
                @Field("api_version") String api_version,
                @Field("info") String deviceInfo,
                @Field("wireless_mac") String wifiMac,
                @Field("wired_mac") String wiredMac
        );

        @FormUrlEncoded
        @POST("/trust/get_licence/")
        Call<ResponseBody> trustGetLicence(
                @Field("fingerprint") String fingerprint,
                @Field("sn") String sn,
                @Field("manufacture") String manufacture,
                @Field("code") String code
        );
    }
}

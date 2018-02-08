package tv.ismar.daisy;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import tv.ismar.daisy.bean.ClipBean;
import tv.ismar.daisy.bean.ItemBean;
import tv.ismar.daisy.bean.TvChannelsBean;
import tv.ismar.daisy.bean.TvSectionBean;
import tv.ismar.daisy.bean.TvSectionListBean;

/**
 * Created by huibin on 29/01/2018.
 */

public interface SkyService {

    @GET("api/tv/channels/")
    Observable<ArrayList<TvChannelsBean>> apiTvChannels(

    );

    @GET
    Observable<List<TvSectionListBean>> apiTvSectionList(
            @Url String url
    );

    @GET
    Observable<TvSectionBean> apiTvSection(
            @Url String url
    );

    @GET
    Observable<ItemBean> apiItem(
            @Url String url
    );

    @GET
    Observable<ClipBean> apiClip(
            @Url String url,
            @Query("sign") String sign,
            @Query("code") String code
    );

    class ServiceManager {
        private static final String SKY_HOST = "http://1.1.1.1/";

        private static ServiceManager mInstance;

        private SkyService mSkyHostService;


        public ServiceManager() {
            HttpParamsInterceptor httpParamsInterceptor = new HttpParamsInterceptor.Builder()
                    .build();
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(httpParamsInterceptor)
                    .addInterceptor(interceptor)
                    .cache(new Cache(App.getApp().getCacheDir(), 1024 * 1024 * 100))
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(SKY_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            mSkyHostService = retrofit.create(SkyService.class);
        }

        public static ServiceManager getInstance() {
            if (mInstance == null) {
                synchronized (ServiceManager.class) {
                    if (mInstance == null) {
                        mInstance = new ServiceManager();
                    }
                }
            }
            return mInstance;
        }

        public SkyService getSkyHostService() {
            return mSkyHostService;
        }
    }
}

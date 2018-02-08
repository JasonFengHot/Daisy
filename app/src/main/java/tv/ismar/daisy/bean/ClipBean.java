package tv.ismar.daisy.bean;

import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.annotations.SerializedName;

import tv.ismar.daisy.active.ActiveServiceManager;
import tv.ismar.daisy.active.SkyAESTool;

/**
 * Created by huibin on 01/02/2018.
 */

public class ClipBean {
    private String medium;
    private String normal;
    private String code_version;
    private String blueray;
    private String high;
    private boolean live;
    private String low;
    private String adaptive;
    private String ultra;
    @SerializedName("4k")
    private String _$4k;
    private String m3u8;

    /** 爱奇艺 */
    private String iqiyi_4_0;
    /** 是否爱奇艺会员 */
    private boolean is_vip;
    /** 奇艺2.1SDK需要新传入字段 */
    private boolean is_drm;

    public String getIqiyi_4_0() {
        return iqiyi_4_0;
    }

    public void setIqiyi_4_0(String iqiyi_4_0) {
        this.iqiyi_4_0 = iqiyi_4_0;
    }

    public boolean isIs_vip() {
        return is_vip;
    }

    public void setIs_vip(boolean is_vip) {
        this.is_vip = is_vip;
    }

    public boolean isIs_drm() {
        return is_drm;
    }

    public void setIs_drm(boolean is_drm) {
        this.is_drm = is_drm;
    }

    public String getMedium() {
        return decryptionClipUrl(medium);
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getNormal() {
        return decryptionClipUrl(normal);
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getCode_version() {
        return code_version;
    }

    public void setCode_version(String code_version) {
        this.code_version = code_version;
    }

    public String getBlueray() {
        return decryptionClipUrl(blueray);
    }

    public void setBlueray(String blueray) {
        this.blueray = blueray;
    }

    public String getHigh() {
        return decryptionClipUrl(high);
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public String getLow() {
        return decryptionClipUrl(low);
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getAdaptive() {
        return decryptionClipUrl(adaptive);
    }

    public void setAdaptive(String adaptive) {
        this.adaptive = adaptive;
    }

    public String getUltra() {
        return decryptionClipUrl(ultra);
    }

    public void setUltra(String ultra) {
        this.ultra = ultra;
    }

    public String get_$4k() {
        return decryptionClipUrl(_$4k);
    }

    public void set_$4k(String _$4k) {
        this._$4k = _$4k;
    }

    private String decryptionClipUrl(String url) {
        if (!TextUtils.isEmpty(url)) {

            return SkyAESTool.decrypt(
                    ActiveServiceManager.getInstance().getDeviceToken().substring(0, 16), Base64.decode(url, Base64.URL_SAFE));
        }
        return null;
    }

    public String getM3u8() {
        return "file://" + m3u8;
    }

    public void setM3u8(String m3u8) {
        this.m3u8 = m3u8;
    }
}

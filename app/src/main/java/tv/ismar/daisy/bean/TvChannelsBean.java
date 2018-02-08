package tv.ismar.daisy.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huibin on 30/01/2018.
 */

public class TvChannelsBean implements Parcelable {

    public static final Parcelable.Creator<TvChannelsBean> CREATOR = new Parcelable.Creator<TvChannelsBean>() {
        @Override
        public TvChannelsBean createFromParcel(Parcel source) {
            return new TvChannelsBean(source);
        }

        @Override
        public TvChannelsBean[] newArray(int size) {
            return new TvChannelsBean[size];
        }
    };
    /**
     * homepage_template : template1
     * chargeable : false
     * banners_url : /api/tv/banners/chinesemovie/
     * style : 2
     * name : 华语电影
     * url : http://sky.tvxio.bestv.com.cn/v4_0/SKY2/touc/api/tv/sections/chinesemovie/
     * icon_url : http://res.tvxio.bestv.com.cn/media/icon/movie.png
     * icon_focus_url : http://res.tvxio.bestv.com.cn/media/icon/movie_focus.png
     * homepage_url : http://sky.tvxio.bestv.com.cn/v4_0/SKY2/touc/api/tv/homepage/chinesemovie/
     * template : 0
     * channel : chinesemovie
     */

    private String homepage_template;
    private boolean chargeable;
    private String banners_url;
    private int style;
    private String name;
    private String url;
    private String icon_url;
    private String icon_focus_url;
    private String homepage_url;
    private int template;
    private String channel;

    public TvChannelsBean() {
    }

    protected TvChannelsBean(Parcel in) {
        this.homepage_template = in.readString();
        this.chargeable = in.readByte() != 0;
        this.banners_url = in.readString();
        this.style = in.readInt();
        this.name = in.readString();
        this.url = in.readString();
        this.icon_url = in.readString();
        this.icon_focus_url = in.readString();
        this.homepage_url = in.readString();
        this.template = in.readInt();
        this.channel = in.readString();
    }

    public String getHomepage_template() {
        return homepage_template;
    }

    public void setHomepage_template(String homepage_template) {
        this.homepage_template = homepage_template;
    }

    public boolean isChargeable() {
        return chargeable;
    }

    public void setChargeable(boolean chargeable) {
        this.chargeable = chargeable;
    }

    public String getBanners_url() {
        return banners_url;
    }

    public void setBanners_url(String banners_url) {
        this.banners_url = banners_url;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getIcon_focus_url() {
        return icon_focus_url;
    }

    public void setIcon_focus_url(String icon_focus_url) {
        this.icon_focus_url = icon_focus_url;
    }

    public String getHomepage_url() {
        return homepage_url;
    }

    public void setHomepage_url(String homepage_url) {
        this.homepage_url = homepage_url;
    }

    public int getTemplate() {
        return template;
    }

    public void setTemplate(int template) {
        this.template = template;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.homepage_template);
        dest.writeByte(this.chargeable ? (byte) 1 : (byte) 0);
        dest.writeString(this.banners_url);
        dest.writeInt(this.style);
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeString(this.icon_url);
        dest.writeString(this.icon_focus_url);
        dest.writeString(this.homepage_url);
        dest.writeInt(this.template);
        dest.writeString(this.channel);
    }
}

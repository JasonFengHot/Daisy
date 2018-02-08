package tv.ismar.daisy.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huibin on 30/01/2018.
 */

public class TvSectionListBean implements Parcelable {


    /**
     * count : 60
     * title : 新片上线
     * url : http://sky.tvxio.bestv.com.cn/v4_0/SKY2/touc/api/tv/section/xinpianshangxian/
     * template : 0
     * cycle_play : false
     * fixed : true
     * slug : xinpianshangxian
     */

    private int count;
    private String title;
    private String url;
    private int template;
    private boolean cycle_play;
    private boolean fixed;
    private String slug;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTemplate() {
        return template;
    }

    public void setTemplate(int template) {
        this.template = template;
    }

    public boolean isCycle_play() {
        return cycle_play;
    }

    public void setCycle_play(boolean cycle_play) {
        this.cycle_play = cycle_play;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.count);
        dest.writeString(this.title);
        dest.writeString(this.url);
        dest.writeInt(this.template);
        dest.writeByte(this.cycle_play ? (byte) 1 : (byte) 0);
        dest.writeByte(this.fixed ? (byte) 1 : (byte) 0);
        dest.writeString(this.slug);
    }

    public TvSectionListBean() {
    }

    protected TvSectionListBean(Parcel in) {
        this.count = in.readInt();
        this.title = in.readString();
        this.url = in.readString();
        this.template = in.readInt();
        this.cycle_play = in.readByte() != 0;
        this.fixed = in.readByte() != 0;
        this.slug = in.readString();
    }

    public static final Parcelable.Creator<TvSectionListBean> CREATOR = new Parcelable.Creator<TvSectionListBean>() {
        @Override
        public TvSectionListBean createFromParcel(Parcel source) {
            return new TvSectionListBean(source);
        }

        @Override
        public TvSectionListBean[] newArray(int size) {
            return new TvSectionListBean[size];
        }
    };
}

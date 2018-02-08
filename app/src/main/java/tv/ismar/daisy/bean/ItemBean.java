package tv.ismar.daisy.bean;

import java.util.List;

/**
 * Created by huibin on 01/02/2018.
 */

public class ItemBean {

    /**
     * classification : 12-15
     * focus : 脑洞大开 中国神盾局
     * poster_url_old : http://res.tvxio.bestv.com.cn/media/upload/2018/1/17/521d7613bac1ecc3458c06c43cdb27db_poster.jpg
     * is_3d : false
     * content_model : movie
     * logo : http://res.tvxio.bestv.com.cn/media/upload/bestvnew.png
     * detail_url : http://res.tvxio.bestv.com.cn/media/upload/2018/1/17/9556328e87d98e01ce7158e46c5cf6e7_detail.jpg
     * quality : 4
     * rating_count : 0
     * clip : {"url":"http://sky.tvxio.bestv.com.cn/v4_0/SKY2/touc/api/clip/9059181/","pk":9059181,"length":"6755"}
     * source :
     * vendor : 百视通
     * adlet_url : http://res.tvxio.bestv.com.cn/media/upload/2018/1/17/521d7613bac1ecc3458c06c43cdb27db_adlet.jpg
     * bean_score : 8.7
     * poster_url : http://res.tvxio.bestv.com.cn/media/upload/2018/1/17/521d7613bac1ecc3458c06c43cdb27db_poster.jpg
     * pk : 3513462
     * preview : {"url":"http://sky.tvxio.bestv.com.cn/v4_0/SKY2/touc/api/clip/9061457/","pk":9061457,"length":"300","seek":true}
     * logo_solid : http://res.tvxio.bestv.com.cn/media/upload/20140922/detaillogos/detail_bestv.png
     * media_code : null
     * vertical_url : http://res.tvxio.bestv.com.cn/media/upload/2018/1/17/9556328e87d98e01ce7158e46c5cf6e7.jpg
     * description : 徐克、袁和平强强联手翻拍经典，脑洞大开，创意无限，大量诡异场景、诡秘妖兽、飞天遁地、虚实幻术贯穿全片。伍佰跨界出演帮派CEO，和大鹏、周冬雨等联手组建“中国神盾局”，全城爆笑。电影讲述了江湖上的神秘组织“雾隐门”和身世不明的少女小圆一行人的冒险故事。
     * tags : ["神脑洞","大片"]
     * rating_average : 0.0
     * start_time : null
     * subitems : null
     * finished : true
     * live_video : false
     * thumb_url : http://res.tvxio.bestv.com.cn/media/upload/2018/1/17/521d7613bac1ecc3458c06c43cdb27db_thumb.jpg
     * counting_count : 0
     * logo_3d : http://res.tvxio.bestv.com.cn/media/upload/bestvnew_3d.png
     * episode : 0
     * is_order : false
     * subitem_show : null
     * title : 奇门遁甲
     * detail_url_old : http://res.tvxio.bestv.com.cn/media/upload/2018/1/17/9556328e87d98e01ce7158e46c5cf6e7_detail.jpg
     * top_right_corner : right3
     * caption :
     * points : []
     * publish_date : 2018-01-25 17:56:44
     * is_complex : true
     * attributes : {"director":[[14805,"袁和平"]],"genre":[[10024,"喜剧"],[10038,"魔幻"]],"air_date":"2017-12-14","actor":[[88,"大鹏"],[2857,"黄晓明"],[4584,"周冬雨"],[4617,"伍佰"],[13151,"倪妮"],[32133,"李治延"]],"area":[10378,"内地"]}
     * expense : {"pay_type":3,"cplogo":"http://res.tvxio.bestv.com.cn/media/upload/20140922/detaillogos/detail_bestv.png","sale_subitem":false,"cpid":3,"cpname":"ismartv","duration":"7","subprice":0,"price":5,"cptitle":"观影卡VIP","jump_to":2}
     * item_pk : 3513462
     */

    private String classification;
    private String focus;
    private String poster_url_old;
    private boolean is_3d;
    private String content_model;
    private String logo;
    private String detail_url;
    private int quality;
    private int rating_count;
    private ClipBean clip;
    private String source;
    private String vendor;
    private String adlet_url;
    private double bean_score;
    private String poster_url;
    private int pk;
    private PreviewBean preview;
    private String logo_solid;
    private Object media_code;
    private String vertical_url;
    private String description;
    private double rating_average;
    private Object start_time;
    private Object subitems;
    private boolean finished;
    private boolean live_video;
    private String thumb_url;
    private int counting_count;
    private String logo_3d;
    private int episode;
    private boolean is_order;
    private Object subitem_show;
    private String title;
    private String detail_url_old;
    private String top_right_corner;
    private String caption;
    private String publish_date;
    private boolean is_complex;
    private AttributesBean attributes;
    private ExpenseBean expense;
    private int item_pk;
    private List<String> tags;
    private List<?> points;

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public String getPoster_url_old() {
        return poster_url_old;
    }

    public void setPoster_url_old(String poster_url_old) {
        this.poster_url_old = poster_url_old;
    }

    public boolean isIs_3d() {
        return is_3d;
    }

    public void setIs_3d(boolean is_3d) {
        this.is_3d = is_3d;
    }

    public String getContent_model() {
        return content_model;
    }

    public void setContent_model(String content_model) {
        this.content_model = content_model;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDetail_url() {
        return detail_url;
    }

    public void setDetail_url(String detail_url) {
        this.detail_url = detail_url;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getRating_count() {
        return rating_count;
    }

    public void setRating_count(int rating_count) {
        this.rating_count = rating_count;
    }

    public ClipBean getClip() {
        return clip;
    }

    public void setClip(ClipBean clip) {
        this.clip = clip;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getAdlet_url() {
        return adlet_url;
    }

    public void setAdlet_url(String adlet_url) {
        this.adlet_url = adlet_url;
    }

    public double getBean_score() {
        return bean_score;
    }

    public void setBean_score(double bean_score) {
        this.bean_score = bean_score;
    }

    public String getPoster_url() {
        return poster_url;
    }

    public void setPoster_url(String poster_url) {
        this.poster_url = poster_url;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public PreviewBean getPreview() {
        return preview;
    }

    public void setPreview(PreviewBean preview) {
        this.preview = preview;
    }

    public String getLogo_solid() {
        return logo_solid;
    }

    public void setLogo_solid(String logo_solid) {
        this.logo_solid = logo_solid;
    }

    public Object getMedia_code() {
        return media_code;
    }

    public void setMedia_code(Object media_code) {
        this.media_code = media_code;
    }

    public String getVertical_url() {
        return vertical_url;
    }

    public void setVertical_url(String vertical_url) {
        this.vertical_url = vertical_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating_average() {
        return rating_average;
    }

    public void setRating_average(double rating_average) {
        this.rating_average = rating_average;
    }

    public Object getStart_time() {
        return start_time;
    }

    public void setStart_time(Object start_time) {
        this.start_time = start_time;
    }

    public Object getSubitems() {
        return subitems;
    }

    public void setSubitems(Object subitems) {
        this.subitems = subitems;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isLive_video() {
        return live_video;
    }

    public void setLive_video(boolean live_video) {
        this.live_video = live_video;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public int getCounting_count() {
        return counting_count;
    }

    public void setCounting_count(int counting_count) {
        this.counting_count = counting_count;
    }

    public String getLogo_3d() {
        return logo_3d;
    }

    public void setLogo_3d(String logo_3d) {
        this.logo_3d = logo_3d;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public boolean isIs_order() {
        return is_order;
    }

    public void setIs_order(boolean is_order) {
        this.is_order = is_order;
    }

    public Object getSubitem_show() {
        return subitem_show;
    }

    public void setSubitem_show(Object subitem_show) {
        this.subitem_show = subitem_show;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail_url_old() {
        return detail_url_old;
    }

    public void setDetail_url_old(String detail_url_old) {
        this.detail_url_old = detail_url_old;
    }

    public String getTop_right_corner() {
        return top_right_corner;
    }

    public void setTop_right_corner(String top_right_corner) {
        this.top_right_corner = top_right_corner;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public boolean isIs_complex() {
        return is_complex;
    }

    public void setIs_complex(boolean is_complex) {
        this.is_complex = is_complex;
    }

    public AttributesBean getAttributes() {
        return attributes;
    }

    public void setAttributes(AttributesBean attributes) {
        this.attributes = attributes;
    }

    public ExpenseBean getExpense() {
        return expense;
    }

    public void setExpense(ExpenseBean expense) {
        this.expense = expense;
    }

    public int getItem_pk() {
        return item_pk;
    }

    public void setItem_pk(int item_pk) {
        this.item_pk = item_pk;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<?> getPoints() {
        return points;
    }

    public void setPoints(List<?> points) {
        this.points = points;
    }

    public static class ClipBean {
        /**
         * url : http://sky.tvxio.bestv.com.cn/v4_0/SKY2/touc/api/clip/9059181/
         * pk : 9059181
         * length : 6755
         */

        private String url;
        private int pk;
        private String length;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getPk() {
            return pk;
        }

        public void setPk(int pk) {
            this.pk = pk;
        }

        public String getLength() {
            return length;
        }

        public void setLength(String length) {
            this.length = length;
        }
    }

    public static class PreviewBean {
        /**
         * url : http://sky.tvxio.bestv.com.cn/v4_0/SKY2/touc/api/clip/9061457/
         * pk : 9061457
         * length : 300
         * seek : true
         */

        private String url;
        private int pk;
        private String length;
        private boolean seek;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getPk() {
            return pk;
        }

        public void setPk(int pk) {
            this.pk = pk;
        }

        public String getLength() {
            return length;
        }

        public void setLength(String length) {
            this.length = length;
        }

        public boolean isSeek() {
            return seek;
        }

        public void setSeek(boolean seek) {
            this.seek = seek;
        }
    }

    public static class AttributesBean {
        /**
         * director : [[14805,"袁和平"]]
         * genre : [[10024,"喜剧"],[10038,"魔幻"]]
         * air_date : 2017-12-14
         * actor : [[88,"大鹏"],[2857,"黄晓明"],[4584,"周冬雨"],[4617,"伍佰"],[13151,"倪妮"],[32133,"李治延"]]
         * area : [10378,"内地"]
         */

        private String air_date;
        private List<List<String>> director;
        private List<List<String>> genre;
        private List<List<String>> actor;
        private List<String> area;

        public String getAir_date() {
            return air_date;
        }

        public void setAir_date(String air_date) {
            this.air_date = air_date;
        }

        public List<List<String>> getDirector() {
            return director;
        }

        public void setDirector(List<List<String>> director) {
            this.director = director;
        }

        public List<List<String>> getGenre() {
            return genre;
        }

        public void setGenre(List<List<String>> genre) {
            this.genre = genre;
        }

        public List<List<String>> getActor() {
            return actor;
        }

        public void setActor(List<List<String>> actor) {
            this.actor = actor;
        }

        public List<String> getArea() {
            return area;
        }

        public void setArea(List<String> area) {
            this.area = area;
        }
    }

    public static class ExpenseBean {
        /**
         * pay_type : 3
         * cplogo : http://res.tvxio.bestv.com.cn/media/upload/20140922/detaillogos/detail_bestv.png
         * sale_subitem : false
         * cpid : 3
         * cpname : ismartv
         * duration : 7
         * subprice : 0.0
         * price : 5.0
         * cptitle : 观影卡VIP
         * jump_to : 2
         */

        private int pay_type;
        private String cplogo;
        private boolean sale_subitem;
        private int cpid;
        private String cpname;
        private String duration;
        private double subprice;
        private double price;
        private String cptitle;
        private int jump_to;

        public int getPay_type() {
            return pay_type;
        }

        public void setPay_type(int pay_type) {
            this.pay_type = pay_type;
        }

        public String getCplogo() {
            return cplogo;
        }

        public void setCplogo(String cplogo) {
            this.cplogo = cplogo;
        }

        public boolean isSale_subitem() {
            return sale_subitem;
        }

        public void setSale_subitem(boolean sale_subitem) {
            this.sale_subitem = sale_subitem;
        }

        public int getCpid() {
            return cpid;
        }

        public void setCpid(int cpid) {
            this.cpid = cpid;
        }

        public String getCpname() {
            return cpname;
        }

        public void setCpname(String cpname) {
            this.cpname = cpname;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public double getSubprice() {
            return subprice;
        }

        public void setSubprice(double subprice) {
            this.subprice = subprice;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getCptitle() {
            return cptitle;
        }

        public void setCptitle(String cptitle) {
            this.cptitle = cptitle;
        }

        public int getJump_to() {
            return jump_to;
        }

        public void setJump_to(int jump_to) {
            this.jump_to = jump_to;
        }
    }
}

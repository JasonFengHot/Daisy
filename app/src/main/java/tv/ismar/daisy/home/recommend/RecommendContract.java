package tv.ismar.daisy.home.recommend;

import java.util.ArrayList;
import java.util.List;

import tv.ismar.daisy.BasePresenter;
import tv.ismar.daisy.BaseView;
import tv.ismar.daisy.bean.TvSectionBean;
import tv.ismar.daisy.bean.TvSectionListBean;

/**
 * Created by huibin on 29/01/2018.
 */

public interface RecommendContract {
    interface View extends BaseView<Presenter> {
        void loadSectionList(List<TvSectionListBean>  beans);

    }

    interface Presenter extends BasePresenter {
        void fetchSectionList(String url);
    }
}

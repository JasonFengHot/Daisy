package tv.ismar.daisy.player;

import java.util.List;

import tv.ismar.daisy.BasePresenter;
import tv.ismar.daisy.BaseView;
import tv.ismar.daisy.bean.ClipBean;
import tv.ismar.daisy.bean.TvSectionListBean;
import tv.ismar.daisy.home.recommend.RecommendContract;

/**
 * Created by huibin on 01/02/2018.
 */

public interface PlayContract {
    interface View extends BaseView<Presenter> {
        void startPlay(ClipBean clipBean);
    }

    interface Presenter extends BasePresenter {
        void fetchItem(String url);

        void fetchClip(String url);
    }
}

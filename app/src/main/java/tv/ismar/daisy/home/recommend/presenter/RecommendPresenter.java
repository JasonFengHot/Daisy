package tv.ismar.daisy.home.recommend.presenter;

import com.orhanobut.logger.Logger;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import tv.ismar.daisy.BaseObserver;
import tv.ismar.daisy.SkyService;
import tv.ismar.daisy.bean.TvSectionBean;
import tv.ismar.daisy.bean.TvSectionListBean;
import tv.ismar.daisy.home.recommend.RecommendContract;

/**
 * Created by huibin on 26/01/2018.
 */

public class RecommendPresenter implements RecommendContract.Presenter {
    private RecommendContract.View mView;

    private List<TvSectionListBean> mTvSectionListBeans;

    public RecommendPresenter(RecommendContract.View view) {
        mView = view;
    }


    @Override
    public void fetchSectionList(String url) {
        SkyService.ServiceManager.getInstance().getSkyHostService().apiTvSectionList(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TvSectionListBean>>() {
                    @Override
                    public void onSuccess(List<TvSectionListBean> tvSectionListBeans) {
                        mTvSectionListBeans = tvSectionListBeans;
                        mView.loadSectionList(mTvSectionListBeans);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        Logger.e(e, "fetchSectionList Throwable");
                    }
                });
    }
}

package tv.ismar.daisy.home.recommend.viewmodel;

import android.databinding.BaseObservable;

import tv.ismar.daisy.home.recommend.presenter.RecommendPresenter;

/**
 * Created by huibin on 26/01/2018.
 */

public class RecommendViewModel extends BaseObservable{
    private RecommendPresenter mPresenter;

    public RecommendViewModel(RecommendPresenter presenter) {
        mPresenter = presenter;
    }
}

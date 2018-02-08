package tv.ismar.daisy.home.recommend.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import tv.ismar.daisy.R;
import tv.ismar.daisy.bean.TvChannelsBean;
import tv.ismar.daisy.bean.TvSectionListBean;
import tv.ismar.daisy.databinding.FragmentRecommendBinding;
import tv.ismar.daisy.home.recommend.RecommendContract;
import tv.ismar.daisy.home.recommend.presenter.RecommendPresenter;
import tv.ismar.daisy.home.recommend.viewmodel.RecommendViewModel;


public class RecommendFragment extends Fragment implements RecommendContract.View {

    private TvChannelsBean mTvChannelsBean;


    private FragmentRecommendBinding mBinding;
    private RecommendPresenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new RecommendPresenter(this);
        if (getArguments() != null) {
            mTvChannelsBean = getArguments().getParcelable("data");
            mPresenter.fetchSectionList(mTvChannelsBean.getUrl());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recommend, container, false);

        RecommendViewModel viewModel = new RecommendViewModel(mPresenter);
        mBinding.setPresenter(mPresenter);
        mBinding.setViewModel(viewModel);


        return mBinding.getRoot();
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecommendFragment() {

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static RecommendFragment newInstance(TvChannelsBean channelsBean) {
        RecommendFragment fragment = new RecommendFragment();
        Bundle args = new Bundle();
        args.putParcelable("data", channelsBean);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void loadSectionList(List<TvSectionListBean> beans) {
        FragmentPagerAdapter adapter = new SectionAdapter(getChildFragmentManager(), beans);
        mBinding.pager.setOffscreenPageLimit(1);
        mBinding.pager.setAdapter(adapter);
        mBinding.indicator.setViewPager(mBinding.pager);
    }


    class SectionAdapter extends FragmentPagerAdapter {
        private List<TvSectionListBean> mBeans;

        public SectionAdapter(FragmentManager fm, List<TvSectionListBean> beans) {
            super(fm);
            mBeans = beans;
        }

        @Override
        public Fragment getItem(int position) {
            return SectionFragment.newInstance(mBeans.get(position));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mBeans.get(position).getTitle();
        }

        @Override
        public int getCount() {
            return mBeans.size();
        }
    }
}

package tv.ismar.daisy.home.recommend.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import tv.ismar.daisy.GlideApp;
import tv.ismar.daisy.R;
import tv.ismar.daisy.bean.TvChannelsBean;
import tv.ismar.daisy.bean.TvSectionListBean;
import tv.ismar.daisy.databinding.FragmentRecommendBinding;
import tv.ismar.daisy.home.recommend.RecommendContract;
import tv.ismar.daisy.home.recommend.presenter.RecommendPresenter;
import tv.ismar.daisy.home.recommend.viewmodel.RecommendViewModel;


public class RecommendFragment extends Fragment implements RecommendContract.View {

    private ArrayList<TvChannelsBean> mTvChannelsBeans;


    private FragmentRecommendBinding mBinding;
    private RecommendPresenter mPresenter;
    private SlidingMenu slidingMenu;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new RecommendPresenter(this);
        if (getArguments() != null) {
            mTvChannelsBeans = getArguments().getParcelableArrayList("data");
            mPresenter.fetchSectionList(mTvChannelsBeans.get(0).getUrl());
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        slidingMenu = new SlidingMenu(getContext());
        bindSlidingMenu(mTvChannelsBeans, slidingMenu);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
//        slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
//        slidingMenu.setShadowDrawable(R.drawable.shadow);
        slidingMenu.setBehindOffset(500);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.attachToActivity(getActivity(), SlidingMenu.SLIDING_CONTENT);
//        slidingMenu.setMenu(R.layout.slidingMenu);


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
    public static RecommendFragment newInstance(ArrayList<TvChannelsBean> channelsBeans) {
        RecommendFragment fragment = new RecommendFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("data", channelsBeans);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void loadSectionList(List<TvSectionListBean> beans) {
        SectionAdapter adapter;
//        if (mBinding.pager.getAdapter() == null) {
            adapter = new SectionAdapter(getChildFragmentManager(), beans);
            mBinding.pager.setAdapter(adapter);
            adapter.notifyDataSetChanged();
//        } else {
//            adapter = (SectionAdapter) mBinding.pager.getAdapter();
//            adapter.setBeans(beans);
//            adapter.notifyDataSetChanged();
//        }

        mBinding.pager.setOffscreenPageLimit(1);
        mBinding.indicator.setViewPager(mBinding.pager);
        mBinding.indicator.notifyDataSetChanged();
        mBinding.indicator.setCurrentItem(0);
    }


    class SectionAdapter extends FragmentPagerAdapter {
        private List<TvSectionListBean> mBeans;
        private boolean update = true;

        public SectionAdapter(FragmentManager fm, List<TvSectionListBean> beans) {
            super(fm);
            update = true;
            mBeans = beans;
        }

        public void setBeans(List<TvSectionListBean> mBeans) {
            update = true;
            this.mBeans = mBeans;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container,
                    position);//得到缓存的fragment


//            if (update) {//我这里只需要重新更新第一个fragment

                //得到tag，这点很重要
                String fragmentTag = fragment.getTag();

                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                //移除旧的fragment
                ft.remove(fragment);
                //换成新的fragment
                fragment = getItem(position);
                //添加新fragment时必须用前面获得的tag，这点很重要
                ft.add(container.getId(), fragment, fragmentTag);
                ft.attach(fragment);
                ft.commit();
                update = false;//清除更新标记（只有重新启动的时候需要去创建新的fragment对象），防止正常情况下频繁创建对象
//            }
            return fragment;
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

    private void bindSlidingMenu(ArrayList<TvChannelsBean> channelsBeans, SlidingMenu slidingMenu) {
        RecyclerView recyclerView = new RecyclerView(getContext());
        slidingMenu.setMenu(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpacesItemDecoration(50));
        recyclerView.setAdapter(new ChannelItemRecyclerViewAdapter(getContext(), channelsBeans));
    }


    public class ChannelItemRecyclerViewAdapter extends RecyclerView.Adapter<ChannelItemRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

        private final ArrayList<TvChannelsBean> mValues;
        private final Context mContext;
        private TextView selectedView;

        public ChannelItemRecyclerViewAdapter(Context context, ArrayList<TvChannelsBean> items) {
            mContext = context;
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_channel, parent, false);
            return new ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);

            GlideApp.with(mContext)
                    .load(mValues.get(position).getIcon_url())
                    .into(holder.mImageView);
            holder.mTitleView.setText(mValues.get(position).getName());
            holder.mView.setTag(holder.mItem);

            holder.mView.setOnClickListener(this);
        }


        @Override
        public int getItemCount() {
            return mValues.size();
        }

        @Override
        public void onClick(View v) {
            //to-do
            if (selectedView!=null){
                selectedView.setSelected(false);
            }
            selectedView = v.findViewById(R.id.textView);
            selectedView.setSelected(true);
            mPresenter.fetchSectionList(((TvChannelsBean) v.getTag()).getUrl());
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final ImageView mImageView;
            public final TextView mTitleView;

            public TvChannelsBean mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = view.findViewById(R.id.imageView);
                mTitleView = (TextView) view.findViewById(R.id.textView);

            }

            @Override
            public String toString() {
                return super.toString();
            }
        }

    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.bottom = space;
        }
    }
}

package tv.ismar.daisy.home.recommend.view;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import tv.ismar.daisy.BaseObserver;
import tv.ismar.daisy.R;
import tv.ismar.daisy.SkyService;
import tv.ismar.daisy.bean.TvSectionBean;
import tv.ismar.daisy.bean.TvSectionListBean;

/**
 * Created by huibin on 31/01/2018.
 */

public class SectionFragment extends Fragment {
    private OnListFragmentInteractionListener mListener;
    private TvSectionBean mTvSectionBean;
    private TvSectionListBean mTvSectionListBean;

    private RecyclerView mRecyclerView;

    public static SectionFragment newInstance(TvSectionListBean bean) {
        SectionFragment fragment = new SectionFragment();
        Bundle args = new Bundle();
        args.putParcelable("data", bean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTvSectionListBean = getArguments().getParcelable("data");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_section, null, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.list);
        fetchSection(mTvSectionListBean.getUrl());
    }

    private void fetchSection(String url) {

        SkyService.ServiceManager.getInstance().getSkyHostService().apiTvSection(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<TvSectionBean>() {
                    @Override
                    public void onSuccess(TvSectionBean tvSectionBean) {
                        mTvSectionBean = tvSectionBean;
                        // Set the adapter
                        Context context = mRecyclerView.getContext();
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                        mRecyclerView.addItemDecoration(new SpacesItemDecoration(50));
                        mRecyclerView.setAdapter(new RecommendItemRecyclerViewAdapter(context, tvSectionBean.getObjects(), mListener));
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        Logger.e(e, "fetchSection Throwable");
                    }
                });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(TvSectionBean.ObjectsBean item);
    }

    public static class RecommendItemRecyclerViewAdapter extends RecyclerView.Adapter<RecommendItemRecyclerViewAdapter.ViewHolder> {

        private final ArrayList<TvSectionBean.ObjectsBean> mValues;
        private final OnListFragmentInteractionListener mListener;
        private final Context mContext;

        public RecommendItemRecyclerViewAdapter(Context context, ArrayList<TvSectionBean.ObjectsBean> items, OnListFragmentInteractionListener listener) {
            mContext = context;
            mValues = items;
            mListener = listener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_recommend_list, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);

            String defaultVerticalUrl = "http://res.tvxio.bestv.com.cn/media/upload/20160321/36c8886fd5b4163ae48534a72ec3a555.png";

            String verticalImageUrl = mValues.get(position).getVertical_url();
            String vertical2ImageUrl = mValues.get(position).getList_url();
            String horizontalImageUrl = mValues.get(position).getPoster_url();

            String targetImageUrl = verticalImageUrl;
            if (TextUtils.isEmpty(verticalImageUrl) || verticalImageUrl.equals(defaultVerticalUrl)) {
                targetImageUrl = vertical2ImageUrl;
            } else if (TextUtils.isEmpty(vertical2ImageUrl)) {
                targetImageUrl = horizontalImageUrl;
            }

            Glide.with(mContext)
                    .load(targetImageUrl)
                    .into(holder.mImageView);
            holder.mTitleView.setText("电影：" + mValues.get(position).getTitle());
            holder.mDirector.setText("导演：" + listToString(mValues.get(position).getAttributes().getDirector()));
            holder.mGenre.setText("类型：" + listToString(mValues.get(position).getAttributes().getGenre()));
            holder.mAirDate.setText("上映：" + (mValues.get(position).getAttributes().getAir_date()));
            holder.mActor.setText("演员：" + listToString(mValues.get(position).getAttributes().getActor()));
            holder.mArea.setText("地区：" + (mValues.get(position).getAttributes().getArea().get(1)));


            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListFragmentInteraction(holder.mItem);
                    }
                }
            });
        }

        private String listToString(List<List<String>> list) {
            if (list == null){
                return "";
            }

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                builder.append(list.get(i).get(1));
                if (i < list.size() - 1) {
                    builder.append(" · ");
                }
            }
            return builder.toString();
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final ImageView mImageView;
            public final TextView mTitleView;
            public final TextView mDirector;
            public final TextView mGenre;
            public final TextView mAirDate;
            public final TextView mActor;
            public final TextView mArea;

            public TvSectionBean.ObjectsBean mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = view.findViewById(R.id.image);
                mTitleView = (TextView) view.findViewById(R.id.title);
                mDirector = (TextView) view.findViewById(R.id.director);
                mGenre = (TextView) view.findViewById(R.id.genre);
                mAirDate = (TextView) view.findViewById(R.id.air_date);
                mActor = (TextView) view.findViewById(R.id.actor);
                mArea = (TextView) view.findViewById(R.id.area);

            }

            @Override
            public String toString() {
                return super.toString();
            }
        }

    }

    private class SpacesItemDecoration extends RecyclerView.ItemDecoration {
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

package tv.ismar.daisy.util;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.ArrayList;

import tv.ismar.daisy.bean.TvSectionBean;

public class Utility {

    @BindingAdapter("bind:image")
    public static void loadImage(ImageView image, Drawable resId){
        image.setImageDrawable(resId);
    }


    @BindingAdapter("bind:data")
    public static void setData(RecyclerView recyclerView, ArrayList<TvSectionBean.ObjectsBean> data){
//        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
//        recyclerView.setAdapter(new RecommendFragment.RecommendItemRecyclerViewAdapter(recyclerView.getContext(), data));
    }
}
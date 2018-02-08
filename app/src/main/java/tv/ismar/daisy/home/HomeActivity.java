package tv.ismar.daisy.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import tv.ismar.daisy.BaseActivity;
import tv.ismar.daisy.BaseObserver;
import tv.ismar.daisy.R;
import tv.ismar.daisy.active.ActiveServiceManager;
import tv.ismar.daisy.bean.TvChannelsBean;
import tv.ismar.daisy.bean.TvSectionBean;
import tv.ismar.daisy.dummy.DummyContent;
import tv.ismar.daisy.home.recommend.view.RecommendFragment;
import tv.ismar.daisy.home.recommend.view.SectionFragment;
import tv.ismar.daisy.player.view.PlayActivity;

public class HomeActivity extends BaseActivity implements SectionFragment.OnListFragmentInteractionListener {
    private static final String TAG = HomeActivity.class.getSimpleName();

    private ArrayList<TvChannelsBean> mTvChannelsBeans;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (fragment == null || !(fragment instanceof RecommendFragment)) {
                        transaction.replace(R.id.fragment, RecommendFragment.newInstance(mTvChannelsBeans.get(0))).commit();
                    }
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        navigation.findViewById(R.id.navigation_home).callOnClick();

        fetchChannel();

    }

    private void fetchChannel() {
        mSkyHostService.apiTvChannels()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ArrayList<TvChannelsBean>>() {
                    @Override
                    public void onSuccess(ArrayList<TvChannelsBean> tvChannelsBeans) {
                        mTvChannelsBeans = tvChannelsBeans;
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
                        if (fragment == null || !(fragment instanceof RecommendFragment)) {
                            transaction.replace(R.id.fragment, RecommendFragment.newInstance(tvChannelsBeans.get(0))).commit();
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        Logger.e(e, "FetchChannel Throwable");
                    }
                });
    }


    @Override
    public void onListFragmentInteraction(TvSectionBean.ObjectsBean item) {
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("item_url", item.getItem_url());
        startActivity(intent);
    }
}

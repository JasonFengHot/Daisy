package tv.ismar.daisy.player.presenter;


import android.text.TextUtils;

import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import tv.ismar.daisy.BaseObserver;
import tv.ismar.daisy.SkyService;
import tv.ismar.daisy.bean.ClipBean;
import tv.ismar.daisy.bean.ItemBean;
import tv.ismar.daisy.bean.PlayCheckEntity;
import tv.ismar.daisy.bean.QiyiCheckBean;
import tv.ismar.daisy.player.PlayContract;
import tv.ismar.daisy.util.Utils;

/**
 * Created by huibin on 01/02/2018.
 */

public class PlayPresenter implements PlayContract.Presenter {
    private PlayContract.View mView;

    public PlayPresenter(PlayContract.View view) {
        mView = view;
    }

    @Override
    public void fetchItem(String url) {
        SkyService.ServiceManager.getInstance().getSkyHostService().apiItem(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ItemBean>() {
                    @Override
                    public void onSuccess(ItemBean itemBean) {
                        requestPlayCheck(String.valueOf(itemBean.getItem_pk()));
                        fetchClip(itemBean.getClip().getUrl());
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void fetchClip(String url) {
        SkyService.ServiceManager.getInstance().getSkyHostService().apiClip(url, "" , "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ClipBean>() {
                    @Override
                    public void onSuccess(ClipBean bean) {
                        ClipBean clipBean = mergeClip(bean);
                        mView.startPlay(clipBean);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    private ClipBean mergeClip(ClipBean clipEntity){
        String normal = clipEntity.getNormal();
        String medium = clipEntity.getMedium();
        String high = clipEntity.getHigh();
        String ultra = clipEntity.getUltra();
        String blueray = clipEntity.getBlueray();
        String _4k = clipEntity.get_$4k();
        try {
            File file = File.createTempFile("video", ".m3u8");
            BufferedSink bufferedSink =
                    Okio.buffer(Okio.sink(file));

            bufferedSink.writeUtf8("#EXTM3U\n");

            if (!TextUtils.isEmpty(normal)) {
                bufferedSink.writeUtf8(
                        "#EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=1000000\n");
                bufferedSink.writeUtf8(
                        clipEntity.getNormal() + "\n");
            }
            if (!TextUtils.isEmpty(medium)) {
                bufferedSink.writeUtf8(
                        "#EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=2000000\n");
                bufferedSink.writeUtf8(
                        clipEntity.getMedium() + "\n");
            }
            if (!TextUtils.isEmpty(high)) {
                bufferedSink.writeUtf8(
                        "#EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=3000000\n");
                bufferedSink.writeUtf8(clipEntity.getHigh() + "\n");
            }
            if (!TextUtils.isEmpty(ultra)) {
                bufferedSink.writeUtf8(
                        "#EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=4000000\n");
                bufferedSink.writeUtf8(
                        clipEntity.getUltra() + "\n");
            }
            if (!TextUtils.isEmpty(blueray)) {
                bufferedSink.writeUtf8(
                        "#EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=5000000\n");
                bufferedSink.writeUtf8(
                        clipEntity.getBlueray() + "\n");
            }
            if (!TextUtils.isEmpty(_4k)) {
                bufferedSink.writeUtf8(
                        "#EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=6000000\n");
                bufferedSink.writeUtf8(clipEntity.get_$4k() + "\n");
            }
            bufferedSink.close();
            clipEntity.setM3u8(file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clipEntity;
    }

    public void checkQiyi(String code){
        SkyService.ServiceManager.getInstance().getCarnationHostService()
                .apiQiyiCheck(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<QiyiCheckBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(QiyiCheckBean qiyiCheckBean) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void requestPlayCheck(String itemPk) {

        SkyService.ServiceManager.getInstance().getSkyHostService().apiPlayCheck(itemPk, null, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
//                            PlayCheckEntity playCheckEntity = calculateRemainDay(responseBody.string());
//                            if (TextUtils.isEmpty(playCheckEntity.getIqiyi_code())){
////                                mDetailView.notifyPlayCheck(playCheckEntity);
//                            }else {
//                            {"iqiyi_4_0": "802853600:802853600:d3a51f928c9c8a2ee2f26056c15e8c23", "drm": "1", "is_vip": true}
                                checkQiyi("802853600:802853600:d3a51f928c9c8a2ee2f26056c15e8c23");
//                            }
                    }

                    @Override
                    public void onSuccess(ResponseBody responseBody) {

                    }

                    @Override
                    public void onFailure(Throwable e) {

                    }
                });
    }

    private PlayCheckEntity calculateRemainDay(String info) {
        PlayCheckEntity playCheckEntity;
        switch (info) {
            case "0":
                playCheckEntity = new PlayCheckEntity();
                playCheckEntity.setRemainDay(0);
                break;
            default:
                playCheckEntity = new GsonBuilder().create().fromJson(info, PlayCheckEntity.class);
                int remainDay;
                try {
                    remainDay = Utils.daysBetween(Utils.getTime(), playCheckEntity.getExpiry_date()) + 1;
                } catch (ParseException e) {
                    remainDay = 0;
                }
                playCheckEntity.setRemainDay(remainDay);
                break;
        }
        return playCheckEntity;
    }

}

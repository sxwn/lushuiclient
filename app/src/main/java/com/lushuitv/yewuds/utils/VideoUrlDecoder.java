package com.lushuitv.yewuds.utils;

import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.app.MyApp;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.module.response.VideoUrlInfo;
import com.socks.library.KLog;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description
 * Created by weip
 * Date on 2017/9/11.
 */

public abstract class VideoUrlDecoder {

    public void decodePath(int workId){
        if (Config.getCachedDataType(MyApp.getContext()) == 1){
            ApiRetrofit.getInstance().getApiService().getVerVideoUrl(workId).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<VideoUrlInfo>() {

                        @Override
                        public void onError(Throwable e) {
                            KLog.e(e.getLocalizedMessage());
                            onDecodeError();
                        }

                        @Override
                        public void onComplete() {

                        }

                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(VideoUrlInfo videoUrlInfo) {
                            KLog.e("parseVideoResponse: " + videoUrlInfo);
                            if (videoUrlInfo != null )
                                onSuccess(videoUrlInfo);
                        }
                    });

        }else if(Config.getCachedDataType(MyApp.getContext()) == 2){
            ApiRetrofit.getInstance().getApiService().getVideoUrl
                    (workId,SystemUtils.getVersion(MyApp.getContext()),1).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<VideoUrlInfo>() {

                        @Override
                        public void onError(Throwable e) {
                            KLog.e(e.getLocalizedMessage());
                            onDecodeError();
                        }

                        @Override
                        public void onComplete() {

                        }

                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(VideoUrlInfo videoUrlInfo) {
                            KLog.e("parseVideoResponse: " + videoUrlInfo);
                            if (videoUrlInfo != null )
                                onSuccess(videoUrlInfo);
                        }
                    });
        }

    }

    public abstract void onSuccess(VideoUrlInfo urlInfo);

    public abstract void onDecodeError();
}

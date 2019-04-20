package com.lushuitv.yewuds.api;

import android.text.TextUtils;

import com.lushuitv.yewuds.module.response.ResultResponse;
import com.lushuitv.yewuds.utils.UIUtils;
import com.socks.library.KLog;

import org.reactivestreams.Subscriber;

import cn.jzvd.JZVideoPlayerStandard;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * @author ChayChan
 * @description: 抽取CallBack
 * @date 2017/6/18  21:37
 */
public abstract class SubscriberCallBack<T> extends Observable<ResultResponse<T>> {

//    @Override
//    public void onNext(ResultResponse response) {
//        boolean isSuccess = (!TextUtils.isEmpty(response.message) && response.message.equals("success"))
//                || !TextUtils.isEmpty(response.success) && response.success.equals("true");
//        if (isSuccess) {
//            onSuccess((T) response.data);
//        } else {
//            UIUtils.showToast(response.message);
//            onFailure(response);
//        }
//    }
//
//
//
//    @Override
//    public void onError(Throwable e) {
//        KLog.e(e.getLocalizedMessage());
//        onError();
//    }

    protected abstract void onSuccess(T response);
    protected abstract void onError();

    protected void onFailure(ResultResponse response) {
    }

}

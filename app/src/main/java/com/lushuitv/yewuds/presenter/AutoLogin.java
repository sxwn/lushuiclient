package com.lushuitv.yewuds.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.app.MyApp;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.module.response.UserInfoResponse;
import com.lushuitv.yewuds.utils.SystemUtils;
import com.socks.library.KLog;

import java.util.UUID;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description 自动登录接口
 * Created by weip
 * Date on 2017/9/23.
 */

public class AutoLogin {
    public static String userPhone, userPwd, userId;

    // 构造函数
    public AutoLogin() {
    }

    public interface AutoCallBack {
        void callBack(String data);
    }

    // 判断是否可以自动登录
    public static boolean autoLogin() {
        userPhone = Config.getCachedPhoneNum(MyApp.getContext());
        userId = Config.getCachedUserId(MyApp.getContext());
        userPwd = Config.getCachedUserPwd(MyApp.getContext());
        if (userId != null && !"".equals(userId)) {
            return true;
        } else {
            return false;
        }
    }

    public static void Login(final AutoCallBack callBack) {
        if (userPhone != null && !TextUtils.isEmpty(userPhone)) {
            //手机号自动登录
            //ApiRetrofit.getInstance().getApiService().getAutoLogin(userPhone, Config.getCachedUserPwd(MyApp.getContext()), 2)
            ApiRetrofit.getInstance().getApiService().getAutoLogin(userPhone,SystemUtils.getUuid(), 2)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<UserInfoResponse>() {
                        @Override
                        public void onError(Throwable e) {
                            KLog.e(e.getLocalizedMessage());
                        }

                        @Override
                        public void onComplete() {
                            KLog.e("成功");
                        }

                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(UserInfoResponse userInfoResponse) {
                            if ("0".equals(userInfoResponse.getSTATUS()))
                                callBack.callBack(userInfoResponse.getOBJECT().toString());
                        }
                    });
        }
    }
}

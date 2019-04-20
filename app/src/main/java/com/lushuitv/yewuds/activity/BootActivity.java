package com.lushuitv.yewuds.activity;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.lushuitv.yewuds.MainActivity;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.app.MyApp;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.boot.module.DataType;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.module.entity.UseParams;
import com.lushuitv.yewuds.presenter.AutoLogin;
import com.socks.library.KLog;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Description 启动页
 * Created by weip
 * Date on 2017/9/19.
 */

public class BootActivity extends BaseActivity {
    private View mView;
    int max = 4;

    @Override
    public void initOptions() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        if (AutoLogin.autoLogin()) {
                            AutoLogin.Login(new AutoLogin.AutoCallBack() {
                                @Override
                                public void callBack(String data) {
                                    UseParams useParams = new Gson().fromJson(data, UseParams.class);
                                    KLog.e("=====================执行了吧" + useParams.getSessionId());
                                    Config.cacheSessionId(BootActivity.this, useParams.getSessionId());
                                }
                            });
                        }
                    }
                }
        ).start();
        Config.cacheDataType(BootActivity.this, 2);
        Observable.timer(1, TimeUnit.SECONDS)
                .repeat()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        max--;
                        if (max == 0) {
                            // 获取是不是第一次打开的标记
                            boolean isFirstFlag = Config.getBoolean("first");
                            // 如果是true 意味着是第一次打开
                            if (isFirstFlag) {
                                startActivity(new Intent(BootActivity.this, GuideActivity.class));
                            } else {// 用户此次不是第一次打开此app
                                startActivity(new Intent(BootActivity.this, MainActivity.class));
                            }
                            finish();// 结束当前activity
                        }
                    }
                });
    }

    @Override
    protected View initContentView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去除状态栏
        mView = View.inflate(this, R.layout.activity_boot_layout, null);
        return mView;
    }

    @Override
    public void initToolbar() {
        appBarLayout.setVisibility(View.GONE);
    }

}

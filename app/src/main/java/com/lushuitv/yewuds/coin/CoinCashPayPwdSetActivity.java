package com.lushuitv.yewuds.coin;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.activity.ObtainCashActivtiy;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.flyn.Eyes;
import com.lushuitv.yewuds.module.entity.HttpBean;
import com.lushuitv.yewuds.utils.MD5Tool;
import com.lushuitv.yewuds.utils.SystemUtils;
import com.lushuitv.yewuds.utils.UIUtils;
import com.lushuitv.yewuds.view.gridpasswordview.GridPasswordView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 设置支付密码
 * Created by weip on 2017\12\22 0022.
 */

public class CoinCashPayPwdSetActivity extends BaseActivity implements View.OnClickListener {

    private View mView;
    private GridPasswordView firstPwdView, secondPwdView;
    private TextView pwdSet;

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.activity_coin_paypwd_set, null);
        return mView;
    }

    @Override
    public void initToolbar() {
        mToolbar.setBackgroundColor(getResources().getColor(R.color.white));
        mToolbar.setNavigationIcon(R.drawable.back_black);
        mToolbarTitle.setText("支付密码设置");
        mToolbarTitle.setTextColor(getResources().getColor(R.color.black));
        setSupportActionBar(mToolbar);
        //设置返回键可用
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Eyes.setStatusBarLightMode(this, getResources().getColor(R.color.white));
    }

    @Override
    protected void initOptions() {
        firstPwdView = (GridPasswordView) mView.findViewById(R.id.pwdpwd_set_first);
        secondPwdView = (GridPasswordView) mView.findViewById(R.id.pwdpwd_set_second);
        pwdSet = (TextView) mView.findViewById(R.id.coin_paypwd_set);
        pwdSet.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base_toolbar, menu);
        updateOptionsMenu(menu);
        return true;
    }

    protected void updateOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_share).setVisible(false);
        menu.findItem(R.id.action_save).setVisible(false);
        menu.findItem(R.id.action_download).setVisible(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.coin_paypwd_set:
                if (!firstPwdView.getPassWord().equals(secondPwdView.getPassWord())) {
                    UIUtils.showToast("设置密码不一致");
                } else {
                    //调取设置密码接口
                    ApiRetrofit.getInstance().getApiService().getSetPayPassword
                            (Config.getCachedUserId(this), SystemUtils.getVersion(this), firstPwdView.getPassWord())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<HttpBean>() {

                                           @Override
                                           public void onError(Throwable e) {

                                           }

                                           @Override
                                           public void onComplete() {

                                           }

                                           @Override
                                           public void onSubscribe(Disposable d) {

                                           }

                                           @Override
                                           public void onNext(HttpBean httpBean) {
                                               if ("0".equals(httpBean.getSTATUS())) {
                                                   Config.cachePayStatus(CoinCashPayPwdSetActivity.this, 1);//设置过支付密码
                                                   UIUtils.showToast("支付密码设置成功");
                                                   finish();
                                               } else {
                                                   UIUtils.showToast("数据异常");
                                               }
                                           }
                                       }
                            );
                }
                break;
        }
    }
}

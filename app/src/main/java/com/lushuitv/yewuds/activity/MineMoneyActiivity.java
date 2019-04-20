package com.lushuitv.yewuds.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lushuitv.yewuds.MainActivity;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.flyn.Eyes;
import com.lushuitv.yewuds.module.response.UserPurseResponse;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description 我的钱包
 * Created by weip
 * Date on 2017/9/1.
 */

public class MineMoneyActiivity extends BaseActivity implements View.OnClickListener {
    TextView obtainCash;
    private View mView;

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.activity_mine_money, null);
        return mView;
    }

    @Override
    public void initToolbar() {
        mToolbar.setBackgroundColor(getResources().getColor(R.color.white));
        mToolbar.setNavigationIcon(R.drawable.back_black);
        mToolbarTitle.setTextColor(getResources().getColor(R.color.black));
        mToolbarTitle.setText("我的钱包");
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

    TextView totalNumber, shareIncome;

    RelativeLayout goShare;

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
    public void initOptions() {
        shareIncome = (TextView) findViewById(R.id.mine_money_share_income);
        goShare = (RelativeLayout) findViewById(R.id.money_money_goshare);
        goShare.setOnClickListener(this);
        obtainCash = (TextView) findViewById(R.id.money_obtain);
        obtainCash.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ApiRetrofit.getInstance().getApiService().getUserPurse()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserPurseResponse>() {

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
                    public void onNext(UserPurseResponse userPurseResponse) {
                        if ("0".equals(userPurseResponse.getSTATUS())) {
//                            userPurseDis = userPurseResponse.getOBJECT().getUserPurseDis();
//                            totalNumber.setText("" + userPurseDis);
                            shareIncome.setText("" + userPurseResponse.getOBJECT().getUserRewardPurseDis());
                        }
                    }
                });
    }

    double userPurseDis;//用户钱包金额


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.money_money_goshare://去分享、跳转到首页
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.money_obtain:
                startActivity(new Intent(this, ObtainCashActivtiy.class));
                break;
        }
    }

}

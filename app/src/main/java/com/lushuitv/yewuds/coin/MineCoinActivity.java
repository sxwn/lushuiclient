package com.lushuitv.yewuds.coin;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.app.ApiConstant;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.detail.RulePageActivity;
import com.lushuitv.yewuds.flyn.Eyes;
import com.lushuitv.yewuds.utils.SystemUtils;
import com.lushuitv.yewuds.utils.UIUtils;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 我的金币
 * Created by weip on 2017\12\18 0018.
 */

public class MineCoinActivity extends BaseActivity implements View.OnClickListener {

    private View mView;

    private TextView coinRecharge;
    private TextView coinTotalNumber, coinIncomeNumber, coinRechargeNumber;
    private RelativeLayout payPwdSetRl, obtainCashAccountRl;
    private TextView chargeAccount, incomeAccount;

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.activity_mine_coin, null);
        return mView;
    }

    @Override
    public void initToolbar() {
        mToolbar.setBackgroundColor(getResources().getColor(R.color.white));
        mToolbar.setNavigationIcon(R.drawable.back_black);
        mToolbarTitle.setText("我的金币");
        mToolbarTitle.setTextColor(getResources().getColor(R.color.black));
        mToolbarRightTitle.setText("交易记录");
        mToolbarRightTitle.setTextColor(getResources().getColor(R.color.black));
        mToolbarRightTitle.setOnClickListener(this);
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
        coinTotalNumber = (TextView) mView.findViewById(R.id.mine_coin_totalmoney);
        coinIncomeNumber = (TextView) mView.findViewById(R.id.mine_coin_income_tv);
        coinRechargeNumber = (TextView) mView.findViewById(R.id.mine_coin_rechage_tv);
        Drawable chargeAccountDrawable = getResources().getDrawable(R.drawable.coin_account_in_icon);
        chargeAccountDrawable.setBounds(0, 0, UIUtils.dip2Px(60), UIUtils.dip2Px(60));
        Drawable incomeAccountDrawable = getResources().getDrawable(R.drawable.coin_account_out_icon);
        incomeAccountDrawable.setBounds(0, 0, UIUtils.dip2Px(60), UIUtils.dip2Px(60));
        chargeAccount = (TextView) mView.findViewById(R.id.mine_coin_account_in);
        chargeAccount.setCompoundDrawables(null, chargeAccountDrawable, null, null);
        incomeAccount = (TextView) mView.findViewById(R.id.mine_coin_account_out);
        incomeAccount.setCompoundDrawables(null, incomeAccountDrawable, null, null);
        obtainCashAccountRl = (RelativeLayout) mView.findViewById(R.id.mine_coin_obtaincash_account_set);
        obtainCashAccountRl.setOnClickListener(this);
        payPwdSetRl = (RelativeLayout) mView.findViewById(R.id.mine_coin_paypwd_set);
        payPwdSetRl.setOnClickListener(this);
        coinRecharge = (TextView) mView.findViewById(R.id.mine_coin_recharge);
        coinRecharge.setOnClickListener(this);
        //提现50%透明度背景
        ((TextView) mView.findViewById(R.id.mine_coin_obtaincash)).setBackgroundColor(Color.argb(128, 235, 209, 127));
        mView.findViewById(R.id.mine_coin_obtaincash).setOnClickListener(this);
        mView.findViewById(R.id.mine_coin_rule).setOnClickListener(this);
        Map<String, String> map = new HashMap<>();
        map.put("version", SystemUtils.getVersion(this));
        map.put("userId", Config.getCachedUserId(this));
        ApiRetrofit.getInstance().getApiService().getFindGold(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CoinResponse>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(CoinResponse response) {
                        if (response.getSTATUS().equals("0")) {
                            coinIncomeNumber.setText("" + response.getOBJECT().getList().get(0).getGold());
                            coinRechargeNumber.setText("" + (response.getOBJECT().getList().get(1).getGold() + response.getOBJECT().getList().get(2).getGold()));
                            coinTotalNumber.setText("" + (response.getOBJECT().getList().get(0).getGold()
                                    + response.getOBJECT().getList().get(1).getGold() + response.getOBJECT().getList().get(2).getGold()));
                        }
                    }
                });
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
            case R.id.toolbar_righttitle://交易记录
                startActivity(new Intent(this, CoinTradeActivity.class));
                break;
            case R.id.mine_coin_obtaincash_account_set://提取账户设置
                startActivity(new Intent(this, CoinCashAccountSetActivity.class));
                break;
            case R.id.mine_coin_paypwd_set://支付密码设置
                startActivity(new Intent(this, CoinCashPayPwdSetActivity.class));
                break;
            case R.id.mine_coin_obtaincash://提现
//                startActivity(new Intent(this, ObtainCashActivtiy.class));
                startActivity(new Intent(this, CoinCashActivtiy.class));
                break;
            case R.id.mine_coin_recharge://充值
                startActivity(new Intent(this, CoinRechargeActivity.class));
                break;
            case R.id.mine_coin_rule://使用规则
                startActivity(new Intent(this, RulePageActivity.class)
                        .putExtra("url", ApiConstant.BASE_SERVER_H5_URL + "h5/rule/gold_make_rule.html"));
                break;
        }
    }
}

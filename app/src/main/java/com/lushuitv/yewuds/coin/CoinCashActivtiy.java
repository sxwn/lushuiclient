package com.lushuitv.yewuds.coin;

import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.app.ApiConstant;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.detail.RulePageActivity;
import com.lushuitv.yewuds.flyn.Eyes;

/**
 * 金币提现页面
 * Created by weip on 2017\12\19 0019.
 */

public class CoinCashActivtiy extends BaseActivity implements View.OnClickListener {

    private View mView;
    private TextView cashRule,cashSetAccount;

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.activity_coin_obtain_cash, null);
        return mView;
    }
    @Override
    public void initToolbar() {
        mToolbar.setBackgroundColor(getResources().getColor(R.color.white));
        mToolbar.setNavigationIcon(R.drawable.back_black);
        mToolbarTitle.setText("提现");
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
        cashRule = (TextView) mView.findViewById(R.id.coin_obtain_cash_rule);
        cashRule.setOnClickListener(this);
        cashSetAccount = (TextView) mView.findViewById(R.id.coin_obtain_cash_setaccount);
        cashSetAccount.setOnClickListener(this);
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
        switch (v.getId()){
            case R.id.coin_obtain_cash_rule:
                startActivity(new Intent(this, RulePageActivity.class).
                        putExtra("url", ApiConstant.BASE_SERVER_H5_URL+"h5/rule/income_cash_rule.html"));
                break;
            case R.id.coin_obtain_cash_setaccount:
                startActivity(new Intent(this, CoinCashAccountSetActivity.class));
                break;
        }
    }
}

package com.lushuitv.yewuds.coin;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.adapter.MainTabAdapter;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.flyn.Eyes;

import java.util.ArrayList;
import java.util.List;

/**
 * 交易记录
 * Created by weip on 2017\12\27 0027.
 */

public class CoinTradeActivity extends BaseActivity {

    private View mView;
    private RadioGroup tradeRg;
    private RadioButton incomeRb, consumeRb;
    private ViewPager coinTradeViewpager;
    private IncomeRecordFragment incomeFragment;
    private ConsumerRecordFragment consumerRecordFragment;

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.coin_trade_layout, null);
        return mView;
    }

    @Override
    public void initToolbar() {
        mToolbar.setBackgroundColor(getResources().getColor(R.color.white));
        mToolbar.setNavigationIcon(R.drawable.back_black);
        mToolbarTitle.setText("交易记录");
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
        tradeRg = (RadioGroup) mView.findViewById(R.id.coin_trade_rg);
        incomeRb = (RadioButton) mView.findViewById(R.id.coin_trade_income_rb);
        consumeRb = (RadioButton) mView.findViewById(R.id.coin_trade_consume_rb);
        if (incomeRb.isChecked()) {
            incomeRb.setTextColor(getResources().getColor(R.color.white));
        } else {
            incomeRb.setTextColor(getResources().getColor(R.color.coin_checked_color));
        }
        coinTradeViewpager = (ViewPager) mView.findViewById(R.id.coin_trade_vp);
        incomeFragment = new IncomeRecordFragment();
        consumerRecordFragment = new ConsumerRecordFragment();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(incomeFragment);
        fragments.add(consumerRecordFragment);
        coinTradeViewpager.setAdapter(new MainTabAdapter(fragments, getSupportFragmentManager()));
        coinTradeViewpager.setCurrentItem(0);
        //RadioGroup选中状态改变监听
        tradeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.coin_trade_income_rb:
                        /**
                         * setCurrentItem第二个参数控制页面切换动画
                         * true:打开/false:关闭
                         */
                        incomeRb.setTextColor(getResources().getColor(R.color.white));
                        consumeRb.setTextColor(getResources().getColor(R.color.coin_checked_color));
                        coinTradeViewpager.setCurrentItem(0, false);
                        break;
                    case R.id.coin_trade_consume_rb:
                        incomeRb.setTextColor(getResources().getColor(R.color.coin_checked_color));
                        consumeRb.setTextColor(getResources().getColor(R.color.white));
                        coinTradeViewpager.setCurrentItem(1, false);
                        break;
                }
            }
        });
        coinTradeViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        incomeRb.setChecked(true);
                        consumeRb.setChecked(false);
                        incomeRb.setTextColor(getResources().getColor(R.color.white));
                        consumeRb.setTextColor(getResources().getColor(R.color.coin_checked_color));
                        break;
                    case 1:
                        incomeRb.setChecked(false);
                        consumeRb.setChecked(true);
                        incomeRb.setTextColor(getResources().getColor(R.color.coin_checked_color));
                        consumeRb.setTextColor(getResources().getColor(R.color.white));
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

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
}

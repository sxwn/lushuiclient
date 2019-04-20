package com.lushuitv.yewuds.vip;

import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.flyn.Eyes;

/**
 * Created by weip on 2017\12\12 0012.
 */

public class VipExchangeActivity extends BaseActivity {

    private View mView;

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.vip_exchange_layout, null);
        return mView;
    }

    private Toolbar mToolbar;
    private TextView mToolbarTitle;

    @Override
    public void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.vip_bg_start_color));
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarTitle.setText("兑换");
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
    }


    @Override
    public void initOptions() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        Eyes.setStatusBarColor(this, Color.parseColor("#FF5E62"));//vip起始色
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

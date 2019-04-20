package com.lushuitv.yewuds.coin;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;

import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.flyn.Eyes;

import java.util.ArrayList;
import java.util.List;

/**
 * 充值记录
 * Created by weip on 2017\12\19 0019.
 */

public class CoinRechargeRecord extends BaseActivity {

    private View mView;
    private RecyclerView rechargeRecordView;
    private CoinRechargeRecordAdapter adapter;
    private List<String> mDatas;

    @Override
    protected void initOptions() {
        initData();
        rechargeRecordView = (RecyclerView) mView.findViewById(R.id.coin_recharge_recycleview);
        CoinRechargeRecordAdapter myAdapter = new CoinRechargeRecordAdapter(this, mDatas);
        rechargeRecordView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //设置增加或删除条目动画
        rechargeRecordView.setItemAnimator(new DefaultItemAnimator());
        rechargeRecordView.setAdapter(myAdapter);//设置适配器
    }

    private void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 0; i < 9; i++) {
            mDatas.add("item" + i);
        }
    }

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.activity_coin_recharge_record, null);
        return mView;
    }

    @Override
    public void initToolbar() {
        mToolbar.setBackgroundColor(getResources().getColor(R.color.white));
        mToolbar.setNavigationIcon(R.drawable.back_black);
        mToolbarTitle.setText("充值记录");
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

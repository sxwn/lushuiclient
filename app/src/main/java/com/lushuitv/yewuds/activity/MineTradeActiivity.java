package com.lushuitv.yewuds.activity;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.adapter.MineSalesAdapter;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.flyn.Eyes;
import com.lushuitv.yewuds.module.response.TradeRecordResponse;
import com.lushuitv.yewuds.view.PullToRefreshLayout;
import com.lushuitv.yewuds.view.pullableview.PullableListView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description 我的交易
 * Created by weip
 * Date on 2017/9/1.
 */

public class MineTradeActiivity extends BaseActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener {

    MineSalesAdapter adapter;

    private int currentPage = 1;

    List<TradeRecordResponse.LISTBean> mLists;

    PullToRefreshLayout pullToRefreshLayout;

    PullableListView pullableListView;

    private View mView;

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.activity_mine_sales, null);
        return mView;
    }

    @Override
    public void initToolbar() {
        mToolbar.setBackgroundColor(getResources().getColor(R.color.white));
        mToolbar.setNavigationIcon(R.drawable.back_black);
        mToolbarTitle.setTextColor(getResources().getColor(R.color.black));
        mToolbarTitle.setText("交易记录");
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
    public void initOptions() {
        mLists = new ArrayList<TradeRecordResponse.LISTBean>();
        pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.mine_sales_refresh_view);
        pullToRefreshLayout.setOnRefreshListener(this);
        pullableListView = (PullableListView) findViewById(R.id.mine_sales_content_view);
        adapter = new MineSalesAdapter(this, mLists);
        pullableListView.setAdapter(adapter);
        initData(currentPage);
    }

    public void initData(int page) {
        ApiRetrofit.getInstance().getApiService().getOrderList(page, 9)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TradeRecordResponse>() {

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
                    public void onNext(TradeRecordResponse tradeRecordResponse) {
                        if ("0".equals(tradeRecordResponse.getSTATUS())) {
                            if (page == 1) {
                                mLists.clear();
                                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                            } else {
                                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                            }
                            adapter.setData(tradeRecordResponse.getLIST());
                        }
                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
//        new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                // 千万别忘了告诉控件刷新完毕了哦！
//                currentPage = 1;
//                initData(currentPage);
//                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
//            }
//        }.sendEmptyMessageDelayed(0,1000);

        currentPage = 1;
        initData(currentPage);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        // 加载操作
//        new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                // 千万别忘了告诉控件加载完毕了哦！
//                currentPage++;
//                initData(currentPage);
//                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
//            }
//        }.sendEmptyMessageDelayed(0,1000);
        currentPage++;
        initData(currentPage);
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

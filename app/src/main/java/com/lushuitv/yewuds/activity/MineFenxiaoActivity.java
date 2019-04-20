package com.lushuitv.yewuds.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.adapter.MineFenxiaoAdapter;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.flyn.Eyes;
import com.lushuitv.yewuds.module.response.FenXiaoResponse;
import com.lushuitv.yewuds.view.PullToRefreshLayout;
import com.lushuitv.yewuds.view.pullableview.PullableListView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description
 * Created by weip
 * Date on 2017/10/24.
 */

public class MineFenxiaoActivity extends BaseActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener {
    List<FenXiaoResponse.LISTBean> lists;
    PullToRefreshLayout pullToRefreshLayout;

    PullableListView pullableListView;

    MineFenxiaoAdapter adapter;
    private View mView;

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.activity_mine_notice, null);
        return mView;
    }

    @Override
    public void initToolbar() {
        mToolbar.setBackgroundColor(getResources().getColor(R.color.white));
        mToolbar.setNavigationIcon(R.drawable.back_black);
        mToolbarTitle.setText("分销记录");
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
    public void initOptions() {
        lists = new ArrayList<FenXiaoResponse.LISTBean>();
        pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.mine_notice_refresh_view);
        pullToRefreshLayout.setOnRefreshListener(this);
        pullableListView = (PullableListView) findViewById(R.id.mine_notice_content_view);
        View view = LayoutInflater.from(this).inflate(R.layout.fenxiao_layout_title, null);
        pullableListView.addHeaderView(view);
        adapter = new MineFenxiaoAdapter(this, lists);
        pullableListView.setAdapter(adapter);
        initData();
    }

    int currentPage = 1;

    public void initData() {
        ApiRetrofit.getInstance().getApiService().getUserBranch(currentPage, 8)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FenXiaoResponse>() {
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
                    public void onNext(FenXiaoResponse response) {
                        if ("0".equals(response.getSTATUS())) {
                            if (response.getLIST() == null)
                                return;
                            if (currentPage == 1)
                                lists.clear();
                            adapter.setData(response.getLIST());
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
        initData();
        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        currentPage++;
        initData();
        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
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

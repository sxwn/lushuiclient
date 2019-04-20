package com.lushuitv.yewuds.activity;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.adapter.MineNoticeAdapter;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.flyn.Eyes;
import com.lushuitv.yewuds.module.response.UserAttentionListBean;
import com.lushuitv.yewuds.view.PullToRefreshLayout;
import com.lushuitv.yewuds.view.pullableview.PullableListView;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description 我的关注
 * Created by weip
 * Date on 2017/9/1.
 */

public class MineNoticeActiivity extends BaseActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener {

    MineNoticeAdapter adapter;

    List<UserAttentionListBean.AttantionUser> lists;

    PullToRefreshLayout pullToRefreshLayout;

    PullableListView pullableListView;
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
        mToolbarTitle.setText("我的关注");
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
        lists = new ArrayList<UserAttentionListBean.AttantionUser>();
        pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.mine_notice_refresh_view);
        pullToRefreshLayout.setOnRefreshListener(this);
        pullableListView = (PullableListView) findViewById(R.id.mine_notice_content_view);
        adapter = new MineNoticeAdapter(this, lists);
        pullableListView.setAdapter(adapter);
        initData();
    }

    int currentPage = 1;

    public void initData() {
        ApiRetrofit.getInstance().getApiService().getUserAttentionList(currentPage, 8)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserAttentionListBean>() {
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
                    public void onNext(UserAttentionListBean userAttentionListBean) {
                        if ("0".equals(userAttentionListBean.getSTATUS())) {
                            if (currentPage == 1) {
                                lists.clear();
                                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                            } else {
                                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                            }
                            adapter.setData(userAttentionListBean.getOBJECT());
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
        currentPage = 1;
        initData();

    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        currentPage++;
        initData();
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

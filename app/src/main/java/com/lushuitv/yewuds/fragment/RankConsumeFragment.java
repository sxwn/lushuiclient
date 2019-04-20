package com.lushuitv.yewuds.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.adapter.RankIncomeAdapter;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.module.response.RankIncomeResponse;
import com.lushuitv.yewuds.utils.RecycleViewDivider;
import com.lushuitv.yewuds.utils.RecycleViewUtil;
import com.lushuitv.yewuds.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description
 * Created by weip
 * Date on 2017/9/18.
 */

public class RankConsumeFragment extends MyLazyLoadFragment {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView cosumeRv;

    private RankIncomeAdapter adapter;

    private List<RankIncomeResponse.LISTBean> mLists;
    private RankIncomeResponse.OBJECTBean obJectBean;

    @Override
    protected int setContentView() {
        return R.layout.rank_consume_fragment;
    }

    @Override
    protected void loadData() {
        initView();
        initData();
    }

    private void initView() {
        mLists = new ArrayList<RankIncomeResponse.LISTBean>();
        obJectBean = new RankIncomeResponse.OBJECTBean();
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        cosumeRv = (RecyclerView) findViewById(R.id.rank_consume_fragment_layout);
        cosumeRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        cosumeRv.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, R.drawable.custom_divider_line));
        adapter = new RankIncomeAdapter(getActivity(), mLists, obJectBean, 2);
        cosumeRv.setAdapter(adapter);
        refreshLayout.setColorSchemeResources(R.color.common_grey,
                R.color.common_grey, R.color.common_grey);
        //给swipeRefreshLayout绑定刷新监听
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);//结束刷新操作
                initData();
            }
        });
        cosumeRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (RecycleViewUtil.isSlideToBottom(recyclerView)) {
                    if (mLists.size() == 0)
                        return;
                    UIUtils.showToast("消费榜仅显示前" + mLists.size() + "位");
                }
            }
        });
    }

    private void initData() {
        if (Config.getCachedDataType(getActivity()) == 2) {
            ApiRetrofit.getInstance().getApiService().getUserBuyRanking()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<RankIncomeResponse>() {

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
                        public void onNext(RankIncomeResponse rankIncomeResponse) {
                            if ("0".equals(rankIncomeResponse.getSTATUS())) {
                                adapter.setData(rankIncomeResponse.getLIST(), rankIncomeResponse.getOBJECT());
                            }
                        }
                    });
        }
    }
}

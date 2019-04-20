package com.lushuitv.yewuds.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.activity.ModelPageActivity;
import com.lushuitv.yewuds.adapter.RankCollectAdapter;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.module.response.WorksResponse;
import com.lushuitv.yewuds.module.response.WorksResponseBean;
import com.lushuitv.yewuds.utils.RecycleViewDivider;
import com.lushuitv.yewuds.utils.RecycleViewUtil;
import com.lushuitv.yewuds.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;
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

public class RankCollectFragment extends MyLazyLoadFragment {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView rankCollectRv;
    private RankCollectAdapter adapter;

    private List<WorksResponseBean> mLists;

    private TextView emptyView;

    @Override
    protected int setContentView() {
        return R.layout.rank_collect_fragment;
    }

    @Override
    protected void loadData() {
        initView();
        initData();
    }

    private void initView() {
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mLists = new ArrayList<WorksResponseBean>();
        rankCollectRv = (RecyclerView) findViewById(R.id.rank_collect_fragment_layout);
        rankCollectRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rankCollectRv.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, R.drawable.custom_divider_line));
        adapter = new RankCollectAdapter(getActivity(), mLists);
        rankCollectRv.setAdapter(adapter);
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
        adapter.setOnUserCenterInterface(new RankCollectAdapter.onUserCenterInterface() {
            @Override
            public void onItemClickListener(View v, int position) {
                Intent in = new Intent(getActivity(), ModelPageActivity.class);
                in.putExtra("actor_id", Integer.parseInt(mLists.get(position - 1).getWorksActor()));
                startActivity(in);
            }
        });
        rankCollectRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (RecycleViewUtil.isSlideToBottom(recyclerView)) {
                    if (mLists.size() ==0)
                        return;
                    UIUtils.showToast("收藏榜仅取前" + mLists.size() + "位");
                }
            }
        });
        emptyView = (TextView) findViewById(R.id.empty_view);
    }

    private void initData() {
        if (Config.getCachedDataType(getActivity()) == 2) {
            ApiRetrofit.getInstance().getApiService().getCollectionRanking()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<WorksResponse>() {

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
                                   public void onNext(WorksResponse worksResponse) {
                                       if ("0".equals(worksResponse.getSTATUS())) {
                                           if (worksResponse.getLIST().size() == 0)
                                               emptyView.setVisibility(View.VISIBLE);
                                           else
                                               adapter.setData(worksResponse.getLIST());
                                       }
                                   }
                               }
                    );
        } else {
            emptyView.setVisibility(View.VISIBLE);
        }
    }

}

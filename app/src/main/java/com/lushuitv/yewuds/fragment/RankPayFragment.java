package com.lushuitv.yewuds.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.activity.ModelPageActivity;
import com.lushuitv.yewuds.adapter.RankCollectAdapter;
import com.lushuitv.yewuds.adapter.RankPayAdapter;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.module.response.RankWorksBuyResponse;
import com.lushuitv.yewuds.utils.RecycleViewDivider;
import com.lushuitv.yewuds.utils.RecycleViewUtil;
import com.lushuitv.yewuds.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description
 * Created by weip
 * Date on 2017/9/18.
 */

public class RankPayFragment extends MyLazyLoadFragment {
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView rankCollectRv;
    private RankPayAdapter adapter;
    private List<RankWorksBuyResponse.LISTBean> mDatas;
    private TextView emptyView;

    public void initView() {
        mDatas = new ArrayList<RankWorksBuyResponse.LISTBean>();
        emptyView = (TextView) findViewById(R.id.empty_view);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        rankCollectRv = (RecyclerView) findViewById(R.id.rank_pay_fragment_layout);
        rankCollectRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rankCollectRv.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, R.drawable.custom_divider_line));
        adapter = new RankPayAdapter(getActivity(), mDatas);
        rankCollectRv.setAdapter(adapter);
        refreshLayout.setColorSchemeResources(R.color.common_grey,
                R.color.common_grey, R.color.common_grey);
        //给swipeRefreshLayout绑定刷新监听
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);//结束刷新操作
                loadData();
            }
        });
        adapter.setOnUserCenterInterface(new RankCollectAdapter.onUserCenterInterface() {
            @Override
            public void onItemClickListener(View v, int position) {
                Intent in = new Intent(getActivity(), ModelPageActivity.class);
                in.putExtra("actor_id", mDatas.get(position - 1).getWorksActor());
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
                    if (size == 0)
                        return;
                    UIUtils.showToast("付费榜仅取前" + size + "位");
                }
            }
        });
    }

    int size;

    protected void initData() {
        if (Config.getCachedDataType(getActivity()) == 2) {
            ApiRetrofit.getInstance().getApiService().getWorksBuyRanking()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<RankWorksBuyResponse>() {

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
                                   public void onNext(RankWorksBuyResponse rankWorksBuyResponse) {
                                       if ("0".equals(rankWorksBuyResponse.getSTATUS())) {
                                           size = rankWorksBuyResponse.getLIST().size();
                                           if (size == 0) {
                                               rankCollectRv.setVisibility(View.GONE);
                                               emptyView.setVisibility(View.VISIBLE);
                                           } else {
                                               emptyView.setVisibility(View.GONE);
                                               adapter.setData(rankWorksBuyResponse.getLIST());
                                           }

                                       }
                                   }
                               }
                    );
        } else {
            rankCollectRv.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected int setContentView() {
        return R.layout.rank_pay_fragment;
    }

    @Override
    protected void loadData() {
        initView();
        initData();
    }
}

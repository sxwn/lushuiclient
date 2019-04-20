package com.lushuitv.yewuds.coin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.constant.Constants;
import com.lushuitv.yewuds.fragment.VideoListFragment;
import com.lushuitv.yewuds.module.response.HomeAll;
import com.lushuitv.yewuds.module.response.UserInfoResponse;
import com.lushuitv.yewuds.utils.SystemUtils;
import com.lushuitv.yewuds.utils.UIUtils;
import com.lushuitv.yewuds.view.EmptyRecyclerView;
import com.socks.library.KLog;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 收入交易记录
 * Created by weip on 2017\12\27 0027.
 */

public class IncomeRecordFragment extends Fragment {
    private EmptyRecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private AVLoadingIndicatorView mAvi;
    private AVLoadingIndicatorView mAviLoadMore;
    private View mEmptyView;
    private Disposable mDisposable;
    private int mPage = 1;
    private LinearLayout mLayoutLoadMore;
    private boolean mIsLoadMore = true;//是否可以加载更多
    private RecordAdapter adapter;
    private List<CoinListResponse.OBJECTBean> incomeRecords;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income_record,container,false);
        initView(view);
        //initData();
        return view;
    }
    private void initView(View view) {
        incomeRecords = new ArrayList<>();
        mRecyclerView = (EmptyRecyclerView) view.findViewById(R.id.fragment_income_record_recycleview);
        mEmptyView = view.findViewById(R.id.empty_view);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshlayout);
        mAvi = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
        mAviLoadMore = (AVLoadingIndicatorView) view.findViewById(R.id.avi_loadmore);
        mLayoutLoadMore = (LinearLayout) view.findViewById(R.id.layout_loadmore);
        //设置下拉刷新样式
        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        adapter = new RecordAdapter(getActivity(),incomeRecords);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        //设置增加或删除条目动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);//设置适配器
        mRecyclerView.setmEmptyView(mEmptyView);
        mRecyclerView.hideEmptyView();
        getDataFromServer(Constants.GET_DATA_TYPE_NORMAL);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新事件
                mPage = 1;
                mIsLoadMore = true;
                getDataFromServer(Constants.GET_DATA_TYPE_NORMAL);
            }
        });
        //监听上拉加载更多
        mRecyclerView.addOnScrollListener(new IncomeRecordFragment.RecyclerViewScrollListener());
    }
    /**
     * RecyclerView 滑动监听器
     */
    class RecyclerViewScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (incomeRecords.size() < 1) {
                return;
            }
            //如果正在下拉刷新则放弃监听状态
            if (mRefreshLayout.isRefreshing()) {
                return;
            }
            //当前RecyclerView显示出来的最后一个的item的position,默认为-1
            int lastPosition = -1;
            //当前状态为停止滑动状态SCROLL_STATE_IDLE时
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                //分别判断三种类型
                lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                // 判断是否可以向下滑动，如果不能就代表已经到底部
                if (!recyclerView.canScrollVertically(1)) {
                    recyclerView.smoothScrollToPosition(lastPosition);
                    if (!mIsLoadMore) {
                        UIUtils.showToast("没有更多数据了....");
                        return;
                    }
                    //此时需要请求更多数据，显示加载更多界面
                    mPage++;
                    startLoadingMore();
                    getDataFromServer(Constants.GET_DATA_TYPE_LOADMORE);
                }
            }
        }
    }
    private void getDataFromServer(int type) {
        String version = SystemUtils.getVersion(getActivity());
        String userId = Config.getCachedUserId(getActivity());
        ApiRetrofit.getInstance().getApiService().getIncomeRecord(version,userId,mPage,Constants.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CoinListResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(CoinListResponse coinListResponse) {
                        //更新界面数据
                        if (Constants.GET_DATA_TYPE_NORMAL == type) {
                            //正常模式下，清空之前数据，重新加载
                            incomeRecords.clear();
                            incomeRecords = coinListResponse.getOBJECT();
                        } else {
                            //加载更多模式
                            incomeRecords.addAll(coinListResponse.getOBJECT());
                        }
                        //如果获取的数据不足一页，代表当前已经没有更过数据，关闭加载更多
                        if (coinListResponse.getOBJECT().size() < Constants.PAGE_SIZE) {
                            mIsLoadMore = false;
                        }
                        adapter.setData(incomeRecords);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        stopRefresh();
                        hideLoading();
                        stopLoadingMore();
                    }

                    @Override
                    public void onComplete() {
                        stopRefresh();
                        hideLoading();
                        stopLoadingMore();
                    }
                });
    }
    public void hideLoading() {
        if (mAvi.isShown()) {
            mAvi.smoothToHide();
        }
    }

    public void stopRefresh() {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    public void stopLoadingMore() {
        mLayoutLoadMore.setVisibility(View.GONE);
        mAviLoadMore.smoothToHide();
    }
    /**
     * 开启加载更多动画
     */
    public void startLoadingMore() {
        mLayoutLoadMore.setVisibility(View.VISIBLE);
        mAviLoadMore.smoothToShow();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 退出的时候不在回调更新界面
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
    private void initData() {
        String version = SystemUtils.getVersion(getActivity());
        String userId = Config.getCachedUserId(getActivity());
        ApiRetrofit.getInstance().getApiService().getIncomeRecord(version,userId,1,9)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CoinListResponse>(){

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CoinListResponse coinListResponse) {
                        if ("0".equals(coinListResponse.getSTATUS())){
                            adapter.setData(coinListResponse.getOBJECT());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

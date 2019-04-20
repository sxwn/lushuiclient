package com.lushuitv.yewuds.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.blankj.utilcode.util.ToastUtils;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.activity.LoginTranslucentActivtiy;
import com.lushuitv.yewuds.activity.ModelPageActivity;
import com.lushuitv.yewuds.activity.PayVideoActivity;
import com.lushuitv.yewuds.activity.VideoCommentActivity;
import com.lushuitv.yewuds.adapter.VideoNewAdapter;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.app.ApiConstant;
import com.lushuitv.yewuds.app.MyApp;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.constant.Constants;
import com.lushuitv.yewuds.module.entity.WorksListBean;
import com.lushuitv.yewuds.module.response.CollectResponse;
import com.lushuitv.yewuds.module.response.HomeAll;
import com.lushuitv.yewuds.share.CommonSharePop;
import com.lushuitv.yewuds.utils.UIUtils;
import com.lushuitv.yewuds.view.EmptyRecyclerView;
import com.wang.avi.AVLoadingIndicatorView;
import java.util.ArrayList;
import java.util.List;
import cn.jzvd.JZVideoPlayer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by weip on 2017/8/25.
 */

public class VideoListFragment extends Fragment {
    public View mView;
    private Context mContex;
    private EmptyRecyclerView mRecyclerView;
    private VideoNewAdapter mSelectdapter;
    private List<WorksListBean> mSelectList;
    private Disposable mDisposable;
    private TextView mTvNoNetwork;
    private View mEmptyView;
    private SwipeRefreshLayout mRefreshLayout;
    private AVLoadingIndicatorView mAvi;
    private AVLoadingIndicatorView mAviLoadMore;
    private int mPage = 1;
    private LinearLayout mLayoutLoadMore;
    private boolean mIsLoadMore = true;//是否可以加载更多

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContex = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_video_selected, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContex, LinearLayoutManager.VERTICAL, false));
        mSelectList = new ArrayList<>();
        mSelectdapter = new VideoNewAdapter(mContex, mSelectList, false);
        mRecyclerView.setAdapter(mSelectdapter);
        mRecyclerView.setmEmptyView(mEmptyView);
        mRecyclerView.hideEmptyView();
        showLoading();
        getDataFromServer(Constants.GET_DATA_TYPE_NORMAL);
        initListener();
    }
    CommonSharePop mSharePop = null;
    private void initListener() {
        mSelectdapter.setVideoClickInterface(new VideoNewAdapter.VideoClickInterface() {
            @Override
            public void onVideoItemClick(View v, int position, ImageView view) {
                Intent intent = null;
                switch (v.getId()) {
                    case R.id.video_pay_commit://付费
                        if (Config.getCachedUserId(getActivity()) == null) {
                            intent = new Intent(getActivity(), LoginTranslucentActivtiy.class);
                            startActivity(intent);
                        } else {
                            intent = new Intent(getActivity(), PayVideoActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("workId", mSelectList.get(position).getWorksId());
                            intent.putExtra("id_bundle", bundle);
                            startActivityForResult(intent, 101);
                        }
                        break;
                    case R.id.item_video_list_top_userhead://个人中心
                        if (Config.getCachedDataType(getActivity()) == 2) {
                            Intent in = new Intent(getActivity(), ModelPageActivity.class);
                            in.putExtra("actor_id", mSelectList.get(position).getWorksActor());
                            startActivity(in);
                        }
                        break;
                    case R.id.item_video_list_comment_rl://评论
                        Intent commentIntent = new Intent(getActivity(), VideoCommentActivity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable(HomeNewFragment.SER_KEY, mSelectList.get(position));
                        commentIntent.putExtras(mBundle);
//                        commentIntent.putExtra("workId", mVideosList.get(position).getWorksId());
//                        commentIntent.putExtra("workCover", mVideosList.get(position).getWorksCover());
//                        commentIntent.putExtra("workIsPraise", mVideosList.get(position).getIsPraising());
//                        commentIntent.putExtra("workActorName", mVideosList.get(position).getActorName());
//                        commentIntent.putExtra("workActorHeadshot", mVideosList.get(position).getActorHeadshot());
//                        commentIntent.putExtra("workActor", mVideosList.get(position).getWorksActor());
                        startActivity(commentIntent);
                        break;
                    case R.id.item_video_item_hot_rl://热度
                        if (Config.getCachedUserId(getActivity()) == null) {
                            intent = new Intent(getActivity(), LoginTranslucentActivtiy.class);
                            startActivity(intent);
                            return;
                        }
                        v.setFocusable(false);
                        if (mSelectList.get(position).getIsPraising() == 0) {
                            //未收藏
                            ApiRetrofit.getInstance().getApiService().getIsPraising(mSelectList.get(position).getWorksId(), 0)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<CollectResponse>() {

                                        @Override
                                        public void onError(Throwable e) {
                                            v.setFocusable(true);
                                        }

                                        @Override
                                        public void onComplete() {

                                        }

                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(CollectResponse collectResponse) {
                                            v.setFocusable(true);
                                            UIUtils.showToast(collectResponse.getMSG());
                                            if ("0".equals(collectResponse.getSTATUS())) {
                                                //icon改变
                                                mSelectList.get(position).setIsPraising(1);
                                                view.setBackgroundResource(R.mipmap.video_hot_yes);
                                            }
                                        }
                                    });
                        } else {
                            ApiRetrofit.getInstance().getApiService().getIsPraising(mSelectList.get(position).getWorksId(), 1)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<CollectResponse>() {

                                        @Override
                                        public void onError(Throwable e) {
                                            v.setFocusable(true);
                                        }

                                        @Override
                                        public void onComplete() {

                                        }

                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(CollectResponse collectResponse) {
                                            v.setFocusable(true);
                                            UIUtils.showToast(collectResponse.getMSG());
                                            if ("0".equals(collectResponse.getSTATUS())) {
                                                mSelectList.get(position).setIsPraising(0);
                                                view.setBackgroundResource(R.mipmap.video_hot_no);
                                            }
                                        }
                                    });
                        }
                        break;
                    case R.id.item_video_list_collect_rl://收藏
                        if (Config.getCachedUserId(getActivity()) == null) {
                            intent = new Intent(getActivity(), LoginTranslucentActivtiy.class);
                            startActivity(intent);
                            return;
                        }
                        v.setFocusable(false);
                        if (mSelectList.get(position).getIsColletion() == 0) {
                            //未收藏
                            ApiRetrofit.getInstance().getApiService().getCollect(mSelectList.get(position).getWorksId(), 0)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<CollectResponse>() {

                                        @Override
                                        public void onError(Throwable e) {
                                            v.setFocusable(true);
                                        }

                                        @Override
                                        public void onComplete() {

                                        }

                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(CollectResponse collectResponse) {
                                            v.setFocusable(true);
                                            UIUtils.showToast("已收藏");
                                            if ("0".equals(collectResponse.getSTATUS())) {
                                                //icon改变
                                                mSelectList.get(position).setIsColletion(1);
                                                view.setBackgroundResource(R.mipmap.video_collect_yes);
                                            }
                                        }
                                    });
                        } else {
                            ApiRetrofit.getInstance().getApiService().getCollect(mSelectList.get(position).getWorksId(), 1)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<CollectResponse>() {

                                        @Override
                                        public void onError(Throwable e) {
                                            v.setFocusable(true);
                                        }

                                        @Override
                                        public void onComplete() {

                                        }

                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(CollectResponse collectResponse) {
                                            v.setFocusable(true);
                                            UIUtils.showToast("真的不喜欢我了吗？");
                                            mSelectList.get(position).setIsColletion(0);
                                            view.setBackgroundResource(R.mipmap.video_collect_no);
                                        }
                                    });

                        }
                        break;
                    case R.id.item_video_list_share_rl://分享
                        if (Config.getCachedUserId(getActivity()) == null) {
                            intent = new Intent(getActivity(), LoginTranslucentActivtiy.class);
                            startActivity(intent);
                            return;
                        }
                        if (mSharePop == null) {
                            mSharePop = new CommonSharePop(getActivity());
                        }
                        mSharePop.show(getActivity().getWindow().getDecorView());
                        WorksListBean worksListBean = mSelectList.get(position);
                        mSharePop.setShare(worksListBean.getWorksName(),
                                "藏不住的妩媚性感，每一个画面尽是无限诱惑~~",
                                ApiConstant.BASE_SERVER_H5_URL + "h5/share_tv.html?worksId="
                                        + worksListBean.getWorksId() + "&hcode=" +
                                        Config.getCachedUserCode(getActivity()) + "&t=" + System.currentTimeMillis(), R.mipmap.logo_48);
                        break;
                }
            }
        });
    }

    private void initView(View view) {
        mRecyclerView = (EmptyRecyclerView) view.findViewById(R.id.select_fragment_recyclerview);
        mEmptyView = view.findViewById(R.id.empty_view);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshlayout);
        mAvi = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
        mAviLoadMore = (AVLoadingIndicatorView) view.findViewById(R.id.avi_loadmore);
        mLayoutLoadMore = (LinearLayout) view.findViewById(R.id.layout_loadmore);
        //设置下拉刷新样式
        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

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
        mRecyclerView.addOnScrollListener(new RecyclerViewScrollListener());
    }

    public void getDataFromServer(final int type) {
        if (Config.getCachedDataType(MyApp.getContext()) == 1) {
            ApiRetrofit.getInstance().getApiService().getVerAllList(mPage, Constants.PAGE_SIZE)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<HomeAll>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            mDisposable = d;
                        }

                        @Override
                        public void onNext(HomeAll homeAll) {
                            //更新界面数据

                            if (Constants.GET_DATA_TYPE_NORMAL == type) {
                                //正常模式下，清空之前数据，重新加载
                                mSelectList.clear();
                                mSelectList = homeAll.getOBJECT();
                            } else {
                                //加载更多模式
                                mSelectList.addAll(homeAll.getOBJECT());
                            }

                            //如果获取的数据不足一页，代表当前已经没有更过数据，关闭加载更多
                            if (homeAll.getOBJECT().size() < Constants.PAGE_SIZE) {
                                mIsLoadMore = false;
                            }

                            mSelectdapter.setData(mSelectList);
                            mSelectdapter.notifyDataSetChanged();
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
        } else {
            ApiRetrofit.getInstance().getApiService().getAllList(mPage, Constants.PAGE_SIZE)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<HomeAll>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            mDisposable = d;
                        }

                        @Override
                        public void onNext(HomeAll homeAll) {
                            //更新界面数据
                            if (Constants.GET_DATA_TYPE_NORMAL == type) {
                                //正常模式下，清空之前数据，重新加载
                                mSelectList.clear();
                                mSelectList = homeAll.getOBJECT();
                            } else {
                                //加载更多模式
                                mSelectList.addAll(homeAll.getOBJECT());
                            }
                            //如果获取的数据不足一页，代表当前已经没有更过数据，关闭加载更多
                            if (homeAll.getOBJECT().size() < Constants.PAGE_SIZE) {
                                mIsLoadMore = false;
                            }
                            mSelectdapter.setData(mSelectList);
                            mSelectdapter.notifyDataSetChanged();
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
    }

    /**
     * 开启加载中动画
     */
    public void showLoading() {
        mAvi.smoothToShow();
    }

    /**
     * 关闭加载中动画
     */

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

    /**
     * RecyclerView 滑动监听器
     */
    class RecyclerViewScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (mSelectList.size() < 1) {
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

//    @Override
//    public void onGetVideosListSuccess(List<WorksListBean> videoList) {
//        Log.i("info", "请求到的数据集合:" + videoList.size());
//        if (pageIndex == 1)
//            mRefreshLayout.endRefreshing();// 结束下拉刷新
//        if (pageIndex > 1)
//            mRefreshLayout.endLoadingMore();//结束加载更多
//        if (videoList.size() == 0) {
//            UIUtils.showToast("撸手机的习惯可不好哟~");
//            return;
//        }
//        //如果获取数据为空
//        if (ListUtils.isEmpty(mVideosList)) {
//            if (ListUtils.isEmpty(videoList)) {
//                //获取不到数据,显示空布局
//                UIUtils.showToast(UIUtils.getString(R.string.no_url));
//                mStateView.showEmpty();
//                return;
//            }
//            mStateView.showContent();//显示内容
//        }
//        mStateView.setVisibility(View.GONE);
//        mVideosList.addAll(videoList);
//        mVideoAdapter.notifyDataSetChanged();
//        mTipView.show("叮~别心急，美女来啦~");
//    }
//
//    @Override
//    public void onError() {
//        mTipView.show("数据请求错误");//弹出提示
//        mStateView.setVisibility(View.GONE);
//        //mStateView.showEmpty();
//        mRefreshLayout.endRefreshing();// 结束下拉刷新
//        mRefreshLayout.endLoadingMore();//结束加载更多
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;
        if (requestCode == 101) {
            boolean isBuy = data.getBooleanExtra("isBuy", false);
            if (isBuy)
                mSelectdapter.setResult();
        } else if (requestCode == 102) {

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

}

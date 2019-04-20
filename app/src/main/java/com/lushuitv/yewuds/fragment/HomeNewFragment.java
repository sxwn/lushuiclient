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
import android.widget.ImageView;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.activity.LoginTranslucentActivtiy;
import com.lushuitv.yewuds.activity.ModelPageActivity;
import com.lushuitv.yewuds.activity.PayVideoActivity;
import com.lushuitv.yewuds.activity.VideoCommentActivity;
import com.lushuitv.yewuds.adapter.VideoNewAdapter;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.app.ApiConstant;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.actor.ActorDetailActivity;
import com.lushuitv.yewuds.module.entity.WorksListBean;
import com.lushuitv.yewuds.module.response.CollectResponse;
import com.lushuitv.yewuds.module.response.NewVideoResponse;
import com.lushuitv.yewuds.share.CommonSharePop;
import com.lushuitv.yewuds.utils.UIUtils;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerManager;
import cn.jzvd.JZVideoPlayerStandard;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description 首页最新
 * Created by weip
 * Date on 2017/11/1.
 */

public class HomeNewFragment extends Fragment {
    private SwipeRefreshLayout mSwipeRefreshView;
    private RecyclerView mRvVideos;
    private VideoNewAdapter mVideoAdapter;
    private List<WorksListBean> mVideosList = new ArrayList<WorksListBean>();//本地视频集合
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_layout, container, false);
        initView(view);
        initData();
        initListener();
        return view;
    }

    public void initView(View rootView) {
        mSwipeRefreshView = (SwipeRefreshLayout) rootView.findViewById(R.id.home_new_refreshview);
        mRvVideos = (RecyclerView) rootView.findViewById(R.id.home_new_recycleview);
        mRvVideos.setLayoutManager(new LinearLayoutManager(getActivity()));
        mVideoAdapter = new VideoNewAdapter(getActivity(), mVideosList, true);
        mRvVideos.setAdapter(mVideoAdapter);
        mSwipeRefreshView.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        //给swipeRefreshLayout绑定刷新监听
        mSwipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //设置2秒的时间来执行以下事件
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        JZVideoPlayer.releaseAllVideos();
                        mSwipeRefreshView.setRefreshing(false);
                        initData();
                    }
                }, 1000);
            }
        });
    }

    public void initData() {
        if (Config.getCachedDataType(getActivity()) == 1) {
            //测试数据
            ApiRetrofit.getInstance().getApiService().getVerNewVideoList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<NewVideoResponse>() {


                        @Override
                        public void onError(Throwable e) {
                            UIUtils.showToast("网络加载失败");
                        }

                        @Override
                        public void onComplete() {

                        }

                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(NewVideoResponse newVideoResponse) {
                            mVideoAdapter.setData(newVideoResponse.getLIST());
                        }
                    });
        } else if (Config.getCachedDataType(getActivity()) == 2) {
            //正式数据
            ApiRetrofit.getInstance().getApiService().getNewVideoList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<NewVideoResponse>() {

                        @Override
                        public void onError(Throwable e) {
                            UIUtils.showToast("网络加载失败");
                        }

                        @Override
                        public void onComplete() {

                        }

                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(NewVideoResponse newVideoResponse) {
                            for (int i = 0; i < newVideoResponse.getLIST().size(); i++) {
                                KLog.e(newVideoResponse.getLIST().get(i).getWorksCommentNum() + "评论数");
                            }
                            mVideoAdapter.setData(newVideoResponse.getLIST());
                        }
                    });
        }
    }

    public final static String SER_KEY = "com.lushuitv.yewuds.ser";

    CommonSharePop mSharePop = null;

    public void initListener() {
        //滚动监听
        mRvVideos.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (JZVideoPlayerManager.getCurrentJzvd() != null) {
                    JZVideoPlayerStandard videoPlayer = (JZVideoPlayerStandard) JZVideoPlayerManager.getCurrentJzvd();
                    if (videoPlayer.currentState == JZVideoPlayerStandard.CURRENT_STATE_PLAYING) {
                        //如果正在播放
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRvVideos.getLayoutManager();
                        int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                        int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                        if (firstVisibleItemPosition > videoPlayer.getPosition() || lastVisibleItemPosition < videoPlayer.getPosition()) {
                            //如果第一个可见的条目位置大于当前播放videoPlayer的位置
                            //或最后一个可见的条目位置小于当前播放videoPlayer的位置，释放资源
                            JZVideoPlayer.releaseAllVideos();
                        }
                    }
                }
            }
        });
        mVideoAdapter.setVideoClickInterface(new VideoNewAdapter.VideoClickInterface() {
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
                            bundle.putInt("workId", mVideosList.get(position).getWorksId());
                            intent.putExtra("id_bundle", bundle);
                            startActivityForResult(intent, 101);
                        }
                        break;
                    case R.id.item_video_list_top_userhead://个人中心
                        if (Config.getCachedDataType(getActivity()) == 2) {
//                            Intent in = new Intent(getActivity(), ModelPageActivity.class);
                            Intent in = new Intent(getActivity(), ActorDetailActivity.class);
                            in.putExtra("actor_id", mVideosList.get(position).getWorksActor());
                            in.putExtra("actor_name",mVideosList.get(position).getActorName());
                            in.putExtra("actor_headview",mVideosList.get(position).getActorHeadshot());
                            startActivity(in);
                        }
                        break;
                    case R.id.item_video_list_comment_rl://评论
                        Intent commentIntent = new Intent(getActivity(), VideoCommentActivity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable(SER_KEY, mVideosList.get(position));
                        commentIntent.putExtras(mBundle);
                        startActivity(commentIntent);
                        break;
                    case R.id.item_video_item_hot_rl://热度
                        if (Config.getCachedUserId(getActivity()) == null) {
                            intent = new Intent(getActivity(), LoginTranslucentActivtiy.class);
                            startActivity(intent);
                            return;
                        }
                        v.setFocusable(false);
                        if (mVideosList.get(position).getIsPraising() == 0) {
                            //未收藏
                            ApiRetrofit.getInstance().getApiService().getIsPraising(mVideosList.get(position).getWorksId(), 0)
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
                                                mVideosList.get(position).setIsPraising(1);
                                                view.setBackgroundResource(R.mipmap.video_hot_yes);
                                            }
                                        }
                                    });
                        } else {
                            ApiRetrofit.getInstance().getApiService().getIsPraising(mVideosList.get(position).getWorksId(), 1)
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
                                                mVideosList.get(position).setIsPraising(0);
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
                        if (mVideosList.get(position).getIsColletion() == 0) {
                            //未收藏
                            ApiRetrofit.getInstance().getApiService().getCollect(mVideosList.get(position).getWorksId(), 0)
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
                                                mVideosList.get(position).setIsColletion(1);
                                                view.setBackgroundResource(R.mipmap.video_collect_yes);
                                            }
                                        }
                                    });
                        } else {
                            ApiRetrofit.getInstance().getApiService().getCollect(mVideosList.get(position).getWorksId(), 1)
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
                                            mVideosList.get(position).setIsColletion(0);
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
                        WorksListBean worksListBean = mVideosList.get(position);
                        KLog.e(ApiConstant.BASE_SERVER_H5_URL + "h5/share_tv.html?worksId="
                                + worksListBean.getWorksId() + "&hcode=" +
                                Config.getCachedUserCode(getActivity()) + "&t=" + System.currentTimeMillis());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;
        if (requestCode == 101) {
            boolean isBuy = data.getBooleanExtra("isBuy", false);
            if (isBuy)
                mVideoAdapter.setResult();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}

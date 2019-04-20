package com.lushuitv.yewuds.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.activity.LoginTranslucentActivtiy;
import com.lushuitv.yewuds.activity.PayVideoActivity;
import com.lushuitv.yewuds.adapter.CollectCommonAdapter;
import com.lushuitv.yewuds.adapter.VideoNewAdapter;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.module.entity.HttpBean;
import com.lushuitv.yewuds.module.entity.LISTBean;
import com.lushuitv.yewuds.module.entity.WorksListBean;
import com.lushuitv.yewuds.module.response.CollectCommonResponse;
import com.lushuitv.yewuds.utils.CustomPopupWindow;
import com.lushuitv.yewuds.utils.UIUtils;
import com.lushuitv.yewuds.view.MyVideoPlayerStandard;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerManager;
import cn.jzvd.JZVideoPlayerStandard;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * 收藏视频
 * Created by weip
 * Date on 2017/8/29.
 */

public class CollectVideoFragment extends Fragment implements View.OnClickListener {

    XRecyclerView videoRecycleView;

    private VideoNewAdapter adapter;

    private List<WorksListBean> data;

    private List<WorksListBean> selectList;

    private int currentPage = 1;

    public static boolean isShowBox; // 是否显示CheckBox标识

    RelativeLayout commitDelete, cacelDelete;
    private CustomPopupWindow mPop;

    public CollectVideoFragment() {
        setUserVisibleHint(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        KLog.e("CollectVideoFragment onCreateView onCreateView onCreateView");
        View view = inflater.inflate(R.layout.collect_fragment_video, null);
        initView(view);
        initData();
        return view;
    }

    public void initView(View rootView) {
        videoRecycleView = (XRecyclerView) rootView.findViewById(R.id.fragment_collect_list_video);
        data = new ArrayList<WorksListBean>();
        selectList = new ArrayList<WorksListBean>();
        adapter = new VideoNewAdapter(getActivity(), data, false);
        videoRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        videoRecycleView.setAdapter(adapter);
        videoRecycleView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (JZVideoPlayerManager.getCurrentJzvd() != null) {
                    MyVideoPlayerStandard videoPlayer = (MyVideoPlayerStandard) JZVideoPlayerManager.getCurrentJzvd();
                    if (videoPlayer.currentState == JZVideoPlayerStandard.CURRENT_STATE_PLAYING) {
                        //如果正在播放
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) videoRecycleView.getLayoutManager();
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
        videoRecycleView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                initData();
            }

            @Override
            public void onLoadMore() {
                currentPage++;
                initData();
            }
        });

        adapter.setOnShowItemClickListener(new VideoNewAdapter.OnShowItemClickListener() {
            @Override
            public void onShowItemClick(WorksListBean bean) {
                if (bean.isChecked() && !selectList.contains(bean)) {
                    selectList.add(bean);
                } else if (!bean.isChecked() && selectList.contains(bean)) {
                    selectList.remove(bean);
                }
                KLog.e("video setOnShowItemClickListener" + selectList.size());
            }
        });

        adapter.setListener(new VideoNewAdapter.VideoItemClickListener() {
            @Override
            public void itemClickListener(View v, int position) {
                Intent intent = null;
                if (Config.getCachedUserId(getActivity()) == null) {
                    intent = new Intent(getActivity(), LoginTranslucentActivtiy.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), PayVideoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("workId", data.get(position).getWorksId());
                    bundle.putInt("workprice", data.get(position).getWorksPrice());
                    intent.putExtra("id_bundle", bundle);
                    startActivityForResult(intent, 101);
                }
            }
        });
        //底部删除view
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_delete_layout, null);
        commitDelete = (RelativeLayout) view.findViewById(R.id.bottom_delete_layout_commit);
        commitDelete.setOnClickListener(this);
        cacelDelete = (RelativeLayout) view.findViewById(R.id.bottom_delete_layout_cancel);
        cacelDelete.setOnClickListener(this);
        mPop = new CustomPopupWindow(getActivity(), view);
        mPop.setFocusable(false);
        mPop.setOutsideTouchable(false);//关闭popupWindow的点击外部事件
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;
        if (requestCode == 101) {
            boolean isBuy = data.getBooleanExtra("isBuy", false);
            if (isBuy)
                adapter.setResult();
        }
    }

    /**
     * 删除视频
     */
    public void setDeleteImage() {
        for (WorksListBean bean : data) {
            if (!bean.isShow()) {
                bean.setShow(true);//是否显示checkbox
            }
        }
        adapter.notifyDataSetChanged();
        mPop.showAtLocation(getActivity().findViewById(R.id.fragment_collect_vp), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * fragment在viewpager中嵌套
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mPop != null)
            mPop.dismiss();
        if (isVisibleToUser) {
            //相当于Fragment的onResume
            initData();
            KLog.e("CollectVideoFragment onResume");
        } else {
            //相当于Fragment的onPause
            JZVideoPlayer.releaseAllVideos();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    protected void initData() {
        ApiRetrofit.getInstance().getApiService().getCollectList(1, currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CollectCommonResponse>() {

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
                    public void onNext(CollectCommonResponse collectCommonResponse) {
                        if ("0".equals(collectCommonResponse.getSTATUS())) {
                            if (currentPage == 1) {
                                if (data.size() > 0)
                                    data.clear();
                                videoRecycleView.refreshComplete();
                            } else {
                                if (collectCommonResponse.getLIST().size() == 0) {
                                    UIUtils.showToast("加载完成");
                                    videoRecycleView.loadMoreComplete();
                                    return;
                                }
                                videoRecycleView.loadMoreComplete();
                            }
                            data.addAll(collectCommonResponse.getLIST());
                            adapter.setData(data);
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bottom_delete_layout_commit://确定删除
                if (selectList != null && selectList.size() > 0) {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < selectList.size(); i++) {
                        if (i < selectList.size() - 1) {
                            builder.append(selectList.get(i).getWorksId() + ",");
                        } else {
                            builder.append(selectList.get(i).getWorksId());
                        }
                    }
                    ApiRetrofit.getInstance().getApiService().getCancelCols(builder.toString())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<HttpBean>() {

                                @Override
                                public void onError(Throwable e) {
                                    KLog.e(e.getLocalizedMessage());
                                }

                                @Override
                                public void onComplete() {

                                }

                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(HttpBean httpBean) {
                                    if ("0".equals(httpBean.getSTATUS())) {
                                        if (mPop != null)
                                            mPop.dismiss();
                                        CollectVideoFragment.isShowBox = false;
                                        selectList.clear();
                                        currentPage = 1;
                                        initData();
                                    }
                                }
                            });
                } else {
                    UIUtils.showToast("请选择条目");
                }
                break;
            case R.id.bottom_delete_layout_cancel://取消
                if (mPop != null)
                    mPop.dismiss();
                CollectVideoFragment.isShowBox = false;
                for (WorksListBean bean : data) {
                    bean.setShow(false);//是否显示checkbox
                }
                adapter.notifyDataSetChanged();
                break;
        }
    }
}

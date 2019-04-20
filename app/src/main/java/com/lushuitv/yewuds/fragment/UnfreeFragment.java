package com.lushuitv.yewuds.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.activity.ImageViewPagerActivtiy;
import com.lushuitv.yewuds.adapter.CollectImageAdapter;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.module.entity.LISTBean;
import com.lushuitv.yewuds.module.entity.WorksListBean;
import com.lushuitv.yewuds.module.response.CollectCommonResponse;
import com.lushuitv.yewuds.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description 免费图片
 * Created by weip
 * Date on 2017/9/20.
 */

public class UnfreeFragment extends Fragment {

    XRecyclerView imageRecycleView;

    CollectImageAdapter adapter;

    List<WorksListBean> lists;

    private int pageIndex = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.collect_fragment_image, null);
        initView(view);
        return view;
    }

    public void initView(View rootView) {
        lists = new ArrayList<WorksListBean>();
        imageRecycleView = (XRecyclerView) rootView.findViewById(R.id.fragment_collect_list_image);
        adapter = new CollectImageAdapter(getActivity(), lists);
        imageRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        imageRecycleView.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
        imageRecycleView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        imageRecycleView.setArrowImageView(R.mipmap.refresh_head_arrow);//下拉图标
        imageRecycleView.setAdapter(adapter);
        adapter.setCollectImageInterface(new CollectImageAdapter.ImageClickInterface() {
            @Override
            public void imageClick(View v, int position) {
                Intent in = new Intent(getActivity(), ImageViewPagerActivtiy.class);
                in.putExtra("worksId", lists.get(position - 1).getWorksId());
                in.putExtra("worksIsCollect", lists.get(position - 1).getIsColletion());
                in.putExtra("worksActor", lists.get(position - 1).getWorksActor());
                startActivity(in);
            }
        });
        imageRecycleView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                initData(pageIndex);
            }

            @Override
            public void onLoadMore() {
                pageIndex++;
                initData(pageIndex);
            }
        });
    }

    /**
     * fragment在viewpager中嵌套
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
            initData(pageIndex);
        } else {
            //相当于Fragment的onPause
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    protected void initData(int page) {
        if (Config.getCachedDataType(getActivity()) == 1) {
            ApiRetrofit.getInstance().getApiService().getVerFreeList(pageIndex, 8)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<CollectCommonResponse>() {

                        @Override
                        public void onError(Throwable e) {
                            if (pageIndex == 1) {
                                imageRecycleView.refreshComplete();
                            } else {
                                imageRecycleView.loadMoreComplete();
                            }
                        }

                        @Override
                        public void onComplete() {

                        }

                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(CollectCommonResponse response) {
                            if (response.getSTATUS().equals("0")) {
                                if (pageIndex == 1) {
                                    imageRecycleView.refreshComplete();
                                    lists.clear();
                                } else {
                                    imageRecycleView.loadMoreComplete();
                                }
                                List<WorksListBean> list = response.getLIST();
                                if (list.size() == 0) {
                                    if (pageIndex > 1)
                                        UIUtils.showToast("撸手机的习惯可不好哟~");
                                    return;
                                }
                                lists.addAll(list);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
        } else if (Config.getCachedDataType(getActivity()) == 2) {
            ApiRetrofit.getInstance().getApiService().getFreeList(pageIndex, 8)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<CollectCommonResponse>() {

                        @Override
                        public void onError(Throwable e) {
                            if (pageIndex == 1) {
                                imageRecycleView.refreshComplete();
                            } else {
                                imageRecycleView.loadMoreComplete();
                            }
                        }

                        @Override
                        public void onComplete() {

                        }

                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(CollectCommonResponse response) {
                            if (response.getSTATUS().equals("0")) {
                                if (pageIndex == 1) {
                                    imageRecycleView.refreshComplete();
                                    lists.clear();
                                } else {
                                    imageRecycleView.loadMoreComplete();
                                }
                                List<WorksListBean> list = response.getLIST();
                                if (list.size() == 0) {
                                    if (pageIndex > 1)
                                        UIUtils.showToast("撸手机的习惯可不好哟~");
                                    return;
                                }
                                lists.addAll(list);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
        }

    }
}

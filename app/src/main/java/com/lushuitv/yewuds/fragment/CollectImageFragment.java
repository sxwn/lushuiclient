package com.lushuitv.yewuds.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.activity.ImageViewPagerActivtiy;
import com.lushuitv.yewuds.adapter.CollectImageAdapter;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.module.entity.HttpBean;
import com.lushuitv.yewuds.module.entity.WorksListBean;
import com.lushuitv.yewuds.module.response.CollectCommonResponse;
import com.lushuitv.yewuds.utils.CustomPopupWindow;
import com.lushuitv.yewuds.utils.UIUtils;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 收藏图片
 * Created by weip
 * Date on 2017/8/29.
 */

public class CollectImageFragment extends Fragment implements View.OnClickListener {

    private XRecyclerView imageRecycleView;

    private CollectImageAdapter adapter;

    public CollectImageAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(CollectImageAdapter adapter) {
        this.adapter = adapter;
    }

    private List<WorksListBean> lists;


    private List<WorksListBean> selectList;

    public List<WorksListBean> getLists() {
        return lists;
    }

    public void setLists(List<WorksListBean> lists) {
        this.lists = lists;
    }

    private int pageIndex = 1;

    public static boolean isShowBox; // 是否显示CheckBox标识

    private CustomPopupWindow mPop;

    public CollectImageFragment() {
        setUserVisibleHint(false);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.collect_fragment_image, null);
        initView(view);
        initData();
        return view;
    }

    RelativeLayout commitDelete, cacelDelete;

    public void initView(View rootView) {
        lists = new ArrayList<WorksListBean>();
        selectList = new ArrayList<WorksListBean>();//选择需要删除的作品集合
        imageRecycleView = (XRecyclerView) rootView.findViewById(R.id.fragment_collect_list_image);
        adapter = new CollectImageAdapter(getActivity(), lists);
        imageRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        imageRecycleView.setAdapter(adapter);
        adapter.setCollectImageInterface(new CollectImageAdapter.ImageClickInterface() {
            @Override
            public void imageClick(View v, int position) {
                if (!isShowBox) {
                    Intent in = new Intent(getActivity(), ImageViewPagerActivtiy.class);
                    in.putExtra("worksId", lists.get(position - 1).getWorksId());
                    in.putExtra("worksIsCollect", lists.get(position - 1).getIsColletion());
                    in.putExtra("worksActor",lists.get(position-1).getWorksActor());
                    startActivity(in);
                } else {
                    WorksListBean listBean = lists.get(position - 1);
                    boolean isChecked = listBean.isChecked();
                    if (isChecked) {
                        listBean.setChecked(false);
                    } else {
                        listBean.setChecked(true);
                    }
                    adapter.getOnShowItemClickListener().onShowItemClick(listBean);
                    adapter.notifyItemChanged(position);
                }
            }
        });
        adapter.setOnShowItemClickListener(new CollectImageAdapter.OnShowItemClickListener() {
            @Override
            public void onShowItemClick(WorksListBean bean) {
                if (bean.isChecked() && !selectList.contains(bean)) {
                    selectList.add(bean);
                } else if (!bean.isChecked() && selectList.contains(bean)) {
                    selectList.remove(bean);
                }
                KLog.e("setOnShowItemClickListener" + selectList.size());
            }
        });
        imageRecycleView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                initData();
            }

            @Override
            public void onLoadMore() {
                pageIndex++;
                initData();
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

    /**
     * 删除图片
     */
    public void setDeleteImage() {
        for (WorksListBean bean : lists) {
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
        } else {
            //相当于Fragment的onPause
        }
    }

    protected void initData() {
        ApiRetrofit.getInstance().getApiService().getCollectList(2, pageIndex)
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
                    public void onNext(CollectCommonResponse response) {
                        if (response.getSTATUS().equals("0")) {
                            List<WorksListBean> list = response.getLIST();
                            if (pageIndex == 1) {
                                if (lists.size() > 0)
                                    lists.clear();
                                imageRecycleView.refreshComplete();
                            } else {
                                imageRecycleView.loadMoreComplete();
                            }
                            adapter.setData(list);
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bottom_delete_layout_commit://确定删除
                if (selectList != null && selectList.size() > 0) {
                    StringBuilder builder = new StringBuilder() ;
                    for (int i = 0;i<selectList.size();i++){
                        if (i<selectList.size()-1){
                            builder.append(selectList.get(i).getWorksId()+",");
                        }else{
                            builder.append(selectList.get(i).getWorksId());
                        }
                    }
                    ApiRetrofit.getInstance().getApiService().getCancelCols(builder.toString())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<HttpBean>(){

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
                                    if ("0".equals(httpBean.getSTATUS())){
                                        if (mPop != null)
                                            mPop.dismiss();
                                        CollectImageFragment.isShowBox = false;
                                        selectList.clear();
                                        pageIndex=1;
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
                CollectImageFragment.isShowBox = false;
                for (WorksListBean bean : lists) {
                    bean.setShow(false);//是否显示checkbox
                }
                adapter.notifyDataSetChanged();
                break;
        }
    }
}

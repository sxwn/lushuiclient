package com.lushuitv.yewuds.actor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.module.entity.WorksListBean;
import com.lushuitv.yewuds.module.response.ActorWorkInfo;
import com.lushuitv.yewuds.utils.SystemUtils;
import com.lushuitv.yewuds.utils.UIUtils;
import com.lushuitv.yewuds.view.DividerItemDecoration;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 艺人页面fragment
 * Created by weip on 2017\12\22 0022.
 */

public class ActorPageFragment extends Fragment implements View.OnClickListener {
    RecyclerView mRecyclerView;
    private int mPage;
    private int actorId;
    public static final String MERCHANT_DETAILS_PAGE = "MERCHANT_DETAILS_PAGE";
    public static final String ACTOR_ID = "ACTOR_ID";
    private List<WorksListBean> workInfos;
    ActorDetailItemAdapter adapter;
    private TextView actorPublish;

    public static ActorPageFragment newInstance(int page,int actorId) {
        Bundle args = new Bundle();
        args.putInt(MERCHANT_DETAILS_PAGE, page);
        args.putInt(ACTOR_ID, actorId);
        ActorPageFragment actorPageFragment = new ActorPageFragment();
        actorPageFragment.setArguments(args);
        return actorPageFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.mRecyclerView);
        actorPublish = (TextView) view.findViewById(R.id.actor_detail_publish);
        actorPublish.setOnClickListener(this);
        workInfos = new ArrayList<WorksListBean>();
        initAdapter(workInfos);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPage = getArguments().getInt(MERCHANT_DETAILS_PAGE);
        actorId = getArguments().getInt(ACTOR_ID);
        setData();
    }
    private void setData(){
        UIUtils.showToast("page:"+mPage);
        if (mPage == 1){
            ApiRetrofit.getInstance().getApiService().getActorInfo(actorId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ActorWorkInfo>() {

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
                        public void onNext(ActorWorkInfo actorWorkInfo) {
//                        ImageManager.getInstance().loadImage(ActorDetailActivity.this,
//                                actorWorkInfo.getOBJECT().getActorImg()+ "?imageView2/0/w/750/h/350", headBg);
//                            ImageManager.getInstance().loadRoundImage(ActorDetailActivity.this,
//                                    actorWorkInfo.getOBJECT().getActorHeadshot(), headUserImg);
//                            headUserName.setText(actorWorkInfo.getOBJECT().getActorName());
//                            fansNumber = actorWorkInfo.getOBJECT().getActorFans();
//                            headUserFans.setText(""+fansNumber+"个粉丝");
                            workInfos.addAll(actorWorkInfo.getLIST());
                            workInfos.addAll(actorWorkInfo.getLIST());
                            workInfos.addAll(actorWorkInfo.getLIST());
                            adapter.refreshData(actorWorkInfo.getLIST(),actorWorkInfo.getOBJECT());
                        }
                    });
        }else if(mPage==2){
            KLog.e("进来了吗？");
            ApiRetrofit.getInstance().getApiService().getActorSelfInfo(Config.getCachedUserId(getActivity()),
                    SystemUtils.getVersion(getActivity()),actorId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ActorWorkInfo>() {

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
                        public void onNext(ActorWorkInfo actorWorkInfo) {
                            workInfos.addAll(actorWorkInfo.getLIST());
                            workInfos.addAll(actorWorkInfo.getLIST());
                            workInfos.addAll(actorWorkInfo.getLIST());
                            adapter.refreshData(actorWorkInfo.getLIST(),actorWorkInfo.getOBJECT());
                        }
                    });
        }
    }
    /**
     * 设置RecyclerView属性
     */
    private void initAdapter(List<WorksListBean> data) {
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ActorDetailItemAdapter(getActivity(), workInfos,actorId);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.actor_detail_publish:
                startActivity(new Intent(getActivity(),ActorPublishActivity.class).putExtra("actorId",actorId));
                break;
        }
    }
}

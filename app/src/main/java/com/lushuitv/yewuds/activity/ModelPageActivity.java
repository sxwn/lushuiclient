package com.lushuitv.yewuds.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.lushuitv.yewuds.Image.ImageManager;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.adapter.ModelPageAdapter;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.module.entity.WorksListBean;
import com.lushuitv.yewuds.module.response.ActorWorkInfo;
import com.lushuitv.yewuds.module.response.UserAttentionResult;
import com.lushuitv.yewuds.view.PullToRefreshLayout;
import com.lushuitv.yewuds.view.pullableview.PullableListView;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Description 模特个人中心
 * Created by weip
 * Date on 2017/9/2.
 */

public class ModelPageActivity extends BaseActivity implements View.OnClickListener {

    private List<WorksListBean> workInfos;

    private ModelPageAdapter adapter;

    int actorId;

    private PullableListView listView;
    private PullToRefreshLayout ptrl;
    private boolean isFirstIn = true;

    int attentionStatus;

    ImageView headBg;
    ImageView backImage;

    private View mView;

    @Override
    protected View initContentView() {
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);//去除状态栏
        mView = View.inflate(this, R.layout.activity_model_page, null);
        return mView;
    }

    @Override
    public void initToolbar() {
        appBarLayout.setVisibility(View.GONE);
    }


    ImageView userHeadImg;
    TextView userName, userType, userCity, userSanwei, userShengao, userNotice,userFans;

    @Override
    public void initOptions() {
        actorId = getIntent().getIntExtra("actor_id",0);
        workInfos = new ArrayList<WorksListBean>();
        ptrl = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
        ptrl.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                ptrl.refreshFinish(PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                ptrl.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        });
        listView = (PullableListView) findViewById(R.id.content_view);
        View mHeaderView = getLayoutInflater().inflate(R.layout.activity_model_page_header, null);
        headBg = (ImageView) mHeaderView.findViewById(R.id.model_page_head_bg);
        backImage = (ImageView) mHeaderView.findViewById(R.id.model_page_head_back);
        backImage.setOnClickListener(this);
        userHeadImg = (ImageView) mHeaderView.findViewById(R.id.model_page_head_user_img);
        userName = (TextView) mHeaderView.findViewById(R.id.model_pager_header_name);
        userType = (TextView) mHeaderView.findViewById(R.id.model_pager_header_type);
        userCity = (TextView) mHeaderView.findViewById(R.id.model_pager_header_citystr);
        userSanwei = (TextView) mHeaderView.findViewById(R.id.model_pager_header_sanweistr);
        userShengao = (TextView) mHeaderView.findViewById(R.id.model_pager_header_shengaostr);
        userFans = (TextView) mHeaderView.findViewById(R.id.model_pager_header_fans_number);
        userNotice = (TextView) mHeaderView.findViewById(R.id.model_pager_header_notice);
        userNotice.setOnClickListener(this);
        listView.addHeaderView(mHeaderView);
        initListView();
        initData();
    }
    int fansNumber ;


    public void initData() {
        if (actorId == 0)
            return;
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
                        ImageManager.getInstance().loadImage(ModelPageActivity.this, actorWorkInfo.getOBJECT().getActorImg()+ "?imageView2/0/w/750/h/350", headBg);
                        ImageManager.getInstance().loadRoundImage(ModelPageActivity.this, actorWorkInfo.getOBJECT().getActorHeadshot(), userHeadImg);
                        userName.setText(actorWorkInfo.getOBJECT().getActorName());
                        userType.setText(actorWorkInfo.getOBJECT().getActorJob());
                        userCity.setText(actorWorkInfo.getOBJECT().getActorCity());
                        userSanwei.setText(actorWorkInfo.getOBJECT().getActorCwh());
                        userShengao.setText(actorWorkInfo.getOBJECT().getActorHeight());
                        attentionStatus = actorWorkInfo.getOBJECT().getIsFocus();
                        if (attentionStatus == 1) {
                            userNotice.setText("+关注她");
                        } else if (attentionStatus == 2) {
                            userNotice.setText("取消关注");
                        }
                        fansNumber = actorWorkInfo.getOBJECT().getActorFans();
                        userFans.setText(""+fansNumber);
                        adapter.refreshData(actorWorkInfo.getLIST(),actorWorkInfo.getOBJECT());
                    }
                });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // 第一次进入自动刷新
//        if (isFirstIn) {
//            ptrl.autoRefresh();
//            isFirstIn = false;
//        }
    }

    /**
     * ListView初始化方法
     */
    private void initListView() {
        adapter = new ModelPageAdapter(ModelPageActivity.this, workInfos,actorId);
        listView.setAdapter(adapter);
        adapter.setVideoItemClickListener(new ModelPageAdapter.VideoItemClickListener() {
            @Override
            public void onVideoItemCliclk(View v, int position) {
                Intent intent = null;
                if (Config.getCachedUserId(ModelPageActivity.this) == null) {
                    intent = new Intent(ModelPageActivity.this, LoginTranslucentActivtiy.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(ModelPageActivity.this, PayVideoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("workId", workInfos.get(position).getWorksId());
                    bundle.putInt("workprice", workInfos.get(position).getWorksPrice());
                    intent.putExtra("id_bundle", bundle);
                    startActivityForResult(intent, 101);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;
        if (requestCode == 101) {
            boolean isBuy = data.getBooleanExtra("isBuy", false);
            if (isBuy)
                adapter.setResult();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.model_page_head_back:
                finish();
                break;
            case R.id.model_pager_header_notice:
                if (attentionStatus == 1) {
                    attentionCommit();//关注
                } else if (attentionStatus == 2) {
                    attentionCacel();//取消关注
                }
                break;
        }
    }

    private void attentionCacel() {
        ApiRetrofit.getInstance().getApiService().getCancelAttention(actorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserAttentionResult>() {

                    @Override
                    public void onError(Throwable e) {
                        KLog.e("onError" + e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserAttentionResult userAttentionResult) {
                        KLog.e("onNext" + userAttentionResult.getSTATUS());
                        if ("0".equals(userAttentionResult.getSTATUS())) {
                            attentionStatus =1;
                            userNotice.setText("+关注她");
                            fansNumber--;
                            userFans.setText(""+fansNumber);
                        }
                    }
                });
    }

    private void attentionCommit() {
        ApiRetrofit.getInstance().getApiService().getCommitAttention(actorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserAttentionResult>() {

                    @Override
                    public void onError(Throwable e) {
                        KLog.e("onError" + e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserAttentionResult userAttentionResult) {
                        KLog.e("onNext" + userAttentionResult.getSTATUS());
                        if ("0".equals(userAttentionResult.getSTATUS())) {
                            attentionStatus = 2;
                            fansNumber++;
                            userNotice.setText("取消关注");
                            userFans.setText(""+fansNumber);
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}

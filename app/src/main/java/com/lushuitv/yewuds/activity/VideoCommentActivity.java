package com.lushuitv.yewuds.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lushuitv.yewuds.Image.ImageManager;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.adapter.VideoCommentAdapter;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.flyn.Eyes;
import com.lushuitv.yewuds.fragment.HomeNewFragment;
import com.lushuitv.yewuds.module.entity.WorksListBean;
import com.lushuitv.yewuds.module.response.CollectResponse;
import com.lushuitv.yewuds.module.response.ComResponse;
import com.lushuitv.yewuds.module.response.CommentResponse;
import com.lushuitv.yewuds.module.response.UserAttentionResult;
import com.lushuitv.yewuds.module.response.UserInfoResponse;
import com.lushuitv.yewuds.module.response.VideoUrlInfo;
import com.lushuitv.yewuds.utils.CustomPopupWindow;
import com.lushuitv.yewuds.utils.KeyBoardUtils;
import com.lushuitv.yewuds.utils.UIUtils;
import com.lushuitv.yewuds.utils.VideoUrlDecoder;
import com.lushuitv.yewuds.view.MyVideoPlayerStandard;
import com.lushuitv.yewuds.vip.VipCenterActivity;
import com.lushuitv.yewuds.vip.VipPayActivity;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZMediaManager;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.OnVideoClickListener;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static cn.jzvd.JZVideoPlayer.backPress;

/**
 * 视频评论详情
 * Created by weip
 * Date on 2017/8/30.
 */

public class VideoCommentActivity extends BaseActivity implements View.OnClickListener {
    XRecyclerView recyclerView = null;
    TextView entryTv;
    VideoCommentAdapter adapter;
    MyVideoPlayerStandard player = null;
    private List<CommentResponse.OBJECTBean.CommentListBean> lists;
    EditText commentEt;
    TextView commentCommit;
    int pageNum = 1;
    String var;
    String var_advance;
    String contentUrl;
    //intent传值
    int worksId;
    int workIsPraise, workPraiseNumber;//作品被点赞,热度值
    //top
    ImageView topActorImage;
    TextView topActorName, topActorFocus, topHot, topComment;

    private CustomPopupWindow mPop;
    private View mView;

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.activity_video_comment_detail, null);
        return mView;
    }

    @Override
    public void initToolbar() {
        appBarLayout.setVisibility(GONE);
    }

    WorksListBean worksListBean;

    @Override
    public void initOptions() {
        worksListBean = (WorksListBean) getIntent().getSerializableExtra(HomeNewFragment.SER_KEY);
        worksId = worksListBean.getWorksId();
        workIsPraise = worksListBean.getIsPraising();
        workPraiseNumber = worksListBean.getWorksPraising();
        lists = new ArrayList<CommentResponse.OBJECTBean.CommentListBean>();
        initTopVideoplayer();
        initTopActor();
        initCommentData();
        initBottom();
        initPopwidow();
    }

    private void initCommentData() {
        recyclerView = (XRecyclerView) findViewById(R.id.video_comment_detail_recycleview);
        entryTv = (TextView) findViewById(R.id.video_comment_detail_entry);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        recyclerView.setArrowImageView(R.mipmap.refresh_head_arrow);//下拉图标
        adapter = new VideoCommentAdapter(this, lists);
        recyclerView.setAdapter(adapter);
        loadData(pageNum);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                loadData(pageNum);
            }

            @Override
            public void onLoadMore() {
                pageNum++;
                loadData(pageNum);
            }
        });
    }

    private void initTopVideoplayer() {
        player = (MyVideoPlayerStandard) findViewById(R.id.video_player);
        player.setAllControlsVisible(VISIBLE, GONE, VISIBLE, GONE, VISIBLE, VISIBLE, GONE);
        player.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        player.tinyBackImageView.setVisibility(GONE);
        player.titleTextView.setText("");//清除标题,防止复用的时候出现
        ImageManager.getInstance().loadImage(this, worksListBean.getWorksCover() + "?imageView2/0/w/750/h/350", player.thumbImageView);//设置缩略图
        playVideoData();
        player.setmOnVideoClickListener(new OnVideoClickListener() {
            @Override
            public void onVideoClickToStart() {
                playVideoData();
            }

            @Override
            public void onVideoProgress(int duration) {
                if (!((boolean) player.objects[1])) {
                    KLog.e("进来了吗？onVideoProgressonVideoProgress未支付过的视频");
                    backPress();
                    ((TextView) findViewById(R.id.isplay_pay_content)).setText("观看完整版只需" + worksListBean.getWorksPrice() + "元哟");
                    findViewById(R.id.item_video_list_pay).setVisibility(View.VISIBLE);
                    JZMediaManager.instance().mediaPlayer.pause();
                }
            }
        });
        //取消
        findViewById(R.id.video_pay_cacel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.item_video_list_pay).setVisibility(View.GONE);
                JZMediaManager.instance().mediaPlayer.seekTo(0);
                JZMediaManager.instance().mediaPlayer.start();//继续播放免费视频
            }
        });
        //支付
        findViewById(R.id.video_pay_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Config.getCachedUserId(VideoCommentActivity.this) == null) {
                    startActivity(new Intent(VideoCommentActivity.this, LoginTranslucentActivtiy.class));
                } else {
                    Intent payIntent = new Intent(VideoCommentActivity.this, PayVideoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("workId", worksId);
                    bundle.putInt("workprice",worksListBean.getWorksPrice());
                    payIntent.putExtra("id_bundle", bundle);
                    startActivityForResult(payIntent, 101);
//                    Intent payIntent = new Intent(VideoCommentActivity.this, VipPayActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("workListBean",worksListBean);
//                    payIntent.putExtra("id_bundle", bundle);
//                    startActivityForResult(payIntent, 101);
                }
            }
        });
    }

    private void playVideoData() {
        VideoUrlDecoder decoder = new VideoUrlDecoder() {
            @Override
            public void onSuccess(VideoUrlInfo urlInfo) {
                String contentAdvanceUrl = urlInfo.getOBJECT().getWorkContent().getContentAdvanceUrl();
                contentUrl = urlInfo.getOBJECT().getWorkContent().getContentUrl();
                var = urlInfo.getOBJECT().getVar();
                var_advance = urlInfo.getOBJECT().getVar_advance();
                KLog.e("///////"+Config.getSessionId(VideoCommentActivity.this));
                player.setUp(contentUrl + "voddrm.token."+Config.getSessionId(VideoCommentActivity.this)+
                        ".v.f230.m3u8?"+var, JZVideoPlayer.SCREEN_LAYOUT_NORMAL, "", true);
//                if (worksListBean.getWorksPrice() == 0) {//免费片
//                    player.setUp(contentUrl + "voddrm.token."+Config.getSessionId(VideoCommentActivity.this)+
//                            ".v.f230.m3u8?"+var, JZVideoPlayer.SCREEN_LAYOUT_NORMAL, "", true);
//                } else if (urlInfo.getOBJECT().getBuyRecord() == null) {//未购买,付费
//                    player.setUp(contentAdvanceUrl + "voddrm.token."+Config.getSessionId(VideoCommentActivity.this)+
//                            ".v.f230.m3u8?"+var_advance, JZVideoPlayer.SCREEN_LAYOUT_NORMAL, "", false);
//                } else {
//                    player.setUp(contentUrl + "voddrm.token."+Config.getSessionId(VideoCommentActivity.this)+
//                            ".v.f230.m3u8?"+var, JZVideoPlayer.SCREEN_LAYOUT_NORMAL, "", true);
//                }
                KLog.e(contentUrl + "voddrm.token."+Config.getSessionId(VideoCommentActivity.this)+
                        ".v.f230.m3u8?"+var);
                player.startVideo();
            }

            @Override
            public void onDecodeError() {

            }
        };
        decoder.decodePath(worksId);
    }

    Drawable hotNo, hotYes;

    private void initTopActor() {
        //Top数据
        topActorImage = (ImageView) findViewById(R.id.activity_video_comment_top_userimage);
        topActorName = (TextView) findViewById(R.id.activity_video_comment_top_username);
        topActorFocus = (TextView) findViewById(R.id.activity_video_comment_top_focus);
        topActorFocus.setOnClickListener(this);
        ImageManager.getInstance().loadRoundImage(this, worksListBean.getActorHeadshot(), topActorImage);
        topActorName.setText(worksListBean.getActorName());
        hotNo = getResources().getDrawable(R.mipmap.video_hot_no);
        hotNo.setBounds(0, 0, UIUtils.dip2Px(25), UIUtils.dip2Px(20));
        hotYes = getResources().getDrawable(R.mipmap.video_hot_yes);
        hotYes.setBounds(0, 0, UIUtils.dip2Px(25), UIUtils.dip2Px(20));
        Drawable commentIcon = getResources().getDrawable(R.mipmap.video_comment);
        commentIcon.setBounds(0, 0, UIUtils.dip2Px(19), UIUtils.dip2Px(20));
        topHot = (TextView) findViewById(R.id.activity_video_comment_top_hot_tv);
        topHot.setCompoundDrawables(hotNo, null, null, null);
        topHot.setText(workPraiseNumber + "");
        topHot.setOnClickListener(this);
        topComment = (TextView) findViewById(R.id.activity_video_comment_top_comment_tv);
        topComment.setCompoundDrawables(commentIcon, null, null, null);
        topComment.setText(worksListBean.getWorksCommentNum() + "");
    }

    TextView bottomCommentTv;

    private void initBottom() {
        bottomCommentTv = (TextView) findViewById(R.id.video_comment_detail_bottom_lefttv);
        bottomCommentTv.setOnClickListener(this);
    }

    private void initPopwidow() {
        View view = LayoutInflater.from(this).inflate(R.layout.comment_popupwindow, null);
        commentEt = (EditText) view.findViewById(R.id.et_discuss);
        commentCommit = (TextView) view.findViewById(R.id.tv_confirm);
        commentCommit.setOnClickListener(this);
        mPop = new CustomPopupWindow(this, view);
    }

    Drawable priseDrawable, priseCheckedDrawable;

    @Override
    protected void onResume() {
        super.onResume();
        Eyes.translucentStatusBar(this, true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;
        if (requestCode == 101) {
            player.setUp(contentUrl + "v.f30.mp4?" + var, JZVideoPlayer.SCREEN_LAYOUT_NORMAL, "", true);
            player.startVideo();
        }
    }

    int isFocus;

    public void loadData(int page) {
        ApiRetrofit.getInstance().getApiService().getCommentData(worksListBean.getWorksId(), page, 8, worksListBean.getWorksActor())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentResponse>() {

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
                    public void onNext(CommentResponse commentResponse) {
                        CommentResponse.OBJECTBean object = commentResponse.getOBJECT();
                        isFocus = object.getActor().getIsFocus();
                        if (isFocus == 1) {
                            topActorFocus.setText("关注");
                        } else {
                            topActorFocus.setText("取消关注");
                        }
                        if (pageNum == 1) {
                            lists.clear();
                            recyclerView.refreshComplete();
                        } else {
                            recyclerView.loadMoreComplete();
                        }
                        if (commentResponse.getOBJECT().getCommentList().size() == 0) {
                            if (page > 1) {
                                UIUtils.showToast(VideoCommentActivity.this.getResources().getString(R.string.loading_end));
                            } else {
                                UIUtils.showToast(VideoCommentActivity.this.getResources().getString(R.string.loading_entry));
                                entryTv.setVisibility(View.VISIBLE);
                            }
                            return;
                        }
                        entryTv.setVisibility(View.GONE);
                        adapter.setData(commentResponse.getOBJECT().getCommentList());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_video_comment_top_hot_tv://点赞
                if (workIsPraise == 0) {
                    //点赞接口
                    ApiRetrofit.getInstance().getApiService().getIsPraising(worksId, 0)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<CollectResponse>() {

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
                                public void onNext(CollectResponse collectResponse) {
                                    UIUtils.showToast(collectResponse.getMSG());
                                    if ("0".equals(collectResponse.getSTATUS())) {
                                        //icon改变
                                        workIsPraise = 1;
                                        workPraiseNumber++;
                                        topHot.setCompoundDrawables(hotYes, null, null, null);
                                        topHot.setText(workPraiseNumber + "");
                                    }
                                }
                            });
                } else {
                    //传1取消点赞接口
                    ApiRetrofit.getInstance().getApiService().getIsPraising(worksId, 1)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<CollectResponse>() {

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
                                public void onNext(CollectResponse collectResponse) {
                                    UIUtils.showToast(collectResponse.getMSG());
                                    workIsPraise = 0;
                                    workPraiseNumber--;
                                    topHot.setCompoundDrawables(hotNo, null, null, null);
                                    topHot.setText(workPraiseNumber + "");
                                }
                            });
                }
                break;
            case R.id.video_comment_detail_bottom_lefttv:
                if (Config.getCachedUserId(this) == null) {
                    startActivity(new Intent(this, LoginTranslucentActivtiy.class));
                } else {
                    //设置PopupWindow中的位置
                    mPop.showAtLocation(VideoCommentActivity.this.findViewById(R.id.video_comment_detail_main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    forceOpenSoftKeyboard(this);
                }
                break;
            case R.id.tv_confirm://提交评论
                if (commentEt.getText().toString().length() == 0) {
                    UIUtils.showToast("请输入评论内容");
                    return;
                }
                mPop.dismiss();
                //KeyBoardUtils.hideSoftInput(this);
                loadCommentData();
                break;
            case R.id.activity_video_comment_top_focus://关注
                if (Config.getCachedUserId(this) == null) {
                    startActivity(new Intent(this, LoginTranslucentActivtiy.class));
                } else if(isFocus == 1){
                    attentionCommit();//关注
                }else if(isFocus == 2){
                    attentionCacel();//取消关注
                }
                break;
        }
    }

    private void attentionCacel() {
        ApiRetrofit.getInstance().getApiService().getCancelAttention(worksListBean.getWorksActor())
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
                            isFocus = 1;
                            topActorFocus.setText("关注");
                        }
                    }
                });
    }

    private void attentionCommit() {
        ApiRetrofit.getInstance().getApiService().getCommitAttention(worksListBean.getWorksActor())
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
                            isFocus = 2;
                            topActorFocus.setText("取消关注");
                        }
                    }
                });
    }

    public void forceOpenSoftKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
    }


    private void loadCommentData() {
        ApiRetrofit.getInstance().getApiService().getCommentWorks(worksId, commentEt.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ComResponse>() {

                    @Override
                    public void onError(Throwable e) {
                        KLog.e("error" + e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ComResponse response) {
                        if (response.getSTATUS().equals("0")) {
                            UIUtils.showToast(response.getMSG());
                            pageNum = 1;
                            loadData(pageNum);
                        } else if ("3".equals(response.getSTATUS())) {
                            if (Config.getCachedPhoneNum(VideoCommentActivity.this) != null && Config.getCachedUserPwd(VideoCommentActivity.this) != null) {
                                //调用登录接口
                                ApiRetrofit.getInstance().getApiService().getAutoLogin
                                        (Config.getCachedPhoneNum(VideoCommentActivity.this), Config.getCachedUserPwd(VideoCommentActivity.this), 2)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<UserInfoResponse>() {

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
                                            public void onNext(UserInfoResponse userInfoResponse) {
                                                if ("0".equals(userInfoResponse.getSTATUS())) {
                                                    loadCommentData();
                                                }
                                            }
                                        });
                            }
                        }
                    }
                });
    }
}

package com.lushuitv.yewuds.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.adapter.VideoCommentAdapter;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.flyn.Eyes;
import com.lushuitv.yewuds.module.response.ComResponse;
import com.lushuitv.yewuds.module.response.CommentResponse;
import com.lushuitv.yewuds.utils.CustomPopupWindow;
import com.lushuitv.yewuds.utils.KeyBoardUtils;
import com.lushuitv.yewuds.utils.UIUtils;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description
 * Created by weip
 * Date on 2017/9/23.
 */

public class CommentListsActivity extends BaseActivity implements View.OnClickListener {

    public static final String COMMENT_WORK_ID = "id";

    public static final String COMMENT_WORK_ACTOR = "worksactor";
    private XRecyclerView commentRecycleView;
    VideoCommentAdapter adapter;
    int workId, currentPage = 1;
    int worksActor;
    private List<CommentResponse.OBJECTBean.CommentListBean> mLists;
    private View mView;

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.comment_list_layout, null);
        return mView;
    }

    @Override
    public void initToolbar() {
        mToolbar.setBackgroundColor(getResources().getColor(R.color.white));
        mToolbar.setNavigationIcon(R.drawable.back_black);
        mToolbarTitle.setTextColor(getResources().getColor(R.color.black));
        mToolbarTitle.setText("评论详情");
        setSupportActionBar(mToolbar);
        //设置返回键可用
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Eyes.setStatusBarLightMode(this, getResources().getColor(R.color.white));
    }

    @Override
    public void initOptions() {
        commentRecycleView = (XRecyclerView) findViewById(R.id.comment_list_layout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        commentRecycleView.setLayoutManager(layoutManager);
        commentRecycleView.setPullRefreshEnabled(true);
        commentRecycleView.setLoadingMoreEnabled(true);
        commentRecycleView.setRefreshProgressStyle(ProgressStyle.LineScalePulseOutRapid);
//        commentRecycleView.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        commentRecycleView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        commentRecycleView.setArrowImageView(R.mipmap.refresh_head_arrow);//下拉图标
        TextView bottomComment = (TextView) findViewById(R.id.comment_list_bottom_comment);
        bottomComment.setOnClickListener(this);
        mLists = new ArrayList<CommentResponse.OBJECTBean.CommentListBean>();
        Intent intent = getIntent();
        workId = intent.getIntExtra(COMMENT_WORK_ID, 0);
        worksActor = intent.getIntExtra(COMMENT_WORK_ACTOR,0);
        adapter = new VideoCommentAdapter(this, mLists);
//        commentRecycleView.addItemDecoration(new DividerDecoration(this,LinearLayoutManager.VERTICAL,1,14,14));
        commentRecycleView.setAdapter(adapter);
        commentRecycleView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                loadData();
            }

            @Override
            public void onLoadMore() {
                currentPage++;
                loadData();
            }
        });
        //
        View view = LayoutInflater.from(this).inflate(R.layout.comment_popupwindow, null);
        commentEt = (EditText) view.findViewById(R.id.et_discuss);
        commentCommit = (TextView) view.findViewById(R.id.tv_confirm);
        commentCommit.setOnClickListener(this);
        mPop = new CustomPopupWindow(this, view);
        loadData();
    }

    EditText commentEt;
    TextView commentCommit;
    CustomPopupWindow mPop;

    private void loadData() {
        ApiRetrofit.getInstance().getApiService().getCommentData(workId, currentPage, 9,worksActor)
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
                        if (currentPage == 1) {
                            mLists.clear();
                            commentRecycleView.refreshComplete();
                        } else {
                            commentRecycleView.loadMoreComplete();
                        }
                        if (currentPage > 1 && commentResponse.getOBJECT().getCommentList().size()==0){
                            UIUtils.showToast(CommentListsActivity.this.getResources().getString(R.string.loading_end));
                            return;
                        }
                        adapter.setData(commentResponse.getOBJECT().getCommentList());
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comment_list_bottom_comment:
                mPop.showAtLocation(this.findViewById(R.id.comment_list_parent), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                //KeyBoardUtils.openKeybord(this);//强制弹出软键盘
                break;
            case R.id.tv_confirm://提交评论
                if (Config.getCachedUserId(this) == null) {
                    startActivity(new Intent(this, LoginTranslucentActivtiy.class));
                    return;
                }
                if (commentEt.getText().toString().length() == 0) {
                    UIUtils.showToast("评论内容为空,请重新输入");
                    break;
                }
                KeyBoardUtils.closeKeyboard(commentEt, this);
                if (mPop != null && mPop.isShowing()) {
                    mPop.dismiss();
                }
                ApiRetrofit.getInstance().getApiService().getCommentWorks(workId, commentEt.getText().toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ComResponse>() {

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
                            public void onNext(ComResponse comResponse) {
                                if ("0".equals(comResponse.getSTATUS())) {
                                    UIUtils.showToast(comResponse.getMSG());
                                    commentEt.setText("");
                                    loadData();
                                }
                            }
                        });
                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base_toolbar, menu);
        updateOptionsMenu(menu);
        return true;
    }

    protected void updateOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_save).setVisible(false);
        menu.findItem(R.id.action_download).setVisible(false);
        menu.findItem(R.id.action_share).setVisible(false);
    }

}

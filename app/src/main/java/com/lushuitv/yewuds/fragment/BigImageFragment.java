package com.lushuitv.yewuds.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.lushuitv.yewuds.Image.ImageManager;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.activity.CommentListsActivity;
import com.lushuitv.yewuds.activity.LoginTranslucentActivtiy;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.module.response.CollectResponse;
import com.lushuitv.yewuds.module.response.ComResponse;
import com.lushuitv.yewuds.utils.BlurUtils;
import com.lushuitv.yewuds.utils.CustomPopupWindow;
import com.lushuitv.yewuds.utils.FileUtils;
import com.lushuitv.yewuds.utils.KeyBoardUtils;
import com.lushuitv.yewuds.utils.UIUtils;
import com.socks.library.KLog;

import java.io.File;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


import static com.lushuitv.yewuds.R.id.tv_save;

/**
 * 展示大图的fragment
 */

public class BigImageFragment extends Fragment implements View.OnClickListener {

    public static final String IMG_URL = "imgUrl";

    public static final String IMG_INDEX = "imgIndex";

    public static final String IMG_TOTALNUMBER = "imgtotal";

    public static final String IMG_WORK_ID = "workid";

    public static final String IMG_WORK_ISCOLLECT = "workiscollect";

    public static final String IMG_WORK_ISPRIASE = "workispriase";

    public static final String IMG_WORK_COMMENTCOUNT = "workcommentcount";

    public static final String IMG_WORK_PRIASECOUNT = "workpriasecount";

    public static final String WORK_ACTOR = "worksActor";

    ImageView backView;

    int workId, isCollect, isPriase;

    int commentCount, priaseCount;

    int workActor;

    PhotoView mIvPic;

    RelativeLayout bottomLayout;
    RelativeLayout topLayout;

    private CustomPopupWindow mPop;

    EditText commentEt;
    TextView commentCommit;

    TextView writeComment;

    //指示器、保存
    TextView mTvIndicator, mTvSave;
    String imgUrl;//图片url

    //评论、收藏、点赞、分享
    FrameLayout bottomCommentFl, bottomPriaseFl;
    RelativeLayout bottomCollectRl;
    ImageView bottomCollectIv, bottomShareIv, bottomPriaseIv;
    TextView bottomCommentTv, bottomPriaseTv;

    RelativeLayout bigImageBottomLayout;

    FrameLayout topRl;

    public View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_big_image, container, false);
        initView(mView);
        initData();
        return mView;
    }
    public void initView(View rootView) {
//        mIvPic = (PhotoView) rootView.findViewById(R.id.pv_pic);
        topRl = (FrameLayout) rootView.findViewById(R.id.big_image_top_rl);
//        mCircleProgressView = (CircleProgressView) rootView.findViewById(R.id.progressView);
        initTopView(rootView);
        initBottomView(rootView);

    }

    private void initTopView(View rootView) {
        backView = (ImageView) rootView.findViewById(R.id.iv_back);
        backView.setOnClickListener(this);
        mTvIndicator = (TextView) rootView.findViewById(R.id.tv_indicator);
        mTvSave = (TextView) rootView.findViewById(tv_save);
        mTvSave.setVisibility(View.GONE);
        mTvSave.setOnClickListener(this);
        topLayout = (RelativeLayout) rootView.findViewById(R.id.big_image_top_bar);
    }

    private void initBottomView(View rootView) {
        bigImageBottomLayout = (RelativeLayout) rootView.findViewById(R.id.big_image_bottom_layout);
        bottomCommentTv = (TextView) rootView.findViewById(R.id.tv_comment_count);
        bottomPriaseTv = (TextView) rootView.findViewById(R.id.tv_priase_count);
        bottomPriaseIv = (ImageView) rootView.findViewById(R.id.bottom_fl_priase_iv);

        writeComment = (TextView) rootView.findViewById(R.id.detail_bottom_comment);
        writeComment.setOnClickListener(this);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.comment_popupwindow, null);
        commentEt = (EditText) view.findViewById(R.id.et_discuss);
        commentCommit = (TextView) view.findViewById(R.id.tv_confirm);
        commentCommit.setOnClickListener(this);
        mPop = new CustomPopupWindow(getActivity(), view);
        //底部
        bottomCommentFl = (FrameLayout) rootView.findViewById(R.id.bottom_fl_comment_fl);
        bottomCommentFl.setOnClickListener(this);
        bottomPriaseFl = (FrameLayout) rootView.findViewById(R.id.bottom_fl_priase_fl);
        bottomPriaseFl.setOnClickListener(this);
        bottomCollectRl = (RelativeLayout) rootView.findViewById(R.id.fragment_bottom_collect_rl);
        if (Config.getCachedDataType(getActivity()) == 1) {
            bottomCollectRl.setVisibility(View.GONE);
        }else{
            bottomCollectRl.setVisibility(View.VISIBLE);
        }
        bottomCollectIv = (ImageView) rootView.findViewById(R.id.star_iv);
        bottomCollectIv.setOnClickListener(this);
        bottomShareIv = (ImageView) rootView.findViewById(R.id.bottom_bar_share_iv);
        bottomShareIv.setVisibility(View.GONE);
        bottomShareIv.setOnClickListener(this);
    }
    protected void initData() {
        Bundle bundle = getArguments();
        imgUrl = bundle.getString(IMG_URL);
        int imgIndex = bundle.getInt(IMG_INDEX);
        int imgTotalNumber = bundle.getInt(IMG_TOTALNUMBER);
        workId = bundle.getInt(IMG_WORK_ID);
        isCollect = bundle.getInt(IMG_WORK_ISCOLLECT);
        isPriase = bundle.getInt(IMG_WORK_ISPRIASE);
        commentCount = bundle.getInt(IMG_WORK_COMMENTCOUNT);
        priaseCount = bundle.getInt(IMG_WORK_PRIASECOUNT);
        workActor = bundle.getInt(WORK_ACTOR);

        bottomCommentTv.setText(commentCount + "");
        bottomPriaseTv.setText(priaseCount + "");

        if (isCollect == 1)
            bottomCollectIv.setBackgroundResource(R.mipmap.video_collect_yes);
        else
            bottomCollectIv.setBackgroundResource(R.mipmap.video_collect_no);
        if (isPriase == 1) {
            bottomPriaseIv.setBackgroundResource(R.mipmap.video_hot_no);
        } else {
            bottomPriaseIv.setBackgroundResource(R.mipmap.video_hot_yes);
        }
        mTvIndicator.setText(imgIndex + 1 + "/" + imgTotalNumber);//设置当前的指示
        int i = mTvIndicator.getText().toString().indexOf("/");
        SpannableString spannableString = new SpannableString(mTvIndicator.getText().toString());
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FD2E42")),
                0, i+1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvIndicator.setText(spannableString);
        KLog.e(imgUrl);

        PhotoView photoView = new PhotoView(getActivity());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        photoView.setLayoutParams(layoutParams);
        photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        photoView.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                if (topLayout.isShown())
                    topLayout.setVisibility(View.GONE);
                else
                    topLayout.setVisibility(View.VISIBLE);
                if (bigImageBottomLayout.isShown())
                    bigImageBottomLayout.setVisibility(View.GONE);
                else
                    bigImageBottomLayout.setVisibility(View.VISIBLE);
            }
        });
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Bitmap bitmap = ImageManager.getInstance().loadImage(getActivity(), imgUrl + "?imageView2/2/w/600");
//                Bitmap bitmap1 = BlurUtils.doBlur(bitmap,8,8);
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        photoView.setImageBitmap(bitmap1);
//                        topRl.addView(photoView);
//                    }
//                });
//            }
//        }).start();
        ImageManager.getInstance().loadImage(getActivity(),imgUrl + "?imageView2/2/w/600",photoView);
        topRl.addView(photoView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().finish();
                break;
            case R.id.detail_bottom_comment:
                //设置PopupWindow中的位置
                mPop.showAtLocation(topRl, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                forceOpenSoftKeyboard(getActivity());
                KLog.e("==========================="+mPop.isShowing());
                break;
            case R.id.tv_save://保存图片
//                new DownloadImgTask().execute(imgUrl);
                break;
            case R.id.tv_confirm://提交评论
                if (Config.getCachedUserId(getActivity()) == null) {
                    startActivity(new Intent(getActivity(), LoginTranslucentActivtiy.class));
                    return;
                }
                if (commentEt.getText().toString().length() == 0) {
                    UIUtils.showToast("评论内容为空,请重新输入");
                    break;
                }
                UIUtils.showToast("提交评论");
                KeyBoardUtils.closeKeyboard(commentEt, getActivity());

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
                                    bottomCommentTv.setText("" + (commentCount + 1));
                                }
                            }
                        });
                break;
            case R.id.bottom_fl_comment_fl://跳到评论列表
                Intent in = new Intent(getActivity(), CommentListsActivity.class);
                in.putExtra(CommentListsActivity.COMMENT_WORK_ID, workId);
                in.putExtra(CommentListsActivity.COMMENT_WORK_ACTOR, workActor);
                startActivity(in);
                break;
            case R.id.bottom_fl_priase_fl://点赞
                if (Config.getCachedUserId(getActivity()) == null) {
                    Intent intent = new Intent(getActivity(), LoginTranslucentActivtiy.class);
                    startActivity(intent);
                } else {
                    if (isPriase == 0) {
                        //点赞
                        ApiRetrofit.getInstance().getApiService().getIsPraising(workId, 0)
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
                                            isPriase = 1;
                                            bottomPriaseTv.setText("" + (++priaseCount));
                                            bottomPriaseIv.setBackgroundResource(R.mipmap.video_hot_yes);
                                        }
                                    }
                                });
                    } else {
                        //取消点赞接口
                        ApiRetrofit.getInstance().getApiService().getIsPraising(workId, 1)
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
                                        //icon改变
                                        isPriase = 0;
                                        bottomPriaseTv.setText("" + (--priaseCount));
                                        bottomPriaseIv.setBackgroundResource(R.mipmap.video_hot_no);
                                    }
                                });
                    }
                }
                break;
            case R.id.star_iv://收藏
                if (Config.getCachedUserId(getActivity()) == null) {
                    Intent intent = new Intent(getActivity(), LoginTranslucentActivtiy.class);
                    startActivity(intent);
                } else {
                    if (isCollect == 0) {//未收藏、调取收藏接口
                        ApiRetrofit.getInstance().getApiService().getCollect(workId, 0)
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
                                        if ("0".equals(collectResponse.getSTATUS())) {
                                            UIUtils.showToast(collectResponse.getMSG());
                                            isCollect = 1;
                                            bottomCollectIv.setBackgroundResource(R.mipmap.video_collect_yes);
                                        }
                                    }
                                });
                    } else {
                        ApiRetrofit.getInstance().getApiService().getCollect(workId, 1)
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
                                        if ("0".equals(collectResponse.getSTATUS())) {
                                            UIUtils.showToast(collectResponse.getMSG());
                                            isCollect = 0;
                                            bottomCollectIv.setBackgroundResource(R.mipmap.video_collect_no);
                                        }
                                    }
                                });
                    }
                }
                break;
            case R.id.bottom_bar_share_iv://分享
                break;
        }
    }

    class DownloadImgTask extends AsyncTask<String, Integer, Void> {


        public DownloadImgTask() {

        }

        @Override
        protected Void doInBackground(String... params) {
            String imgUrl = params[0];
            File file = null;
            try {
                FutureTarget<File> future = Glide
                        .with(getActivity())
                        .load(imgUrl)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                file = future.get();

                String filePath = file.getAbsolutePath();

                String destFileName = System.currentTimeMillis() + FileUtils.getImageFileExt(filePath);
                File destFile = new File(FileUtils.getDir(""), destFileName);

                FileUtils.copy(file, destFile);// 保存图片

                // 最后通知图库更新
                getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                        Uri.fromFile(new File(destFile.getPath()))));
            } catch (Exception e) {
                KLog.e(e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            UIUtils.showToast("保存成功，图片所在文件夹:SD卡根路径/TouTiao");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            KLog.e("progress: " + values[0]);
        }

    }

    public void forceOpenSoftKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}



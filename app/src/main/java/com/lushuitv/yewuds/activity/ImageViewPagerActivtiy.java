package com.lushuitv.yewuds.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.fragment.BigImageFragment;
import com.lushuitv.yewuds.module.response.UnfreeWorkLists;
import com.lushuitv.yewuds.utils.FileUtils;
import com.lushuitv.yewuds.utils.KeyBoardUtils;
import com.lushuitv.yewuds.utils.UIUtils;
import com.socks.library.KLog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 详情页查看图片的activity
 * Created by weip
 * Date on 2017/8/30.
 */

public class ImageViewPagerActivtiy extends BaseActivity implements ViewPager.OnPageChangeListener {
    private View mView;
    ViewPager mVpPics;
    private List<String> mImageUrls = new ArrayList<String>();
    private List<BigImageFragment> mFragments = new ArrayList<BigImageFragment>();
    private int mCurrentPosition;
    private Map<Integer, Boolean> mDownloadingFlagMap = new HashMap<Integer, Boolean>();//用于保存对应位置图片是否在下载的标识
    private int worksId, isCollect, worksActor;

    @Override
    protected View initContentView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去除状态栏
        mView = View.inflate(this, R.layout.activity_image_view_pager, null);
        return mView;
    }

    @Override
    public void initToolbar() {
        appBarLayout.setVisibility(View.GONE);
    }

    @Override
    public void initOptions() {
        Intent intent = getIntent();
        mCurrentPosition = intent.getIntExtra("minposition",0);
        worksId = intent.getIntExtra("worksId", 0);
        isCollect = intent.getIntExtra("worksIsCollect", 0);
        worksActor = intent.getIntExtra("worksActor", 0);
        mVpPics = (ViewPager) findViewById(R.id.vp_pics);
        if (Config.getCachedDataType(this) == 1) {
            ApiRetrofit.getInstance().getApiService().getVerUnfreeDetail(worksId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<UnfreeWorkLists>() {

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
                        public void onNext(UnfreeWorkLists unfreeWorkLists) {
                            if ("0".equals(unfreeWorkLists.getSTATUS())) {
                                List<UnfreeWorkLists.OBJECTBean.ContentListBean> contentList = unfreeWorkLists.getOBJECT().getContentList();
                                for (UnfreeWorkLists.OBJECTBean.ContentListBean bean : contentList) {
                                    mImageUrls.add(bean.getContentUrl());
                                }
                                for (int i = 0; i < mImageUrls.size(); i++) {
                                    String url = mImageUrls.get(i);
                                    BigImageFragment imageFragment = new BigImageFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putString(BigImageFragment.IMG_URL, url);
                                    bundle.putInt(BigImageFragment.IMG_INDEX, i);
                                    bundle.putInt(BigImageFragment.IMG_TOTALNUMBER, mImageUrls.size());
                                    bundle.putInt(BigImageFragment.IMG_WORK_ID, unfreeWorkLists.getOBJECT().getWorksId());
                                    bundle.putInt(BigImageFragment.IMG_WORK_ISCOLLECT, unfreeWorkLists.getOBJECT().getIsColletion());
                                    bundle.putInt(BigImageFragment.IMG_WORK_ISPRIASE, unfreeWorkLists.getOBJECT().getIsPraising());
                                    bundle.putInt(BigImageFragment.IMG_WORK_COMMENTCOUNT, unfreeWorkLists.getOBJECT().getWorksCommentNum());
                                    bundle.putInt(BigImageFragment.IMG_WORK_PRIASECOUNT, unfreeWorkLists.getOBJECT().getWorksPraising());
                                    bundle.putInt(BigImageFragment.WORK_ACTOR, worksActor);
                                    imageFragment.setArguments(bundle);
                                    mFragments.add(imageFragment);//添加到fragment集合中
                                    mDownloadingFlagMap.put(i, false);//初始化map，一开始全部的值都为false
                                }
                                mVpPics.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
                                mVpPics.addOnPageChangeListener(ImageViewPagerActivtiy.this);
                                mVpPics.setCurrentItem(mCurrentPosition);// 设置当前所在的位置
                            }
                        }
                    });

        } else if (Config.getCachedDataType(this) == 2) {
            ApiRetrofit.getInstance().getApiService().getUnfreeDetail(worksId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<UnfreeWorkLists>() {

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
                        public void onNext(UnfreeWorkLists unfreeWorkLists) {
                            if ("0".equals(unfreeWorkLists.getSTATUS())) {
                                List<UnfreeWorkLists.OBJECTBean.ContentListBean> contentList = unfreeWorkLists.getOBJECT().getContentList();
                                for (UnfreeWorkLists.OBJECTBean.ContentListBean bean : contentList) {
                                    mImageUrls.add(bean.getContentUrl());
                                }
                                for (int i = 0; i < mImageUrls.size(); i++) {
                                    String url = mImageUrls.get(i);
                                    BigImageFragment imageFragment = new BigImageFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putString(BigImageFragment.IMG_URL, url);
                                    bundle.putInt(BigImageFragment.IMG_INDEX, i);
                                    bundle.putInt(BigImageFragment.IMG_TOTALNUMBER, mImageUrls.size());
                                    bundle.putInt(BigImageFragment.IMG_WORK_ID, unfreeWorkLists.getOBJECT().getWorksId());
                                    bundle.putInt(BigImageFragment.IMG_WORK_ISCOLLECT, unfreeWorkLists.getOBJECT().getIsColletion());
                                    bundle.putInt(BigImageFragment.IMG_WORK_ISPRIASE, unfreeWorkLists.getOBJECT().getIsPraising());
                                    bundle.putInt(BigImageFragment.IMG_WORK_COMMENTCOUNT, unfreeWorkLists.getOBJECT().getWorksCommentNum());
                                    bundle.putInt(BigImageFragment.IMG_WORK_PRIASECOUNT, unfreeWorkLists.getOBJECT().getWorksPraising());
                                    bundle.putInt(BigImageFragment.WORK_ACTOR, worksActor);
                                    imageFragment.setArguments(bundle);
                                    mFragments.add(imageFragment);//添加到fragment集合中
                                    mDownloadingFlagMap.put(i, false);//初始化map，一开始全部的值都为false
                                }
                                mVpPics.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
                                mVpPics.addOnPageChangeListener(ImageViewPagerActivtiy.this);
                                mVpPics.setCurrentItem(mCurrentPosition);// 设置当前所在的位置
                            }
                        }
                    });
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}

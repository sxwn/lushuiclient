package com.lushuitv.yewuds.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.utils.UIUtils;
import com.socks.library.KLog;

import cn.jzvd.JZMediaManager;
import cn.jzvd.JZUtils;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import cn.jzvd.OnVideoClickListener;

/**
 * Created by Nathen on 2017/9/22.
 */

public class MyVideoPlayerStandard extends JZVideoPlayerStandard {

    private ImageView videoType;

    public ImageView getVideoType() {
        return videoType;
    }

    public void setVideoType(ImageView videoType) {
        this.videoType = videoType;
    }

    public MyVideoPlayerStandard(Context context) {
        super(context);
    }

    public MyVideoPlayerStandard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.my_jc_layout_standard;
    }

    @Override
    public void init(Context context) {
        super.init(context);
        videoType = (ImageView) findViewById(R.id.my_jc_layout_standard_freetype);
    }

    @Override
    public void onStateNormal() {
        super.onStateNormal();
        findViewById(R.id.item_video_list_pay).setVisibility(View.INVISIBLE);
    }

    @Override
    public void onStatePlaying() {
        super.onStatePlaying();
        KLog.e("video onStatePlaying"+getPosition());
    }

    @Override
    public void onStatePause() {
        super.onStatePause();
        KLog.e("video onStatePause");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start || v.getId() == R.id.thumb) {
            KLog.e("=============="+currentState);
            if (mOnVideoClickListener != null && currentState == -1 || currentState == 0) {
                mOnVideoClickListener.onVideoClickToStart();
                return;
            }
        }
        super.onClick(v);//走到super()视频就开始播放了
    }

    /**
     * 播放完成
     */
    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();
//        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
//            //quit fullscreen
//            backPress();
//        }
//        if (!((boolean)objects[1])){
//            findViewById(R.id.item_video_list_pay).setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void setProgressAndText(int progress, int position, int duration) {
        super.setProgressAndText(progress, position, duration);
        if (progress != 0) {
            bottomProgressBar.setProgress(progress);
            int currentPlayTime = getCurrentPositionWhenPlaying();
            int totalDaration =  getDuration();
            //8
            if (currentPlayTime / 1000 > 8) {
                if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
                    if (!((boolean) objects[1])) {
                        backPress();
                    }
                }
                //播放10s之后弹出窗口
                if (mOnVideoClickListener != null) {
                    mOnVideoClickListener.onVideoProgress(currentPlayTime);//时间
                }
            }
        }
    }

    private OnVideoClickListener mOnVideoClickListener;

    public void setmOnVideoClickListener(OnVideoClickListener mOnVideoClickListener) {
        this.mOnVideoClickListener = mOnVideoClickListener;
    }

    private int mPosition;

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        this.mPosition = position;
    }

}

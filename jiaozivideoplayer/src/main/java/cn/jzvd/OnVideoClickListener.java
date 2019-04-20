package cn.jzvd;

/**
 * Description
 * Created by weip
 * Date on 2017/9/28.
 */

public interface OnVideoClickListener {

    void onVideoClickToStart();
    void onVideoProgress(int duration);//监听播放进度
}

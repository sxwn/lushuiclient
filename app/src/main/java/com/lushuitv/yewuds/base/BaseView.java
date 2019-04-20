package com.lushuitv.yewuds.base;

/**
 * Created by weip on 2017/11/23.
 */

public interface BaseView {
    //开启加载动画
    void showLoading();

    //关闭加载动画
    void hideLoading();

    //开启下拉刷新
    void startRefush();

    //关闭下拉刷新
    void stopRefresh();

    //开启加载更多
    void startLoadingMore();

    //停止加载更多
    void stopLoadingMore();
}

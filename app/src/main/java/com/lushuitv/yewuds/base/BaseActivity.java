package com.lushuitv.yewuds.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lushuitv.yewuds.R;
import com.umeng.analytics.MobclickAgent;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * Created by weip on 2017/8/25.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public LinearLayout mRootLayout;
    public Toolbar mToolbar;
    public TextView mToolbarTitle, mToolbarRightTitle;
    public AVLoadingIndicatorView mLoading;
    public AppBarLayout appBarLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mRootLayout = (LinearLayout) findViewById(R.id.root_layout);
        appBarLayout = (AppBarLayout) findViewById(R.id.base_appbarLayout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarRightTitle = (TextView) findViewById(R.id.toolbar_righttitle);
        mLoading = (AVLoadingIndicatorView) findViewById(R.id.avi_loading);
        //显示具体的布局界面，由子类显示
        mRootLayout.addView(initContentView());
        //执行子类的操作
        initOptions();
        //初始化Toolbar
        initToolbar();
        //setStatusBar();
    }

//    private void setStatusBar() {
//        Eyes.setStatusBarLightMode(this, getResources().getColor(R.color.white));
//    }

    /**
     * 具体的业务逻辑，由子类实现
     */
    protected abstract void initOptions();

    /**
     * 具体的布局
     */
    protected abstract View initContentView();

    /**
     * 对toolbar进行设置
     */

    public abstract void initToolbar();

    /**
     * 展示加载动画
     */
    public void startLoading() {
        mLoading.smoothToShow();
    }

    /**
     * 隐藏加载动画
     */
    public void stopLoading() {
        mLoading.smoothToHide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base_toolbar, menu);
        updateOptionsMenu(menu);
        return true;
    }

    /**
     * 子类可以根据需要动态的更改菜单
     *
     * @param menu
     */
    protected void updateOptionsMenu(Menu menu) {

    }

    /**
     * 打开分享界面
     *
     * @param type
     */
    public void startShareIntent(String type, String content) {
        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);
        share_intent.setType(type);
        share_intent.putExtra(Intent.EXTRA_TEXT, content);
        share_intent = Intent.createChooser(share_intent, "分享");
        startActivity(share_intent);
    }

}

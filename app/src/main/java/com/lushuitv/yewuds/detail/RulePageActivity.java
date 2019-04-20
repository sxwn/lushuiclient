package com.lushuitv.yewuds.detail;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.app.ApiConstant;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.flyn.Eyes;
import com.lushuitv.yewuds.share.CommonSharePop;
import com.lushuitv.yewuds.utils.UIUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * 规则页面
 * Created by weip on 2017\12\19 0019.
 */

public class RulePageActivity extends BaseActivity {
    //http://www.cfrhls.com/h5/rule/income_cash_rule.html    收益提现规则
    private View mView;
    private WebView mWebView;
    private String mUrl;

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.activity_detail, null);
        return mView;
    }

    @Override
    public void initToolbar() {
        mRootLayout.setBackgroundColor(getResources().getColor(R.color.white));
        mToolbar.setBackgroundColor(getResources().getColor(R.color.white));
        mToolbar.setNavigationIcon(R.drawable.back_black);
        mToolbarTitle.setTextColor(getResources().getColor(R.color.black));
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
    String interfaceName;
    @Override
    protected void initOptions() {
        mUrl = getIntent().getStringExtra("url");
        String[] split = mUrl.split("/");
        interfaceName = split[split.length-1];
        if (interfaceName.startsWith("gold_make_rule")){
            mToolbarTitle.setText("露水金币用户协议");
        }else if(interfaceName.startsWith("disclaimer")){
            mToolbarTitle.setText("免责声明");
        }else if(interfaceName.startsWith("help")){
            mToolbarTitle.setText("帮助");
        }else if(interfaceName.startsWith("booking")){
            mToolbarTitle.setText("圣诞女神礼盒预售");
        }else if(interfaceName.startsWith("item")){
            mToolbarTitle.setText("微店");
        }else if(interfaceName.startsWith("share_rule")){
            mToolbarTitle.setText("收益规则");
        }else if(interfaceName.startsWith("income_cash_rule")){
            mToolbarTitle.setText("收益提现规则");
        }else{
            mToolbarTitle.setText("露水视频");
        }
        mWebView = (WebView) findViewById(R.id.webview);
        initWebView();
        mWebView.loadUrl(mUrl);
    }
    /**
     * webview相关参数设置
     */
    private void initWebView() {
        WebSettings webSettings = mWebView.getSettings();
        //支持js脚本
        webSettings.setJavaScriptEnabled(true);
        //支持缩放
        webSettings.setSupportZoom(true);
        //支持内容重新布局
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //多窗口
        webSettings.supportMultipleWindows();
        //当webview调用requestFocus时为webview设置节点
        webSettings.setNeedInitialFocus(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(false);
        //支持通过JS打开新窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //支持自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        //优先使用缓存:
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //提高渲染的优先级
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // 开启H5(APPCache)缓存功能
        webSettings.setAppCacheEnabled(true);
        // 开启 DOM storage 功能
        webSettings.setDomStorageEnabled(true);
        // 应用可以有数据库
        webSettings.setDatabaseEnabled(true);
        // 可以读取文件缓存(manifest生效)
        webSettings.setAllowFileAccess(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    //网页加载完成
                    //stopLoading();
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清除缓存
        mWebView.clearCache(true);
        //清除历史记录
        mWebView.clearHistory();
        mWebView.destroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base_toolbar, menu);
        updateOptionsMenu(menu);
        return true;
    }

    protected void updateOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_share).setVisible(false);
        menu.findItem(R.id.action_save).setVisible(false);
        menu.findItem(R.id.action_download).setVisible(false);
        if (interfaceName.startsWith("item") || interfaceName.startsWith("booking")) {
            menu.findItem(R.id.action_share).setVisible(true);
        }
    }
    CommonSharePop mSharePop;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                if (mSharePop == null) {
                    mSharePop = new CommonSharePop(this);
                }
                mSharePop.show(this.getWindow().getDecorView());
                if (interfaceName.startsWith("booking")){
                    mSharePop.setShare("2017·女神陪你过圣诞",
                            "分享给你一个不一样的圣诞礼物，如此性感炫酷，千万不要太感谢我~",
                            ApiConstant.BASE_SERVER_H5_URL + "h5/booking.html", R.drawable.share_christmas_pic);
                }else if(interfaceName.startsWith("item")){
                    mSharePop.setShare("2017·女神陪你过圣诞",
                            "分享给你一个不一样的圣诞礼物，如此性感炫酷，千万不要太感谢我~",
                            "https://weidian.com/item.html?itemID=2206284888&wfr=c&ifr=itemdetail", R.drawable.share_christmas_pic);
                }
                break;
            case R.id.action_save:
                break;
        }
        return true;
    }
}

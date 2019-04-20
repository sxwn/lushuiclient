package com.lushuitv.yewuds.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.app.ApiConstant;
import com.lushuitv.yewuds.app.MyApp;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.detail.RulePageActivity;
import com.lushuitv.yewuds.flyn.Eyes;
import com.lushuitv.yewuds.module.response.UserInfoResponse;
import com.lushuitv.yewuds.module.response.VersionInfo;
import com.lushuitv.yewuds.utils.GlideCacheUtil;
import com.lushuitv.yewuds.utils.SystemUtils;
import com.lushuitv.yewuds.utils.UIUtils;
import com.socks.library.KLog;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description 我的设置
 * Created by weip
 * Date on 2017/9/1.
 */

public class MineSetActiivity extends BaseActivity implements View.OnClickListener {

    RelativeLayout clearCacheRl, checkVersionRl, abousUs, freeContent, onLineHelp, logoutRl;
    private View mView;

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.activity_mine_set, null);
        return mView;
    }

    @Override
    public void initToolbar() {
        mToolbar.setBackgroundColor(getResources().getColor(R.color.white));
        mToolbar.setNavigationIcon(R.drawable.back_black);
        mToolbarTitle.setTextColor(getResources().getColor(R.color.black));
        mToolbarTitle.setText("系统设置");
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
        logoutRl = (RelativeLayout) mView.findViewById(R.id.mine_set_logout);
        logoutRl.setOnClickListener(this);
        clearCacheRl = (RelativeLayout) mView.findViewById(R.id.mine_set_clearcache);
        clearCacheRl.setOnClickListener(this);
        checkVersionRl = (RelativeLayout) mView.findViewById(R.id.mine_set_checkupdate);
        checkVersionRl.setOnClickListener(this);
        abousUs = (RelativeLayout) mView.findViewById(R.id.mine_set_aboutus);
        abousUs.setOnClickListener(this);
        freeContent = (RelativeLayout) mView.findViewById(R.id.mine_set_mianzecontent_rl);
        freeContent.setOnClickListener(this);
        onLineHelp = (RelativeLayout) mView.findViewById(R.id.mine_set_help);
        onLineHelp.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.mine_set_logout:
                logout();
                break;
            case R.id.mine_set_checkupdate://检测自动更新
                initCheckVersion();
//                showDialogUpdate();//弹出提示版本更新的对话框
                break;
            case R.id.mine_set_aboutus:
                startActivity(new Intent(this, AboutUsActivity.class));
                break;
            case R.id.mine_set_clearcache:
                String cacheSize = GlideCacheUtil.getInstance().getCacheSize(this);
//                double cacheNumber = Double.parseDouble(cacheSize.substring(0, cacheSize.length() - 2));
//                GlideCacheUtil.getInstance().clearImageAllCache(this);
                UIUtils.showToast("成功清除缓存" + cacheSize);
                break;
            case R.id.mine_set_mianzecontent_rl:
//                startActivity(new Intent(this, FreeStatementActivity.class));
                startActivity(new Intent(this, RulePageActivity.class)
                        .putExtra("url", ApiConstant.BASE_SERVER_H5_URL+"h5/rule/disclaimer.html"));
                break;
            case R.id.mine_set_help:
//                startActivity(new Intent(this, OnLineHelpActivity.class));
                startActivity(new Intent(this, RulePageActivity.class)
                        .putExtra("url", ApiConstant.BASE_SERVER_H5_URL+"h5/rule/help.html"));
                break;
        }
    }

    private void initCheckVersion() {
        String version = SystemUtils.getVersion(this);
        ApiRetrofit.getInstance().getApiService().getCheckVersion(version, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VersionInfo>() {
                    @Override
                    public void onError(Throwable e) {
                        UIUtils.showToast("网络加载失败");
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(VersionInfo versionInfo) {
                        if (versionInfo.getOBJECT().getIsToUp() == 1) {
                            //强制更新
                            showDialogUpdate(versionInfo);//弹出提示版本更新的对话框
                        } else if (versionInfo.getOBJECT().getIsUp() == 1) {
                            //有更新
                            showDialogUpdate(versionInfo);//弹出提示版本更新的对话框
                        } else {
                            UIUtils.showToast("已是最新版本,不需要更新");
                        }
                    }
                });
    }
    private void showDialogUpdate(VersionInfo info) {
        // 这里的属性可以一直设置，因为每次设置后返回的是一个builder对象
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 设置提示框的标题
        builder.setTitle("版本升级").
                // 设置提示框的图标
                        setIcon(R.mipmap.logo_48).
                // 设置要显示的信息
                        setMessage("发现新版本！请及时更新").
                // 设置确定按钮
                        setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(MainActivity.this, "选择确定哦", 0).show();
                        loadNewVersionProgress(info.getOBJECT().getUrl());//下载最新的版本程序
                    }
                }).

                // 设置取消按钮,null是什么都不做，并关闭对话框
                        setNegativeButton("取消", null);

        // 生产对话框
        AlertDialog alertDialog = builder.create();
        // 显示对话框
        alertDialog.show();
    }

    /**
     * 下载新版本程序
     */
    private void loadNewVersionProgress(String url) {
        //final String uri = "http://www.apk.anzhi.com/data3/apk/201703/14/4636d7fce23c9460587d602b9dc20714_88002100.apk";
        final ProgressDialog pd;//进度条对话框
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.show();
        //启动子线程下载任务
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = getFileFromServer(url, pd);
                    sleep(3000);
                    installApk(file);
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    //下载apk失败
                    UIUtils.showToast("下载新版本失败");
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 安装apk
     */
    protected void installApk(File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
    }

    /**
     * 从服务器获取apk文件的代码
     * 传入网址uri，进度条对象即可获得一个File文件
     * （要在子线程中执行哦）
     */
    public static File getFileFromServer(String uri, ProgressDialog pd) throws Exception {
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            long time = System.currentTimeMillis();//当前时间的毫秒数
            File file = new File(Environment.getExternalStorageDirectory(), time + "updata.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                //获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }


    private void logout() {
        if (Config.getCachedUserId(this) == null) {
            UIUtils.showToast("用户未登录");
            return;
        }
        ApiRetrofit.getInstance().getApiService().getUserLogout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserInfoResponse>() {

                    @Override
                    public void onError(Throwable e) {
                        UIUtils.showToast("请检查网络~");
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserInfoResponse userInfoResponse) {
                        KLog.e("logout code:" + userInfoResponse.getSTATUS());
                        if ("0".equals(userInfoResponse.getSTATUS())) {
                            Config.cachePhoneNum(MineSetActiivity.this, null);
                            Config.cacheSessionId(MineSetActiivity.this, null);
                            Config.cachePwd(MineSetActiivity.this, null);
                            Config.cacheUserId(MineSetActiivity.this, null);
                            Config.cachePayStatus(MineSetActiivity.this, 0);
                            Config.cacheUserCode(MineSetActiivity.this, null);
                            MyApp.isLogout = true;
                            finish();
                        } else if ("3".equals(userInfoResponse.getSTATUS())) {
                            //用户未登录,调用登录接口
                            String userPhone = Config.getCachedPhoneNum(MyApp.getContext());
                            String userPwd = Config.getCachedUserPwd(MyApp.getContext());
                            ApiRetrofit.getInstance().getApiService().getAutoLogin(userPhone, userPwd, 2)
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
                                            if ("0".equals(userInfoResponse.getSTATUS()))
                                                logout();
                                        }
                                    });
                        }
                    }
                });
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

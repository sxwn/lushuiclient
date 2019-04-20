package com.lushuitv.yewuds.app;


import android.content.SharedPreferences;
import android.os.Handler;

import com.lushuitv.yewuds.BuildConfig;
import com.lushuitv.yewuds.app.base.BaseApp;
import com.lushuitv.yewuds.constant.Constants;
import com.lushuitv.yewuds.utils.BizService;
import com.socks.library.KLog;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.common.QueuedWork;
import com.umeng.socialize.common.SocializeConstants;

import cn.beecloud.BeeCloud;

/**
 * Created by weip on 2017/8/25.
 */

public class MyApp extends BaseApp {
    public static BizService bizService;
    public static boolean isLogout;
    // 系统上下文实例
    private static MyApp mInstance;
    // 所有系统配置有关的信息 都存放在这个xml文件里面（sharedPreferencesFile）
    private static final String SystemConfigFile = "systemConfig.xml";
    // 存放系统配置信息的sharedPreferences文件实例
    private static SharedPreferences mSharedPreferences;
    // 系统级hanlder实例
    private static Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        KLog.init(BuildConfig.DEBUG);//初始化KLog
        mInstance = this;
        mHandler = new Handler();
        createSharedPreferencesFile();
        KLog.e("友盟分享的sdk版本号===================" + SocializeConstants.SDK_VERSION);
        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        //Config.DEBUG = true;
        UMConfigure.init(this,UMConfigure.DEVICE_TYPE_PHONE,null);
        QueuedWork.isUseThreadPool = false;
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);//每次都需要授权
        UMShareAPI.get(this).setShareConfig(config);
        //BeeCloud.setSandbox(true);
        BeeCloud.setAppIdAndSecret("89a4a77b-cfab-485f-94ff-c3320ca79310", "a5485013-5908-47fe-b2b5-ee2022f81d33");
        //初始化 cosClient
        bizService = BizService.instance();
        bizService.init(getApplicationContext());
    }

    public void createSharedPreferencesFile() {
        mSharedPreferences = getSharedPreferences(SystemConfigFile, MODE_PRIVATE);
    }

    public static SharedPreferences getSystemConfigSharedFile() {
        return mSharedPreferences;
    }

    /**
     * 暴露上下文
     * @return
     */
    public static MyApp getApplication() {
        return mInstance;
    }

    public static Handler getHandler() {
        return mHandler;
    }

    //各个平台的配置，建议放在全局Application或者程序入口
    {
        PlatformConfig.setWeixin(Constants.WE_CHAT_APP_ID, "7a9b389ead994099f5bc7b49bd73498c");//切记,加密签名
        PlatformConfig.setSinaWeibo("3780171069", "94edbffa4b186480fd6b8f1c248af138",
                "http://sns.whalecloud.com");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }
}

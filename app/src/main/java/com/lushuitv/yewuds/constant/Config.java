package com.lushuitv.yewuds.constant;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;

import com.lushuitv.yewuds.app.MyApp;

/**
 * Description
 * Created by weip
 * Date on 2017/9/12.
 */

public class Config {

    public final static SharedPreferences sp = MyApp.getSystemConfigSharedFile();

    public static final String CHARSET = "utf-8";

    public static final String APP_ID = "com.lushuitv.yewuds";
    private static final String KEY_DATA_TYPE = "dataType";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_PHONE_NUM = "phone";
    public static final String KEY_SESSION_ID = "sessionId";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_CODE = "userCode";
    private static final String KEY_USER_PWD = "password";
    private static final String KEY_USER_PAYSTATUS = "status";

    private static final String KEY_WECHAT_OPENID = "openid";
    private static final String KEY_WECHAT_NAME = "wechatname";
    private static final String KEY_WECHAT_USERICON = "wechatusericon";


    public static void putBoolean(String key,boolean b){
        sp.edit().putBoolean(key,b).commit();
    }
    public static boolean getBoolean(String key){
        return sp.getBoolean(key,true);
    }


    public static String getCachedToken(Context context){
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_TOKEN,null);
    }
    public static void cachedToken(Context context,String token){
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).edit();
        e.putString(KEY_TOKEN,token);
        e.commit();
    }
    public static String getCachedPhoneNum(Context context){
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_PHONE_NUM, null);
    }

    public static void cachePhoneNum(Context context,String phoneNum){
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(KEY_PHONE_NUM, phoneNum);
        e.commit();
    }
    public static String getCachedUserId(Context context){
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_USER_ID, null);
    }

    public static void cacheSessionId(Context context,String sessionId){
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(KEY_SESSION_ID, sessionId);
        e.commit();
    }
    public static String getSessionId(Context context){
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_SESSION_ID, null);
    }

    public static void cacheUserId(Context context,String userid){
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(KEY_USER_ID, userid);
        e.commit();
    }


    public static void cachePwd(Context context,String password){
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(KEY_USER_PWD, password);
        e.commit();
    }

    public static String getCachedUserPwd(Context context){
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_USER_PWD, null);
    }

    public static void cachePayStatus(Context context,int status){
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putInt(KEY_USER_PAYSTATUS, status);
        e.commit();
    }

    public static int getCachedPayStatus(Context context){
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getInt(KEY_USER_PAYSTATUS,0);
    }

    public static void cacheWeChatOpenId(Context context,String openId){
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(KEY_WECHAT_OPENID,openId);
        e.commit();
    }

    public static String getCachedWeChatOpenId(Context context){
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_WECHAT_OPENID,null);
    }

    public static void cacheWeChatName(Context context,String name){
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(KEY_WECHAT_NAME,name);
        e.commit();
    }

    public static String getCachedWeChatName(Context context){
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_WECHAT_NAME,null);
    }

    public static void cacheWeChatUserIcon(Context context,String name){
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(KEY_WECHAT_USERICON,name);
        e.commit();
    }

    public static String getCachedWeChatUserIcon(Context context){
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_WECHAT_USERICON,null);
    }

    public static void cacheUserCode(Context context,String name){
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(KEY_USER_CODE,name);
        e.commit();
    }

    public static String getCachedUserCode(Context context){
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_USER_CODE,null);
    }


    public static void cacheDataType(Context context,int value){
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putInt(KEY_DATA_TYPE,value);
        e.commit();
    }

    public static int getCachedDataType(Context context){
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getInt(KEY_DATA_TYPE,0);
    }

}

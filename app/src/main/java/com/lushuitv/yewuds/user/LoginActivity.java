package com.lushuitv.yewuds.user;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.app.MyApp;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.coin.CoinCashPayPwdSetActivity;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.flyn.Eyes;
import com.lushuitv.yewuds.module.entity.UseParams;
import com.lushuitv.yewuds.module.response.AuthCodeResponse;
import com.lushuitv.yewuds.module.response.UserInfoResponse;
import com.lushuitv.yewuds.utils.SystemUtils;
import com.lushuitv.yewuds.utils.UIUtils;
import com.socks.library.KLog;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 登录
 * Created by weip on 2017\12\4 0004.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private static final int REQUEST_CODE_BINDPHONE = 1;
    private RadioGroup logonRg;
    private RadioButton loginAccountRb, loginAuthcodeRb;
    private EditText loginPhoneEt;
    private View loginPhoneLine;
    private EditText loginPwdEt;
    private TextView loginForgetPwdTv, loginObtainAuthcodeTv;
    private View loginPwdLine;
    private RelativeLayout loginRl;
    private TextView LoginRegisterTv, LoginWeChatLogin;
    private Drawable lockedPwdDrawable, authcodeDrawable;


    private View mView;

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.activity_login, null);
        return mView;
    }

    @Override
    public void initToolbar() {
        appBarLayout.setVisibility(View.GONE);
    }

    @Override
    public void initOptions() {
        Drawable phoneDrawable = getResources().getDrawable(R.drawable.login_phone_icon);
        phoneDrawable.setBounds(0, 0, UIUtils.dip2Px(this, 13), UIUtils.dip2Px(this, 20));
        lockedPwdDrawable = getResources().getDrawable(R.drawable.login_locked_icon);
        lockedPwdDrawable.setBounds(0, 0, UIUtils.dip2Px(this, 16), UIUtils.dip2Px(this, 20));
        authcodeDrawable = getResources().getDrawable(R.drawable.login_authcode_icon);
        authcodeDrawable.setBounds(0, 0, UIUtils.dip2Px(this, 20), UIUtils.dip2Px(this, 14));
        findViewById(R.id.activity_back).setOnClickListener(this);
        logonRg = (RadioGroup) findViewById(R.id.activity_login_rg);
        logonRg.setOnCheckedChangeListener(this);
        loginAccountRb = (RadioButton) findViewById(R.id.activity_login_rb_account);
        loginAccountRb.setOnClickListener(this);
        loginAuthcodeRb = (RadioButton) findViewById(R.id.activity_login_rb_authcode);
        loginAuthcodeRb.setOnClickListener(this);
        loginPhoneEt = (EditText) findViewById(R.id.activity_login_et_phone);
        TextChange tc = new TextChange();
        loginPhoneEt.addTextChangedListener(tc);//监听器
        loginPhoneEt.setCompoundDrawables(phoneDrawable, null, null, null);
        loginPhoneLine = findViewById(R.id.activity_login_et_phone_line);
        loginPwdEt = (EditText) findViewById(R.id.activity_login_et_pwd);
        loginPwdEt.addTextChangedListener(tc);//监听器
        loginPwdEt.setCompoundDrawables(lockedPwdDrawable, null, null, null);
        loginForgetPwdTv = (TextView) findViewById(R.id.activity_login_tv_forgetpwd);
        loginForgetPwdTv.setOnClickListener(this);
        loginObtainAuthcodeTv = (TextView) findViewById(R.id.activity_login_obtaincode_tv);
        loginObtainAuthcodeTv.setOnClickListener(this);
        loginPwdLine = findViewById(R.id.activity_login_et_pwd_line);
        loginRl = (RelativeLayout) findViewById(R.id.activity_login_rl);
        loginRl.setOnClickListener(this);
        LoginRegisterTv = (TextView) findViewById(R.id.activity_login_register_tv);
        LoginRegisterTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        LoginRegisterTv.getPaint().setAntiAlias(true);//抗锯齿
        LoginRegisterTv.setOnClickListener(this);
        LoginWeChatLogin = (TextView) findViewById(R.id.activity_wechat_login_tv);
        Drawable drawable = getResources().getDrawable(R.drawable.login_wechat);
        drawable.setBounds(0, 0, UIUtils.dip2Px(36), UIUtils.dip2Px(36));
        LoginWeChatLogin.setCompoundDrawables(null, drawable, null, null);
        LoginWeChatLogin.setOnClickListener(this);
    }

    class TextChange implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (loginPhoneEt.getText().length() > 0 && loginPwdEt.getText().length() > 0) {
                loginRl.setBackgroundResource(R.color.user_login_text_color);
            } else {
                loginRl.setBackgroundResource(R.color.user_login_text_hint_color);
            }
        }
    }
    int type;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_back:
                finish();
                break;
            case R.id.activity_login_rb_account://账号登录
                loginForgetPwdTv.setVisibility(View.VISIBLE);
                loginObtainAuthcodeTv.setVisibility(View.GONE);
                loginPwdEt.setHint("请输入密码");
                loginPwdEt.setCompoundDrawables(lockedPwdDrawable, null, null, null);
                break;
            case R.id.activity_login_rb_authcode://验证码登录
                loginForgetPwdTv.setVisibility(View.GONE);
                loginObtainAuthcodeTv.setVisibility(View.VISIBLE);
                loginPwdEt.setHint("请输入验证码");
                loginPwdEt.setCompoundDrawables(authcodeDrawable, null, null, null);
                break;
            case R.id.activity_login_rl://登录
                if (TextUtils.isEmpty(loginPhoneEt.getText().toString())) {
                    UIUtils.showToast("手机号不能为空");
                    return;
                }
                if (!SystemUtils.isMobileNO(loginPhoneEt.getText().toString())) {
                    UIUtils.showToast("手机号不合法");
                    return;
                }
                if (loginAccountRb.isChecked()) {
                    type = 1;
                    if (TextUtils.isEmpty(loginPwdEt.getText().toString())) {
                        UIUtils.showToast("密码不能为空");
                        return;
                    }
                } else {
                    type = 2;
                    if (TextUtils.isEmpty(loginPwdEt.getText().toString())) {
                        UIUtils.showToast("验证码不能为空");
                        return;
                    }
                }
                Map<String,Object> map = new HashMap<>();
                map.put("userTel",loginPhoneEt.getText().toString());
                if (type ==1){//账号登录
                    map.put("userPassword",loginPwdEt.getText().toString());
                }else{//验证码登录
                    map.put("veryCode",loginPwdEt.getText().toString());
                }
                map.put("type",String.valueOf(type));
                map.put("version",SystemUtils.getVersion(this));
                map.put("model",SystemUtils.getDeviceBrand());
                KLog.e("http://139.199.30.181:8080/api-1.0/userLogin.do?type=1&userPassword=123456&userTel=13641398016&version=1.1.4&model=Xiaomi");
                ApiRetrofit.getInstance().getApiService().getLogin(map)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<UserInfoResponse>() {

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
                            public void onNext(UserInfoResponse userInfo) {
                                if (userInfo.getSTATUS().equals("0")) {
                                    MyApp.isLogout = false;
                                    UseParams useParams = new Gson().fromJson(userInfo.getOBJECT().toString(), UseParams.class);
                                    Config.cacheSessionId(LoginActivity.this, useParams.getSessionId());
                                    Config.cachePhoneNum(LoginActivity.this, useParams.getUserTel());
                                    Config.cachePwd(LoginActivity.this, loginPwdEt.getText().toString());
                                    Config.cacheUserId(LoginActivity.this, useParams.getUserId());
                                    Config.cachePayStatus(LoginActivity.this, useParams.getUserPayStats());
                                    Config.cacheUserCode(LoginActivity.this, useParams.getUserCode());
                                    finish();
                                } else {
                                    UIUtils.showToast(userInfo.getMSG());
                                }
                            }
                        });
                break;
            case R.id.activity_login_obtaincode_tv://获取验证码
                if (TextUtils.isEmpty(loginPhoneEt.getText().toString())) {
                    UIUtils.showToast("手机号不能为空");
                    return;
                }
                if (!SystemUtils.isMobileNO(loginPhoneEt.getText().toString())) {
                    UIUtils.showToast("手机号不合法");
                    return;
                }
                loginObtainAuthcodeTv.setClickable(false);
                ApiRetrofit.getInstance().getApiService().getPhoneCode(loginPhoneEt.getText().toString(), 9).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<AuthCodeResponse>() {

                            @Override
                            public void onError(Throwable e) {
                                KLog.e(e.getLocalizedMessage());
                                loginObtainAuthcodeTv.setClickable(true);
                            }

                            @Override
                            public void onComplete() {

                            }

                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(AuthCodeResponse codeResponse) {
                                KLog.e("parseVideoResponse: " + codeResponse);
                                UIUtils.showToast(codeResponse.getMSG());
                                loginObtainAuthcodeTv.setClickable(true);
                                if (codeResponse.getSTATUS().equals("0")) {
                                    // 发送验证吗成功
                                    UIUtils.showToast("验证码发送成功");
                                    TimerCode timer = new TimerCode(60000, 1000, loginObtainAuthcodeTv);
                                    timer.start();
                                }
                            }
                        });
                break;
            case R.id.activity_login_register_tv://注册
                startActivityForResult(new Intent(this, RegisterActivity.class), 101);
                break;
            case R.id.activity_login_tv_forgetpwd://忘记密码
                startActivityForResult(new Intent(this, RegisterActivity.class).putExtra(RegisterActivity.TAG, 1), 101);
                break;
            case R.id.activity_wechat_login_tv://微信登录
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
        }
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
            String uid = data.get("uid");
            String accessToken = data.get("accessToken");
            ApiRetrofit.getInstance().getApiService().getWeChatLogin(accessToken,uid,SystemUtils.getVersion(LoginActivity.this))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<UserInfoResponse>() {

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
                        public void onNext(UserInfoResponse userInfo) {
                            if (userInfo.getSTATUS().equals("0")) {
                                MyApp.isLogout = false;
                                UseParams useParams = new Gson().fromJson(userInfo.getOBJECT().toString(), UseParams.class);
                                Config.cacheSessionId(LoginActivity.this, useParams.getSessionId());
                                Config.cacheUserId(LoginActivity.this, useParams.getUserId());
                                Config.cachePayStatus(LoginActivity.this, useParams.getUserPayStats());
                                Config.cacheUserCode(LoginActivity.this, useParams.getUserCode());
                                Config.cachePhoneNum(LoginActivity.this,useParams.getUserTel());
                                Config.cacheWeChatOpenId(LoginActivity.this,uid);
                                finish();
                            } else if("5".equals(userInfo.getSTATUS())){
                                startActivityForResult(new Intent(LoginActivity.this,BindPhoneActivity.class)
                                        .putExtra("uid",uid),REQUEST_CODE_BINDPHONE);
                            }
                        }
                    });

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
        if (requestCode == 101 && resultCode == RESULT_OK) {
            int value = data.getIntExtra(RegisterActivity.TAG, 0);
            if (value == 1) {
                finish();
            }
        }else if(requestCode == REQUEST_CODE_BINDPHONE){
            UIUtils.showToast("绑定手机登录成功");
            finish();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.activity_login_rb_account:
                loginAccountRb.setChecked(true);
                loginAuthcodeRb.setChecked(false);
                break;
            case R.id.activity_login_rb_authcode:
                loginAuthcodeRb.setChecked(true);
                loginAccountRb.setChecked(false);
                break;
        }
    }
}

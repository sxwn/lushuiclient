package com.lushuitv.yewuds.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.app.MyApp;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.flyn.Eyes;
import com.lushuitv.yewuds.module.entity.UseParams;
import com.lushuitv.yewuds.module.response.AuthCodeResponse;
import com.lushuitv.yewuds.module.response.UserInfoResponse;
import com.lushuitv.yewuds.utils.KeyBoardUtils;
import com.lushuitv.yewuds.utils.SystemUtils;
import com.lushuitv.yewuds.utils.UIUtils;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description
 * Created by weip
 * Date on 2017/11/1.
 */

public class LoginTranslucentActivtiy extends Activity implements View.OnClickListener {

    private EditText userName, userAuthcode;

    private TextView obtainAuthCode, loginCommit;

    private ImageView loginClose;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translucent_login);
        Eyes.translucentStatusBar(this, true);
        Drawable phoneDrawable = getResources().getDrawable(R.drawable.login_phone_icon, null);
        phoneDrawable.setBounds(0, 0, UIUtils.dip2Px(this, 13), UIUtils.dip2Px(this, 20));
        Drawable authcodeDrawable = getResources().getDrawable(R.drawable.login_locked_icon, null);
        authcodeDrawable.setBounds(0, 0, UIUtils.dip2Px(this, 16), UIUtils.dip2Px(this, 20));
        userName = (EditText) findViewById(R.id.activity_login_username_et);
        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //if (s.toString().length() == 11)
                    //KeyBoardUtils.hideSoftInput(LoginTranslucentActivtiy.this);
            }
        });
        userName.setCompoundDrawables(phoneDrawable, null, null, null);
        userAuthcode = (EditText) findViewById(R.id.activity_login_userauthcode_et);
        userAuthcode.setCompoundDrawables(authcodeDrawable, null, null, null);
        obtainAuthCode = (TextView) findViewById(R.id.activity_login_obtain_authcode);
        obtainAuthCode.setOnClickListener(this);
        userAuthcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (s.toString().length() == 6)
//                    KeyBoardUtils.hideSoftInput(LoginTranslucentActivtiy.this);
            }
        });
        loginCommit = (TextView) findViewById(R.id.activity_login_commit);
        loginCommit.setOnClickListener(this);
        loginClose = (ImageView) findViewById(R.id.activity_login_close);
        loginClose.setOnClickListener(this);
        userAuthcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_login_obtain_authcode://获取验证码
                if (TextUtils.isEmpty(userName.getText().toString())) {
                    UIUtils.showToast("手机号不能为空");
                    return;
                }
                if (!SystemUtils.isMobileNO(userName.getText().toString())) {
                    UIUtils.showToast("手机号不合法");
                    return;
                }
                obtainAuthCode.setClickable(false);
                ApiRetrofit.getInstance().getApiService().getPhoneCode(userName.getText().toString(), 9).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<AuthCodeResponse>() {


                            @Override
                            public void onError(Throwable e) {
                                KLog.e(e.getLocalizedMessage());
                                obtainAuthCode.setClickable(true);
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
                                if (codeResponse.getSTATUS().equals("0")) {
                                    // 发送验证吗成功
                                    UIUtils.showToast("验证码发送成功");
                                    TimerCount timer = new TimerCount(60000, 1000, obtainAuthCode);
                                    timer.start();
                                } else {
                                    obtainAuthCode.setClickable(true);
                                }
                            }
                        });
                break;
            case R.id.activity_login_commit://提交注册登录
                if (userName.getText().toString().length() == 0 && TextUtils.isEmpty(userName.getText().toString())) {
                    UIUtils.showToast("请输入手机号");
                    return;
                }
                if (!SystemUtils.isMobileNO(userName.getText().toString())) {
                    UIUtils.showToast("手机号不合法");
                    return;
                }
                if (TextUtils.isEmpty(userAuthcode.getText().toString())) {
                    UIUtils.showToast("请输入验证码");
                    return;
                }
                String randomStr =SystemUtils.getUuid();
                KLog.e("randomStr:" + randomStr);
                KLog.e("userName:" + userName.getText().toString());
                KLog.e("obtainAuthCode:" + obtainAuthCode.getText().toString());
                Map<String,Object> map = new HashMap<>();
                map.put("userTel",userName.getText().toString());
                map.put("veryCode", userAuthcode.getText().toString());
                map.put("appNum",randomStr);
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
                                    Config.cacheSessionId(LoginTranslucentActivtiy.this, useParams.getSessionId());
                                    Config.cachePhoneNum(LoginTranslucentActivtiy.this, useParams.getUserTel());
                                    Config.cachePwd(LoginTranslucentActivtiy.this, randomStr);
                                    Config.cacheUserId(LoginTranslucentActivtiy.this, useParams.getUserId());
                                    Config.cachePayStatus(LoginTranslucentActivtiy.this, useParams.getUserPayStats());
                                    Config.cacheUserCode(LoginTranslucentActivtiy.this, useParams.getUserCode());
                                    finish();
                                } else {
                                    UIUtils.showToast(userInfo.getMSG());
                                }
                            }
                        });
                break;
            case R.id.activity_login_close:
                finish();
                break;
        }
    }


    public class TimerCount extends CountDownTimer {
        private TextView btn;

        public TimerCount(long millisInFuture, long countDownInterval, TextView btn) {
            super(millisInFuture, countDownInterval);
            this.btn = btn;
        }

        public TimerCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            btn.setClickable(true);
            btn.setText("获取验证码");
        }

        @Override
        public void onTick(long arg0) {
            btn.setClickable(false);
            btn.setText(arg0 / 1000 + "秒后重新获取");
        }
    }
}

package com.lushuitv.yewuds.user;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.app.MyApp;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.module.entity.UseParams;
import com.lushuitv.yewuds.module.response.AuthCodeResponse;
import com.lushuitv.yewuds.module.response.UserInfoResponse;
import com.lushuitv.yewuds.utils.SystemUtils;
import com.lushuitv.yewuds.utils.UIUtils;
import com.socks.library.KLog;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 注册
 * Created by weip on 2017\12\4 0005.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = "success";
    private EditText phoneEt, authCodeEt, pwdEt;
    private TextView obtainCodeTv,registerTv;
    private RelativeLayout registerRl;

    private Intent intent;
    private View mView;
    private int intentValue;

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.activity_register, null);
        return mView;
    }

    @Override
    public void initToolbar() {
        appBarLayout.setVisibility(View.GONE);
    }

    @Override
    public void initOptions() {
        intent = getIntent();
        intentValue = intent.getIntExtra(TAG, 0);
        Drawable phoneDrawable = getResources().getDrawable(R.drawable.login_phone_icon);
        phoneDrawable.setBounds(0, 0, UIUtils.dip2Px(this, 13), UIUtils.dip2Px(this, 20));
        Drawable lockedPwdDrawable = getResources().getDrawable(R.drawable.login_locked_icon);
        lockedPwdDrawable.setBounds(0, 0, UIUtils.dip2Px(this, 16), UIUtils.dip2Px(this, 20));
        Drawable authcodeDrawable = getResources().getDrawable(R.drawable.login_authcode_icon);
        authcodeDrawable.setBounds(0, 0, UIUtils.dip2Px(this, 20), UIUtils.dip2Px(this, 14));
        findViewById(R.id.activity_back).setOnClickListener(this);
        phoneEt = (EditText) findViewById(R.id.activity_register_phone_et);
        TextChange tc = new TextChange();
        phoneEt.addTextChangedListener(tc);
        phoneEt.setCompoundDrawables(phoneDrawable, null, null, null);
        authCodeEt = (EditText) findViewById(R.id.activity_register_authcode_et);
        authCodeEt.addTextChangedListener(tc);
        authCodeEt.setCompoundDrawables(lockedPwdDrawable, null, null, null);
        obtainCodeTv = (TextView) findViewById(R.id.activity_register_obtaincode_tv);
        obtainCodeTv.setOnClickListener(this);
        pwdEt = (EditText) findViewById(R.id.activity_register_pwd_et);
        pwdEt.addTextChangedListener(tc);
        pwdEt.setCompoundDrawables(authcodeDrawable, null, null, null);
        registerRl = (RelativeLayout) findViewById(R.id.activity_register_rl);
        registerRl.setOnClickListener(this);
        registerTv = (TextView) findViewById(R.id.activity_register_tv);
        if (intentValue == 1){
            registerTv.setText("提交");
        }else{
            registerTv.setText("注册");
        }
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
            if (phoneEt.getText().length() > 0 && authCodeEt.getText().length() > 0 && pwdEt.getText().length() > 0) {
                registerRl.setBackgroundResource(R.color.user_login_text_color);
            } else {
                registerRl.setBackgroundResource(R.color.user_login_text_hint_color);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_back://返回
                finish();
                break;
            case R.id.activity_register_obtaincode_tv://获取验证码
                if (TextUtils.isEmpty(phoneEt.getText().toString())) {
                    UIUtils.showToast("手机号不能为空");
                    return;
                }
                if (!SystemUtils.isMobileNO(phoneEt.getText().toString())) {
                    UIUtils.showToast("手机号不合法");
                    return;
                }
                obtainCodeTv.setClickable(false);
                ApiRetrofit.getInstance().getApiService().getPhoneCode(phoneEt.getText().toString(), intentValue).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<AuthCodeResponse>() {

                            @Override
                            public void onError(Throwable e) {
                                KLog.e(e.getLocalizedMessage());
                                obtainCodeTv.setClickable(true);
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
                                obtainCodeTv.setClickable(true);
                                if (codeResponse.getSTATUS().equals("0")) {
                                    // 发送验证吗成功
                                    UIUtils.showToast("验证码发送成功");
                                    TimerCode timer = new TimerCode(60000, 1000, obtainCodeTv);
                                    timer.start();
                                }
                            }
                        });
                break;
            case R.id.activity_register_rl://注册
                if (TextUtils.isEmpty(phoneEt.getText().toString())) {
                    UIUtils.showToast("手机号不能为空");
                    return;
                }
                if (!SystemUtils.isMobileNO(phoneEt.getText().toString())) {
                    UIUtils.showToast("手机号不合法");
                    return;
                }
                if (TextUtils.isEmpty(authCodeEt.getText().toString())) {
                    UIUtils.showToast("验证码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(pwdEt.getText().toString())) {
                    UIUtils.showToast("密码不能为空");
                    return;
                }
                if (intentValue == 0){
                    ApiRetrofit.getInstance().getApiService().getRegister(phoneEt.getText().toString(),
                            authCodeEt.getText().toString(),
                            pwdEt.getText().toString(),
                            SystemUtils.getVersion(this),
                            SystemUtils.getDeviceBrand())
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
                                        Config.cacheSessionId(RegisterActivity.this, useParams.getSessionId());
                                        Config.cachePhoneNum(RegisterActivity.this, useParams.getUserTel());
                                        //Config.cachePwd(RegisterActivity.this, pwdEt.getText().toString());
                                        Config.cacheUserId(RegisterActivity.this, useParams.getUserId());
                                        Config.cachePayStatus(RegisterActivity.this, useParams.getUserPayStats());
                                        Config.cacheUserCode(RegisterActivity.this, useParams.getUserCode());
                                        intent.putExtra(TAG, 1);
                                        setResult(RESULT_OK, intent);
                                        finish();
                                    } else {
                                        UIUtils.showToast(userInfo.getMSG());
                                    }
                                }
                            });
                    break;
                }else if(intentValue == 1){
                    ApiRetrofit.getInstance().getApiService().getFindPwd(phoneEt.getText().toString(),
                            authCodeEt.getText().toString(),
                            pwdEt.getText().toString())
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
                                        intent.putExtra(RegisterActivity.TAG,2);
                                        setResult(RESULT_OK, intent);
                                        finish();
                                    } else {
                                        UIUtils.showToast(userInfo.getMSG());
                                    }
                                }
                            });
                }
        }
    }
}

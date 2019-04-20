package com.lushuitv.yewuds.user;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.app.MyApp;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.flyn.Eyes;
import com.lushuitv.yewuds.module.entity.UseParams;
import com.lushuitv.yewuds.module.response.AuthCodeResponse;
import com.lushuitv.yewuds.module.response.UserInfoResponse;
import com.lushuitv.yewuds.utils.SystemUtils;
import com.lushuitv.yewuds.utils.UIUtils;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 绑定手机
 * Created by weip on 2017\12\28 0028.
 */

public class BindPhoneActivity extends BaseActivity implements View.OnClickListener {

    private View mView;
    private EditText phoneEt, authcodeEt;
    private TextView obtainAuthcode, loginTv;

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.activity_bind_phone, null);
        return mView;
    }

    @Override
    public void initToolbar() {
        mToolbar.setBackgroundColor(getResources().getColor(R.color.white));
        mToolbar.setNavigationIcon(R.drawable.back_black);
        mToolbarTitle.setTextColor(getResources().getColor(R.color.black));
        mToolbarTitle.setText("绑定手机号");
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

    private String openId;
    private Intent intent;
    @Override
    protected void initOptions() {
        intent = getIntent();
        openId = intent.getStringExtra("uid");
        phoneEt = (EditText) mView.findViewById(R.id.bind_phone_et);
        authcodeEt = (EditText) mView.findViewById(R.id.bind_phone_authcode_et);
        obtainAuthcode = (TextView) mView.findViewById(R.id.bind_phone_obtain_authcode);
        obtainAuthcode.setOnClickListener(this);
        loginTv = (TextView) mView.findViewById(R.id.activity_login_rl);
        loginTv.setOnClickListener(this);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bind_phone_obtain_authcode://获取验证码
                if (TextUtils.isEmpty(phoneEt.getText().toString())) {
                    UIUtils.showToast("手机号不能为空");
                    return;
                }
                if (!SystemUtils.isMobileNO(phoneEt.getText().toString())) {
                    UIUtils.showToast("手机号不合法");
                    return;
                }
                obtainAuthcode.setClickable(false);
                ApiRetrofit.getInstance().getApiService().getPhoneCode(phoneEt.getText().toString(), 9).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<AuthCodeResponse>() {

                            @Override
                            public void onError(Throwable e) {
                                KLog.e(e.getLocalizedMessage());
                                obtainAuthcode.setClickable(true);
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
                                obtainAuthcode.setClickable(true);
                                if (codeResponse.getSTATUS().equals("0")) {
                                    // 发送验证吗成功
                                    UIUtils.showToast("验证码发送成功");
                                    TimerCode timer = new TimerCode(60000, 1000, obtainAuthcode);
                                    timer.start();
                                }
                            }
                        });
                break;
            case R.id.activity_login_rl://登录
                if (TextUtils.isEmpty(phoneEt.getText().toString())) {
                    UIUtils.showToast("手机号不能为空");
                    return;
                }
                if (!SystemUtils.isMobileNO(phoneEt.getText().toString())) {
                    UIUtils.showToast("手机号不合法");
                    return;
                }
                if (TextUtils.isEmpty(authcodeEt.getText().toString())) {
                    UIUtils.showToast("验证码不能为空");
                    return;
                }
                Map<String,Object> map = new HashMap<>();
                map.put("userTel",phoneEt.getText().toString());
                map.put("veryCode",authcodeEt.getText().toString());
                map.put("type",String.valueOf(2));
                map.put("version",SystemUtils.getVersion(this));
                map.put("model",SystemUtils.getDeviceBrand());
                if (openId!= null && !TextUtils.isEmpty(openId)){
                    map.put("userWechatOpenid",openId);
                }
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
                                KLog.e("登录返回的信息:"+userInfo.getOBJECT());
                                if (userInfo.getSTATUS().equals("0")) {
                                    MyApp.isLogout = false;
                                    UseParams useParams = new Gson().fromJson(userInfo.getOBJECT().toString(), UseParams.class);
                                    Config.cacheSessionId(BindPhoneActivity.this, useParams.getSessionId());
                                    Config.cacheUserId(BindPhoneActivity.this, useParams.getUserId());
                                    Config.cachePayStatus(BindPhoneActivity.this, useParams.getUserPayStats());
                                    Config.cacheUserCode(BindPhoneActivity.this, useParams.getUserCode());
                                    Config.cachePhoneNum(BindPhoneActivity.this,useParams.getUserTel());
                                    Config.cacheWeChatOpenId(BindPhoneActivity.this,openId);
                                    setResult(RESULT_OK,intent);
                                    finish();
                                } else {
                                    UIUtils.showToast(userInfo.getMSG());
                                }
                            }
                        });
                break;
        }
    }
}

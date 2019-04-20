package com.lushuitv.yewuds.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.module.entity.HttpBean;
import com.lushuitv.yewuds.utils.UIUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description
 * Created by weip
 * Date on 2017/9/24.
 */

public class BindAlipayActivity extends BaseActivity implements View.OnClickListener {

    public static final String CODE = "add";

    private View mView;

    ImageView backView;

    TextView tvTitle, sendCode;
    EditText alipayEt, nameEt, codeEt;
    RelativeLayout commitAlipay;
    int codeFlag;
    Intent in;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.bing_alipay_rl_commit://提交
                if (alipayEt.getText().toString().length() == 0 || TextUtils.isEmpty(alipayEt.getText().toString())) {
                    UIUtils.showToast("请输入支付宝账号");
                    return;
                }
                if (nameEt.getText().toString().length() == 0 || TextUtils.isEmpty(nameEt.getText().toString())) {
                    UIUtils.showToast("请输入真姓名");
                    return;
                }
                ApiRetrofit.getInstance().getApiService().getAddUserBank(alipayEt.getText().toString(), 8, nameEt.getText().toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<HttpBean>() {

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
                            public void onNext(HttpBean httpBean) {
                                if ("0".equals(httpBean.getSTATUS())) {
                                    setResult(RESULT_OK, in);
                                    finish();
                                }
                            }
                        });
                break;
        }
    }

    @Override
    protected void initOptions() {
        in = getIntent();
        codeFlag = in.getIntExtra(CODE, 0);
        alipayEt = (EditText) findViewById(R.id.bing_alipay_et);
        nameEt = (EditText) findViewById(R.id.bing_alipay_truename_et);
//        codeEt = (EditText) findViewById(R.id.bing_alipay_authcode_et);
//        sendCode = (TextView) findViewById(R.id.bing_alipay_send_authcode);
//        sendCode.setOnClickListener(this);
        commitAlipay = (RelativeLayout) findViewById(R.id.bing_alipay_rl_commit);
        commitAlipay.setOnClickListener(this);
    }

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.bind_alipay_layout, null);
        return mView;
    }
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    @Override
    public void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbar.setTitle(getResources().getString(R.string.pay_tixian));
        setSupportActionBar(mToolbar);
        //设置返回键可用
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

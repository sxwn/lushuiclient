package com.lushuitv.yewuds.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.flyn.Eyes;
import com.lushuitv.yewuds.module.response.BeenPayResult;
import com.lushuitv.yewuds.utils.UIUtils;
import com.socks.library.KLog;

import cn.beecloud.BCPay;
import cn.beecloud.async.BCCallback;
import cn.beecloud.async.BCResult;
import cn.beecloud.entity.BCPayResult;
import cn.beecloud.entity.BCReqParams;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Description
 * Created by weip
 * Date on 2017/9/6.
 */

public class PayVideoActivity extends BaseActivity implements View.OnClickListener {

    TextView payMoney;

//    CheckBox cbMoney;

    CheckBox cbWechat;

    CheckBox cbAlipay;

//    RelativeLayout moneyRl;

    RelativeLayout wechatRl;

    RelativeLayout alipayRl;

    RelativeLayout payRl;

    Intent intent = null;
    int workId;

    private View mView;

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.activity_pay_video, null);
        return mView;
    }

    TextView payTv;

    @Override
    public void initToolbar() {
        mToolbar.setBackgroundColor(getResources().getColor(R.color.white));
        mToolbar.setNavigationIcon(R.drawable.back_black);
        mToolbarTitle.setTextColor(getResources().getColor(R.color.black));
        mToolbarTitle.setText("支付");
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
    @Override
    public void initOptions() {
        //初始化微信支付，必须在需要调起微信支付的Activity的onCreate函数中调用
        BCPay.initWechatPay(this, "wx22f8942fab7c79ef");
        payMoney = (TextView) findViewById(R.id.pay_video_money);
//        cbMoney = (CheckBox) findViewById(R.id.pay_video_cb_money);
        cbWechat = (CheckBox) findViewById(R.id.pay_video_cb_wechat);
        cbAlipay = (CheckBox) findViewById(R.id.pay_video_cb_alipay);
//        moneyRl = (RelativeLayout) findViewById(R.id.pay_video_money_rl);
        wechatRl = (RelativeLayout) findViewById(R.id.pay_video_money_wechat_rl);
        alipayRl = (RelativeLayout) findViewById(R.id.pay_video_money_alipay_rl);
        wechatRl.setOnClickListener(this);
        alipayRl.setOnClickListener(this);
        payRl = (RelativeLayout) findViewById(R.id.activity_pay_video_rl);
        payRl.setOnClickListener(this);
        intent = getIntent();
        Bundle bundle = intent.getBundleExtra("id_bundle");
        workId = bundle.getInt("workId");
        workprice = bundle.getInt("workprice");
        payMoney.setText("" + workprice);
        payTv = (TextView) findViewById(R.id.activity_pay_video_tv);
        payTv.setText("确认支付" + workprice + "元");
    }

    int workprice;
    public static final int WECHAT_PAY_CODE = 0;//微信支付
    public static final int ALIPAY_CODE = 1;//支付宝支付

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
//            case R.id.pay_video_money_rl:
//                cbMoney.setChecked(true);
//                cbWechat.setChecked(false);
//                cbAlipay.setChecked(false);
//                break;
            case R.id.pay_video_money_wechat_rl:
//                cbMoney.setChecked(false);
                cbWechat.setChecked(true);
                cbAlipay.setChecked(false);
                break;
            case R.id.pay_video_money_alipay_rl:
//                cbMoney.setChecked(false);
                cbWechat.setChecked(false);
                cbAlipay.setChecked(true);
                break;
            case R.id.activity_pay_video_rl:
                mLoading.setVisibility(View.VISIBLE);
                mLoading.smoothToShow();
                if (cbWechat.isChecked()) {
                    loadPay(WECHAT_PAY_CODE);
                } else if (cbAlipay.isChecked()) {
                    loadPay(ALIPAY_CODE);
                }
                break;
        }
    }

    private void loadPay(int code) {
        ApiRetrofit.getInstance().getApiService().getBeeCloudPay(0, workprice * 100, workId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BeenPayResult>() {
                    @Override
                    public void onError(Throwable e) {
                        UIUtils.showToast("调取支付异常");
                        mLoading.smoothToHide();
                    }

                    @Override
                    public void onComplete() {
                        mLoading.smoothToHide();
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        mLoading.smoothToHide();
                    }

                    @Override
                    public void onNext(BeenPayResult beenPayResult) {
                        mLoading.smoothToHide();
                        if (beenPayResult.getSTATUS().equals("0"))
                            payBeenAlipayPay(code, beenPayResult.getOBJECT().getOutTradeNo());
                        else
                            UIUtils.showToast("数据异常");
                    }
                });
    }

    /**
     * 调起支付
     */
    private void payBeenAlipayPay(int code, String orderNum) {
        BCPay.PayParams payParam = new BCPay.PayParams();
        if (code == WECHAT_PAY_CODE) {
            payParam.channelType = BCReqParams.BCChannelTypes.WX_APP;//微信
        } else if (code == ALIPAY_CODE) {
            payParam.channelType = BCReqParams.BCChannelTypes.ALI_APP;//支付宝
        }
        //商品描述
        payParam.billTitle = "露水TV";
        //支付金额，以分为单位，必须是正整数
        payParam.billTotalFee = workprice * 100;
        //商户自定义订单号
        payParam.billNum = orderNum;
        // 第二个参数实现BCCallback接口，在done方法中查看支付结果
        BCPay.getInstance(this).reqPaymentAsync(payParam, new BCCallback() {
            @Override
            public void done(BCResult result) {
                if (((BCPayResult) result).getErrCode() == 0) {
                    KLog.e(result.toString());
                    setResult(RESULT_OK, intent);
                    intent.putExtra("isBuy", true);
                    finish();
                } else {
                }
            }
        });
//        if (code == WECHAT_PAY_CODE){
//           BCPay.getInstance(this).reqWXPaymentAsync("露水TV", 1, orderNum, null, new BCCallback() {
//               @Override
//               public void done(BCResult result) {
//                   UIUtils.showToast("支付结果:"+result.toString());
//                   finish();
//               }
//           });
//        }else if(code == ALIPAY_CODE){
//            payParam.channelType = BCReqParams.BCChannelTypes.ALI_APP;//支付宝
//        }
    }
}

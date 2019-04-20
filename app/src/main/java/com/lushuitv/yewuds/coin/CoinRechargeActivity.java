package com.lushuitv.yewuds.coin;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.app.ApiConstant;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.detail.RulePageActivity;
import com.lushuitv.yewuds.flyn.Eyes;
import com.lushuitv.yewuds.module.response.BeenPayResult;
import com.lushuitv.yewuds.utils.UIUtils;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import cn.beecloud.BCPay;
import cn.beecloud.async.BCCallback;
import cn.beecloud.async.BCResult;
import cn.beecloud.entity.BCPayResult;
import cn.beecloud.entity.BCReqParams;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.lushuitv.yewuds.activity.PayVideoActivity.ALIPAY_CODE;
import static com.lushuitv.yewuds.activity.PayVideoActivity.WECHAT_PAY_CODE;

/**
 * Created by weip on 2017\12\18 0018.
 */

public class CoinRechargeActivity extends BaseActivity implements View.OnClickListener {

    private View mView;
    private RecyclerView mRecycleView;
    private TextView wechatTv, alipayTv;
    private CheckBox wechatCb, alipayCb;
    private RelativeLayout wechatRl, alipayRl;
    private TextView payMoney;

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.activity_coin_recharge, null);
        return mView;
    }

    @Override
    public void initToolbar() {
        mToolbar.setBackgroundColor(getResources().getColor(R.color.white));
        mToolbar.setNavigationIcon(R.drawable.back_black);
        mToolbarTitle.setText("金币充值");
        mToolbarTitle.setTextColor(getResources().getColor(R.color.black));
        mToolbarRightTitle.setText("充值记录");
        mToolbarRightTitle.setTextColor(getResources().getColor(R.color.black));
        mToolbarRightTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CoinRechargeActivity.this, CoinRechargeRecord.class));
            }
        });
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

    private List<CoinResponse.OBJECTBean.ListBean> mData;
    private CoinRechargeAdapter myAdapter;
    private int id, price;

    @Override
    protected void initOptions() {
        mData = new ArrayList<CoinResponse.OBJECTBean.ListBean>();
        mRecycleView = (RecyclerView) findViewById(R.id.coin_recharge_recycleview);
        myAdapter = new CoinRechargeAdapter(this, mData, 2);
        mRecycleView.setLayoutManager(new GridLayoutManager(this, 3));
        //设置增加或删除条目动画
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(myAdapter);//设置适配器
        initdata();
        payMoney = (TextView) mView.findViewById(R.id.coin_recharge_paymoney);
        payMoney.setText("支付金额:18元");
        myAdapter.setCoinItemClickListener(new CoinRechargeAdapter.CoinItemClickListener() {
            @Override
            public void onItemClick(int position, View view, TextView coinView, TextView rmbView) {
                myAdapter.notifyDataSetChanged();
                payMoney.setText("支付金额:" + mData.get(position).getPrice() + "元");
                price = mData.get(position).getPrice();
                id = mData.get(position).getId();
            }
        });
        Drawable wechatDrawable = getResources().getDrawable(R.drawable.vip_pay_wechat);
        wechatDrawable.setBounds(0, 0, UIUtils.dip2Px(20), UIUtils.dip2Px(20));
        ;
        Drawable alipayDrawable = getResources().getDrawable(R.drawable.vip_pay_alipay);
        alipayDrawable.setBounds(0, 0, UIUtils.dip2Px(20), UIUtils.dip2Px(16));
        ;
        wechatTv = (TextView) mView.findViewById(R.id.vip_pay_wechat_tv);
        wechatTv.setCompoundDrawables(wechatDrawable, null, null, null);
        alipayTv = (TextView) mView.findViewById(R.id.vip_pay_alipay_tv);
        alipayTv.setCompoundDrawables(alipayDrawable, null, null, null);
        wechatCb = (CheckBox) mView.findViewById(R.id.vip_pay_cb_wechat);
        alipayCb = (CheckBox) mView.findViewById(R.id.vip_pay_cb_alipay);
        wechatRl = (RelativeLayout) mView.findViewById(R.id.vip_pay_wechat_rl);
        wechatRl.setOnClickListener(this);
        alipayRl = (RelativeLayout) mView.findViewById(R.id.vip_pay_alipay_rl);
        alipayRl.setOnClickListener(this);
        ((TextView) mView.findViewById(R.id.coin_recharge_userule)).getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        ((TextView) mView.findViewById(R.id.coin_recharge_userule)).setOnClickListener(this);
        mView.findViewById(R.id.coin_recharge_pay).setOnClickListener(this);//确定支付
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

    private void initdata() {
        ApiRetrofit.getInstance().getApiService().getCoinBuyList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CoinResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CoinResponse response) {
                        if ("0".equals(response.getSTATUS())) {
                            List<CoinResponse.OBJECTBean.ListBean> objectBeans = response.getOBJECT().getList();
                            mData = response.getOBJECT().getList();
                            myAdapter.setData(objectBeans);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vip_pay_wechat_rl://微信支付布局
                wechatCb.setChecked(true);
                alipayCb.setChecked(false);
                break;
            case R.id.vip_pay_alipay_rl://支付宝支付布局
                wechatCb.setChecked(false);
                alipayCb.setChecked(true);
                break;
            case R.id.coin_recharge_userule://用户金币协议
                startActivity(new Intent(this, RulePageActivity.class)
                        .putExtra("url", ApiConstant.BASE_SERVER_H5_URL + "h5/rule/gold_make_rule.html"));
                break;
            case R.id.coin_recharge_pay://确定支付
                if (wechatCb.isChecked()) {
                    UIUtils.showToast("调起微信支付");
                    loadPay(WECHAT_PAY_CODE);
                } else if (alipayCb.isChecked()) {
                    UIUtils.showToast("调起支付宝支付");
                    loadPay(ALIPAY_CODE);
                }
                break;
        }
    }

    private void loadPay(int code) {
        ApiRetrofit.getInstance().getApiService().getBeeCloudPayGold(code, 1, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BeenPayResult>() {
                    @Override
                    public void onError(Throwable e) {
                        UIUtils.showToast("调取支付异常");
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
                        if (beenPayResult.getSTATUS().equals("0"))
                            payBeenAlipayPay(code, beenPayResult.getOBJECT().getOutTradeNo());
                        else
                            UIUtils.showToast("数据异常");
                    }
                });
    }

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
        payParam.billTotalFee = 1;
        //商户自定义订单号
        payParam.billNum = orderNum;
        // 第二个参数实现BCCallback接口，在done方法中查看支付结果
        BCPay.getInstance(this).reqPaymentAsync(payParam, new BCCallback() {
            @Override
            public void done(BCResult result) {
                KLog.e("code:"+((BCPayResult) result).getErrCode());
                if (((BCPayResult) result).getErrCode() == 0) {
                    UIUtils.showToast("支付成功");
                    finish();
                } else {
                }
            }
        });
    }
}




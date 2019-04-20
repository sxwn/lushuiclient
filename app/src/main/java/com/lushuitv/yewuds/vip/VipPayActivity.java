package com.lushuitv.yewuds.vip;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.coin.CoinCashPayPwdSetActivity;
import com.lushuitv.yewuds.coin.CoinResponse;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.flyn.Eyes;
import com.lushuitv.yewuds.module.entity.HttpBean;
import com.lushuitv.yewuds.module.entity.WorksListBean;
import com.lushuitv.yewuds.utils.SystemUtils;
import com.lushuitv.yewuds.utils.UIUtils;
import com.lushuitv.yewuds.view.PwdInputDialog;
import com.lushuitv.yewuds.view.gridpasswordview.GridPasswordView;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by weip on 2017\12\13 0013.
 */

public class VipPayActivity extends BaseActivity implements View.OnClickListener {
    private View mView;
    private TextView orderCoin, coinIncomeAccount, coinChargeAccount, orderTitle, orderCommit;
    private CheckBox chargeRb, incomeRb;
    private RelativeLayout chargeRbRl, incomeRbRl;

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.activity_mine_vip_pay, null);
        return mView;
    }

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
    private Bundle bundle;
    private Bundle viptaocanBundle;//套餐bundle
    private VipBuyListResponse.OBJECTBean.BuyListBean taocanBean;
    private int index;//下标
    private WorksListBean worksListBean;
    private Intent intent;
    private int actorId;
    private String actorName;
    private int coinNumber;
    @Override
    public void initOptions() {
        intent = getIntent();
        if (intent!=null){
            actorId = intent.getIntExtra("actorId",0);
            actorName = intent.getStringExtra("actor");
            coinNumber = intent.getIntExtra("coinNumber",0);
            //获取会员套餐
            viptaocanBundle = intent.getBundleExtra("viptaocan_bundle");
            if (viptaocanBundle!=null){
                index = viptaocanBundle.getInt("index");
                taocanBean = (VipBuyListResponse.OBJECTBean.BuyListBean) viptaocanBundle.getSerializable("viptaocan");
            }
            //单独1、3、6元视频
            bundle = intent.getBundleExtra("id_bundle");
            if (bundle!=null){
                worksListBean = (WorksListBean) bundle.getSerializable("workListBean");
            }
        }
        orderCoin = (TextView) mView.findViewById(R.id.vip_pay_order_coin);
        coinChargeAccount = (TextView) mView.findViewById(R.id.vip_pay_charge_account);
        coinIncomeAccount = (TextView) mView.findViewById(R.id.vip_pay_income_account);
        chargeRbRl = (RelativeLayout) mView.findViewById(R.id.vip_pay_charge_account_rl);
        chargeRbRl.setOnClickListener(this);
        chargeRb = (CheckBox) mView.findViewById(R.id.vip_pay_charge_cb);
        incomeRbRl = (RelativeLayout) mView.findViewById(R.id.vip_pay_income_account_rl);
        incomeRbRl.setOnClickListener(this);
        incomeRb = (CheckBox) mView.findViewById(R.id.vip_pay_income_cb);
        orderTitle = (TextView) mView.findViewById(R.id.vip_pay_order_title);
        orderCommit = (TextView) mView.findViewById(R.id.vip_pay_commit);
        orderCommit.setOnClickListener(this);
        //购买会员信息
        if (taocanBean!=null){
            orderCoin.setText(taocanBean.getGold()+"");//支付金币数
            orderTitle.setText("开通/续费"+taocanBean.getMonth()+"个月会员");
        }
        if (bundle !=null){
            orderCoin.setText(""+worksListBean.getWorksPrice()*10);
            orderTitle.setText(worksListBean.getWorksName());
        }
        if (actorName != null){
            orderCoin.setText(""+coinNumber);
            orderTitle.setText("打赏-"+actorName);
        }
        Map<String, String> map = new HashMap<>();
        map.put("version", SystemUtils.getVersion(this));
        map.put("userId", Config.getCachedUserId(this));
        ApiRetrofit.getInstance().getApiService().getFindGold(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CoinResponse>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(CoinResponse response) {
                        if (response.getSTATUS().equals("0")) {
                            coinIncomeAccount.setText("收入账户\t(" + response.getOBJECT().getList().get(0).getGold() + ")");
                            coinChargeAccount.setText("充值账户\t(" + (response.getOBJECT().getList().get(1).getGold()
                                    + response.getOBJECT().getList().get(2).getGold()) + ")");
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
        menu.findItem(R.id.action_share).setVisible(false);
        menu.findItem(R.id.action_save).setVisible(false);
        menu.findItem(R.id.action_download).setVisible(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vip_pay_charge_account_rl://充值账户
                chargeRb.setChecked(true);
                incomeRb.setChecked(false);
                break;
            case R.id.vip_pay_income_account_rl://收益账户
                chargeRb.setChecked(false);
                incomeRb.setChecked(true);
                break;
            case R.id.vip_pay_commit://提交订单
                if (Config.getCachedPayStatus(this) == 1) {//设置支付密码
                    inputPwdDialog();
                } else {
                    startActivity(new Intent(this, CoinCashPayPwdSetActivity.class));
                }
                break;
        }
    }

    /**
     * 弹出密码输入框
     */
    private void inputPwdDialog() {
        //弹出密码框
        PwdInputDialog setPwdDialog = new PwdInputDialog(VipPayActivity.this);
        updateBg();
        setPwdDialog.setTitle("请输入支付密码");
        setPwdDialog.show();
        setPwdDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                recoverBg();
            }
        });
        setPwdDialog.getPwdView().setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {

            }

            @Override
            public void onInputFinish(String psw) {
                //判断输入完密码的请求
                commitOrder(psw);
            }
        });

    }

    private void commitOrder(String pwd) {
        Map<String, Object> map = new HashMap<>();
        map.put("version", SystemUtils.getVersion(this));
        map.put("userId", Config.getCachedUserId(this));
        if (chargeRb.isChecked()) {
            map.put("purseType", 2);//充值账户
        } else {
            map.put("purseType", 1);//收益账户
        }
        map.put("userPayPassWord", pwd);//支付密码
        if (taocanBean!=null){
            map.put("buyType", 2);//购买会员
        }
        if (index == 0) {
            map.put("chType", 4);//支付子类型 1 1元视频，2 3元视频 ，3 6元视频， 4月会员， 5 半年会员 6 年会员, 7 other(打赏)
        } else if (index == 1) {
            map.put("chType", 5);//支付子类型 1 1元视频，2 3元视频 ，3 6元视频， 4月会员， 5 半年会员 6 年会员, 7 other(打赏)
        } else if (index == 2) {
            map.put("chType", 6);//支付子类型 1 1元视频，2 3元视频 ，3 6元视频， 4月会员， 5 半年会员 6 年会员, 7 other(打赏)
        }

        if (worksListBean!=null){//金币买视频
            map.put("buyType", 1);//购买视频
            map.put("worksId", worksListBean.getWorksId());//作品id
            map.put("actorId",worksListBean.getWorksActor());//艺人id
            if (worksListBean.getWorksPrice() == 1){
                map.put("chType", 1);
            }else if(worksListBean.getWorksPrice() == 3){
                map.put("chType", 3);
            }else if(worksListBean.getWorksPrice() == 6){
                map.put("chType", 6);
            }
        }
        if (actorId!=0){
            map.put("actorId",actorId);
            map.put("chType", 7);//支付子类型 1->1元视频，2->3元视频,3->6元视频,4->月会员,5->半年会员,6->年会员,7->other(打赏)
            map.put("buyType",3);//支付类型   1购买视频，2购买会员、3 打赏 金额
        }
        int payGoldNumber = Integer.parseInt(orderCoin.getText().toString()) * 10;
        map.put("gold", payGoldNumber);//gold 金币数
        ApiRetrofit.getInstance().getApiService().getGoldCosumer(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpBean httpBean) {
                        if ("0".equals(httpBean.getSTATUS())) {
                            UIUtils.showToast("购买成功");
                            if (bundle != null){
                                setResult(RESULT_OK, intent);
                                intent.putExtra("isBuy", true);
                            }else{
                                setResult(RESULT_OK, intent);
                            }
                            finish();
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

    private void updateBg() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.6f;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    private void recoverBg() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1.0f;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
}

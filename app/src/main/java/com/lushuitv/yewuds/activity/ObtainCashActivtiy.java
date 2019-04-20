package com.lushuitv.yewuds.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lushuitv.yewuds.MainActivity;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.flyn.Eyes;
import com.lushuitv.yewuds.module.entity.HttpBean;
import com.lushuitv.yewuds.module.response.AccountInfoResponse;
import com.lushuitv.yewuds.utils.MD5Tool;
import com.lushuitv.yewuds.utils.UIUtils;
import com.lushuitv.yewuds.view.CustomDialog;
import com.lushuitv.yewuds.view.PwdInputDialog;
import com.lushuitv.yewuds.view.gridpasswordview.GridPasswordView;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description
 * Created by weip
 * Date on 2017/9/24.
 */

public class ObtainCashActivtiy extends BaseActivity implements View.OnClickListener {
    ImageView backView;
    TextView tvTitle;
    CheckBox alipayCb;
    RelativeLayout alipayRl, obtainCashRl;
    TextView alipayAccount;
    EditText moneyInputEt;
    private View mView;

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.obtain_cash_layout, null);
        return mView;
    }

    @Override
    public void initToolbar() {
        mToolbar.setBackgroundColor(getResources().getColor(R.color.white));
        mToolbar.setNavigationIcon(R.drawable.back_black);
        mToolbarTitle.setTextColor(getResources().getColor(R.color.black));
        mToolbarTitle.setText("提现");
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
    public void initOptions() {
        alipayAccount = (TextView) findViewById(R.id.obtain_cash_alipay_account);
        alipayCb = (CheckBox) findViewById(R.id.obtain_cash_cb_alipay);
        alipayRl = (RelativeLayout) findViewById(R.id.obtain_cash_alipay_rl);
        alipayRl.setOnClickListener(this);
        moneyInputEt = (EditText) findViewById(R.id.obtain_cash_monet_et);
        moneyInputEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                        moneyInputEt.setText(s);
                        moneyInputEt.setSelection(s.length());
                    }
                }

                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    moneyInputEt.setText(s);
                    moneyInputEt.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        moneyInputEt.setText(s.subSequence(0, 1));
                        moneyInputEt.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        obtainCashRl = (RelativeLayout) findViewById(R.id.obtain_cash_layout_rl);
        obtainCashRl.setOnClickListener(this);
        initData();
    }

    private CustomDialog customDialog;

    List<AccountInfoResponse.OBJECTBean.BanksBean> banks;
    Double withDrawMoney;

    public void initData() {
        ApiRetrofit.getInstance().getApiService().getQueryUserAccount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AccountInfoResponse>() {

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
                    public void onNext(AccountInfoResponse accountInfoResponse) {
                        if ("0".equals(accountInfoResponse.getSTATUS())) {
                            banks = accountInfoResponse.getOBJECT().getBanks();
                            withDrawMoney = Double.parseDouble(accountInfoResponse.getOBJECT().getWithDrawMoney());//可提现金额
                            if (banks != null) {
                                alipayAccount.setText("支付宝\t" + banks.get(0).getBankOwner());
                            } else {
                                alipayAccount.setText("支付宝");
                            }
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
            case R.id.iv_back:
                finish();
                break;
            case R.id.obtain_cash_alipay_rl:
                if (alipayCb.isChecked())
                    alipayCb.setChecked(false);
                else
                    alipayCb.setChecked(true);
                break;
            case R.id.obtain_cash_layout_rl://提现支付宝
                if (!alipayCb.isChecked()) {
                    UIUtils.showToast("请选择支付宝账号");
                    return;
                }
                if (TextUtils.isEmpty(moneyInputEt.getText().toString())) {
                    UIUtils.showToast("请输入提现金额");
                    return;
                }
                if (Double.parseDouble(moneyInputEt.getText().toString()) < 10) {
                    UIUtils.showToast("您的提现金额不能少于10元");
                    return;
                }
                if (Double.parseDouble(moneyInputEt.getText().toString()) > withDrawMoney) {
                    UIUtils.showToast("您的提现金额不能大于" + withDrawMoney);
                    return;
                }
                //1、先判断有没有绑定支付宝,如果绑定直接弹出支付密码框,从而进行提现
                if (banks != null) {
//                    isBuyVideo();
                    if (Config.getCachedPayStatus(ObtainCashActivtiy.this) == 2) {
                        //未设置过支付密码
                        setPwdDialog();
                    } else {
                        //输入支付密码
                        inputPwdDialog();
                    }
                } else {
                    customDialog = new CustomDialog(ObtainCashActivtiy.this);
                    updateBg();
                    customDialog.setMessage("您还没有绑定支付宝，无法提现");
                    customDialog.setYesOnclickListener("去绑定", new CustomDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            UIUtils.showToast("确定");
                            customDialog.dismiss();
                            Intent in = new Intent(ObtainCashActivtiy.this, BindAlipayActivity.class);
                            in.putExtra(BindAlipayActivity.CODE, 1);
                            startActivityForResult(in, 101);
                        }
                    });
                    customDialog.setNoOnclickListener("下次再说", new CustomDialog.onNoOnclickListener() {
                        @Override
                        public void onNoClick() {
                            UIUtils.showToast("取消");
                            customDialog.dismiss();
                        }
                    });
                    customDialog.show();
                    customDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            recoverBg();
                        }
                    });
                    break;
                }
        }
    }

    private void isBuyVideo() {
        //先判断有没有购买过正片
        ApiRetrofit.getInstance().getApiService().getUserWithDraw(banks.get(0).getBankId(),
                Double.parseDouble(moneyInputEt.getText().toString()))
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
                        if ("1".equals(httpBean.getSTATUS())) {
                            //您还没有付费观看正片，无法提现
                            customDialog = new CustomDialog(ObtainCashActivtiy.this);
                            updateBg();
                            customDialog.setMessage("您还没有付费看正片,无法提现");
                            customDialog.setYesOnclickListener("去选片", new CustomDialog.onYesOnclickListener() {
                                @Override
                                public void onYesClick() {
                                    customDialog.dismiss();
                                    startActivity(new Intent(ObtainCashActivtiy.this, MainActivity.class));
                                    finish();
                                }
                            });
                            customDialog.setNoOnclickListener("下次再说", new CustomDialog.onNoOnclickListener() {
                                @Override
                                public void onNoClick() {
                                    customDialog.dismiss();
                                }
                            });
                            customDialog.show();
                            customDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    recoverBg();
                                }
                            });
                        } else if ("0".equals(httpBean.getSTATUS())) {
                            //设置提现密码、如果设置了就直接输入提现密码
                            //先判断有没有设置过支付密码
                            if (Config.getCachedPayStatus(ObtainCashActivtiy.this) == 2) {
                                //未设置过支付密码
                                setPwdDialog();
                            } else {
                                //输入支付密码
                                inputPwdDialog();
                            }
                        }
                    }
                });
    }

    private void setPwdDialog() {
        //弹出密码框
        PwdInputDialog dialog = new PwdInputDialog(ObtainCashActivtiy.this);
        dialog.setMoney(Double.parseDouble(moneyInputEt.getText().toString()));
        dialog.setTitle("请设置支付密码");
        dialog.show();
        updateBg();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
//                recoverBg();
            }
        });
        dialog.getPwdView().setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {

            }

            @Override
            public void onInputFinish(String psw) {
                //调取设置密码接口
//                ApiRetrofit.getInstance().getApiService().getSetPayPassword(MD5Tool.md5(psw))
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Observer<HttpBean>() {
//
//                                       @Override
//                                       public void onError(Throwable e) {
//
//                                       }
//
//                                       @Override
//                                       public void onComplete() {
//
//                                       }
//
//                                       @Override
//                                       public void onSubscribe(Disposable d) {
//
//                                       }
//
//                                       @Override
//                                       public void onNext(HttpBean httpBean) {
//                                           if (dialog != null)
//                                               dialog.dismiss();
////                                           recoverBg();
//                                           if ("0".equals(httpBean.getSTATUS())) {
//                                               Config.cachePayStatus(ObtainCashActivtiy.this, 1);//设置过支付密码
//                                               inputPwdDialog();
//                                           } else {
//                                               UIUtils.showToast("数据异常");
//                                           }
//                                       }
//                                   }
//                        );
            }
        });
    }

    private void inputPwdDialog() {
        //弹出密码框
        PwdInputDialog setPwdDialog = new PwdInputDialog(ObtainCashActivtiy.this);
        updateBg();
        setPwdDialog.setMoney(Double.parseDouble(moneyInputEt.getText().toString()));
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
                getVerifyPwd(psw, setPwdDialog);
            }
        });
    }

    private void getVerifyPwd(String s, PwdInputDialog dialog) {
        ApiRetrofit.getInstance().getApiService().getVerifyPayPassword(MD5Tool.md5(s))
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
                        if (dialog != null) {
                            dialog.dismiss();
                            recoverBg();//恢复
                        }
                        if ("0".equals(httpBean.getSTATUS())) {
                            //调取提现接口
                            ApiRetrofit.getInstance().getApiService().getUserWithDraw(banks.get(0).getBankId(),
                                    Double.parseDouble(moneyInputEt.getText().toString()))
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
                                                UIUtils.showToast("提现成功");
                                                finish();
                                            } else if ("1".equals(httpBean.getSTATUS())) {
                                                //您还没有付费观看正片,无法提现
                                                //UIUtils.showToast(httpBean.getMSG());
                                                buyVideo();
                                            } else if ("33".equals(httpBean.getSTATUS())) {
                                                //您还没有绑定手机号,无法提现
                                                //UIUtils.showToast(httpBean.getMSG());
                                                //bindPhone();
                                            }
                                        }
                                    });
                        } else if ("11".equals(httpBean.getSTATUS())) {
                            //设置支付密码
                            setPwdDialog();
                        } else {
                            UIUtils.showToast(httpBean.getMSG());
                        }
                    }
                });
    }

    /**
     * 购买正片dialog
     */
    private void buyVideo() {
        CustomDialog customDialog = new CustomDialog(ObtainCashActivtiy.this);
        updateBg();
        customDialog.setMessage("您还没有付费看正片,无法提现");
        customDialog.setYesOnclickListener("去选片", new CustomDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                customDialog.dismiss();
                startActivity(new Intent(ObtainCashActivtiy.this, MainActivity.class));
                finish();
            }
        });
        customDialog.setNoOnclickListener("下次再说", new CustomDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                customDialog.dismiss();
            }
        });
        customDialog.show();
        customDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                recoverBg();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;
        if (requestCode == 101) {
            initData();
        }
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

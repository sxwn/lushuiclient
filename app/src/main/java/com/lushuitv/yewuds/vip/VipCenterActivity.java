package com.lushuitv.yewuds.vip;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lushuitv.yewuds.Image.ImageManager;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.coin.CoinResponse;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.flyn.Eyes;
import com.lushuitv.yewuds.module.entity.UseParams;
import com.lushuitv.yewuds.utils.SystemUtils;
import com.lushuitv.yewuds.utils.UIUtils;
import com.lushuitv.yewuds.view.MyListView;
import com.socks.library.KLog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by weip on 2017\12\11 0011.
 */

public class VipCenterActivity extends BaseActivity implements View.OnClickListener {
    public static final int VIP_PAY_CODE = 1;
    private View mView;
    private View headView, footView;
    private LayoutInflater mInflater;
    private MyListView vipListView;
    private VipCenterListAdapter memberCenterListAdapter;
    private List<VipBuyListResponse.OBJECTBean.BuyListBean> datas;

    private ImageView headUserHeadShot;
    private TextView headCoinView;//金币数
    private ImageView headVipIcon;//VIP ICON
    private TextView headVipName, headVipStatus;
    private TextView headExchange;

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.activity_mine_vip, null);
        return mView;
    }

    @Override
    public void initToolbar() {
        mToolbar.setBackgroundColor(getResources().getColor(R.color.vip_bg_start_color));
        mToolbarTitle.setText("会员中心");
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
    }

    @Override
    public void initOptions() {
        Eyes.setStatusBarColor(this, Color.parseColor("#FF5E62"));//vip起始色
        datas = new ArrayList<>();
        vipListView = (MyListView) mView.findViewById(R.id.member_center_listview);
        mInflater = LayoutInflater.from(this);
        headView = mInflater.inflate(R.layout.vip_listview_headview, null);
        initHeadView(headView);
        footView = mInflater.inflate(R.layout.vip_listview_footview, null);
        vipListView.addHeaderView(headView);
        vipListView.addFooterView(footView);
        memberCenterListAdapter = new VipCenterListAdapter(this,datas);
        vipListView.setAdapter(memberCenterListAdapter);
        memberCenterListAdapter.setOpenVipInterface(new VipCenterListAdapter.OpenVipInterface() {
            @Override
            public void openVipClick(View v, int position, List<VipBuyListResponse.OBJECTBean.BuyListBean> datas) {
                Intent in = new Intent(VipCenterActivity.this, VipPayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("viptaocan", (Serializable) datas.get(position));
                bundle.putInt("index",position);
                in.putExtra("viptaocan_bundle",bundle);
                startActivityForResult(in, VIP_PAY_CODE);
            }
        });
        initData();
    }

    private void initData() {
        ApiRetrofit.getInstance().getApiService().getVipBuyList(Config.getCachedUserId(this),SystemUtils.getVersion(this))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VipBuyListResponse>() {
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
                    public void onNext(VipBuyListResponse response) {
                        if ("0".equals(response.getSTATUS())){
                            List<VipBuyListResponse.OBJECTBean.BuyListBean> buyList = response.getOBJECT().getBuyList();
                            memberCenterListAdapter.setData(buyList);
                            initHeadData(response.getOBJECT());
                        }
                    }
                });
    }

    private void initHeadView(View headView) {
        headUserHeadShot = (ImageView) headView.findViewById(R.id.vip_listview_headview_userheadshot);
        headVipName = (TextView) headView.findViewById(R.id.vip_listview_headview_name);
        headVipStatus = (TextView) headView.findViewById(R.id.vip_listview_headview_status);
        headVipIcon = (ImageView) headView.findViewById(R.id.vip_listview_headview_icon);
        headCoinView = (TextView) headView.findViewById(R.id.vip_listview_headview_coin);
        headExchange = (TextView) headView.findViewById(R.id.vip_listview_headview_exchange);
        headExchange.setOnClickListener(this);
    }

    private void initHeadData(VipBuyListResponse.OBJECTBean objectBean){
        headVipName.setText(objectBean.getUserName());
        headCoinView.setText("我的金币:" + objectBean.getGold());
        if (!objectBean.getUserHeadshot().contains("http://")) {
            ImageManager.getInstance().loadRoundImage(this, "http://" + objectBean.getUserHeadshot(), headUserHeadShot);
        } else {
            ImageManager.getInstance().loadRoundImage(this, objectBean.getUserHeadshot(), headUserHeadShot);
        }
        if (objectBean.getInsiderType() == 0) {
            headVipStatus.setText("暂未开通");
            headVipIcon.setBackgroundResource(R.drawable.member_vip_gray);
        } else {
            headVipStatus.setText(objectBean.getEndTime() + "到期");
            headVipStatus.setTextColor(Color.parseColor("#FA8072"));
            headVipIcon.setBackgroundResource(R.drawable.member_vip_red);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;
        if (requestCode == VIP_PAY_CODE){
            initData();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vip_listview_headview_exchange://会员兑换
                startActivity(new Intent(this, VipExchangeActivity.class));
                break;
        }
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
}

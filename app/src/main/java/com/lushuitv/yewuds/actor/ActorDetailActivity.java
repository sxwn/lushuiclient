package com.lushuitv.yewuds.actor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lushuitv.yewuds.Image.ImageManager;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.activity.LoginTranslucentActivtiy;
import com.lushuitv.yewuds.activity.PayVideoActivity;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.flyn.Eyes;
import com.lushuitv.yewuds.module.entity.WorksListBean;
import com.lushuitv.yewuds.module.response.ActorWorkInfo;
import com.lushuitv.yewuds.reward.ActorRewardActivity;
import com.lushuitv.yewuds.reward.ActorRewardRankActivity;
import com.lushuitv.yewuds.view.PullToRefreshLayout;
import com.lushuitv.yewuds.view.pullableview.PullableListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 艺人中心页
 * Created by weip on 2017\12\14 0014.
 */

public class ActorDetailActivity extends AppCompatActivity implements CommonTabPagerAdapter.TabPagerListener, View.OnClickListener {
    int actorId;
    String actorName;
    ImageView userHeadImg;
    TextView userName, userFans;
    String url;
    //新需求
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbar;
    TabLayout tabLayout;
    AppBarLayout appbar;
    ViewPager viewpager;
    private CommonTabPagerAdapter tabAdapter;
    private RelativeLayout actorRankRl, actorRewardRl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor_detail);
        actorId = getIntent().getIntExtra("actor_id", 0);
        actorName = getIntent().getStringExtra("actor_name");
        url = getIntent().getStringExtra("actor_headview");
        initView();
    }

    @Override
    public Fragment getFragment(int position) {
        return ActorPageFragment.newInstance(position, actorId);
    }

    public void initView() {
        Eyes.setStatusBarColor(this, getResources().getColor(R.color.black));
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        userHeadImg = (ImageView) findViewById(R.id.actor_header_actorimage);
        ImageManager.getInstance().loadRoundImage(this, url, userHeadImg);
        userName = (TextView) findViewById(R.id.actor_header_actorname);
        userName.setText(actorName);
        userFans = (TextView) findViewById(R.id.actor_header_actor_fans);
        actorRankRl = (RelativeLayout) findViewById(R.id.actor_rank_rl);
        actorRankRl.setOnClickListener(this);
        actorRewardRl = (RelativeLayout) findViewById(R.id.actor_reward_rl);
        actorRewardRl.setOnClickListener(this);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        setTitle(actorName);
        collapsingToolbar.setTitle(actorName);
        collapsingToolbar.setExpandedTitleColor(Color.parseColor("#00ffffff"));//设置还没收缩时状态下字体颜色
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的
        tabAdapter = new CommonTabPagerAdapter(getSupportFragmentManager()
                , 2, Arrays.asList("露水动态", "女神动态"), this);
        viewpager.setAdapter(tabAdapter);
        tabAdapter.setListener(this);
        tabLayout.setupWithViewPager(viewpager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.actor_rank_rl://打赏榜
                startActivity(new Intent(this,ActorRewardRankActivity.class).putExtra("actorId",actorId));
                break;
            case R.id.actor_reward_rl://打赏
                startActivity(new Intent(this, ActorRewardActivity.class)
                        .putExtra("actorid",actorId)
                        .putExtra("actorname",actorName)
                        .putExtra("actorimage",url));
                break;
        }
    }
}

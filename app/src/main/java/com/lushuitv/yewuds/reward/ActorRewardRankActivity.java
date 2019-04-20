package com.lushuitv.yewuds.reward;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;

import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.coin.CoinListResponse;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.flyn.Eyes;
import com.lushuitv.yewuds.module.response.UserInfoResponse;
import com.lushuitv.yewuds.utils.SystemUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 打赏排行榜
 * Created by weip on 2017\12\25 0025.
 */

public class ActorRewardRankActivity extends BaseActivity {

    private View mView;
    private ActorRewardRankAdapter adapter;

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.activity_actor_reward_rank_layout, null);
        return mView;
    }

    @Override
    public void initToolbar() {
        Eyes.setStatusBarColor(this, getResources().getColor(R.color.reward_top_bg_color));//打赏榜起始色
        mToolbar.setBackgroundColor(getResources().getColor(R.color.reward_top_bg_color));
        mToolbarTitle.setText("打赏排行榜");
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
    private RecyclerView mRecycleView;
    private int actorId;
    @Override
    protected void initOptions() {
        actorId = getIntent().getIntExtra("actorId",0);
        mRecycleView = (RecyclerView) mView.findViewById(R.id.actor_reward_recycleview);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        List<CoinListResponse.OBJECTBean> rewardDatas = new ArrayList<>();
        adapter = new ActorRewardRankAdapter(this,rewardDatas);
        mRecycleView.setAdapter(adapter);
        ApiRetrofit.getInstance().getApiService().getActorRewardRank(Config.getCachedUserId(this),SystemUtils.getVersion(this),actorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CoinListResponse>(){

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CoinListResponse coinListResponse) {
                        if ("0".equals(coinListResponse.getSTATUS())){
                            adapter.setData(coinListResponse.getOBJECT());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

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
}

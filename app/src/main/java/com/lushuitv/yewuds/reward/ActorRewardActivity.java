package com.lushuitv.yewuds.reward;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.lushuitv.yewuds.Image.ImageManager;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.coin.CoinRechargeAdapter;
import com.lushuitv.yewuds.coin.CoinResponse;
import com.lushuitv.yewuds.flyn.Eyes;
import com.lushuitv.yewuds.utils.KeyBoardUtils;
import com.lushuitv.yewuds.utils.UIUtils;
import com.lushuitv.yewuds.vip.VipPayActivity;
import java.util.ArrayList;
import java.util.List;

/**
 * 艺人打赏榜
 * Created by weip on 2017\12\23 0023.
 */

public class ActorRewardActivity extends BaseActivity implements View.OnClickListener {
    private View mView;
    private ImageView rewardTopImage;
    private TextView rewardTopName;
    private RecyclerView rewardRecycleView;
    private TextView rewardCommit;
    private EditText rewardEt;

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.activity_actor_reward_layout, null);
        return mView;
    }
    private int actorId;
    private String actorName, actorImageUrl;
    Intent intent;

    @Override
    public void initToolbar() {
        Eyes.setStatusBarColor(this, getResources().getColor(R.color.reward_top_bg_color));//打赏榜起始色
        mToolbar.setBackgroundColor(getResources().getColor(R.color.reward_top_bg_color));
        mToolbarTitle.setText("打赏");
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

    List<CoinResponse.OBJECTBean.ListBean> mData;
    private int currentPos = 2;//当前点击的位置,默认是3
    @Override
    protected void initOptions() {
        mData = new ArrayList<CoinResponse.OBJECTBean.ListBean>();
        for (int i = 0; i < 6; i++) {
            mData.add(new CoinResponse.OBJECTBean.ListBean());
        }
        intent = getIntent();
        actorId = intent.getIntExtra("actorid",0);
        actorName = intent.getStringExtra("actorname");
        actorImageUrl = intent.getStringExtra("actorimage");
        rewardTopImage = (ImageView) mView.findViewById(R.id.actor_reward_userimage);
        rewardTopName = (TextView) mView.findViewById(R.id.actor_reward_name);
        rewardTopName.setText(actorName);
        ImageManager.getInstance().loadRoundImage(this, actorImageUrl, rewardTopImage);
        rewardRecycleView = (RecyclerView) mView.findViewById(R.id.actor_reward_recycleview);
        CoinRechargeAdapter myAdapter = new CoinRechargeAdapter(this, mData, 1);
        rewardRecycleView.setLayoutManager(new GridLayoutManager(this, 3));
        //设置增加或删除条目动画
        rewardRecycleView.setItemAnimator(new DefaultItemAnimator());
        rewardRecycleView.setAdapter(myAdapter);//设置适配器
        myAdapter.setCoinItemClickListener(new CoinRechargeAdapter.CoinItemClickListener() {
            @Override
            public void onItemClick(int position, View view, TextView coinView, TextView rmbView) {
                currentPos = position;
                myAdapter.notifyDataSetChanged();
            }
        });
        rewardEt = (EditText) mView.findViewById(R.id.actor_reward_et);
        rewardEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        KeyBoardUtils.closeKeyboard(rewardEt,ActorRewardActivity.this);
                    }
                },500);

            }
        });
        rewardCommit = (TextView) mView.findViewById(R.id.actor_reward_comment);
        rewardCommit.setOnClickListener(this);
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

    private int coinNumber;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.actor_reward_comment://打赏支付
                if (!TextUtils.isEmpty(rewardEt.getText().toString()) && Integer.parseInt(rewardEt.getText().toString()) > 0 ){
                    coinNumber = Integer.parseInt(rewardEt.getText().toString());
                }else if (currentPos == 0) {
                    coinNumber = 88;
                } else if (currentPos == 1) {
                    coinNumber = 888;
                } else if (currentPos == 2) {
                    coinNumber = 1888;
                } else if (currentPos == 3) {
                    coinNumber = 5200;
                } else if (currentPos == 4) {
                    coinNumber = 6666;
                } else if (currentPos == 5) {
                    coinNumber = 8888;
                }
                startActivity(new Intent(this, VipPayActivity.class).putExtra("actorId",actorId).
                        putExtra("actor", actorName).putExtra("coinNumber", coinNumber));
                break;
        }
    }
}

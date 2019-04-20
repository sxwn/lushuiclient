package com.lushuitv.yewuds.reward;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lushuitv.yewuds.Image.ImageManager;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.adapter.RankCollectAdapter;
import com.lushuitv.yewuds.adapter.SearchVideoAdapter;
import com.lushuitv.yewuds.coin.CoinListResponse;

import java.util.List;

/**
 * 打赏排行榜适配器
 * Created by weip on 2017\12\25 0025.
 */

public class ActorRewardRankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private static final int TYPE_TOP = 0;
    private static final int TYPE_CONTENT = 1;
    private List<CoinListResponse.OBJECTBean> rewardDatas;


    public ActorRewardRankAdapter(Context context, List<CoinListResponse.OBJECTBean> rewardDatas) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.rewardDatas = rewardDatas;
    }

    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_TOP;
        } else {
            return TYPE_CONTENT;
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TOP) {
            RewardRankTopHolder collectTitleHolder = new RewardRankTopHolder
                    (mInflater.inflate(R.layout.activity_actor_reward_rank_top, parent, false));
            return collectTitleHolder;
        } else if (viewType == TYPE_CONTENT) {
            RewardRankHolder collectTitleHolder = new RewardRankHolder
                    (mInflater.inflate(R.layout.reward_rank_item_layout, parent, false));
            return collectTitleHolder;
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ActorRewardRankAdapter.RewardRankTopHolder) {
        } else if (holder instanceof ActorRewardRankAdapter.RewardRankHolder) {
            bindItemHolder((ActorRewardRankAdapter.RewardRankHolder) holder, position);
        }

    }

    private void bindItemHolder(RewardRankHolder holder, int position) {
        if (position-1>2){
            holder.rewardId.setText(""+position);
        }else {
            holder.rewardId.setBackgroundResource(R.drawable.reward_rank_top);
        }
        CoinListResponse.OBJECTBean objectBean = rewardDatas.get(position-1);
        holder.rewardUserName.setText(objectBean.getUserName());
        holder.rewardMoney.setText(objectBean.getGold()+"");
        ImageManager.getInstance().loadRoundImage(context,objectBean.getUserHeadshot(),holder.rewardUserIcon);
    }

    @Override
    public int getItemCount() {
        return rewardDatas.size()+1;
    }

    public void setData(List<CoinListResponse.OBJECTBean> datas) {
        rewardDatas.addAll(datas);
        notifyDataSetChanged();
    }
    class RewardRankTopHolder extends RecyclerView.ViewHolder {

        public RewardRankTopHolder(View itemView) {
            super(itemView);
        }
    }
    class RewardRankHolder extends RecyclerView.ViewHolder {
        private TextView rewardId,rewardUserName,rewardMoney;
        private ImageView rewardUserIcon;

        public RewardRankHolder(View itemView) {
            super(itemView);
            rewardId = (TextView) itemView.findViewById(R.id.reward_rank_item_id);
            rewardUserName = (TextView) itemView.findViewById(R.id.reward_rank_item_username);
            rewardMoney = (TextView) itemView.findViewById(R.id.reward_rank_item_coin_number);
            rewardUserIcon = (ImageView) itemView.findViewById(R.id.reward_rank_item_usericon);
        }
    }
}

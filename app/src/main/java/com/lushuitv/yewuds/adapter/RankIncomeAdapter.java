package com.lushuitv.yewuds.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lushuitv.yewuds.Image.ImageManager;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.module.response.RankIncomeResponse;
import com.socks.library.KLog;

import java.util.List;

/**
 * Description
 * Created by weip
 * Date on 2017/9/18.
 */

public class RankIncomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private static final int TYPE_SORT = 0;
    private static final int TYPE_CONTENT = 1;

    private int type;

    private List<RankIncomeResponse.LISTBean> mDatas;
    private RankIncomeResponse.OBJECTBean objectBean;


    public RankIncomeAdapter(Context context, List<RankIncomeResponse.LISTBean> mLists, RankIncomeResponse.OBJECTBean objectBean, int type) {
        this.context = context;
        this.mDatas = mLists;
        this.objectBean = objectBean;
        this.type = type;
    }

    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_SORT;
        } else {
            return TYPE_CONTENT;
        }
    }

    public void setData(List<RankIncomeResponse.LISTBean> mLists, RankIncomeResponse.OBJECTBean obJectBean) {
        if (this.mDatas.size()>0)
            this.mDatas.clear();
        this.mDatas.addAll(mLists);
        this.objectBean = obJectBean;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_SORT) {
            RankIncomeTitleHolder incomeTitleHolder =
                    new RankIncomeTitleHolder(LayoutInflater.from(context).inflate(R.layout.rank_income_title, parent, false));
            return incomeTitleHolder;
        } else if (viewType == TYPE_CONTENT) {
            RankIncomeHolder incomeContentHolder = new RankIncomeHolder(LayoutInflater.from(context).inflate(R.layout.rank_income_item, parent, false));
            return incomeContentHolder;
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RankIncomeTitleHolder) {
            bindTitleHolder((RankIncomeTitleHolder) holder);
        } else if (holder instanceof RankIncomeHolder) {
            bintItemHolder((RankIncomeHolder) holder, position);
        }
    }

    private void bindTitleHolder(RankIncomeTitleHolder holder) {
        if (type == 1) {
            holder.titleIncomeType.setText("分销收入");
        } else {
            holder.titleIncomeType.setText("消费金额");
        }
        holder.titleTag.setText("用户名");
        if (objectBean != null) {
            if (objectBean.getUser() != null) {
                if (objectBean.getUser().getUserHeadshot() != null) {
                    if (!objectBean.getUser().getUserHeadshot().startsWith("http://")) {
                        objectBean.getUser().setUserHeadshot("http://" + objectBean.getUser().getUserHeadshot());
                    }
                    ImageManager.getInstance().loadRoundImage(context, objectBean.getUser().getUserHeadshot(), holder.titleUserIcon);
                }
                holder.titleUserName.setText(objectBean.getUser().getUserName());
                holder.titleIncome.setText(objectBean.getUserRewardPurse() + "");
                holder.titleRank.setText("第" + objectBean.getRanking() + "名");
                if (type == 1) {
                    holder.titleIncome.setText(objectBean.getUserRewardPurse() + "");
                } else {
                    holder.titleIncome.setText(objectBean.getBuyMoney() + "");//消费金额
                }
            }
        }
    }

    private void bintItemHolder(RankIncomeHolder holder, int position) {
        holder.id.setText(position + "");
        KLog.e("position:" + position);
        if (position == 1) {
            holder.id.setBackgroundResource(R.mipmap.top_one_icon);
        } else if (position == 2) {
            holder.id.setBackgroundResource(R.mipmap.top_second_icon);
        } else if (position == 3) {
            holder.id.setBackgroundResource(R.mipmap.top_thrid_icon);
        } else {
            holder.id.setBackgroundResource(R.mipmap.top_four_icon);
        }

        holder.itemName.setText(mDatas.get(position-1).getUserName());
        if (type == 1) {
            holder.totalNum.setText(mDatas.get(position-1).getUserRewardPurse() + "");//分销收入
        } else {
            holder.totalNum.setText(mDatas.get(position-1).getBuyMoney() + "");//消费金额
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size()+1;
    }

    class RankIncomeHolder extends RecyclerView.ViewHolder {
        private TextView id, itemName, totalNum;
        private ImageView itemImage;

        public RankIncomeHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.rank_income_item_id);
            itemName = (TextView) itemView.findViewById(R.id.rank_income_item_name);
            totalNum = (TextView) itemView.findViewById(R.id.rank_income_item_count);
            itemImage = (ImageView) itemView.findViewById(R.id.rank_income_item_image);
        }
    }

    class RankIncomeTitleHolder extends RecyclerView.ViewHolder {
        private ImageView titleUserIcon;
        private TextView titleUserName, titleRank, titleIncome, titleIncomeType,titleTag;

        public RankIncomeTitleHolder(View itemView) {
            super(itemView);
            titleUserIcon = (ImageView) itemView.findViewById(R.id.rank_income_title_image);
            titleUserName = (TextView) itemView.findViewById(R.id.rank_income_title_name);
            titleTag = (TextView) itemView.findViewById(R.id.rank_income_title_usertag);
            titleRank = (TextView) itemView.findViewById(R.id.rank_income_title_rank);
            titleIncome = (TextView) itemView.findViewById(R.id.rank_income_title_income);
            titleIncomeType = (TextView) itemView.findViewById(R.id.rank_income_title_type);
        }
    }
}

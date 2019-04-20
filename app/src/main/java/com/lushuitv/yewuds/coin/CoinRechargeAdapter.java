package com.lushuitv.yewuds.coin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class CoinRechargeAdapter extends RecyclerView.Adapter<CoinRechargeAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private Context mContext;
    private List<CoinResponse.OBJECTBean.ListBean> mDatas;
    private List<Boolean> isClicks;//控件是否被点击,默认为false，如果被点击，改变值，控件根据值改变自身颜色
    private CoinItemClickListener coinItemClickListener;
    private int type;

    public void setCoinItemClickListener(CoinItemClickListener coinItmClickListener) {
        this.coinItemClickListener = coinItmClickListener;
    }
    LinearLayout.LayoutParams params;

    //创建构造参数
    public CoinRechargeAdapter(Context context, List<CoinResponse.OBJECTBean.ListBean> datas, int type) {
        this.mContext = context;
        this.mDatas = datas;
        this.type = type;
        inflater = LayoutInflater.from(context);
        isClicks = new ArrayList<>();
        params = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = UIUtils.dip2Px(12);
    }

    //创建ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.coin_rechage_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    //绑定ViewHolder
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        for (int i = 0; i < mDatas.size(); i++) {
            if (i == 2) {
                isClicks.add(true);
            } else {
                isClicks.add(false);
            }
        }
        //为textview 赋值
        if (type == 1) {
            holder.moneyTv.setVisibility(View.GONE);
            holder.coinTv.setLayoutParams(params);
            if (position == 0) {
                holder.coinTv.setText("88金币");
            } else if (position == 1) {
                holder.coinTv.setText("888金币");
            } else if (position == 2) {
                holder.coinTv.setText("1888金币");
            } else if (position == 3) {
                holder.coinTv.setText("5200金币");
            } else if (position == 4) {
                holder.coinTv.setText("6666金币");
            } else if (position == 5) {
                holder.coinTv.setText("8888金币");
            }
        } else {
            holder.moneyTv.setVisibility(View.VISIBLE);
            holder.coinTv.setText(mDatas.get(position).getGold() + "金币");
            holder.moneyTv.setText(mDatas.get(position).getPrice() + "");
        }

        if (isClicks.get(position)) {
            holder.relativeLayout.setBackgroundResource(R.drawable.drawable_coin_recharge_orange);
            holder.coinTv.setTextColor(mContext.getResources().getColor(R.color.coin_checked_color));
            holder.moneyTv.setTextColor(mContext.getResources().getColor(R.color.coin_checked_color));
        } else {
            holder.coinTv.setTextColor(mContext.getResources().getColor(R.color.coin_color));
            holder.moneyTv.setTextColor(mContext.getResources().getColor(R.color.coin_money_color));
            holder.relativeLayout.setBackgroundResource(R.drawable.drawable_coin_recharge_grey);
        }
        // 如果设置了回调，则设置点击事件
        if (coinItemClickListener != null) {
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < isClicks.size(); i++) {
                        isClicks.set(i, false);
                    }
                    isClicks.set(position, true);
                    coinItemClickListener.onItemClick(position, holder.relativeLayout, holder.coinTv, holder.moneyTv);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.mDatas.size();
    }

    public void setData(List<CoinResponse.OBJECTBean.ListBean> objectBeans) {
        this.mDatas = objectBeans;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout relativeLayout;
        TextView coinTv, moneyTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            relativeLayout = (LinearLayout) itemView.findViewById(R.id.coin_recharge_item_parent);
            coinTv = (TextView) itemView.findViewById(R.id.coin_recharge_recycle_item_coin);
            moneyTv = (TextView) itemView.findViewById(R.id.coin_recharge_recycle_item_rmb);
        }
    }

    public interface CoinItemClickListener {
        void onItemClick(int position, View view, TextView coinView, TextView rmbView);
    }
}




package com.lushuitv.yewuds.coin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lushuitv.yewuds.R;
import java.util.List;

/**
 * 充值记录
 * Created by weip on 2017\12\19 0019.
 */

public class CoinRechargeRecordAdapter extends RecyclerView.Adapter<CoinRechargeRecordAdapter.CoinRecordHolder> {

    private LayoutInflater inflater;
    private Context mContext;
    private List<String> mDatas;


    public CoinRechargeRecordAdapter(Context context, List<String> datas) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(context);
    }
    //创建ViewHolder
    @Override
    public CoinRechargeRecordAdapter.CoinRecordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.coin_record_item, parent, false);
        CoinRechargeRecordAdapter.CoinRecordHolder viewHolder = new CoinRechargeRecordAdapter.CoinRecordHolder(view);
        return viewHolder;
    }

    //绑定ViewHolder
    @Override
    public void onBindViewHolder(CoinRecordHolder holder, int position) {
        //为textview 赋值

    }

    @Override
    public int getItemCount() {
        return this.mDatas.size();
    }

    class CoinRecordHolder extends RecyclerView.ViewHolder {

        LinearLayout relativeLayout;
        TextView coinTv, moneyTv;

        public CoinRecordHolder(View itemView) {
            super(itemView);
            relativeLayout = (LinearLayout) itemView.findViewById(R.id.coin_recharge_item_parent);
            coinTv = (TextView) itemView.findViewById(R.id.coin_recharge_recycle_item_coin);
            moneyTv = (TextView) itemView.findViewById(R.id.coin_recharge_recycle_item_rmb);
        }
    }
}

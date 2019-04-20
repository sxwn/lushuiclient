package com.lushuitv.yewuds.coin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lushuitv.yewuds.R;

import java.util.List;

/**
 * 记录adapter
 * Created by weip on 2017\12\27 0027.
 */
public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.TradeItemViewHolder> {

    private LayoutInflater mInflater;
    private List<CoinListResponse.OBJECTBean> incomeRecords;

    public RecordAdapter(Context context, List<CoinListResponse.OBJECTBean> incomeRecords) {
        mInflater = LayoutInflater.from(context);
        this.incomeRecords = incomeRecords;
    }

    @Override
    public RecordAdapter.TradeItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_trade_record, parent, false);
        TradeItemViewHolder holder = new TradeItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(TradeItemViewHolder holder, int position) {
        CoinListResponse.OBJECTBean objectBean = incomeRecords.get(position);
        if (TextUtils.isEmpty(objectBean.getWorksName())){
            holder.recordContent.setVisibility(View.GONE);
        }else{
            holder.recordContent.setVisibility(View.VISIBLE);
            holder.recordContent.setText(objectBean.getWorksName());
        }
        if (objectBean.getType() == 0) {
            holder.recordType.setText("视频分销收入");
        } else {
            holder.recordType.setText("金币充值");
        }
        holder.recordTime.setText(objectBean.getRecordDate());
        holder.recordCoinNumber.setText(objectBean.getGold()+"");
    }

    @Override
    public int getItemCount() {
        return incomeRecords.size();
    }

    public void setData(List<CoinListResponse.OBJECTBean> lists) {
        this.incomeRecords = lists;
    }

    /**
     * 希望读者有良好的编码习惯，将ViewHolder类写成静态的.
     **/
    static class TradeItemViewHolder extends RecyclerView.ViewHolder {
        TextView recordType, recordContent, recordTime, recordCoinNumber;

        public TradeItemViewHolder(View itemView) {
            super(itemView);
            recordType = (TextView) itemView.findViewById(R.id.item_trade_record_type);
            recordContent = (TextView) itemView.findViewById(R.id.item_trade_record_content);
            recordTime = (TextView) itemView.findViewById(R.id.item_trade_record_time);
            recordCoinNumber = (TextView) itemView.findViewById(R.id.item_trade_record_count);
        }
    }
}

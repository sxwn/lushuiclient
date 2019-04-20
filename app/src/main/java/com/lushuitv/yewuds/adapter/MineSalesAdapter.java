package com.lushuitv.yewuds.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lushuitv.yewuds.Image.ImageManager;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.module.response.TradeRecordResponse;

import java.util.List;

/**
 * Description 我的收益适配器
 * Created by weip
 * Date on 2017/9/1.
 */

public class MineSalesAdapter extends BaseAdapter{

    private Context context;
    private LayoutInflater mInflater;
    private List<TradeRecordResponse.LISTBean> mLists;

    public MineSalesAdapter(Context context,List<TradeRecordResponse.LISTBean> mLists) {
        this.context = context;
        this.mLists = mLists;
        this.mInflater = LayoutInflater.from(context);
    }

    public void setData(List<TradeRecordResponse.LISTBean> datas){
        this.mLists.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mLists.size();
    }

    @Override
    public Object getItem(int position) {
        return mLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NoticeHolder noticeHolder = null;
        if (convertView == null){
            noticeHolder = new NoticeHolder();
            convertView = mInflater.inflate(R.layout.activity_mine_sales_item,null);
            noticeHolder.imageView = (ImageView) convertView.findViewById(R.id.sales_item_usericon);
            noticeHolder.nameTv = (TextView) convertView.findViewById(R.id.sales_item_username);
            noticeHolder.timeTv = (TextView) convertView.findViewById(R.id.sales_item_time);
            noticeHolder.meneyTv = (TextView) convertView.findViewById(R.id.sales_item_money);
            convertView.setTag(noticeHolder);
        }else{
            noticeHolder = (NoticeHolder) convertView.getTag();
        }
        noticeHolder.meneyTv.setText(mLists.get(position).getRecordMoney());
        noticeHolder.nameTv.setText(mLists.get(position).getWorksName());
        if (mLists.get(position).getAvatar().startsWith("http://")) {
            ImageManager.getInstance().loadRoundImage(context,mLists.get(position).getAvatar()+"?imageView2/0/w/100/h/100",noticeHolder.imageView);
        } else {
            ImageManager.getInstance().loadRoundImage(context, R.drawable.default_user_icon, noticeHolder.imageView);
        }
        noticeHolder.timeTv.setText(mLists.get(position).getRecordDate().substring(5,mLists.get(position).getRecordDate().length()));
        return convertView;
    }

    class NoticeHolder{
        ImageView imageView;
        TextView nameTv,timeTv,meneyTv;
    }
}

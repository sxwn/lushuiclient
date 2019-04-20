package com.lushuitv.yewuds.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.module.response.FenXiaoResponse;

import java.util.List;

/**
 * Description 我的分销适配器
 * Created by weip
 * Date on 2017/9/1.
 */

public class MineFenxiaoAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mInflater;
    private List<FenXiaoResponse.LISTBean> lists;
    ;

    public MineFenxiaoAdapter(Context context, List<FenXiaoResponse.LISTBean> lists) {
        this.context = context;
        this.lists = lists;
        this.mInflater = LayoutInflater.from(context);
    }


    public void setData(List<FenXiaoResponse.LISTBean> lists) {
        this.lists.addAll(lists);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FenxiaoHolder fenxiaoHolder = null;
        if (convertView == null) {
            fenxiaoHolder = new FenxiaoHolder();
            convertView = mInflater.inflate(R.layout.activity_mine_fenxiao_item, null);
            fenxiaoHolder.itemWorkName = (TextView) convertView.findViewById(R.id.mine_fenxiao_item_workname);
            fenxiaoHolder.itemShare = (TextView) convertView.findViewById(R.id.mine_fenxiao_item_share);
            fenxiaoHolder.itemOtherShare = (TextView) convertView.findViewById(R.id.mine_fenxiao_item_othershare);
            fenxiaoHolder.itemTotal = (TextView) convertView.findViewById(R.id.mine_fenxiao_item_total);
            convertView.setTag(fenxiaoHolder);
        } else {
            fenxiaoHolder = (FenxiaoHolder) convertView.getTag();
        }
        FenXiaoResponse.LISTBean fenxiaoItemData = lists.get(position);
        fenxiaoHolder.itemWorkName.setText(fenxiaoItemData.getWorksName());
        fenxiaoHolder.itemShare.setText(fenxiaoItemData.getHigherReward());
        fenxiaoHolder.itemOtherShare.setText(fenxiaoItemData.getSuperiorReward());
        fenxiaoHolder.itemTotal.setText(fenxiaoItemData.getAllReward());
        return convertView;
    }

    class FenxiaoHolder {
        TextView itemWorkName, itemShare,itemOtherShare,itemTotal;
    }
}

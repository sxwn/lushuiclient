package com.lushuitv.yewuds.vip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lushuitv.yewuds.R;

import java.util.List;

/**
 * Created by weip on 2017\12\12 0012.
 */

public class VipCenterListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<VipBuyListResponse.OBJECTBean.BuyListBean> datas;
    private OpenVipInterface openVipInterface;

    public void setOpenVipInterface(OpenVipInterface openVipInterface) {
        this.openVipInterface = openVipInterface;
    }

    public VipCenterListAdapter(Context context,List<VipBuyListResponse.OBJECTBean.BuyListBean> data) {
        this.mInflater = LayoutInflater.from(context);
        datas = data;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MemberVipHolder memberVipHolder = null;
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.member_center_item,null);
            memberVipHolder = new MemberVipHolder(convertView);
            convertView.setTag(memberVipHolder);
        }else{
            memberVipHolder = (MemberVipHolder) convertView.getTag();
        }
        VipBuyListResponse.OBJECTBean.BuyListBean buyListBean = datas.get(position);
        memberVipHolder.vipType.setText(buyListBean.getGold()+"金币/"+buyListBean.getMonth()+"个月");
        memberVipHolder.vipOpen.setText("开通");
        if (position == 0){
            memberVipHolder.vipDetail.setText("当月10-60金币视频免费看");
            memberVipHolder.goneView.setVisibility(View.GONE);
        }else if(position == 1){
            memberVipHolder.vipDetail.setText("3个月内10-60金币视频免费看");
            memberVipHolder.goneView.setVisibility(View.GONE);
        }else if(position == 2){
            memberVipHolder.vipDetail.setText("全年10-60金币视频免费看(另附赠超值年费会员加上视频专区)");
            memberVipHolder.goneView.setVisibility(View.VISIBLE);
        }
        MemberVipHolder finalMemberVipHolder = memberVipHolder;
        memberVipHolder.vipOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (openVipInterface != null){
                    openVipInterface.openVipClick(finalMemberVipHolder.vipOpen,position,datas);
                }
            }
        });
        return convertView;
    }

    public void setData(List<VipBuyListResponse.OBJECTBean.BuyListBean> lists) {
        this.datas = lists;
        notifyDataSetChanged();
    }


    class MemberVipHolder{
        TextView vipType,vipDetail,vipOpen;
        View goneView;
        public MemberVipHolder(View view){
            vipType = (TextView) view.findViewById(R.id.vip_item_type);
            vipDetail = (TextView) view.findViewById(R.id.vip_item_detail);
            vipOpen = (TextView) view.findViewById(R.id.vip_item_open);
            goneView = view.findViewById(R.id.gone_view);
        }
    }
    public interface OpenVipInterface{
        void openVipClick(View v,int position,List<VipBuyListResponse.OBJECTBean.BuyListBean> datas);
    }
}

package com.lushuitv.yewuds.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lushuitv.yewuds.Image.ImageManager;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.module.response.RankWorksBuyResponse;

import java.util.List;

/**
 * Description
 * Created by weip
 * Date on 2017/9/6.
 */

public class RankPayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private static final int TYPE_SORT = 0;
    private static final int TYPE_CONTENT = 1;

    private int type;

    private List<RankWorksBuyResponse.LISTBean> mDatas;


    private RankCollectAdapter.onUserCenterInterface onUserCenterInterface;

    public RankCollectAdapter.onUserCenterInterface getOnUserCenterInterface() {
        return onUserCenterInterface;
    }

    public void setOnUserCenterInterface(RankCollectAdapter.onUserCenterInterface onUserCenterInterface) {
        this.onUserCenterInterface = onUserCenterInterface;
    }


    public RankPayAdapter(Context context, List<RankWorksBuyResponse.LISTBean> mLists) {
        this.context = context;
        this.mDatas = mLists;
    }


    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_SORT;
        } else {
            return TYPE_CONTENT;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_SORT) {
            RankCollectTitleHolder collectTitleHolder = new RankCollectTitleHolder(LayoutInflater.from(context).inflate(R.layout.rank_collect_title, parent, false));
            return collectTitleHolder;
        } else if (viewType == TYPE_CONTENT) {
            RankCollectHolder fenXiaoHolder = new RankCollectHolder(LayoutInflater.from(context).inflate(R.layout.rank_collect_item, parent, false));
            return fenXiaoHolder;
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RankCollectHolder) {
            bindItemHolder((RankCollectHolder) holder, position);
        } else if (holder instanceof RankCollectTitleHolder) {
            bindTitleHolder((RankCollectTitleHolder) holder, position);
        }
    }

    private void bindItemHolder(RankCollectHolder holder, int position) {
        RankWorksBuyResponse.LISTBean listBean = mDatas.get(position-1);
        holder.id.setText(position + "");
        if (position == 1) {
            holder.id.setBackgroundResource(R.mipmap.top_one_icon);
        } else if (position == 2) {
            holder.id.setBackgroundResource(R.mipmap.top_second_icon);
        } else if (position == 3) {
            holder.id.setBackgroundResource(R.mipmap.top_thrid_icon);
        }else{
            holder.id.setBackgroundResource(R.mipmap.top_four_icon);
        }
        holder.itemName.setText(listBean.getWorksName());
        holder.totalNum.setText(listBean.getWorksBuyNum()+"");
        ImageManager.getInstance().loadImage(context,listBean.getWorksCover(),holder.itemImage);
    }

    private void bindTitleHolder(RankCollectTitleHolder holder, int position) {
        holder.collectNum.setText("付费数量");
    }

    public void setData(List<RankWorksBuyResponse.LISTBean> datas) {
        if(mDatas.size()>0)
            mDatas.clear();
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mDatas.size()+1;
    }


    class RankCollectHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private LinearLayout parentView;
        private TextView id, itemName, totalNum;
        private ImageView itemImage;

        public RankCollectHolder(View itemView) {
            super(itemView);
            parentView = (LinearLayout) itemView.findViewById(R.id.rank_collect_item_parent);
            id = (TextView) itemView.findViewById(R.id.rank_collect_item_id);
            itemImage = (ImageView) itemView.findViewById(R.id.rank_collect_item_image);
            parentView.setOnClickListener(this);
            itemName = (TextView) itemView.findViewById(R.id.rank_collect_item_name);
            totalNum = (TextView) itemView.findViewById(R.id.rank_collect_item_count);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.rank_collect_item_parent:
                    if (onUserCenterInterface != null){
                        onUserCenterInterface.onItemClickListener(v,getAdapterPosition());
                    }
                    break;

            }
        }
    }

    class RankCollectTitleHolder extends RecyclerView.ViewHolder {
        private TextView collectNum;

        public RankCollectTitleHolder(View itemView) {
            super(itemView);
            collectNum = (TextView) itemView.findViewById(R.id.rank_title_count);

        }
    }

    public interface onUserCenterInterface{
        void onItemClickListener(View v, int position);
    }

}

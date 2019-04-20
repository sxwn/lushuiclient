package com.lushuitv.yewuds.actor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lushuitv.yewuds.Image.ImageManager;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.module.response.ContentListBean;
import com.lushuitv.yewuds.utils.UIUtils;

import java.util.List;

/**
 * Created by Administrator on 2017\12\15 0015.
 */

public class ActorImageAdapter extends
        RecyclerView.Adapter<ActorImageAdapter.ViewHolder> {

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    private OnItemClickListener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickListener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    private LayoutInflater mInflater;
    private List<ContentListBean> mDatas;
    private Context context;

    public ActorImageAdapter(Context context, List<ContentListBean> datas) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.actor_detail_item_image, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mImg;
        public ViewHolder(View view) {
            super(view);
            mImg = (ImageView) view.findViewById(R.id.actor_detail_item_image);
            mImg.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickLitener != null){
                mOnItemClickLitener.onItemClick(v,getAdapterPosition());//0开始
            }
        }
    }


    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        ImageManager.getInstance().loadImage(context,
                mDatas.get(position).getContentUrl()+"?imageView2/0/w/190/h/190",viewHolder.mImg);
    }

}
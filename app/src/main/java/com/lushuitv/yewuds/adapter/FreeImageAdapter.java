package com.lushuitv.yewuds.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lushuitv.yewuds.Image.ImageManager;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.module.entity.LISTBean;

import java.util.List;

/**
 * Description
 * Created by weip
 * Date on 2017/11/7.
 */

public class FreeImageAdapter extends Adapter<ViewHolder>  {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private Context context;
    private List<LISTBean> lists;


    public FreeImageAdapter(Context context,List<LISTBean> lists) {
        this.context = context;
        this.lists = lists;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return lists.size() == 0 ? 0 : lists.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.collect_fragment_image_item, parent,
                    false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_foot, parent,
                    false);
            return new FootViewHolder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            LISTBean listBean = lists.get(position);
            ((ItemViewHolder)holder).itemAuthor.setText(listBean.getActorName());
            ImageManager.getInstance().loadImage(context, listBean.getWorksCover() + "?imageMogr2/thumbnail/342x481",
                    ((ItemViewHolder)holder).itemImage);
            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, position);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemLongClick(holder.itemView, position);
                        return false;
                    }
                });
            }
        }
    }


    static class ItemViewHolder extends ViewHolder {

        private ImageView itemImage;
        private TextView itemAuthor, itemImageCount;


        public ItemViewHolder(View view) {
            super(view);
            itemImage = (ImageView) itemView.findViewById(R.id.collect_fragment_image_item_iv);
            itemAuthor = (TextView) itemView.findViewById(R.id.collect_fragment_image_item_author);
            itemImageCount = (TextView) itemView.findViewById(R.id.collect_fragment_image_item_count);
        }
    }

    static class FootViewHolder extends ViewHolder {

        public FootViewHolder(View view) {
            super(view);
        }
    }
}

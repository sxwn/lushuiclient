package com.lushuitv.yewuds.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lushuitv.yewuds.Image.ImageManager;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.module.entity.WorksListBean;
import com.socks.library.KLog;

import java.util.List;

/**
 * Created by weip
 * Date on 2017/8/29.
 */

public class CollectImageAdapter extends RecyclerView.Adapter<CollectImageAdapter.MyViewHolder> {

    private Context context;
    private List<WorksListBean> lists;

    public CollectImageAdapter(Context context, List<WorksListBean> lists) {
        this.context = context;
        this.lists = lists;
    }

    private ImageClickInterface imageClick;

    private OnShowItemClickListener onShowItemClickListener;

    public void setCollectImageInterface(ImageClickInterface imageClick) {
        this.imageClick = imageClick;
    }

    public void setData(List<WorksListBean> lists) {
        this.lists.addAll(lists);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.collect_fragment_image_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        WorksListBean listBean = lists.get(position);
        holder.itemAuthor.setText(listBean.getActorName());
        KLog.e("url:" + listBean.getWorksCover() + "?imageView2/0/w/684/h/962");
        //GlideUtils.load(context,lists.get(position).getWorksCover()+"?imageView2/0/w/684/h/962", holder.itemImage);
        //腾讯人脸识别、等比
        ImageManager.getInstance().loadImage(context, listBean.getWorksCover() + "?imageMogr2/thumbnail/342x481", holder.itemImage);
//        GlideUtils.load(context,listBean.getWorksCover()+"?imageMogr2/crop/309x173", holder.itemImage);
//        holder.itemImageCount.setText(listBean.getWorksContentNum() + "P");

        // 是否是多选状态
        if (listBean.isShow()) {
            holder.imageCheckBox.setVisibility(View.VISIBLE);
        } else {
            holder.imageCheckBox.setVisibility(View.GONE);
        }
//        if (lists.get(position).isChecked()){
//            holder.imageCheckBox.setChecked(true);
//        }else{
//            holder.imageCheckBox.setChecked(false);
//        }
        holder.imageCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                KLog.e("回调了吗?" + isChecked);
                if (isChecked) {
                    listBean.setChecked(true);
                } else {
                    listBean.setChecked(false);
                }
                //接口回调
                onShowItemClickListener.onShowItemClick(listBean);
            }
        });
        // 必须放在监听后面
        holder.imageCheckBox.setChecked(listBean.isChecked());
        holder.itemAuthor.setText(listBean.getWorksName());//艺人名
    }

    public interface OnShowItemClickListener {
        void onShowItemClick(WorksListBean bean);
    }

    public void setOnShowItemClickListener(OnShowItemClickListener onShowItemClickListener) {
        this.onShowItemClickListener = onShowItemClickListener;
    }

    public OnShowItemClickListener getOnShowItemClickListener() {
        return onShowItemClickListener;
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CheckBox imageCheckBox;
        private ImageView itemImage;
        private TextView itemAuthor, itemImageCount;


        public MyViewHolder(View itemView) {
            super(itemView);
            imageCheckBox = (CheckBox) itemView.findViewById(R.id.collect_fragment_image_item_cb);
            itemImage = (ImageView) itemView.findViewById(R.id.collect_fragment_image_item_iv);
            itemImage.setOnClickListener(this);
            itemAuthor = (TextView) itemView.findViewById(R.id.collect_fragment_image_item_author);
            itemImageCount = (TextView) itemView.findViewById(R.id.collect_fragment_image_item_count);
        }

        @Override
        public void onClick(View v) {
            if (imageClick != null) {
                imageClick.imageClick(v, getAdapterPosition());
            }
        }
    }

    public interface ImageClickInterface {
        void imageClick(View v, int position);
    }
}

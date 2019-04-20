package com.lushuitv.yewuds.actor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.ImageHeaderParser;
import com.lushuitv.yewuds.Image.ImageManager;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.activity.ImageViewPagerActivtiy;
import com.lushuitv.yewuds.activity.VideoCommentActivity;
import com.lushuitv.yewuds.fragment.HomeNewFragment;
import com.lushuitv.yewuds.module.entity.WorksListBean;
import com.lushuitv.yewuds.module.response.ActorWorkInfo;
import com.lushuitv.yewuds.view.MyVideoPlayerStandard;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZMediaManager;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.OnVideoClickListener;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * Description 模特个人主页
 * Created by weip
 * Date on 2017/9/4.
 */

public class ActorDetailItemAdapter extends RecyclerView.Adapter {

    public static enum ITEM_TYPE {
        ITEM_TYPE_IMAGE, ITEM_TYPE_VIDEO
    }
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<WorksListBean> info;
    private int actorId;
    private ActorWorkInfo.OBJECTBean bean;

    public ActorDetailItemAdapter(Context context, List<WorksListBean> info, int actorId) {
        mContext = context;
        this.info = info;
        this.actorId = actorId;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getItemCount() {
        return info == null ? 0 : info.size();
    }
    public void refreshData(List<WorksListBean> info, ActorWorkInfo.OBJECTBean bean) {
        this.info.addAll(info);
        this.bean = bean;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        //1是视频,其它是图片
        if (info.get(position).getWorksType() == 1){
            return ITEM_TYPE.ITEM_TYPE_VIDEO.ordinal();
        }else if(info.get(position).getWorksType() == 2){
            return ITEM_TYPE.ITEM_TYPE_IMAGE.ordinal();
        }else{
            return 0;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_VIDEO.ordinal()) {
            return new VideoViewHolder(mLayoutInflater.inflate(R.layout.actor_detail_item_style_video, parent, false));
        } else {
            return new ImageViewHolder(mLayoutInflater.inflate(R.layout.actor_detail_item_style_image, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof VideoViewHolder){
            VideoViewHolder videoViewHolder = ((VideoViewHolder) holder);
            WorksListBean listBean = info.get(position);
            MyVideoPlayerStandard videoPlayer = videoViewHolder.jcVideoPlayerStandard;
            ImageManager.getInstance().loadImage(mContext, listBean.getWorksCover() + "?imageView2/0/w/750/h/350",
                    videoPlayer.thumbImageView);//设置缩略图
            videoViewHolder.videoTitle.setText(listBean.getWorksName());
            videoPlayer.setAllControlsVisible(GONE, GONE, VISIBLE, GONE, VISIBLE, VISIBLE, GONE);
            videoPlayer.tinyBackImageView.setVisibility(GONE);
            videoPlayer.setPosition(position);//绑定Position
            videoPlayer.titleTextView.setText("");//清除标题,防止复用的时候出现
            videoPlayer.setmOnVideoClickListener(new OnVideoClickListener() {
                @Override
                public void onVideoClickToStart() {
                    Intent commentIntent = new Intent(mContext, VideoCommentActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable(HomeNewFragment.SER_KEY,listBean);
                    commentIntent.putExtras(mBundle);
                    mContext.startActivity(commentIntent);
                }

                @Override
                public void onVideoProgress(int duration) {

                }
            });
        }else if(holder instanceof ImageViewHolder){
            //设置布局管理器
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            ((ImageViewHolder)holder).imageRecyclewView.setLayoutManager(linearLayoutManager);
            //设置适配器
            ActorImageAdapter actorImageAdapter = new ActorImageAdapter(mContext, info.get(position).getContentList());
            ((ImageViewHolder)holder).imageRecyclewView.setAdapter(actorImageAdapter);
            actorImageAdapter.setOnItemClickLitener(new ActorImageAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int imagePosition) {
                    Intent in = new Intent(mContext, ImageViewPagerActivtiy.class);
                    imagePosition++;//集合下标从1开始取
                    in.putExtra("worksId", info.get(position).getWorksId());
                    in.putExtra("worksIsCollect", info.get(position).getIsColletion());
                    in.putExtra("worksActor",actorId);
                    mContext.startActivity(in);
                }
            });
        }

    }
    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        MyVideoPlayerStandard jcVideoPlayerStandard;
        TextView videoTitle, watchCount, videoDucation;

        public VideoViewHolder(View itemView) {
            super(itemView);
            videoTitle = (TextView) itemView.findViewById(R.id.videoplayer_item_title);
            jcVideoPlayerStandard = (MyVideoPlayerStandard) itemView.findViewById(R.id.videoplayer_item_video);
            watchCount = (TextView) itemView.findViewById(R.id.videoplayer_item_watch_count);
            videoDucation = (TextView) itemView.findViewById(R.id.videoplayer_item_tv_duration);
        }
    }
    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        RecyclerView imageRecyclewView;
        public ImageViewHolder(View view) {
            super(view);
            imageRecyclewView = (RecyclerView) view.findViewById(R.id.model_page_item_recycleview);
        }
    }

}

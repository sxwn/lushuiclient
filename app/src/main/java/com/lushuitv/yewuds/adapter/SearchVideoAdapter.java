package com.lushuitv.yewuds.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lushuitv.yewuds.Image.ImageManager;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.module.entity.WorksListBean;
import com.lushuitv.yewuds.module.response.VideoUrlInfo;
import com.lushuitv.yewuds.utils.VideoUrlDecoder;
import com.lushuitv.yewuds.view.MyVideoPlayerStandard;
import com.socks.library.KLog;

import java.util.List;

import cn.jzvd.JZMediaManager;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.OnVideoClickListener;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Description
 * Created by weip
 * Date on 2017/10/9.
 */

public class SearchVideoAdapter extends RecyclerView.Adapter<SearchVideoAdapter.SearchHolder> {

    private Context context;
    private List<WorksListBean> mLists;

    public SearchVideoAdapter(Context context, List<WorksListBean> mLists) {
        this.context = context;
        this.mLists = mLists;
    }

    @Override
    public SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SearchHolder holder = new SearchHolder
                (LayoutInflater.from(context).inflate(R.layout.item_video_list, parent, false));
        return holder;
    }

    String contentUrl, var;

    MyVideoPlayerStandard clickVideoPlayer = null;

    public void setResult() {
        if (clickVideoPlayer != null) {
            KLog.e("播放新地址了、来看看哦.... ....");
            clickVideoPlayer.findViewById(R.id.item_video_list_pay).setVisibility(View.GONE);
            clickVideoPlayer.objects[1] = true;
            clickVideoPlayer.setUp(contentUrl + "v.f30.mp4?" + var, JZVideoPlayer.SCREEN_LAYOUT_LIST, "", true);
            clickVideoPlayer.startVideo();
        }
    }

    @Override
    public void onBindViewHolder(SearchHolder holder, int position) {
        if(Config.getCachedDataType(context) == 1){
            holder.bottomCollectRl.setVisibility(View.INVISIBLE);
            holder.bottomShareRl.setVisibility(View.INVISIBLE);
        }else{
            holder.bottomCollectRl.setVisibility(View.VISIBLE);
            holder.bottomShareRl.setVisibility(View.VISIBLE);
        }
        WorksListBean worksListBean = mLists.get(position);
        MyVideoPlayerStandard videoPlayer = holder.videoPlayer;
        ImageManager.getInstance().loadImage(context, worksListBean.getWorksCover() + "?imageView2/0/w/750/h/350",
                videoPlayer.thumbImageView);//设置缩略图
        videoPlayer.setAllControlsVisible(GONE, GONE, VISIBLE, GONE, VISIBLE, VISIBLE, GONE);
        videoPlayer.tinyBackImageView.setVisibility(GONE);
        videoPlayer.setPosition(position);//绑定Position
        videoPlayer.titleTextView.setText("");//清除标题,防止复用的时候出现
        //播放视频接口
        videoPlayer.setmOnVideoClickListener(new OnVideoClickListener() {
            @Override
            public void onVideoClickToStart() {
                clickVideoPlayer = videoPlayer;
                holder.llDuration.setVisibility(View.GONE);//隐藏时长
                holder.llTitle.setVisibility(View.GONE);//隐藏标题栏
                VideoUrlDecoder decoder = new VideoUrlDecoder() {
                    @Override
                    public void onSuccess(VideoUrlInfo urlInfo) {
                        KLog.e("第" + videoPlayer.getPosition() + "条视频数据开始播放" + "workId:" + worksListBean.getWorksId());
                        String contentAdvanceUrl = urlInfo.getOBJECT().getWorkContent().getContentAdvanceUrl();
                        contentUrl = urlInfo.getOBJECT().getWorkContent().getContentUrl();
                        var = urlInfo.getOBJECT().getVar();
                        String var_advance = urlInfo.getOBJECT().getVar_advance();
                        if (urlInfo.getOBJECT().getBuyRecord() == null) {
                            videoPlayer.setUp(contentAdvanceUrl + "v.f30.mp4?" + var_advance, JZVideoPlayer.SCREEN_LAYOUT_LIST, "", false);
                        } else {
                            videoPlayer.setUp(contentUrl + "v.f30.mp4?" + var, JZVideoPlayer.SCREEN_LAYOUT_LIST, "", true);
                        }
                        KLog.e("Video url:" + contentAdvanceUrl + "v.f30.mp4?" + var_advance);
                        videoPlayer.startVideo();
                    }

                    @Override
                    public void onDecodeError() {
                    }
                };
                decoder.decodePath(worksListBean.getWorksId());
            }

            @Override
            public void onVideoProgress(int duration) {
                if (!((boolean) videoPlayer.objects[1])) {
                    KLog.e("进来了吗？onVideoProgressonVideoProgress未支付过的视频");
                    JZMediaManager.instance().mediaPlayer.pause();
//                    helper.getView(R.id.item_video_list_pay).setVisibility(View.VISIBLE);
                    videoPlayer.findViewById(R.id.item_video_list_pay).setVisibility(View.VISIBLE);
                    holder.llDuration.setVisibility(View.VISIBLE);//显示时长
                    holder.llTitle.setVisibility(View.VISIBLE);//显示标题栏
                }
            }
        });
        videoPlayer.findViewById(R.id.video_pay_cacel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KLog.i("info", "点击下次再说");
                videoPlayer.findViewById(R.id.item_video_list_pay).setVisibility(View.GONE);
                JZMediaManager.instance().mediaPlayer.seekTo(0);
                JZMediaManager.instance().mediaPlayer.start();//继续播放免费视频
                holder.llDuration.setVisibility(View.VISIBLE);//隐藏时长
                holder.llTitle.setVisibility(View.VISIBLE);//隐藏标题栏
            }
        });
        videoPlayer.findViewById(R.id.video_pay_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KLog.e("================" + position);
                //点击付费的业务操作;
                if (videoClickInterface != null) {
                    videoClickInterface.onVideoItemClick(v, position, null);
                }
            }
        });
        //top信息
        holder.videoTitle.setText(worksListBean.getActorName());
        holder.videoWatchCount.setText(worksListBean.getWorksPlayNum() + "次播放");
        holder.videoTime.setText(worksListBean.getContentDuration());
        holder.topUserName.setText(worksListBean.getActorName());
        ImageManager.getInstance().loadRoundImage(context, worksListBean.getActorHeadshot(), holder.topUserHeadView);
        //bottom信息
        holder.bottomHotTv.setText(worksListBean.getWorksPraising() + "");
        holder.bottomCommentTv.setText(worksListBean.getWorksCommentNum() + "");
        //收藏
        if (worksListBean.getIsColletion() == 1) {
            holder.bottomCollectIv.setBackgroundResource(R.mipmap.video_collect_yes);
        } else {
            holder.bottomCollectIv.setBackgroundResource(R.mipmap.video_collect_no);
        }
        //点赞
        if (worksListBean.getIsPraising() == 1) {
            holder.bottomHotIv.setBackgroundResource(R.mipmap.video_hot_yes);
        } else {
            holder.bottomHotIv.setBackgroundResource(R.mipmap.video_hot_no);
        }
        holder.topUserHeadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoClickInterface != null) {
                    videoClickInterface.onVideoItemClick(v, position, null);
                }
            }
        });
        //评论
        holder.bottomCommentRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoClickInterface != null) {
                    videoClickInterface.onVideoItemClick(v, position, null);
                }
            }
        });
        //收藏
        holder.bottomCollectRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoClickInterface != null) {
                    videoClickInterface.onVideoItemClick(v, position, holder.bottomCollectIv);
                }
            }
        });
        //分享
//        holder.bottomShareRl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (videoClickInterface != null) {
//                    videoClickInterface.onVideoItemCliclk(v,position,null);
//                }
//            }
//        });
    }

    public void setData(List<WorksListBean> lists) {
        this.mLists = lists;
        notifyDataSetChanged();
    }

    public VideoClickInterface videoClickInterface;

    public VideoClickInterface getVideoClickInterface() {
        return videoClickInterface;
    }

    public void setVideoClickInterface(VideoClickInterface videoClickInterface) {
        this.videoClickInterface = videoClickInterface;
    }

    public interface VideoClickInterface {
        void onVideoItemClick(View v, int position, ImageView view);
    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }


    class SearchHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MyVideoPlayerStandard videoPlayer;
        TextView videoTitle, videoTime, videoWatchCount, videoAuthor;
        public LinearLayout llDuration, llTitle;//时长,标题栏
        //top信息
        private TextView topUserName;
        private ImageView topUserHeadView;
        //bottom信息
        private RelativeLayout bottomHotRl, bottomCommentRl, bottomCollectRl, bottomShareRl;
        private ImageView bottomHotIv, bottomCollectIv;
        private TextView bottomHotTv, bottomCommentTv;

        public SearchHolder(View itemView) {
            super(itemView);
            videoPlayer = (MyVideoPlayerStandard) itemView.findViewById(R.id.video_player);
            videoTitle = (TextView) itemView.findViewById(R.id.tv_title);
            videoTime = (TextView) itemView.findViewById(R.id.tv_duration);
            videoWatchCount = (TextView) itemView.findViewById(R.id.tv_watch_count);
            videoAuthor = (TextView) itemView.findViewById(R.id.tv_author);
            //时间、标题
            llDuration = (LinearLayout) itemView.findViewById(R.id.ll_duration);
            llTitle = (LinearLayout) itemView.findViewById(R.id.ll_title);
            //top信息
            topUserName = (TextView) itemView.findViewById(R.id.item_video_list_top_username);
            topUserHeadView = (ImageView) itemView.findViewById(R.id.item_video_list_top_userhead);
            //bottom信息
            bottomHotRl = (RelativeLayout) itemView.findViewById(R.id.item_video_item_hot_rl);
            bottomHotRl.setOnClickListener(this);
            bottomHotIv = (ImageView) itemView.findViewById(R.id.item_video_item_hot_icon);
            bottomHotTv = (TextView) itemView.findViewById(R.id.item_video_item_hot_tv);
            bottomCommentRl = (RelativeLayout) itemView.findViewById(R.id.item_video_list_comment_rl);
            bottomCommentTv = (TextView) itemView.findViewById(R.id.item_video_item_comment_tv);
            bottomCollectRl = (RelativeLayout) itemView.findViewById(R.id.item_video_list_collect_rl);
            bottomCollectIv = (ImageView) itemView.findViewById(R.id.item_video_item_collect_icon);
            bottomShareRl = (RelativeLayout) itemView.findViewById(R.id.item_video_list_share_rl);
            bottomShareRl.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_video_item_hot_rl: //热度
                    if (videoClickInterface != null) {
                        videoClickInterface.onVideoItemClick(v, getAdapterPosition(),bottomHotIv);
                    }
                    break;
                case R.id.item_video_list_share_rl: //分享
                    if (videoClickInterface != null) {
                        videoClickInterface.onVideoItemClick(v, getAdapterPosition(),null);
                    }
                    break;
            }
        }
    }
}

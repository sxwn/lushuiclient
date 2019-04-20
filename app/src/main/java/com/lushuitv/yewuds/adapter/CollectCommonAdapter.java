package com.lushuitv.yewuds.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lushuitv.yewuds.Image.ImageManager;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.module.entity.LISTBean;
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
 * Date on 2017/9/15.
 */

public class CollectCommonAdapter extends RecyclerView.Adapter<CollectCommonAdapter.MyHolder> {

    private Context mContext;
    private String mChannelCode;
    private boolean isVideoList;

    private List<LISTBean> data;
    //居中大图布局(1.单图文章；2.单图广告；3.视频，中间显示播放图标，右侧显示时长)
    private static final int CENTER_SINGLE_PIC_NEWS = 200;

    /**
     * 视频列表类型
     */
    private static final int VIDEO_LIST_NEWS = 500;


    private VideoItemClickListener listener;

    public void setListener(VideoItemClickListener listener) {
        this.listener = listener;
    }

    private OnShowItemClickListener onShowItemClickListener;

    public CollectCommonAdapter(Context context, List<LISTBean> data) {
        mContext = context;
        this.data = data;
    }

    public void setData(List<LISTBean> lists) {
        this.data.addAll(lists);
        notifyDataSetChanged();
    }

    /**
     * /**
     *
     * @param context     上下文
     * @param channelCode 频道
     * @param isVideoList 是否是视频列表
     * @param data        新闻集合
     */
    public CollectCommonAdapter(Context context, String channelCode, boolean isVideoList, List<LISTBean> data) {
        mContext = context;
        mChannelCode = channelCode;
        this.isVideoList = isVideoList;
        this.data = data;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_list, parent, false);
        MyHolder vh = new MyHolder(view);
        return vh;
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
    public void onBindViewHolder(MyHolder holder, int position) {
        LISTBean listBean = data.get(position);
//        holder.bottomLayout.setVisibility(View.GONE);
        holder.videoTitle.setText(listBean.getWorksName());
        holder.videoWatchCount.setText(listBean.getWorksPlayNum() + "次播放");
        holder.videoDuration.setText(listBean.getContentDuration());
        //videoplayer
        MyVideoPlayerStandard videoPlayer = holder.jcVideoPlayerStandard;
        ImageManager.getInstance().loadImage(mContext, data.get(position).getWorksCover() + "?imageView2/0/w/750/h/350", videoPlayer.thumbImageView);//设置缩略图
        videoPlayer.setAllControlsVisible(GONE, GONE, VISIBLE, GONE, VISIBLE, VISIBLE, GONE);
        videoPlayer.tinyBackImageView.setVisibility(GONE);
        videoPlayer.setPosition(position);//绑定Position
        videoPlayer.titleTextView.setText("");//清除标题,防止复用的时候出现
        videoPlayer.setmOnVideoClickListener(new OnVideoClickListener() {
            @Override
            public void onVideoClickToStart() {

                clickVideoPlayer = videoPlayer;

                holder.llDuration.setVisibility(View.GONE);//隐藏时长
                holder.llTitle.setVisibility(View.GONE);//隐藏标题栏

                VideoUrlDecoder decoder = new VideoUrlDecoder() {
                    @Override
                    public void onSuccess(VideoUrlInfo urlInfo) {
                        KLog.e("收藏视频Fragment中第" + videoPlayer.getPosition() + "条视频数据开始播放" + "workId:" + listBean.getWorksId());
                        String contentAdvanceUrl = urlInfo.getOBJECT().getWorkContent().getContentAdvanceUrl();
                        contentUrl = urlInfo.getOBJECT().getWorkContent().getContentUrl();
                        var = urlInfo.getOBJECT().getVar();
                        String var_advance = urlInfo.getOBJECT().getVar_advance();
                        if (urlInfo.getOBJECT().getBuyRecord() == null) {
                            videoPlayer.setUp(contentAdvanceUrl + "v.f30.mp4?" + var_advance, JZVideoPlayer.SCREEN_LAYOUT_LIST, "", false);
                        } else {
                            KLog.e("");
                            videoPlayer.setUp(contentUrl + "v.f30.mp4?" + var, JZVideoPlayer.SCREEN_LAYOUT_LIST, "", true);
                        }
                        videoPlayer.startVideo();
                    }

                    @Override
                    public void onDecodeError() {
                    }
                };
                decoder.decodePath(listBean.getWorksId());
            }

            @Override
            public void onVideoProgress(int duration) {
                if (!((boolean) videoPlayer.objects[1])) {
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
                //点击付费的业务操作;
                if (listener != null) {
                    listener.itemClickListener(v, position);
                }
            }
        });

        // 是否是多选状态
        if (listBean.isShow()) {
            holder.videoCheckBox.setVisibility(View.VISIBLE);
        } else {
            holder.videoCheckBox.setVisibility(View.GONE);
        }

        holder.videoCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
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
        holder.videoCheckBox.setChecked(listBean.isChecked());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CheckBox videoCheckBox;
        public FrameLayout videoFrameLayout;
        public RelativeLayout payLayout;
        public MyVideoPlayerStandard jcVideoPlayerStandard;
        public TextView videoTitle, videoWatchCount, videoDuration;
        public LinearLayout llDuration, llTitle;//时长,标题栏
        public RelativeLayout commentRl;
        public TextView authorName;
        public ImageView zanIv, shareIv;
        public RelativeLayout bottomLayout;

        public MyHolder(View view) {
            super(view);
            videoCheckBox = (CheckBox) view.findViewById(R.id.collect_fragment_video_item_cb);
            videoFrameLayout = (FrameLayout) view.findViewById(R.id.video_main_framelayout);
            payLayout = (RelativeLayout) view.findViewById(R.id.video_pay_layout);
            jcVideoPlayerStandard = (MyVideoPlayerStandard) view.findViewById(R.id.video_player);
            videoTitle = (TextView) view.findViewById(R.id.tv_title);
            videoWatchCount = (TextView) view.findViewById(R.id.tv_watch_count);
            videoDuration = (TextView) view.findViewById(R.id.tv_duration);
            llDuration = (LinearLayout) view.findViewById(R.id.ll_duration);
            llTitle = (LinearLayout) view.findViewById(R.id.ll_title);
//            authorImageView = (CircleImageView) view.findViewById(R.id.iv_avatar);
//            authorImageView.setOnClickListener(this);
//            authorName = (TextView) view.findViewById(R.id.tv_author);
//            zanIv = (ImageView) view.findViewById(R.id.item_video_list_prase);
//            zanIv.setOnClickListener(this);
//            shareIv = (ImageView) view.findViewById(R.id.iv_share);
//            shareIv.setOnClickListener(this);
//            commentRl = (RelativeLayout) view.findViewById(R.id.item_video_list_comment_rl);
//            commentRl.setOnClickListener(this);
            bottomLayout = (RelativeLayout) view.findViewById(R.id.item_video_list_bottom_layout);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
//                case R.id.iv_avatar://查看个人中心
//                    if (listener != null) {
//                        listener.itemClickListener(v, getAdapterPosition());
//                    }
//                    break;
//                case R.id.item_video_list_comment_rl://评论
//                    if (listener != null) {
//                        listener.itemClickListener(v, getAdapterPosition());
//                    }
//                    break;
//                case R.id.item_video_list_prase://点赞
//                    if (listener != null) {
//                        listener.itemClickListener(v, getAdapterPosition());
//                    }
//                    break;
//                case R.id.iv_share://分享
//                    if (listener != null) {
//                        listener.itemClickListener(v, getAdapterPosition());
//                    }
//                    break;
            }
        }
    }

    public interface VideoItemClickListener {
        void itemClickListener(View v, int position);
    }

    public interface OnShowItemClickListener {
        void onShowItemClick(LISTBean bean);
    }

    public OnShowItemClickListener getOnShowItemClickListener() {
        return onShowItemClickListener;
    }

    public void setOnShowItemClickListener(OnShowItemClickListener onShowItemClickListener) {
        this.onShowItemClickListener = onShowItemClickListener;
    }
}

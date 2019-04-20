package com.lushuitv.yewuds.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lushuitv.yewuds.Image.ImageManager;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.activity.VideoCommentActivity;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.app.ApiConstant;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.detail.RulePageActivity;
import com.lushuitv.yewuds.fragment.HomeNewFragment;
import com.lushuitv.yewuds.module.entity.WorksListBean;
import com.lushuitv.yewuds.module.response.BannerResponse;
import com.lushuitv.yewuds.view.MyVideoPlayerStandard;
import com.lushuitv.yewuds.view.mzbanner.MZBannerView;
import com.lushuitv.yewuds.view.mzbanner.holder.MZHolderCreator;
import com.lushuitv.yewuds.view.mzbanner.holder.MZViewHolder;
import com.socks.library.KLog;

import java.util.List;

import cn.jzvd.JZMediaManager;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.OnVideoClickListener;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Description
 * Created by weip
 * Date on 2017/9/4.
 */

public class VideoNewAdapter extends RecyclerView.Adapter {

    private static final int ITEM_TYPE_HEAD_BANNER = 0;
    private static final int ITEM_TYPE_ITEM_VIDEO = 1;
    private Context context;
    private boolean isShowPager;
    private List<WorksListBean> mLists;
    private LayoutInflater mInflater;

    public VideoNewAdapter(Context context, List<WorksListBean> mLists, boolean isShowPager) {
        this.context = context;
        this.mLists = mLists;
        this.isShowPager = isShowPager;
        this.mInflater = LayoutInflater.from(context);

    }

    @Override
    public int getItemViewType(int position) {
        if (isShowPager) {
            if (position == 0) {
                return ITEM_TYPE_HEAD_BANNER;
            } else {
                return ITEM_TYPE_ITEM_VIDEO;
            }
        } else {
            return ITEM_TYPE_ITEM_VIDEO;
        }
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (isShowPager) {
            return mLists == null ? 0 : mLists.size() + 1;
        } else {
            return mLists == null ? 0 : mLists.size();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isShowPager) {
            if (viewType == ITEM_TYPE_HEAD_BANNER) {
                return new VideoHeadHolder(LayoutInflater.from(context).inflate(R.layout.view_new_pager, parent, false));
            } else if (viewType == ITEM_TYPE_ITEM_VIDEO) {
                return new VideoItemHolder
                        (LayoutInflater.from(context).inflate(R.layout.item_video_list, parent, false));
            } else {
                return null;
            }
        } else {
            return new VideoItemHolder
                    (LayoutInflater.from(context).inflate(R.layout.item_video_list, parent, false));
        }
    }


    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isShowPager) {
            if (holder instanceof VideoHeadHolder) {
                bindHeadData((VideoHeadHolder) holder);
            } else if (holder instanceof VideoItemHolder) {
                bindVideoData((VideoItemHolder) holder, position - 1);
            }
        } else {
            bindVideoData((VideoItemHolder) holder, position);
        }

    }

    private void bindHeadData(VideoHeadHolder holder) {
        holder.homeNewBannerView.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                context.startActivity(new Intent(context, RulePageActivity.class)
                        .putExtra("url", "http://www.lushuitv.com/"));
                if (position == 0) {
//                    context.startActivity(new Intent(context, RulePageActivity.class)
//                            .putExtra("url", "http://www.lushuitv.com/"));
                } else if (position == 1) {
//                    context.startActivity(new Intent(context, RulePageActivity.class)
//                            .putExtra("url", ApiConstant.BASE_SERVER_H5_URL + "h5/booking.html"));
//                    context.startActivity(new Intent(context, RulePageActivity.class)
//                            .putExtra("url", "http://www.lushuitv.com/"));
                } else if (position == 2) {
//                    context.startActivity(new Intent(context, RulePageActivity.class)
//                            .putExtra("url", "https://weidian.com/item.html?itemID=2206284888&wfr=c&ifr=itemdetail"));
//                    context.startActivity(new Intent(context, RulePageActivity.class)
//                            .putExtra("url", "http://www.lushuitv.com/"));
                } else if (position == 3) {
//                    context.startActivity(new Intent(context, RulePageActivity.class)
//                            .putExtra("url", ApiConstant.BASE_SERVER_H5_URL + "/h5/rule/share_rule.html"));
                }
            }
        });
        if (Config.getCachedDataType(context) == 1) {
            //测试数据 getVerBanner()
            ApiRetrofit.getInstance().getApiService().getBanner()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BannerResponse>() {
                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }

                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BannerResponse bannerResponse) {
                            if ("0".equals(bannerResponse.getSTATUS())) {
                                List<BannerResponse.LISTBean> list = bannerResponse.getLIST();
                                //设置数据
                                holder.homeNewBannerView.setPages(list, new MZHolderCreator<BannerViewHolder>() {
                                    @Override
                                    public BannerViewHolder createViewHolder() {
                                        return new BannerViewHolder();
                                    }
                                });
                                holder.homeNewBannerView.start();
                            }
                        }
                    });

        } else if (Config.getCachedDataType(context) == 2) {
            //正式数据
            ApiRetrofit.getInstance().getApiService().getBanner()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BannerResponse>() {

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }

                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BannerResponse bannerResponse) {
                            if ("0".equals(bannerResponse.getSTATUS())) {
                                List<BannerResponse.LISTBean> list = bannerResponse.getLIST();
                                if (list != null && list.size() > 0) {
                                    //设置数据
                                    holder.homeNewBannerView.setPages(list, new MZHolderCreator<BannerViewHolder>() {
                                        @Override
                                        public BannerViewHolder createViewHolder() {
                                            return new BannerViewHolder();
                                        }
                                    });
                                    holder.homeNewBannerView.start();
                                }
                            }
                        }
                    });
        }
    }

    String contentUrl, var;

    MyVideoPlayerStandard clickVideoPlayer = null;

    public void setResult() {
        if (clickVideoPlayer != null) {
            clickVideoPlayer.findViewById(R.id.item_video_list_pay).setVisibility(View.GONE);
            clickVideoPlayer.objects[1] = true;
            clickVideoPlayer.setUp(contentUrl + "v.f30.mp4?" + var, JZVideoPlayer.SCREEN_LAYOUT_LIST, "", true);
            clickVideoPlayer.startVideo();
        }
    }

    /**
     * 绑定视频数据
     *
     * @param holder
     */
    private void bindVideoData(VideoItemHolder holder, int position) {
        if (Config.getCachedDataType(context) == 1) {
            holder.bottomCollectRl.setVisibility(View.INVISIBLE);
            holder.bottomShareRl.setVisibility(View.INVISIBLE);
        } else {
            holder.bottomCollectRl.setVisibility(View.VISIBLE);
            holder.bottomShareRl.setVisibility(View.VISIBLE);
        }
        WorksListBean worksListBean = mLists.get(position);
        // 是否是多选状态
        if (worksListBean.isShow()) {
            holder.videoCheckBox.setVisibility(View.VISIBLE);
        } else {
            holder.videoCheckBox.setVisibility(View.GONE);
        }
        MyVideoPlayerStandard videoPlayer = holder.videoPlayer;
        ImageManager.getInstance().loadImage(context,
                worksListBean.getWorksCover() + "?imageView2/0/w/750/h/350", videoPlayer.thumbImageView);
        if (worksListBean.getWorksPrice() == 0) {
            videoPlayer.getVideoType().setBackgroundResource(R.drawable.video_free);
        } else if (worksListBean.getWorksPrice() == 1) {
            videoPlayer.getVideoType().setBackgroundResource(R.drawable.video_rmb_one);
        } else if (worksListBean.getWorksPrice() == 3) {
            videoPlayer.getVideoType().setBackgroundResource(R.drawable.video_rmb_three);
        } else if (worksListBean.getWorksPrice() == 6) {
            videoPlayer.getVideoType().setBackgroundResource(R.drawable.video_rmb_six);
        }
        videoPlayer.setAllControlsVisible(GONE, GONE, VISIBLE, GONE, VISIBLE, VISIBLE, GONE);
        videoPlayer.tinyBackImageView.setVisibility(GONE);
        videoPlayer.setPosition(position);//绑定Position
        videoPlayer.titleTextView.setText("");//清除标题,防止复用的时候出现
        //播放视频接口
        videoPlayer.setmOnVideoClickListener(new OnVideoClickListener() {
            @Override
            public void onVideoClickToStart() {
                Intent commentIntent = new Intent(context, VideoCommentActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable(HomeNewFragment.SER_KEY, worksListBean);
                commentIntent.putExtras(mBundle);
                context.startActivity(commentIntent);
//                clickVideoPlayer = videoPlayer;
//                holder.llDuration.setVisibility(View.GONE);//隐藏时长
//                holder.llTitle.setVisibility(View.GONE);//隐藏标题栏
//                VideoUrlDecoder decoder = new VideoUrlDecoder() {
//                    @Override
//                    public void onSuccess(VideoUrlInfo urlInfo) {
//                        KLog.e("第" + videoPlayer.getPosition() + "条视频数据开始播放" + "workId:" + worksListBean.getWorksId());
//                        String contentAdvanceUrl = urlInfo.getOBJECT().getWorkContent().getContentAdvanceUrl();
//                        contentUrl = urlInfo.getOBJECT().getWorkContent().getContentUrl();
//                        var = urlInfo.getOBJECT().getVar();
//                        String var_advance = urlInfo.getOBJECT().getVar_advance();
//                        if (urlInfo.getOBJECT().getBuyRecord() == null) {
//                            videoPlayer.setUp(contentAdvanceUrl + "v.f30.mp4?" + var_advance, JZVideoPlayer.SCREEN_LAYOUT_LIST, "", false);
//                        } else {
//                            videoPlayer.setUp(contentUrl + "v.f30.mp4?" + var, JZVideoPlayer.SCREEN_LAYOUT_LIST, "", true);
//                        }
//                        videoPlayer.startVideo();
//                    }
//
//                    @Override
//                    public void onDecodeError() {
//                    }
//                };
//                decoder.decodePath(worksListBean.getWorksId());
            }

            @Override
            public void onVideoProgress(int duration) {
//                if (!((boolean) videoPlayer.objects[1])) {
//                    KLog.e("进来了吗？onVideoProgressonVideoProgress未支付过的视频");
//                    JZMediaManager.instance().mediaPlayer.pause();
//                    videoPlayer.findViewById(R.id.item_video_list_pay).setVisibility(View.VISIBLE);
//                    holder.llDuration.setVisibility(View.VISIBLE);//显示时长
//                    holder.llTitle.setVisibility(View.VISIBLE);//显示标题栏
//                }
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
        holder.videoCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    worksListBean.setChecked(true);
                } else {
                    worksListBean.setChecked(false);
                }
                //接口回调
                onShowItemClickListener.onShowItemClick(worksListBean);
            }
        });
        // 必须放在监听后面
        holder.videoCheckBox.setChecked(worksListBean.isChecked());
        //top信息
        holder.topUserName.setText(worksListBean.getActorName());
        holder.videoTitle.setText(worksListBean.getWorksName());
        holder.videoWatchCount.setText(worksListBean.getWorksPlayNum() + "次播放");
        holder.videoTime.setText(worksListBean.getContentDuration());
        ImageManager.getInstance().loadRoundImage(context, worksListBean.getActorHeadshot(), holder.topUserIcon);
        holder.topUserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoClickInterface != null) {
                    videoClickInterface.onVideoItemClick(v, position, null);
                }
            }
        });
        //bottom信息、热度
        holder.bottomHotRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoClickInterface != null) {
                    videoClickInterface.onVideoItemClick(v, position, holder.bottomHotIv);
                }
            }
        });
        if (worksListBean.getIsColletion() == 1) {
            holder.bottomHotIv.setBackgroundResource(R.mipmap.video_hot_yes);//收藏、热度
        } else {
            holder.bottomHotIv.setBackgroundResource(R.mipmap.video_hot_no);
        }
        holder.bottomHotCount.setText(worksListBean.getWorksPraising() + "");
        //评论
        KLog.e("====================" + worksListBean.getWorksCommentNum());
        holder.bottomCommentCount.setText(worksListBean.getWorksCommentNum() + "");
        holder.bottomCommentRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoClickInterface != null) {
                    videoClickInterface.onVideoItemClick(v, position, null);
                }
            }
        });
        //收藏
        if (Config.getCachedDataType(context) == 1) {//假数据隐藏收藏
            holder.bottomCollectRl.setVisibility(View.INVISIBLE);
        } else {
            holder.bottomCollectRl.setVisibility(View.VISIBLE);
        }
        holder.bottomCollectRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoClickInterface != null) {
                    videoClickInterface.onVideoItemClick(v, position, holder.bottomCollectIv);
                }
            }
        });
        //分享
        holder.bottomShareRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoClickInterface != null) {
                    videoClickInterface.onVideoItemClick(v, position, null);
                }
            }
        });
    }

    public void setData(List<WorksListBean> lists) {
        if (isShowPager) {
            if (this.mLists.size() > 0) {
                this.mLists.clear();
                this.mLists.addAll(lists);
            } else {
                this.mLists.addAll(lists);
            }
        } else {
            this.mLists = lists;
        }
        KLog.e("sizesize:" + mLists.size());
        if (mLists.size() == 0)
            return;
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

    //头holder
    class VideoHeadHolder extends RecyclerView.ViewHolder {
        private MZBannerView homeNewBannerView;

        public VideoHeadHolder(View itemView) {
            super(itemView);
            homeNewBannerView = (MZBannerView) itemView.findViewById(R.id.view_new_pager_banner);
        }
    }

    private class BannerViewHolder implements MZViewHolder<BannerResponse.LISTBean> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = mInflater.inflate(R.layout.banner_item, null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, BannerResponse.LISTBean data) {
            // 数据绑定
            ImageManager.getInstance().loadImage(context, data.getBannerImg() + "?imageView2/0/w/750/h/350", mImageView);
        }
    }

    //视频holder
    class VideoItemHolder extends RecyclerView.ViewHolder {
        public CheckBox videoCheckBox;
        private ImageView topUserIcon;
        private TextView topUserName;
        MyVideoPlayerStandard videoPlayer;
        TextView videoTitle, videoTime, videoWatchCount;
        RelativeLayout bottomHotRl, bottomCommentRl, bottomCollectRl, bottomShareRl;
        TextView bottomHotCount, bottomCommentCount;
        ImageView bottomHotIv, bottomCollectIv;
        public LinearLayout llDuration, llTitle;//时长,标题栏

        public VideoItemHolder(View itemView) {
            super(itemView);
            videoCheckBox = (CheckBox) itemView.findViewById(R.id.collect_fragment_video_item_cb);
            //top信息
            topUserName = (TextView) itemView.findViewById(R.id.item_video_list_top_username);
            topUserIcon = (ImageView) itemView.findViewById(R.id.item_video_list_top_userhead);
            //video
            videoPlayer = (MyVideoPlayerStandard) itemView.findViewById(R.id.video_player);
            videoTitle = (TextView) itemView.findViewById(R.id.tv_title);
            videoTime = (TextView) itemView.findViewById(R.id.tv_duration);
            videoWatchCount = (TextView) itemView.findViewById(R.id.tv_watch_count);
            //时间、标题
            llDuration = (LinearLayout) itemView.findViewById(R.id.ll_duration);
            llTitle = (LinearLayout) itemView.findViewById(R.id.ll_title);
            //底部热度
            bottomHotRl = (RelativeLayout) itemView.findViewById(R.id.item_video_item_hot_rl);
            bottomHotIv = (ImageView) itemView.findViewById(R.id.item_video_item_hot_icon);
            bottomHotCount = (TextView) itemView.findViewById(R.id.item_video_item_hot_tv);
            //底部评论数
            bottomCommentRl = (RelativeLayout) itemView.findViewById(R.id.item_video_list_comment_rl);
            bottomCommentCount = (TextView) itemView.findViewById(R.id.item_video_item_comment_tv);
            bottomShareRl = (RelativeLayout) itemView.findViewById(R.id.item_video_list_share_rl);
            //底部收藏数
            bottomCollectRl = (RelativeLayout) itemView.findViewById(R.id.item_video_list_collect_rl);
            bottomCollectIv = (ImageView) itemView.findViewById(R.id.item_video_item_collect_icon);
        }
    }


    public interface VideoItemClickListener {
        void itemClickListener(View v, int position);
    }

    private VideoItemClickListener listener;

    public void setListener(VideoItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnShowItemClickListener {
        void onShowItemClick(WorksListBean bean);
    }

    private OnShowItemClickListener onShowItemClickListener;

    public OnShowItemClickListener getOnShowItemClickListener() {
        return onShowItemClickListener;
    }

    public void setOnShowItemClickListener(OnShowItemClickListener onShowItemClickListener) {
        this.onShowItemClickListener = onShowItemClickListener;
    }

}

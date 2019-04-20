package com.lushuitv.yewuds.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lushuitv.yewuds.Image.ImageManager;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.activity.ImageViewPagerActivtiy;
import com.lushuitv.yewuds.activity.VideoCommentActivity;
import com.lushuitv.yewuds.fragment.HomeNewFragment;
import com.lushuitv.yewuds.module.entity.WorksListBean;
import com.lushuitv.yewuds.module.response.ActorWorkInfo;
import com.lushuitv.yewuds.module.response.VideoUrlInfo;
import com.lushuitv.yewuds.utils.UIUtils;
import com.lushuitv.yewuds.utils.VideoUrlDecoder;
import com.lushuitv.yewuds.view.MyGridView;
import com.lushuitv.yewuds.view.MyVideoPlayerStandard;
import com.socks.library.KLog;

import java.util.List;

import cn.jzvd.JZMediaManager;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.OnVideoClickListener;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


/**
 * Description 模特个人主页
 * Created by weip
 * Date on 2017/9/4.
 */

public class ModelPageAdapter extends BaseAdapter {

    final int TYPE_ONE = 0;
    final int TYPE_TWO = 1;
    final int TYPE_THREE = 2;
    final int ITEM_TYPE_COUNT = 2;
    private int actorId;

    private List<WorksListBean> info;

    private Context context;

    public VideoItemClickListener videoItemClickListener;

    public VideoItemClickListener getVideoItemClickListener() {
        return videoItemClickListener;
    }

    public void setVideoItemClickListener(VideoItemClickListener videoItemClickListener) {
        this.videoItemClickListener = videoItemClickListener;
    }

    public ModelPageAdapter(Context context, List<WorksListBean> info, int actorId) {
        this.context = context;
        this.info = info;
        this.actorId = actorId;
    }
    ActorWorkInfo.OBJECTBean bean;
    public void refreshData(List<WorksListBean> info, ActorWorkInfo.OBJECTBean bean) {
        this.info.addAll(info);
        this.bean = bean;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return info.size();
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
    public int getItemViewType(int position) {
        if (info.get(position).getWorksType() == 1) {
            return TYPE_ONE;
        } else if (info.get(position).getWorksType() == 2) {
            return TYPE_TWO;
        } else {
            return TYPE_THREE;
        }
    }

    @Override
    public int getViewTypeCount() {
        return ITEM_TYPE_COUNT;
    }

    private MyVideoPlayerStandard clickVideoPlayer;

    String contentUrl, var;

    public void setResult() {
        if (clickVideoPlayer != null) {
            clickVideoPlayer.findViewById(R.id.item_video_list_pay).setVisibility(View.GONE);
            clickVideoPlayer.objects[1] = true;
            clickVideoPlayer.setUp(contentUrl + "v.f30.mp4?" + var, JZVideoPlayer.SCREEN_LAYOUT_LIST, "", true);
            clickVideoPlayer.startVideo();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderOne holderOne;
        ViewHolderTwo holderTwo;
        //对listView设置不同布局
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_ONE:
                //第一种item
                if (convertView == null) {
                    holderOne = new ViewHolderOne();
                    convertView = LayoutInflater.from(context).inflate(R.layout.model_page_item_one, null);
                    holderOne.userHeadView = (ImageView) convertView.findViewById(R.id.model_page_item_one_userimage);
                    holderOne.name = (TextView) convertView.findViewById(R.id.model_page_item_one_name);
                    holderOne.type = (TextView) convertView.findViewById(R.id.model_page_item_one_type);
                    holderOne.date = (TextView) convertView.findViewById(R.id.model_page_item_one_date);
                    holderOne.jcVideoPlayerStandard = (MyVideoPlayerStandard) convertView.findViewById(R.id.model_page_item_one_video_player);
                    convertView.setTag(holderOne);
                } else {
                    holderOne = (ViewHolderOne) convertView.getTag();
                }
                ImageManager.getInstance().loadRoundImage(context,bean.getActorHeadshot(),holderOne.userHeadView);
                holderOne.name.setText(bean.getActorName());
                holderOne.type.setText(info.get(position).getWorksName());
                holderOne.date.setText(info.get(position).getWorksDate().substring(5,info.get(position).getWorksDate().length()));
                MyVideoPlayerStandard videoPlayer = holderOne.jcVideoPlayerStandard;
                ImageManager.getInstance().loadImage(context, info.get(position).getWorksCover() + "?imageView2/0/w/750/h/350",
                        videoPlayer.thumbImageView);//设置缩略图
                videoPlayer.setAllControlsVisible(GONE, GONE, VISIBLE, GONE, VISIBLE, VISIBLE, GONE);
                videoPlayer.tinyBackImageView.setVisibility(GONE);
                videoPlayer.setPosition(position);//绑定Position
                videoPlayer.titleTextView.setText("");//清除标题,防止复用的时候出现
                videoPlayer.setmOnVideoClickListener(new OnVideoClickListener() {
                    @Override
                    public void onVideoClickToStart() {
                        clickVideoPlayer = videoPlayer;
                        VideoUrlDecoder decoder = new VideoUrlDecoder() {
                            @Override
                            public void onSuccess(VideoUrlInfo urlInfo) {
                                KLog.e("艺人中心页第" + videoPlayer.getPosition() + "条视频数据开始播放" + "workId:" + info.get(position).getWorksId());
                                Intent commentIntent = new Intent(context, VideoCommentActivity.class);
                                Bundle mBundle = new Bundle();
                                mBundle.putSerializable(HomeNewFragment.SER_KEY,info.get(position));
                                commentIntent.putExtras(mBundle);
                                context.startActivity(commentIntent);
//                                String contentAdvanceUrl = urlInfo.getOBJECT().getWorkContent().getContentAdvanceUrl();
//                                String var_advance = urlInfo.getOBJECT().getVar_advance();
//                                contentUrl = urlInfo.getOBJECT().getWorkContent().getContentUrl();
//                                var = urlInfo.getOBJECT().getVar();
//                                if (urlInfo.getOBJECT().getBuyRecord() == null) {
//                                    videoPlayer.setUp(contentAdvanceUrl + "v.f30.mp4?" + var_advance, JZVideoPlayer.SCREEN_LAYOUT_NORMAL, "", false);
//                                } else {
//                                    videoPlayer.setUp(contentUrl + "v.f30.mp4?" + var, JZVideoPlayer.SCREEN_LAYOUT_NORMAL, "", true);
//                                }
//                                videoPlayer.startVideo();
                            }

                            @Override
                            public void onDecodeError() {
                            }
                        };
                        decoder.decodePath(info.get(position).getWorksId());
                    }

                    @Override
                    public void onVideoProgress(int duration) {
//                        if (!((boolean) videoPlayer.objects[1])) {
//                            videoPlayer.findViewById(R.id.item_video_list_pay).setVisibility(View.VISIBLE);
//                            JZMediaManager.instance().mediaPlayer.pause();
//                        }
                    }
                });
                videoPlayer.findViewById(R.id.video_pay_cacel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        videoPlayer.findViewById(R.id.item_video_list_pay).setVisibility(View.GONE);
                        JZMediaManager.instance().mediaPlayer.seekTo(0);
                        JZMediaManager.instance().mediaPlayer.start();//继续播放免费视频
                    }
                });
                videoPlayer.findViewById(R.id.video_pay_commit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击付费的业务操作;
                        if (videoItemClickListener != null) {
                            videoItemClickListener.onVideoItemCliclk(v, position);
                        }
                    }
                });
                break;
            case TYPE_TWO:
                //第二种item
                if (convertView == null) {
                    holderTwo = new ViewHolderTwo();
                    convertView = LayoutInflater.from(context).inflate(R.layout.model_page_item_two, null);
                    holderTwo.userHeadView = (ImageView) convertView.findViewById(R.id.model_page_item_two_userimage);
                    holderTwo.name = (TextView) convertView.findViewById(R.id.model_page_item_two_name);
                    holderTwo.type = (TextView) convertView.findViewById(R.id.model_page_item_two_type);
                    holderTwo.date = (TextView) convertView.findViewById(R.id.model_page_item_two_date);
                    holderTwo.myGridView = (MyGridView) convertView.findViewById(R.id.model_page_item_two_gridview);
                    convertView.setTag(holderTwo);
                } else {
                    holderTwo = (ViewHolderTwo) convertView.getTag();
                }
                //赋值
                ImageManager.getInstance().loadRoundImage(context,bean.getActorHeadshot(),holderTwo.userHeadView);
                holderTwo.name.setText(bean.getActorName());
                holderTwo.type.setText(info.get(position).getWorksName() + "");
                holderTwo.date.setText(info.get(position).getWorksDate().substring(5,info.get(position).getWorksDate().length()));
                holderTwo.myGridView.setAdapter(new ModelPageImageAdapter(context, info.get(position).getContentList()));
                holderTwo.myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int minPosition, long id) {
                        Intent in = new Intent(context, ImageViewPagerActivtiy.class);
                        in.putExtra("worksId", info.get(position).getWorksId());
                        in.putExtra("worksIsCollect", info.get(position).getIsColletion());
                        in.putExtra("worksActor",actorId);
                        in.putExtra("minposition",minPosition);
                        KLog.e("worksId"+info.get(position).getWorksId()+",worksActor:"+actorId);
                        context.startActivity(in);
                    }
                });
                break;

        }
        return convertView;
    }

    class ViewHolderOne {
        ImageView userHeadView;
        TextView name, type, date, desc;
        MyVideoPlayerStandard jcVideoPlayerStandard;
    }

    class ViewHolderTwo {
        TextView name, type, date, desc;
        ImageView userHeadView;
        //带有图片的网格布局
        MyGridView myGridView;
    }


    public interface VideoItemClickListener {
        void onVideoItemCliclk(View v, int position);
    }

}

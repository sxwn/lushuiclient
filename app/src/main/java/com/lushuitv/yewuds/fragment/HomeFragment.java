package com.lushuitv.yewuds.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.adapter.ChannelAdapter;
import com.lushuitv.yewuds.constant.Constants;
import com.lushuitv.yewuds.actor.ActorDetailActivity;
import com.lushuitv.yewuds.module.entity.Channel;
import com.lushuitv.yewuds.search.SearchActivity;

import java.util.ArrayList;
import java.util.List;
import cn.jzvd.JZVideoPlayer;
import me.weyye.library.colortrackview.ColorTrackTabLayout;

/**
 * 首页
 * Created by weip on 2017/8/25.
 */

public class HomeFragment extends Fragment {

    ColorTrackTabLayout mTabChannel;

    ImageView ivAddChannel;

    RadioGroup homeRg;

    RadioButton homeRbNew, homeRbImage, homeRbGood;

    ViewPager mVpContent;


    private List<Channel> mSelectedChannels = new ArrayList<Channel>();
    private List<Fragment> mChannelVideoFragments = new ArrayList<Fragment>();

    public View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        initView(mView);
        initData();
        initListener();
        return mView;
    }

    public void initView(View rootView) {
//        mTabChannel = (ColorTrackTabLayout) rootView.findViewById(R.id.tab_channel);
        homeRg = (RadioGroup) rootView.findViewById(R.id.home_fragment_rg);
        homeRbNew = (RadioButton) rootView.findViewById(R.id.home_fragment_rb_new);
        homeRbImage = (RadioButton) rootView.findViewById(R.id.home_fragment_rb_image);
        homeRbGood = (RadioButton) rootView.findViewById(R.id.home_fragment_rb_good);
        ivAddChannel = (ImageView) rootView.findViewById(R.id.iv_search);
        ivAddChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });

        mVpContent = (ViewPager) rootView.findViewById(R.id.vp_content);
        mVpContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //当页签切换的时候，如果有播放视频，则释放资源
                JZVideoPlayer.releaseAllVideos();
                homeRg.setOnCheckedChangeListener(null);
                setRadioGroupListener();
                switch (position) {
                    case 0:
                        homeRbNew.setChecked(true);
                        homeRbImage.setChecked(false);
                        homeRbGood.setChecked(false);
                        break;
                    case 1:
                        homeRbNew.setChecked(false);
                        homeRbImage.setChecked(true);
                        homeRbGood.setChecked(false);
                        break;
                    case 2:
                        homeRbNew.setChecked(false);
                        homeRbImage.setChecked(false);
                        homeRbGood.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setRadioGroupListener() {
        homeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.home_fragment_rb_new:
                        //最新
                        mVpContent.setCurrentItem(0);
                        break;
                    case R.id.home_fragment_rb_image:
                        //图片
                        mVpContent.setCurrentItem(1);
                        break;
                    case R.id.home_fragment_rb_good:
                        //特辑
                        mVpContent.setCurrentItem(2);
                        break;
                }
            }
        });
    }

    public void initData() {
        //初始化数据
        String[] channels = getResources().getStringArray(R.array.channel);
        String[] channelCodes = getResources().getStringArray(R.array.channel_code);
        for (int i = 0; i < channelCodes.length; i++) {
            String title = channels[i];
            String code = channelCodes[i];
            mSelectedChannels.add(new Channel(title, code));
        }
        for (Channel channel : mSelectedChannels) {
            if ("new".equals(channel.channelCode)) {
                HomeNewFragment homeNewFragment = new HomeNewFragment();
                mChannelVideoFragments.add(homeNewFragment);
            } else if ("image".equals(channel.channelCode)) {
                UnfreeFragment unfreeFragment = new UnfreeFragment();//免费图片
                mChannelVideoFragments.add(unfreeFragment);
            } else if ("all".equals(channel.channelCode)) {
                VideoListFragment videosFragment = new VideoListFragment();//视频列表图片
                Bundle bundle = new Bundle();
                bundle.putString(Constants.CHANNEL_CODE, channel.channelCode);
                videosFragment.setArguments(bundle);
                mChannelVideoFragments.add(videosFragment);//添加到集合中
            }
        }
    }
    public void initListener() {
        ChannelAdapter channelAdapter = new ChannelAdapter(mChannelVideoFragments, mSelectedChannels, getChildFragmentManager());
        mVpContent.setAdapter(channelAdapter);
        setRadioGroupListener();
    }

}

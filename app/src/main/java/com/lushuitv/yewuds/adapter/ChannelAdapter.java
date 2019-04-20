package com.lushuitv.yewuds.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lushuitv.yewuds.module.entity.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 频道的adapter
 */

public class ChannelAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;
    private List<Channel> mChannels;

    public ChannelAdapter(List<Fragment> fragmentList, List<Channel> channelList, FragmentManager fm) {
        super(fm);
        mFragments = fragmentList != null ? fragmentList : new ArrayList<>();
        mChannels = channelList != null ? channelList : new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mChannels.get(position).title;
    }
}

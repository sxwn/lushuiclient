package com.lushuitv.yewuds.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import java.util.ArrayList;
import java.util.List;

/**
 * 首页页签的adapter
 * Created by weip on 2017/8/14.
 */

public class MainTabAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments = new ArrayList<Fragment>();

    public MainTabAdapter(List<Fragment> fragmentList,FragmentManager fm) {
        super(fm);
        if (fragmentList != null){
            mFragments = fragmentList;
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}

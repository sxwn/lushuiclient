package com.lushuitv.yewuds.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * Created by weip
 * Date on 2017/11/1.
 */

public class MineCollectAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments = new ArrayList<Fragment>();

    public MineCollectAdapter(List<Fragment> fragmentList,FragmentManager fm) {
        super(fm);
        if (fragmentList != null){
            this.mFragments = fragmentList;
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

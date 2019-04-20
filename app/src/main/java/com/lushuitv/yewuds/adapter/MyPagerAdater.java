package com.lushuitv.yewuds.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.lushuitv.yewuds.module.response.BannerResponse;

/**
 * 首页Fragment轮播banner的adapter
 */

public class MyPagerAdater extends PagerAdapter {

    private Context context;
    List<View> viewList = null;
    private List<BannerResponse.LISTBean> mLists;

    public MyPagerAdater(Context context, List<View> viewList, List<BannerResponse.LISTBean> mLists) {
        this.context = context;
        this.viewList = viewList;
        this.mLists = mLists;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // 对ViewPager页号求模取出View列表中要显示的项
        View view = viewList.get(position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent in = new Intent(context, ModelPageActivity.class);
//                in.putExtra("actor_id", mLists.get(position).getBannerActor());
//                context.startActivity(in);
            }
        });
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

}

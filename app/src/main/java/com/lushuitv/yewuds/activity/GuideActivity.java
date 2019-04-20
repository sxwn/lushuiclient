package com.lushuitv.yewuds.activity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.lushuitv.yewuds.MainActivity;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.app.MyApp;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.constant.Config;

/**
 * Description
 * Created by weip
 * Date on 2017/10/25.
 */

public class GuideActivity extends BaseActivity {

    private View mView;
    private ViewPager vp;

    @Override
    protected void initOptions() {
        vp = (ViewPager) findViewById(R.id.vp_guide_viewpager);
        // 设定viewPager的adapter
        vp.setAdapter(new MyPagerAdapter());
        // 实现最后一页时条页面的逻辑
        vp.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 2) {
                    toJumpMainActivity();
                }
            }
        });
    }

    @Override
    protected View initContentView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去除状态栏
        mView = View.inflate(this, R.layout.activity_guide, null);
        return mView;
    }

    @Override
    public void initToolbar() {
        appBarLayout.setVisibility(View.GONE);
    }


    private void toJumpMainActivity() {
        MyApp.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                // 修改第一次访问的标记 false 表示已经不是第一次访问
                Config.putBoolean("first", false);
                finish();
            }
        },1000);
    }



    class MyPagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView image = new ImageView(GuideActivity.this);
            image.setBackgroundResource(R.drawable.guide_one);
            if (position == 0) {
                image.setBackgroundResource(R.drawable.guide_one);
            } else if (position == 1) {
                image.setBackgroundResource(R.drawable.guide_two);
            } else if (position == 2) {
                image.setBackgroundResource(R.drawable.guide_three);
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toJumpMainActivity();
                    }
                });
            }
            container.addView(image);
            return image;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}

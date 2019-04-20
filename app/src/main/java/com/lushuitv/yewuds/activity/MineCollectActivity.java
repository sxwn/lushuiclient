package com.lushuitv.yewuds.activity;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.adapter.MineCollectAdapter;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.flyn.Eyes;
import com.lushuitv.yewuds.fragment.CollectImageFragment;
import com.lushuitv.yewuds.fragment.CollectVideoFragment;
import com.lushuitv.yewuds.module.entity.WorksListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * Created by weip
 * Date on 2017/11/1.
 */

public class MineCollectActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    RadioGroup collectRg;

    RadioButton imageRb;

    RadioButton videoRb;

    ViewPager collectVp;

    private CollectImageFragment imageFragment;

    private CollectVideoFragment videoFragment;

    private List<Fragment> list = new ArrayList<Fragment>();

    private View mView;

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.activity_mine_collect, null);
        return mView;
    }

    @Override
    public void initToolbar() {
        appBarLayout.setVisibility(View.GONE);
    }

    @Override
    public void initOptions() {
        Eyes.setStatusBarColor(this, getResources().getColor(R.color.black));
        back = (ImageView) findViewById(R.id.iv_back);
        back.setOnClickListener(this);
        collectRg = (RadioGroup)findViewById(R.id.fragment_collect_rg);
        imageRb = (RadioButton)findViewById(R.id.fragment_collect_rb_image);
        videoRb = (RadioButton)findViewById(R.id.fragment_collect_rb_video);
        collectVp = (ViewPager)findViewById(R.id.fragment_collect_vp);
        imageFragment = new CollectImageFragment();
        videoFragment = new CollectVideoFragment();
        list.add(imageFragment);
        list.add(videoFragment);
        setRadioGroupListener();
        collectVp.setAdapter(new MineCollectAdapter(list,this.getSupportFragmentManager()));
        collectVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                collectRg.setOnCheckedChangeListener(null);
                setRadioGroupListener();
                switch (position) {
                    case 0:
                        imageRb.setChecked(true);
                        videoRb.setChecked(false);
                        break;
                    case 1:
                        videoRb.setChecked(true);
                        imageRb.setChecked(false);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        titleRightView = (ImageView)findViewById(R.id.fragment_collect_delete);
        titleRightView.setOnClickListener(this);
    }

    ImageView titleRightView;

    private void setRadioGroupListener() {
        collectRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.fragment_collect_rb_image:
                        //图片
                        collectVp.setCurrentItem(0);
                        break;
                    case R.id.fragment_collect_rb_video:
                        //视频
                        collectVp.setCurrentItem(1);
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.fragment_collect_delete://删除
                if (collectVp.getCurrentItem() == 0) {
                    CollectImageFragment.isShowBox = true;
                    imageFragment.setDeleteImage();//删除状态
                } else if (collectVp.getCurrentItem() == 1) {
                    //视频
                    CollectVideoFragment.isShowBox = true;//显示box
                    videoFragment.setDeleteImage();//删除状态
                }
                break;
        }
    }
}

package com.lushuitv.yewuds.fragment;

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
import android.widget.TextView;

import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.adapter.MainTabAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 排行
 * Created by weip on 2017/8/25.
 */

public class RankFragment extends Fragment {

    ImageView backView;

    TextView titleView;

    RadioGroup rankRg;

    RadioButton rankCollectRb, rankPayRb, rankIncomeRb, rankConsumeRb;

    private RankCollectFragment rankCollectFragment;

    private RankPayFragment rankPayFragment;

    private RankIncomeFragment rankIncomeFragment;

    private RankConsumeFragment rankConsumeFragment;

    private ViewPager rankVp;

    private List<Fragment> list = new ArrayList<Fragment>();

    public View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_ranking, container, false);
        initView(mView);
        return mView;
    }

    public void initView(View rootView) {
        backView = (ImageView) rootView.findViewById(R.id.iv_back);
        titleView = (TextView) rootView.findViewById(R.id.tv_title);
        backView.setVisibility(View.GONE);
        titleView.setText("排行榜");

        rankRg = (RadioGroup) rootView.findViewById(R.id.fragment_rank_rg);
        rankCollectRb = (RadioButton) rootView.findViewById(R.id.fragment_rank_rb_collect);
        rankPayRb = (RadioButton) rootView.findViewById(R.id.fragment_rank_rb_pay);
        rankIncomeRb = (RadioButton) rootView.findViewById(R.id.fragment_rank_rb_income);
        rankConsumeRb = (RadioButton) rootView.findViewById(R.id.fragment_rank_rb_consume);

        rankCollectFragment = new RankCollectFragment();
        rankPayFragment = new RankPayFragment();
        rankIncomeFragment = new RankIncomeFragment();
        rankConsumeFragment = new RankConsumeFragment();

        list.add(rankCollectFragment);
        list.add(rankPayFragment);
        list.add(rankIncomeFragment);
        list.add(rankConsumeFragment);

        rankVp = (ViewPager) rootView.findViewById(R.id.fragment_ranking_vp);

        setRadioGroupListener();
        rankVp.setAdapter(new MainTabAdapter(list, getChildFragmentManager()));

        rankVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                rankRg.setOnCheckedChangeListener(null);
                setRadioGroupListener();
                switch (position) {
                    case 0:
                        rankCollectRb.setChecked(true);
                        rankPayRb.setChecked(false);
                        rankIncomeRb.setChecked(false);
                        rankConsumeRb.setChecked(false);
                        break;
                    case 1:
                        rankCollectRb.setChecked(false);
                        rankPayRb.setChecked(true);
                        rankIncomeRb.setChecked(false);
                        rankConsumeRb.setChecked(false);
                        break;
                    case 2:
                        rankCollectRb.setChecked(false);
                        rankPayRb.setChecked(false);
                        rankIncomeRb.setChecked(true);
                        rankConsumeRb.setChecked(false);
                        break;
                    case 3:
                        rankCollectRb.setChecked(false);
                        rankPayRb.setChecked(false);
                        rankIncomeRb.setChecked(false);
                        rankConsumeRb.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setRadioGroupListener() {
        rankRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.fragment_rank_rb_collect:
                        //排行
                        rankVp.setCurrentItem(0);
                        break;
                    case R.id.fragment_rank_rb_pay:
                        //收入
                        rankVp.setCurrentItem(1);
                        break;
                    case R.id.fragment_rank_rb_income:
                        //排行
                        rankVp.setCurrentItem(2);
                        break;
                    case R.id.fragment_rank_rb_consume:
                        //收入
                        rankVp.setCurrentItem(3);
                        break;
                }
            }
        });
    }
}

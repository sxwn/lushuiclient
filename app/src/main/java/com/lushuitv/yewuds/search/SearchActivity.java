package com.lushuitv.yewuds.search;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.adapter.VideoNewAdapter;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.constant.Constants;
import com.lushuitv.yewuds.module.entity.WorksListBean;
import com.lushuitv.yewuds.module.response.NewVideoResponse;
import com.lushuitv.yewuds.utils.KeyBoardUtils;
import com.lushuitv.yewuds.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * 搜索
 * Created by weip on 2017\12\12 0012.
 */

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, SearchContract.View {

    private ImageView back;
    private EditText mEtSearch;
    private TextView searchTv, mTvHotSearch;
    private FlexboxLayout mFlexboxLayout;
    private RecyclerView mRecyclerView;
    private VideoNewAdapter mVideoAdapter;
    private SearchPrenter mSearchPrenter;
    private List<String> mHotTags;//热门标签数据
    private String mKeywords;//搜索关键字
    private int mPage = 1;//当前页码
    protected List<WorksListBean> mList = new ArrayList<WorksListBean>();//从服务端加载到的数据

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
    }

    public void initView() {
        back = (ImageView) findViewById(R.id.search_top_back);
        back.setOnClickListener(this);
        mEtSearch = (EditText) findViewById(R.id.search_top_et);
        searchTv = (TextView) findViewById(R.id.search_top_tv);
        searchTv.setOnClickListener(this);
        mTvHotSearch = (TextView) findViewById(R.id.tv_hotsearch);
        mFlexboxLayout = (FlexboxLayout) findViewById(R.id.search_flexbox_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.search_recyclerview);
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length()==0){
                    mFlexboxLayout.setVisibility(VISIBLE);
                }else{
                    mFlexboxLayout.setVisibility(GONE);
                }
            }
        });
        setPrestener();
        //加载热门标签数据
        mHotTags = mSearchPrenter.loadHotTag();
        //显示热门标签数据
        updateShowHotTag(mHotTags);
        //展示历史搜索效果
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mVideoAdapter = new VideoNewAdapter(this, mList, false);
        mRecyclerView.setAdapter(mVideoAdapter);
    }

    private void updateShowHotTag(List<String> tags) {
        // 通过代码向FlexboxLayout添加View
        for (int i = 0; i < tags.size(); i++) {
            TextView textView = new TextView(this);
            textView.setBackground(getResources().getDrawable(R.drawable.flexbox_text_bg));
            textView.setText(tags.get(i));
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(16, 12, 16, 12);
            textView.setClickable(true);
            textView.setFocusable(true);
            textView.setTextColor(getResources().getColor(R.color.text_color_666));
            mFlexboxLayout.addView(textView);
            //通过FlexboxLayout.LayoutParams 设置子元素支持的属性
            ViewGroup.LayoutParams params = textView.getLayoutParams();
            if (params instanceof FlexboxLayout.LayoutParams) {
                FlexboxLayout.LayoutParams layoutParams = (FlexboxLayout.LayoutParams) params;
                //layoutParams.setFlexBasisPercent(0.5f);
                layoutParams.setMargins(8, 8, 12, 12);
            }
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView tv = (TextView) v;
                    //得到搜索条件，首先屏蔽掉历史搜索和热门搜索
                    showSearchResult(true);
                    //更新界面，显示加载中
                    showLoading();
                    //保存搜索记录
                    mKeywords = tv.getText().toString().trim();
                    mEtSearch.setText(mKeywords);
                    //发起服务请求
                    mSearchPrenter.getDataFromService(mKeywords, mPage, Constants.GET_DATA_TYPE_NORMAL);
                }
            });
        }
    }

    @Override
    public void setEditText(String msg) {
        mEtSearch.setText(msg);
    }

    @Override
    public void showSearchResult(boolean flag) {
        if (flag) {
            mTvHotSearch.setVisibility(GONE);
            mFlexboxLayout.setVisibility(GONE);
        } else {
            mTvHotSearch.setVisibility(VISIBLE);
            mFlexboxLayout.setVisibility(VISIBLE);
        }
    }

    @Override
    public void updateShow(NewVideoResponse videoResponse, int type) {
        if (Constants.GET_DATA_TYPE_NORMAL == type) {
            //正常模式，清空数据，重新加载
            mList.clear();
            for (int i = 0; i < videoResponse.getLIST().size(); i++) {
                if (videoResponse.getLIST().get(i).getWorksType() == 1) {
                    //只添加视频
                    mList.add(videoResponse.getLIST().get(i));
                }
            }
        } else {
            //加载更多模式
            for (int i = 0; i < videoResponse.getLIST().size(); i++) {
                if (videoResponse.getLIST().get(i).getWorksType() == 1) {
                    //只添加视频
                    mList.add(videoResponse.getLIST().get(i));
                }
            }
        }
        mVideoAdapter.setData(mList);
    }

    @Override
    public void showErrorTip(String msg) {
        UIUtils.showToast(msg);
    }

    @Override
    public void setPrestener() {
        mSearchPrenter = new SearchPrenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_top_back://返回
                finish();
                break;
            case R.id.search_top_tv://搜索
                //KeyBoardUtils.hideSoftInput(this);
                String keyword = mEtSearch.getText().toString().trim();
                mSearchPrenter.searchFromServer(keyword);
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void startRefush() {

    }

    @Override
    public void stopRefresh() {

    }

    @Override
    public void startLoadingMore() {

    }

    @Override
    public void stopLoadingMore() {
    }
}

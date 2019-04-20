package com.lushuitv.yewuds.search;

import android.content.Context;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.constant.Constants;
import com.lushuitv.yewuds.module.response.NewVideoResponse;
import com.lushuitv.yewuds.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kgc on 2017/11/30.
 */

public class SearchPrenter implements SearchContract.Prestener {
    private SearchContract.View mView;
    private final String HISTORY_SEARCHA = "history_search";
    private List<String> mHistoryTitles;

    public SearchPrenter(SearchContract.View mView) {
        this.mView = mView;
    }


    @Override
    public List<String> loadHotTag() {
        List<String> mHotTitles = new ArrayList<String>();
        mHotTitles.add("性感");
        mHotTitles.add("神仙姐姐");
        mHotTitles.add("LSTV_SEX_0056_雨菲");
        mHotTitles.add("校园");
        mHotTitles.add("欣杨");
        mHotTitles.add("孟晓艺");
        return mHotTitles;
    }

    @Override
    public void getDataFromService(String keyword, int page,final int type) {
        ApiRetrofit.getInstance().getApiService().getSearchWorks(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewVideoResponse>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewVideoResponse response) {
                        if ("0".equals(response.getSTATUS())) {
                            if (response.getLIST().size() == 0) {
                                UIUtils.showToast("抱歉,并未查询到相关信息~");
                            } else {
                                mView.updateShow(response, type);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.stopLoadingMore();
                        mView.hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        mView.stopLoadingMore();
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void searchFromServer(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            UIUtils.showToast("请输入搜索条件");
            return;
        }
        //显示搜索结果
        mView.showSearchResult(true);
        mView.showLoading();
        getDataFromService(keyword, 1, Constants.GET_DATA_TYPE_NORMAL);
    }

}

package com.lushuitv.yewuds.view;

import com.lushuitv.yewuds.module.entity.VideoItemBean;
import com.lushuitv.yewuds.module.entity.WorksListBean;

import java.util.List;

/**
 * Description
 * Created by weip
 * Date on 2017/9/8.
 */

public interface IVideoListView {

    void onGetVideosListSuccess(List<WorksListBean> videoList);

    void  onError();

}

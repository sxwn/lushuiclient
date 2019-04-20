package com.lushuitv.yewuds.module.response;


import com.lushuitv.yewuds.module.entity.HttpBean;
import com.lushuitv.yewuds.module.entity.VideoItemBean;
import com.lushuitv.yewuds.module.entity.WorksListBean;

import org.json.JSONObject;

import java.util.List;

/**
 * Description 最新视频
 * Created by weip
 * Date on 2017/9/8.
 */

public class NewVideoResponse extends HttpBean {

    private List<WorksListBean> LIST;
    private JSONObject OBJECT;

    public List<WorksListBean> getLIST() {
        return LIST;
    }

    public void setLIST(List<WorksListBean> LIST) {
        this.LIST = LIST;
    }

    public JSONObject getOBJECT() {
        return OBJECT;
    }

    public void setOBJECT(JSONObject OBJECT) {
        this.OBJECT = OBJECT;
    }

}

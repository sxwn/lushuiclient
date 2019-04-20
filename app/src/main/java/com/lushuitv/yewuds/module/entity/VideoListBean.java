package com.lushuitv.yewuds.module.entity;

import org.json.JSONObject;

import java.util.List;

/**
 * Description 视频列表
 * Created by weip
 * Date on 2017/9/8.
 */

public class VideoListBean extends HttpBean{
    private List<VideoItemBean> LIST;
    private JSONObject OBJECT;

    public List<VideoItemBean> getLIST() {
        return LIST;
    }

    public void setLIST(List<VideoItemBean> LIST) {
        this.LIST = LIST;
    }

    public JSONObject getOBJECT() {
        return OBJECT;
    }

    public void setOBJECT(JSONObject OBJECT) {
        this.OBJECT = OBJECT;
    }
}

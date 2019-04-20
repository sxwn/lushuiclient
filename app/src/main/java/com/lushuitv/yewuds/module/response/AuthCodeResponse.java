package com.lushuitv.yewuds.module.response;


import com.lushuitv.yewuds.module.entity.HttpBean;

import org.json.JSONObject;

/**
 * Description
 * Created by weip
 * Date on 2017/9/11.
 */

public class AuthCodeResponse extends HttpBean {

    private JSONObject OBJECT;

    public JSONObject getOBJECT() {
        return OBJECT;
    }

    public void setOBJECT(JSONObject OBJECT) {
        this.OBJECT = OBJECT;
    }
}

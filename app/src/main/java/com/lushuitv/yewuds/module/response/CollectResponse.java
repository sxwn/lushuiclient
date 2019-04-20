package com.lushuitv.yewuds.module.response;

import com.lushuitv.yewuds.module.entity.HttpBean;

/**
 * Description
 * Created by weip
 * Date on 2017/9/14.
 */

public class CollectResponse extends HttpBean{
    /**
     * MSG : 收藏成功！
     * OBJECT : 1
     * STATUS : 0
     */

    private int OBJECT;

    public int getOBJECT() {
        return OBJECT;
    }

    public void setOBJECT(int OBJECT) {
        this.OBJECT = OBJECT;
    }
}

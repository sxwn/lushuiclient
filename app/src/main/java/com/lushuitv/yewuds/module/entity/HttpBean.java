package com.lushuitv.yewuds.module.entity;

import java.io.Serializable;

/**
 * Description 基本的bean的信息
 * Created by weip
 * Date on 2017/9/8.
 */

public class HttpBean implements Serializable{
    private String MSG;
    private String STATUS;//状态码


    public String getMSG() {
        return MSG;
    }

    public void setMSG(String MSG) {
        this.MSG = MSG;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }


}

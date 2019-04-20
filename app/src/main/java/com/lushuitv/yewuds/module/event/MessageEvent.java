package com.lushuitv.yewuds.module.event;

/**
 * Description
 * Created by weip
 * Date on 2017/9/14.
 */

public class MessageEvent {
    private int count;
    private String str;
    private boolean flag;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

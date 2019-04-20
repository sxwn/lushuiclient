package com.lushuitv.yewuds.module.entity;

import java.io.Serializable;

/**
 * Description
 * Created by weip
 * Date on 2017/9/13.
 */

public class UseParams implements Serializable{
    private int gold;
    private int insiderType;
    private String insiderEndTime;
    private String userId;
    private String userCode;
    private String userName;
    private String userTel;
    private String userHeadshot;
    private int userPayStats;
    private String sessionId;

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getInsiderType() {
        return insiderType;
    }

    public void setInsiderType(int insiderType) {
        this.insiderType = insiderType;
    }

    public String getInsiderEndTime() {
        return insiderEndTime;
    }

    public void setInsiderEndTime(String insiderEndTime) {
        this.insiderEndTime = insiderEndTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserHeadshot() {
        return userHeadshot;
    }

    public void setUserHeadshot(String userHeadshot) {
        this.userHeadshot = userHeadshot;
    }

    public int getUserPayStats() {
        return userPayStats;
    }

    public void setUserPayStats(int userPayStats) {
        this.userPayStats = userPayStats;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}

package com.lushuitv.yewuds.module.response;

import com.lushuitv.yewuds.module.entity.HttpBean;

/**
 * Description
 * Created by weip
 * Date on 2017/9/12.
 */

public class UserInfoResponse extends HttpBean {

    private OBJECTBean OBJECT;

    public OBJECTBean getOBJECT() {
        return OBJECT;
    }

    public void setOBJECT(OBJECTBean OBJECT) {
        this.OBJECT = OBJECT;
    }

    public static class OBJECTBean {
        private int gold;
        private String insiderEndTime;//过期时间
        private int insiderType;//1 普通会员  2 年会员
        private String userCode;
        private String userId;
        private String userName;
        private String userTel;
        private String userHeadshot;
        private int userPayStats; //是否设置过支付密码
        private String sessionId;

        public int getGold() {
            return gold;
        }

        public void setGold(int gold) {
            this.gold = gold;
        }

        public String getInsiderEndTime() {
            return insiderEndTime;
        }

        public void setInsiderEndTime(String insiderEndTime) {
            this.insiderEndTime = insiderEndTime;
        }

        public int getInsiderType() {
            return insiderType;
        }

        public void setInsiderType(int insiderType) {
            this.insiderType = insiderType;
        }

        public String getUserCode() {
            return userCode;
        }

        public void setUserCode(String userCode) {
            this.userCode = userCode;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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

        @Override
        public String toString() {
            return "{" +
                    "gold=" + gold +
                    ", insiderEndTime='" + insiderEndTime + '\'' +
                    ", insiderType=" + insiderType +
                    ", userCode='" + userCode + '\'' +
                    ", userId='" + userId + '\'' +
                    ", userName='" + userName + '\'' +
                    ", userTel='" + userTel + '\'' +
                    ", userHeadshot='" + userHeadshot + '\'' +
                    ", userPayStats=" + userPayStats +
                    ", sessionId='" + sessionId + '\'' +
                    '}';
        }
    }
}

package com.lushuitv.yewuds.module.response;

import com.lushuitv.yewuds.module.entity.HttpBean;

/**
 * Description
 * Created by weip
 * Date on 2017/9/12.
 */

public class UserInfoThridResponse extends HttpBean {


    /**
     * OBJECT : {"userCode":"X5DA6Fa7","userHeadshot":"http://wx.qlogo.cn/mmopen/vi_32/gfnQyDo2iaD20OFSpSa8ZDHiapFudsiaBJXZT6dzxRCsCetuq64kOZ0sLYggMUumgG7zL9INg0peNbmEBV59jbQkA/0","userId":"9af0d9e36b6b43039ada73ae2238298d","userName":"蔚鹏","userPayStats":0}
     */

    private OBJECTBean OBJECT;

    public OBJECTBean getOBJECT() {
        return OBJECT;
    }

    public void setOBJECT(OBJECTBean OBJECT) {
        this.OBJECT = OBJECT;
    }

    public static class OBJECTBean {
        /**
         * userCode : X5DA6Fa7
         * userHeadshot : http://wx.qlogo.cn/mmopen/vi_32/gfnQyDo2iaD20OFSpSa8ZDHiapFudsiaBJXZT6dzxRCsCetuq64kOZ0sLYggMUumgG7zL9INg0peNbmEBV59jbQkA/0
         * userId : 9af0d9e36b6b43039ada73ae2238298d
         * userName : 蔚鹏
         * userPayStats : 0
         */

        private String userCode;
        private String userHeadshot;
        private String userId;
        private String userName;
        private int userPayStats;

        public String getUserCode() {
            return userCode;
        }

        public void setUserCode(String userCode) {
            this.userCode = userCode;
        }

        public String getUserHeadshot() {
            return userHeadshot;
        }

        public void setUserHeadshot(String userHeadshot) {
            this.userHeadshot = userHeadshot;
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

        public int getUserPayStats() {
            return userPayStats;
        }

        public void setUserPayStats(int userPayStats) {
            this.userPayStats = userPayStats;
        }

        @Override
        public String toString() {
            return "{" +
                    "userCode='" + userCode + '\'' +
                    ", userHeadshot='" + userHeadshot + '\'' +
                    ", userId='" + userId + '\'' +
                    ", userName='" + userName + '\'' +
                    ", userPayStats=" + userPayStats +
                    '}';
        }
    }
}

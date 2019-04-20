package com.lushuitv.yewuds.module.response;

import com.lushuitv.yewuds.module.entity.HttpBean;

/**
 * Description
 * Created by weip
 * Date on 2017/9/21.
 */

public class UserPurseResponse extends HttpBean{


    /**
     * OBJECT : {"userId":"cca43b8b03224c2b92adf46f3e2994bb","userIsFreeze":0,"userPayStats":0,"userPurseDis":938,"userRewardPurseDis":0}
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
         * userId : cca43b8b03224c2b92adf46f3e2994bb
         * userIsFreeze : 0
         * userPayStats : 0
         * userPurseDis : 938
         * userRewardPurseDis : 0
         */

        private String userId;
        private int userIsFreeze;
        private int userPayStats;
        private double userPurseDis;
        private double userRewardPurseDis;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getUserIsFreeze() {
            return userIsFreeze;
        }

        public void setUserIsFreeze(int userIsFreeze) {
            this.userIsFreeze = userIsFreeze;
        }

        public int getUserPayStats() {
            return userPayStats;
        }

        public void setUserPayStats(int userPayStats) {
            this.userPayStats = userPayStats;
        }

        public double getUserPurseDis() {
            return userPurseDis;
        }

        public void setUserPurseDis(double userPurseDis) {
            this.userPurseDis = userPurseDis;
        }

        public double getUserRewardPurseDis() {
            return userRewardPurseDis;
        }

        public void setUserRewardPurseDis(double userRewardPurseDis) {
            this.userRewardPurseDis = userRewardPurseDis;
        }
    }
}

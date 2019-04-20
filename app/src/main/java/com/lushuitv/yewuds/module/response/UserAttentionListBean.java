package com.lushuitv.yewuds.module.response;

import com.lushuitv.yewuds.module.entity.HttpBean;

import java.util.List;

/**
 * Description
 * Created by weip
 * Date on 2017/9/20.
 */

public class UserAttentionListBean extends HttpBean{
    /**
     * MSG : SUCCESS
     * OBJECT : [{"actorFans":1,"actorHeadshot":"aaa.jpg","actorId":1,"actorName":"梦心月","isFocus":0}]
     * STATUS : 0
     */

    private List<AttantionUser> OBJECT;

    public List<AttantionUser> getOBJECT() {
        return OBJECT;
    }

    public void setOBJECT(List<AttantionUser> OBJECT) {
        this.OBJECT = OBJECT;
    }

    public static class AttantionUser {
        /**
         * actorFans : 1
         * actorHeadshot : aaa.jpg
         * actorId : 1
         * actorName : 梦心月
         * isFocus : 0
         */

        private int actorFans;
        private String actorHeadshot;
        private int actorId;
        private String actorName;
        private int isFocus;

        public int getActorFans() {
            return actorFans;
        }

        public void setActorFans(int actorFans) {
            this.actorFans = actorFans;
        }

        public String getActorHeadshot() {
            return actorHeadshot;
        }

        public void setActorHeadshot(String actorHeadshot) {
            this.actorHeadshot = actorHeadshot;
        }

        public int getActorId() {
            return actorId;
        }

        public void setActorId(int actorId) {
            this.actorId = actorId;
        }

        public String getActorName() {
            return actorName;
        }

        public void setActorName(String actorName) {
            this.actorName = actorName;
        }

        public int getIsFocus() {
            return isFocus;
        }

        public void setIsFocus(int isFocus) {
            this.isFocus = isFocus;
        }


        @Override
        public String toString() {
            return "{" +
                    "actorFans=" + actorFans +
                    ", actorHeadshot='" + actorHeadshot + '\'' +
                    ", actorId=" + actorId +
                    ", actorName='" + actorName + '\'' +
                    ", isFocus=" + isFocus +
                    '}';
        }
    }
}

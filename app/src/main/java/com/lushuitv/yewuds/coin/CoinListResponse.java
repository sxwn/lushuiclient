package com.lushuitv.yewuds.coin;

import com.lushuitv.yewuds.module.entity.HttpBean;

import java.util.List;

/**
 * 我的金币
 * Created by weip on 2017\12\21 0021.
 */

public class CoinListResponse extends HttpBean {
    private List<OBJECTBean> OBJECT;

    public List<OBJECTBean> getOBJECT() {
        return OBJECT;
    }

    public void setOBJECT(List<OBJECTBean> OBJECT) {
        this.OBJECT = OBJECT;
    }

    public static class OBJECTBean {
        /**
         * gold : 22
         * purseType : 1
         * userId : 8c60842d58ce48209c6f1fd3d54041b8
         */

        private int gold;
        private int purseType;
        private String userId;
        private String recordDate;
        private String recordMoney;
        private String recordNo;
        private int type;
        private String worksId;
        private String worksName;
        private String actorId;
        private String actorName;
        private String buyType;
        private String cardNum;
        private String chType;
        private String userName;//添加字段  打赏
        private String userHeadshot;//添加字段 打赏

        public String getUserHeadshot() {
            return userHeadshot;
        }

        public void setUserHeadshot(String userHeadshot) {
            this.userHeadshot = userHeadshot;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getGold() {
            return gold;
        }

        public void setGold(int gold) {
            this.gold = gold;
        }

        public int getPurseType() {
            return purseType;
        }

        public void setPurseType(int purseType) {
            this.purseType = purseType;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getRecordDate() {
            return recordDate;
        }

        public void setRecordDate(String recordDate) {
            this.recordDate = recordDate;
        }

        public String getRecordMoney() {
            return recordMoney;
        }

        public void setRecordMoney(String recordMoney) {
            this.recordMoney = recordMoney;
        }

        public String getRecordNo() {
            return recordNo;
        }

        public void setRecordNo(String recordNo) {
            this.recordNo = recordNo;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getWorksId() {
            return worksId;
        }

        public void setWorksId(String worksId) {
            this.worksId = worksId;
        }

        public String getWorksName() {
            return worksName;
        }

        public void setWorksName(String worksName) {
            this.worksName = worksName;
        }

        public String getActorId() {
            return actorId;
        }

        public void setActorId(String actorId) {
            this.actorId = actorId;
        }

        public String getActorName() {
            return actorName;
        }

        public void setActorName(String actorName) {
            this.actorName = actorName;
        }

        public String getBuyType() {
            return buyType;
        }

        public void setBuyType(String buyType) {
            this.buyType = buyType;
        }

        public String getCardNum() {
            return cardNum;
        }

        public void setCardNum(String cardNum) {
            this.cardNum = cardNum;
        }

        public String getChType() {
            return chType;
        }

        public void setChType(String chType) {
            this.chType = chType;
        }
    }
}

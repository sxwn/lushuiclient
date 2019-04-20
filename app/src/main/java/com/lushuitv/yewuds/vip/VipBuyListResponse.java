package com.lushuitv.yewuds.vip;

import com.lushuitv.yewuds.module.entity.HttpBean;

import java.io.Serializable;
import java.util.List;

/**
 * 会员购买列表
 * Created by weip on 2017\12\26 0026.
 */

public class VipBuyListResponse extends HttpBean {

    /**
     * OBJECT : {"buyList":[{"buyType":2,"chType":4,"gold":980,"isOpen":1,"month":1,"name":"月会员"},{"buyType":2,"chType":5,"gold":4880,"isOpen":1,"month":3,"name":"季度会员"},{"buyType":2,"chType":6,"gold":7980,"isOpen":1,"month":12,"name":"年会员"}],"endTime":"2020-01-23","gold":42110,"insiderType":1,"userHeadshot":"userimage-1254218478.picsh.myqcloud.com/1513050690109.jpeg","userName":"lushui9779"}
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
         * buyList : [{"buyType":2,"chType":4,"gold":980,"isOpen":1,"month":1,"name":"月会员"},{"buyType":2,"chType":5,"gold":4880,"isOpen":1,"month":3,"name":"季度会员"},{"buyType":2,"chType":6,"gold":7980,"isOpen":1,"month":12,"name":"年会员"}]
         * endTime : 2020-01-23
         * gold : 42110
         * insiderType : 1
         * userHeadshot : userimage-1254218478.picsh.myqcloud.com/1513050690109.jpeg
         * userName : lushui9779
         */

        private String endTime;
        private int gold;
        private int insiderType;
        private String userHeadshot;
        private String userName;
        private List<BuyListBean> buyList;

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

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

        public List<BuyListBean> getBuyList() {
            return buyList;
        }

        public void setBuyList(List<BuyListBean> buyList) {
            this.buyList = buyList;
        }

        public static class BuyListBean implements Serializable{
            /**
             * buyType : 2
             * chType : 4
             * gold : 980
             * isOpen : 1
             * month : 1
             * name : 月会员
             */

            private int buyType;
            private int chType;
            private int gold;
            private int isOpen;
            private int month;
            private String name;

            public int getBuyType() {
                return buyType;
            }

            public void setBuyType(int buyType) {
                this.buyType = buyType;
            }

            public int getChType() {
                return chType;
            }

            public void setChType(int chType) {
                this.chType = chType;
            }

            public int getGold() {
                return gold;
            }

            public void setGold(int gold) {
                this.gold = gold;
            }

            public int getIsOpen() {
                return isOpen;
            }

            public void setIsOpen(int isOpen) {
                this.isOpen = isOpen;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}

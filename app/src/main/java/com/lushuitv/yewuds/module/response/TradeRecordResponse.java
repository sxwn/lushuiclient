package com.lushuitv.yewuds.module.response;

import com.lushuitv.yewuds.module.entity.HttpBean;

import java.util.List;

/**
 * Description
 * Created by weip
 * Date on 2017/9/20.
 */

public class TradeRecordResponse extends HttpBean {


    /**
     * LIST : [{"actorId":"1","actorName":"梦心月","avatar":"aaa.jpg","recordChannel":"ALI_APP","recordDate":"2017-08-29 10:08","recordId":"2","recordMoney":"100.00","recordNo":"170829102513233754","recordStatus":"2","recordType":"交易","recordWorks":"1","worksName":"路边野餐"}]
     * OBJECT : {}
     */

    private OBJECTBean OBJECT;
    private List<LISTBean> LIST;

    public OBJECTBean getOBJECT() {
        return OBJECT;
    }

    public void setOBJECT(OBJECTBean OBJECT) {
        this.OBJECT = OBJECT;
    }

    public List<LISTBean> getLIST() {
        return LIST;
    }

    public void setLIST(List<LISTBean> LIST) {
        this.LIST = LIST;
    }

    public static class OBJECTBean {
    }

    public static class LISTBean {
        /**
         * actorId : 1
         * actorName : 梦心月
         * avatar : aaa.jpg
         * recordChannel : ALI_APP
         * recordDate : 2017-08-29 10:08
         * recordId : 2
         * recordMoney : 100.00
         * recordNo : 170829102513233754
         * recordStatus : 2
         * recordType : 交易
         * recordWorks : 1
         * worksName : 路边野餐
         */

        private String actorId;
        private String actorName;
        private String avatar;
        private String recordChannel;
        private String recordDate;
        private String recordId;
        private String recordMoney;
        private String recordNo;
        private String recordStatus;
        private String recordType;
        private String recordWorks;
        private String worksName;

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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getRecordChannel() {
            return recordChannel;
        }

        public void setRecordChannel(String recordChannel) {
            this.recordChannel = recordChannel;
        }

        public String getRecordDate() {
            return recordDate;
        }

        public void setRecordDate(String recordDate) {
            this.recordDate = recordDate;
        }

        public String getRecordId() {
            return recordId;
        }

        public void setRecordId(String recordId) {
            this.recordId = recordId;
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

        public String getRecordStatus() {
            return recordStatus;
        }

        public void setRecordStatus(String recordStatus) {
            this.recordStatus = recordStatus;
        }

        public String getRecordType() {
            return recordType;
        }

        public void setRecordType(String recordType) {
            this.recordType = recordType;
        }

        public String getRecordWorks() {
            return recordWorks;
        }

        public void setRecordWorks(String recordWorks) {
            this.recordWorks = recordWorks;
        }

        public String getWorksName() {
            return worksName;
        }

        public void setWorksName(String worksName) {
            this.worksName = worksName;
        }
    }
}

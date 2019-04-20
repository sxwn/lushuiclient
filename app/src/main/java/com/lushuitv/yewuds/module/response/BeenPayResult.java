package com.lushuitv.yewuds.module.response;

import com.lushuitv.yewuds.module.entity.HttpBean;

/**
 * Description
 * Created by weip
 * Date on 2017/9/16.
 */

public class BeenPayResult extends HttpBean {


    /**
     * OBJECT : {"money":100,"outTradeNo":"170916153613045746","type":"1","worksId":"1"}
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
         * money : 100
         * outTradeNo : 170916153613045746
         * type : 1
         * worksId : 1
         */

        private int money;
        private String outTradeNo;
        private String type;
        private String worksId;

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public String getOutTradeNo() {
            return outTradeNo;
        }

        public void setOutTradeNo(String outTradeNo) {
            this.outTradeNo = outTradeNo;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getWorksId() {
            return worksId;
        }

        public void setWorksId(String worksId) {
            this.worksId = worksId;
        }
    }
}

package com.lushuitv.yewuds.module.response;

import com.lushuitv.yewuds.module.entity.HttpBean;

import java.util.List;

/**
 * Description
 * Created by weip
 * Date on 2017/10/24.
 */

public class FenXiaoResponse extends HttpBean {


    /**
     * LIST : [{"higherReward":"15","superiorReward":"0","worksName":"梦心月\u2014原始的欲望"},{"higherReward":"15","superiorReward":"0","worksName":"周妍希\u2014美人出浴版"}]
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
         * higherReward : 15
         * superiorReward : 0
         * worksName : 梦心月—原始的欲望
         */
        private String allReward;
        private String higherReward;
        private String superiorReward;
        private String worksName;

        public String getAllReward() {
            return allReward;
        }

        public void setAllReward(String allReward) {
            this.allReward = allReward;
        }

        public String getHigherReward() {
            return higherReward;
        }

        public void setHigherReward(String higherReward) {
            this.higherReward = higherReward;
        }

        public String getSuperiorReward() {
            return superiorReward;
        }

        public void setSuperiorReward(String superiorReward) {
            this.superiorReward = superiorReward;
        }

        public String getWorksName() {
            return worksName;
        }

        public void setWorksName(String worksName) {
            this.worksName = worksName;
        }
    }
}

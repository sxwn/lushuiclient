package com.lushuitv.yewuds.boot.module;

import com.lushuitv.yewuds.module.entity.HttpBean;

/**
 * Created by Administrator on 2017\11\17 0017.
 */

public class DataType extends HttpBean{

    /**
     * MSG : SUCCESS
     * OBJECT : {"ishidden":"2","stratery":"0","version":"1.1。0"}
     * STATUS : 0
     */

    private OBJECTEntity OBJECT;


    public void setOBJECT(OBJECTEntity OBJECT) {
        this.OBJECT = OBJECT;
    }


    public OBJECTEntity getOBJECT() {
        return OBJECT;
    }

    public static class OBJECTEntity {
        /**
         * ishidden : 2
         * stratery : 0
         * version : 1.1。0
         */

        private String ishidden;
        private String stratery;
        private String version;

        public void setIshidden(String ishidden) {
            this.ishidden = ishidden;
        }

        public void setStratery(String stratery) {
            this.stratery = stratery;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getIshidden() {
            return ishidden;
        }

        public String getStratery() {
            return stratery;
        }

        public String getVersion() {
            return version;
        }
    }
}

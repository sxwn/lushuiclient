package com.lushuitv.yewuds.module.response;

import com.lushuitv.yewuds.module.entity.HttpBean;

/**
 * Created by weip on 2017\12\20 0020.
 */

public class VersionInfo extends HttpBean {

    /**
     * OBJECT : {"des":"真的需要更新了","isToUp":0,"isUp":1,"name":"更新","title":"新版本来啦","upVersion":"1.3","url":"https://www.baidu.com"}
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
         * des : 真的需要更新了
         * isToUp : 0
         * isUp : 1
         * name : 更新
         * title : 新版本来啦
         * upVersion : 1.3
         * url : https://www.baidu.com
         */

        private String des;
        private int isToUp;
        private int isUp;
        private String name;
        private String title;
        private String upVersion;
        private String url;

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public int getIsToUp() {
            return isToUp;
        }

        public void setIsToUp(int isToUp) {
            this.isToUp = isToUp;
        }

        public int getIsUp() {
            return isUp;
        }

        public void setIsUp(int isUp) {
            this.isUp = isUp;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUpVersion() {
            return upVersion;
        }

        public void setUpVersion(String upVersion) {
            this.upVersion = upVersion;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}

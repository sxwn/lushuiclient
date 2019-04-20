package com.lushuitv.yewuds.module.response;

import com.lushuitv.yewuds.module.entity.HttpBean;

import java.util.List;

/**
 * Description
 * Created by weip
 * Date on 2017/10/13.
 */

public class BannerResponse extends HttpBean{

    /**
     * LIST : [{"bannerActor":1,"bannerId":1,"bannerImg":"http://photo-1254218478.picsh.myqcloud.com/001_fenmian.jpg","bannerTitle":"aaa","bannerWeight":13},{"bannerActor":1,"bannerId":2,"bannerImg":"http://photo-1254218478.picsh.myqcloud.com/001_fenmian.jpg","bannerWeight":20},{"bannerActor":1,"bannerId":3,"bannerImg":"http://photo-1254218478.picsh.myqcloud.com/001_fenmian.jpg","bannerWeight":30}]
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
         * bannerActor : 1
         * bannerId : 1
         * bannerImg : http://photo-1254218478.picsh.myqcloud.com/001_fenmian.jpg
         * bannerTitle : aaa
         * bannerWeight : 13
         */

        private int bannerActor;
        private int bannerId;
        private String bannerImg;
        private String bannerTitle;
        private int bannerWeight;

        public int getBannerActor() {
            return bannerActor;
        }

        public void setBannerActor(int bannerActor) {
            this.bannerActor = bannerActor;
        }

        public int getBannerId() {
            return bannerId;
        }

        public void setBannerId(int bannerId) {
            this.bannerId = bannerId;
        }

        public String getBannerImg() {
            return bannerImg;
        }

        public void setBannerImg(String bannerImg) {
            this.bannerImg = bannerImg;
        }

        public String getBannerTitle() {
            return bannerTitle;
        }

        public void setBannerTitle(String bannerTitle) {
            this.bannerTitle = bannerTitle;
        }

        public int getBannerWeight() {
            return bannerWeight;
        }

        public void setBannerWeight(int bannerWeight) {
            this.bannerWeight = bannerWeight;
        }
    }
}

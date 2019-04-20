package com.lushuitv.yewuds.module.response;

import com.lushuitv.yewuds.module.entity.WorksListBean;

import java.io.Serializable;
import java.util.List;

/**
 * Description
 * Created by weip
 * Date on 2017/9/19.
 */

public class ActorWorkInfo {

    /**
     * LIST : [{"isColletion":0,"isPraising":0,"worksCommentNum":0,"worksCover":"aaa.jpg","worksDate":"2017-08-24 17:11:41.0","worksDescription":"2","worksId":1,"worksName":"路边野餐","worksPlayNum":11,"worksPraising":0,"worksType":1},{"isColletion":0,"isPraising":0,"worksCommentNum":0,"worksCover":"aaa.jpg","worksDate":"2017-08-24 17:15:04.0","worksDescription":"2","worksId":2,"worksName":"性感文艺的女神范","worksPlayNum":8,"worksPraising":0,"worksType":1},{"contentList":[{"contentId":2},{"contentId":3,"contentUrl":"http://work-1254218478.cossh.myqcloud.com/bbb"}],"isColletion":0,"isPraising":0,"worksCommentNum":0,"worksCover":"aaa.jpg","worksDate":"2017-08-29 10:17:19.0","worksDescription":"aaa","worksId":3,"worksName":"出水芙蓉","worksPlayNum":0,"worksPraising":0,"worksType":2},{"contentList":[{"contentId":4,"contentUrl":"http://work-1254218478.cossh.myqcloud.com/lushuihengone.JPG"},{"contentId":5,"contentUrl":"http://work-1254218478.cossh.myqcloud.com/lushuihengtwo.JPG"},{"contentId":6,"contentUrl":"http://work-1254218478.cossh.myqcloud.com/lushuihengthree.JPG"},{"contentId":7,"contentUrl":"http://work-1254218478.cossh.myqcloud.com/lushuihengfour.JPG"},{"contentId":8,"contentUrl":"http://work-1254218478.cossh.myqcloud.com/lushuihengfive.JPG"}],"isColletion":0,"isPraising":0,"worksCommentNum":0,"worksCover":"aaa.jpg","worksDate":"2017-09-04 17:43:47.0","worksId":4,"worksName":"性感尤物","worksPlayNum":0,"worksPraising":0,"worksType":2}]
     * MSG : SUCCESS
     * OBJECT : {"actorCity":"北京","actorCwh":"85/62/90","actorFans":1,"actorHeadshot":"aaa.jpg","actorHeight":"172","actorId":1,"actorJob":"模特","actorName":"梦心月","isFocus":2}
     * STATUS : 0
     */

    private OBJECTBean OBJECT;
    private List<WorksListBean> LIST;

    public OBJECTBean getOBJECT() {
        return OBJECT;
    }

    public void setOBJECT(OBJECTBean OBJECT) {
        this.OBJECT = OBJECT;
    }

    public List<WorksListBean> getLIST() {
        return LIST;
    }

    public void setLIST(List<WorksListBean> LIST) {
        this.LIST = LIST;
    }

    public static class OBJECTBean {
        /**
         * actorCity : 北京
         * actorCwh : 85/62/90
         * actorFans : 1
         * actorHeadshot : aaa.jpg
         * actorHeight : 172
         * actorId : 1
         * actorJob : 模特
         * actorName : 梦心月
         * isFocus : 2
         */

        private String actorCity;
        private String actorCwh;
        private int actorFans;
        private String actorHeadshot;
        private String actorHeight;
        private int actorId;
        private String actorImg;
        private String actorJob;
        private String actorName;
        private int isFocus;

        public String getActorCity() {
            return actorCity;
        }

        public void setActorCity(String actorCity) {
            this.actorCity = actorCity;
        }

        public String getActorCwh() {
            return actorCwh;
        }

        public void setActorCwh(String actorCwh) {
            this.actorCwh = actorCwh;
        }

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

        public String getActorHeight() {
            return actorHeight;
        }

        public void setActorHeight(String actorHeight) {
            this.actorHeight = actorHeight;
        }

        public int getActorId() {
            return actorId;
        }

        public void setActorId(int actorId) {
            this.actorId = actorId;
        }

        public String getActorImg() {
            return actorImg;
        }

        public void setActorImg(String actorImg) {
            this.actorImg = actorImg;
        }

        public String getActorJob() {
            return actorJob;
        }

        public void setActorJob(String actorJob) {
            this.actorJob = actorJob;
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
    }


}

package com.lushuitv.yewuds.module.response;

import com.lushuitv.yewuds.module.entity.HttpBean;

import java.util.List;

/**
 * Description
 * Created by weip
 * Date on 2017/9/21.
 */

public class UnfreeWorkLists extends HttpBean{


    /**
     * MSG : SUCCESS
     * OBJECT : {"actorHeadshot":"aaa.jpg","actorName":"梦心月","contentList":[{"contentId":9,"contentUrl":"http://work-1254218478.cossh.myqcloud.com/lushuishuthree.jpg"},{"contentId":10,"contentUrl":"http://work-1254218478.cossh.myqcloud.com/lushuishutwo.jpg"},{"contentId":11,"contentUrl":"http://work-1254218478.cossh.myqcloud.com/lushuishuone.jpg"}],"isColletion":0,"isPraising":0,"worksActor":"1","worksCommentNum":0,"worksContentNum":3,"worksCover":"aaa.jpg","worksDate":"2017-09-04 17:49:30.0","worksDescription":"蕾丝","worksId":5,"worksName":"白色诱惑","worksPlayNum":0,"worksPraising":0,"worksType":2}
     * STATUS : 0
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
         * actorHeadshot : aaa.jpg
         * actorName : 梦心月
         * contentList : [{"contentId":9,"contentUrl":"http://work-1254218478.cossh.myqcloud.com/lushuishuthree.jpg"},{"contentId":10,"contentUrl":"http://work-1254218478.cossh.myqcloud.com/lushuishutwo.jpg"},{"contentId":11,"contentUrl":"http://work-1254218478.cossh.myqcloud.com/lushuishuone.jpg"}]
         * isColletion : 0
         * isPraising : 0
         * worksActor : 1
         * worksCommentNum : 0
         * worksContentNum : 3
         * worksCover : aaa.jpg
         * worksDate : 2017-09-04 17:49:30.0
         * worksDescription : 蕾丝
         * worksId : 5
         * worksName : 白色诱惑
         * worksPlayNum : 0
         * worksPraising : 0
         * worksType : 2
         */

        private String actorHeadshot;
        private String actorName;
        private int isColletion;
        private int isPraising;
        private String worksActor;
        private int worksCommentNum;
        private int worksContentNum;
        private String worksCover;
        private String worksDate;
        private String worksDescription;
        private int worksId;
        private String worksName;
        private int worksPlayNum;
        private int worksPraising;
        private int worksType;
        private List<ContentListBean> contentList;

        public String getActorHeadshot() {
            return actorHeadshot;
        }

        public void setActorHeadshot(String actorHeadshot) {
            this.actorHeadshot = actorHeadshot;
        }

        public String getActorName() {
            return actorName;
        }

        public void setActorName(String actorName) {
            this.actorName = actorName;
        }

        public int getIsColletion() {
            return isColletion;
        }

        public void setIsColletion(int isColletion) {
            this.isColletion = isColletion;
        }

        public int getIsPraising() {
            return isPraising;
        }

        public void setIsPraising(int isPraising) {
            this.isPraising = isPraising;
        }

        public String getWorksActor() {
            return worksActor;
        }

        public void setWorksActor(String worksActor) {
            this.worksActor = worksActor;
        }

        public int getWorksCommentNum() {
            return worksCommentNum;
        }

        public void setWorksCommentNum(int worksCommentNum) {
            this.worksCommentNum = worksCommentNum;
        }

        public int getWorksContentNum() {
            return worksContentNum;
        }

        public void setWorksContentNum(int worksContentNum) {
            this.worksContentNum = worksContentNum;
        }

        public String getWorksCover() {
            return worksCover;
        }

        public void setWorksCover(String worksCover) {
            this.worksCover = worksCover;
        }

        public String getWorksDate() {
            return worksDate;
        }

        public void setWorksDate(String worksDate) {
            this.worksDate = worksDate;
        }

        public String getWorksDescription() {
            return worksDescription;
        }

        public void setWorksDescription(String worksDescription) {
            this.worksDescription = worksDescription;
        }

        public int getWorksId() {
            return worksId;
        }

        public void setWorksId(int worksId) {
            this.worksId = worksId;
        }

        public String getWorksName() {
            return worksName;
        }

        public void setWorksName(String worksName) {
            this.worksName = worksName;
        }

        public int getWorksPlayNum() {
            return worksPlayNum;
        }

        public void setWorksPlayNum(int worksPlayNum) {
            this.worksPlayNum = worksPlayNum;
        }

        public int getWorksPraising() {
            return worksPraising;
        }

        public void setWorksPraising(int worksPraising) {
            this.worksPraising = worksPraising;
        }

        public int getWorksType() {
            return worksType;
        }

        public void setWorksType(int worksType) {
            this.worksType = worksType;
        }

        public List<ContentListBean> getContentList() {
            return contentList;
        }

        public void setContentList(List<ContentListBean> contentList) {
            this.contentList = contentList;
        }

        public static class ContentListBean {
            /**
             * contentId : 9
             * contentUrl : http://work-1254218478.cossh.myqcloud.com/lushuishuthree.jpg
             */

            private int contentId;
            private String contentUrl;

            public int getContentId() {
                return contentId;
            }

            public void setContentId(int contentId) {
                this.contentId = contentId;
            }

            public String getContentUrl() {
                return contentUrl;
            }

            public void setContentUrl(String contentUrl) {
                this.contentUrl = contentUrl;
            }
        }
    }
}

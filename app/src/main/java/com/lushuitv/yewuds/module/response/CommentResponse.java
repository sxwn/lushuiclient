package com.lushuitv.yewuds.module.response;

import com.lushuitv.yewuds.module.entity.HttpBean;

import java.util.List;

/**
 * Description
 * Created by weip
 * Date on 2017/9/18.
 */

public class CommentResponse extends HttpBean{


    /**
     * OBJECT : {"actor":{"actorFans":0,"actorId":1,"isFocus":2},"commentList":[{"commentContent":"了了了了了了了了来咯哦哦哦我","commentDate":"2017-10-25 10:49","commentId":117,"commentList":[],"commentPraising":0,"commentWorks":51,"isPraising":0,"replyNum":0,"userId":"4bb9b425fd7e4d12a5d8dea5ed829fcb","userName":"夏薇","userHeadshot":"userimage-1254218478.picsh.myqcloud.com/1506491045021.jpeg"},{"commentContent":"一回事","commentDate":"2017-10-25 10:49","commentId":118,"commentList":[],"commentPraising":0,"commentWorks":51,"isPraising":0,"replyNum":0,"userId":"4bb9b425fd7e4d12a5d8dea5ed829fcb","userName":"夏薇"},{"commentContent":"咋回事","commentDate":"2017-10-25 10:46","commentId":115,"commentList":[],"commentPraising":0,"commentWorks":51,"isPraising":0,"replyNum":0,"userId":"4bb9b425fd7e4d12a5d8dea5ed829fcb","userName":"夏薇"},{"commentContent":"了了了了了了了了","commentDate":"2017-10-25 10:46","commentId":116,"commentList":[],"commentPraising":0,"commentWorks":51,"isPraising":0,"replyNum":0,"userId":"4bb9b425fd7e4d12a5d8dea5ed829fcb","userName":"夏薇"},{"commentContent":"困了","commentDate":"2017-10-25 10:45","commentId":114,"commentList":[],"commentPraising":0,"commentWorks":51,"isPraising":0,"replyNum":0,"userId":"4bb9b425fd7e4d12a5d8dea5ed829fcb","userName":"夏薇"},{"commentContent":"美得很","commentDate":"2017-10-21 13:48","commentId":94,"commentList":[],"commentPraising":0,"commentWorks":51,"isPraising":0,"replyNum":0,"userId":"4bb9b425fd7e4d12a5d8dea5ed829fcb","userName":"夏薇"},{"commentContent":"咋回事","commentDate":"2017-10-21 13:48","commentId":95,"commentList":[],"commentPraising":0,"commentWorks":51,"isPraising":0,"replyNum":0,"userId":"4bb9b425fd7e4d12a5d8dea5ed829fcb","userName":"夏薇"},{"commentContent":"咋回事","commentDate":"2017-09-26 22:48","commentId":85,"commentList":[],"commentPraising":0,"commentWorks":51,"isPraising":0,"replyNum":0,"userHeadshot":"userimage-1254218478.picsh.myqcloud.com/1506491045021.jpeg","userId":"cca43b8b03224c2b92adf46f3e2994bb","userName":"魏鹏"},{"commentContent":"哈哈，还不错的视频","commentDate":"2017-09-26 22:47","commentId":82,"commentList":[],"commentPraising":0,"commentWorks":51,"isPraising":0,"replyNum":0,"userHeadshot":"userimage-1254218478.picsh.myqcloud.com/1506491045021.jpeg","userId":"cca43b8b03224c2b92adf46f3e2994bb","userName":"魏鹏"}]}
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
         * actor : {"actorFans":0,"actorId":1,"isFocus":2}
         * commentList : [{"commentContent":"了了了了了了了了来咯哦哦哦我","commentDate":"2017-10-25 10:49","commentId":117,"commentList":[],"commentPraising":0,"commentWorks":51,"isPraising":0,"replyNum":0,"userId":"4bb9b425fd7e4d12a5d8dea5ed829fcb","userName":"夏薇"},{"commentContent":"一回事","commentDate":"2017-10-25 10:49","commentId":118,"commentList":[],"commentPraising":0,"commentWorks":51,"isPraising":0,"replyNum":0,"userId":"4bb9b425fd7e4d12a5d8dea5ed829fcb","userName":"夏薇"},{"commentContent":"咋回事","commentDate":"2017-10-25 10:46","commentId":115,"commentList":[],"commentPraising":0,"commentWorks":51,"isPraising":0,"replyNum":0,"userId":"4bb9b425fd7e4d12a5d8dea5ed829fcb","userName":"夏薇"},{"commentContent":"了了了了了了了了","commentDate":"2017-10-25 10:46","commentId":116,"commentList":[],"commentPraising":0,"commentWorks":51,"isPraising":0,"replyNum":0,"userId":"4bb9b425fd7e4d12a5d8dea5ed829fcb","userName":"夏薇"},{"commentContent":"困了","commentDate":"2017-10-25 10:45","commentId":114,"commentList":[],"commentPraising":0,"commentWorks":51,"isPraising":0,"replyNum":0,"userId":"4bb9b425fd7e4d12a5d8dea5ed829fcb","userName":"夏薇"},{"commentContent":"美得很","commentDate":"2017-10-21 13:48","commentId":94,"commentList":[],"commentPraising":0,"commentWorks":51,"isPraising":0,"replyNum":0,"userId":"4bb9b425fd7e4d12a5d8dea5ed829fcb","userName":"夏薇"},{"commentContent":"咋回事","commentDate":"2017-10-21 13:48","commentId":95,"commentList":[],"commentPraising":0,"commentWorks":51,"isPraising":0,"replyNum":0,"userId":"4bb9b425fd7e4d12a5d8dea5ed829fcb","userName":"夏薇"},{"commentContent":"咋回事","commentDate":"2017-09-26 22:48","commentId":85,"commentList":[],"commentPraising":0,"commentWorks":51,"isPraising":0,"replyNum":0,"userHeadshot":"userimage-1254218478.picsh.myqcloud.com/1506491045021.jpeg","userId":"cca43b8b03224c2b92adf46f3e2994bb","userName":"魏鹏"},{"commentContent":"哈哈，还不错的视频","commentDate":"2017-09-26 22:47","commentId":82,"commentList":[],"commentPraising":0,"commentWorks":51,"isPraising":0,"replyNum":0,"userHeadshot":"userimage-1254218478.picsh.myqcloud.com/1506491045021.jpeg","userId":"cca43b8b03224c2b92adf46f3e2994bb","userName":"魏鹏"}]
         */

        private ActorBean actor;
        private List<CommentListBean> commentList;

        public ActorBean getActor() {
            return actor;
        }

        public void setActor(ActorBean actor) {
            this.actor = actor;
        }

        public List<CommentListBean> getCommentList() {
            return commentList;
        }

        public void setCommentList(List<CommentListBean> commentList) {
            this.commentList = commentList;
        }

        public static class ActorBean {
            /**
             * actorFans : 0
             * actorId : 1
             * isFocus : 2
             */

            private int actorFans;
            private int actorId;
            private int isFocus;

            public int getActorFans() {
                return actorFans;
            }

            public void setActorFans(int actorFans) {
                this.actorFans = actorFans;
            }

            public int getActorId() {
                return actorId;
            }

            public void setActorId(int actorId) {
                this.actorId = actorId;
            }

            public int getIsFocus() {
                return isFocus;
            }

            public void setIsFocus(int isFocus) {
                this.isFocus = isFocus;
            }
        }

        public static class CommentListBean {
            /**
             * commentContent : 了了了了了了了了来咯哦哦哦我
             * commentDate : 2017-10-25 10:49
             * commentId : 117
             * commentList : []
             * commentPraising : 0
             * commentWorks : 51
             * isPraising : 0
             * replyNum : 0
             * userId : 4bb9b425fd7e4d12a5d8dea5ed829fcb
             * userName : 夏薇
             * userHeadshot : userimage-1254218478.picsh.myqcloud.com/1506491045021.jpeg
             */

            private String commentContent;
            private String commentDate;
            private int commentId;
            private int commentPraising;
            private int commentWorks;
            private int isPraising;
            private int replyNum;
            private String userId;
            private String userName;
            private String userHeadshot;
            private List<?> commentList;

            public String getCommentContent() {
                return commentContent;
            }

            public void setCommentContent(String commentContent) {
                this.commentContent = commentContent;
            }

            public String getCommentDate() {
                return commentDate;
            }

            public void setCommentDate(String commentDate) {
                this.commentDate = commentDate;
            }

            public int getCommentId() {
                return commentId;
            }

            public void setCommentId(int commentId) {
                this.commentId = commentId;
            }

            public int getCommentPraising() {
                return commentPraising;
            }

            public void setCommentPraising(int commentPraising) {
                this.commentPraising = commentPraising;
            }

            public int getCommentWorks() {
                return commentWorks;
            }

            public void setCommentWorks(int commentWorks) {
                this.commentWorks = commentWorks;
            }

            public int getIsPraising() {
                return isPraising;
            }

            public void setIsPraising(int isPraising) {
                this.isPraising = isPraising;
            }

            public int getReplyNum() {
                return replyNum;
            }

            public void setReplyNum(int replyNum) {
                this.replyNum = replyNum;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserHeadshot() {
                return userHeadshot;
            }

            public void setUserHeadshot(String userHeadshot) {
                this.userHeadshot = userHeadshot;
            }

            public List<?> getCommentList() {
                return commentList;
            }

            public void setCommentList(List<?> commentList) {
                this.commentList = commentList;
            }
        }
    }
}

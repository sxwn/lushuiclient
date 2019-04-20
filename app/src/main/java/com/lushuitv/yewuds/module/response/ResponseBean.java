package com.lushuitv.yewuds.module.response;

import java.util.List;

/**
 * Description
 * Created by weip
 * Date on 2017/9/18.
 */

public class ResponseBean {
    private String commentContent;
    private String commentDate;
    private int commentId;
    private List<?> commentList;
    private int commentPraising;
    private int commentWorks;
    private int isPraising;
    private int replyNum;
    private String userHeadshot;
    private String userId;
    private String userName;


    public String getUserHeadshot() {
        return userHeadshot;
    }

    public void setUserHeadshot(String userHeadshot) {
        this.userHeadshot = userHeadshot;
    }

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

    public List<?> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<?> commentList) {
        this.commentList = commentList;
    }


}

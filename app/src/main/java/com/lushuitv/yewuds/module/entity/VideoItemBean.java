package com.lushuitv.yewuds.module.entity;

import java.io.Serializable;

/**
 * Description 视频item
 * Created by weip
 * Date on 2017/9/8.
 */

public class VideoItemBean implements Serializable{
    private String actorHeadshot;
    private String actorName;
    private String contentDuration;
    private int isColletion;
    private int isPraising;
    private String worksActor;
    private int worksCommentNum;
    private String worksCover;
    private String worksDate;
    private String worksDescription;
    private int worksId;
    private String worksName;
    private int worksPlayNum;
    private int worksPraising;
    private int worksType;

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

    public String getContentDuration() {
        return contentDuration;
    }

    public void setContentDuration(String contentDuration) {
        this.contentDuration = contentDuration;
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

    @Override
    public String toString() {
        return "VideoItemBean{" +
                "actorHeadshot='" + actorHeadshot + '\'' +
                ", actorName='" + actorName + '\'' +
                ", contentDuration='" + contentDuration + '\'' +
                ", isColletion=" + isColletion +
                ", isPraising=" + isPraising +
                ", worksActor='" + worksActor + '\'' +
                ", worksCommentNum=" + worksCommentNum +
                ", worksCover='" + worksCover + '\'' +
                ", worksDate='" + worksDate + '\'' +
                ", worksDescription='" + worksDescription + '\'' +
                ", worksId=" + worksId +
                ", worksName='" + worksName + '\'' +
                ", worksPlayNum=" + worksPlayNum +
                ", worksPraising=" + worksPraising +
                ", worksType=" + worksType +
                '}';
    }
}

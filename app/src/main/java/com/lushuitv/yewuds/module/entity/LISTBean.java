package com.lushuitv.yewuds.module.entity;

import java.io.Serializable;

/**
 * Description
 * Created by weip
 * Date on 2017/9/15.
 */

public class LISTBean implements Serializable{
    //checked
    private boolean isShow; // 是否显示CheckBox
    private boolean isChecked; // 是否选中CheckBox
    //data
    private String actorName;
    private String contentDuration;
    private int isColletion;
    private int isPraising;
    private String tagName;
    private int worksActor;
    private String worksCode;
    private int worksCommentNum;
    private int worksContentNum;
    private String worksCover;
    private int worksId;
    private String worksName;
    private int worksPlayNum;
    private int worksPraising;
    private int worksType;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
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

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getWorksActor() {
        return worksActor;
    }

    public void setWorksActor(int worksActor) {
        this.worksActor = worksActor;
    }

    public String getWorksCode() {
        return worksCode;
    }

    public void setWorksCode(String worksCode) {
        this.worksCode = worksCode;
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
}

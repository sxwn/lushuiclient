package com.lushuitv.yewuds.module.entity;

import com.lushuitv.yewuds.module.response.ActorWorkInfo;
import com.lushuitv.yewuds.module.response.ContentListBean;

import java.io.Serializable;
import java.util.List;

/**
 * Description
 * Created by weip
 * Date on 2017/9/14.
 */

public class WorksListBean implements Serializable{
    //checked
    private boolean isShow; // 是否显示CheckBox
    private boolean isChecked; // 是否选中CheckBox

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

    /**
     * actorHeadshot : http://img.cfrhls.com/xying_image.jpg
     * actorName : 新颖
     * contentDuration : 00:02:45
     * isColletion : 0
     * isPraising : 0
     * tagName : 美臀
     * worksActor : 47
     * worksCode : lushui015
     * worksCommentNum : 1
     * worksCover : http://img.cfrhls.com/xying01_01cover.jpg
     * worksDate : 2017-11-17 16:47:39.0
     * worksId : 110
     * worksName : LSTV_SEX_0015_新颖
     * worksPlayNum : 97
     * worksPraising : 3
     * worksType : 1
     */

    private String actorHeadshot;
    private String actorName;
    private String contentDuration;
    private int isColletion;
    private int isPraising;
    private String tagName;
    private int worksActor;
    private String worksCode;
    private int worksCommentNum;
    private String worksCover;
    private String worksDate;
    private int worksId;
    private String worksName;
    private int worksPlayNum;
    private int worksPraising;
    private int worksType;
    private List<ContentListBean> contentList;
    private int worksPrice;
    private int worksInsider;

    public int getWorksPrice() {
        return worksPrice;
    }

    public void setWorksPrice(int worksPrice) {
        this.worksPrice = worksPrice;
    }

    public int getWorksInsider() {
        return worksInsider;
    }

    public void setWorksInsider(int worksInsider) {
        this.worksInsider = worksInsider;
    }

    public List<ContentListBean> getContentList() {
        return contentList;
    }

    public void setContentList(List<ContentListBean> contentList) {
        this.contentList = contentList;
    }

    public void setActorHeadshot(String actorHeadshot) {
        this.actorHeadshot = actorHeadshot;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public void setContentDuration(String contentDuration) {
        this.contentDuration = contentDuration;
    }

    public void setIsColletion(int isColletion) {
        this.isColletion = isColletion;
    }

    public void setIsPraising(int isPraising) {
        this.isPraising = isPraising;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public void setWorksActor(int worksActor) {
        this.worksActor = worksActor;
    }

    public void setWorksCode(String worksCode) {
        this.worksCode = worksCode;
    }

    public void setWorksCommentNum(int worksCommentNum) {
        this.worksCommentNum = worksCommentNum;
    }

    public void setWorksCover(String worksCover) {
        this.worksCover = worksCover;
    }

    public void setWorksDate(String worksDate) {
        this.worksDate = worksDate;
    }

    public void setWorksId(int worksId) {
        this.worksId = worksId;
    }

    public void setWorksName(String worksName) {
        this.worksName = worksName;
    }

    public void setWorksPlayNum(int worksPlayNum) {
        this.worksPlayNum = worksPlayNum;
    }

    public void setWorksPraising(int worksPraising) {
        this.worksPraising = worksPraising;
    }

    public void setWorksType(int worksType) {
        this.worksType = worksType;
    }

    public String getActorHeadshot() {
        return actorHeadshot;
    }

    public String getActorName() {
        return actorName;
    }

    public String getContentDuration() {
        return contentDuration;
    }

    public int getIsColletion() {
        return isColletion;
    }

    public int getIsPraising() {
        return isPraising;
    }

    public String getTagName() {
        return tagName;
    }

    public int getWorksActor() {
        return worksActor;
    }

    public String getWorksCode() {
        return worksCode;
    }

    public int getWorksCommentNum() {
        return worksCommentNum;
    }

    public String getWorksCover() {
        return worksCover;
    }

    public String getWorksDate() {
        return worksDate;
    }

    public int getWorksId() {
        return worksId;
    }

    public String getWorksName() {
        return worksName;
    }

    public int getWorksPlayNum() {
        return worksPlayNum;
    }

    public int getWorksPraising() {
        return worksPraising;
    }

    public int getWorksType() {
        return worksType;
    }
}

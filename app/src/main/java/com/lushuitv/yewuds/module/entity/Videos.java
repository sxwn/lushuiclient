package com.lushuitv.yewuds.module.entity;

/**
 * Description
 * Created by weip
 * Date on 2017/9/1.
 */

public class Videos {

    private String urlImage;
    private String urlVideo;

    public Videos(String urlImage, String urlVideo) {
        this.urlImage = urlImage;
        this.urlVideo = urlVideo;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }
}

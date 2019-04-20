package com.lushuitv.yewuds.module.response;

import java.io.Serializable;

/**
 * Created by weip on 2017\12\15 0015.
 */

public class ContentListBean implements Serializable{
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

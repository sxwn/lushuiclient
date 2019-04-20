package com.lushuitv.yewuds.share;

import android.app.Activity;
import android.content.Context;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.app.ApiConstant;
import com.lushuitv.yewuds.constant.Config;
import com.socks.library.KLog;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

public class ShareTool {

    private static Activity mActivity;
    private static ShareTool instance;


    private ShareTool() {}

    public static ShareTool getInstance(Context context) {
        mActivity = (Activity) context;
        if (instance == null) {
            instance = new ShareTool();
        }
        return instance;
    }

    /**
     * 分享到短信
     *
     * @param shareContent
     * @param url
     */
    public void shareToSms(String shareContent, String url) {
        new ShareAction(mActivity).setPlatform(SHARE_MEDIA.SMS)
                .withText(shareContent + url)
                .setCallback(mShareListener)
                .share();
    }

    /**
     * 分享到微信好友
     *
     * @param shareContent
     * @param url
     */
    public void shareToWX(String title, String shareContent, String url,int thumbnailimg) {
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        UMImage thumb = new UMImage(mActivity,thumbnailimg);
        web.setThumb(thumb);  //缩略图
        web.setDescription(shareContent);//描述
        new ShareAction(mActivity).setPlatform(SHARE_MEDIA.WEIXIN)
                .withMedia(web)
                .setCallback(mShareListener)
                .share();
    }

    /**
     * 分享到微信朋友圈
     * @param shareContent
     * @param url
     */
    public void shareToWXCircle(String title,String shareContent, String url,int thumbnailimg) {
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        UMImage thumb = new UMImage(mActivity,thumbnailimg);
        web.setThumb(thumb);  //缩略图
        web.setDescription(shareContent);//描述
        new ShareAction(mActivity).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                .withMedia(web)
                .setCallback(mShareListener)
                .share();
    }

    /**
     * qq空间
     * @param title
     * @param shareContent
     * @param url
     */
    public void shareToQQSpace(String title, String shareContent, String url,int thumbnailimg) {
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        UMImage thumb = new UMImage(mActivity,thumbnailimg);
        web.setThumb(thumb);  //缩略图
        web.setDescription(shareContent);//描述
        new ShareAction(mActivity).setPlatform(SHARE_MEDIA.QZONE)
                .withMedia(web)
                .setCallback(mShareListener)
                .share();
    }

    /**
     * 微博
     * @param title
     * @param shareContent
     * @param url
     */
    public void shareToWeiBo(String title, String shareContent, String url,int thumbnailimg) {
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        UMImage thumb = new UMImage(mActivity,thumbnailimg);
        web.setThumb(thumb);  //缩略图
        web.setDescription(shareContent);//描述
        new ShareAction(mActivity).setPlatform(SHARE_MEDIA.SINA)
                .withMedia(web)
                .setCallback(mShareListener)
                .share();
    }

    /**
     * 分享结果回调
     */
    private UMShareListener mShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            KLog.e(share_media + " --->>> 分享成功");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            KLog.e(share_media + " --->>> 分享失败，原因--->>> " + throwable.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            KLog.e(share_media + " --->>> 分享取消");
        }
    };


}


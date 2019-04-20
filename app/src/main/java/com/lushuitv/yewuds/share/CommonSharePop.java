package com.lushuitv.yewuds.share;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.app.MyApp;
import com.lushuitv.yewuds.utils.UIUtils;
import com.umeng.socialize.ShareAction;

/**
 * 分享
 * Created by weip on 2017\12\4 0004.
 */

public class CommonSharePop implements View.OnClickListener {

    private CommonBottomPop mCommonBottomPop;
    private final ShareTool mShare;
    private String mShareTitle;
    private String mShareContent;
    private String mShareUrl;
    private int resourceId;
    private String mCircleContent;
    private String mMsgContent; //短信分享内容
    private Context context;

    public CommonSharePop(Context context) {
        this.context = context;
        mShare = ShareTool.getInstance(context);
        initView(context);
    }

    /**
     * 初始化UI
     * @param context
     */
    private void initView(Context context) {
        View mPopView = View.inflate(context, R.layout.popup_shared_bottom, null);
        Drawable wechatFriend = context.getResources().getDrawable(R.drawable.wechat_friend_icon);
        wechatFriend.setBounds(0, 0, UIUtils.dip2Px(context, 36), UIUtils.dip2Px(context, 36));
        Drawable wechatCircle = context.getResources().getDrawable(R.drawable.wechat_quan_icon);
        wechatCircle.setBounds(0, 0, UIUtils.dip2Px(context, 36), UIUtils.dip2Px(context, 36));
        Drawable qqFriend = context.getResources().getDrawable(R.drawable.qq_friend);
        qqFriend.setBounds(0, 0, UIUtils.dip2Px(context, 36), UIUtils.dip2Px(context, 36));
        Drawable weiboCircle = context.getResources().getDrawable(R.drawable.weibo_icon);
        weiboCircle.setBounds(0, 0, UIUtils.dip2Px(context, 36), UIUtils.dip2Px(context, 36));
        ((TextView)mPopView.findViewById(R.id.we_chat)).setCompoundDrawables(null, wechatFriend, null, null);
        ((TextView)mPopView.findViewById(R.id.we_chat_quan)).setCompoundDrawables(null, wechatCircle, null, null);
        ((TextView)mPopView.findViewById(R.id.we_qq_space_tv)).setCompoundDrawables(null, qqFriend, null, null);
        ((TextView)mPopView.findViewById(R.id.we_sina_weibo_tv)).setCompoundDrawables(null, weiboCircle, null, null);
        mPopView.findViewById(R.id.we_chat).setOnClickListener(this);
        mPopView.findViewById(R.id.we_chat_quan).setOnClickListener(this);
        mPopView.findViewById(R.id.we_qq_space_tv).setOnClickListener(this);
        mPopView.findViewById(R.id.we_sina_weibo_tv).setOnClickListener(this);
        mCommonBottomPop = new CommonBottomPop(context, mPopView);
        mCommonBottomPop.setOutsideClickable(true);
        mCommonBottomPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);//popupWindow隐藏时恢复屏幕正常透明度
            }
        });
    }
    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     *            屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity)context).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) context).getWindow().setAttributes(lp);
    }

    /**
     * 显示
     * @param parent
     */
    public void show(View parent) {
        setBackgroundAlpha(0.5f);
        mCommonBottomPop.show(parent);
    }

    /**
     * 设置分享的数据
     * @param shareTitle
     * @param shareContent
     * @param shareUrl
     */
    public void setShare(String shareTitle, String shareContent, String shareUrl,int resourceId) {
        this.mShareTitle = shareTitle;
        this.mShareContent = shareContent;
        this.mShareUrl = shareUrl;
        this.resourceId = resourceId;
    }

    /**
     * 设置分享的数据
     * @param circleContent
     */
    public void setShareContent(String circleContent) {
        this.mCircleContent = circleContent;
    }

    /**
     * 设置分享的数据
     * @param msgContent
     */
    public void setMsgContent(String msgContent) {
        this.mMsgContent = msgContent;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.we_chat:
                mCommonBottomPop.dismiss();
                //R.drawable.share_thumbnail
                mShare.shareToWX(mShareTitle, mShareContent, mShareUrl,resourceId);
                break;
            case R.id.we_chat_quan:
                mCommonBottomPop.dismiss();
                mShare.shareToWXCircle(mShareTitle, mShareContent, mShareUrl,resourceId);
                break;
            case R.id.we_qq_space_tv:
                mCommonBottomPop.dismiss();
                mShare.shareToQQSpace(mShareTitle,mCircleContent, mShareUrl,resourceId);
                break;
            case R.id.we_sina_weibo_tv:
                mCommonBottomPop.dismiss();
                mShare.shareToWeiBo(mShareTitle,mCircleContent, mShareUrl,resourceId);
//                new ShareAction(MyApp.getContext()).
                break;
        }
    }
}

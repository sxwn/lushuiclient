package com.lushuitv.yewuds.constant;

import java.util.List;

/**
 * Created by weip
 * Date on 2017/8/28.
 */

public class Constants {

    // APP_ID 替换为你的应用从官方网站申请到的合法appId
    public static final String WE_CHAT_APP_ID = "wx22f8942fab7c79ef";
    /**
     * 频道对应的请求参数
     */
    public static final String CHANNEL_CODE = "channelCode";
    public static final String IS_VIDEO_LIST = "isVideoList";

    //每次请求大小
    public static final int PAGE_SIZE = 6;
    //更新数据类型 0:正常加载、下拉刷新   1: 加载更多
    public static final int GET_DATA_TYPE_NORMAL = 0;
    public static final int GET_DATA_TYPE_LOADMORE = 1;
    //正常模式
    public static final int ITEM_TYPE_TEXT = 0;
    //福利模式
    public static final int ITEM_TYPE_IMAGE = 1;

}

package com.lushuitv.yewuds.utils;

import android.support.v7.widget.RecyclerView;

/**
 * Description 是否滑动到底部
 * Created by weip
 * Date on 2017/11/10.
 */

public class RecycleViewUtil {
    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }
}

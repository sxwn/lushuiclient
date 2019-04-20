package com.lushuitv.yewuds.share;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.lushuitv.yewuds.R;

/**
 * Created by weip on 2017\12\4 0004.
 */

public class CommonBottomPop extends PopupWindow implements View.OnClickListener{

    private boolean isOutsideClickable; // 外部是否可点击

    public CommonBottomPop(Context context, View showView) {
        super(context);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setFocusable(true);
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);//虚拟键盘不会遮挡
        initView(context, showView);
    }

    private void initView(Context context, View showView) {
        RelativeLayout mainLayout = (RelativeLayout) View.inflate(context, R.layout.common_bottom_pop_view, null);
        View aboveView = mainLayout.findViewById(R.id.out_above);
        FrameLayout middleView = (FrameLayout) mainLayout.findViewById(R.id.bottom_content);
        middleView.addView(showView);
        aboveView.setOnClickListener(this);
        // 添加视图
        this.setContentView(mainLayout);
        this.setAnimationStyle(R.style.PopAnim); // 动画
    }

    /**
     * 设置外面是否可点击关闭
     * @param clickable
     */
    public void setOutsideClickable( boolean clickable) {
        isOutsideClickable = clickable;
    }

    /**
     * 显示
     * @param parent
     */
    public void show(View parent) {
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.out_above:
                if(isOutsideClickable && isShowing()) {
                    dismiss();
                }
                break;
            default:
                break;
        }
    }
}

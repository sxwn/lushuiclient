package com.lushuitv.yewuds.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.lushuitv.yewuds.R;

/**
 * Description 自定义popwindow
 * Created by weip
 * Date on 2017/9/08.
 */

public class CustomPopupWindow extends PopupWindow implements View.OnClickListener{
    private Button btnTakePhoto, btnSelect, btnCancel;
    private View mPopView;
    private OnItemClickListener mListener;


    public View getmPopView() {
        return mPopView;
    }

    public void setmPopView(View mPopView) {
        this.mPopView = mPopView;
    }

    public CustomPopupWindow(Context context, View view) {
        super(context);
        init(context,view);
        setPopupWindow();
//        btnTakePhoto.setOnClickListener(this);
//        btnSelect.setOnClickListener(this);
//        btnCancel.setOnClickListener(this);

    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context,View v) {
        //绑定布局
        mPopView = v;
//        btnTakePhoto = (Button) mPopView.findViewById(R.id.id_btn_take_photo);
//        btnSelect = (Button) mPopView.findViewById(R.id.id_btn_select);
//        btnCancel = (Button) mPopView.findViewById(R.id.id_btn_cancelo);
    }

    /**
     * 设置窗口的相关属性
     */
    private void setPopupWindow() {
        this.setContentView(mPopView);// 设置View
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
        this.setAnimationStyle(R.style.mypopwindow_anim_style);// 设置动画
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
        mPopView.setOnTouchListener(new View.OnTouchListener() {// 如果触摸位置在窗口外面则销毁
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                int height = mPopView.findViewById(R.id.id_pop_layout).getTop();
//                int y = (int) event.getY();
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (y < height) {
//                        dismiss();
//                    }
//                }
                dismiss();
                return true;
            }
        });
    }

    /**
     * 定义一个接口，公布出去 在Activity中操作按钮的单击事件
     */
    public interface OnItemClickListener {
        void setOnItemClick(View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
//            mListener.setOnItemClick(v);
        }
    }
}

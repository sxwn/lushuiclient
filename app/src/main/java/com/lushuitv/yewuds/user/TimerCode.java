package com.lushuitv.yewuds.user;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by weip on 2017\10\8 0008.
 */

public class TimerCode extends CountDownTimer {

    private TextView btn;

    public TimerCode(long millisInFuture, long countDownInterval, TextView btn) {
        super(millisInFuture, countDownInterval);
        this.btn = btn;
    }

    @Override
    public void onFinish() {
        btn.setClickable(true);
        btn.setText("重新获取");
    }

    @Override
    public void onTick(long time) {
        btn.setClickable(false);
        btn.setText("重新获取" + time / 1000 + "s...");
    }
}

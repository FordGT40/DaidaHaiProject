package com.wisdom.project.util;

import android.widget.Button;

/**
 * @authorzhanglichao
 * @date2018/3/27 8:51
 * @package_name com.wisdom.nhoa.util
 */

public class MyCountDownTimer extends android.os.CountDownTimer{
    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    private Button bt_count;
    public MyCountDownTimer(long millisInFuture, long countDownInterval,Button button) {
        super(millisInFuture, countDownInterval);
        bt_count=button;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        bt_count.setClickable(false);
        bt_count.setText(millisUntilFinished/1000+"s");
    }

    @Override
    public void onFinish() {
        bt_count.setClickable(true);
        bt_count.setText("点击重新发送");
    }
}

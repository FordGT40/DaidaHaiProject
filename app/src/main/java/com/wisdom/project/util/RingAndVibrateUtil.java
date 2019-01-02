package com.wisdom.project.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Vibrator;

/**
 * Created by HanXueFeng on 2017/11/7.
 * 以下方法均必须在Activity生命周期结束时候
 * 进行取消操作。
 *
 */

public class RingAndVibrateUtil {

    /**
     * 播放本地音频文件
     *
     * @param context
     * @param fileName
     */
    public static MediaPlayer startRingTarget(Context context, String fileName) {
        MediaPlayer player = new MediaPlayer();
        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor afd = assetManager.openFd(fileName);
            player.setDataSource(afd.getFileDescriptor(),
                    afd.getStartOffset(), afd.getLength());
            player.setLooping(true);//循环播放
            player.prepare();
            player.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return player;
    }

    /**
     * 停止播放铃声
     * @param player
     */
    public static void stopRing(MediaPlayer player) {
        if (player != null) {
            player.stop();
        }
    }
    /**
     * 停止震动
     */
    public static void stopVibrate(Vibrator vibrator) {
        vibrator.cancel();
    }

    /**
     * 开始震动
     */
    public static Vibrator startVibrate(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {1000, 2000, 1000, 2000};   // 停止 开启 停止 开启
        vibrator.vibrate(pattern, 2);           //重复两次上面的pattern 如果只想震动一次，index设为
        return vibrator;
    }

}

package com.wisdom.project.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author HanXueFeng
 * @ProjectName project： Nhoa
 * @class package：com.wisdom.nhoa.util.http_util
 * @class describe：图片有关的工具类
 * @time 2018/4/9 15:15
 * @change
 */

public class PicUtil {
    /**
     * 将bitmap保存为JPG格式的图片
     *
     * @param bitmap
     */
    public static void saveMypic(Bitmap bitmap, OnPicSaveSuccessListener listener , Context context) {
        //非空判断
        if (bitmap == null) {
            return;
        }
        // 保存图片
        try {
            String filePath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + System.currentTimeMillis() + ".jpg";
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }
            String url = file.getAbsolutePath();
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();
            listener.onPicSaveSuccess(file,url,Environment.getExternalStorageDirectory() + "/nhoa");
        } catch (Exception e) {
            e.printStackTrace();
           listener.onPicSaveFail(e);
        }
    }

    public interface OnPicSaveSuccessListener {
        void onPicSaveSuccess(File file, String filePath, String folderPath);
        void onPicSaveFail(Exception e);
    }

    /**
     * 将View转换成位图
     *
     * @param view
     * @return
     */
    public static Bitmap makeView2Bitmap(View view) {
        //View是你需要绘画的View
        int width = view.getWidth();
        int height = view.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        //如果不设置canvas画布为白色，则生成透明   							canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }


}

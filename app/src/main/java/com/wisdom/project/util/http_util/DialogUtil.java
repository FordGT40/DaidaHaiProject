package com.wisdom.project.util.http_util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * @author HanXueFeng
 * @ProjectName project： Nhoa
 * @class package：com.wisdom.nhoa.util.http_util
 * @class describe：
 * @time 2018/3/29 11:16
 * @change
 */

public class DialogUtil {
    public static void showConfirmDialog(Context context, String msg, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage(msg)
                .setPositiveButton("确定", listener)
                .create().show();
    }

}

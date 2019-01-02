package com.wisdom.project.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Administrator on 2017/4/18.
 */

public class U {
    public static int login_state = 0;// 0-未登录
    private static ProgressDialog progressDialog;
    /**
     * 显示加载对话框 加载数据的对话框
     */
    public static void showLoadingDialog(Context context) {

        progressDialog = new ProgressDialog(context);
        if (!progressDialog.isShowing()) {
            progressDialog.setMessage("正在加载.....");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
        }
    }




    public static void showLoginDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("正在登录.....");
        progressDialog.show();
    }

    public static void showDeleteDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("正在删除.....");
        progressDialog.show();

    }

    /**
     * @param context 下载中的对话框
     */
    public static void showDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("正在下载中.....");
        progressDialog.show();
    }

    // 关闭对话框
    public static void closeDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    /**
     * @param context 上传对话框
     */
    public static void showUploadDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("正在上传中.....");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    /**
     * 关闭加载对话框
     */
    public static void closeLoadingDialog() {
        if (progressDialog != null) {
            progressDialog.cancel();
        }
    }





}

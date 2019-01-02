package com.wisdom.project.util;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by Mustang on 16/8/29.
 */
public class SoftInputUtil {

    /**
     * 任何情况下，直接隐藏键盘
     *不好使的方法
     * @param context
     */
    public static void hideSoftInput(Context context, EditText view) {
//        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        boolean isOpen=imm.isActive();//isOpen若返回true，则表示输入法打开
//        if(isOpen){//如果键盘打开状态，那么隐藏键盘
//         imm.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
//        }
//        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSaoftInput(view,InputMethodManager.SHOW_FORCED);
//                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }


    /**
     * 任何情况下，直接隐藏键盘
     *
     * @param context
     */
    public static void hideSoftInput(Context context) {
//        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        boolean isOpen=imm.isActive();//isOpen若返回true，则表示输入法打开
//        if(isOpen){//如果键盘打开状态，那么隐藏键盘
//         imm.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
//        }else{
//
//        }


        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(((Activity) context).getWindow().getDecorView().getWindowToken(),
                    0);
        }

    }


}

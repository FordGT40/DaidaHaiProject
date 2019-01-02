package com.wisdom.project.util;

import android.content.Context;
import com.wisdom.ConstantString;
import com.wisdom.project.login.model.LoginModel;

/**
 * @author HanXueFeng
 * @ProjectName project： Nhoa
 * @class package：com.wisdom.nhoa.util
 * @class describe：
 * @time 2018/3/25 11:42
 * @change
 */

public class SharedPreferenceUtil {


    public static PrefsConfig getConfig(Context context) {
        return PrefsConfig.getPrefsConfig(new PrefsConfig.Config(
                ConstantString.INSTANCE.getSHARE_PER_INFO(), 0), context);
    }


    /**
     * 返回用户登录后的信息
     *
     * @param context
     * @return
     */
    public static LoginModel getUserInfo(Context context) {
        return ((LoginModel) getConfig(context).getSerializable(ConstantString.INSTANCE.getUSER_INFO()));
    }



}

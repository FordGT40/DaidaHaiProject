package com.wisdom.project.login.activity

import com.lzy.okgo.model.HttpParams
import com.wisdom.project.R
import com.wisdom.project.base.BaseActivity
import com.wisdom.project.util.RegularUtil
import com.wisdom.project.util.StrUtils
import com.wisdom.project.util.http_util.HttpUtil
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

/**
 * @ProjectName project： ExtraProject
 * @class package：com.wisdom.project.login.activity
 * @class describe：注册页面
 * @author HanXueFeng
 * @time 2019/1/2 10:29
 * @change
 */
class RegisterActivity : BaseActivity() {
    override fun setlayoutIds() {
        setContentView(R.layout.activity_register)
    }

    /**
     *  @describe 初始化相关操作
     *  @return
     *  @author HanXueFeng
     *  @time 2019/1/2  17:01
     */
    override fun initViews() {
        setTitle(R.string.register_title)
    }

    /**
     *  @describe 界面空值验证
     *  @return 通过返回true，否则false
     *  @author HanXueFeng
     *  @time 2019/1/2  17:01
     */
    private fun checkEmpty(): Boolean {
        var isChecked = true
        if (StrUtils.isEdtTxtEmpty(register_name)) {
            isChecked = false
            toast(R.string.register_name_hint)
        } else if (StrUtils.isEdtTxtEmpty(register_phone)) {
            isChecked = false
            toast(R.string.register_phone_hint)
        } else if (RegularUtil.isPhoneNumCorrect(register_phone)) {
            isChecked = false
            toast(R.string.register_phone_hint2)
        } else if (StrUtils.isEdtTxtEmpty(register_psw)) {
            isChecked = false
            toast(R.string.register_psw_hint)
        } else if (StrUtils.isEdtTxtEmpty(register_invate)) {
            isChecked = false
            toast(R.string.register_invate_code_hint)
        } else if (StrUtils.isEdtTxtEmpty(register_code)) {
            isChecked = false
            toast(R.string.register_code_hint)
        }
        return isChecked
    }

    /**
     *  @describe 提交数据到服务器进行注册
     *  @return
     *  @author HanXueFeng
     *  @time 2019/1/2  17:23
     */
    private fun sunmitData() {
        val httpParams = HttpParams()
        httpParams.put("nikeName", StrUtils.getEdtTxtContent(register_name))
        httpParams.put("mobile", StrUtils.getEdtTxtContent(register_phone))
        httpParams.put("code", StrUtils.getEdtTxtContent(register_invate))
        httpParams.put("password", StrUtils.getEdtTxtContent(register_psw))
        httpParams.put("captcha", StrUtils.getEdtTxtContent(register_code))
//HttpUtil.httpPost()

    }


}
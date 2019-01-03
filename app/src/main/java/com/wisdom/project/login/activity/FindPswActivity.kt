package com.wisdom.project.login.activity

import android.view.View
import com.google.gson.Gson
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.HttpParams
import com.wisdom.ConstantUrl
import com.wisdom.project.R
import com.wisdom.project.base.BaseActivity
import com.wisdom.project.login.model.ResetPswModel
import com.wisdom.project.util.RegularUtil
import com.wisdom.project.util.StrUtils
import com.wisdom.project.util.http_util.HttpUtil
import kotlinx.android.synthetic.main.activity_find_psw.*
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.toast
import org.json.JSONObject

/**
 * @ProjectName project： DaidaHaiProject
 * @class package：com.wisdom.project.login.activity
 * @class describe： 找回密码界面
 * @author HanXueFeng
 * @time 2019/1/3 13:16
 * @change
 */
class FindPswActivity : BaseActivity(), View.OnClickListener {
    companion object {
        val TAG = FindPswActivity::class.java.simpleName
    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_find_psw)
    }

    override fun initViews() {
        setTitle(R.string.find_psw_title)
        btn_find_psw_confirm.setOnClickListener(this@FindPswActivity)
        btn_find_psw_get_code.setOnClickListener(this@FindPswActivity)
    }

    /**
     *  @describe 页面内点击事件
     *  @return
     *  @author HanXueFeng
     *  @time 2019/1/3  13:50
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_find_psw_get_code -> {
                //获取验证码按钮点击事件
                //判断是否倒计时结束，避免在倒计时时多次点击导致重复请求接口
                if (btn_find_psw_get_code.isFinish) {
                    getValidateCode(StrUtils.getEdtTxtContent(find_psw_phone))
                }

            }
            R.id.btn_find_psw_confirm -> {
                //点击确认按钮
                if (validatePage()) {
                    submitData()
                }
            }
        }
    }

    /**
     *  @describe 验证界面空值
     *  @return 验证通过返回true
     *  @author HanXueFeng
     *  @time 2019/1/3  13:52
     */
    private fun validatePage(): Boolean {
        var isChecked = true
        if (StrUtils.isEdtTxtEmpty(find_psw_phone)) {
            isChecked = false
            toast(R.string.find_psw_phone_hint)
        } else if (!RegularUtil.isPhoneNumCorrect(find_psw_phone)) {
            isChecked = false
            toast(R.string.find_psw_phone_hint2)
        } else if (StrUtils.isEdtTxtEmpty(find_psw_new_psw)) {
            isChecked = false
            toast(R.string.find_psw_new_psw_hint)
        } else if (StrUtils.getEdtTxtContent(find_psw_new_psw).length < 6 ||
            StrUtils.getEdtTxtContent(find_psw_new_psw).length > 18
        ) {
            isChecked = false
            toast(R.string.find_psw_new_psw_hint2)
        } else if (StrUtils.isEdtTxtEmpty(find_psw_validate_code)) {
            isChecked = false
            toast(R.string.find_psw_validate_code)
        }
        return isChecked
    }

    /**
     *  @describe 获取验证码
     *  @return
     *  @params [phoneNum]
     *  @author hanxuefeng
     *  @time 2019/1/2
     */
    private fun getValidateCode(phoneNum: String) {
        if (StrUtils.isBlank(phoneNum)) {
            toast(R.string.find_psw_phone_hint)
        } else if (!RegularUtil.isPhoneNumCorrect(phoneNum)) {
            toast(R.string.find_psw_phone_hint2)
        } else {
            //开始倒计时
            btn_find_psw_get_code.start()
            //走发送验证码的接口
            val params = HttpParams()
            params.put("mobile", phoneNum)
            HttpUtil.httpPost(ConstantUrl.GET_VALIDATE_CODE_URL, params, object : StringCallback() {
                override fun onSuccess(t: String?, call: Call?, response: Response?) {
                    if (!StrUtils.isBlank(t)) {
                        val jsonObject = JSONObject(t)
                        if (jsonObject.getInt("code") == 200) {
                            toast(R.string.register_validate_code_succeed)
                        } else {
                            toast(jsonObject.getString("msg"))
                        }
                    } else {
                        toast(R.string.register_validate_code_fail)
                    }
                }
            })
        }
    }

    /**
     *  @describe 提交数据到接口
     *  @return
     *  @author HanXueFeng
     *  @time 2019/1/3  14:10
     */
    private fun submitData() {
        val resetPswModel = ResetPswModel(
            StrUtils.getEdtTxtContent(find_psw_phone),
            StrUtils.getEdtTxtContent(find_psw_new_psw),
            StrUtils.getEdtTxtContent(find_psw_validate_code)
        )
        val params = Gson().toJson(resetPswModel)
        println(TAG + "strParams:" + params)
        //参数json拼接完毕，访问接口  "\""+strParams+"\""
        HttpUtil.httpPostWithoutBaseString(ConstantUrl.RESET_PSW_URL, params, object : StringCallback() {
            override fun onSuccess(t: String?, call: Call?, response: Response?) {
                println(TAG + "onSuccess:" + t)
                if (!StrUtils.isBlank(t)) {
                    val jsonObject = JSONObject(t)
                    if (jsonObject.getInt("code") == 200) {
                        toast(R.string.find_psw_succeed)
                    } else {
                        toast(jsonObject.getString("msg"))
                    }
                } else {
                    toast(R.string.find_psw_fail)
                }
            }
        }
        )
    }
}
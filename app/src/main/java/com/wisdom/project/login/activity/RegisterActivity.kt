package com.wisdom.project.login.activity

import android.view.View
import com.google.gson.Gson
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.HttpParams
import com.wisdom.ConstantUrl
import com.wisdom.project.R
import com.wisdom.project.base.BaseActivity
import com.wisdom.project.login.model.RegisterSubmitClass
import com.wisdom.project.util.RegularUtil
import com.wisdom.project.util.StrUtils
import com.wisdom.project.util.http_util.HttpUtil
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.head_title_bar.*
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject

/**
 * @ProjectName project： ExtraProject
 * @class package：com.wisdom.project.login.activity
 * @class describe：注册页面
 * @author HanXueFeng
 * @time 2019/1/2 10:29
 * @change
 */
class RegisterActivity : BaseActivity(), View.OnClickListener {


    companion object {
        val TAG = RegisterActivity::class.java.simpleName
    }

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
        head_back_iv.visibility = View.GONE
        btn_get_code.setOnClickListener(this)
        btn_register.setOnClickListener(this)
        tv_login.setOnClickListener(this)
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
        } else if (!RegularUtil.isPhoneNumCorrect(register_phone)) {
            isChecked = false
            toast(R.string.register_phone_hint2)
        } else if (StrUtils.isEdtTxtEmpty(register_psw)) {
            isChecked = false
            toast(R.string.register_psw_hint)
        } else if (StrUtils.getEdtTxtContent(register_psw).length < 6 ||
            StrUtils.getEdtTxtContent(register_psw).length > 18
        ) {
            isChecked = false
            toast(R.string.register_psw_hint2)
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
    private fun submitData() {
        val loginSubmitClass = RegisterSubmitClass(
            StrUtils.getEdtTxtContent(register_name),
            StrUtils.getEdtTxtContent(register_phone),
            StrUtils.getEdtTxtContent(register_invate),
            StrUtils.getEdtTxtContent(register_psw),
            StrUtils.getEdtTxtContent(register_code)
        )
        val strParams = Gson().toJson(loginSubmitClass)
        println(TAG + "strParams:" + strParams)
        //参数json拼接完毕，访问接口  "\""+strParams+"\""
        HttpUtil.httpPostWithoutBaseString(ConstantUrl.REGISTER_URL, strParams, object : StringCallback() {
            override fun onSuccess(t: String?, call: Call?, response: Response?) {
                println(TAG + "onSuccess:" + t)
                if (!StrUtils.isBlank(t)) {
                    val jsonObject = JSONObject(t)
                    if (jsonObject.getInt("code") == 200) {
                        toast(R.string.register_succeed)
                    } else {
                        toast(jsonObject.getString("msg"))
                    }
                } else {
                    toast(R.string.register_fail)
                }
            }
        }
        )
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
            toast(R.string.register_phone_hint)
        } else if (!RegularUtil.isPhoneNumCorrect(phoneNum)) {
            toast(R.string.register_phone_hint2)
        } else {
            //开始倒计时
            btn_get_code.start()
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
     *  @describe 页面内点击事件
     *  @return
     *  @author HanXueFeng
     *  @time 2019/1/3  11:53
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_login -> {
                //页面下方”登录按钮“点击事件
                startActivity<LoginActivity>()
                finish()
            }
            R.id.btn_register -> {
                //注册按钮点击事件
                if (checkEmpty()) {
                    submitData()
                }
            }
            R.id.btn_get_code -> {
                //获取验证码按钮点击事件
                //判断是否倒计时结束，避免在倒计时时多次点击导致重复请求接口
                if (btn_get_code.isFinish) {
                    getValidateCode(StrUtils.getEdtTxtContent(register_phone))
                }
            }
        }
    }
}
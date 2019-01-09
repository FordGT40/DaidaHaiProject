package com.wisdom.project.login.activity

import android.content.Intent
import android.view.View
import com.lzy.okgo.model.HttpParams
import com.wisdom.ConstantString
import com.wisdom.ConstantUrl
import com.wisdom.project.R
import com.wisdom.project.base.BaseActivity
import com.wisdom.project.base.BroadCastManager
import com.wisdom.project.homepage.activity.HomePageActivity
import com.wisdom.project.login.model.LoginBackModel
import com.wisdom.project.util.RegularUtil
import com.wisdom.project.util.SharedPreferenceUtil
import com.wisdom.project.util.StrUtils
import com.wisdom.project.util.http_util.HttpUtil
import com.wisdom.project.util.http_util.callback.BaseModel
import com.wisdom.project.util.http_util.callback.JsonCallback
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.head_title_bar.*
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * @author HanXueFeng
 * @ProjectName project： FrameProject
 * @class package：com.wisdom.project.login.activity
 * @class describe：登录页面
 * @time 2018/12/20 11:39
 * @change
 */
class LoginActivity : BaseActivity(), View.OnClickListener {

    override fun setlayoutIds() {
        setContentView(R.layout.activity_login)
    }

    /**
     *  @describe 初始化界面信息
     *  @return
     *  @author HanXueFeng
     *  @time 2019/1/3  11:15
     */
    override fun initViews() {
        setTitle(R.string.login_title)
        head_back_iv.visibility = View.GONE
        tv_login_fast_register.setOnClickListener(this@LoginActivity)
        iv_login_delete.setOnClickListener(this@LoginActivity)
        tv_login_find_psw.setOnClickListener(this@LoginActivity)
        btn_login.setOnClickListener(this@LoginActivity)
        //切换密码是否可见按钮点击事件
        cb_login_hide.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                login_psw.inputType = 0x90//可见
            } else {
                login_psw.inputType = 0x81//不可见
            }
        }
//判断用户是否登陆过
        if (SharedPreferenceUtil.getUserInfo(this@LoginActivity) == null) {
            //没有登陆过
        } else {
            //登陆过 且是第一次登陆
            if (ConstantString.IS_FIRST_LOGIN) {
                startActivity<HomePageActivity>()
                this@LoginActivity.finish()
                ConstantString.IS_FIRST_LOGIN=false
            }
        }

    }

    /**
     *  @describe 页面内点击事件
     *  @return
     *  @author HanXueFeng
     *  @time 2019/1/3  11:48
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_login_fast_register -> {
                //快速注册按钮点击事件
                startActivity<RegisterActivity>()
                this.finish()
            }
            R.id.iv_login_delete -> {
                //账号后清空按钮点击事件
                login_accout.setText("")
            }
            R.id.btn_login -> {
                //登录按钮点击事件
                if (validatePageDate()) {
                    login()
                }
            }
            R.id.tv_login_find_psw -> {
                //找回密码页面
                startActivity<FindPswActivity>()
                this.finish()
            }
        }
    }


    /**
     *  @describe 登录方法
     *  @return
     *  @author HanXueFeng
     *  @time 2019/1/3  11:15
     */
    private fun login() {
        val params = HttpParams()
        params.put("account", StrUtils.getEdtTxtContent(login_accout))
        params.put("password", StrUtils.getEdtTxtContent(login_psw))
        HttpUtil.httpPost(ConstantUrl.LOGIN_URL, params, object : JsonCallback<BaseModel<LoginBackModel>>() {
            override fun onSuccess(t: BaseModel<LoginBackModel>?, call: Call?, response: Response?) {
                if (t != null) {
                    SharedPreferenceUtil.getConfig(this@LoginActivity).putSerializable(
                        ConstantString.SP_USER_LOGIN_MODEL_KEY, t!!.data
                    )
                    toast(R.string.login_succeed)
                    startActivity<HomePageActivity>()
                    //发送广播，刷新界面
                    val broadcastIntent = Intent()
                    broadcastIntent.action = ConstantString.BROAD_CAST_REFRESH_PAGE_DATA
                    BroadCastManager.getInstance().sendBroadCast(this@LoginActivity, broadcastIntent)
                    this@LoginActivity.finish()
                } else {
                    toast(R.string.login_fail)
                }
            }
        })
    }

    /**
     *  @describe 检查界面是否有空值
     *  @return
     *  @author HanXueFeng
     *  @time 2019/1/3  11:30
     */
    private fun validatePageDate(): Boolean {
        var isChecked = true
        if (StrUtils.isEdtTxtEmpty(login_accout)) {
            isChecked = false
            toast(R.string.login_accout_hint)
        } else if (!RegularUtil.isPhoneNumCorrect(login_accout)) {
            isChecked = false
            toast(R.string.register_phone_hint2)
        } else if (StrUtils.isEdtTxtEmpty(login_psw)) {
            isChecked = false
            toast(R.string.login_psw_hint)
        } else if (StrUtils.getEdtTxtContent(login_psw).length < 6 ||
            StrUtils.getEdtTxtContent(login_psw).length > 18
        ) {
            isChecked = false
            toast(R.string.register_psw_hint2)
        }
        return isChecked
    }

}

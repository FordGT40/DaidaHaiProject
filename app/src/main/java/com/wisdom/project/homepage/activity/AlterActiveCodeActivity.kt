package com.wisdom.project.homepage.activity

import android.view.View
import com.google.gson.Gson
import com.lzy.okgo.callback.StringCallback
import com.wisdom.ConstantUrl
import com.wisdom.project.R
import com.wisdom.project.base.BaseActivity
import com.wisdom.project.homepage.model.NickNameModel
import com.wisdom.project.util.SharedPreferenceUtil
import com.wisdom.project.util.StrUtils
import com.wisdom.project.util.http_util.HttpUtil
import kotlinx.android.synthetic.main.activity_alter_nick_name.*
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast
import org.json.JSONObject

class AlterActiveCodeActivity : BaseActivity(), View.OnClickListener {


    override fun setlayoutIds() {
        setContentView(R.layout.activity_alter_active_code)
    }

    override fun initViews() {
        setTitle(R.string.personal_alter_active_code)
        btn_confirm.setOnClickListener(this@AlterActiveCodeActivity)
    }

    /**
     *  @describe  修改激活码
     *  @return
     *  @params [activeCode]
     *  @author hanxuefeng
     *  @time 2019/1/3
     */
    private fun alterActiveCode(activeCode: String) {
        val param = Gson().toJson(NickNameModel(activeCode)).toString()
        HttpUtil.httpPostWithoutBaseStringWithToken(
            ConstantUrl.ALTER_ACTIVE_CODE, param
            , SharedPreferenceUtil.getUserInfo(this@AlterActiveCodeActivity).token,
            object : StringCallback() {
                override fun onSuccess(t: String?, call: Call?, response: Response?) {
                    val jsonObject = JSONObject(t)
                    if (jsonObject.getInt("code") == 200) {
                        toast(R.string.personal_alter_success)
                        this@AlterActiveCodeActivity.finish()
                    } else {
                        toast(jsonObject.getString("msg"))
                    }
                }
            }
        )
    }

    /**
     *  @describe 页面内点击事件
     *  @return
     *  @params
     *  @author hanxuefeng
     *  @time 2019/1/3
     */
    override fun onClick(p0: View?) {
        val newCode: String = personal_name.text.toString()
        if (StrUtils.isEmpty(newCode)) {
            toast(R.string.personal_head_name_hint)
        } else {
            alert {
                title("提示")
                message("确定修改新激活码为：$newCode 吗？")
                negativeButton("取消") {}
                positiveButton("确定") {
                    alterActiveCode(newCode)
                }
            }.show()
        }
    }
}
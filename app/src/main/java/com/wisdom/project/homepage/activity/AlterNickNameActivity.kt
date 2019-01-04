package com.wisdom.project.homepage.activity

import android.view.View
import com.google.gson.Gson
import com.lzy.okgo.callback.StringCallback
import com.wisdom.ConstantString
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

class AlterNickNameActivity : BaseActivity(), View.OnClickListener {


    override fun setlayoutIds() {
        setContentView(R.layout.activity_alter_nick_name)
    }

    override fun initViews() {
        setTitle(R.string.personal_alter_nick_name)
        btn_confirm.setOnClickListener(this@AlterNickNameActivity)
    }

    /**
     *  @describe  修改昵称
     *  @return
     *  @params [name]
     *  @author hanxuefeng
     *  @time 2019/1/3
     */
    private fun alterNickName(name: String) {
        val param=Gson().toJson(NickNameModel(name)).toString()
        HttpUtil.httpPostWithoutBaseStringWithToken(
            ConstantUrl.ALTER_NICK_NAME_URL, param
            , SharedPreferenceUtil.getUserInfo(this@AlterNickNameActivity).token,
            object : StringCallback() {
                override fun onSuccess(t: String?, call: Call?, response: Response?) {
                    val jsonObject = JSONObject(t)
                    if (jsonObject.getInt("code") == 200) {
                        toast(R.string.personal_alter_success)
                        setResult(ConstantString.CODE_REFRESH)
                        this@AlterNickNameActivity.finish()
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
        val newName: String = personal_name.text.toString()
        if (StrUtils.isEmpty(newName)) {
            toast(R.string.personal_head_name_hint)
        } else {
            alert {
                title("提示")
                message("确定修改昵称为：$newName 吗？")
                negativeButton("取消") {}
                positiveButton("确定") {
                    alterNickName(newName)
                }
            }.show()
        }
    }
}
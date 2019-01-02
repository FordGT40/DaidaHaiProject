package com.wisdom.project.login.activity

import com.wisdom.project.R
import com.wisdom.project.base.BaseActivity

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

    override fun initViews() {
        setTitle(R.string.register_title)
    }
}
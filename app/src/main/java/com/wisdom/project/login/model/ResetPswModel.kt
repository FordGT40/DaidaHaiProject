package com.wisdom.project.login.model

import java.io.Serializable

/**
 * @ProjectName project： DaidaHaiProject
 * @class package：com.wisdom.project.login.model
 * @class describe：重置密码的model
 * @author HanXueFeng
 * @time 2019/1/3 14:10
 * @change
 */
data class ResetPswModel(
    val mobile: String,
    val password: String,
    val captcha: String
) : Serializable
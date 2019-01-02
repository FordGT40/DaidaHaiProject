package com.wisdom.project.login.model

import java.io.Serializable

/**
 * @author HanXueFeng
 * @ProjectName project： Nhoa
 * @class package：com.wisdom.nhoa.mine.model
 * @class describe：
 * @time 2018/3/25 11:32
 * @change
 */

data class LoginModel(
    var uid: String?,
    var access_token: String? ,
    var expires_in: String? ,
    var organization: String? ,
    var user_name: String? ,
    var phone: String?  ,
    var passsword: String?  ,
    var login_name: String?  ,
    var simple_name: String?  ,
    var department_name: String? ,
    var role_name: String?): Serializable

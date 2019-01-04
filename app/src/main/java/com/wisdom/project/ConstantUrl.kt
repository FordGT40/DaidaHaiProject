package com.wisdom

/**
 * @author HanXueFeng
 * @ProjectName project： FrameProject
 * @class package：com.wisdom
 * @class describe：
 * @time 2018/12/20 13:12
 * @change
 */
object ConstantUrl {
    const val BASE_URL = "https://api.haximeishi.com/"//线上
    const val REGISTER_URL = "customer/register"//注册
    const val GET_VALIDATE_CODE_URL = "customer/getCaptcha"//获取验证码
    const val LOGIN_URL = "customer/login"//登录
    const val RESET_PSW_URL = "customer/modifyPassword"//修改密码
    const val GET_PERSONAL_INFO_URL = "customer/detail"//获得个人信息
    const val LOGOUT_URL = "customer/logout"//退出，注销
    const val ALTER_NICK_NAME_URL = "customer/modifyNikeName"//修改昵称
    const val UPLOAD_FILE_URL = "customer/upload"//上传文件
    const val ALTER_SEX_URL = "customer/modifyGender"//修改性别
    const val ALTER_ACTIVE_CODE = "customer/modifyCode"//修改激活码
}

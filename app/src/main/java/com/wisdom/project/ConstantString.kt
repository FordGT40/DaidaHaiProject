package com.wisdom

/**
 * @author HanXueFeng
 * @ProjectName project： FrameProject
 * @class package：com.wisdom
 * @class describe：
 * @time 2018/12/20 11:32
 * @change
 */
object ConstantString {
    var LOGIN_STATE: Boolean? = true
    //token过期后返回的code
    val CODE_TOKEN_ILLEGAL = 301
    val CODE_NO_FILE = 20013
    val CODE_NO_DATA = 30001
    val FILE_NO_CONTENT_CODE = "20402"
    //本地存储sp文件的名称
    val SHARE_PER_INFO = "nahe_oa_sp"//本地sp文件的存储名称
    //sp文件存储用户名
    val USER_NAME = "userName"
    //sp文件存储密码
    val PASS_WORD = "psw"
    //sp文件存储用户信息的key
    val USER_INFO = "userInfo"
    //sp文件存储用户权限信息的key
    val USER_PERMISSION = "userPermission"
    //用来唤起的Activity名字的key
    val CALL_TO_ACTIVITY = "callToActivity"//
    val FORM_NOTICE_OPEN = "fromNoticOpen"//
    val FORM_NOTICE_OPEN_DATA = "formNoticeOpenData"//

}

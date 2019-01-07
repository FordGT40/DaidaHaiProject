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
    val CODE_REFRESH = 0x123
    val BROAD_CAST_ALTER_SEX = "broad_cast_alter_sex"
    val BROAD_CAST_REFRESH_PAGE_DATA = "refresh_page_data"
    val BROAD_CAST_REFRESH_LOGOUT_DATA = "refresh_logout_data"
    val FILE_NO_CONTENT_CODE = "20402"
    //本地存储sp文件的名称
     val SHARE_PER_INFO = "daidahai_sp"//本地sp文件的存储名称
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
     val SP_USER_LOGIN_MODEL_KEY = "userLoginModel"

    //调取相册相机文件方面的参数常量
     val REQUEST_CAMERA = 101//调起相机
    val ALBUM_SELECT_CODE = 102//调起相册
    val FILE_SELECT_CODE = 103//调起文件选择
    var PIC_LOCATE = ""//拍照后图片缓存的地址
}

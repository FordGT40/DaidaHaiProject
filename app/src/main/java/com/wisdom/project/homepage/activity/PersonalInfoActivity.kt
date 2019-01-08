package com.wisdom.project.homepage.activity

import android.Manifest
import android.app.ProgressDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.HttpParams
import com.lzy.okgo.request.BaseRequest
import com.wisdom.ConstantString
import com.wisdom.ConstantUrl
import com.wisdom.project.R
import com.wisdom.project.base.ActivityManager
import com.wisdom.project.base.BaseActivity
import com.wisdom.project.base.BroadCastManager
import com.wisdom.project.homepage.helper.PopWindowHelper
import com.wisdom.project.homepage.model.GenderModel
import com.wisdom.project.homepage.model.PersonalInfoModel
import com.wisdom.project.login.activity.LoginActivity
import com.wisdom.project.util.BitmapUtil
import com.wisdom.project.util.FileUtils
import com.wisdom.project.util.FileUtils.UTIL_FILE_SELECT_CODE
import com.wisdom.project.util.SharedPreferenceUtil
import com.wisdom.project.util.U
import com.wisdom.project.util.http_util.HttpUtil
import com.wisdom.project.util.http_util.HttpUtil.httpGetWithToken
import com.wisdom.project.util.http_util.callback.BaseModel
import com.wisdom.project.util.http_util.callback.JsonCallback
import kotlinx.android.synthetic.main.activity_personal_info.*
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.alert
import org.jetbrains.anko.progressDialog
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.io.File

/**
 * @ProjectName project： DaidaHaiProject
 * @class package：com.wisdom.project.homepage.activity
 * @class describe：个人信息页面
 * @author HanXueFeng
 * @time 2019/1/3 16:05
 * @change
 */
class PersonalInfoActivity : BaseActivity(), View.OnClickListener {
    var mFilePath = ""
    lateinit var progressShow: ProgressDialog
    private val alterSexReceiver = AlterSexReceiver()

    // 指定路径
    override fun setlayoutIds() {
        setContentView(R.layout.activity_personal_info)
    }

    override fun initViews() {
        setTitle(R.string.personal_title)
        mFilePath = Environment.getExternalStorageDirectory().getPath()// 获取SD卡路径
        mFilePath = "$mFilePath/temp.png"
        rl_personal_info_head.setOnClickListener(this@PersonalInfoActivity)
        rl_personal_info_alter_name.setOnClickListener(this@PersonalInfoActivity)
        rl_personal_info_alter_sex.setOnClickListener(this@PersonalInfoActivity)
        ll_logout.setOnClickListener(this@PersonalInfoActivity)
        //判断用户是否登陆过
        if (SharedPreferenceUtil.getUserInfo(this@PersonalInfoActivity) != null) {
            getUserInfo()
        } else {
            toast("请先登录")
            startActivity<LoginActivity>()
        }
        //注册修改性别的广播接收者
        registerReceiver(alterSexReceiver, IntentFilter(ConstantString.BROAD_CAST_ALTER_SEX))
    }

    /**
     *  @describe 页面点击事件
     *  @return
     *  @author HanXueFeng
     *  @time 2019/1/3  16:24
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_personal_info_head -> {
                //用户授权
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.CAMERA
                    ) != PackageManager.PERMISSION_GRANTED
                ) run {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ),
                        1
                    )
                }
                //修改头像
                PopWindowHelper(this@PersonalInfoActivity).showUploadPop(this@PersonalInfoActivity)
            }
            R.id.rl_personal_info_alter_name -> {
                //修改昵称
                startActivityForResult(
                    Intent(this@PersonalInfoActivity, AlterNickNameActivity::class.java)
                    , ConstantString.CODE_REFRESH
                )

            }
            R.id.rl_personal_info_alter_sex -> {
                //修改性别
                PopWindowHelper(this@PersonalInfoActivity).showAlterSexPop(this, this)
            }
            R.id.ll_logout -> {
                //退出登录
                alert {
                    title("提示")
                    message("确认退出当前账号？")
                    negativeButton("取消") {}
                    positiveButton("确定") { logout() }
                }.show()
            }
        }
    }

    /**
     *  @describe 获取个人信息接口
     *  @return
     *  @author HanXueFeng
     *  @time 2019/1/3  16:43
     */
    private fun getUserInfo() {
        U.showLoadingDialog(this@PersonalInfoActivity)
        httpGetWithToken(ConstantUrl.GET_PERSONAL_INFO_URL, null
            , SharedPreferenceUtil.getUserInfo(this@PersonalInfoActivity).token,
            object : JsonCallback<BaseModel<PersonalInfoModel>>() {
                override fun onError(call: Call?, response: Response?, e: Exception?) {
                    super.onError(call, response, e)
                    U.closeLoadingDialog()
                }

                override fun onSuccess(t: BaseModel<PersonalInfoModel>?, call: Call?, response: Response?) {
                    if (t!!.code == 200) {
                        U.closeLoadingDialog()
                        //获取个人信息成功,将信息填充至界面
                        tv_personal_name.text = t.data.nikeName
                        tv_personal_phone.text = t.data.mobile
                        tv_personal_sex.text = t.data.gender
                        Glide.with(this@PersonalInfoActivity)
                            .load(t.data.image)
                            .apply(RequestOptions.circleCropTransform())
                            .into(iv_head)
                    } else {
                        toast(t.msg)
                    }
                }
            }

        )
    }

    /**
     *  @describe 退出登录
     *  @return
     *  @author HanXueFeng
     *  @time 2019/1/3  17:19
     */
    private fun logout() {
        U.showLoadingDialog(this@PersonalInfoActivity)
        httpGetWithToken(ConstantUrl.LOGOUT_URL,
            null, SharedPreferenceUtil.getUserInfo(this@PersonalInfoActivity).token, object : StringCallback() {
                override fun onError(call: Call?, response: Response?, e: java.lang.Exception?) {
                    super.onError(call, response, e)
                    U.closeLoadingDialog()
                }

                override fun onSuccess(t: String?, call: Call?, response: Response?) {
                    U.closeLoadingDialog()
                    val jsonObject = JSONObject(t)
                    if (jsonObject.getInt("code") == 200) {
                        toast("退出成功！")
                        SharedPreferenceUtil.getConfig(this@PersonalInfoActivity).putSerializable(
                            ConstantString.SP_USER_LOGIN_MODEL_KEY, null
                        )
                        startActivity<LoginActivity>()
                        //发送退出广播，更新界面显示
                        val broadcastIntent = Intent()
                        broadcastIntent.action = ConstantString.BROAD_CAST_REFRESH_LOGOUT_DATA
                        BroadCastManager.getInstance().sendBroadCast(this@PersonalInfoActivity, broadcastIntent)
                        ActivityManager.getActivityManagerInstance().clearAllActivity()
//                        startActivity<LoginActivity>()

                        finish()
                    } else {
                        toast(jsonObject.getString("msg"))
                    }
                }
            })
    }

    /**
     *  @describe 页面回调方法
     *  @return
     *  @params
     *  @author hanxuefeng
     *  @time 2019/1/3
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        println("data:${data?.data}")
        when (requestCode) {
            UTIL_FILE_SELECT_CODE -> {
                if (data != null) {
                    val uri = data.data
                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.setDataAndType(uri, "*/*")
                    intent.addCategory(Intent.CATEGORY_OPENABLE)
                    this.startActivity(intent)
                }
            }
            ConstantString.ALBUM_SELECT_CODE -> {//相册选择
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        // Get the Uri of the selected file
                        val uri = data.data
                        val path = FileUtils.getPathByUri4kitkat(this, uri)
                        println("***uri***:${data.data}")
                        println("***path***:$path")
                        if (mFilePath != "") {
                            uploadFiles(path)
                        }
                    }
                }

            }
            ConstantString.FILE_SELECT_CODE -> {//文件选择器选择
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        // Get the Uri of the selected file
                        val uri = data.data
                        val path = FileUtils.getPathByUri4kitkat(this, uri)
                        if (mFilePath != "") {
                            uploadFiles(path)
                        }
                    }
                }
            }
            ConstantString.REQUEST_CAMERA -> {//相机拍照选择
                if (!ConstantString.PIC_LOCATE.equals("")) {
                    uploadFiles(ConstantString.PIC_LOCATE)
                }
            }
            else -> {//刷新数据
                getUserInfo()

            }
        }

    }

    /**
     *  @describe 上传文件的方法
     *  @return
     *  @author HanXueFeng
     *  @time 2019/1/4  9:04
     */
    private fun uploadFiles(path: String) {
        val parameter = HttpParams()
        progressShow = progressDialog(title = "提示", message = "正在上传……")
        //压缩图片
        val afterPressureurl = BitmapUtil.compressImage(path)
        parameter.put("file", File(afterPressureurl))
        HttpUtil.uploadFiles(ConstantUrl.UPLOAD_FILE_URL, parameter
            , SharedPreferenceUtil.getUserInfo(this@PersonalInfoActivity).token
            , object : StringCallback() {
                override fun onBefore(request: BaseRequest<out BaseRequest<*>>?) {
                    super.onBefore(request)
                    progressShow.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                    progressShow.setCancelable(false)
                    progressShow.setCanceledOnTouchOutside(false)
                    progressShow.show()
                }

                override fun upProgress(currentSize: Long, totalSize: Long, progress: Float, networkSpeed: Long) {
                    super.upProgress(currentSize, totalSize, progress, networkSpeed)
                    progressShow.max = 100
                    progressShow.progress = (progress * 100).toInt()
                }

                override fun onError(call: Call?, response: Response?, e: java.lang.Exception?) {
                    super.onError(call, response, e)
                    progressShow?.dismiss()
                }

                override fun onSuccess(t: String?, call: Call?, response: Response?) {
                    progressShow?.dismiss()
                    toast("上传成功！")
                    val dataObject = JSONObject(t).getJSONObject("data")
                    Glide.with(this@PersonalInfoActivity)
                        .load(dataObject.getString("image"))
                        .apply(RequestOptions.circleCropTransform())
                        .into(iv_head)

                    getUserInfo()
                }
            })
    }


    /**
     *  @describe  修改昵称
     *  @return
     *  @params [name]
     *  @author hanxuefeng
     *  @time 2019/1/3
     */
    private fun alterSex(gender: String) {
        val param = Gson().toJson(GenderModel(gender)).toString()
        HttpUtil.httpPostWithoutBaseStringWithToken(
            ConstantUrl.ALTER_SEX_URL, param
            , SharedPreferenceUtil.getUserInfo(this@PersonalInfoActivity).token,
            object : StringCallback() {
                override fun onSuccess(t: String?, call: Call?, response: Response?) {
                    val jsonObject = JSONObject(t)
                    if (jsonObject.getInt("code") == 200) {
                        toast(R.string.personal_alter_success)
                        getUserInfo()
                    } else {
                        toast(jsonObject.getString("msg"))
                    }
                }
            }
        )
    }

    /**
     *  @describe 接收更改性别的广播接收器
     *  @return
     *  @author HanXueFeng
     *  @time 2019/1/4  11:19
     */
    private inner class AlterSexReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            //接到广播更改性别信息
            val sex = intent.getStringExtra("sex")
            alterSex(sex)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        BroadCastManager.getInstance().unregisterReceiver(this, alterSexReceiver)
    }

}
package com.wisdom.project.homepage.activity

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import com.bumptech.glide.Glide
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.HttpParams
import com.lzy.okgo.request.BaseRequest
import com.wisdom.ConstantString
import com.wisdom.ConstantUrl
import com.wisdom.project.R
import com.wisdom.project.base.BaseActivity
import com.wisdom.project.homepage.helper.PopWindowHelper
import com.wisdom.project.homepage.model.PersonalInfoModel
import com.wisdom.project.login.activity.LoginActivity
import com.wisdom.project.util.FileUtils
import com.wisdom.project.util.FileUtils.UTIL_FILE_SELECT_CODE
import com.wisdom.project.util.SharedPreferenceUtil
import com.wisdom.project.util.U
import com.wisdom.project.util.http_util.HttpUtil
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
        rl_personal_info_alter_active_code.setOnClickListener(this@PersonalInfoActivity)
        rl_personal_info_alter_sex.setOnClickListener(this@PersonalInfoActivity)
        ll_logout.setOnClickListener(this@PersonalInfoActivity)
        //判断用户是否登陆过
        if (SharedPreferenceUtil.getUserInfo(this@PersonalInfoActivity) != null) {
            getUserInfo()
        } else {
            toast("请先登录")
            startActivity<LoginActivity>()
        }
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
                //修改头像
                PopWindowHelper(this@PersonalInfoActivity).showUploadPop(this@PersonalInfoActivity)
            }
            R.id.rl_personal_info_alter_name -> {
                //修改昵称
                startActivityForResult(
                    Intent(this@PersonalInfoActivity, AlterNickNameActivity::class.java)
                    , ConstantString.CODE_REFRESH
                )
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
            }
            R.id.rl_personal_info_alter_active_code -> {
                //修改激活码
            }
            R.id.rl_personal_info_alter_sex -> {
                //修改性别
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
        HttpUtil.httpGetWithToken(ConstantUrl.GET_PERSONAL_INFO_URL, null
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
                        tv_personal_phone.text = t.data.mobile
                        tv_personal_sex.text = t.data.gender
                        Glide.with(this@PersonalInfoActivity)
                            .load(t.data.image).into(iv_head)
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
        HttpUtil.httpGetWithToken(ConstantUrl.LOGOUT_URL,
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
                        startActivity<LoginActivity>()
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
        parameter.put("file", File(path))
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
                    print("错误是:${e?.message}")
                    print("错误是:${e?.toString()}")
                    progressShow?.dismiss()
                }

                override fun onSuccess(t: String?, call: Call?, response: Response?) {
                    progressShow?.dismiss()
                    getUserInfo()
                }
            })
    }
}
package com.wisdom.project.homepage.fragment

import android.app.ProgressDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lzy.okgo.OkGo
import com.lzy.okgo.cache.CacheMode
import com.lzy.okgo.callback.FileCallback
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.HttpParams
import com.lzy.okgo.request.BaseRequest
import com.wisdom.ConstantString
import com.wisdom.ConstantUrl
import com.wisdom.project.R
import com.wisdom.project.base.BroadCastManager
import com.wisdom.project.homepage.activity.AlterActiveCodeActivity
import com.wisdom.project.homepage.activity.PersonalInfoActivity
import com.wisdom.project.homepage.model.PersonalInfoModel
import com.wisdom.project.login.activity.FindPswActivity
import com.wisdom.project.login.activity.LoginActivity
import com.wisdom.project.util.SharedPreferenceUtil
import com.wisdom.project.util.U
import com.wisdom.project.util.VersionUtil
import com.wisdom.project.util.http_util.HttpUtil
import com.wisdom.project.util.http_util.callback.BaseModel
import com.wisdom.project.util.http_util.callback.JsonCallback
import kotlinx.android.synthetic.main.fragment_mine.*
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.alert
import org.jetbrains.anko.progressDialog
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.io.File

/**
 * @ProjectName project： ExtraProject
 * @class package：com.wisdom.project.homepage.fragment
 * @class describe：我的
 * @author HanXueFeng
 * @time 2019/1/2 9:51
 * @change
 */
class MineFragment : Fragment(), View.OnClickListener {
    lateinit var progressDialog: ProgressDialog
    private val refreshDataReceiver = RefreshDataReceiver()
    private val logoutDataReceiver = LogoutDataReceiver()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mine, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rl_mine_personal_info.setOnClickListener(this@MineFragment)
        rl_mine_alter_psw.setOnClickListener(this@MineFragment)
        rl_mine_alter_active_code.setOnClickListener(this@MineFragment)
        rl_mine_check_update.setOnClickListener(this@MineFragment)
        iv_login.setOnClickListener(this@MineFragment)
        tv_mine_login.setOnClickListener(this@MineFragment)

    }

    /**
     *  @describe 当用户可见时候刷新界面
     *  @return
     *  @author HanXueFeng
     *  @time 2019/1/4  17:13
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        //注册刷新数据的广播接收者
        activity?.registerReceiver(refreshDataReceiver, IntentFilter(ConstantString.REFRESH_PAGE_DATA))
        //注册刷新退出登录的广播接收者
        activity?.registerReceiver(refreshDataReceiver, IntentFilter(ConstantString.REFRESH_LOGOUT_DATA))
        if (isVisibleToUser) {
            if (context?.getSharedPreferences(ConstantString.SHARE_PER_INFO, 0) != null) {
                if (SharedPreferenceUtil.getUserInfo(context) == null) {
                    //没有登陆过
                    context?.startActivity(Intent(context, LoginActivity::class.java))
                } else {
                    //之前登陆过，但是并不知道token是否过期
                    getUserInfo()
                }
            } else {
//                没有登陆过
                context?.startActivity(Intent(context, LoginActivity::class.java))
            }
        }
    }


    /**
     *  @describe 页面内的点击事件
     *  @return
     *  @author HanXueFeng
     *  @time 2019/1/3  15:55
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_mine_personal_info -> {
                //个人信息(未登录直接跳登录页)
                if (SharedPreferenceUtil.getUserInfo(context) != null) {
                    context?.startActivity(Intent(activity, PersonalInfoActivity::class.java))
                } else {
                    context?.startActivity<LoginActivity>()
                }
            }
            R.id.rl_mine_alter_psw -> {
                //修改密码
                context?.startActivity(Intent(activity, FindPswActivity::class.java))
            }
            R.id.rl_mine_alter_active_code -> {
                //修改激活码
                startActivity(
                    Intent(activity, AlterActiveCodeActivity::class.java)
                )
            }
            R.id.rl_mine_check_update -> {
                //检查版本更新
                checkVersion()
            }
            R.id.tv_mine_login,
            R.id.iv_login -> {
                if (SharedPreferenceUtil.getUserInfo(activity) == null) {
                    //未登录状态
                    context?.startActivity(Intent(activity, LoginActivity::class.java))
                } else {
                    //登录状态
                    context?.startActivity(Intent(activity, PersonalInfoActivity::class.java))
                }
            }
        }
    }

    /**
     *  @describe 检查版本更新
     *  @return
     *  @author HanXueFeng
     *  @time 2019/1/4  13:07
     */
    private fun checkVersion() {
        U.showLoadingDialog(activity)
        val params = HttpParams()
        params.put("platform", "android")
        HttpUtil.httpGet(ConstantUrl.CHECK_VERSION, params, object : StringCallback() {
            override fun onError(call: Call?, response: Response?, e: java.lang.Exception?) {
                super.onError(call, response, e)
                U.closeLoadingDialog()
            }

            override fun onSuccess(t: String?, call: Call?, response: Response?) {
                U.closeLoadingDialog()
                val jsonObject = JSONObject(t)
                if (jsonObject.getInt("code") == 200) {
                    //访问成功，去的版本号和下载地址
                    val dataJson = jsonObject.getJSONObject("data")
                    val fileUrl = dataJson.getString("fileUrl")
                    val version = dataJson.getString("version")
                    compareVersion(version, fileUrl)
                } else {
                    //失败
                    context!!.toast(jsonObject.getString("msg"))
                }
            }
        })
    }

    /**
     *  @describe 比对版本号进行更新apk操作
     *  @return
     *  @author HanXueFeng
     *  @time 2019/1/4  13:36
     */
    private fun compareVersion(versionCode: String, newApkUrl: String) {
        val code = VersionUtil.compareVersion(versionCode, VersionUtil.getVersion(context!!.applicationContext))
        if (code == 1) {
            //发现新版本，询问是否更新
            activity!!.alert {
                title("提示")
                message("发现新版本，是否更新")
                negativeButton("取消") {}
                positiveButton("马上更新") {
                    updateApk(newApkUrl)
                }
            }.show()
        } else if (code == 0) {
            //未发现新版本
            context!!.toast("已是最新版本")
        } else {
//            当前版本比服务器端版本还新
            context!!.toast("检查更新出错，请重试")
        }
    }


    /**
     * 根据下载地址对apk进行更新的操作
     * @param url
     */
    private fun updateApk(url: String) {
        progressDialog = activity!!.progressDialog(title = "提示", message = "正在下载")
        OkGo.get(HttpUtil.getAbsolteUrl(url.replace("'\'".toRegex(), "")))
            .cacheMode(CacheMode.DEFAULT)
            .execute(object :
                FileCallback("${context!!.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)}/", "TKB.apk") {
                override fun downloadProgress(currentSize: Long, totalSize: Long, progress: Float, networkSpeed: Long) {
                    progressDialog.max = 100
                    progressDialog.progress = (progress * 100).toInt()
                    super.downloadProgress(currentSize, totalSize, progress, networkSpeed)
                }

                override fun onBefore(request: BaseRequest<*>?) {
                    progressDialog.show()
                    super.onBefore(request)
                }

                @RequiresApi(api = Build.VERSION_CODES.O)
                override fun onSuccess(file: File, call: Call, response: Response) {
                    progressDialog.dismiss()
                    VersionUtil.installApk(context, file)
                }
            })
    }

    /**
     *  @describe 获取个人信息接口
     *  @return
     *  @author HanXueFeng
     *  @time 2019/1/3  16:43
     */
    private fun getUserInfo() {
        U.showLoadingDialog(activity)
        HttpUtil.httpGetWithToken(ConstantUrl.GET_PERSONAL_INFO_URL, null
            , SharedPreferenceUtil.getUserInfo(context).token,
            object : JsonCallback<BaseModel<PersonalInfoModel>>() {
                override fun onError(call: Call?, response: Response?, e: Exception?) {
                    super.onError(call, response, e)
                    U.closeLoadingDialog()
                }

                override fun onSuccess(t: BaseModel<PersonalInfoModel>?, call: Call?, response: Response?) {
                    if (t!!.code == 200) {
                        U.closeLoadingDialog()
                        //获取个人信息成功,将信息填充至界面
                        tv_mine_login.text = t.data.nikeName
                        Glide.with(context!!)
                            .load(t.data.image)
                            .apply(RequestOptions.circleCropTransform())
                            .into(iv_mine_head_img)
                    } else {
                        context!!.toast(t.msg)
                    }
                }
            }

        )
    }

    /**
     *  @describe 接收刷新界面的广播接收器
     *  @return
     *  @author HanXueFeng
     *  @time 2019/1/4  11:19
     */
    private inner class RefreshDataReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            //接到广播 刷新界面数据
            getUserInfo()
        }
    }

    /**
     *  @describe 接收退出登录的广播接收器
     *  @return
     *  @author HanXueFeng
     *  @time 2019/1/4  11:19
     */
    private inner class LogoutDataReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            //接到广播  退出登录成功  刷新界面数据
            tv_mine_login.text = context?.resources.getString(R.string.mine_hint)
            iv_mine_head_img.setImageResource(R.drawable.touxiang)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        BroadCastManager.getInstance().unregisterReceiver(activity, refreshDataReceiver)
        BroadCastManager.getInstance().unregisterReceiver(activity, logoutDataReceiver)
    }
}
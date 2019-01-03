package com.wisdom

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.support.v4.app.NotificationCompat
import android.util.Log
import cn.jpush.android.api.JPushInterface
import com.lzy.okgo.OkGo
import com.lzy.okgo.cache.CacheEntity
import com.lzy.okgo.cache.CacheMode
import com.lzy.okgo.cookie.store.PersistentCookieStore
import com.lzy.okgo.model.HttpHeaders
import com.wisdom.project.R
import com.wisdom.project.login.model.LoginModel
import com.wisdom.project.util.SharedPreferenceUtil
import com.wisdom.project.util.ToastUtil
import java.util.logging.Level

/**
 * @author HanXueFeng
 * @ProjectName project： Nhoa
 * @class package：com.wisdom.nhoa
 * @class describe：
 * @time 2018/1/30 13:55
 * @change
 */

class AppApplication : Application() {
    private var loginModel: LoginModel? = null//登陆后返回的用户信息。
    private var context: Context? = null

    private val headers = HttpHeaders()

    override fun onCreate() {
        super.onCreate()
        context = this
        ToastUtil.init(this)
        OkGo.init(this)
        instance = this
        okgoInit()
        //初始化极光推送
        JPushInterface.setDebugMode(true)
        JPushInterface.init(this)


    }


    /**
     * 将用户登录后信息缓存至sp文件
     *
     * @param loginModel
     */
    fun setUserInfo(loginModel: LoginModel) {
        this.loginModel = loginModel
        Log.i(TAG, "setUserInfo: " + loginModel.toString())
        SharedPreferenceUtil.getConfig(this)
            .putSerializable(ConstantString.USER_INFO, loginModel)
    }


    /**
     * 初始化OKGo的基本操作参数等信息
     */
    private fun okgoInit() {
        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkGo.getInstance()
                // 打开该调试开关,打印级别INFO,并不是异常,是为了显眼,不需要就不要加入该行
                // 最后的true表示是否打印okgo的内部异常，一般打开方便调试错误
                .debug("OkGo", Level.INFO, true)
                //如果使用默认的 60秒,以下三行也不需要传
                .setConnectTimeout(OkGo.DEFAULT_MILLISECONDS.toLong())  //全局的连接超时时间
                .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS.toLong())     //全局的读取超时时间
                .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS.toLong())    //全局的写入超时时间
                .addCommonHeaders(headers)
                //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                .setCacheMode(CacheMode.DEFAULT)

                //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)

                //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                .setRetryCount(3)

                //如果不想让框架管理cookie（或者叫session的保持）,以下不需要
                //              .setCookieStore(new MemoryCookieStore())            //cookie使用内存缓存（app退出后，cookie消失）
                .setCookieStore(PersistentCookieStore())        //cookie持久化存储，如果cookie不过期，则一直有效

                //可以设置https的证书,以下几种方案根据需要自己设置
                .setCertificates()                                //方法一：信任所有证书,不安全有风险
            //              .setCertificates(new SafeTrustManager())            //方法二：自定义信任规则，校验服务端证书
            //              .setCertificates(getAssets().open("srca.cer"))      //方法三：使用预埋证书，校验服务端证书（自签名证书）
            //              //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
            //               .setCertificates(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"))//

            //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
            //               .setHostnameVerifier(new SafeHostnameVerifier())

            //可以添加全局拦截器，不需要就不要加入，错误写法直接导致任何回调不执行
            //                .addInterceptor(new Interceptor() {
            //                    @Override
            //                    public Response intercept(Chain chain) throws IOException {
            //                        return chain.proceed(chain.request());
            //                    }
            //                })

            //这两行同上，不需要就不要加入
            //                    .addCommonHeaders(headers)  //设置全局公共头
            //                    .addCommonParams(params);   //设置全局公共参数

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    /**
     * 发送Notification通知的方法
     *
     * @param title
     * @param content
     */
    private fun sendNotification(title: String, content: String) {
        Log.i(TAG, "sendNotification: " + "0001")
        val notifyManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        Log.i(TAG, "sendNotification: " + "1")
        val builder = NotificationCompat.Builder(this)
            .setSmallIcon(R.mipmap.ic_launcher)
            //设置通知标题
            .setContentTitle(title)
            //设置通知内容
            .setContentText(content)
        Log.i(TAG, "sendNotification: " + "2")
        notifyManager.notify(1, builder.build())
        Log.i(TAG, "sendNotification: " + "3")
        //   ToastUtil.showToast("收到！");
    }

    companion object {
        var instance: AppApplication? = null
        val TAG = AppApplication::class.java.simpleName
    }

}

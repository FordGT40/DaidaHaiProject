package com.wisdom.project.homepage.fragment

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.wisdom.project.R
import com.wisdom.project.util.SharedPreferenceUtil
import com.wisdom.project.util.U
import kotlinx.android.synthetic.main.fragment_type_class.*
import kotlinx.android.synthetic.main.head_title_bar.*
import org.jetbrains.anko.toast

/**
 * @ProjectName project： ExtraProject
 * @class package：com.wisdom.project.homepage.fragment
 * @class describe：分类
 * @author HanXueFeng
 * @time 2019/1/2 9:51
 * @change
 */
class TypeClassFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_type_class, null, false)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
//            U.showLoadingDialog(context)
        }
    }

    @SuppressLint("ObsoleteSdkInt", "SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        head_back_iv.setOnClickListener {
            webView.goBack()
        }
        comm_head_title.text="粉丝福利购"
        val webSettings = webView?.settings
/**
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(
                webView,
                true
            )
        }

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                handler?.proceed()
                super.onReceivedSslError(view, handler, error)
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse {
                return super.shouldInterceptRequest(view, request)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
            }

            override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                return super.onJsConfirm(view, url, message, result)
            }

            override fun onHideCustomView() {

            }

            override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                super.onShowCustomView(view, callback)
            }

            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
            }

            override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                return super.onJsAlert(view, url, message, result)
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
            }
        }



        webSettings?.allowFileAccess = true
        webSettings?.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        webSettings?.setSupportZoom(true)
        webSettings?.builtInZoomControls = true
        webSettings?.useWideViewPort = true
        webSettings?.setSupportMultipleWindows(true)
        webSettings?.setAppCacheEnabled(true)
        webSettings?.javaScriptEnabled = true
        webSettings?.domStorageEnabled = true
        webSettings?.setGeolocationEnabled(true)
        webSettings?.setAppCacheMaxSize(Long.MAX_VALUE)
        webSettings?.pluginState = WebSettings.PluginState.ON_DEMAND
        webSettings?.loadWithOverviewMode = true
        webSettings?.cacheMode = WebSettings.LOAD_NO_CACHE
        val mUserAgent = webSettings?.userAgentString
        webSettings?.userAgentString = "$mUserAgent App/AppName"
        syncCookie()
        webSettings?.useWideViewPort = true
        webSettings?.loadWithOverviewMode = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webSettings?.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        } else {
            webSettings?.cacheMode = WebSettings.LOAD_DEFAULT
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webSettings?.displayZoomControls = false
        }
        webSettings?.loadsImagesAutomatically = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings?.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW; }

        webView.scrollBarStyle = WebView.SCROLLBARS_INSIDE_OVERLAY
        webView.isHorizontalScrollBarEnabled = false
        webView.isHorizontalFadingEdgeEnabled = false
        webView.isVerticalFadingEdgeEnabled = false
        webView.requestFocus()
**/
        webSettings?.domStorageEnabled = true
        webSettings?.setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
        webSettings?.builtInZoomControls = true //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings?.displayZoomControls = false //隐藏原生的缩放控件
        webSettings?.loadWithOverviewMode = true
        webSettings?.setAppCacheEnabled(true)
        webSettings?.useWideViewPort = true
        webSettings?.loadWithOverviewMode = true
        webSettings?.cacheMode = WebSettings.LOAD_DEFAULT
        webSettings?.javaScriptEnabled = true//允许网页使用js
        webSettings?.javaScriptCanOpenWindowsAutomatically = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                //页面加载结束
                val url = SharedPreferenceUtil.getUserInfo(context).shopUrl
                if (!webView.url.equals(url)) {
                    ll_top.visibility=View.VISIBLE
                }else{
                    ll_top.visibility=View.GONE
                }
                U.closeLoadingDialog()
            }
        }
        if (SharedPreferenceUtil.getUserInfo(context) != null) {
            val url = SharedPreferenceUtil.getUserInfo(context).shopUrl
            webView.loadUrl(url)
        } else {
            U.closeLoadingDialog()
            context?.toast("获取信息失败，请重试")
        }

    }

    private fun syncCookie() {
        CookieSyncManager.createInstance(activity)
        CookieManager.getInstance().setAcceptCookie(
            true
        ); CookieSyncManager.getInstance().sync(); }

    /**
     *  @describe 刷新界面的方法
     *  @return
     *  @author HanXueFeng
     *  @time 2019/1/22  8:52
     */
    public fun reloadPage() {
        if (SharedPreferenceUtil.getUserInfo(context) != null) {
            val url = SharedPreferenceUtil.getUserInfo(context).shopUrl
            if (!webView.url.equals(url)) {
                webView.loadUrl(url)
            }
        } else {
            U.closeLoadingDialog()
            context?.toast("获取信息失败，请重试")
        }
    }

}
package com.wisdom.project.homepage.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.wisdom.project.R
import com.wisdom.project.util.SharedPreferenceUtil
import com.wisdom.project.util.U
import kotlinx.android.synthetic.main.fragment_nine_point_nine.*

import org.jetbrains.anko.toast

/**
 * @ProjectName project： ExtraProject
 * @class package：com.wisdom.project.homepage.fragment
 * @class describe：9.9包邮
 * @author HanXueFeng
 * @time 2019/1/2 9:51
 * @change
 */
class NinePointNineFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_nine_point_nine, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val webSettings = webView?.settings
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
                U.closeLoadingDialog()
            }
        }
        if (SharedPreferenceUtil.getUserInfo(context) != null) {
            val url = SharedPreferenceUtil.getUserInfo(context).discountUrl
            webView.loadUrl(url)
        } else {
            U.closeLoadingDialog()
            context?.toast("获取信息失败，请重试")
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
//            U.showLoadingDialog(context)
        }
    }

    /**
     *  @describe 刷新界面方法
     *  @return
     *  @author HanXueFeng
     *  @time 2019/1/22  8:52
     */
    public fun reloadPage() {
        if (SharedPreferenceUtil.getUserInfo(context) != null) {
            val url = SharedPreferenceUtil.getUserInfo(context).discountUrl
            if (!webView.url.equals(url)) {
                webView.loadUrl(url)
            }
        } else {
            U.closeLoadingDialog()
            context?.toast("获取信息失败，请重试")
        }
    }
}
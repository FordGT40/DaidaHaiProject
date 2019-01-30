package com.wisdom.project.homepage.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.wisdom.project.R
import com.wisdom.project.util.SharedPreferenceUtil
import com.wisdom.project.util.U
import kotlinx.android.synthetic.main.fragment_homepage.*
import kotlinx.android.synthetic.main.head_title_bar.*
import org.jetbrains.anko.toast

/**
 * @ProjectName project： ExtraProject
 * @class package：com.wisdom.project.homepage.fragment
 * @class describe：首页
 * @author HanXueFeng
 * @time 2019/1/2 9:51
 * @change
 */
class HomepageFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_homepage, null, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        head_back_iv.setOnClickListener {
            webView.goBack()
        }
        comm_head_title.text="粉丝福利购"
//        U.showLoadingDialog(context)
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                val url = SharedPreferenceUtil.getUserInfo(context).homeUrl
                if (!webView.url.equals(url)) {
                    ll_top.visibility=View.VISIBLE
                }else{
                    ll_top.visibility=View.GONE
                }
                //页面加载结束
                U.closeLoadingDialog()
            }


        }
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

        if (SharedPreferenceUtil.getUserInfo(context) != null) {
            val url = SharedPreferenceUtil.getUserInfo(context).homeUrl
            webView.loadUrl(url)
        } else {
            U.closeLoadingDialog()
            context?.toast("获取信息失败，请重试")
        }
    }

    /**
     *  @describe 刷新当前页面
     *  @return 
     *  @author HanXueFeng
     *  @time 2019/1/22  8:47
     */
    public fun reloadPage() {
        if (SharedPreferenceUtil.getUserInfo(context) != null) {
            val url = SharedPreferenceUtil.getUserInfo(context).homeUrl
            if (!webView.url.equals(url)) {
                webView.loadUrl(url)
            }
        } else {
            U.closeLoadingDialog()
            context?.toast("获取信息失败，请重试")
        }
    }

}
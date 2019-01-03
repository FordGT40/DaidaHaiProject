package com.wisdom.project.util.http_util;

import android.util.Log;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.wisdom.ConstantUrl;
import com.wisdom.project.util.http_util.callback.JsonCallback;
import okhttp3.MediaType;


/**
 * Created by Administrator on 2017/5/11.
 */

public class HttpUtil {
    public static final String TAG = HttpUtil.class.getSimpleName();

    /**
     * 拼接并返回完整的请求地址
     */
    public static String getAbsolteUrl(String relativeUrl) {
        return ConstantUrl.INSTANCE.getBASE_URL() + relativeUrl;
    }

    /**
     * HttpGet方法访问接口
     */
    public static void httpGet(String url, HttpParams params, JsonCallback callback) {
        Log.i("123", "访问的URL: " + getAbsolteUrl(url));
        OkGo.get(getAbsolteUrl(url))
                .connTimeOut(3000)
                .cacheMode(CacheMode.DEFAULT)
                .params(params)
                .execute(callback);
    }

    /**
     * HttpGet方法访问接口
     */
    public static void httpGet(String url, HttpParams params, StringCallback callback) {
        OkGo.get(getAbsolteUrl(url))
                .cacheMode(CacheMode.DEFAULT)
                .params(params)
                .execute(callback);


    }

    /**
     * HttpGet方法访问接口
     */
    public static void httpPost(String url, HttpParams params, StringCallback callback) {
        OkGo.post(getAbsolteUrl(url))
                .cacheMode(CacheMode.DEFAULT)
                .params(params)
                .execute(callback);
    }

    /**
     * HttpGet方法访问接口
     */
    public static void httpPost(String url, HttpParams params, JsonCallback callback) {
        OkGo.post(getAbsolteUrl(url))
                .cacheMode(CacheMode.DEFAULT)
                .params(params)
                .execute(callback);
    }

    /**
     * Multipart方式提交post表单
     *
     * @param url
     * @param params
     * @param callback
     */
    public static void httpPostWithMultipart(String url, HttpParams params, StringCallback callback) {
        OkGo.post(getAbsolteUrl(url))
                .cacheMode(CacheMode.DEFAULT)
                .params(params)
                .isMultipart(true)
                .execute(callback);
    }

    /**
     * 上传文件的相关方法
     *
     * @param url
     * @param params
     * @param callback
     */
    public static void uploadFiles(String url, HttpParams params, StringCallback callback) {
        OkGo.post(getAbsolteUrl(url))
                .params(params)
                .isMultipart(true)
                .cacheMode(CacheMode.DEFAULT)
                .execute(callback);
    }

    /**
     * 上传文件的相关方法
     *
     * @param url
     * @param params
     * @param callback
     */
    public static void uploadFiles(String url, HttpParams params, JsonCallback callback) {
        OkGo.post(getAbsolteUrl(url))
                .params(params)
                .isMultipart(true)
                .cacheMode(CacheMode.DEFAULT)
                .execute(callback);
    }

    /**
     * raw json方式提交数据
     *
     * @return a
     * @describe a
     * @author HanXueFeng
     * @time 2019/1/2  17:33
     */
    public static void httpPostWithoutBaseString(String url, String params, StringCallback callback) {
        Log.i(TAG, "httpPostWithoutBaseString: " + params);
        OkGo.post(getAbsolteUrl(url))
                .cacheMode(CacheMode.DEFAULT)
                .upString(params)
                .execute(callback);
    }
//     .headers("Content-type", "application/json")
//                .headers("Content-type", "text/json")
//                .headers("Content-type", "text/javascript")
//                .headers("Content-type", "text/plain")
//                .headers("Content-type", "application/xml")
//                .headers("Content-type", "text/html")
}

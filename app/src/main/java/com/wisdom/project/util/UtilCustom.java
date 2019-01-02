package com.wisdom.project.util;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;

/**
 * Created by hanxuefeng on 2017/11/2.
 */

public class UtilCustom {
    /**
     * 上传文件的相关方法
     *
     * @param url
     * @param params
     * @param callback
     */
    public static void uploadFiles(String url, HttpParams params, StringCallback callback) {
        OkGo.post(url)
                .params(params)
                .isMultipart(true)
                .cacheMode(CacheMode.DEFAULT)
                .execute(callback);
    }


}

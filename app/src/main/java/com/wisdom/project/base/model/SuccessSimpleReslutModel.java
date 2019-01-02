package com.wisdom.project.base.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/25.
 * 服务器返回简单字符串结果model，主要用于撤销，删除申请接口的返回
 */

public class SuccessSimpleReslutModel implements Serializable {
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

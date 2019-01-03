package com.wisdom.project.login.model;

import java.io.Serializable;

/**
 * @author HanXueFeng
 * @ProjectName project： DaidaHaiProject
 * @class package：com.wisdom.project.login.model
 * @class describe：
 * @time 2019/1/3 11:21
 * @change
 */
public class LoginBackModel implements Serializable {
    private String token;
    private String shopUrl;
    private String discountUrl;
    private String nickName;
    @Override
    public String toString() {
        return "LoginBackModel{" +
                "token='" + token + '\'' +
                ", shopUrl='" + shopUrl + '\'' +
                ", discountUrl='" + discountUrl + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public String getDiscountUrl() {
        return discountUrl;
    }

    public void setDiscountUrl(String discountUrl) {
        this.discountUrl = discountUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}

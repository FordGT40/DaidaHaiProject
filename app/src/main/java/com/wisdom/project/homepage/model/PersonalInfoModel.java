package com.wisdom.project.homepage.model;

import java.io.Serializable;

/**
 * @author HanXueFeng
 * @ProjectName project： DaidaHaiProject
 * @class package：com.wisdom.project.homepage.model
 * @class describe：个人信息返回
 * @time 2019/1/3 16:45
 * @change
 */
public class PersonalInfoModel implements Serializable {
    private String nikeName;
    private String mobile;
    private String gender;
    private String image;

    @Override
    public String toString() {
        return "PersonalInfoModel{" +
                "nikeName='" + nikeName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", gender='" + gender + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

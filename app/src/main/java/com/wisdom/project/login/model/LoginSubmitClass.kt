package com.wisdom.project.login.model

data class LoginSubmitClass(
    val nikeName:String,
    val mobile:String,
    val code:String,
    val password:String,
    val captcha:String
)
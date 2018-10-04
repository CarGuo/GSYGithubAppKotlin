package com.shuyu.github.kotlin.repository

import android.arch.lifecycle.MutableLiveData
import android.util.Base64
import com.shuyu.github.kotlin.common.config.AppConfig
import com.shuyu.github.kotlin.common.net.ResultObserver
import com.shuyu.github.kotlin.common.net.RetrofitFactory
import com.shuyu.github.kotlin.common.utils.Debuger
import com.shuyu.github.kotlin.common.utils.GSYPreference
import com.shuyu.github.kotlin.model.AccessToken
import com.shuyu.github.kotlin.model.LoginRequestModel
import com.shuyu.github.kotlin.service.LoginService
import retrofit2.Retrofit
import javax.inject.Inject

class LoginRepository @Inject constructor(val retrofit: Retrofit) {


    private var usernameStorage: String by GSYPreference(AppConfig.USER_NAME, "")

    private var passwordStorage: String by GSYPreference(AppConfig.PASSWORD, "")

    private var accessTokenStorage: String by GSYPreference(AppConfig.ACCESS_TOKEN, "")

    fun login(username: String, password: String, token: MutableLiveData<AccessToken>) {

        val type = "$username:$password"

        val base64 = Base64.encodeToString(type.toByteArray(), Base64.NO_WRAP).replace("\\+", "%2B");

        Debuger.printfLog("base64Str login $base64")

        usernameStorage = username
        passwordStorage = password
        accessTokenStorage = "Basic $base64"

        val authorizations = retrofit.create(LoginService::class.java)
                .authorizations(LoginRequestModel.generate())
        RetrofitFactory.executeResult(authorizations, object : ResultObserver<AccessToken>() {
            override fun onSuccess(t: AccessToken?) {
                Debuger.printfLog(t.toString())
                token.value = t
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                accessTokenStorage = ""
                token.value = null
            }
        })

    }
}
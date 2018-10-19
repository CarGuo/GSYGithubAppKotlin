package com.shuyu.github.kotlin.repository

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Base64
import com.shuyu.github.kotlin.common.config.AppConfig
import com.shuyu.github.kotlin.common.net.ResultProgressObserver
import com.shuyu.github.kotlin.common.net.RetrofitFactory
import com.shuyu.github.kotlin.common.utils.Debuger
import com.shuyu.github.kotlin.common.utils.GSYPreference
import com.shuyu.github.kotlin.model.AccessToken
import com.shuyu.github.kotlin.model.LoginRequestModel
import com.shuyu.github.kotlin.service.LoginService
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * 登录数据仓库对象
 */
class LoginRepository @Inject constructor(val retrofit: Retrofit) {

    private var usernameStorage: String by GSYPreference(AppConfig.USER_NAME, "")

    private var passwordStorage: String by GSYPreference(AppConfig.PASSWORD, "")

    private var accessTokenStorage: String by GSYPreference(AppConfig.ACCESS_TOKEN, "")

    private var userBasicCodeStorage: String by GSYPreference(AppConfig.USER_BASIC_CODE, "")

    fun login(context: Context, username: String, password: String, token: MutableLiveData<Boolean>) {

        val type = "$username:$password"

        val base64 = Base64.encodeToString(type.toByteArray(), Base64.NO_WRAP).replace("\\+", "%2B")

        Debuger.printfLog("base64Str login $base64")

        usernameStorage = username

        userBasicCodeStorage = base64

        val authorizations = retrofit.create(LoginService::class.java)
                .authorizations(LoginRequestModel.generate())

        RetrofitFactory.executeResult(authorizations, object : ResultProgressObserver<AccessToken>(context) {
            override fun onSuccess(result: AccessToken?) {
                Debuger.printfLog(result.toString())
                result?.apply {
                    accessTokenStorage = this.token!!
                }
                passwordStorage = password
                token.value = true
            }

            override fun onCodeError(code: Int, message: String) {
                accessTokenStorage = ""
                userBasicCodeStorage = ""
                token.value = false
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                accessTokenStorage = ""
                userBasicCodeStorage = ""
                token.value = false
            }

        })

    }
}
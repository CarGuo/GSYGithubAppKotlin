package com.shuyu.github.kotlin.repository

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Base64
import com.shuyu.github.kotlin.common.config.AppConfig
import com.shuyu.github.kotlin.common.net.*
import com.shuyu.github.kotlin.common.utils.Debuger
import com.shuyu.github.kotlin.common.utils.GSYPreference
import com.shuyu.github.kotlin.model.AccessToken
import com.shuyu.github.kotlin.model.LoginRequestModel
import com.shuyu.github.kotlin.model.User
import com.shuyu.github.kotlin.service.LoginService
import com.shuyu.github.kotlin.service.UserService
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * 登录数据仓库对象
 */
class LoginRepository @Inject constructor(private val retrofit: Retrofit) {

    private var usernameStorage: String by GSYPreference(AppConfig.USER_NAME, "")

    private var passwordStorage: String by GSYPreference(AppConfig.PASSWORD, "")

    private var accessTokenStorage: String by GSYPreference(AppConfig.ACCESS_TOKEN, "")

    private var userBasicCodeStorage: String by GSYPreference(AppConfig.USER_BASIC_CODE, "")

    private var userInfoStorage: String by GSYPreference(AppConfig.USER_INFO, "")

    fun login(context: Context, username: String, password: String, token: MutableLiveData<Boolean>) {

        clearTokenStorage()

        val type = "$username:$password"

        val base64 = Base64.encodeToString(type.toByteArray(), Base64.NO_WRAP).replace("\\+", "%2B")

        Debuger.printfLog("base64Str login $base64")

        usernameStorage = username

        userBasicCodeStorage = base64


        val loginService = retrofit.create(LoginService::class.java)
        val userService = retrofit.create(UserService::class.java)

        val authorizations = loginService
                .authorizations(LoginRequestModel.generate())
                .flatMap {
                    FlatMapResponse2Result(it)
                }
                .doOnNext {
                    accessTokenStorage = it.token!!
                    passwordStorage = password
                    Debuger.printfLog("token $accessTokenStorage")
                }
                .flatMap {
                    userService.getPersonInfo(true)
                }
                .flatMap {
                    FlatMapResponse2Result(it)
                }
                .doOnNext {
                    userInfoStorage = GsonUtils.toJsonString(it)
                    Debuger.printfLog("userInfo $userInfoStorage")
                }.flatMap {
                    FlatMapResult2Response(it)
                }

        RetrofitFactory.executeResult(authorizations, object : ResultProgressObserver<User>(context) {
            override fun onSuccess(result: User?) {
                token.value = true
            }

            override fun onCodeError(code: Int, message: String) {
                clearTokenStorage()
                token.value = false
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                clearTokenStorage()
                token.value = false
            }

        })

    }


    fun clearTokenStorage() {
        accessTokenStorage = ""
        userBasicCodeStorage = ""
    }
}

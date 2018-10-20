package com.shuyu.github.kotlin.repository

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Base64
import com.shuyu.github.kotlin.common.config.AppConfig
import com.shuyu.github.kotlin.common.net.ResultObserver
import com.shuyu.github.kotlin.common.net.ResultProgressObserver
import com.shuyu.github.kotlin.common.net.RetrofitFactory
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
class LoginRepository @Inject constructor(val retrofit: Retrofit) {

    private var usernameStorage: String by GSYPreference(AppConfig.USER_NAME, "")

    private var passwordStorage: String by GSYPreference(AppConfig.PASSWORD, "")

    private var accessTokenStorage: String by GSYPreference(AppConfig.ACCESS_TOKEN, "")

    private var userBasicCodeStorage: String by GSYPreference(AppConfig.USER_BASIC_CODE, "")

    fun login(context: Context, username: String, password: String, token: MutableLiveData<Boolean>) {

        clearTokenStorage()

        val type = "$username:$password"

        val base64 = Base64.encodeToString(type.toByteArray(), Base64.NO_WRAP).replace("\\+", "%2B")

        Debuger.printfLog("base64Str login $base64")

        usernameStorage = username

        userBasicCodeStorage = base64


        val loginService = retrofit.create(LoginService::class.java)
        val userService = retrofit.create(UserService::class.java)

        loginService.authorizations(LoginRequestModel.generate())
                .flatMap { response ->
                    if (response.isSuccessful) {
                        ObservableSource<AccessToken?> {
                            it.onNext(response.body())
                        }
                    } else {
                        ObservableSource {
                            it.onError(Throwable(response.errorBody().toString()))
                        }
                    }
                }.doOnNext {
                    if (it != null) {
                        Debuger.printfLog(it.toString())
                        accessTokenStorage = it.token!!
                        passwordStorage = password
                    }
                }.flatMap {
                    userService.getPersonInfo(true)
                }.subscribeOn(Schedulers.io()).doOnNext { response ->
                    if (response.isSuccessful) {
                        ObservableSource<User?> {
                            it.onNext(response.body())
                        }
                    } else {
                        ObservableSource {
                            it.onError(Throwable(response.errorBody().toString()))
                        }
                    }
                }.observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ResultProgressObserver<User>(context) {
                    override fun onSuccess(result: User?) {
                        Debuger.printfLog("********************")
                        Debuger.printfLog(result?.login!!)
                        Debuger.printfLog("********************")
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
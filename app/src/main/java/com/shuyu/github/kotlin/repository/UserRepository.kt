package com.shuyu.github.kotlin.repository

import android.arch.lifecycle.MutableLiveData
import com.shuyu.github.kotlin.common.config.AppConfig
import com.shuyu.github.kotlin.common.net.*
import com.shuyu.github.kotlin.common.utils.Debuger
import com.shuyu.github.kotlin.common.utils.GSYPreference
import com.shuyu.github.kotlin.model.AppGlobalModel
import com.shuyu.github.kotlin.model.bean.User
import com.shuyu.github.kotlin.model.conversion.EventConversion
import com.shuyu.github.kotlin.service.UserService
import io.reactivex.Observable
import io.reactivex.functions.Function
import retrofit2.Retrofit
import javax.inject.Inject


class UserRepository @Inject constructor(private val retrofit: Retrofit, private val appGlobalModel: AppGlobalModel) {

    private var userInfoStorage: String by GSYPreference(AppConfig.USER_INFO, "")

    fun getPersonInfoObservable(): Observable<User> {
        return retrofit.create(UserService::class.java).getPersonInfo(true)
                .flatMap {
                    FlatMapResponse2Result(it)
                }
                .doOnNext {
                    ///保存用户信息
                    Debuger.printfLog("userInfo $userInfoStorage")
                    userInfoStorage = GsonUtils.toJsonString(it)
                }.onErrorResumeNext(Function<Throwable, Observable<User>> { t ->
                    ///拦截错误
                    //userInfoStorage = ""
                    Debuger.printfLog("userInfo onErrorResumeNext ")
                    Observable.error(t)
                })
    }

    fun getPersonInfo(liveUser: MutableLiveData<User>? = null) {
        val userInfoService = getPersonInfoObservable().flatMap { FlatMapResult2Response(it) }
        RetrofitFactory.executeResult(userInfoService, object : ResultObserver<User>() {
            override fun onSuccess(result: User?) {
                liveUser?.value = result
            }

            override fun onCodeError(code: Int, message: String) {
                liveUser?.value = null
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                liveUser?.value = null
            }

        })
    }

    fun getReceivedEvent(resultCallBack: ResultCallBack<ArrayList<Any>>, page: Int = 0) {
        val login = appGlobalModel.userObservable.get()?.login
        val username = login ?: ""
        if (username.isEmpty()) {
            return
        }
        val receivedEvent = retrofit.create(UserService::class.java)
                .getNewsEvent(true, username, page)
                .flatMap {
                    FlatMapResponse2Result(it)
                }
                .map {
                    val eventUIList = ArrayList<Any>()
                    for (event in it) {
                        eventUIList.add(EventConversion.EventToEventUIModel(event))
                    }
                    eventUIList
                }.flatMap {
                    FlatMapResult2Response(it)
                }

        RetrofitFactory.executeResult(receivedEvent, object : ResultObserver<ArrayList<Any>>() {
            override fun onSuccess(result: ArrayList<Any>?) {
                resultCallBack.onSuccess(result)
            }

            override fun onCodeError(code: Int, message: String) {
                resultCallBack.onFailure()
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack.onFailure()
            }

        })

    }

}
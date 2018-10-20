package com.shuyu.github.kotlin.repository

import android.arch.lifecycle.MutableLiveData
import com.shuyu.github.kotlin.common.config.AppConfig
import com.shuyu.github.kotlin.common.net.*
import com.shuyu.github.kotlin.common.utils.Debuger
import com.shuyu.github.kotlin.common.utils.GSYPreference
import com.shuyu.github.kotlin.model.AppGlobalModel
import com.shuyu.github.kotlin.model.Event
import com.shuyu.github.kotlin.model.User
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

    fun getReceivedEvent(dataList: MutableLiveData<ArrayList<Any>>) {
        appGlobalModel.userObservable.get()?.apply {
            val userName = this.login!!
            val receivedEvent = retrofit.create(UserService::class.java).getNewsEvent(true, userName, 0)
            RetrofitFactory.executeResult(receivedEvent, object : ResultObserver<ArrayList<Event>>() {
                override fun onSuccess(result: ArrayList<Event>?) {
                    result?.forEach {
                        dataList.value?.add(Event())
                    }
                }

                override fun onCodeError(code: Int, message: String) {

                }

                override fun onFailure(e: Throwable, isNetWorkError: Boolean) {

                }

            })
        }
    }

}
package com.shuyu.github.kotlin.repository

import android.arch.lifecycle.MutableLiveData
import com.shuyu.github.kotlin.common.config.AppConfig
import com.shuyu.github.kotlin.common.net.GsonUtils
import com.shuyu.github.kotlin.common.net.ResultObserver
import com.shuyu.github.kotlin.common.net.RetrofitFactory
import com.shuyu.github.kotlin.common.utils.Debuger
import com.shuyu.github.kotlin.common.utils.GSYPreference
import com.shuyu.github.kotlin.model.AppGlobalModel
import com.shuyu.github.kotlin.model.Event
import com.shuyu.github.kotlin.model.User
import com.shuyu.github.kotlin.service.UserService
import retrofit2.Retrofit
import javax.inject.Inject


class UserRepository @Inject constructor(private val retrofit: Retrofit, private val appGlobalModel: AppGlobalModel) {

    private var userInfoStorage: String by GSYPreference(AppConfig.USER_INFO, "")

    fun getPersonInfo(liveUser: MutableLiveData<User>? = null) {
        val personInfo = retrofit.create(UserService::class.java).getPersonInfo(true)
        RetrofitFactory.executeResult(personInfo, object : ResultObserver<User>() {
            override fun onSuccess(result: User?) {
                appGlobalModel.userObservable.set(result)
                userInfoStorage = GsonUtils.toJsonString(result)
                liveUser?.value = result
            }

            override fun onCodeError(code: Int, message: String) {
                Debuger.printfLog(message)
                liveUser?.value = null
                userInfoStorage = ""
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                Debuger.printfLog(e.printStackTrace().toString())
                liveUser?.value = null
                userInfoStorage = ""
            }

        })
    }

    fun getReceivedEvent(dataList : MutableLiveData<ArrayList<Any>>) {
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
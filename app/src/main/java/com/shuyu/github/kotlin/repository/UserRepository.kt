package com.shuyu.github.kotlin.repository

import android.arch.lifecycle.MutableLiveData
import com.shuyu.github.kotlin.common.net.ResultObserver
import com.shuyu.github.kotlin.common.net.RetrofitFactory
import com.shuyu.github.kotlin.common.utils.Debuger
import com.shuyu.github.kotlin.model.User
import com.shuyu.github.kotlin.service.UserService
import retrofit2.Retrofit
import javax.inject.Inject


class UserRepository @Inject constructor(private val retrofit: Retrofit) {


    fun getPersonInfo(liveUser: MutableLiveData<User>? = null) {
        val personInfo = retrofit.create(UserService::class.java).getPersonInfo(true)
        RetrofitFactory.executeResult(personInfo, object : ResultObserver<User>() {
            override fun onSuccess(user: User?) {
                liveUser?.value = user
            }

            override fun onCodeError(code: Int, message: String) {
                liveUser?.value = null
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                liveUser?.value = null
            }

        })
    }

}
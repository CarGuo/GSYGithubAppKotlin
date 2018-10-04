package com.shuyu.github.kotlin.module.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.model.AccessToken
import com.shuyu.github.kotlin.repository.LoginRepository
import javax.inject.Inject

/**
 * Created by guoshuyu
 * Date: 2018-09-29
 */
class LoginViewModel @Inject constructor(val loginRepository: LoginRepository) : ViewModel() {

    val token = MutableLiveData<AccessToken>()

    fun login(username: String, password: String) {
        loginRepository.login(username, password, token)
    }
}
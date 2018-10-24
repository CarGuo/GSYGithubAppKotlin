package com.shuyu.github.kotlin.module.person

import android.app.Application
import com.shuyu.github.kotlin.common.net.ResultCallBack
import com.shuyu.github.kotlin.model.bean.User
import com.shuyu.github.kotlin.model.conversion.UserConversion
import com.shuyu.github.kotlin.model.ui.UserUIModel
import com.shuyu.github.kotlin.module.base.BaseUserInfoViewModel
import com.shuyu.github.kotlin.repository.UserRepository
import javax.inject.Inject

class PersonViewModel @Inject constructor(private val userRepository: UserRepository, private val application: Application) : BaseUserInfoViewModel(userRepository, application) {

    val userObservable = UserUIModel()

    override fun loadDataByRefresh() {
        userRepository.getPersonInfo(object : ResultCallBack<User> {
            override fun onSuccess(result: User?) {
                result?.apply {
                    UserConversion.cloneDataFromUser(application, this, userObservable)
                }
            }

            override fun onFailure() {

            }
        }, this, login)
    }

    override fun getUserModel() = userObservable
}
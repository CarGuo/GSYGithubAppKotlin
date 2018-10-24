package com.shuyu.github.kotlin.module.my

import android.app.Application
import com.shuyu.github.kotlin.model.AppGlobalModel
import com.shuyu.github.kotlin.module.base.BaseUserInfoViewModel
import com.shuyu.github.kotlin.repository.UserRepository
import javax.inject.Inject

class MyViewModel @Inject constructor(userRepository: UserRepository, private val globalAppModel: AppGlobalModel,  application: Application) : BaseUserInfoViewModel(userRepository, application) {

    override fun getUserModel() = globalAppModel.userObservable
}
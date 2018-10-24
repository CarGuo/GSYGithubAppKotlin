package com.shuyu.github.kotlin.module.my

import com.shuyu.github.kotlin.model.AppGlobalModel
import com.shuyu.github.kotlin.module.base.BaseUserInfoViewModel
import com.shuyu.github.kotlin.repository.UserRepository
import javax.inject.Inject

class MyViewModel @Inject constructor(userRepository: UserRepository, private val globalAppModel: AppGlobalModel) : BaseUserInfoViewModel(userRepository) {

    override fun getUserModel() = globalAppModel.userObservable
}
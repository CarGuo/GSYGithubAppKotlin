package com.shuyu.github.kotlin.module.person

import com.shuyu.github.kotlin.model.AppGlobalModel
import com.shuyu.github.kotlin.module.base.BaseUserInfoViewModel
import com.shuyu.github.kotlin.repository.UserRepository
import javax.inject.Inject

class PersonViewModel @Inject constructor(userRepository: UserRepository, private val globalAppModel: AppGlobalModel) : BaseUserInfoViewModel(userRepository) {

    override fun getUserModel() = globalAppModel.userObservable
}
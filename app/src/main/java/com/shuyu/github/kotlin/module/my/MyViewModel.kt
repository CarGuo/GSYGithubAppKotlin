package com.shuyu.github.kotlin.module.my

import com.shuyu.github.kotlin.model.AppGlobalModel
import com.shuyu.github.kotlin.module.base.BaseViewModel
import com.shuyu.github.kotlin.repository.UserRepository
import javax.inject.Inject

class MyViewModel @Inject constructor(private val userRepository: UserRepository, private val globalAppModel: AppGlobalModel) : BaseViewModel() {


    override fun loadData() {
    }
}
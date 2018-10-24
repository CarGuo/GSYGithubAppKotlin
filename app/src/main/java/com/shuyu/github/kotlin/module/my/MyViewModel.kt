package com.shuyu.github.kotlin.module.my

import com.shuyu.github.kotlin.common.net.ResultCallBack
import com.shuyu.github.kotlin.model.AppGlobalModel
import com.shuyu.github.kotlin.module.base.BaseViewModel
import com.shuyu.github.kotlin.repository.UserRepository
import javax.inject.Inject

class MyViewModel @Inject constructor(private val userRepository: UserRepository, private val globalAppModel: AppGlobalModel) : BaseViewModel(), ResultCallBack<ArrayList<Any>> {

    var login: String? = null

    override fun loadDataByRefresh() {
        userRepository.getPersonInfo(null, this, login)
    }

    override fun loadDataByLoadMore() {
        userRepository.getUserEvent(getUserModel().login, this, page)
    }

    override fun onSuccess(result: ArrayList<Any>?) {
        commitResult(result)
        completeLoadData()
    }

    override fun onFailure() {
        completeLoadData()
    }

    open fun getUserModel() = globalAppModel.userObservable
}
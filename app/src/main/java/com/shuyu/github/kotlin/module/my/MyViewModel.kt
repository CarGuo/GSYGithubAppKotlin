package com.shuyu.github.kotlin.module.my

import com.shuyu.github.kotlin.common.net.ResultCallBack
import com.shuyu.github.kotlin.model.AppGlobalModel
import com.shuyu.github.kotlin.module.base.BaseViewModel
import com.shuyu.github.kotlin.repository.UserRepository
import javax.inject.Inject

class MyViewModel @Inject constructor(private val userRepository: UserRepository, private val globalAppModel: AppGlobalModel) : BaseViewModel() {


    override fun loadData() {
        clearWhenRefresh()
        val login = globalAppModel.userObservable.get()?.login
        userRepository.getPersonInfo(null)
        userRepository.getUserEvent(login, object: ResultCallBack<ArrayList<Any>>{
            override fun onSuccess(result: ArrayList<Any>?) {
                commitResult(result)
                completeLoadData()
            }

            override fun onFailure() {
                completeLoadData()
            }
        }, page)
    }
}
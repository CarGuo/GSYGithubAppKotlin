package com.shuyu.github.kotlin.module.dynamic

import com.shuyu.github.kotlin.common.net.ResultCallBack
import com.shuyu.github.kotlin.module.base.BaseViewModel
import com.shuyu.github.kotlin.repository.UserRepository
import javax.inject.Inject

/**
 * Created by guoshuyu
 * Date: 2018-10-19
 */

class DynamicViewModel @Inject constructor(private val userRepository: UserRepository) : BaseViewModel() {

    override fun refresh() {
        if (isLoading()) {
            return
        }
        super.refresh()
    }

    override fun loadData() {
        clearWhenRefresh()
        userRepository.getReceivedEvent(object : ResultCallBack<ArrayList<Any>> {
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
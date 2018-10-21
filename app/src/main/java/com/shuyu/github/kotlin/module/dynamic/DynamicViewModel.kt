package com.shuyu.github.kotlin.module.dynamic

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shuyu.github.kotlin.common.net.ResultCallBack
import com.shuyu.github.kotlin.model.AppGlobalModel
import com.shuyu.github.kotlin.module.base.BaseViewModel
import com.shuyu.github.kotlin.module.base.LoadState
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
        if (page <= 1) {
            dataList.value?.clear()
        }
        userRepository.getReceivedEvent(object : ResultCallBack<ArrayList<Any>> {
            override fun onSuccess(result: ArrayList<Any>?) {
                result?.apply {
                    val value = dataList.value
                    value?.addAll(this.toArray())
                    dataList.value = value
                }
                completeLoadData()
            }

            override fun onFailure() {
                completeLoadData()
            }
        }, page)
    }

}
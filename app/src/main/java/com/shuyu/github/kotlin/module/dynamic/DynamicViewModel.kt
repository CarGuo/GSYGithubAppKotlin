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

class DynamicViewModel @Inject constructor(private val userRepository: UserRepository, private val globalModel: AppGlobalModel) : BaseViewModel() {

    val eventDataList = MutableLiveData<ArrayList<Any>>()

    init {
        eventDataList.value = arrayListOf()
    }

    override fun refresh() {
        if (isLoading()){
            return
        }
        super.refresh()
    }

    override fun loadData() {
        if (page <= 1) {
            eventDataList.value?.clear()
        }
        userRepository.getReceivedEvent(object : ResultCallBack<ArrayList<Any>> {
            override fun onSuccess(result: ArrayList<Any>?) {
                result?.apply {
                    val dataList = eventDataList.value
                    dataList?.addAll(result.toArray())
                    eventDataList.value = dataList
                }
                completeLoadData()
            }

            override fun onFailure() {
                completeLoadData()
            }
        }, page)
    }

}
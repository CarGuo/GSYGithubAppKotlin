package com.shuyu.github.kotlin.module.dynamic

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shuyu.github.kotlin.model.AppGlobalModel
import com.shuyu.github.kotlin.repository.UserRepository
import javax.inject.Inject

/**
 * Created by guoshuyu
 * Date: 2018-10-19
 */

class DynamicViewModel @Inject constructor(private val userRepository: UserRepository, private val globalModel: AppGlobalModel) : ViewModel() {

    val eventDataList = MutableLiveData<ArrayList<Any>>()

    val loading = MutableLiveData<Boolean>()

    private var page = 0

    init {
        loadData()
    }


    fun refresh() {
        page = 0
        eventDataList.value?.clear()
        loadData()
    }

    fun loadMore() {
        page++
        loadData()
    }


    private fun loadData() {
        userRepository.getReceivedEvent(eventDataList, loading, page)
    }

}
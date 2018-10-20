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

    val eventDataList =  MutableLiveData<ArrayList<Any>>()

    val loading = MutableLiveData<Boolean>()

    init {
        userRepository.getReceivedEvent(eventDataList, loading)
    }

}
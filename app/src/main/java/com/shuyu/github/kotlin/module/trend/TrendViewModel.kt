package com.shuyu.github.kotlin.module.trend

import android.arch.lifecycle.MutableLiveData
import com.shuyu.github.kotlin.common.net.ResultCallBack
import com.shuyu.github.kotlin.model.AppGlobalModel
import com.shuyu.github.kotlin.module.base.BaseViewModel
import com.shuyu.github.kotlin.repository.ReposRepository
import com.shuyu.github.kotlin.repository.UserRepository
import javax.inject.Inject


class TrendViewModel @Inject constructor(private val repository: ReposRepository) : BaseViewModel() {

    val dataList = MutableLiveData<ArrayList<Any>>()

    init {
        dataList.value = arrayListOf()
    }


    override fun refresh() {
        super.refresh()
    }

    override fun loadMore() {
        super.loadMore()
    }

    override fun loadData() {
        if (page <= 1) {
            dataList.value?.clear()
        }
        repository.getTrend(object : ResultCallBack<ArrayList<Any>> {
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
        })
    }

}
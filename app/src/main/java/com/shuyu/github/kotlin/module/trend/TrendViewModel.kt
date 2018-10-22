package com.shuyu.github.kotlin.module.trend

import android.app.Application
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.net.ResultCallBack
import com.shuyu.github.kotlin.module.base.BaseViewModel
import com.shuyu.github.kotlin.repository.ReposRepository
import javax.inject.Inject


class TrendViewModel @Inject constructor(private val repository: ReposRepository, private val application: Application) : BaseViewModel() {


    val sortData: List<List<String>> = listOf(
            application.resources.getStringArray(R.array.trend_language).toList(),
            application.resources.getStringArray(R.array.trend_since).toList())

    val sortValue: List<List<String>> = listOf(
            application.resources.getStringArray(R.array.trend_language_data).toList(),
            application.resources.getStringArray(R.array.trend_since_data).toList())

    var sortType = arrayListOf(sortValue[0][0], sortValue[1][0])

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
        }, sortType[0], sortType[1])
    }

}
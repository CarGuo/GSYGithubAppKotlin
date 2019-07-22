package com.shuyu.github.kotlin.module.base

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shuyu.github.kotlin.common.net.ResultCallBack
import org.jetbrains.anko.runOnUiThread

/**
 * 基类列表VM
 */
abstract class BaseViewModel(private val application: Application) : ViewModel(), ResultCallBack<ArrayList<Any>> {

    val dataList = MutableLiveData<ArrayList<Any>>()

    val loading = MutableLiveData<LoadState>()

    val needMore = MutableLiveData<Boolean>()

    var lastPage: Int = -1

    var page = 1

    init {
        needMore.value = true
        loading.value = LoadState.NONE
        dataList.value = arrayListOf()
    }

    open fun refresh() {
        if (isLoading()) {
            return
        }
        page = 1
        loading.value = LoadState.Refresh
        loadDataByRefresh()
    }

    open fun loadMore() {
        if (isLoading()) {
            return
        }
        page++
        loading.value = LoadState.LoadMore
        loadDataByLoadMore()
    }


    open fun completeLoadData() {
        when (loading.value) {
            LoadState.Refresh -> {
                loading.value = LoadState.RefreshDone
            }
            LoadState.LoadMore -> {
                loading.value = LoadState.LoadMoreDone
            }
            LoadState.NONE -> {
                loading.value = LoadState.NONE
            }
        }
    }

    open fun isLoading(): Boolean =
            loading.value == LoadState.Refresh && loading.value == LoadState.LoadMore


    open fun clearWhenRefresh() {
        if (page <= 1) {
            dataList.value = arrayListOf()
            needMore.value = true
        }
    }

    open fun commitResult(result: ArrayList<Any>?) {
        result?.apply {
            dataList.value = result
        }
    }

    open fun isFirstData(): Boolean = page == 1


    override fun onSuccess(result: ArrayList<Any>?) {
        commitResult(result)
        completeLoadData()
    }

    override fun onCacheSuccess(result: ArrayList<Any>?) {
        application.runOnUiThread {
            result?.apply {
                if (this.isNotEmpty()) {
                    commitResult(result)
                }
            }
        }
    }

    override fun onFailure() {
        completeLoadData()
    }

    override fun onPage(first: Int, current: Int, last: Int) {
        if (last != -1) {
            lastPage = last
        }
        if (lastPage != -1) {
            application.runOnUiThread {
                needMore.value = (page < lastPage)
            }
        }
    }

    abstract fun loadDataByRefresh()

    abstract fun loadDataByLoadMore()

}
package com.shuyu.github.kotlin.module.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel


abstract class BaseViewModel : ViewModel() {

    val dataList = MutableLiveData<ArrayList<Any>>()

    val loading = MutableLiveData<LoadState>()

    var page = 1

    init {
        loading.value = LoadState.NONE
        dataList.value = arrayListOf()
    }

    open fun refresh() {
        if (isLoading()) {
            return
        }
        page = 1
        loading.value = LoadState.Refresh
        loadData()
    }

    open fun loadMore() {
        if (isLoading()) {
            return
        }
        page++
        loading.value = LoadState.LoadMore
        loadData()
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
            dataList.value?.clear()
        }
    }

    open fun commitResult(result: ArrayList<Any>?) {
        result?.apply {
            //val value = dataList.value
            //value?.addAll(this.toArray())
            dataList.value = result
        }
    }

    abstract fun loadData()

}
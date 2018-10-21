package com.shuyu.github.kotlin.module.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel


abstract class BaseViewModel : ViewModel() {

    val loading = MutableLiveData<LoadState>()

    init {
        loading.value = LoadState.NONE
    }

    var page = 0

    open fun refresh() {
        page = 0
        loadData(LoadState.Refresh)
    }

    open fun loadMore() {
        page++
        loadData(LoadState.LoadMoreDone)
    }


    fun completeLoadData(loadState: LoadState) {
        when (loadState) {
            LoadState.Refresh -> {
                loading.value = LoadState.RefreshDone
            }
            LoadState.LoadMore -> {
                loading.value = LoadState.LoadMoreDone
            }
            else -> {
                loading.value = LoadState.NONE
            }
        }
    }


    abstract fun loadData(loadState: LoadState)

}
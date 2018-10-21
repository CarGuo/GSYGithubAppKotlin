package com.shuyu.github.kotlin.module.trend

import com.shuyu.github.kotlin.model.AppGlobalModel
import com.shuyu.github.kotlin.module.base.BaseViewModel
import com.shuyu.github.kotlin.repository.ReposRepository
import com.shuyu.github.kotlin.repository.UserRepository
import javax.inject.Inject


class TrendViewModel @Inject constructor(private val repository: ReposRepository) : BaseViewModel() {


    init {
        repository.getTrend()
    }

    override fun refresh() {
        super.refresh()
    }

    override fun loadMore() {
        super.loadMore()
    }


    override fun loadData() {


    }
}
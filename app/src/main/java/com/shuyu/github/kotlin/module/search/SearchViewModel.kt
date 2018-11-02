package com.shuyu.github.kotlin.module.search

import android.app.Application
import com.shuyu.github.kotlin.module.base.BaseViewModel
import com.shuyu.github.kotlin.repository.ReposRepository
import javax.inject.Inject

/**
 * Created by guoshuyu
 * Date: 2018-11-02
 */
class SearchViewModel @Inject constructor(private val reposRepository: ReposRepository, private val application: Application) : BaseViewModel(application) {

    override fun loadDataByRefresh() {

    }

    override fun loadDataByLoadMore() {

    }
}
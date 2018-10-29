package com.shuyu.github.kotlin.module.repos.issue

import android.app.Application
import com.shuyu.github.kotlin.module.base.BaseViewModel
import com.shuyu.github.kotlin.repository.ReposRepository
import javax.inject.Inject

/**
 * Created by guoshuyu
 * Date: 2018-10-29
 */

class ReposIssueListViewModel @Inject constructor(private val reposRepository: ReposRepository, application: Application) : BaseViewModel(application) {

    var userName: String = ""

    var reposName: String = ""

    override fun loadDataByRefresh() {
        loadData()
    }

    override fun loadDataByLoadMore() {
        loadData()
    }

    private fun loadData() {
        reposRepository.getReposIssueList(userName, reposName, page, this)
    }
}
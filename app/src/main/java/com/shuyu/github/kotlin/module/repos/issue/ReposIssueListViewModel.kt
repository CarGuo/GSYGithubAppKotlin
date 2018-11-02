package com.shuyu.github.kotlin.module.repos.issue

import android.app.Application
import android.content.Context
import com.shuyu.github.kotlin.common.net.ResultCallBack
import com.shuyu.github.kotlin.model.bean.Issue
import com.shuyu.github.kotlin.model.ui.IssueUIModel
import com.shuyu.github.kotlin.module.base.BaseViewModel
import com.shuyu.github.kotlin.repository.IssueRepository
import com.shuyu.github.kotlin.repository.ReposRepository
import javax.inject.Inject

/**
 * Created by guoshuyu
 * Date: 2018-10-29
 */

class ReposIssueListViewModel @Inject constructor(private val reposRepository: ReposRepository, private val issueRepository: IssueRepository, application: Application) : BaseViewModel(application) {

    var userName: String = ""

    var reposName: String = ""

    var status: String = ""

    override fun loadDataByRefresh() {
        loadData()
    }

    override fun loadDataByLoadMore() {
        loadData()
    }

    private fun loadData() {
        reposRepository.getReposIssueList(userName, reposName, status, page, this)
    }

    fun createIssue(context: Context, title: String, body: String, resultCallback: ResultCallBack<IssueUIModel>) {
        val issue = Issue()
        issue.title = title
        issue.body = body
        issueRepository.createIssue(context, userName, reposName,  issue, resultCallback)
    }
}
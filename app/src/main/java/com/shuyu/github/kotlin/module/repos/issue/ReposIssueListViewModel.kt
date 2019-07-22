package com.shuyu.github.kotlin.module.repos.issue

import android.app.Application
import android.content.Context
import android.view.KeyEvent
import android.view.View
import androidx.databinding.ObservableField
import com.shuyu.github.kotlin.common.net.ResultCallBack
import com.shuyu.github.kotlin.model.bean.Issue
import com.shuyu.github.kotlin.model.ui.IssueUIModel
import com.shuyu.github.kotlin.module.base.BaseViewModel
import com.shuyu.github.kotlin.module.base.LoadState
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

    val query = ObservableField<String>()

    override fun loadDataByRefresh() {
        loadData()
    }

    override fun loadDataByLoadMore() {
        loadData()
    }

    private fun loadData() {
        if (query.get().isNullOrBlank()) {
            reposRepository.getReposIssueList(userName, reposName, status, page, this)
        } else {
            reposRepository.searchReposIssueList(userName, reposName, status, query.get()!!, page, this)
        }
    }

    fun createIssue(context: Context, title: String, body: String, resultCallback: ResultCallBack<IssueUIModel>) {
        val issue = Issue()
        issue.title = title
        issue.body = body
        issueRepository.createIssue(context, userName, reposName, issue, resultCallback)
    }


    fun onSearchKeyListener(v: View, keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            val searchQ = query.get()
            if (searchQ.isNullOrBlank()) {
                return false
            }
            loading.value = LoadState.Refresh
            refresh()
            return true
        }
        return false
    }

    /**
     * 通过DataBinding在XML绑定的点击方法
     */
    fun onSearchClick(v: View) {
        loading.value = LoadState.Refresh
        refresh()
    }
}
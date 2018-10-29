package com.shuyu.github.kotlin.module.issue

import android.app.Application
import com.shuyu.github.kotlin.common.net.ResultCallBack
import com.shuyu.github.kotlin.model.ui.IssueUIModel
import com.shuyu.github.kotlin.module.base.BaseViewModel
import com.shuyu.github.kotlin.repository.IssueRepository
import javax.inject.Inject

class IssueDetailViewModel @Inject constructor(private val issueRepository: IssueRepository, private val application: Application) : BaseViewModel(application) {

    var userName = ""

    var reposName = ""

    var issueNumber = 0

    val issueUIModel = IssueUIModel()

    override fun loadDataByRefresh() {
        issueRepository.getIssueInfo(userName, reposName, issueNumber, object : ResultCallBack<IssueUIModel> {
            override fun onSuccess(result: IssueUIModel?) {
                result?.apply {
                    issueUIModel.cloneFrom(this)
                }
            }

            override fun onFailure() {
            }
        })
    }

    override fun loadDataByLoadMore() {
    }
}
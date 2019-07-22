package com.shuyu.github.kotlin.module.issue

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.shuyu.github.kotlin.common.net.ResultCallBack
import com.shuyu.github.kotlin.model.bean.CommentRequestModel
import com.shuyu.github.kotlin.model.bean.Issue
import com.shuyu.github.kotlin.model.ui.IssueUIModel
import com.shuyu.github.kotlin.module.base.BaseViewModel
import com.shuyu.github.kotlin.repository.IssueRepository
import javax.inject.Inject

class IssueDetailViewModel @Inject constructor(private val issueRepository: IssueRepository, private val application: Application) : BaseViewModel(application) {

    var userName = ""

    var reposName = ""

    var issueNumber = 0

    val issueUIModel = IssueUIModel()

    val liveIssueModel = MutableLiveData<IssueUIModel>()

    override fun loadDataByRefresh() {
        loadInfo()
        loadData()
    }

    override fun loadDataByLoadMore() {
        loadData()
    }

    private fun loadData() {
        issueRepository.getIssueComments(userName, reposName, issueNumber, page, this)
    }

    private fun loadInfo() {
        issueRepository.getIssueInfo(userName, reposName, issueNumber, object : ResultCallBack<IssueUIModel> {
            override fun onCacheSuccess(result: IssueUIModel?) {
                result?.apply {
                    issueUIModel.cloneFrom(this)
                }
            }

            override fun onSuccess(result: IssueUIModel?) {
                result?.apply {
                    issueUIModel.cloneFrom(this)
                    liveIssueModel.value = this
                }
            }

            override fun onFailure() {
            }
        })
    }


    fun commentIssue(context: Context, content: String, resultCallBack: ResultCallBack<IssueUIModel>?) {
        val commentRequestModel = CommentRequestModel()
        commentRequestModel.body = content
        issueRepository.commentIssue(context, userName, reposName, issueNumber, commentRequestModel, object : ResultCallBack<IssueUIModel> {
            override fun onSuccess(result: IssueUIModel?) {
                result?.apply {
                    dataList.value = arrayListOf(result)
                }
                resultCallBack?.onSuccess(result)
            }

            override fun onFailure() {
                resultCallBack?.onFailure()
            }
        })
    }

    fun changeIssueStatus(context: Context, status: String) {
        val issue = Issue()
        issue.state = status
        issueRepository.editIssue(context, userName, reposName, issueNumber, issue, object : ResultCallBack<IssueUIModel> {
            override fun onSuccess(result: IssueUIModel?) {
                result?.apply {
                    issueUIModel.cloneFrom(this)
                    liveIssueModel.value = this
                }
            }
        })

    }

    fun lockIssueStatus(context: Context, lock: Boolean) {
        issueRepository.lockIssue(context, userName, reposName, issueNumber, lock, object : ResultCallBack<Boolean> {
            override fun onSuccess(result: Boolean?) {
                loadInfo()
            }
        })
    }

    fun editIssue(context: Context, title: String, body: String) {
        val issue = Issue()
        issue.title = title
        issue.body = body
        issueRepository.editIssue(context, userName, reposName, issueNumber, issue, object : ResultCallBack<IssueUIModel> {
            override fun onSuccess(result: IssueUIModel?) {
                result?.apply {
                    issueUIModel.cloneFrom(this)
                    liveIssueModel.value = this
                }
            }
        })
    }

    fun editComment(context: Context, commentId: String, issueUIModel: IssueUIModel) {
        val commentRequestModel = CommentRequestModel()
        commentRequestModel.body = issueUIModel.action
        issueRepository.editComment(context, userName, reposName, commentId, commentRequestModel, object : ResultCallBack<IssueUIModel> {
            override fun onSuccess(result: IssueUIModel?) {
                result?.apply {
                    issueUIModel.cloneFrom(this)
                }
            }
        })
    }

    fun deleteComment(context: Context, commentId: String, resultCallBack: ResultCallBack<String>?) {
        issueRepository.deleteComment(context, userName, reposName, commentId, resultCallBack)
    }
}
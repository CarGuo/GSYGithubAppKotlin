package com.shuyu.github.kotlin.common.utils

import android.content.Context
import com.orhanobut.dialogplus.OnItemClickListener
import com.shuyu.github.kotlin.model.ui.EventUIAction
import com.shuyu.github.kotlin.model.ui.EventUIModel
import com.shuyu.github.kotlin.module.issue.IssueDetailActivity
import com.shuyu.github.kotlin.module.person.PersonActivity
import com.shuyu.github.kotlin.module.push.PushDetailActivity
import com.shuyu.github.kotlin.module.repos.ReposDetailActivity

/**
 * 事件相关跳转
 * Created by guoshuyu
 * Date: 2018-10-25
 */

object EventUtils {

    fun evenAction(context: Context?, eventUIModel: EventUIModel?) {
        when (eventUIModel?.actionType) {
            EventUIAction.Person -> {
                PersonActivity.gotoPersonInfo(eventUIModel.owner)
            }
            EventUIAction.Repos -> {
                ReposDetailActivity.gotoReposDetail(eventUIModel.owner, eventUIModel.repositoryName)
            }
            EventUIAction.Issue -> {
                IssueDetailActivity.gotoIssueDetail(eventUIModel.owner, eventUIModel.repositoryName, eventUIModel.IssueNum)
            }
            EventUIAction.Push -> {
                if (eventUIModel.pushSha.size == 1) {
                    PushDetailActivity.gotoPushDetail(eventUIModel.owner, eventUIModel.repositoryName, eventUIModel.pushSha[0])
                } else {
                    context?.showOptionSelectDialog(eventUIModel.pushShaDes, OnItemClickListener { dialog, _, _, position ->
                        dialog.dismiss()
                        PushDetailActivity.gotoPushDetail(eventUIModel.owner, eventUIModel.repositoryName, eventUIModel.pushSha[position])
                    })
                }
            }
            EventUIAction.Release -> {
            }
        }
    }
}
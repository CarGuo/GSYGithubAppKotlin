package com.shuyu.github.kotlin.common.utils

import com.shuyu.github.kotlin.model.ui.EventUIAction
import com.shuyu.github.kotlin.model.ui.EventUIModel
import com.shuyu.github.kotlin.module.person.PersonActivity
import com.shuyu.github.kotlin.module.repos.ReposDetailActivity

/**
 * Created by guoshuyu
 * Date: 2018-10-25
 */

object EventUtils {

    fun evenAction(eventUIModel: EventUIModel?) {
        when (eventUIModel?.actionType) {
            EventUIAction.Person -> {
                PersonActivity.gotoPersonInfo(eventUIModel.owner)
            }
            EventUIAction.Repos -> {
                ReposDetailActivity.gotoReposDetail(eventUIModel.owner, eventUIModel.repositoryName)
            }
            EventUIAction.Issue -> {
            }
            EventUIAction.Push -> {
            }
            EventUIAction.Release -> {
            }
        }
    }
}
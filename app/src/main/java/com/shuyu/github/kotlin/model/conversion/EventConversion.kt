package com.shuyu.github.kotlin.model.conversion


import com.shuyu.github.kotlin.common.utils.CommonUtils
import com.shuyu.github.kotlin.model.bean.Event
import com.shuyu.github.kotlin.model.ui.EventUIModel


object EventConversion {


    fun eventToEventUIModel(event: Event): EventUIModel {
        var actionStr: String? = ""
        var des: String? = ""
        when (event.type) {
            "CommitCommentEvent" -> {
                actionStr = "Commit comment at " + event.repo?.name
            }
            "CreateEvent" -> {
                actionStr = if (event.payload?.refType == "repository") {
                    "Created repository " + event.repo?.name
                } else {
                    "Created " + event.payload?.refType + " " + event.payload?.ref + " at " + event.repo?.name
                }
            }
            "DeleteEvent" -> {
                actionStr = "Delete " + event.payload?.refType + " " + event.payload?.ref + " at " + event.repo?.name
            }
            "ForkEvent" -> {
                val oriRepo = event.repo?.name
                val newRepo = event.actor?.login + "/" + event.repo?.name
                actionStr = "Forked $oriRepo to $newRepo"
            }
            "GollumEvent" -> {
                actionStr = event.actor?.login + " a wiki page "
            }

            "InstallationEvent" -> {
                actionStr = event.payload?.action + " an GitHub App "
            }
            "InstallationRepositoriesEvent" -> {
                actionStr = event.payload?.action + " repository from an installation "
            }
            "IssueCommentEvent" -> {
                actionStr = event.payload?.action + " comment on issue " + event.payload?.issue?.number.toString() + " in " + event.repo?.name
                des = event.payload?.comment?.body
            }
            "IssuesEvent" -> {
                actionStr = event.payload?.action + " issue " + event.payload?.issue?.number.toString() + " in " + event.repo?.name
                des = event.payload?.issue?.title
            }

            "MarketplacePurchaseEvent" -> {
                actionStr = event.payload?.action + " marketplace plan "
            }
            "MemberEvent" -> {
                actionStr = event.payload?.action + " member to " + event.repo?.name
            }
            "OrgBlockEvent" -> {
                actionStr = event.payload?.action + " a user "
            }
            "ProjectCardEvent" -> {
                actionStr = event.payload?.action + " a project "
            }
            "ProjectColumnEvent" -> {
                actionStr = event.payload?.action + " a project "
            }

            "ProjectEvent" -> {
                actionStr = event.payload?.action + " a project "
            }
            "PublicEvent" -> {
                actionStr = "Made " + event.repo?.name + " public"
            }
            "PullRequestEvent" -> {
                actionStr = event.payload?.action + " pull request " + event.repo?.name
            }
            "PullRequestReviewEvent" -> {
                actionStr = event.payload?.action + " pull request review at" + event.repo?.name
            }
            "PullRequestReviewCommentEvent" -> {
                actionStr = event.payload?.action + " pull request review comment at" + event.repo?.name

            }
            "PushEvent" -> {
                var ref: String? = event.payload?.ref
                ref = ref?.substring(ref.lastIndexOf("/") + 1)
                actionStr = "Push to " + ref + " at " + event.repo?.name
                des = ""
                var descSpan: String? = ""
                val count = event.payload?.commits?.size!!
                val maxLines = 4
                val max = if (count > maxLines) {
                    maxLines - 1
                } else {
                    count
                }
                for (i in 0 until max) {
                    val commit = event.payload?.commits!![i]
                    if (i != 0) {
                        descSpan += ("\n")
                    }
                    val sha = commit.sha?.substring(0, 7)
                    descSpan += sha
                    descSpan += " "
                    descSpan += commit.message
                }
                if (count > maxLines) {
                    descSpan = "$descSpan\n..."
                }
            }
            "ReleaseEvent" -> {
                actionStr = event.payload?.action + " release " + event.payload?.release?.tagName + " at " + event.repo?.name
            }
            "WatchEvent" -> {
                actionStr = event.payload?.action + " " + event.repo?.name

            }
        }
        val eventUIModel = EventUIModel()
        eventUIModel.username = event.actor?.login ?: ""
        eventUIModel.action = actionStr ?: ""
        eventUIModel.des = des ?: ""
        eventUIModel.image = event.actor?.avatarUrl ?:""
        eventUIModel.time = CommonUtils.getNewsTimeStr(event.createdAt)
        return eventUIModel
    }
}
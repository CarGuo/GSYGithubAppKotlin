package com.shuyu.github.kotlin.model.conversion


import android.content.Context
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.utils.CommonUtils
import com.shuyu.github.kotlin.model.bean.Event
import com.shuyu.github.kotlin.model.bean.Notification
import com.shuyu.github.kotlin.model.bean.RepoCommit
import com.shuyu.github.kotlin.model.ui.CommitUIModel
import com.shuyu.github.kotlin.model.ui.EventUIAction
import com.shuyu.github.kotlin.model.ui.EventUIModel

/**
 * 事件相关实体转换
 */
object EventConversion {


    fun eventToEventUIModel(event: Event): EventUIModel {
        var actionStr: String? = ""
        var des: String? = ""
        val eventUIModel = EventUIModel()
        eventAction(event, eventUIModel)
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
        eventUIModel.username = event.actor?.login ?: ""
        eventUIModel.action = actionStr ?: ""
        eventUIModel.des = des ?: ""
        eventUIModel.image = event.actor?.avatarUrl ?: ""
        eventUIModel.time = CommonUtils.getNewsTimeStr(event.createdAt)
        return eventUIModel
    }

    ///跳转
    private fun eventAction(event: Event, eventUIModel: EventUIModel) {
        if (event.repo == null) {
            eventUIModel.owner = event.actor?.login ?: ""
            eventUIModel.actionType = EventUIAction.Person
            return
        }
        val owner = event.repo?.name?.split("/")?.get(0)
        val repositoryName = event.repo?.name?.split("/")?.get(1)
        val fullName = "$owner/$repositoryName"
        eventUIModel.owner = owner ?: ""
        eventUIModel.repositoryName = repositoryName ?: ""
        when (event.type) {
            "ForkEvent" -> {
                eventUIModel.actionType = EventUIAction.Repos
                eventUIModel.owner = event.actor?.login!!
            }
            "PushEvent" -> {
                when {
                    event.payload?.commits == null -> {
                        eventUIModel.actionType = EventUIAction.Repos
                    }
                    event.payload?.commits?.size == 1 -> {
                        eventUIModel.actionType = EventUIAction.Push
                        eventUIModel.pushSha.clear()
                        eventUIModel.pushSha.add(event.payload?.commits?.get(0)?.sha ?: "")
                    }
                    else -> {
                        eventUIModel.actionType = EventUIAction.Push
                        eventUIModel.pushSha.clear()
                        event.payload?.commits?.apply {
                            forEach {
                                eventUIModel.pushSha.add(it.sha ?: "")
                                eventUIModel.pushShaDes.add(it.message ?: "")
                            }
                        }
                    }
                }
            }
            "ReleaseEvent" -> {
                val url = event.payload?.release?.tarballUrl
                eventUIModel.actionType = EventUIAction.Release
                eventUIModel.releaseUrl = url ?: ""
                /*CommonUtils.launchWebView(context, repositoryName, url);*/
            }
            "IssueCommentEvent", "IssuesEvent" -> {
                eventUIModel.actionType = EventUIAction.Issue
                eventUIModel.IssueNum = event.payload?.issue?.number ?: 0
            }
            else -> {
                eventUIModel.actionType = EventUIAction.Repos
            }
        }
    }


    fun notificationToEventUIModel(context: Context, notification: Notification): EventUIModel {
        val eventUIModel = EventUIModel()
        eventUIModel.time = CommonUtils.getNewsTimeStr(notification.updateAt)
        eventUIModel.username = notification.repository?.fullName ?: ""
        val type = notification.subject?.type ?: ""
        val status = if (notification.unread) {
            context.getString(R.string.unread)
        } else {
            context.getString(R.string.readed)
        }
        eventUIModel.des = notification.reason + " " + "${context.getString(R.string.notifyType)}：$type，${context.getString(R.string.notifyStatus)}：$status"
        eventUIModel.action = notification.subject?.title ?: ""
        eventUIModel.actionType = if (notification.subject?.type == "Issue") {
            EventUIAction.Issue
        } else {
            EventUIAction.Person
        }

        eventUIModel.owner = notification.repository?.owner?.login ?: ""
        eventUIModel.repositoryName = notification.repository?.name ?: ""

        val url = notification.subject?.url
        url?.apply {
            val tmp = url.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val number = tmp[tmp.size - 1]
            eventUIModel.IssueNum = number.toInt()
        }
        eventUIModel.threadId = notification.id ?: ""

        return eventUIModel
    }

    fun commitToCommitUIModel(repoCommit: RepoCommit): CommitUIModel {
        val commitUIModel = CommitUIModel()
        commitUIModel.time = CommonUtils.getNewsTimeStr(repoCommit.commit?.committer?.date)
        commitUIModel.userName = repoCommit.commit?.committer?.name ?: ""
        commitUIModel.sha = repoCommit.sha ?: ""
        commitUIModel.des = repoCommit.commit?.message ?: ""
        return commitUIModel
    }
}
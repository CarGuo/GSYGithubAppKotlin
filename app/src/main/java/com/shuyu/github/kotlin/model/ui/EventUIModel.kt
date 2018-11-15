package com.shuyu.github.kotlin.model.ui

/**
 * 事件相关UI实体
 */
data class EventUIModel(var username: String = "",
                        var image: String = "",
                        var action: String = "",
                        var des: String = "",
                        var time: String = "---",
                        var actionType: EventUIAction = EventUIAction.Person,
                        var owner: String = "",
                        var repositoryName: String = "",
                        var IssueNum: Int = 0,
                        var releaseUrl: String = "",
                        var pushSha: ArrayList<String> = arrayListOf(),
                        var pushShaDes: ArrayList<String> = arrayListOf(),
                        var threadId: String = "")

/**
 * 事件相关UI类型
 */
enum class EventUIAction {
    Person,
    Repos,
    Push,
    Release,
    Issue
}


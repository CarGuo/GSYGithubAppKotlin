package com.shuyu.github.kotlin.model.ui

data class EventUIModel(var username: String = "",
                        var image: String = "",
                        var action: String = "",
                        var des: String = "",
                        var time: String = "---",
                        var actionType: EventUIAction = EventUIAction.Person,
                        var owner: String = "",
                        var repositoryName: String = "",
                        var IssueNum: String = "",
                        var releaseUrl: String = "",
                        var pushSha: String = "")


enum class EventUIAction {
    Person,
    Repos,
    Push,
    Release,
    Issue
}


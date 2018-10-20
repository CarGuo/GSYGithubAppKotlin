package com.shuyu.github.kotlin.model.bean


import com.google.gson.annotations.SerializedName

import java.util.ArrayList


class EventPayload {

    //PushEvent
    @SerializedName("push_id")
    var pushId: String? = null
    var size: Int = 0
    @SerializedName("distinct_size")
    var distinctSize: Int = 0
    var ref: String? = null //PushEvent&CreateEvent
    var head: String? = null
    var before: String? = null
    var commits: ArrayList<PushEventCommit>? = null

    //WatchEvent&PullRequestEvent
    var action: String? = null

    //CreateEvent
    @SerializedName("ref_type")
    var refType: String? = null
    @SerializedName("master_branch")
    var masterBranch: String? = null
    var description: String? = null
    @SerializedName("pusher_type")
    var pusherType: String? = null

    //ReleaseEvent
    var release: Release? = null
    //IssueCommentEvent
    var issue: Issue? = null
    var comment: IssueEvent? = null

}
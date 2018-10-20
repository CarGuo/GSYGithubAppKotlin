package com.shuyu.github.kotlin.model.bean


import com.google.gson.annotations.SerializedName

class CommitGitInfo {

    var message: String? = null
    var url: String? = null
    @SerializedName("comment_count")
    var commentCount: Int = 0
    var author: CommitGitUser? = null
    var committer: CommitGitUser? = null


}

package com.shuyu.github.kotlin.model.bean


import com.google.gson.annotations.SerializedName

import java.util.Date


class CommitComment {

    var id: Int = 0
    var body: String? = null
    var path: String? = null
    var position: Int = 0
    var line: Int = 0
    @SerializedName("commit_id")
    var commitId: String? = null
    @SerializedName("created_at")
    var createdAt: Date? = null
    @SerializedName("updated_at")
    var updatedAt: Date? = null
    @SerializedName("html_url")
    var htmlUrl: String? = null
    var url: String? = null
    var user: User? = null


}

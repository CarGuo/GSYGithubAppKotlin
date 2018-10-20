package com.shuyu.github.kotlin.model.bean


import com.google.gson.annotations.SerializedName

import java.util.Date


class Issue {

    var id: String? = null
    var number: Int = 0
    var title: String? = null
    var state: String? = null
    var locked: Boolean = false
    @SerializedName("comments")
    var commentNum: Int = 0

    @SerializedName("created_at")
    var createdAt: Date? = null
    @SerializedName("updated_at")
    var updatedAt: Date? = null
    @SerializedName("closed_at")
    var closedAt: Date? = null
    var body: String? = null
    @SerializedName("body_html")
    var bodyHtml: String? = null

    var user: User? = null
    @SerializedName("repository_url")
    var repoUrl: String? = null
    @SerializedName("html_url")
    var htmlUrl: String? = null


}

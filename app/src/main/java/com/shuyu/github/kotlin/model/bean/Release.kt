package com.shuyu.github.kotlin.model.bean


import com.google.gson.annotations.SerializedName

import java.util.Date


class Release {

    var id: String? = null
    @SerializedName("tag_name")
    var tagName: String? = null
    @SerializedName("target_commitish")
    var targetCommitish: String? = null
    var name: String? = null
    var body: String? = null
    @SerializedName("body_html")
    var bodyHtml: String? = null
    @SerializedName("tarball_url")
    var tarballUrl: String? = null
    @SerializedName("zipball_url")
    var zipballUrl: String? = null

    var draft: Boolean = false
    @SerializedName("prerelease")
    var preRelease: Boolean = false
    @SerializedName("created_at")
    var createdAt: Date? = null
    @SerializedName("published_at")
    var publishedAt: Date? = null

    var author: User? = null

}

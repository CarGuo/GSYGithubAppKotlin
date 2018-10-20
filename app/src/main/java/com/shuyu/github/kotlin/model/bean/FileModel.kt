package com.shuyu.github.kotlin.model.bean


import com.google.gson.annotations.SerializedName


class FileModel {
    var name: String? = null
    var path: String? = null
    var sha: String? = null
    var size: Int = 0
    var url: String? = null
    @SerializedName("html_url")
    var htmlUrl: String? = null
    @SerializedName("git_url")
    var gitUrl: String? = null
    @SerializedName("download_url")
    var downloadUrl: String? = null
    @SerializedName("type")
    var type: String? = null
}
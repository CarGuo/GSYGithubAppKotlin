package com.shuyu.github.kotlin.model.bean


import com.google.gson.annotations.SerializedName


class CommitFile {

    var sha: String? = null
    @SerializedName("filename")
    var fileName: String? = null
    var status: String? = null
    var additions: Int = 0
    var deletions: Int = 0
    var changes: Int = 0
    @SerializedName("blob_url")
    var blobUrl: String? = null
    @SerializedName("raw_url")
    var rawUrl: String? = null
    @SerializedName("contents_url")
    var contentsUrl: String? = null
    var patch: String? = null

}

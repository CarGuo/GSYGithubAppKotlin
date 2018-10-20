package com.shuyu.github.kotlin.model.bean


import com.google.gson.annotations.SerializedName

import java.util.ArrayList


class CommitsComparison {

    var url: String? = null
    @SerializedName("html_url")
    var htmlUrl: String? = null
    @SerializedName("base_commit")
    var baseCommit: RepoCommit? = null
    @SerializedName("merge_base_commit")
    var mergeBaseCommit: RepoCommit? = null
    var status: String? = null
    @SerializedName("total_commits")
    var totalCommits: Int = 0
    var commits: ArrayList<RepoCommit>? = null
    var files: ArrayList<CommitFile>? = null


}

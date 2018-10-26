package com.shuyu.github.kotlin.model.bean

import com.google.gson.annotations.SerializedName
import java.util.*

class Repository {

    var id: Int = 0
    var name: String? = null
    @SerializedName("full_name")
    var fullName: String? = null
    @SerializedName("")
    var rep: Boolean = false
    @SerializedName("html_url")
    var htmlUrl: String? = null
    var description: String? = null
    var language: String? = null
    var owner: User? = null
    var license: License? = null

    @SerializedName("default_branch")
    var defaultBranch: String? = null

    @SerializedName("created_at")
    var createdAt: Date? = null
    @SerializedName("updated_at")
    var updatedAt: Date? = null
    @SerializedName("pushed_at")
    var pushedAt: Date? = null

    @SerializedName("git_url")
    var gitUrl: String? = null
    @SerializedName("ssh_url")
    var sshUrl: String? = null
    @SerializedName("clone_url")
    var cloneUrl: String? = null
    @SerializedName("svn_url")
    var svnUrl: String? = null

    var size: Int = 0
    @SerializedName("stargazers_count")
    var stargazersCount: Int = 0
    @SerializedName("watchers_count")
    var watchersCount: Int = 0
    @SerializedName("forks_count")
    var forksCount: Int = 0
    @SerializedName("open_issues_count")
    var openIssuesCount: Int = 0
    @SerializedName("subscribers_count")
    var subscribersCount: Int = 0

    var fork: Boolean = false
    var parent: Repository? = null

    @SerializedName("has_issues")
    var hasIssues: Boolean = false
    @SerializedName("has_projects")
    var hasProjects: Boolean = false
    @SerializedName("has_downloads")
    var hasDownloads: Boolean = false
    @SerializedName("has_wiki")
    var hasWiki: Boolean = false
    @SerializedName("has_pages")
    var hasPages: Boolean = false

}

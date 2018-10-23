package com.shuyu.github.kotlin.model.bean


import com.google.gson.annotations.SerializedName
import java.util.*


class User {

    var login: String? = null
    var id: String? = null
    var name: String? = null
    @SerializedName("avatar_url")
    var avatarUrl: String? = null
    @SerializedName("html_url")
    var htmlUrl: String? = null
    var type: String? = null
    var company: String? = null
    var blog: String? = null
    var location: String? = null
    var email: String? = null
    var bio: String? = null

    var starRepos: Int? = null
    var honorRepos: Int? = null
    @SerializedName("public_repos")
    var publicRepos: Int = 0
    @SerializedName("public_gists")
    var publicGists: Int = 0
    var followers: Int = 0
    var following: Int = 0
    @SerializedName("created_at")
    var createdAt: Date? = null
    @SerializedName("updated_at")
    var updatedAt: Date? = null


}

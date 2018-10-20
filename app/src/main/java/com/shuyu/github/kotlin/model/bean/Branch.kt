package com.shuyu.github.kotlin.model.bean


import com.google.gson.annotations.SerializedName

class Branch {

    var name: String? = null
    @SerializedName("zipball_url")
    var zipballUrl: String? = null
    @SerializedName("tarball_url")
    var tarballUrl: String? = null

    var isBranch = true

}

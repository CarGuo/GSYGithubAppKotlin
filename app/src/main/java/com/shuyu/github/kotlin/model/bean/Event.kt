package com.shuyu.github.kotlin.model.bean

import com.google.gson.annotations.SerializedName

import java.util.Date

class Event {


    var id: String? = null
    var type: String? = null
    var actor: User? = null
    var repo: Repository? = null
    var org: User? = null
    var payload: EventPayload? = null
    @SerializedName("public")
    var isPublic: Boolean = false
    @SerializedName("created_at")
    var createdAt: Date? = null

}
package com.shuyu.github.kotlin.model.bean

import com.google.gson.annotations.SerializedName

import java.util.ArrayList


class SearchResult<M> {

    @SerializedName("total_count")
    var totalCount: String? = null
    @SerializedName("incomplete_results")
    var incompleteResults: Boolean = false
    var items: ArrayList<M>? = null

}

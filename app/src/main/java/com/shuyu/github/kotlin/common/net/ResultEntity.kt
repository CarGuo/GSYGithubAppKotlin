package com.shuyu.github.kotlin.common.net


class ResultEntity<T> {

    var code: Int = 0

    var msg: String? = null

    var data: T? = null


    val isSuccess: Boolean
        get() = code == SUCCESS_CODE

    companion object {
        private const val SUCCESS_CODE = 200
    }

}

package com.shuyu.github.kotlin.common.net

interface ResultCallBack<T> {

    fun onPage(first: Int, current: Int, last: Int) {

    }

    fun onSuccess(result: T?)

    fun onFailure() {

    }

}
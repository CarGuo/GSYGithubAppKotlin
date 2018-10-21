package com.shuyu.github.kotlin.common.net

interface ResultCallBack<T> {

    fun onSuccess(result: T?)

    fun onFailure()

}
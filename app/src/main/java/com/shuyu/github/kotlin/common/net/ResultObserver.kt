package com.shuyu.github.kotlin.common.net

import android.accounts.NetworkErrorException

import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.Response

abstract class ResultObserver<T> : Observer<Response<T>> {

    override fun onSubscribe(d: Disposable) {
        onRequestStart()
    }

    override fun onNext(reposnse: Response<T>) {
        onRequestEnd()
        if (reposnse.code() == 200) {
            try {
                onSuccess(reposnse.body())
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            try {
                onCodeError(reposnse)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    override fun onError(e: Throwable) {
        onRequestEnd()
        try {
            if (e is ConnectException
                    || e is TimeoutException
                    || e is NetworkErrorException
                    || e is UnknownHostException) {
                onFailure(e, true)
            } else {
                onFailure(e, false)
            }
        } catch (e1: Exception) {
            e1.printStackTrace()
        }

    }

    override fun onComplete() {}

    /**
     * 返回成功
     *
     * @param t
     * @throws Exception
     */
    @Throws(Exception::class)
    protected abstract fun onSuccess(t: T?)

    /**
     * 返回成功了,但是code错误
     *
     * @param t
     * @throws Exception
     */
    @Throws(Exception::class)
    protected fun onCodeError(t: Response<T>) {
    }

    /**
     * 返回失败
     *
     * @param e
     * @param isNetWorkError 是否是网络错误
     * @throws Exception
     */
    @Throws(Exception::class)
    protected abstract fun onFailure(e: Throwable, isNetWorkError: Boolean)

    protected fun onRequestStart() {}

    protected fun onRequestEnd() {

    }

}

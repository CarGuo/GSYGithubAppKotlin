package com.shuyu.github.kotlin.common.net

import android.accounts.NetworkErrorException
import android.content.Context

import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class ResultObserver<T>(cxt: Context) : Observer<ResultEntity<T>> {

    protected var mContext: Context? = cxt

    override fun onSubscribe(d: Disposable) {
        onRequestStart()
    }

    override fun onNext(tBaseEntity: ResultEntity<T>) {
        onRequestEnd()
        if (tBaseEntity.isSuccess) {
            try {
                onSuccess(tBaseEntity)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            try {
                onCodeError(tBaseEntity)
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
    protected abstract fun onSuccess(t: ResultEntity<T>)

    /**
     * 返回成功了,但是code错误
     *
     * @param t
     * @throws Exception
     */
    @Throws(Exception::class)
    protected fun onCodeError(t: ResultEntity<T>) {
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
        closeProgressDialog()
    }

    fun showProgressDialog() {
    }

    fun closeProgressDialog() {
    }

}

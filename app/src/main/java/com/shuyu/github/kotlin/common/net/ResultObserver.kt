package com.shuyu.github.kotlin.common.net

import android.accounts.NetworkErrorException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * 网络请求返回处理
 */
abstract class ResultObserver<T> : Observer<Response<T>> {

    override fun onSubscribe(d: Disposable) {
        if (!d.isDisposed) {
            onRequestStart()
        }
    }

    override fun onNext(reposnse: Response<T>) {
        onPageInfo(reposnse)
        onRequestEnd()
        if (reposnse.isSuccessful) {
            try {
                onSuccess(reposnse.body())
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            try {
                onInnerCodeError(reposnse.code(), reposnse.message())
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

    open fun onInnerCodeError(code: Int, message: String) {
        onCodeError(code, message)
    }

    /**
     * 返回成功了,但是code错误
     *
     * @param t
     * @throws Exception
     */
    @Throws(Exception::class)
    open fun onCodeError(code: Int, message: String) {

    }

    open fun onRequestStart() {

    }

    open fun onRequestEnd() {

    }

    open fun onPageInfo(first: Int, current: Int, last: Int) {

    }


    fun onPageInfo(response: Response<T>) {
        val pageString = response.headers().get("page_info")
        if (pageString != null) {
            val pageInfo = GsonUtils.parserJsonToBean(pageString, PageInfo::class.java)
            onPageInfo(pageInfo.first, pageInfo.next - 1, pageInfo.last)
        }
    }

    /**
     * 返回成功
     *
     * @param result
     * @throws Exception
     */
    @Throws(Exception::class)
    abstract fun onSuccess(result: T?)

    /**
     * 返回失败
     *
     * @param e
     * @param isNetWorkError 是否是网络错误
     * @throws Exception
     */
    @Throws(Exception::class)
    abstract fun onFailure(e: Throwable, isNetWorkError: Boolean)

}

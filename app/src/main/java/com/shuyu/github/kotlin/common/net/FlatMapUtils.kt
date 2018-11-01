package com.shuyu.github.kotlin.common.net

import io.reactivex.ObservableSource
import io.reactivex.Observer
import retrofit2.Response

class FlatMapResponse2Result<T>(private val response: Response<T>) : ObservableSource<T> {
    override fun subscribe(observer: Observer<in T?>) {
        if (response.isSuccessful) {
            observer.onNext(response.body())
        } else {
            observer.onError(Throwable(response.errorBody().toString()))
        }
    }
}

class FlatMapResult2Response<T>(private val t: T) : ObservableSource<Response<T>> {

    override fun subscribe(observer: Observer<in Response<T>?>) {
        observer.onNext(Response.success(t))
    }
}

class FlatMapResponse2ResponseResult<T>(private val response: Response<T>, private val conversionCallBack: FlatConversionInterface<T>) : ObservableSource<Response<ArrayList<Any>>> {
    override fun subscribe(observer: Observer<in Response<ArrayList<Any>>>) {
        if (response.isSuccessful) {
            val result = conversionCallBack.onConversion(response.body())
            observer.onNext(Response.success(result, response.headers()))

        } else {
            observer.onError(Throwable(response.errorBody().toString()))
        }

    }
}


class FlatMapResponse2ResponseObject<T, R>(private val response: Response<T>, private val conversionCallBack: FlatConversionObjectInterface<T, R>) : ObservableSource<Response<R>> {
    override fun subscribe(observer: Observer<in Response<R>>) {
        if (response.isSuccessful) {
            val result = conversionCallBack.onConversion(response.body())
            observer.onNext(Response.success(result, response.headers()))

        } else {
            observer.onError(Throwable(response.errorBody().toString()))
        }

    }
}


interface FlatConversionInterface<T> {
    fun onConversion(t: T?): ArrayList<Any>
}

interface FlatConversionObjectInterface<T, R> {
    fun onConversion(t: T?): R
}
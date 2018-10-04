package com.shuyu.github.kotlin.common.net

import com.shuyu.github.kotlin.common.config.AppConfig
import com.shuyu.github.kotlin.common.utils.Debuger
import com.shuyu.github.kotlin.common.utils.GSYPreference
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import java.util.concurrent.TimeUnit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response


class RetrofitFactory private constructor() {

    private var accessTokenStorage: String by GSYPreference(AppConfig.ACCESS_TOKEN, "")

    val retrofit: Retrofit

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val mOkHttpClient = OkHttpClient.Builder()
                .connectTimeout(AppConfig.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .addInterceptor(headerInterceptor())
                .build()
        retrofit = Retrofit.Builder()
                .baseUrl(AppConfig.GITHUB_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mOkHttpClient)
                .build()
    }


    private fun headerInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()

            //add access token
            if (!accessTokenStorage.isEmpty()) {
                Debuger.printfLog(accessTokenStorage)
                val url = request.url().toString()
                request = request.newBuilder()
                        .addHeader("Authorization", accessTokenStorage)
                        .url(url)
                        .build()
            }

            chain.proceed(request)
        }

    }

    companion object {

        @Volatile
        private var mRetrofitFactory: RetrofitFactory? = null

        val instance: RetrofitFactory
            get() {
                if (mRetrofitFactory == null) {
                    synchronized(RetrofitFactory::class.java) {
                        if (mRetrofitFactory == null)
                            mRetrofitFactory = RetrofitFactory()
                    }

                }
                return mRetrofitFactory!!
            }

        fun <T> createService(service: Class<T>): T {
            return instance.retrofit.create(service)
        }

        fun <T> executeResult(observable: Observable<Response<T>>, subscriber: ResultObserver<T>) {
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber)
        }

    }
}

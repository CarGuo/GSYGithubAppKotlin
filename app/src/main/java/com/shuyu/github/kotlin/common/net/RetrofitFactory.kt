package com.shuyu.github.kotlin.common.net

import com.shuyu.github.kotlin.common.config.AppConfig

import java.util.concurrent.TimeUnit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author yemao
 * @date 2017/4/9
 * @description 写自己的代码, 让别人说去吧!
 */

class RetrofitFactory private constructor() {

    val retrofit: Retrofit

    init {
        val mOkHttpClient = OkHttpClient.Builder()
                .connectTimeout(AppConfig.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(headerInterceptor())
                .build()
        retrofit = Retrofit.Builder()
                .baseUrl(AppConfig.GITHUB_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mOkHttpClient)
                .build()
    }

    companion object {

        @Volatile
        private var mRetrofitFactory: RetrofitFactory? = null

        val instence: RetrofitFactory?
            get() {
                if (mRetrofitFactory == null) {
                    synchronized(RetrofitFactory::class.java) {
                        if (mRetrofitFactory == null)
                            mRetrofitFactory = RetrofitFactory()
                    }

                }
                return mRetrofitFactory
            }


        private fun headerInterceptor(): Interceptor {
            return Interceptor { chain ->
                val mRequest = chain.request()
                //在这里你可以做一些想做的事,比如token失效时,重新获取token
                //或者添加header等等,PS我会在下一篇文章总写拦截token方法
                chain.proceed(mRequest)
            }

        }
    }
}

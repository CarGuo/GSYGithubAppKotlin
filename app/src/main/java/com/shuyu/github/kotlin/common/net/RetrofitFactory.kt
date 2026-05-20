package com.shuyu.github.kotlin.common.net

import com.shuyu.github.kotlin.BuildConfig
import com.shuyu.github.kotlin.common.config.AppConfig
import com.shuyu.github.kotlin.common.utils.Debuger
import com.shuyu.github.kotlin.common.utils.GSYPreference
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 网络请求
 */
class RetrofitFactory private constructor() {

    private var accessTokenStorage: String by GSYPreference(AppConfig.ACCESS_TOKEN, "")

    private var userBasicCodeStorage: String by GSYPreference(AppConfig.USER_BASIC_CODE, "")

    val retrofit: Retrofit

    init {
        //打印请求log
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        val mOkHttpClient = OkHttpClient.Builder()
                .connectTimeout(AppConfig.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .addInterceptor(headerInterceptor())
                .addInterceptor(PageInfoInterceptor())
                .build()
        retrofit = Retrofit.Builder()
                .baseUrl(AppConfig.GITHUB_API_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mOkHttpClient)
                .build()
    }

    /**
     * 拦截头部增加token
     */
    private fun headerInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()

            //add access token
            val accessToken = getAuthorization()

            Debuger.printfLog("headerInterceptor", accessToken)

            if (!accessToken.isEmpty()) {
                Debuger.printfLog(accessToken)
                val url = request.url.toString()
                request = request.newBuilder()
                        .addHeader("Authorization", accessToken)
                        .url(url)
                        .build()
            }

            chain.proceed(request)
        }

    }

    /**
     * 获取token
     */
    fun getAuthorization(): String {
        if (accessTokenStorage.isBlank()) {
            val basic = userBasicCodeStorage
            return if (basic.isBlank()) {
                //提示输入账号密码
                ""
            } else {
                //通过 basic 去获取token，获取到设置，返回token
                "Basic $basic"
            }
        }
        return "token $accessTokenStorage"

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

        /**
         * 执行请求返回结果
         */
        fun <T> executeResult(observable: Observable<Response<T>>, subscriber: ResultObserver<T>) {
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber)
        }

    }
}

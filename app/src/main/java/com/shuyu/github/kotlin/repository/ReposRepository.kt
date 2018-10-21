package com.shuyu.github.kotlin.repository

import com.shuyu.github.kotlin.common.net.FlatMapResponse2Result
import com.shuyu.github.kotlin.common.net.ResultObserver
import com.shuyu.github.kotlin.common.net.RetrofitFactory
import com.shuyu.github.kotlin.common.utils.Debuger
import com.shuyu.github.kotlin.model.AppGlobalModel
import com.shuyu.github.kotlin.model.bean.LoginRequestModel
import com.shuyu.github.kotlin.model.conversion.TrendConversion
import com.shuyu.github.kotlin.service.LoginService
import com.shuyu.github.kotlin.service.RepoService
import io.reactivex.Observable
import retrofit2.Retrofit
import javax.inject.Inject

class ReposRepository @Inject constructor(private val retrofit: Retrofit, private val appGlobalModel: AppGlobalModel) {


    fun getTrend() {
        val trendService = retrofit.create(RepoService::class.java)
                .getTrendData(true, "java", "week")

        RetrofitFactory.executeResult(trendService, object : ResultObserver<String>() {
            override fun onSuccess(result: String?) {
                val dataList = TrendConversion.htmlToRepo(result!!)
                Debuger.printfLog("&&&&&&&&&&&&" + dataList.size)
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
            }
        })

    }
}
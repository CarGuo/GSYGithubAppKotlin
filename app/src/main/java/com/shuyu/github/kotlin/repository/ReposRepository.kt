package com.shuyu.github.kotlin.repository

import com.shuyu.github.kotlin.common.net.*
import com.shuyu.github.kotlin.common.utils.Debuger
import com.shuyu.github.kotlin.model.AppGlobalModel
import com.shuyu.github.kotlin.model.bean.LoginRequestModel
import com.shuyu.github.kotlin.model.conversion.ReposConversion
import com.shuyu.github.kotlin.model.conversion.TrendConversion
import com.shuyu.github.kotlin.service.LoginService
import com.shuyu.github.kotlin.service.RepoService
import io.reactivex.Observable
import retrofit2.Retrofit
import javax.inject.Inject

class ReposRepository @Inject constructor(private val retrofit: Retrofit, private val appGlobalModel: AppGlobalModel) {


    fun getTrend(resultCallBack: ResultCallBack<ArrayList<Any>>) {
        val trendService = retrofit.create(RepoService::class.java)
                .getTrendData(true, "java", "week")
                .flatMap {
                    FlatMapResponse2Result(it)
                }.map {
                    TrendConversion.htmlToRepo(it)
                }.map {
                    val dataUIList = ArrayList<Any>()
                    for (reposUi in it) {
                        dataUIList.add(ReposConversion.trendToReposUIModel(reposUi))
                    }
                    dataUIList
                }.flatMap {
                    FlatMapResult2Response(it)
                }

        RetrofitFactory.executeResult(trendService, object : ResultObserver<ArrayList<Any>>() {
            override fun onSuccess(result: ArrayList<Any>?) {
                resultCallBack.onSuccess(result)
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack.onFailure()
            }
        })

    }
}
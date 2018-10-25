package com.shuyu.github.kotlin.repository

import android.app.Application
import com.shuyu.github.kotlin.common.net.*
import com.shuyu.github.kotlin.common.utils.HtmlUtils
import com.shuyu.github.kotlin.model.conversion.ReposConversion
import com.shuyu.github.kotlin.model.conversion.TrendConversion
import com.shuyu.github.kotlin.service.RepoService
import retrofit2.Retrofit
import javax.inject.Inject

class ReposRepository @Inject constructor(private val retrofit: Retrofit, private val application: Application) {


    fun getTrend(resultCallBack: ResultCallBack<ArrayList<Any>>, language: String, since: String) {
        val trendService = retrofit.create(RepoService::class.java)
                .getTrendData(true, language, since)
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

    fun getReposReadme(resultCallBack: ResultCallBack<String>, userName: String, reposName: String) {
        val readeService = retrofit.create(RepoService::class.java)
                .getReadmeHtml(true, userName, reposName)
                .flatMap {
                    FlatMapResponse2Result(it)
                }.map {
                    HtmlUtils.generateHtml(application, it)
                }.flatMap {
                    FlatMapResult2Response(it)
                }

        RetrofitFactory.executeResult(readeService, object : ResultObserver<String>() {
            override fun onSuccess(result: String?) {
                resultCallBack.onSuccess(result)
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack.onFailure()
            }
        })

    }
}
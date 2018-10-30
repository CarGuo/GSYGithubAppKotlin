package com.shuyu.github.kotlin.repository

import android.app.Application
import com.shuyu.github.kotlin.common.net.*
import com.shuyu.github.kotlin.model.bean.IssueEvent
import com.shuyu.github.kotlin.model.conversion.IssueConversion
import com.shuyu.github.kotlin.model.ui.IssueUIModel
import com.shuyu.github.kotlin.service.IssueService
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by guoshuyu
 * Date: 2018-10-29
 */
class IssueRepository @Inject constructor(private val retrofit: Retrofit, private val application: Application) {


    fun getIssueInfo(userName: String, reposName: String, number: Int, resultCallBack: ResultCallBack<IssueUIModel>?) {
        val issueService = retrofit.create(IssueService::class.java)
                .getIssueInfo(true, userName, reposName, number)
                .flatMap {
                    FlatMapResponse2Result(it)
                }.map {
                    IssueConversion.issueToIssueUIModel(it)
                }.flatMap {
                    FlatMapResult2Response(it)
                }

        RetrofitFactory.executeResult(issueService, object : ResultObserver<IssueUIModel>() {
            override fun onSuccess(result: IssueUIModel?) {
                resultCallBack?.onSuccess(result)
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack?.onFailure()
            }
        })
    }

    fun getIssueComments(userName: String, reposName: String, number: Int, page: Int, resultCallBack: ResultCallBack<ArrayList<Any>>?) {
        val issueService = retrofit.create(IssueService::class.java)
                .getIssueComments(true, userName, reposName, number, page)
                .flatMap {
                    FlatMapResponse2ResponseResult(it, object : FlatConversionInterface<ArrayList<IssueEvent>> {
                        override fun onConversion(t: ArrayList<IssueEvent>?): ArrayList<Any> {
                            val list = ArrayList<Any>()
                            t?.apply {
                                for (issue in t) {
                                    list.add(IssueConversion.issueEventToIssueUIModel(issue))
                                }
                            }
                            return list
                        }
                    })
                }
        RetrofitFactory.executeResult(issueService, object : ResultObserver<ArrayList<Any>>() {

            override fun onPageInfo(first: Int, current: Int, last: Int) {
                resultCallBack?.onPage(first, current, last)
            }

            override fun onSuccess(result: ArrayList<Any>?) {
                resultCallBack?.onSuccess(result)
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack?.onFailure()
            }
        })
    }
}
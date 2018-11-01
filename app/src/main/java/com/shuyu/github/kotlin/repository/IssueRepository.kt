package com.shuyu.github.kotlin.repository

import android.app.Application
import android.content.Context
import com.shuyu.github.kotlin.common.net.*
import com.shuyu.github.kotlin.model.bean.CommentRequestModel
import com.shuyu.github.kotlin.model.bean.Issue
import com.shuyu.github.kotlin.model.bean.IssueEvent
import com.shuyu.github.kotlin.model.conversion.IssueConversion
import com.shuyu.github.kotlin.model.ui.IssueUIModel
import com.shuyu.github.kotlin.service.IssueService
import okhttp3.ResponseBody
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by guoshuyu
 * Date: 2018-10-29
 */
class IssueRepository @Inject constructor(private val retrofit: Retrofit, private val application: Application) {

    /**
     * issue 信息
     */
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

    /**
     * issue 评论
     */
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

    fun editIssue(context: Context, userName: String, reposName: String, number: Int, issue: Issue, resultCallBack: ResultCallBack<IssueUIModel>?) {
        val issueService = retrofit.create(IssueService::class.java).editIssue(userName, reposName, number, issue)
                .flatMap {
                    FlatMapResponse2ResponseObject(it, object : FlatConversionObjectInterface<Issue, IssueUIModel> {
                        override fun onConversion(t: Issue?): IssueUIModel {
                            return IssueConversion.issueToIssueUIModel(t!!)
                        }
                    })
                }
        RetrofitFactory.executeResult(issueService, object : ResultProgressObserver<IssueUIModel>(context) {

            override fun onSuccess(result: IssueUIModel?) {
                resultCallBack?.onSuccess(result)
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack?.onFailure()
            }
        })
    }

    fun createIssue(context: Context, userName: String, reposName: String, issue: Issue, resultCallBack: ResultCallBack<IssueUIModel>?) {
        val issueService = retrofit.create(IssueService::class.java).createIssue(userName, reposName, issue)
                .flatMap {
                    FlatMapResponse2ResponseObject(it, object : FlatConversionObjectInterface<Issue, IssueUIModel> {
                        override fun onConversion(t: Issue?): IssueUIModel {
                            return IssueConversion.issueToIssueUIModel(t!!)
                        }
                    })
                }
        RetrofitFactory.executeResult(issueService, object : ResultProgressObserver<IssueUIModel>(context) {

            override fun onSuccess(result: IssueUIModel?) {
                resultCallBack?.onSuccess(result)
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack?.onFailure()
            }
        })
    }

    fun commentIssue(context: Context, userName: String, reposName: String, number: Int, commentRequestModel: CommentRequestModel, resultCallBack: ResultCallBack<IssueUIModel>?) {
        val issueService = retrofit.create(IssueService::class.java).addComment(userName, reposName, number, commentRequestModel)
                .flatMap {
                    FlatMapResponse2ResponseObject(it, object : FlatConversionObjectInterface<IssueEvent, IssueUIModel> {
                        override fun onConversion(t: IssueEvent?): IssueUIModel {
                            return IssueConversion.issueEventToIssueUIModel(t!!)
                        }
                    })
                }
        RetrofitFactory.executeResult(issueService, object : ResultProgressObserver<IssueUIModel>(context) {

            override fun onSuccess(result: IssueUIModel?) {
                resultCallBack?.onSuccess(result)
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack?.onFailure()
            }
        })

    }

    fun lockIssue(context: Context, userName: String, reposName: String, number: Int, lock: Boolean, resultCallBack: ResultCallBack<Boolean>?) {
        val service = retrofit.create(IssueService::class.java)
        val issueService = if (lock) {
            service.lockIssue(userName, reposName, number)
        } else {
            service.unLockIssue(userName, reposName, number)
        }
        RetrofitFactory.executeResult(issueService, object : ResultProgressObserver<ResponseBody>(context) {

            override fun onSuccess(result: ResponseBody?) {
                resultCallBack?.onSuccess(true)
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack?.onFailure()
            }
        })
    }


    fun editComment(context: Context, userName: String, reposName: String, commentId: String, commentRequestModel: CommentRequestModel, resultCallBack: ResultCallBack<IssueUIModel>?) {
        val issueService = retrofit.create(IssueService::class.java).editComment(userName, reposName, commentId, commentRequestModel)
                .flatMap {
                    FlatMapResponse2ResponseObject(it, object : FlatConversionObjectInterface<IssueEvent, IssueUIModel> {
                        override fun onConversion(t: IssueEvent?): IssueUIModel {
                            return IssueConversion.issueEventToIssueUIModel(t!!)
                        }
                    })
                }
        RetrofitFactory.executeResult(issueService, object : ResultProgressObserver<IssueUIModel>(context) {

            override fun onSuccess(result: IssueUIModel?) {
                resultCallBack?.onSuccess(result)
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack?.onFailure()
            }
        })
    }

    fun deleteComment(context: Context, userName: String, reposName: String, commentId: String, resultCallBack: ResultCallBack<String>?) {
        val issueService = retrofit.create(IssueService::class.java).deleteComment(userName, reposName, commentId)
        RetrofitFactory.executeResult(issueService, object : ResultProgressObserver<ResponseBody>(context) {
            override fun onSuccess(result: ResponseBody?) {
                resultCallBack?.onSuccess(commentId)
            }

            override fun onCodeError(code: Int, message: String) {
                if (code == 404) {
                    resultCallBack?.onSuccess(commentId)
                } else {
                    resultCallBack?.onFailure()
                }
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack?.onFailure()
            }
        })
    }

}
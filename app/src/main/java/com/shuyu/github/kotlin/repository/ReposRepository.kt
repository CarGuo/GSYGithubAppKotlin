package com.shuyu.github.kotlin.repository

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.net.*
import com.shuyu.github.kotlin.common.utils.HtmlUtils
import com.shuyu.github.kotlin.model.bean.Event
import com.shuyu.github.kotlin.model.bean.Issue
import com.shuyu.github.kotlin.model.bean.Repository
import com.shuyu.github.kotlin.model.conversion.EventConversion
import com.shuyu.github.kotlin.model.conversion.IssueConversion
import com.shuyu.github.kotlin.model.conversion.ReposConversion
import com.shuyu.github.kotlin.model.conversion.TrendConversion
import com.shuyu.github.kotlin.model.ui.ReposUIModel
import com.shuyu.github.kotlin.service.IssueService
import com.shuyu.github.kotlin.service.RepoService
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import okhttp3.ResponseBody
import org.jetbrains.anko.toast
import retrofit2.Retrofit
import javax.inject.Inject

class ReposRepository @Inject constructor(private val retrofit: Retrofit, private val application: Application) {

    companion object {
        const val STAR_KEY = "starred"
        const val WATCH_KEY = "watched"
    }

    /**
     * 趋势
     */
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

    /**
     * readme
     */
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


    /**
     * 文件详情
     */
    fun getRepoFilesDetail(userName: String, reposName: String, path: String, resultCallBack: ResultCallBack<String>) {
        val readeService = retrofit.create(RepoService::class.java)
                .getRepoFilesDetail(userName, reposName, path)
                .flatMap {
                    FlatMapResponse2Result(it)
                }.map {
                    HtmlUtils.resolveHtmlFile(application, it)
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


    /**
     * 仓库详情
     */
    fun getRepoInfo(userName: String, reposName: String, resultCallBack: ResultCallBack<ReposUIModel>?) {
        val infoService = retrofit.create(RepoService::class.java).getRepoInfo(true, userName, reposName)
                .flatMap {
                    FlatMapResponse2Result(it)
                }.map {
                    ReposConversion.reposToReposUIModel(application, it)
                }.flatMap {
                    FlatMapResult2Response(it)
                }

        RetrofitFactory.executeResult(infoService, object : ResultObserver<ReposUIModel>() {

            override fun onSuccess(result: ReposUIModel?) {
                resultCallBack?.onSuccess(result)
            }

            override fun onCodeError(code: Int, message: String) {
                resultCallBack?.onFailure()
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack?.onFailure()
            }

        })
    }

    /**
     * 仓库事件
     */
    fun getReposEvents(userName: String, reposName: String, resultCallBack: ResultCallBack<ArrayList<Any>>?, page: Int = 1) {
        val eventService = retrofit.create(RepoService::class.java).getRepoEvent(true, userName, reposName, page)
                .flatMap {
                    FlatMapResponse2ResponseResult(it, object : FlatConversionInterface<ArrayList<Event>> {
                        override fun onConversion(t: ArrayList<Event>?): ArrayList<Any> {
                            val list = ArrayList<Any>()
                            t?.apply {
                                for (event in t) {
                                    list.add(EventConversion.eventToEventUIModel(event))
                                }
                            }
                            return list
                        }
                    })
                }

        RetrofitFactory.executeResult(eventService, object : ResultObserver<ArrayList<Any>>() {

            override fun onPageInfo(first: Int, current: Int, last: Int) {
                resultCallBack?.onPage(first, current, last)
            }

            override fun onSuccess(result: ArrayList<Any>?) {
                resultCallBack?.onSuccess(result)
            }

            override fun onCodeError(code: Int, message: String) {
                resultCallBack?.onFailure()
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack?.onFailure()
            }

        })
    }


    /**
     * 仓库文件
     */
    fun getFiles(userName: String, reposName: String, path: String, resultCallBack: ResultCallBack<ArrayList<Any>>?) {
        val eventService = retrofit.create(RepoService::class.java).getRepoFiles(userName, reposName, path)
                .flatMap {
                    FlatMapResponse2Result(it)
                }.map {
                    ReposConversion.fileListToFileUIList(it)
                }.flatMap {
                    FlatMapResult2Response(it)
                }

        RetrofitFactory.executeResult(eventService, object : ResultObserver<ArrayList<Any>>() {

            override fun onSuccess(result: ArrayList<Any>?) {
                resultCallBack?.onSuccess(result)
            }

            override fun onCodeError(code: Int, message: String) {
                resultCallBack?.onFailure()
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack?.onFailure()
            }

        })

    }

    /**
     * 获取仓库状态
     */
    fun getReposStatus(userName: String, reposName: String, resultCallBack: ResultCallBack<HashMap<String, Boolean>>?) {
        val starredService = retrofit.create(RepoService::class.java).checkRepoStarred(userName, reposName)
                .flatMap {
                    val starred = if (it.code() == 404) {
                        false
                    } else it.isSuccessful
                    Observable.just(starred)
                }
        val watchedService = retrofit.create(RepoService::class.java).checkRepoWatched(userName, reposName)
                .flatMap {
                    val watched = if (it.code() == 404) {
                        false
                    } else it.isSuccessful
                    Observable.just(watched)
                }

        val statusService = Observable.zip(starredService, watchedService,
                BiFunction<Boolean, Boolean, HashMap<String, Boolean>> { starred, watched ->
                    val map = HashMap<String, Boolean>()
                    map[STAR_KEY] = starred
                    map[WATCH_KEY] = watched
                    map
                })
                .flatMap {
                    FlatMapResult2Response(it)
                }
        RetrofitFactory.executeResult(statusService, object : ResultObserver<HashMap<String, Boolean>>() {

            override fun onSuccess(result: HashMap<String, Boolean>?) {
                resultCallBack?.onSuccess(result)
            }

            override fun onCodeError(code: Int, message: String) {
                resultCallBack?.onFailure()
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack?.onFailure()
            }

        })
    }

    /**
     * STAR 操作
     */
    fun changeStarStatus(context: Context, userName: String, reposName: String, status: MutableLiveData<Boolean>) {
        val reposService = retrofit.create(RepoService::class.java)
        val starred = status.value ?: return
        val starredStatus = if (starred) {
            reposService.unstarRepo(userName, reposName)
        } else {
            reposService.starRepo(userName, reposName)
        }
        RetrofitFactory.executeResult(starredStatus, object : ResultProgressObserver<ResponseBody>(context) {

            override fun onSuccess(result: ResponseBody?) {
                status.value = status.value?.not()
            }

            override fun onCodeError(code: Int, message: String) {

            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {

            }

        })
    }

    fun changeWatchStatus(context: Context, userName: String, reposName: String, status: MutableLiveData<Boolean>) {
        val reposService = retrofit.create(RepoService::class.java)
        val watched = status.value ?: return
        val watchedStatus = if (watched) {
            reposService.unwatchRepo(userName, reposName)
        } else {
            reposService.watchRepo(userName, reposName)
        }
        RetrofitFactory.executeResult(watchedStatus, object : ResultProgressObserver<ResponseBody>(context) {

            override fun onSuccess(result: ResponseBody?) {
                status.value = status.value?.not()
            }

            override fun onCodeError(code: Int, message: String) {

            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {

            }

        })
    }

    fun forkRepository(context: Context, userName: String, reposName: String) {
        val reposService = retrofit.create(RepoService::class.java)
                .createFork(userName, reposName)
        RetrofitFactory.executeResult(reposService, object : ResultProgressObserver<Repository>(context) {

            override fun onSuccess(result: Repository?) {
                context.toast(R.string.forkSuccess)
            }

            override fun onCodeError(code: Int, message: String) {
                context.toast(R.string.forkFail)
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                context.toast(R.string.forkFail)
            }
        })
    }

    /**
     * 获取issue列表
     */
    fun getReposIssueList(userName: String, reposName: String, status: String, page: Int, resultCallBack: ResultCallBack<ArrayList<Any>>?) {
        val eventService = retrofit.create(IssueService::class.java).getRepoIssues(true, userName, reposName, page, status)
                .flatMap {
                    FlatMapResponse2ResponseResult(it, object : FlatConversionInterface<ArrayList<Issue>> {
                        override fun onConversion(t: ArrayList<Issue>?): ArrayList<Any> {
                            val list = ArrayList<Any>()
                            t?.apply {
                                for (issue in t) {
                                    list.add(IssueConversion.issueToIssueUIModel(issue))
                                }
                            }
                            return list
                        }
                    })
                }

        RetrofitFactory.executeResult(eventService, object : ResultObserver<ArrayList<Any>>() {

            override fun onPageInfo(first: Int, current: Int, last: Int) {
                resultCallBack?.onPage(first, current, last)
            }

            override fun onSuccess(result: ArrayList<Any>?) {
                resultCallBack?.onSuccess(result)
            }

            override fun onCodeError(code: Int, message: String) {
                resultCallBack?.onFailure()
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack?.onFailure()
            }

        })
    }
}
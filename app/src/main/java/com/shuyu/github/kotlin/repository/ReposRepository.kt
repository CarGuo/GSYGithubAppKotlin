package com.shuyu.github.kotlin.repository

import android.app.Application
import com.shuyu.github.kotlin.common.net.*
import com.shuyu.github.kotlin.common.utils.HtmlUtils
import com.shuyu.github.kotlin.model.bean.Event
import com.shuyu.github.kotlin.model.conversion.EventConversion
import com.shuyu.github.kotlin.model.conversion.ReposConversion
import com.shuyu.github.kotlin.model.conversion.TrendConversion
import com.shuyu.github.kotlin.model.ui.ReposUIModel
import com.shuyu.github.kotlin.service.RepoService
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import retrofit2.Retrofit
import javax.inject.Inject

class ReposRepository @Inject constructor(private val retrofit: Retrofit, private val application: Application) {

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
                    map["starred"] = starred
                    map["watched"] = watched
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
}
package com.shuyu.github.kotlin.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.net.*
import com.shuyu.github.kotlin.common.utils.HtmlUtils
import com.shuyu.github.kotlin.common.utils.compareVersion
import com.shuyu.github.kotlin.common.utils.getVersionName
import com.shuyu.github.kotlin.model.bean.*
import com.shuyu.github.kotlin.model.conversion.EventConversion
import com.shuyu.github.kotlin.model.conversion.IssueConversion
import com.shuyu.github.kotlin.model.conversion.ReposConversion
import com.shuyu.github.kotlin.model.conversion.TrendConversion
import com.shuyu.github.kotlin.model.ui.PushUIModel
import com.shuyu.github.kotlin.model.ui.ReposUIModel
import com.shuyu.github.kotlin.repository.dao.ReposDao
import com.shuyu.github.kotlin.service.CommitService
import com.shuyu.github.kotlin.service.IssueService
import com.shuyu.github.kotlin.service.RepoService
import com.shuyu.github.kotlin.service.SearchService
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import okhttp3.ResponseBody
import org.jetbrains.anko.toast
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * 仓库相关数据获取
 */
class ReposRepository @Inject constructor(private val retrofit: Retrofit, private val application: Application, private val reposDao: ReposDao) {

    companion object {
        const val STAR_KEY = "starred"
        const val WATCH_KEY = "watched"
    }

    /**
     * 通过仓库release信息，检查当前App的更新
     */
    fun checkoutUpDate(context: Context, resultCallBack: ResultCallBack<Release>?) {
        val service = retrofit.create(RepoService::class.java)
                .getReleasesNotHtml(true, "CarGuo", "GSYGithubAppKotlin", 1)
                .flatMap {
                    FlatMapResponse2Result(it)
                }.map {
                    if (it.size > 0) {
                        val item = it[0]
                        val versionName = item.name
                        versionName?.apply {
                            val currentName = context.getVersionName()
                            val hadNew = currentName.compareVersion(versionName) != currentName
                            if (hadNew) {
                                return@map item
                            }
                        }
                        Release()
                    } else {
                        Release()
                    }
                }.flatMap {
                    FlatMapResult2Response(it)
                }

        RetrofitFactory.executeResult(service, object : ResultTipObserver<Release>(application) {
            override fun onSuccess(result: Release?) {
                resultCallBack?.onSuccess(result)
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack?.onFailure()
            }
        })
    }

    /**
     * 趋势
     * @param language 语言
     * @param since 时间（今天/本周/本月）
     */
    fun getTrend(resultCallBack: ResultCallBack<ArrayList<Any>>, language: String, since: String) {

        val dbService = reposDao.getTrendDao(language, since)
                .doOnNext {
                    resultCallBack.onCacheSuccess(it)
                }


        val trendService = retrofit.create(RepoService::class.java)
                .getTrendData(true, language, since)
                .flatMap {
                    FlatMapResponse2Result(it)
                }.map {
                    TrendConversion.htmlToRepo(it)
                }.doOnNext {
                    reposDao.saveTrendDao(Response.success(GsonUtils.toJsonString(it)), language, since, true)
                }.map {
                    val dataUIList = ArrayList<Any>()
                    for (reposUi in it) {
                        dataUIList.add(ReposConversion.trendToReposUIModel(reposUi))
                    }
                    dataUIList
                }.flatMap {
                    FlatMapResult2Response(it)
                }


        val zipService = Observable.zip(dbService, trendService,
                BiFunction<ArrayList<Any>, Response<ArrayList<Any>>, Response<ArrayList<Any>>> { _, remote ->
                    remote
                })

        RetrofitFactory.executeResult(zipService, object : ResultTipObserver<ArrayList<Any>>(application) {
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

        val dbService = reposDao.getReadmeDao(userName, reposName, branch = "master")
                .doOnNext {
                    resultCallBack.onCacheSuccess(it)
                }


        val readeService = retrofit.create(RepoService::class.java)
                .getReadmeHtml(true, userName, reposName)
                .flatMap {
                    FlatMapResponse2Result(it)
                }.map {
                    HtmlUtils.generateHtml(application, it)
                }.flatMap {
                    FlatMapResult2Response(it)
                }.doOnNext {
                    reposDao.saveReadme(it, userName, reposName, branch = "master")
                }


        val zipService = Observable.zip(dbService, readeService,
                BiFunction<String, Response<String>, Response<String>> { _, remote ->
                    remote
                })

        RetrofitFactory.executeResult(zipService, object : ResultTipObserver<String>(application) {
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

        RetrofitFactory.executeResult(readeService, object : ResultTipObserver<String>(application) {
            override fun onSuccess(result: String?) {
                resultCallBack.onSuccess(result)
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                if(!isNetWorkError) {
                    resultCallBack.onFailure()
                }
            }
            override fun onCodeError(code: Int, message: String) {
                super.onCodeError(code, message)
                resultCallBack.onFailure()
            }
        })

    }


    /**
     * 仓库详情
     */
    fun getRepoInfo(userName: String, reposName: String, resultCallBack: ResultCallBack<ReposUIModel>?) {

        val dbService = reposDao.getRepoInfoDao(userName, reposName)
                .doOnNext {
                    resultCallBack?.onCacheSuccess(it)
                }


        val infoService = retrofit.create(RepoService::class.java).getRepoInfo(true, userName, reposName)
                .doOnNext {
                    reposDao.saveReposInfo(it, userName, reposName)
                }
                .flatMap {
                    FlatMapResponse2Result(it)
                }.map {
                    ReposConversion.reposToReposUIModel(application, it)
                }.flatMap {
                    FlatMapResult2Response(it)
                }


        val zipService = Observable.zip(dbService, infoService,
                BiFunction<ReposUIModel, Response<ReposUIModel>, Response<ReposUIModel>> { _, remote ->
                    remote
                })

        RetrofitFactory.executeResult(zipService, object : ResultTipObserver<ReposUIModel>(application) {

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


        val dbService = reposDao.getReposEventDao(userName, reposName)
                .doOnNext {
                    if (page == 1) {
                        resultCallBack?.onCacheSuccess(it)
                    }
                }


        val eventService = retrofit.create(RepoService::class.java).getRepoEvent(true, userName, reposName, page)
                .doOnNext {
                    reposDao.saveReposEventDao(it, userName, reposName, page == 1)
                }
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

        val zipService = Observable.zip(dbService, eventService,
                BiFunction<ArrayList<Any>, Response<ArrayList<Any>>, Response<ArrayList<Any>>> { _, remote ->
                    remote
                })

        RetrofitFactory.executeResult(zipService, object : ResultTipObserver<ArrayList<Any>>(application) {

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

    fun getReposCommits(userName: String, reposName: String, resultCallBack: ResultCallBack<ArrayList<Any>>?, page: Int = 1) {

        val dbService = reposDao.getReposCommitDao(userName, reposName)
                .doOnNext {
                    if (page == 1) {
                        resultCallBack?.onCacheSuccess(it)
                    }
                }

        val eventService = retrofit.create(CommitService::class.java).getRepoCommits(true, userName, reposName, page)
                .doOnNext {
                    reposDao.saveReposCommitDao(it, userName, reposName, page == 1)
                }
                .flatMap {
                    FlatMapResponse2ResponseResult(it, object : FlatConversionInterface<ArrayList<RepoCommit>> {
                        override fun onConversion(t: ArrayList<RepoCommit>?): ArrayList<Any> {
                            val list = ArrayList<Any>()
                            t?.apply {
                                for (item in t) {
                                    list.add(EventConversion.commitToCommitUIModel(item))
                                }
                            }
                            return list
                        }
                    })
                }

        val zipService = Observable.zip(dbService, eventService,
                BiFunction<ArrayList<Any>, Response<ArrayList<Any>>, Response<ArrayList<Any>>> { _, remote ->
                    remote
                })

        RetrofitFactory.executeResult(zipService, object : ResultTipObserver<ArrayList<Any>>(application) {

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
     * 仓库文件数据
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

        RetrofitFactory.executeResult(eventService, object : ResultTipObserver<ArrayList<Any>>(application) {

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
     * 获取当前用户对仓库状态
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
     * 改变当前用户对仓库的Star状态
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

    /**
     * 改变当前用户对仓库的订阅状态
     */
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
     * 获取仓库的issue列表
     */
    fun getReposIssueList(userName: String, reposName: String, status: String, page: Int, resultCallBack: ResultCallBack<ArrayList<Any>>?) {

        val dbService = reposDao.getReposIssueDao(userName, reposName, status)
                .doOnNext {
                    resultCallBack?.onCacheSuccess(it)
                }
        val issueService = retrofit.create(IssueService::class.java).getRepoIssues(true, userName, reposName, page, status)
                .doOnNext {
                    reposDao.saveReposIssue(it, userName, reposName, status, page == 1)
                }
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

        val zipService = Observable.zip(dbService, issueService,
                BiFunction<ArrayList<Any>, Response<ArrayList<Any>>, Response<ArrayList<Any>>> { _, remote ->
                    remote
                })

        RetrofitFactory.executeResult(zipService, object : ResultTipObserver<ArrayList<Any>>(application) {

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
     * 搜索仓库相关的issue
     * @param status issue 状态
     * @param query 搜索关键字
     */
    fun searchReposIssueList(userName: String, reposName: String, status: String, query: String, page: Int, resultCallBack: ResultCallBack<ArrayList<Any>>?) {
        val q = if (status == "all") {
            "$query+repo:$userName/$reposName"
        } else {
            "$query+repo:$userName/$reposName+state:$status"
        }
        val issueService = retrofit.create(SearchService::class.java).searchIssues(true, q, page)
                .flatMap {
                    FlatMapResponse2ResponseResult(it, object : FlatConversionInterface<SearchResult<Issue>> {
                        override fun onConversion(t: SearchResult<Issue>?): ArrayList<Any> {
                            val list = ArrayList<Any>()
                            t?.items?.apply {
                                for (issue in this) {
                                    list.add(IssueConversion.issueToIssueUIModel(issue))
                                }
                            }
                            return list
                        }
                    })
                }
        RetrofitFactory.executeResult(issueService, object : ResultTipObserver<ArrayList<Any>>(application) {

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
     * 获取仓库被Fork的数据
     */
    fun getReposFork(userName: String, reposName: String, page: Int, resultCallBack: ResultCallBack<ArrayList<Any>>?) {

        val dbService = reposDao.getReposFork(userName, reposName)

        val netService = retrofit.create(RepoService::class.java)
                .getForks(true, userName, reposName, page)
                .doOnNext {
                    reposDao.saveReposFork(it, userName, reposName, page == 1)
                }

        reposListRequest(dbService, netService, resultCallBack, page)
    }

    /**
     * 获取用户的仓库数据
     */
    fun getUserRepos(userName: String, page: Int, sort: String, resultCallBack: ResultCallBack<ArrayList<Any>>?) {

        val dbService = reposDao.getUserRepos(userName, sort)

        val netService = retrofit.create(RepoService::class.java)
                .getUserPublicRepos(true, userName, page, sort)
                .doOnNext {
                    reposDao.saveUserRepos(it, userName, sort, page == 1)
                }

        reposListRequest(dbService, netService, resultCallBack, page)

    }

    /**
     * 获取用户star的仓库数据
     */
    fun getUserStarRepos(userName: String, page: Int, resultCallBack: ResultCallBack<ArrayList<Any>>?) {

        val dbService = reposDao.getUserStarRepos(userName)

        val netService = retrofit.create(RepoService::class.java)
                .getStarredRepos(true, userName, page)
                .doOnNext {
                    reposDao.saveUserStarRepos(it, userName, page == 1)
                }

        reposListRequest(dbService, netService, resultCallBack, page)
    }

    /**
     * 获取提交详情
     */
    fun getPushDetailInfo(userName: String, reposName: String, sha: String, resultCallBack: ResultCallBack<PushUIModel>?, listCallBack: ResultCallBack<ArrayList<Any>>?) {
        val service = retrofit.create(CommitService::class.java).getCommitInfo(true, userName, reposName, sha)
                .flatMap {
                    FlatMapResponse2Result(it)
                }.map {
                    resultCallBack?.onSuccess(ReposConversion.pushInfoToPushUIModel(it))
                    it
                }.map {
                    val list = arrayListOf<Any>()
                    it.files?.apply {
                        forEach {
                            list.add(ReposConversion.repoCommitToFileUIModel(application, it))
                        }
                    }
                    list
                }.flatMap {
                    FlatMapResult2Response(it)
                }

        RetrofitFactory.executeResult(service, object : ResultTipObserver<ArrayList<Any>>(application) {
            override fun onSuccess(result: ArrayList<Any>?) {
                listCallBack?.onSuccess(result)
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack?.onFailure()
                listCallBack?.onFailure()
            }
        })
    }

    /**
     * 仓库相关的 observer 请求执行
     */
    private fun reposListRequest(dbObserver: Observable<ArrayList<Any>>, observer: Observable<Response<ArrayList<Repository>>>, resultCallBack: ResultCallBack<ArrayList<Any>>?, page: Int) {


        val dbService = dbObserver.doOnNext {
            if (page == 1) {
                resultCallBack?.onCacheSuccess(it)
            }
        }

        val service = observer.flatMap {
            FlatMapResponse2ResponseResult(it, object : FlatConversionInterface<ArrayList<Repository>> {
                override fun onConversion(t: ArrayList<Repository>?): ArrayList<Any> {
                    val list = arrayListOf<Any>()
                    t?.apply {
                        this.forEach { data ->
                            val item = ReposConversion.reposToReposUIModel(application, data)
                            list.add(item)
                        }
                    }
                    return list
                }
            })
        }

        val zipService = Observable.zip(dbService, service,
                BiFunction<ArrayList<Any>, Response<ArrayList<Any>>, Response<ArrayList<Any>>> { _, remote ->
                    remote
                })

        RetrofitFactory.executeResult(zipService, object : ResultTipObserver<ArrayList<Any>>(application) {
            override fun onPageInfo(first: Int, current: Int, last: Int) {
                super.onPageInfo(first, current, last)
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
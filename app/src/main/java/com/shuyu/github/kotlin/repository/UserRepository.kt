package com.shuyu.github.kotlin.repository

import android.app.Application
import com.shuyu.github.kotlin.common.config.AppConfig
import com.shuyu.github.kotlin.common.net.*
import com.shuyu.github.kotlin.common.utils.Debuger
import com.shuyu.github.kotlin.common.utils.GSYPreference
import com.shuyu.github.kotlin.model.AppGlobalModel
import com.shuyu.github.kotlin.model.bean.Event
import com.shuyu.github.kotlin.model.bean.User
import com.shuyu.github.kotlin.model.conversion.EventConversion
import com.shuyu.github.kotlin.model.conversion.UserConversion
import com.shuyu.github.kotlin.service.RepoService
import com.shuyu.github.kotlin.service.UserService
import io.reactivex.Observable
import io.reactivex.functions.Function
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject


class UserRepository @Inject constructor(private val retrofit: Retrofit, private val appGlobalModel: AppGlobalModel, private val application: Application) {

    private var userInfoStorage: String by GSYPreference(AppConfig.USER_INFO, "")

    fun getPersonInfoObservable(userName: String? = null): Observable<User> {
        val isLoginUser = userName == null
        val userService = if (isLoginUser) {
            retrofit.create(UserService::class.java).getPersonInfo(true)
        } else {
            retrofit.create(UserService::class.java).getUser(true, userName!!)
        }
        return userService.flatMap {
            FlatMapResponse2Result(it)
        }.flatMap {
            ///获取用户star数
            val starredService = retrofit.create(RepoService::class.java).getStarredRepos(true, it.login!!, 1, "updated", 1)
            val response = starredService.blockingSingle()
            val pageString = response.headers().get("page_info")
            if (pageString != null) {
                val pageInfo = GsonUtils.parserJsonToBean(pageString, PageInfo::class.java)
                it.starRepos = pageInfo.last
            }
            Observable.just(it)
        }.doOnNext {
            if (isLoginUser) {
                ///保存用户信息
                userInfoStorage = GsonUtils.toJsonString(it)
                UserConversion.cloneDataFromUser(application, it, appGlobalModel.userObservable)
            }
            Debuger.printfLog("userInfo $userInfoStorage")
        }.onErrorResumeNext(Function<Throwable, Observable<User>> { t ->
            ///拦截错误
            //userInfoStorage = ""
            Debuger.printfLog("userInfo onErrorResumeNext ")
            Observable.error(t)
        })
    }


    fun getUserEventObservable(userName: String?, page: Int = 1): Observable<Response<ArrayList<Event>>> {
        return retrofit.create(UserService::class.java)
                .getUserEvents(true, userName ?: "", page)
    }

    /**
     * 获取用户信息
     */
    fun getPersonInfo(resultCallBack: ResultCallBack<User>?, resultEventCallBack: ResultCallBack<ArrayList<Any>>, userName: String? = null) {
        val mergeService = getPersonInfoObservable(userName)
                .flatMap {
                    resultCallBack?.onSuccess(it)
                    getUserEventObservable(it.login)
                }
        userEventRequest(mergeService, resultEventCallBack)
    }

    /**
     * 获取用户产生的事件
     */
    fun getUserEvent(login: String?, resultCallBack: ResultCallBack<ArrayList<Any>>, page: Int = 1) {
        val username = login ?: ""
        if (username.isEmpty()) {
            return
        }
        val userEvent = getUserEventObservable(login, page)
        userEventRequest(userEvent, resultCallBack)
    }

    /**
     * 获取用户接收到的事件
     */
    fun getReceivedEvent(resultCallBack: ResultCallBack<ArrayList<Any>>?, page: Int = 1) {
        val login = appGlobalModel.userObservable.login
        val username = login ?: ""
        if (username.isEmpty()) {
            return
        }
        val receivedEvent = retrofit.create(UserService::class.java)
                .getNewsEvent(true, username, page)
        userEventRequest(receivedEvent, resultCallBack)
    }

    /**
     * 执行用事件相关请求
     */
    private fun userEventRequest(observer: Observable<Response<ArrayList<Event>>>, resultCallBack: ResultCallBack<ArrayList<Any>>?) {
        val service = observer
                .flatMap {
                    FlatMapResponse2ResponseResult(it, object : FlatConversionInterface<ArrayList<Event>> {
                        override fun onConversion(t: ArrayList<Event>?): ArrayList<Any> {
                            val eventUIList = ArrayList<Any>()
                            t?.apply {
                                for (event in t) {
                                    eventUIList.add(EventConversion.eventToEventUIModel(event))
                                }
                            }
                            return eventUIList
                        }
                    })
                }
        RetrofitFactory.executeResult(service, object : ResultObserver<ArrayList<Any>>() {

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
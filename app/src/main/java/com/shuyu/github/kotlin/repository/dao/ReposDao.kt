package com.shuyu.github.kotlin.repository.dao

import android.app.Application
import com.shuyu.github.kotlin.common.db.*
import com.shuyu.github.kotlin.common.net.GsonUtils
import com.shuyu.github.kotlin.model.bean.*
import com.shuyu.github.kotlin.model.conversion.EventConversion
import com.shuyu.github.kotlin.model.conversion.IssueConversion
import com.shuyu.github.kotlin.model.conversion.ReposConversion
import com.shuyu.github.kotlin.model.ui.ReposUIModel
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmQuery
import io.realm.RealmResults
import retrofit2.Response
import javax.inject.Inject

/**
 * 仓库相关数据库操作
 * Created by guoshuyu
 * Date: 2018-11-07
 */
class ReposDao @Inject constructor(private val application: Application) {

    /**
     * 获取趋势数据
     */
    fun getTrendDao(language: String, since: String): Observable<ArrayList<Any>> {
        return RealmFactory.getRealmObservable()
                .map {
                    val list = FlatMapRealmReadList(it, object : FlatRealmReadConversionInterface<TrendingRepoModel, TrendRepository> {
                        override fun query(realm: Realm): RealmResults<TrendRepository> {
                            return realm.where(TrendRepository::class.java).equalTo("languageType", language).equalTo("since", since).findAll()
                        }

                        override fun onJSON(t: TrendRepository): List<TrendingRepoModel> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, TrendingRepoModel::class.java)
                        }

                        override fun onConversion(t: TrendingRepoModel): Any {
                            return ReposConversion.trendToReposUIModel(t)
                        }
                    })
                    list
                }
    }

    /**
     * 保存趋势数据
     */
    fun saveTrendDao(response: Response<String>, language: String, since: String, needSave: Boolean) {
        FlatMapRealmSaveResult(response, TrendRepository::class.java, object : FlatTransactionInterface<TrendRepository> {
            override fun query(q: RealmQuery<TrendRepository>): RealmResults<TrendRepository> {
                return q.equalTo("languageType", language).equalTo("since", since).findAll()
            }

            override fun onTransaction(targetObject: TrendRepository?) {
                targetObject?.data = response.body()
                targetObject?.languageType = language
                targetObject?.since = since
            }
        }, needSave)
    }

    /**
     * 保存仓库readme
     */
    fun saveReadme(response: Response<String>, userName: String, reposName: String, branch: String) {
        FlatMapRealmSaveResult(response, RepositoryDetailReadme::class.java, object : FlatTransactionInterface<RepositoryDetailReadme> {
            override fun query(q: RealmQuery<RepositoryDetailReadme>): RealmResults<RepositoryDetailReadme> {
                return q.equalTo("fullName", "$userName/$reposName").equalTo("branch", branch).findAll()
            }

            override fun onTransaction(targetObject: RepositoryDetailReadme?) {
                targetObject?.data = response.body()
                targetObject?.fullName = "$userName/$reposName"
                targetObject?.branch = branch
            }
        }, true)
    }

    /**
     * 获取仓库readme
     */
    fun getReadmeDao(userName: String, reposName: String, branch: String): Observable<String> {
        return RealmFactory.getRealmObservable()
                .map {
                    val item = FlatMapRealmReadObject(it, object : FlatRealmReadConversionObjectInterface<String, RepositoryDetailReadme, String> {
                        override fun query(realm: Realm): RealmResults<RepositoryDetailReadme> {
                            return realm.where(RepositoryDetailReadme::class.java).equalTo("fullName", "$userName/$reposName").equalTo("branch", branch).findAll()
                        }

                        override fun onJSON(t: RepositoryDetailReadme): String {
                            return t.data ?: ""
                        }

                        override fun onConversion(t: String?): String? {
                            return t ?: ""
                        }
                    })
                    item
                }
    }

    /**
     * 保存仓库信息
     */
    fun saveReposInfo(response: Response<Repository>, userName: String, reposName: String) {
        FlatMapRealmSaveResult(response, RepositoryDetail::class.java, object : FlatTransactionInterface<RepositoryDetail> {
            override fun query(q: RealmQuery<RepositoryDetail>): RealmResults<RepositoryDetail> {
                return q.equalTo("fullName", "$userName/$reposName").findAll()
            }

            override fun onTransaction(targetObject: RepositoryDetail?) {
                targetObject?.data = GsonUtils.toJsonString(response.body())
                targetObject?.fullName = "$userName/$reposName"
            }
        }, true)
    }

    /**
     * 获取仓库信息
     */
    fun getRepoInfoDao(userName: String, reposName: String): Observable<ReposUIModel> {
        return RealmFactory.getRealmObservable()
                .map {
                    val item = FlatMapRealmReadObject(it, object : FlatRealmReadConversionObjectInterface<Repository, RepositoryDetail, ReposUIModel> {
                        override fun query(realm: Realm): RealmResults<RepositoryDetail> {
                            return realm.where(RepositoryDetail::class.java).equalTo("fullName", "$userName/$reposName").findAll()
                        }

                        override fun onJSON(t: RepositoryDetail): Repository {
                            return GsonUtils.parserJsonToBean(t.data!!, Repository::class.java)
                        }

                        override fun onConversion(t: Repository?): ReposUIModel? {
                            return ReposConversion.reposToReposUIModel(application, t)
                        }
                    })
                    item
                }
    }

    /**
     * 保存仓库事件
     */
    fun saveReposEventDao(response: Response<ArrayList<Event>>, userName: String, reposName: String, needSave: Boolean) {
        FlatMapRealmSaveResult(response, RepositoryEvent::class.java, object : FlatTransactionInterface<RepositoryEvent> {
            override fun query(q: RealmQuery<RepositoryEvent>): RealmResults<RepositoryEvent> {
                return q.equalTo("fullName", "$userName/$reposName").findAll()
            }

            override fun onTransaction(targetObject: RepositoryEvent?) {
                targetObject?.data = GsonUtils.toJsonString(response.body())
                targetObject?.fullName = "$userName/$reposName"
            }
        }, needSave)
    }

    /**
     * 获取仓库事件
     */
    fun getReposEventDao(userName: String, reposName: String): Observable<ArrayList<Any>> {
        return RealmFactory.getRealmObservable()
                .map {
                    val list = FlatMapRealmReadList(it, object : FlatRealmReadConversionInterface<Event, RepositoryEvent> {
                        override fun query(realm: Realm): RealmResults<RepositoryEvent> {
                            return realm.where(RepositoryEvent::class.java).equalTo("fullName", "$userName/$reposName").findAll()
                        }

                        override fun onJSON(t: RepositoryEvent): List<Event> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, Event::class.java)
                        }

                        override fun onConversion(t: Event): Any {
                            return EventConversion.eventToEventUIModel(t)
                        }
                    })
                    list
                }
    }


    /**
     * 保存仓库提交
     */
    fun saveReposCommitDao(response: Response<ArrayList<RepoCommit>>, userName: String, reposName: String, needSave: Boolean) {
        FlatMapRealmSaveResult(response, RepositoryCommits::class.java, object : FlatTransactionInterface<RepositoryCommits> {
            override fun query(q: RealmQuery<RepositoryCommits>): RealmResults<RepositoryCommits> {
                return q.equalTo("fullName", "$userName/$reposName").findAll()
            }

            override fun onTransaction(targetObject: RepositoryCommits?) {
                targetObject?.data = GsonUtils.toJsonString(response.body())
                targetObject?.fullName = "$userName/$reposName"
            }
        }, needSave)
    }

    /**
     * 获取仓库提交
     */
    fun getReposCommitDao(userName: String, reposName: String): Observable<ArrayList<Any>> {
        return RealmFactory.getRealmObservable()
                .map {
                    val list = FlatMapRealmReadList(it, object : FlatRealmReadConversionInterface<RepoCommit, RepositoryCommits> {
                        override fun query(realm: Realm): RealmResults<RepositoryCommits> {
                            return realm.where(RepositoryCommits::class.java).equalTo("fullName", "$userName/$reposName").findAll()
                        }

                        override fun onJSON(t: RepositoryCommits): List<RepoCommit> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, RepoCommit::class.java)
                        }

                        override fun onConversion(t: RepoCommit): Any {
                            return EventConversion.commitToCommitUIModel(t)
                        }
                    })
                    list
                }
    }

    /**
     * 保存仓库Issue
     */
    fun saveReposIssue(response: Response<ArrayList<Issue>>, userName: String, reposName: String, status: String, needSave: Boolean) {
        FlatMapRealmSaveResult(response, RepositoryIssue::class.java, object : FlatTransactionInterface<RepositoryIssue> {
            override fun query(q: RealmQuery<RepositoryIssue>): RealmResults<RepositoryIssue> {
                return q.equalTo("fullName", "$userName/$reposName").equalTo("state", status).findAll()
            }

            override fun onTransaction(targetObject: RepositoryIssue?) {
                targetObject?.data = GsonUtils.toJsonString(response.body())
                targetObject?.fullName = "$userName/$reposName"
                targetObject?.state = status
            }
        }, needSave)
    }

    /**
     * 获取仓库Issue
     */
    fun getReposIssueDao(userName: String, reposName: String, status: String): Observable<ArrayList<Any>> {
        return RealmFactory.getRealmObservable()
                .map {
                    val list = FlatMapRealmReadList(it, object : FlatRealmReadConversionInterface<Issue, RepositoryIssue> {
                        override fun query(realm: Realm): RealmResults<RepositoryIssue> {
                            return realm.where(RepositoryIssue::class.java).equalTo("fullName", "$userName/$reposName")
                                    .equalTo("state", status).findAll()
                        }

                        override fun onJSON(t: RepositoryIssue): List<Issue> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, Issue::class.java)
                        }

                        override fun onConversion(t: Issue): Any {
                            return IssueConversion.issueToIssueUIModel(t)
                        }
                    })
                    list
                }
    }

    /**
     * 保存仓库的fork列表信息
     */
    fun saveReposFork(response: Response<ArrayList<Repository>>, userName: String, reposName: String, needSave: Boolean) {
        FlatMapRealmSaveResult(response, RepositoryFork::class.java, object : FlatTransactionInterface<RepositoryFork> {
            override fun query(q: RealmQuery<RepositoryFork>): RealmResults<RepositoryFork> {
                return q.equalTo("fullName", "$userName/$reposName").findAll()
            }

            override fun onTransaction(targetObject: RepositoryFork?) {
                targetObject?.data = GsonUtils.toJsonString(response.body())
                targetObject?.fullName = "$userName/$reposName"
            }
        }, needSave)
    }

    /**
     * 获取仓库的fork列表信息
     */
    fun getReposFork(userName: String, reposName: String): Observable<ArrayList<Any>> {
        return RealmFactory.getRealmObservable()
                .map {
                    val list = FlatMapRealmReadList(it, object : FlatRealmReadConversionInterface<Repository, RepositoryFork> {
                        override fun query(realm: Realm): RealmResults<RepositoryFork> {
                            return realm.where(RepositoryFork::class.java).equalTo("fullName", "$userName/$reposName").findAll()
                        }

                        override fun onJSON(t: RepositoryFork): List<Repository> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, Repository::class.java)
                        }

                        override fun onConversion(t: Repository): Any {
                            return ReposConversion.reposToReposUIModel(application, t)
                        }
                    })
                    list
                }
    }

    /**
     * 保存用户的仓库列表
     */
    fun saveUserRepos(response: Response<ArrayList<Repository>>, userName: String, sort: String, needSave: Boolean) {
        FlatMapRealmSaveResult(response, UserRepos::class.java, object : FlatTransactionInterface<UserRepos> {
            override fun query(q: RealmQuery<UserRepos>): RealmResults<UserRepos> {
                return q.equalTo("userName", userName).findAll()
            }

            override fun onTransaction(targetObject: UserRepos?) {
                targetObject?.data = GsonUtils.toJsonString(response.body())
                targetObject?.userName = userName
                targetObject?.sort = sort
            }
        }, needSave)
    }

    /**
     * 获取用户的仓库列表
     */
    fun getUserRepos(userName: String, sort: String): Observable<ArrayList<Any>> {
        return RealmFactory.getRealmObservable()
                .map {
                    val list = FlatMapRealmReadList(it, object : FlatRealmReadConversionInterface<Repository, UserRepos> {
                        override fun query(realm: Realm): RealmResults<UserRepos> {
                            return realm.where(UserRepos::class.java).equalTo("userName", userName).equalTo("sort", sort).findAll()
                        }

                        override fun onJSON(t: UserRepos): List<Repository> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, Repository::class.java)
                        }

                        override fun onConversion(t: Repository): Any {
                            return ReposConversion.reposToReposUIModel(application, t)
                        }
                    })
                    list
                }
    }

    /**
     * 保存用户Star的仓库列表
     */
    fun saveUserStarRepos(response: Response<ArrayList<Repository>>, userName: String, needSave: Boolean) {
        FlatMapRealmSaveResult(response, UserStared::class.java, object : FlatTransactionInterface<UserStared> {
            override fun query(q: RealmQuery<UserStared>): RealmResults<UserStared> {
                return q.equalTo("userName", userName).findAll()
            }

            override fun onTransaction(targetObject: UserStared?) {
                targetObject?.data = GsonUtils.toJsonString(response.body())
                targetObject?.userName = userName
            }
        }, needSave)
    }

    /**
     * 获取用户Star的仓库列表
     */
    fun getUserStarRepos(userName: String): Observable<ArrayList<Any>> {
        return RealmFactory.getRealmObservable()
                .map {
                    val list = FlatMapRealmReadList(it, object : FlatRealmReadConversionInterface<Repository, UserStared> {
                        override fun query(realm: Realm): RealmResults<UserStared> {
                            return realm.where(UserStared::class.java).equalTo("userName", userName).findAll()
                        }

                        override fun onJSON(t: UserStared): List<Repository> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, Repository::class.java)
                        }

                        override fun onConversion(t: Repository): Any {
                            return ReposConversion.reposToReposUIModel(application, t)
                        }
                    })
                    list
                }
    }
}
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
import retrofit2.Response
import javax.inject.Inject

/**
 * 仓库相关数据库操作（Room 版本）
 * Created by guoshuyu
 * Date: 2018-11-07
 */
class ReposDao @Inject constructor(private val application: Application) {

    /**
     * 获取趋势数据
     */
    fun getTrendDao(language: String, since: String): Observable<ArrayList<Any>> {
        return RoomFactory.getDatabaseObservable()
                .map { db ->
                    FlatMapRoomReadList(db, object : FlatRoomReadConversionInterface<TrendingRepoModel, TrendRepository> {
                        override fun query(db: GSYRoomDatabase): TrendRepository? {
                            return db.trendRepositoryDao().queryFirst(language, since)
                        }

                        override fun onJSON(t: TrendRepository): List<TrendingRepoModel> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, TrendingRepoModel::class.java)
                        }

                        override fun onConversion(t: TrendingRepoModel): Any {
                            return ReposConversion.trendToReposUIModel(t)
                        }
                    })
                }
    }

    /**
     * 保存趋势数据
     */
    fun saveTrendDao(response: Response<String>, language: String, since: String, needSave: Boolean) {
        FlatMapRoomSaveResult(response, { TrendRepository() }, object : FlatRoomTransactionInterface<TrendRepository> {
            override fun query(): TrendRepository? {
                return RoomFactory.database.trendRepositoryDao().queryFirst(language, since)
            }

            override fun onTransaction(targetObject: TrendRepository) {
                targetObject.data = response.body()
                targetObject.languageType = language
                targetObject.since = since
            }

            override fun insert(targetObject: TrendRepository) {
                RoomFactory.database.trendRepositoryDao().insert(targetObject)
            }

            override fun update(targetObject: TrendRepository) {
                RoomFactory.database.trendRepositoryDao().update(targetObject)
            }
        }, needSave)
    }

    /**
     * 保存仓库readme
     */
    fun saveReadme(response: Response<String>, userName: String, reposName: String, branch: String) {
        FlatMapRoomSaveResult(response, { RepositoryDetailReadme() }, object : FlatRoomTransactionInterface<RepositoryDetailReadme> {
            override fun query(): RepositoryDetailReadme? {
                return RoomFactory.database.repositoryDetailReadmeDao().queryFirst("$userName/$reposName", branch)
            }

            override fun onTransaction(targetObject: RepositoryDetailReadme) {
                targetObject.data = response.body()
                targetObject.fullName = "$userName/$reposName"
                targetObject.branch = branch
            }

            override fun insert(targetObject: RepositoryDetailReadme) {
                RoomFactory.database.repositoryDetailReadmeDao().insert(targetObject)
            }

            override fun update(targetObject: RepositoryDetailReadme) {
                RoomFactory.database.repositoryDetailReadmeDao().update(targetObject)
            }
        }, true)
    }

    /**
     * 获取仓库readme
     */
    fun getReadmeDao(userName: String, reposName: String, branch: String): Observable<String> {
        return RoomFactory.getDatabaseObservable()
                .map { db ->
                    val item = FlatMapRoomReadObject(db, object : FlatRoomReadConversionObjectInterface<String, RepositoryDetailReadme, String> {
                        override fun query(db: GSYRoomDatabase): RepositoryDetailReadme? {
                            return db.repositoryDetailReadmeDao().queryFirst("$userName/$reposName", branch)
                        }

                        override fun onJSON(t: RepositoryDetailReadme): String {
                            return t.data ?: ""
                        }

                        override fun onConversion(t: String?): String? {
                            return t ?: ""
                        }
                    })
                    item ?: ""
                }
    }

    /**
     * 保存仓库信息
     */
    fun saveReposInfo(response: Response<Repository>, userName: String, reposName: String) {
        FlatMapRoomSaveResult(response, { RepositoryDetail() }, object : FlatRoomTransactionInterface<RepositoryDetail> {
            override fun query(): RepositoryDetail? {
                return RoomFactory.database.repositoryDetailDao().queryFirst("$userName/$reposName")
            }

            override fun onTransaction(targetObject: RepositoryDetail) {
                targetObject.data = GsonUtils.toJsonString(response.body())
                targetObject.fullName = "$userName/$reposName"
            }

            override fun insert(targetObject: RepositoryDetail) {
                RoomFactory.database.repositoryDetailDao().insert(targetObject)
            }

            override fun update(targetObject: RepositoryDetail) {
                RoomFactory.database.repositoryDetailDao().update(targetObject)
            }
        }, true)
    }

    /**
     * 获取仓库信息
     */
    fun getRepoInfoDao(userName: String, reposName: String): Observable<ReposUIModel> {
        return RoomFactory.getDatabaseObservable()
                .map { db ->
                    val item = FlatMapRoomReadObject(db, object : FlatRoomReadConversionObjectInterface<Repository, RepositoryDetail, ReposUIModel> {
                        override fun query(db: GSYRoomDatabase): RepositoryDetail? {
                            return db.repositoryDetailDao().queryFirst("$userName/$reposName")
                        }

                        override fun onJSON(t: RepositoryDetail): Repository {
                            return GsonUtils.parserJsonToBean(t.data!!, Repository::class.java)
                        }

                        override fun onConversion(t: Repository?): ReposUIModel? {
                            return ReposConversion.reposToReposUIModel(application, t)
                        }
                    })
                    item ?: ReposUIModel()
                }
    }

    /**
     * 保存仓库事件
     */
    fun saveReposEventDao(response: Response<ArrayList<Event>>, userName: String, reposName: String, needSave: Boolean) {
        FlatMapRoomSaveResult(response, { RepositoryEvent() }, object : FlatRoomTransactionInterface<RepositoryEvent> {
            override fun query(): RepositoryEvent? {
                return RoomFactory.database.repositoryEventDao().queryFirst("$userName/$reposName")
            }

            override fun onTransaction(targetObject: RepositoryEvent) {
                targetObject.data = GsonUtils.toJsonString(response.body())
                targetObject.fullName = "$userName/$reposName"
            }

            override fun insert(targetObject: RepositoryEvent) {
                RoomFactory.database.repositoryEventDao().insert(targetObject)
            }

            override fun update(targetObject: RepositoryEvent) {
                RoomFactory.database.repositoryEventDao().update(targetObject)
            }
        }, needSave)
    }

    /**
     * 获取仓库事件
     */
    fun getReposEventDao(userName: String, reposName: String): Observable<ArrayList<Any>> {
        return RoomFactory.getDatabaseObservable()
                .map { db ->
                    FlatMapRoomReadList(db, object : FlatRoomReadConversionInterface<Event, RepositoryEvent> {
                        override fun query(db: GSYRoomDatabase): RepositoryEvent? {
                            return db.repositoryEventDao().queryFirst("$userName/$reposName")
                        }

                        override fun onJSON(t: RepositoryEvent): List<Event> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, Event::class.java)
                        }

                        override fun onConversion(t: Event): Any {
                            return EventConversion.eventToEventUIModel(t)
                        }
                    })
                }
    }


    /**
     * 保存仓库提交
     */
    fun saveReposCommitDao(response: Response<ArrayList<RepoCommit>>, userName: String, reposName: String, needSave: Boolean) {
        FlatMapRoomSaveResult(response, { RepositoryCommits() }, object : FlatRoomTransactionInterface<RepositoryCommits> {
            override fun query(): RepositoryCommits? {
                return RoomFactory.database.repositoryCommitsDao().queryFirst("$userName/$reposName")
            }

            override fun onTransaction(targetObject: RepositoryCommits) {
                targetObject.data = GsonUtils.toJsonString(response.body())
                targetObject.fullName = "$userName/$reposName"
            }

            override fun insert(targetObject: RepositoryCommits) {
                RoomFactory.database.repositoryCommitsDao().insert(targetObject)
            }

            override fun update(targetObject: RepositoryCommits) {
                RoomFactory.database.repositoryCommitsDao().update(targetObject)
            }
        }, needSave)
    }

    /**
     * 获取仓库提交
     */
    fun getReposCommitDao(userName: String, reposName: String): Observable<ArrayList<Any>> {
        return RoomFactory.getDatabaseObservable()
                .map { db ->
                    FlatMapRoomReadList(db, object : FlatRoomReadConversionInterface<RepoCommit, RepositoryCommits> {
                        override fun query(db: GSYRoomDatabase): RepositoryCommits? {
                            return db.repositoryCommitsDao().queryFirst("$userName/$reposName")
                        }

                        override fun onJSON(t: RepositoryCommits): List<RepoCommit> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, RepoCommit::class.java)
                        }

                        override fun onConversion(t: RepoCommit): Any {
                            return EventConversion.commitToCommitUIModel(t)
                        }
                    })
                }
    }

    /**
     * 保存仓库Issue
     */
    fun saveReposIssue(response: Response<ArrayList<Issue>>, userName: String, reposName: String, status: String, needSave: Boolean) {
        FlatMapRoomSaveResult(response, { RepositoryIssue() }, object : FlatRoomTransactionInterface<RepositoryIssue> {
            override fun query(): RepositoryIssue? {
                return RoomFactory.database.repositoryIssueDao().queryFirst("$userName/$reposName", status)
            }

            override fun onTransaction(targetObject: RepositoryIssue) {
                targetObject.data = GsonUtils.toJsonString(response.body())
                targetObject.fullName = "$userName/$reposName"
                targetObject.state = status
            }

            override fun insert(targetObject: RepositoryIssue) {
                RoomFactory.database.repositoryIssueDao().insert(targetObject)
            }

            override fun update(targetObject: RepositoryIssue) {
                RoomFactory.database.repositoryIssueDao().update(targetObject)
            }
        }, needSave)
    }

    /**
     * 获取仓库Issue
     */
    fun getReposIssueDao(userName: String, reposName: String, status: String): Observable<ArrayList<Any>> {
        return RoomFactory.getDatabaseObservable()
                .map { db ->
                    FlatMapRoomReadList(db, object : FlatRoomReadConversionInterface<Issue, RepositoryIssue> {
                        override fun query(db: GSYRoomDatabase): RepositoryIssue? {
                            return db.repositoryIssueDao().queryFirst("$userName/$reposName", status)
                        }

                        override fun onJSON(t: RepositoryIssue): List<Issue> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, Issue::class.java)
                        }

                        override fun onConversion(t: Issue): Any {
                            return IssueConversion.issueToIssueUIModel(t)
                        }
                    })
                }
    }

    /**
     * 保存仓库的fork列表信息
     */
    fun saveReposFork(response: Response<ArrayList<Repository>>, userName: String, reposName: String, needSave: Boolean) {
        FlatMapRoomSaveResult(response, { RepositoryFork() }, object : FlatRoomTransactionInterface<RepositoryFork> {
            override fun query(): RepositoryFork? {
                return RoomFactory.database.repositoryForkDao().queryFirst("$userName/$reposName")
            }

            override fun onTransaction(targetObject: RepositoryFork) {
                targetObject.data = GsonUtils.toJsonString(response.body())
                targetObject.fullName = "$userName/$reposName"
            }

            override fun insert(targetObject: RepositoryFork) {
                RoomFactory.database.repositoryForkDao().insert(targetObject)
            }

            override fun update(targetObject: RepositoryFork) {
                RoomFactory.database.repositoryForkDao().update(targetObject)
            }
        }, needSave)
    }

    /**
     * 获取仓库的fork列表信息
     */
    fun getReposFork(userName: String, reposName: String): Observable<ArrayList<Any>> {
        return RoomFactory.getDatabaseObservable()
                .map { db ->
                    FlatMapRoomReadList(db, object : FlatRoomReadConversionInterface<Repository, RepositoryFork> {
                        override fun query(db: GSYRoomDatabase): RepositoryFork? {
                            return db.repositoryForkDao().queryFirst("$userName/$reposName")
                        }

                        override fun onJSON(t: RepositoryFork): List<Repository> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, Repository::class.java)
                        }

                        override fun onConversion(t: Repository): Any {
                            return ReposConversion.reposToReposUIModel(application, t)
                        }
                    })
                }
    }

    /**
     * 保存用户的仓库列表
     */
    fun saveUserRepos(response: Response<ArrayList<Repository>>, userName: String, sort: String, needSave: Boolean) {
        FlatMapRoomSaveResult(response, { UserRepos() }, object : FlatRoomTransactionInterface<UserRepos> {
            override fun query(): UserRepos? {
                return RoomFactory.database.userReposDao().queryFirstByUser(userName)
            }

            override fun onTransaction(targetObject: UserRepos) {
                targetObject.data = GsonUtils.toJsonString(response.body())
                targetObject.userName = userName
                targetObject.sort = sort
            }

            override fun insert(targetObject: UserRepos) {
                RoomFactory.database.userReposDao().insert(targetObject)
            }

            override fun update(targetObject: UserRepos) {
                RoomFactory.database.userReposDao().update(targetObject)
            }
        }, needSave)
    }

    /**
     * 获取用户的仓库列表
     */
    fun getUserRepos(userName: String, sort: String): Observable<ArrayList<Any>> {
        return RoomFactory.getDatabaseObservable()
                .map { db ->
                    FlatMapRoomReadList(db, object : FlatRoomReadConversionInterface<Repository, UserRepos> {
                        override fun query(db: GSYRoomDatabase): UserRepos? {
                            return db.userReposDao().queryFirst(userName, sort)
                        }

                        override fun onJSON(t: UserRepos): List<Repository> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, Repository::class.java)
                        }

                        override fun onConversion(t: Repository): Any {
                            return ReposConversion.reposToReposUIModel(application, t)
                        }
                    })
                }
    }

    /**
     * 保存用户Star的仓库列表
     */
    fun saveUserStarRepos(response: Response<ArrayList<Repository>>, userName: String, needSave: Boolean) {
        FlatMapRoomSaveResult(response, { UserStared() }, object : FlatRoomTransactionInterface<UserStared> {
            override fun query(): UserStared? {
                return RoomFactory.database.userStaredDao().queryFirstByUser(userName)
            }

            override fun onTransaction(targetObject: UserStared) {
                targetObject.data = GsonUtils.toJsonString(response.body())
                targetObject.userName = userName
            }

            override fun insert(targetObject: UserStared) {
                RoomFactory.database.userStaredDao().insert(targetObject)
            }

            override fun update(targetObject: UserStared) {
                RoomFactory.database.userStaredDao().update(targetObject)
            }
        }, needSave)
    }

    /**
     * 获取用户Star的仓库列表
     */
    fun getUserStarRepos(userName: String): Observable<ArrayList<Any>> {
        return RoomFactory.getDatabaseObservable()
                .map { db ->
                    FlatMapRoomReadList(db, object : FlatRoomReadConversionInterface<Repository, UserStared> {
                        override fun query(db: GSYRoomDatabase): UserStared? {
                            return db.userStaredDao().queryFirstByUser(userName)
                        }

                        override fun onJSON(t: UserStared): List<Repository> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, Repository::class.java)
                        }

                        override fun onConversion(t: Repository): Any {
                            return ReposConversion.reposToReposUIModel(application, t)
                        }
                    })
                }
    }
}

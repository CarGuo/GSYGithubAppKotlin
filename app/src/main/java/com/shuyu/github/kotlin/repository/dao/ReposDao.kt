package com.shuyu.github.kotlin.repository.dao

import android.app.Application
import com.shuyu.github.kotlin.common.db.*
import com.shuyu.github.kotlin.common.net.GsonUtils
import com.shuyu.github.kotlin.model.bean.Repository
import com.shuyu.github.kotlin.model.bean.TrendingRepoModel
import com.shuyu.github.kotlin.model.conversion.ReposConversion
import com.shuyu.github.kotlin.model.ui.ReposUIModel
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmQuery
import io.realm.RealmResults
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by guoshuyu
 * Date: 2018-11-07
 */
class ReposDao @Inject constructor(private val application: Application) {


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
}
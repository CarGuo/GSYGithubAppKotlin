package com.shuyu.github.kotlin.repository.dao

import android.app.Application
import com.shuyu.github.kotlin.common.db.*
import com.shuyu.github.kotlin.common.net.GsonUtils
import com.shuyu.github.kotlin.model.bean.Issue
import com.shuyu.github.kotlin.model.bean.IssueEvent
import com.shuyu.github.kotlin.model.conversion.IssueConversion
import com.shuyu.github.kotlin.model.ui.IssueUIModel
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
class IssueDao @Inject constructor(private val application: Application) {

    fun saveIssueInfoDao(response: Response<Issue>, userName: String, reposName: String, number: Int) {
        FlatMapRealmSaveResult(response, IssueDetail::class.java, object : FlatTransactionInterface<IssueDetail> {
            override fun query(q: RealmQuery<IssueDetail>): RealmResults<IssueDetail> {
                return q.equalTo("fullName", "$userName/$reposName").equalTo("number", number.toString()).findAll()
            }

            override fun onTransaction(targetObject: IssueDetail?) {
                val data = GsonUtils.toJsonString(response.body())
                targetObject?.fullName = "$userName/$reposName"
                targetObject?.number = number.toString()
                targetObject?.data = data
            }
        }, true)
    }

    fun getIssueInfoDao(userName: String, reposName: String, number: Int): Observable<IssueUIModel?> {
        return RealmFactory.getRealmObservable()
                .map {
                    val item = FlatMapRealmReadObject(it, object : FlatRealmReadConversionObjectInterface<Issue, IssueDetail, IssueUIModel> {
                        override fun query(realm: Realm): RealmResults<IssueDetail> {
                            return realm.where(IssueDetail::class.java).equalTo("fullName", "$userName/$reposName").equalTo("number", number.toString()).findAll()
                        }

                        override fun onJSON(t: IssueDetail): Issue {
                            return GsonUtils.parserJsonToBean(t.data!!, Issue::class.java)
                        }

                        override fun onConversion(t: Issue?): IssueUIModel? {
                            return if (t == null) {
                                IssueUIModel()
                            } else {
                                IssueConversion.issueToIssueUIModel(t)
                            }
                        }
                    })
                    item
                }
    }

    fun saveIssueCommentDao(response: Response<ArrayList<IssueEvent>>, userName: String, reposName: String, number: Int, needSave: Boolean) {
        FlatMapRealmSaveResult(response, IssueComment::class.java, object : FlatTransactionInterface<IssueComment> {
            override fun query(q: RealmQuery<IssueComment>): RealmResults<IssueComment> {
                return q.equalTo("fullName", "$userName/$reposName").equalTo("number", number.toString()).findAll()
            }

            override fun onTransaction(targetObject: IssueComment?) {
                val data = GsonUtils.toJsonString(response.body())
                targetObject?.fullName = "$userName/$reposName"
                targetObject?.number = number.toString()
                targetObject?.commentId = "-1"
                targetObject?.data = data
            }
        }, needSave)
    }

    fun getIssueCommentDao(userName: String, reposName: String, number: Int): Observable<ArrayList<Any>> {
        return RealmFactory.getRealmObservable()
                .map {
                    val list = FlatMapRealmReadList(it, object : FlatRealmReadConversionInterface<IssueEvent, IssueComment> {
                        override fun query(realm: Realm): RealmResults<IssueComment> {
                            return realm.where(IssueComment::class.java).equalTo("fullName", "$userName/$reposName").equalTo("number", number.toString()).findAll()
                        }

                        override fun onJSON(t: IssueComment): List<IssueEvent> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, IssueEvent::class.java)
                        }

                        override fun onConversion(t: IssueEvent): Any {
                            return IssueConversion.issueEventToIssueUIModel(t)
                        }
                    })
                    list
                }
    }


}
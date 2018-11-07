package com.shuyu.github.kotlin.repository.dao

import android.app.Application
import com.shuyu.github.kotlin.common.db.*
import com.shuyu.github.kotlin.common.net.GsonUtils
import com.shuyu.github.kotlin.model.bean.Event
import com.shuyu.github.kotlin.model.bean.User
import com.shuyu.github.kotlin.model.conversion.EventConversion
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
class UserDao @Inject constructor(private val application: Application) {

    fun saveReceivedEventDao(response: Response<ArrayList<Event>>, needSave: Boolean) {
        FlatMapRealmSaveResult(response, ReceivedEvent::class.java, object : FlatTransactionInterface<ReceivedEvent> {
            override fun query(q: RealmQuery<ReceivedEvent>): RealmResults<ReceivedEvent> {
                return q.findAll()
            }

            override fun onTransaction(targetObject: ReceivedEvent?) {
                val data = GsonUtils.toJsonString(response.body())
                targetObject?.data = data
            }
        }, needSave)
    }

    /**
     * 获取用户接收到的事件
     */
    fun getReceivedEventDao(): Observable<ArrayList<Any>> {
        return RealmFactory.getRealmObservable()
                .map {
                    val list = FlatMapRealmReadList(it, object : FlatRealmReadConversionInterface<Event, ReceivedEvent> {
                        override fun query(realm: Realm): RealmResults<ReceivedEvent> {
                            return realm.where(ReceivedEvent::class.java).findAll()
                        }

                        override fun onJSON(t: ReceivedEvent): List<Event> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, Event::class.java)
                        }

                        override fun onConversion(t: Event): Any {
                            return EventConversion.eventToEventUIModel(t)
                        }
                    })
                    list
                }
    }

    fun getUserEventDao(userName: String): Observable<ArrayList<Any>> {
        return RealmFactory.getRealmObservable()
                .map {
                    val list = FlatMapRealmReadList(it, object : FlatRealmReadConversionInterface<Event, UserEvent> {
                        override fun query(realm: Realm): RealmResults<UserEvent> {
                            return realm.where(UserEvent::class.java).equalTo("userName", userName).findAll()
                        }

                        override fun onJSON(t: UserEvent): List<Event> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, Event::class.java)
                        }

                        override fun onConversion(t: Event): Any {
                            return EventConversion.eventToEventUIModel(t)
                        }
                    })
                    list
                }
    }


    fun saveUserEventDao(response: Response<ArrayList<Event>>, userName: String, needSave: Boolean) {
        FlatMapRealmSaveResult(response, UserEvent::class.java, object : FlatTransactionInterface<UserEvent> {
            override fun query(q: RealmQuery<UserEvent>): RealmResults<UserEvent> {
                return q.equalTo("userName", userName).findAll()
            }

            override fun onTransaction(targetObject: UserEvent?) {
                val data = GsonUtils.toJsonString(response.body())
                targetObject?.userName = userName
                targetObject?.data = data
            }
        }, needSave)
    }

    fun getUserInfoDao(userName: String?): Observable<User?> {
        return RealmFactory.getRealmObservable()
                .map {
                    val result = it.where(UserInfo::class.java).equalTo("userName",
                            userName ?: "").findAll()
                    val item = if (result.isEmpty()) {
                        User()
                    } else {
                        GsonUtils.parserJsonToBean(result[0]!!.data!!, User::class.java)
                    }
                    item
                }
    }


    fun saveUserInfo(response: Response<User>, userName: String) {
        FlatMapRealmSaveResult(response, UserInfo::class.java, object : FlatTransactionInterface<UserInfo> {
            override fun query(q: RealmQuery<UserInfo>): RealmResults<UserInfo> {
                return q.equalTo("userName", userName).findAll()
            }

            override fun onTransaction(targetObject: UserInfo?) {
                val data = GsonUtils.toJsonString(response.body())
                targetObject?.userName = userName
                targetObject?.data = data
            }
        }, true)
    }

}
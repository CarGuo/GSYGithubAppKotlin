package com.shuyu.github.kotlin.repository.dao

import android.app.Application
import com.shuyu.github.kotlin.common.db.*
import com.shuyu.github.kotlin.common.net.GsonUtils
import com.shuyu.github.kotlin.model.bean.Event
import com.shuyu.github.kotlin.model.bean.User
import com.shuyu.github.kotlin.model.conversion.EventConversion
import com.shuyu.github.kotlin.model.conversion.UserConversion
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
    fun saveOrgMembersDao(response: Response<ArrayList<User>>, userName: String, needSave: Boolean) {
        FlatMapRealmSaveResult(response, OrgMember::class.java, object : FlatTransactionInterface<OrgMember> {
            override fun query(q: RealmQuery<OrgMember>): RealmResults<OrgMember> {
                return q.equalTo("org", userName).findAll()
            }

            override fun onTransaction(targetObject: OrgMember?) {
                val data = GsonUtils.toJsonString(response.body())
                targetObject?.org = userName
                targetObject?.data = data
            }
        }, needSave)
    }

    fun getOrgMembersDao(userName: String?): Observable<ArrayList<Any>> {
        return RealmFactory.getRealmObservable()
                .map {
                    val list = FlatMapRealmReadList(it, object : FlatRealmReadConversionInterface<User, OrgMember> {
                        override fun query(realm: Realm): RealmResults<OrgMember> {
                            return realm.where(OrgMember::class.java).equalTo("org", userName).findAll()
                        }

                        override fun onJSON(t: OrgMember): List<User> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, User::class.java)
                        }

                        override fun onConversion(t: User): Any {
                            return UserConversion.userToUserUIModel(t)
                        }
                    })
                    list
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


    fun saveUserFollowerDao(userName: String, response: Response<ArrayList<User>>, needSave: Boolean) {
        FlatMapRealmSaveResult(response, UserFollower::class.java, object : FlatTransactionInterface<UserFollower> {
            override fun query(q: RealmQuery<UserFollower>): RealmResults<UserFollower> {
                return q.equalTo("userName", userName).findAll()
            }

            override fun onTransaction(targetObject: UserFollower?) {
                targetObject?.data = GsonUtils.toJsonString(response.body())
                targetObject?.userName = userName
            }
        }, needSave)
    }

    fun getUserFollowerDao(userName: String): Observable<ArrayList<Any>> {
        return RealmFactory.getRealmObservable()
                .map {
                    val list = FlatMapRealmReadList(it, object : FlatRealmReadConversionInterface<User, UserFollower> {
                        override fun query(realm: Realm): RealmResults<UserFollower> {
                            return realm.where(UserFollower::class.java).equalTo("userName", userName).findAll()
                        }

                        override fun onJSON(t: UserFollower): List<User> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, User::class.java)
                        }

                        override fun onConversion(t: User): Any {
                            return UserConversion.userToUserUIModel(t)
                        }
                    })
                    list
                }
    }

    fun saveUserFollowedDao(userName: String, response: Response<ArrayList<User>>, needSave: Boolean) {
        FlatMapRealmSaveResult(response, UserFollowed::class.java, object : FlatTransactionInterface<UserFollowed> {
            override fun query(q: RealmQuery<UserFollowed>): RealmResults<UserFollowed> {
                return q.equalTo("userName", userName).findAll()
            }

            override fun onTransaction(targetObject: UserFollowed?) {
                targetObject?.data = GsonUtils.toJsonString(response.body())
                targetObject?.userName = userName
            }
        }, needSave)
    }

    fun getUserFollowedDao(userName: String): Observable<ArrayList<Any>> {
        return RealmFactory.getRealmObservable()
                .map {
                    val list = FlatMapRealmReadList(it, object : FlatRealmReadConversionInterface<User, UserFollowed> {
                        override fun query(realm: Realm): RealmResults<UserFollowed> {
                            return realm.where(UserFollowed::class.java).equalTo("userName", userName).findAll()
                        }

                        override fun onJSON(t: UserFollowed): List<User> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, User::class.java)
                        }

                        override fun onConversion(t: User): Any {
                            return UserConversion.userToUserUIModel(t)
                        }
                    })
                    list
                }
    }

    fun saveRepositoryStarUserDao(userName: String, reposName: String, response: Response<ArrayList<User>>, needSave: Boolean) {
        FlatMapRealmSaveResult(response, RepositoryStar::class.java, object : FlatTransactionInterface<RepositoryStar> {
            override fun query(q: RealmQuery<RepositoryStar>): RealmResults<RepositoryStar> {
                return q.equalTo("fullName", "$userName/$reposName").findAll()
            }

            override fun onTransaction(targetObject: RepositoryStar?) {
                targetObject?.data = GsonUtils.toJsonString(response.body())
                targetObject?.fullName = "$userName/$reposName"
            }
        }, needSave)
    }


    fun getRepositoryStarUserDao(userName: String, reposName: String): Observable<ArrayList<Any>> {
        return RealmFactory.getRealmObservable()
                .map {
                    val list = FlatMapRealmReadList(it, object : FlatRealmReadConversionInterface<User, RepositoryStar> {
                        override fun query(realm: Realm): RealmResults<RepositoryStar> {
                            return realm.where(RepositoryStar::class.java).equalTo("fullName", "$userName/$reposName").findAll()
                        }

                        override fun onJSON(t: RepositoryStar): List<User> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, User::class.java)
                        }

                        override fun onConversion(t: User): Any {
                            return UserConversion.userToUserUIModel(t)
                        }
                    })
                    list
                }
    }

    fun saveRepositoryWatchUserDao(userName: String, reposName: String, response: Response<ArrayList<User>>, needSave: Boolean) {
        FlatMapRealmSaveResult(response, RepositoryWatcher::class.java, object : FlatTransactionInterface<RepositoryWatcher> {
            override fun query(q: RealmQuery<RepositoryWatcher>): RealmResults<RepositoryWatcher> {
                return q.equalTo("fullName", "$userName/$reposName").findAll()
            }

            override fun onTransaction(targetObject: RepositoryWatcher?) {
                targetObject?.data = GsonUtils.toJsonString(response.body())
                targetObject?.fullName = "$userName/$reposName"
            }
        }, needSave)
    }

    fun getRepositoryWatchUserDao(userName: String, reposName: String): Observable<ArrayList<Any>> {
        return RealmFactory.getRealmObservable()
                .map {
                    val list = FlatMapRealmReadList(it, object : FlatRealmReadConversionInterface<User, RepositoryWatcher> {
                        override fun query(realm: Realm): RealmResults<RepositoryWatcher> {
                            return realm.where(RepositoryWatcher::class.java).equalTo("fullName", "$userName/$reposName").findAll()
                        }

                        override fun onJSON(t: RepositoryWatcher): List<User> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, User::class.java)
                        }

                        override fun onConversion(t: User): Any {
                            return UserConversion.userToUserUIModel(t)
                        }
                    })
                    list
                }
    }
}
package com.shuyu.github.kotlin.repository.dao

import android.app.Application
import com.shuyu.github.kotlin.common.db.*
import com.shuyu.github.kotlin.common.net.GsonUtils
import com.shuyu.github.kotlin.model.bean.Event
import com.shuyu.github.kotlin.model.bean.User
import com.shuyu.github.kotlin.model.conversion.EventConversion
import com.shuyu.github.kotlin.model.conversion.UserConversion
import io.reactivex.Observable
import retrofit2.Response
import javax.inject.Inject

/**
 * 用户相关数据库操作（Room 版本）
 * Created by guoshuyu
 * Date: 2018-11-07
 */
class UserDao @Inject constructor(private val application: Application) {

    /**
     * 保存当前用户接收到的事件
     */
    fun saveReceivedEventDao(response: Response<ArrayList<Event>>, needSave: Boolean) {
        FlatMapRoomSaveResult(response, { ReceivedEvent() }, object : FlatRoomTransactionInterface<ReceivedEvent> {
            override fun query(): ReceivedEvent? {
                return RoomFactory.database.receivedEventDao().queryFirst()
            }

            override fun onTransaction(targetObject: ReceivedEvent) {
                targetObject.data = GsonUtils.toJsonString(response.body())
            }

            override fun insert(targetObject: ReceivedEvent) {
                RoomFactory.database.receivedEventDao().insert(targetObject)
            }

            override fun update(targetObject: ReceivedEvent) {
                RoomFactory.database.receivedEventDao().update(targetObject)
            }
        }, needSave)
    }

    /**
     * 获取当前用户接收到的事件
     */
    fun getReceivedEventDao(): Observable<ArrayList<Any>> {
        return RoomFactory.getDatabaseObservable()
                .map { db ->
                    FlatMapRoomReadList(db, object : FlatRoomReadConversionInterface<Event, ReceivedEvent> {
                        override fun query(db: GSYRoomDatabase): ReceivedEvent? {
                            return db.receivedEventDao().queryFirst()
                        }

                        override fun onJSON(t: ReceivedEvent): List<Event> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, Event::class.java)
                        }

                        override fun onConversion(t: Event): Any {
                            return EventConversion.eventToEventUIModel(t)
                        }
                    })
                }
    }

    /**
     * 获取用户的行为信息
     */
    fun getUserEventDao(userName: String): Observable<ArrayList<Any>> {
        return RoomFactory.getDatabaseObservable()
                .map { db ->
                    FlatMapRoomReadList(db, object : FlatRoomReadConversionInterface<Event, UserEvent> {
                        override fun query(db: GSYRoomDatabase): UserEvent? {
                            return db.userEventDao().queryFirst(userName)
                        }

                        override fun onJSON(t: UserEvent): List<Event> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, Event::class.java)
                        }

                        override fun onConversion(t: Event): Any {
                            return EventConversion.eventToEventUIModel(t)
                        }
                    })
                }
    }

    /**
     * 保存用户的行为信息
     */
    fun saveUserEventDao(response: Response<ArrayList<Event>>, userName: String, needSave: Boolean) {
        FlatMapRoomSaveResult(response, { UserEvent() }, object : FlatRoomTransactionInterface<UserEvent> {
            override fun query(): UserEvent? {
                return RoomFactory.database.userEventDao().queryFirst(userName)
            }

            override fun onTransaction(targetObject: UserEvent) {
                targetObject.userName = userName
                targetObject.data = GsonUtils.toJsonString(response.body())
            }

            override fun insert(targetObject: UserEvent) {
                RoomFactory.database.userEventDao().insert(targetObject)
            }

            override fun update(targetObject: UserEvent) {
                RoomFactory.database.userEventDao().update(targetObject)
            }
        }, needSave)
    }

    /**
     * 获取用户的信息
     */
    fun getUserInfoDao(userName: String?): Observable<User?> {
        return RoomFactory.getDatabaseObservable()
                .map { db ->
                    val result = db.userInfoDao().queryFirst(userName ?: "")
                    val item: User = if (result == null || result.data.isNullOrEmpty()) {
                        User()
                    } else {
                        GsonUtils.parserJsonToBean(result.data!!, User::class.java)
                    }
                    item
                }
    }

    /**
     * 保存用户的信息
     */
    fun saveOrgMembersDao(response: Response<ArrayList<User>>, userName: String, needSave: Boolean) {
        FlatMapRoomSaveResult(response, { OrgMember() }, object : FlatRoomTransactionInterface<OrgMember> {
            override fun query(): OrgMember? {
                return RoomFactory.database.orgMemberDao().queryFirst(userName)
            }

            override fun onTransaction(targetObject: OrgMember) {
                targetObject.org = userName
                targetObject.data = GsonUtils.toJsonString(response.body())
            }

            override fun insert(targetObject: OrgMember) {
                RoomFactory.database.orgMemberDao().insert(targetObject)
            }

            override fun update(targetObject: OrgMember) {
                RoomFactory.database.orgMemberDao().update(targetObject)
            }
        }, needSave)
    }

    /**
     * 获取组织的成员信息
     */
    fun getOrgMembersDao(userName: String?): Observable<ArrayList<Any>> {
        return RoomFactory.getDatabaseObservable()
                .map { db ->
                    FlatMapRoomReadList(db, object : FlatRoomReadConversionInterface<User, OrgMember> {
                        override fun query(db: GSYRoomDatabase): OrgMember? {
                            return db.orgMemberDao().queryFirst(userName ?: "")
                        }

                        override fun onJSON(t: OrgMember): List<User> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, User::class.java)
                        }

                        override fun onConversion(t: User): Any {
                            return UserConversion.userToUserUIModel(t)
                        }
                    })
                }
    }


    /**
     * 保存组织的成员信息
     */
    fun saveUserInfo(response: Response<User>, userName: String) {
        FlatMapRoomSaveResult(response, { UserInfo() }, object : FlatRoomTransactionInterface<UserInfo> {
            override fun query(): UserInfo? {
                return RoomFactory.database.userInfoDao().queryFirst(userName)
            }

            override fun onTransaction(targetObject: UserInfo) {
                targetObject.userName = userName
                targetObject.data = GsonUtils.toJsonString(response.body())
            }

            override fun insert(targetObject: UserInfo) {
                RoomFactory.database.userInfoDao().insert(targetObject)
            }

            override fun update(targetObject: UserInfo) {
                RoomFactory.database.userInfoDao().update(targetObject)
            }
        }, true)
    }

    /**
     * 保存用户的粉丝信息
     */
    fun saveUserFollowerDao(userName: String, response: Response<ArrayList<User>>, needSave: Boolean) {
        FlatMapRoomSaveResult(response, { UserFollower() }, object : FlatRoomTransactionInterface<UserFollower> {
            override fun query(): UserFollower? {
                return RoomFactory.database.userFollowerDao().queryFirst(userName)
            }

            override fun onTransaction(targetObject: UserFollower) {
                targetObject.data = GsonUtils.toJsonString(response.body())
                targetObject.userName = userName
            }

            override fun insert(targetObject: UserFollower) {
                RoomFactory.database.userFollowerDao().insert(targetObject)
            }

            override fun update(targetObject: UserFollower) {
                RoomFactory.database.userFollowerDao().update(targetObject)
            }
        }, needSave)
    }

    /**
     * 获取用户的粉丝信息
     */
    fun getUserFollowerDao(userName: String): Observable<ArrayList<Any>> {
        return RoomFactory.getDatabaseObservable()
                .map { db ->
                    FlatMapRoomReadList(db, object : FlatRoomReadConversionInterface<User, UserFollower> {
                        override fun query(db: GSYRoomDatabase): UserFollower? {
                            return db.userFollowerDao().queryFirst(userName)
                        }

                        override fun onJSON(t: UserFollower): List<User> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, User::class.java)
                        }

                        override fun onConversion(t: User): Any {
                            return UserConversion.userToUserUIModel(t)
                        }
                    })
                }
    }

    /**
     * 保存用户的关注信息
     */
    fun saveUserFollowedDao(userName: String, response: Response<ArrayList<User>>, needSave: Boolean) {
        FlatMapRoomSaveResult(response, { UserFollowed() }, object : FlatRoomTransactionInterface<UserFollowed> {
            override fun query(): UserFollowed? {
                return RoomFactory.database.userFollowedDao().queryFirst(userName)
            }

            override fun onTransaction(targetObject: UserFollowed) {
                targetObject.data = GsonUtils.toJsonString(response.body())
                targetObject.userName = userName
            }

            override fun insert(targetObject: UserFollowed) {
                RoomFactory.database.userFollowedDao().insert(targetObject)
            }

            override fun update(targetObject: UserFollowed) {
                RoomFactory.database.userFollowedDao().update(targetObject)
            }
        }, needSave)
    }

    /**
     * 获取用户的关注信息
     */
    fun getUserFollowedDao(userName: String): Observable<ArrayList<Any>> {
        return RoomFactory.getDatabaseObservable()
                .map { db ->
                    FlatMapRoomReadList(db, object : FlatRoomReadConversionInterface<User, UserFollowed> {
                        override fun query(db: GSYRoomDatabase): UserFollowed? {
                            return db.userFollowedDao().queryFirst(userName)
                        }

                        override fun onJSON(t: UserFollowed): List<User> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, User::class.java)
                        }

                        override fun onConversion(t: User): Any {
                            return UserConversion.userToUserUIModel(t)
                        }
                    })
                }
    }


    /**
     * 保存仓库的star用户信息
     */
    fun saveRepositoryStarUserDao(userName: String, reposName: String, response: Response<ArrayList<User>>, needSave: Boolean) {
        FlatMapRoomSaveResult(response, { RepositoryStar() }, object : FlatRoomTransactionInterface<RepositoryStar> {
            override fun query(): RepositoryStar? {
                return RoomFactory.database.repositoryStarDao().queryFirst("$userName/$reposName")
            }

            override fun onTransaction(targetObject: RepositoryStar) {
                targetObject.data = GsonUtils.toJsonString(response.body())
                targetObject.fullName = "$userName/$reposName"
            }

            override fun insert(targetObject: RepositoryStar) {
                RoomFactory.database.repositoryStarDao().insert(targetObject)
            }

            override fun update(targetObject: RepositoryStar) {
                RoomFactory.database.repositoryStarDao().update(targetObject)
            }
        }, needSave)
    }


    /**
     * 获取仓库的star用户信息
     */
    fun getRepositoryStarUserDao(userName: String, reposName: String): Observable<ArrayList<Any>> {
        return RoomFactory.getDatabaseObservable()
                .map { db ->
                    FlatMapRoomReadList(db, object : FlatRoomReadConversionInterface<User, RepositoryStar> {
                        override fun query(db: GSYRoomDatabase): RepositoryStar? {
                            return db.repositoryStarDao().queryFirst("$userName/$reposName")
                        }

                        override fun onJSON(t: RepositoryStar): List<User> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, User::class.java)
                        }

                        override fun onConversion(t: User): Any {
                            return UserConversion.userToUserUIModel(t)
                        }
                    })
                }
    }

    /**
     * 保存仓库的订阅用户信息
     */
    fun saveRepositoryWatchUserDao(userName: String, reposName: String, response: Response<ArrayList<User>>, needSave: Boolean) {
        FlatMapRoomSaveResult(response, { RepositoryWatcher() }, object : FlatRoomTransactionInterface<RepositoryWatcher> {
            override fun query(): RepositoryWatcher? {
                return RoomFactory.database.repositoryWatcherDao().queryFirst("$userName/$reposName")
            }

            override fun onTransaction(targetObject: RepositoryWatcher) {
                targetObject.data = GsonUtils.toJsonString(response.body())
                targetObject.fullName = "$userName/$reposName"
            }

            override fun insert(targetObject: RepositoryWatcher) {
                RoomFactory.database.repositoryWatcherDao().insert(targetObject)
            }

            override fun update(targetObject: RepositoryWatcher) {
                RoomFactory.database.repositoryWatcherDao().update(targetObject)
            }
        }, needSave)
    }

    /**
     * 获取仓库的定于用户信息
     */
    fun getRepositoryWatchUserDao(userName: String, reposName: String): Observable<ArrayList<Any>> {
        return RoomFactory.getDatabaseObservable()
                .map { db ->
                    FlatMapRoomReadList(db, object : FlatRoomReadConversionInterface<User, RepositoryWatcher> {
                        override fun query(db: GSYRoomDatabase): RepositoryWatcher? {
                            return db.repositoryWatcherDao().queryFirst("$userName/$reposName")
                        }

                        override fun onJSON(t: RepositoryWatcher): List<User> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, User::class.java)
                        }

                        override fun onConversion(t: User): Any {
                            return UserConversion.userToUserUIModel(t)
                        }
                    })
                }
    }
}

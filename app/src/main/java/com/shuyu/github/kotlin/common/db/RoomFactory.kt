package com.shuyu.github.kotlin.common.db

import android.content.Context
import androidx.room.Room
import io.reactivex.Observable

/**
 * Room 数据库工厂单例
 * 替代原 RealmFactory：
 *  - init(context) 在 Application 启动时调用一次
 *  - database 暴露 Room 实例
 *  - getDatabaseObservable() 用于 RxJava 链路，与原 RealmFactory.getRealmObservable() 用法一致
 */
class RoomFactory private constructor() {

    companion object {

        private const val DB_NAME = "gsy.room"

        @Volatile
        private var instance: RoomFactory? = null

        @Volatile
        private var db: GSYRoomDatabase? = null

        @JvmStatic
        fun init(context: Context) {
            if (instance == null) {
                synchronized(RoomFactory::class.java) {
                    if (instance == null) {
                        instance = RoomFactory()
                        db = Room.databaseBuilder(
                            context.applicationContext,
                            GSYRoomDatabase::class.java,
                            DB_NAME
                        )
                            // KV 缓存表，丢失旧数据可接受。
                            // Room 2.7+ 起 fallbackToDestructiveMigration() 已 deprecated，
                            // 当前固定 room_version = 2.6.1，仍使用无参版本；升级到 2.7 时
                            // 改为 fallbackToDestructiveMigration(dropAllTables = true)。
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
        }

        @JvmStatic
        fun getInstance(): RoomFactory {
            return instance
                ?: throw IllegalStateException("RoomFactory not initialized, call RoomFactory.init(context) first")
        }

        @JvmStatic
        val database: GSYRoomDatabase
            get() = db
                ?: throw IllegalStateException("RoomFactory not initialized, call RoomFactory.init(context) first")

        /**
         * 与原 RealmFactory.getRealmObservable() 用法对齐，
         * 在订阅时同步发出当前 Room 数据库实例。
         */
        @JvmStatic
        fun getDatabaseObservable(): Observable<GSYRoomDatabase> {
            return Observable.create { emitter ->
                emitter.onNext(database)
                emitter.onComplete()
            }
        }
    }
}

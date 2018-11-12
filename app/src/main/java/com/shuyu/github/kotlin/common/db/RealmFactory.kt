package com.shuyu.github.kotlin.common.db

import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Realm数据初始化
 * Created by guoshuyu
 * Date: 2018-11-06
 */

class RealmFactory private constructor() {

    companion object {

        private const val VERSION = 1L

        @Volatile
        private var mRealmFactory: RealmFactory? = null

        val instance: RealmFactory
            get() {
                if (mRealmFactory == null) {
                    synchronized(RealmFactory::class.java) {
                        if (mRealmFactory == null)
                            mRealmFactory = RealmFactory()
                    }

                }
                return mRealmFactory!!
            }

        fun getRealmObservable(): Observable<Realm> {
            return Observable.create { emitter ->
                val observableRealm = Realm.getDefaultInstance()
                emitter.onNext(observableRealm)
                emitter.onComplete()
            }
        }
    }

    init {
        val config = RealmConfiguration.Builder()
                .name("gsy.realm")
                .schemaVersion(VERSION)
                .build()
        Realm.setDefaultConfiguration(config)
    }
}
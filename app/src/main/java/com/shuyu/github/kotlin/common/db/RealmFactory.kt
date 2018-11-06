package com.shuyu.github.kotlin.common.db

import io.realm.Realm
import io.realm.RealmConfiguration

/**
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
    }

    val realm: Realm

    init {
        val config = RealmConfiguration.Builder()
                .name("gsy.realm")
                .schemaVersion(VERSION)
                .build()
        realm = Realm.getInstance(config)
    }
}
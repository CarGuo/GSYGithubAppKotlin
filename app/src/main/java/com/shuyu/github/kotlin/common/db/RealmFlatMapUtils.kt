package com.shuyu.github.kotlin.common.db

import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.realm.Realm
import io.realm.RealmModel
import retrofit2.Response

/**
 * Created by guoshuyu
 * Date: 2018-11-06
 */

class FlatMapRealmSaveResult<T, E : RealmModel>(private val response: Response<T>, private val clazz: Class<E>, private val listener: FlatTransactionInterface<E>, private val needSave: Boolean) : ObservableSource<Response<T>> {
    override fun subscribe(observer: Observer<in Response<T>?>) {
        if (response.isSuccessful && needSave) {
            val realm = Realm.getDefaultInstance()
            realm.executeTransaction { bgRealm ->
                val results = bgRealm.where(clazz).findAll()
                val commitTarget = if (results.isNotEmpty()) {
                    results[0]
                } else {
                    bgRealm.createObject(clazz)
                }
                listener.onTransaction(commitTarget)

                bgRealm.close()
            }
        }
        observer.onNext(response)
    }
}

interface FlatTransactionInterface<E : RealmModel> {
    fun onTransaction(targetObject: E?)
}


fun <T, E : RealmModel> FlatMapRealmReadList(realm: Realm, clazz: Class<E>, listener: FlatRealmReadConversionInterface<T, E>): ArrayList<Any> {
    val realmResults = realm.where(clazz).findAll()
    val list = if (realmResults.isEmpty()) {
        ArrayList()
    } else {
        listener.onJSON(realmResults[0])
    }
    val dataList = ArrayList<Any>()
    for (item in list) {
        dataList.add(listener.onConversion(item))
    }
    return dataList
}

interface FlatRealmReadConversionInterface<T, E> {
    fun onJSON(t: E?): List<T>
    fun onConversion(t: T?): Any
}
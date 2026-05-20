package com.shuyu.github.kotlin.common.db

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

/**
 * Room 版本的 FlatMap 工具，对外语义与原 RealmFlatMapUtils 保持一致：
 *  - 写入：response 成功时，按查询条件取第一条，若不存在则新建对象，
 *    然后由调用方在 onTransaction 中填充字段，最后由本工具统一执行 insert / update。
 *  - 读取列表：以查询拿到的第一条记录的 data 为基础解析得到列表，
 *    并对每个元素调用 onConversion 得到 UI 模型。
 *  - 读取实体：以查询拿到的第一条记录的 data 为基础解析得到实体，
 *    再调用 onConversion 得到 UI 模型。
 *
 * 写入操作放在 Schedulers.io 的子线程中执行，避免阻塞主线程
 * （Room 默认禁止主线程 IO，必须在子线程执行）。
 */

/**
 * 保存 response 中的实体信息。
 * 注意：构造时立即调度到子线程执行，以保持与原 FlatMapRealmSaveResult 的“构造即生效”语义。
 */
class FlatMapRoomSaveResult<T, E : Any>(
    response: Response<T>,
    private val entityFactory: () -> E,
    private val listener: FlatRoomTransactionInterface<E>,
    needSave: Boolean
) {
    init {
        if (response.isSuccessful && needSave) {
            Observable.fromCallable {
                val existed = listener.query()
                val isNew = existed == null
                val target = existed ?: entityFactory()
                listener.onTransaction(target)
                if (isNew) {
                    listener.insert(target)
                } else {
                    listener.update(target)
                }
                true
            }
                .subscribeOn(Schedulers.io())
                .subscribe({ }, { it.printStackTrace() })
        }
    }
}

/**
 * 写入事务回调。query 返回当前匹配的第一条记录（不存在返回 null）；
 * onTransaction 中由调用方填充字段；insert / update 由调用方提供 Dao 调用。
 */
interface FlatRoomTransactionInterface<E> {
    fun query(): E?
    fun onTransaction(targetObject: E)
    fun insert(targetObject: E)
    fun update(targetObject: E)
}

/**
 * 获取数据库保存的列表信息
 */
fun <T, E : Any> FlatMapRoomReadList(
    db: GSYRoomDatabase,
    listener: FlatRoomReadConversionInterface<T, E>
): ArrayList<Any> {
    val first = listener.query(db)
    val list: List<T> = if (first == null) {
        ArrayList()
    } else {
        listener.onJSON(first)
    }
    val dataList = ArrayList<Any>()
    for (item in list) {
        dataList.add(listener.onConversion(item))
    }
    return dataList
}

/**
 * 获取数据库保存的实体信息
 */
fun <T, E : Any, R> FlatMapRoomReadObject(
    db: GSYRoomDatabase,
    listener: FlatRoomReadConversionObjectInterface<T, E, R>
): R? {
    val first = listener.query(db)
    val data: T? = if (first == null) {
        null
    } else {
        listener.onJSON(first)
    }
    return listener.onConversion(data)
}

interface FlatRoomReadConversionInterface<T, E> {
    fun query(db: GSYRoomDatabase): E?
    fun onJSON(t: E): List<T>
    fun onConversion(t: T): Any
}

interface FlatRoomReadConversionObjectInterface<T, E, R> {
    fun query(db: GSYRoomDatabase): E?
    fun onJSON(t: E): T
    fun onConversion(t: T?): R?
}

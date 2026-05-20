package com.shuyu.github.kotlin.repository.dao

import android.app.Application
import com.shuyu.github.kotlin.common.db.*
import com.shuyu.github.kotlin.common.net.GsonUtils
import com.shuyu.github.kotlin.model.bean.Issue
import com.shuyu.github.kotlin.model.bean.IssueEvent
import com.shuyu.github.kotlin.model.conversion.IssueConversion
import com.shuyu.github.kotlin.model.ui.IssueUIModel
import io.reactivex.Observable
import retrofit2.Response
import javax.inject.Inject

/**
 * Issue相关数据库操作（Room 版本）
 * Created by guoshuyu
 * Date: 2018-11-07
 */
class IssueDao @Inject constructor(private val application: Application) {

    /**
     * 保存issue详情
     */
    fun saveIssueInfoDao(response: Response<Issue>, userName: String, reposName: String, number: Int) {
        FlatMapRoomSaveResult(response, { IssueDetail() }, object : FlatRoomTransactionInterface<IssueDetail> {
            override fun query(): IssueDetail? {
                return RoomFactory.database.issueDetailDao().queryFirst("$userName/$reposName", number.toString())
            }

            override fun onTransaction(targetObject: IssueDetail) {
                targetObject.fullName = "$userName/$reposName"
                targetObject.number = number.toString()
                targetObject.data = GsonUtils.toJsonString(response.body())
            }

            override fun insert(targetObject: IssueDetail) {
                RoomFactory.database.issueDetailDao().insert(targetObject)
            }

            override fun update(targetObject: IssueDetail) {
                RoomFactory.database.issueDetailDao().update(targetObject)
            }
        }, true)
    }

    /**
     * 获取issue详情
     */
    fun getIssueInfoDao(userName: String, reposName: String, number: Int): Observable<IssueUIModel?> {
        return RoomFactory.getDatabaseObservable()
                .map { db ->
                    val item = FlatMapRoomReadObject(db, object : FlatRoomReadConversionObjectInterface<Issue, IssueDetail, IssueUIModel> {
                        override fun query(db: GSYRoomDatabase): IssueDetail? {
                            return db.issueDetailDao().queryFirst("$userName/$reposName", number.toString())
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

    /**
     * 保存issue评论
     */
    fun saveIssueCommentDao(response: Response<ArrayList<IssueEvent>>, userName: String, reposName: String, number: Int, needSave: Boolean) {
        FlatMapRoomSaveResult(response, { IssueComment() }, object : FlatRoomTransactionInterface<IssueComment> {
            override fun query(): IssueComment? {
                return RoomFactory.database.issueCommentDao().queryFirst("$userName/$reposName", number.toString())
            }

            override fun onTransaction(targetObject: IssueComment) {
                targetObject.fullName = "$userName/$reposName"
                targetObject.number = number.toString()
                targetObject.commentId = "-1"
                targetObject.data = GsonUtils.toJsonString(response.body())
            }

            override fun insert(targetObject: IssueComment) {
                RoomFactory.database.issueCommentDao().insert(targetObject)
            }

            override fun update(targetObject: IssueComment) {
                RoomFactory.database.issueCommentDao().update(targetObject)
            }
        }, needSave)
    }

    /**
     * 获取issue评论
     */
    fun getIssueCommentDao(userName: String, reposName: String, number: Int): Observable<ArrayList<Any>> {
        return RoomFactory.getDatabaseObservable()
                .map { db ->
                    FlatMapRoomReadList(db, object : FlatRoomReadConversionInterface<IssueEvent, IssueComment> {
                        override fun query(db: GSYRoomDatabase): IssueComment? {
                            return db.issueCommentDao().queryFirst("$userName/$reposName", number.toString())
                        }

                        override fun onJSON(t: IssueComment): List<IssueEvent> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, IssueEvent::class.java)
                        }

                        override fun onConversion(t: IssueEvent): Any {
                            return IssueConversion.issueEventToIssueUIModel(t)
                        }
                    })
                }
    }


}

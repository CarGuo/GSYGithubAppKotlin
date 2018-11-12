package com.shuyu.github.kotlin.common.db

import io.realm.RealmObject
import io.realm.annotations.RealmClass

/**
 * Realm数据库表
 * 主要保存json的String
 * Created by guoshuyu
 * Date: 2018-11-06
 */


/**
 * 仓库pulse表
 */
@RealmClass
open class RepositoryPulse : RealmObject() {
    open var fullName: String? = null
    open var data: String? = null
}

/**
 * 本地已读历史表
 */
@RealmClass
open class ReadHistory : RealmObject() {
    open var fullName: String? = null
    open var readDate: String? = null
    open var data: Long? = null
}

/**
 * 仓库分支表
 */
@RealmClass
open class RepositoryBranch : RealmObject() {
    open var fullName: String? = null
    open var data: String? = null
}

/**
 * 仓库提交信息表
 */
@RealmClass
open class RepositoryCommits : RealmObject() {
    open var fullName: String? = null
    open var data: String? = null
}

/**
 * 仓库订阅用户表
 */
@RealmClass
open class RepositoryWatcher : RealmObject() {
    open var fullName: String? = null
    open var data: String? = null
}

/**
 * 仓库收藏用户表
 */
@RealmClass
open class RepositoryStar : RealmObject() {
    open var fullName: String? = null
    open var data: String? = null
}

/**
 * 仓库分支表
 */
@RealmClass
open class RepositoryFork : RealmObject() {
    open var fullName: String? = null
    open var data: String? = null
}

/**
 * 仓库详情数据表
 */
@RealmClass
open class RepositoryDetail : RealmObject() {
    open var fullName: String? = null
    open var data: String? = null
    open var branch: String? = null
}

/**
 * 仓库readme文件表
 */
@RealmClass
open class RepositoryDetailReadme : RealmObject() {
    open var fullName: String? = null
    open var data: String? = null
    open var branch: String? = null

}

/**
 * 仓库活跃事件表
 */
@RealmClass
open class RepositoryEvent : RealmObject() {
    open var fullName: String? = null
    open var data: String? = null
}

/**
 * 仓库issue表
 */
@RealmClass
open class RepositoryIssue : RealmObject() {
    open var fullName: String? = null
    open var data: String? = null
    open var state: String? = null
}

/**
 * 仓库提交信息详情表
 */
@RealmClass
open class RepositoryCommitInfoDetail : RealmObject() {
    open var fullName: String? = null
    open var data: String? = null
    open var sha: String? = null
}

/**
 * 趋势表
 */
@RealmClass
open class TrendRepository : RealmObject() {
    open var languageType: String? = null
    open var data: String? = null
    open var since: String? = null
}

/**
 * 用户表
 */
@RealmClass
open class UserInfo : RealmObject() {
    open var userName: String? = null
    open var data: String? = null
}

/**
 * 用户粉丝表
 */
@RealmClass
open class UserFollower : RealmObject() {
    open var userName: String? = null
    open var data: String? = null
}

/**
 * 用户关注表
 */
open class UserFollowed : RealmObject() {
    open var userName: String? = null
    open var data: String? = null
}

/**
 * 用户关注表
 */
@RealmClass
open class OrgMember : RealmObject() {
    open var org: String? = null
    open var data: String? = null
}


/**
 * 用户组织表
 */
@RealmClass
open class UserOrgs : RealmObject() {
    open var userName: String? = null
    open var data: String? = null
}


/**
 * 用户收藏表
 */
@RealmClass
open class UserStared : RealmObject() {

    open var userName: String? = null
    open var data: String? = null
    open var sort: String? = null
}

/**
 * 用户仓库表
 */
@RealmClass
open class UserRepos : RealmObject() {

    open var userName: String? = null
    open var data: String? = null
    open var sort: String? = null
}

/**
 * 用户接受事件表
 */
@RealmClass
open class ReceivedEvent : RealmObject() {
    open var data: String? = null
}

/**
 * 用户动态表
 */
@RealmClass
open class UserEvent : RealmObject() {
    open var userName: String? = null
    open var data: String? = null
}

/**
 * issue详情表
 */
@RealmClass
open class IssueDetail : RealmObject() {
    open var fullName: String? = null
    open var number: String? = null
    open var data: String? = null
}

/**
 * issue评论表
 */
@RealmClass
open class IssueComment : RealmObject() {
    open var fullName: String? = null
    open var number: String? = null
    open var commentId: String? = null
    open var data: String? = null
}


fun clearCache() {
}

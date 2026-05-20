package com.shuyu.github.kotlin.common.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room 数据库表
 * 主要保存 json 的 String，作为离线缓存。
 * 字段保持与原 RealmTable 完全一致；每张表使用自增 id 作为主键。
 */

@Entity(tableName = "RepositoryPulse")
class RepositoryPulse {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var fullName: String? = null
    var data: String? = null
}

@Entity(tableName = "ReadHistory")
class ReadHistory {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var fullName: String? = null
    var readDate: String? = null
    var data: Long? = null
}

@Entity(tableName = "RepositoryBranch")
class RepositoryBranch {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var fullName: String? = null
    var data: String? = null
}

@Entity(tableName = "RepositoryCommits")
class RepositoryCommits {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var fullName: String? = null
    var data: String? = null
}

@Entity(tableName = "RepositoryWatcher")
class RepositoryWatcher {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var fullName: String? = null
    var data: String? = null
}

@Entity(tableName = "RepositoryStar")
class RepositoryStar {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var fullName: String? = null
    var data: String? = null
}

@Entity(tableName = "RepositoryFork")
class RepositoryFork {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var fullName: String? = null
    var data: String? = null
}

@Entity(tableName = "RepositoryDetail")
class RepositoryDetail {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var fullName: String? = null
    var data: String? = null
    var branch: String? = null
}

@Entity(tableName = "RepositoryDetailReadme")
class RepositoryDetailReadme {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var fullName: String? = null
    var data: String? = null
    var branch: String? = null
}

@Entity(tableName = "RepositoryEvent")
class RepositoryEvent {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var fullName: String? = null
    var data: String? = null
}

@Entity(tableName = "RepositoryIssue")
class RepositoryIssue {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var fullName: String? = null
    var data: String? = null
    var state: String? = null
}

@Entity(tableName = "RepositoryCommitInfoDetail")
class RepositoryCommitInfoDetail {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var fullName: String? = null
    var data: String? = null
    var sha: String? = null
}

@Entity(tableName = "TrendRepository")
class TrendRepository {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var languageType: String? = null
    var data: String? = null
    var since: String? = null
}

@Entity(tableName = "UserInfo")
class UserInfo {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var userName: String? = null
    var data: String? = null
}

@Entity(tableName = "UserFollower")
class UserFollower {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var userName: String? = null
    var data: String? = null
}

@Entity(tableName = "UserFollowed")
class UserFollowed {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var userName: String? = null
    var data: String? = null
}

@Entity(tableName = "OrgMember")
class OrgMember {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var org: String? = null
    var data: String? = null
}

@Entity(tableName = "UserOrgs")
class UserOrgs {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var userName: String? = null
    var data: String? = null
}

@Entity(tableName = "UserStared")
class UserStared {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var userName: String? = null
    var data: String? = null
    var sort: String? = null
}

@Entity(tableName = "UserRepos")
class UserRepos {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var userName: String? = null
    var data: String? = null
    var sort: String? = null
}

@Entity(tableName = "ReceivedEvent")
class ReceivedEvent {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var data: String? = null
}

@Entity(tableName = "UserEvent")
class UserEvent {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var userName: String? = null
    var data: String? = null
}

@Entity(tableName = "IssueDetail")
class IssueDetail {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var fullName: String? = null
    var number: String? = null
    var data: String? = null
}

@Entity(tableName = "IssueComment")
class IssueComment {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var fullName: String? = null
    var number: String? = null
    var commentId: String? = null
    var data: String? = null
}

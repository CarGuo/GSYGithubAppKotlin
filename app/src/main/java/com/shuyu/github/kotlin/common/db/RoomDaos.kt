package com.shuyu.github.kotlin.common.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

/**
 * Room Dao 接口集合
 * 与原 Realm 表对应，提供基本的：按业务字段查询第一条 + insert + update。
 * 上层 RoomFlatMapUtils 会按 “查到则 update，否则 insert” 的语义统一封装，
 * 以保持与原 Realm 版本相同的对外行为。
 */

@Dao
interface RepositoryPulseDao {
    @Query("SELECT * FROM RepositoryPulse WHERE fullName = :fullName LIMIT 1")
    fun queryFirst(fullName: String): RepositoryPulse?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: RepositoryPulse): Long

    @Update
    fun update(entity: RepositoryPulse)
}

@Dao
interface ReadHistoryDao {
    @Query("SELECT * FROM ReadHistory WHERE fullName = :fullName LIMIT 1")
    fun queryFirst(fullName: String): ReadHistory?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: ReadHistory): Long

    @Update
    fun update(entity: ReadHistory)
}

@Dao
interface RepositoryBranchDao {
    @Query("SELECT * FROM RepositoryBranch WHERE fullName = :fullName LIMIT 1")
    fun queryFirst(fullName: String): RepositoryBranch?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: RepositoryBranch): Long

    @Update
    fun update(entity: RepositoryBranch)
}

@Dao
interface RepositoryCommitsDao {
    @Query("SELECT * FROM RepositoryCommits WHERE fullName = :fullName LIMIT 1")
    fun queryFirst(fullName: String): RepositoryCommits?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: RepositoryCommits): Long

    @Update
    fun update(entity: RepositoryCommits)
}

@Dao
interface RepositoryWatcherDao {
    @Query("SELECT * FROM RepositoryWatcher WHERE fullName = :fullName LIMIT 1")
    fun queryFirst(fullName: String): RepositoryWatcher?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: RepositoryWatcher): Long

    @Update
    fun update(entity: RepositoryWatcher)
}

@Dao
interface RepositoryStarDao {
    @Query("SELECT * FROM RepositoryStar WHERE fullName = :fullName LIMIT 1")
    fun queryFirst(fullName: String): RepositoryStar?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: RepositoryStar): Long

    @Update
    fun update(entity: RepositoryStar)
}

@Dao
interface RepositoryForkDao {
    @Query("SELECT * FROM RepositoryFork WHERE fullName = :fullName LIMIT 1")
    fun queryFirst(fullName: String): RepositoryFork?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: RepositoryFork): Long

    @Update
    fun update(entity: RepositoryFork)
}

@Dao
interface RepositoryDetailDao {
    @Query("SELECT * FROM RepositoryDetail WHERE fullName = :fullName LIMIT 1")
    fun queryFirst(fullName: String): RepositoryDetail?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: RepositoryDetail): Long

    @Update
    fun update(entity: RepositoryDetail)
}

@Dao
interface RepositoryDetailReadmeDao {
    @Query("SELECT * FROM RepositoryDetailReadme WHERE fullName = :fullName AND branch = :branch LIMIT 1")
    fun queryFirst(fullName: String, branch: String): RepositoryDetailReadme?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: RepositoryDetailReadme): Long

    @Update
    fun update(entity: RepositoryDetailReadme)
}

@Dao
interface RepositoryEventDao {
    @Query("SELECT * FROM RepositoryEvent WHERE fullName = :fullName LIMIT 1")
    fun queryFirst(fullName: String): RepositoryEvent?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: RepositoryEvent): Long

    @Update
    fun update(entity: RepositoryEvent)
}

@Dao
interface RepositoryIssueDao {
    @Query("SELECT * FROM RepositoryIssue WHERE fullName = :fullName AND state = :state LIMIT 1")
    fun queryFirst(fullName: String, state: String): RepositoryIssue?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: RepositoryIssue): Long

    @Update
    fun update(entity: RepositoryIssue)
}

@Dao
interface RepositoryCommitInfoDetailDao {
    @Query("SELECT * FROM RepositoryCommitInfoDetail WHERE fullName = :fullName AND sha = :sha LIMIT 1")
    fun queryFirst(fullName: String, sha: String): RepositoryCommitInfoDetail?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: RepositoryCommitInfoDetail): Long

    @Update
    fun update(entity: RepositoryCommitInfoDetail)
}

@Dao
interface TrendRepositoryDao {
    @Query("SELECT * FROM TrendRepository WHERE languageType = :languageType AND since = :since LIMIT 1")
    fun queryFirst(languageType: String, since: String): TrendRepository?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: TrendRepository): Long

    @Update
    fun update(entity: TrendRepository)
}

@Dao
interface UserInfoDao {
    @Query("SELECT * FROM UserInfo WHERE userName = :userName LIMIT 1")
    fun queryFirst(userName: String): UserInfo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: UserInfo): Long

    @Update
    fun update(entity: UserInfo)
}

@Dao
interface UserFollowerDao {
    @Query("SELECT * FROM UserFollower WHERE userName = :userName LIMIT 1")
    fun queryFirst(userName: String): UserFollower?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: UserFollower): Long

    @Update
    fun update(entity: UserFollower)
}

@Dao
interface UserFollowedDao {
    @Query("SELECT * FROM UserFollowed WHERE userName = :userName LIMIT 1")
    fun queryFirst(userName: String): UserFollowed?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: UserFollowed): Long

    @Update
    fun update(entity: UserFollowed)
}

@Dao
interface OrgMemberDao {
    @Query("SELECT * FROM OrgMember WHERE org = :org LIMIT 1")
    fun queryFirst(org: String): OrgMember?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: OrgMember): Long

    @Update
    fun update(entity: OrgMember)
}

@Dao
interface UserOrgsDao {
    @Query("SELECT * FROM UserOrgs WHERE userName = :userName LIMIT 1")
    fun queryFirst(userName: String): UserOrgs?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: UserOrgs): Long

    @Update
    fun update(entity: UserOrgs)
}

@Dao
interface UserStaredDao {
    @Query("SELECT * FROM UserStared WHERE userName = :userName AND sort = :sort LIMIT 1")
    fun queryFirst(userName: String, sort: String): UserStared?

    @Query("SELECT * FROM UserStared WHERE userName = :userName LIMIT 1")
    fun queryFirstByUser(userName: String): UserStared?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: UserStared): Long

    @Update
    fun update(entity: UserStared)
}

@Dao
interface UserReposDao {
    @Query("SELECT * FROM UserRepos WHERE userName = :userName AND sort = :sort LIMIT 1")
    fun queryFirst(userName: String, sort: String): UserRepos?

    @Query("SELECT * FROM UserRepos WHERE userName = :userName LIMIT 1")
    fun queryFirstByUser(userName: String): UserRepos?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: UserRepos): Long

    @Update
    fun update(entity: UserRepos)
}

@Dao
interface ReceivedEventDao {
    @Query("SELECT * FROM ReceivedEvent LIMIT 1")
    fun queryFirst(): ReceivedEvent?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: ReceivedEvent): Long

    @Update
    fun update(entity: ReceivedEvent)
}

@Dao
interface UserEventDao {
    @Query("SELECT * FROM UserEvent WHERE userName = :userName LIMIT 1")
    fun queryFirst(userName: String): UserEvent?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: UserEvent): Long

    @Update
    fun update(entity: UserEvent)
}

@Dao
interface IssueDetailDao {
    @Query("SELECT * FROM IssueDetail WHERE fullName = :fullName AND number = :number LIMIT 1")
    fun queryFirst(fullName: String, number: String): IssueDetail?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: IssueDetail): Long

    @Update
    fun update(entity: IssueDetail)
}

@Dao
interface IssueCommentDao {
    @Query("SELECT * FROM IssueComment WHERE fullName = :fullName AND number = :number LIMIT 1")
    fun queryFirst(fullName: String, number: String): IssueComment?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: IssueComment): Long

    @Update
    fun update(entity: IssueComment)
}

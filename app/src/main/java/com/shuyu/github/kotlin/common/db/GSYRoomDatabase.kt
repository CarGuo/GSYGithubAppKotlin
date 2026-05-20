package com.shuyu.github.kotlin.common.db

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Room 数据库定义。
 * 取代原 Realm 数据库（gsy.realm）。
 * 旧的 Realm 数据不做迁移，version 重新从 1 开始。
 */
@Database(
    entities = [
        RepositoryPulse::class,
        ReadHistory::class,
        RepositoryBranch::class,
        RepositoryCommits::class,
        RepositoryWatcher::class,
        RepositoryStar::class,
        RepositoryFork::class,
        RepositoryDetail::class,
        RepositoryDetailReadme::class,
        RepositoryEvent::class,
        RepositoryIssue::class,
        RepositoryCommitInfoDetail::class,
        TrendRepository::class,
        UserInfo::class,
        UserFollower::class,
        UserFollowed::class,
        OrgMember::class,
        UserOrgs::class,
        UserStared::class,
        UserRepos::class,
        ReceivedEvent::class,
        UserEvent::class,
        IssueDetail::class,
        IssueComment::class
    ],
    version = 1,
    exportSchema = false
)
abstract class GSYRoomDatabase : RoomDatabase() {

    abstract fun repositoryPulseDao(): RepositoryPulseDao
    abstract fun readHistoryDao(): ReadHistoryDao
    abstract fun repositoryBranchDao(): RepositoryBranchDao
    abstract fun repositoryCommitsDao(): RepositoryCommitsDao
    abstract fun repositoryWatcherDao(): RepositoryWatcherDao
    abstract fun repositoryStarDao(): RepositoryStarDao
    abstract fun repositoryForkDao(): RepositoryForkDao
    abstract fun repositoryDetailDao(): RepositoryDetailDao
    abstract fun repositoryDetailReadmeDao(): RepositoryDetailReadmeDao
    abstract fun repositoryEventDao(): RepositoryEventDao
    abstract fun repositoryIssueDao(): RepositoryIssueDao
    abstract fun repositoryCommitInfoDetailDao(): RepositoryCommitInfoDetailDao
    abstract fun trendRepositoryDao(): TrendRepositoryDao
    abstract fun userInfoDao(): UserInfoDao
    abstract fun userFollowerDao(): UserFollowerDao
    abstract fun userFollowedDao(): UserFollowedDao
    abstract fun orgMemberDao(): OrgMemberDao
    abstract fun userOrgsDao(): UserOrgsDao
    abstract fun userStaredDao(): UserStaredDao
    abstract fun userReposDao(): UserReposDao
    abstract fun receivedEventDao(): ReceivedEventDao
    abstract fun userEventDao(): UserEventDao
    abstract fun issueDetailDao(): IssueDetailDao
    abstract fun issueCommentDao(): IssueCommentDao
}

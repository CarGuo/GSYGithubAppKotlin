package com.shuyu.github.kotlin.module.list


/**
 * 通用列表显示类型
 * Created by guoshuyu
 * Date: 2018-11-08
 */
enum class GeneralEnum {
    //用户粉丝
    UserFollower,
    //用户关注
    UserFollowed,
    //用户仓库
    UserRepository,
    //用户star
    UserStar,
    //仓库star用户
    RepositoryStarUser,
    //仓库被fork列表
    RepositoryForkUser,
    //仓库订阅用户
    RepositoryWatchUser,
}
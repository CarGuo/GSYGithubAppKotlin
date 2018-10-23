package com.shuyu.github.kotlin.model.conversion

import com.shuyu.github.kotlin.model.bean.User
import com.shuyu.github.kotlin.model.ui.UserUIModel

/**
 * Created by guoshuyu
 * Date: 2018-10-23
 */

object UserConversion {


    fun userToUserUIModel(user: User): UserUIModel {
        val uiModel = UserUIModel()
        uiModel.login = user.login
        uiModel.id = user.id
        uiModel.name = user.name
        uiModel.avatarUrl = user.avatarUrl
        uiModel.htmlUrl = user.htmlUrl
        uiModel.type = user.type
        uiModel.company = user.company
        uiModel.blog = user.blog
        uiModel.location = user.location
        uiModel.email = user.email
        uiModel.bio = user.bio
        uiModel.starRepos = user.starRepos
        uiModel.honorRepos = user.honorRepos
        uiModel.publicRepos = user.publicRepos
        uiModel.publicGists = user.publicGists
        uiModel.followers = user.followers
        uiModel.following = user.following
        uiModel.createdAt = user.createdAt
        uiModel.updatedAt = user.updatedAt
        return uiModel
    }
}
package com.shuyu.github.kotlin.model.ui

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.shuyu.github.kotlin.BR
import com.shuyu.github.kotlin.model.bean.User
import java.util.*


/**
 * Created by guoshuyu
 * Date: 2018-10-23
 */

class UserUIModel : BaseObservable() {
    var login: String? = null
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.login)
        }

    var id: String? = null
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.id)
        }

    var name: String? = null
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

    var avatarUrl: String? = null
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.avatarUrl)
        }

    var htmlUrl: String? = null
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.htmlUrl)
        }

    var type: String? = null
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.type)
        }

    var company: String? = null
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.company)
        }

    var blog: String? = null
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.blog)
        }

    var location: String? = null
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.location)
        }

    var email: String? = null
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

    var bio: String? = null
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.bio)
        }


    var starRepos: Int? = null
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.starRepos)
        }

    var honorRepos: Int? = null
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.honorRepos)
        }

    var publicRepos: Int = 0
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.publicRepos)
        }

    var publicGists: Int = 0
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.publicGists)
        }

    var followers: Int = 0
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.followers)
        }

    var following: Int = 0
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.following)
        }

    var createdAt: Date? = null
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.createdAt)
        }

    var updatedAt: Date? = null
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.updatedAt)
        }

    fun cloneDataFromUser(user: User) {
        login = user.login
        id = user.id
        name = user.name
        avatarUrl = user.avatarUrl
        htmlUrl = user.htmlUrl
        type = user.type
        company = user.company
        blog = user.blog
        location = user.location
        email = user.email
        bio = user.bio
        starRepos = user.starRepos
        honorRepos = user.honorRepos
        publicRepos = user.publicRepos
        publicGists = user.publicGists
        followers = user.followers
        following = user.following
        createdAt = user.createdAt
        updatedAt = user.updatedAt
    }
}
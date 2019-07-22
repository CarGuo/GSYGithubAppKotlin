package com.shuyu.github.kotlin.model.ui

import com.shuyu.github.kotlin.BR
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import java.util.*


/**
 * 用户
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


    var bioDes: String? = null
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.bioDes)
        }

    var starRepos: String = ""
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.starRepos)
        }

    var honorRepos: String = ""
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.honorRepos)
        }

    var publicRepos: String = ""
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

    var followers: String = ""
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.followers)
        }

    var following: String = ""
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

    var actionUrl: String = ""
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.actionUrl)
        }
}
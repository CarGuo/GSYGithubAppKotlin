package com.shuyu.github.kotlin.model.ui


import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.shuyu.github.kotlin.BR

/**
 * 仓库相关UI类型
 */
class ReposUIModel : BaseObservable() {

    var ownerName: String = "--"
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.ownerName)
        }
    var ownerPic: String = ""
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.ownerPic)
        }
    var repositoryName: String = "---"
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.repositoryName)
        }
    var repositoryStar: String = "---"
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.repositoryStar)
        }
    var repositoryFork: String = "---"
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.repositoryFork)
        }
    var repositoryWatch: String = "---"
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.repositoryWatch)
        }
    var hideWatchIcon: Boolean = true
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.hideWatchIcon)
        }
    var repositoryType: String = "---"
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.repositoryType)
        }
    var repositoryDes: String = "--"
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.repositoryDes)
        }
    var repositorySize: String = "--"
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.repositorySize)
        }
    var repositoryLicense: String = "--"
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.repositoryLicense)
        }
    var repositoryAction: String = "--"
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.repositoryAction)
        }
    var repositoryIssue: String = "--"
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.repositoryIssue)
        }

    fun cloneFrom(reposUIModel: ReposUIModel) {
        ownerName = reposUIModel.ownerName
        if (ownerPic != reposUIModel.ownerPic) {
            ownerPic = reposUIModel.ownerPic
        }
        repositoryName = reposUIModel.repositoryName
        repositoryStar = reposUIModel.repositoryStar
        repositoryFork = reposUIModel.repositoryFork
        repositoryWatch = reposUIModel.repositoryWatch
        hideWatchIcon = reposUIModel.hideWatchIcon
        repositoryType = reposUIModel.repositoryType
        repositoryDes = reposUIModel.repositoryDes
        repositorySize = reposUIModel.repositorySize
        repositoryLicense = reposUIModel.repositoryLicense
        repositoryAction = reposUIModel.repositoryAction
        repositoryIssue = reposUIModel.repositoryIssue
    }
}
package com.shuyu.github.kotlin.model.ui


import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.shuyu.github.kotlin.BR

/**
 * Issue相关UI类型
 */
class IssueUIModel : BaseObservable() {
    var username: String = "---"
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.username)
        }
    var image: String = ""
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.image)
        }
    var action: String = "---"
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.action)
        }
    var time: String = "---"
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.time)
        }
    var comment: String = "---"
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.comment)
        }
    var content: String = "---"
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.content)
        }
    var issueNum: Int = 0
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.issueNum)
        }
    var status: String = "---"
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.status)
        }

    var locked: Boolean = false
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.locked)
        }

    fun cloneFrom(issueUIModel: IssueUIModel) {
        username = issueUIModel.username
        image = issueUIModel.image
        action = issueUIModel.action
        time = issueUIModel.time
        comment = issueUIModel.comment
        content = issueUIModel.content
        issueNum = issueUIModel.issueNum
        status = issueUIModel.status
        locked = issueUIModel.locked
    }
}



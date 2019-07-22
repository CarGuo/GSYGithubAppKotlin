package com.shuyu.github.kotlin.model.ui


import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.shuyu.github.kotlin.BR

/**
 * Created by guoshuyu
 * Date: 2018-11-15
 */
class PushUIModel : BaseObservable() {
    var pushUserName: String = "---"
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.pushUserName)
        }
    var pushImage: String = "---"
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.pushImage)
        }
    var pushEditCount: String = ""
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.pushEditCount)
        }
    var pushAddCount: String = "---"
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.pushAddCount)
        }
    var pushReduceCount: String = "---"
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.pushReduceCount)
        }
    var pushTime: String = "---"
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.pushTime)
        }
    var pushDes: String = "---"
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.pushDes)
        }

    fun cloneFrom(pushUIModel: PushUIModel) {
        pushUserName = pushUIModel.pushUserName
        pushImage = pushUIModel.pushImage
        pushEditCount = pushUIModel.pushEditCount
        pushAddCount = pushUIModel.pushAddCount
        pushReduceCount = pushUIModel.pushReduceCount
        pushTime = pushUIModel.pushTime
        pushDes = pushUIModel.pushDes
    }
}

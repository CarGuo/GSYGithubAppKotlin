package com.shuyu.github.kotlin.holder.base

import android.databinding.BindingAdapter
import android.databinding.DataBindingComponent
import android.widget.ImageView
import android.widget.TextView
import com.shuyu.github.kotlin.common.utils.CommonUtils
import com.shuyu.github.kotlin.model.User


/**
 * DataBinding 的拓展适配器
 */
class DataBindingExtendUtils {

    companion object {
        @BindingAdapter("userHeaderUrl")
        fun loadImage(view: ImageView, url: String) {
            CommonUtils.loadUserHeaderImage(view, url)
        }

        @BindingAdapter("userModel", "userParams", requireAll = false)
        fun userModelInsert(view: TextView, user: User, userParams: String) {
            when (userParams) {
                "login" -> view.text = user.login
            }
        }
    }

}

/**
 * 加载 DataBinding 的拓展适配器
 */
class GSYDataBindingComponent : DataBindingComponent {
    override fun getCompanion(): DataBindingExtendUtils.Companion = DataBindingExtendUtils
}

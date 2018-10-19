package com.shuyu.github.kotlin.ui.adapter

import android.databinding.BindingAdapter
import android.databinding.DataBindingComponent
import android.widget.ImageView
import com.shuyu.github.kotlin.common.utils.CommonUtils

class DataBindingExtendUtils {

    companion object {
        @BindingAdapter("userHeaderUrl")
        fun loadImage(view: ImageView, url: String) {
            CommonUtils.loadUserHeaderImage(view, url)
        }
    }

}


class GSYDataBindingComponent : DataBindingComponent {
    override fun getCompanion(): DataBindingExtendUtils.Companion = DataBindingExtendUtils
}

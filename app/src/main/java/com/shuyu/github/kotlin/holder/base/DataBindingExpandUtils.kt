package com.shuyu.github.kotlin.holder.base

import android.databinding.BindingAdapter
import android.databinding.DataBindingComponent
import android.graphics.Point
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView
import com.shuyu.github.kotlin.common.utils.CommonUtils
import com.shuyu.github.kotlin.common.utils.dp


/**
 * DataBinding 的拓展适配器
 */
class DataBindingExpandUtils {

    companion object {

        @BindingAdapter("userHeaderUrl", "userHeaderSize", requireAll = false)
        fun loadImage(view: ImageView, url: String, size: Int = 50) {
            CommonUtils.loadUserHeaderImage(view, url, Point(size.dp, size.dp))
        }

        @BindingAdapter("webViewUrl")
        fun webViewUrl(view: WebView?, url: String?) {
            view?.apply {
                val settings = this.settings
                settings?.javaScriptEnabled = true
                settings?.loadWithOverviewMode = true
                settings?.builtInZoomControls = false
                settings?.displayZoomControls = false
                settings?.domStorageEnabled = true
                settings?.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
                settings?.setAppCacheEnabled(true)

                view.isVerticalScrollBarEnabled = false
                view.loadUrl(url)
            }

        }

    }
}

/**
 * 加载 DataBinding 的拓展适配器
 */
class GSYDataBindingComponent : DataBindingComponent {
    override fun getCompanion(): DataBindingExpandUtils.Companion = DataBindingExpandUtils
}

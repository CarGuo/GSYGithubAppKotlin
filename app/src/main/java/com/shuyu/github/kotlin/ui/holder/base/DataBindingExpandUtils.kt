package com.shuyu.github.kotlin.ui.holder.base

import android.databinding.BindingAdapter
import android.databinding.DataBindingComponent
import android.graphics.Point
import android.widget.ImageView
import android.widget.TextView
import com.shuyu.github.kotlin.common.utils.CommonUtils
import com.shuyu.github.kotlin.common.utils.dp
import com.shuyu.github.kotlin.ui.view.GSYWebViewContainer
import ru.noties.markwon.Markwon


/**
 * DataBinding 的拓展适配器
 */
class DataBindingExpandUtils {

    companion object {

        @BindingAdapter("image_blur")
        fun loadImageBlur(view: ImageView, url: String?) {
            CommonUtils.loadImageBlur(view, url ?: "")
        }

        @BindingAdapter("userHeaderUrl", "userHeaderSize", requireAll = false)
        fun loadImage(view: ImageView, url: String?, size: Int = 50) {
            CommonUtils.loadUserHeaderImage(view, url ?: "", Point(size.dp, size.dp))
        }

        @BindingAdapter("webViewUrl")
        fun webViewUrl(view: GSYWebViewContainer?, url: String?) {
            view?.apply {
                webView.isVerticalScrollBarEnabled = false
                webView.loadUrl(url)
            }

        }


        @BindingAdapter("markdownText", "style", requireAll = false)
        fun webViewUrl(view: TextView?, text: String?, style: String? = "default") {
            view?.apply {
                Markwon.setMarkdown(this, text ?: "")
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

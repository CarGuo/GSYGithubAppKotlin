package com.shuyu.github.kotlin.ui.holder.base

import android.graphics.Point
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.utils.colorInt
import com.mikepenz.iconics.utils.icon
import com.mikepenz.iconics.view.IconicsImageView
import com.shuyu.github.kotlin.common.style.MarkDownConfig
import com.shuyu.github.kotlin.common.utils.CommonUtils
import com.shuyu.github.kotlin.common.utils.dp
import com.shuyu.github.kotlin.ui.view.GSYWebViewContainer
import io.noties.markwon.Markwon


/**
 * DataBinding 的拓展适配器实现。
 *
 * 注意：AGP 9 的 dataBinding 注解处理器仅扫描 javac round 中的 source 文件来发现 @BindingAdapter，
 * 不会扫描已编译好的 Kotlin .class，因此真正带有 @BindingAdapter 注解的桥接器写在
 * [DataBindingExpandUtilsJava]（Java 文件）中，由它委托到这里的 Kotlin 实现。
 */
class DataBindingExpandUtils {

    companion object {

        @JvmStatic
        fun loadImageBlur(view: ImageView, url: String?) {
            CommonUtils.loadImageBlur(view, url ?: "")
        }

        @JvmStatic
        @JvmOverloads
        fun loadImage(view: ImageView, url: String?, size: Int = 50) {
            CommonUtils.loadUserHeaderImage(view, url ?: "", Point(size.dp, size.dp))
        }

        @JvmStatic
        fun webViewUrl(view: GSYWebViewContainer?, url: String?) {
            view?.apply {
                webView.isVerticalScrollBarEnabled = false
                url?.let { webView.loadUrl(it) }
            }
        }

        @JvmStatic
        @JvmOverloads
        fun markdownText(view: TextView?, text: String?, style: String? = "default") {
            view?.apply {
                Markwon.builder(context)
                        .usePlugins(MarkDownConfig.getConfig(view.context))
                        .build()
                        .setMarkdown(view, text ?: "")
            }
        }

        @JvmStatic
        fun editTextKeyListener(view: EditText?, listener: View.OnKeyListener) {
            view?.apply {
                this.setOnKeyListener(listener)
            }
        }

        @JvmStatic
        fun editTextKeyListener(view: IconicsImageView?, value: String?, colorId: Int?) {
            if (view == null || value == null) {
                return
            }
            val d = IconicsDrawable(view.context).apply {
                icon(value)
                colorId?.let { this.colorInt = it }
            }
            view.icon = d
        }
    }
}

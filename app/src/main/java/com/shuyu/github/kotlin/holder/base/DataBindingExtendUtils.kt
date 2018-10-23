package com.shuyu.github.kotlin.holder.base

import android.annotation.SuppressLint
import android.databinding.BindingAdapter
import android.databinding.DataBindingComponent
import android.graphics.Color
import android.graphics.Point
import android.support.v4.content.ContextCompat
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.config.AppConfig
import com.shuyu.github.kotlin.common.utils.CommonUtils
import com.shuyu.github.kotlin.model.bean.User


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
        @SuppressLint("SetTextI18n")
        fun userModelInsert(view: View?, user: User?, userParams: String?) {
            if (view is TextView) {
                fun getBlankText(value: Int?): String {
                    return value?.toString() ?: "---"
                }
                when (userParams) {
                    "login" -> view.text = user?.login
                    "name" -> view.text = user?.name
                    "company" -> view.text = "{GSY_USER_ITEM_COMPANY} ${user?.company ?: "---"}"
                    "location" -> view.text = "{GSY_USER_ITEM_LOCATION} ${user?.location ?: "---"}"
                    "link" -> view.text = "{GSY_USER_ITEM_LINK} ${user?.blog ?: "---"}"
                    "bio" -> {
                        view.text = if (user?.bio != null) {
                            user.bio + "\n" + view.context.getString(R.string.created_at) + CommonUtils.getDateStr(user.createdAt)
                        } else {
                            view.context.getString(R.string.created_at) + CommonUtils.getDateStr(user?.createdAt)
                        }
                    }
                    "publicRepos" -> {
                        view.text = view.context.getString(R.string.repositoryText) + "\n" + getBlankText(user?.publicRepos)
                    }
                    "followers" -> {
                        view.text = view.context.getString(R.string.FollowersText) + "\n" + getBlankText(user?.followers)
                    }
                    "following" -> {
                        view.text = view.context.getString(R.string.FollowedText) + "\n" + getBlankText(user?.following)
                    }
                    "starRepos" -> {
                        view.text = view.context.getString(R.string.staredText) + "\n" + getBlankText(user?.starRepos)
                    }
                    "honorRepos" -> {
                        view.text = view.context.getString(R.string.beStaredText) + "\n" + getBlankText(user?.honorRepos)
                    }
                }
            } else if (view is ImageView) {
                CommonUtils.loadUserHeaderImage(view, user?.avatarUrl ?: "", Point(90, 90))
            } else if (view is WebView) {
                view.apply {
                    fun changeColor(): String {
                        val stringBuffer = StringBuffer()
                        val color = ContextCompat.getColor(view.context, R.color.colorPrimary)
                        stringBuffer.append(Integer.toHexString(Color.red(color)))
                        stringBuffer.append(Integer.toHexString(Color.green(color)))
                        stringBuffer.append(Integer.toHexString(Color.blue(color)))
                        return stringBuffer.toString()
                    }
                    fun getUserChartAddress(name: String): String {
                        return AppConfig.GRAPHIC_HOST + changeColor() + "/" + name
                    }
                    val settings = view.settings
                    settings?.javaScriptEnabled = true
                    settings?.loadWithOverviewMode = true
                    settings?.builtInZoomControls = false
                    settings?.displayZoomControls = false
                    settings?.domStorageEnabled = true
                    settings?.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
                    settings?.setAppCacheEnabled(true)
                    view.loadUrl(getUserChartAddress(user?.login ?: ""))
                }
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

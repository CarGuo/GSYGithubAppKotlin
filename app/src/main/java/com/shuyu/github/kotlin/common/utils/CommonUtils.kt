package com.shuyu.github.kotlin.common.utils

import android.graphics.Point
import android.widget.ImageView
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.style.image.BlurTransformation
import com.shuyu.gsyimageloader.GSYImageLoader
import com.shuyu.gsyimageloader.GSYImageLoaderManager
import com.shuyu.gsyimageloader.GSYLoadOption
import java.util.*


object CommonUtils {

    private const val MILLIS_LIMIT = 1000.0

    private const val SECONDS_LIMIT = 60 * MILLIS_LIMIT

    private const val MINUTES_LIMIT = 60 * SECONDS_LIMIT

    private const val HOURS_LIMIT = 24 * MINUTES_LIMIT

    private const val DAYS_LIMIT = 30 * HOURS_LIMIT


    fun loadUserHeaderImage(imageView: ImageView, url: String, size: Point = Point(50, 50)) {
        val option = GSYLoadOption()
                .setDefaultImg(R.drawable.logo)
                .setErrorImg(R.drawable.logo)
                .setCircle(true)
                .setSize(size)
                .setUri(url)
        GSYImageLoaderManager.sInstance.imageLoader().loadImage(option, imageView, null)
    }


    fun loadImageBlur(imageView: ImageView, url: String) {
        val process = BlurTransformation()
        val option = GSYLoadOption()
                .setDefaultImg(R.drawable.logo)
                .setErrorImg(R.drawable.logo)
                .setUri(url)
                .setTransformations(process)

        GSYImageLoaderManager.sInstance.imageLoader().loadImage(option, imageView, null)
    }


    fun getDateStr(date: Date?): String {
        if (date?.toString() == null) {
            return ""
        } else if (date.toString().length < 10) {
            return date.toString()
        }
        return date.toString().substring(0, 10)
    }

    fun getNewsTimeStr(date: Date?): String {
        if (date == null) {
            return ""
        }
        val subTime = Date().time - date.time
        return when {
            subTime < MILLIS_LIMIT -> "刚刚"
            subTime < SECONDS_LIMIT -> Math.round(subTime / MILLIS_LIMIT).toString() + " 秒前"
            subTime < MINUTES_LIMIT -> Math.round(subTime / SECONDS_LIMIT).toString() + " 分钟前"
            subTime < HOURS_LIMIT -> Math.round(subTime / MINUTES_LIMIT).toString() + " 小时前"
            subTime < DAYS_LIMIT -> Math.round(subTime / HOURS_LIMIT).toString() + " 天前"
            else -> getDateStr(date)
        }
    }
}
package com.shuyu.github.kotlin.common.utils

import android.graphics.Point
import android.widget.ImageView
import com.shuyu.github.kotlin.R
import com.shuyu.gsyimageloader.GSYImageLoaderManager
import com.shuyu.gsyimageloader.GSYLoadOption


object CommonUtils {

    fun loadUserHeaderImage(imageView: ImageView, url: String, size: Point = Point(30, 30)) {
        val option = GSYLoadOption()
                .setDefaultImg(R.drawable.logo)
                .setErrorImg(R.drawable.logo)
                .setCircle(true)
                .setSize(size)
                .setUri(url)
        GSYImageLoaderManager.sInstance.imageLoader().loadImage(option, imageView, null)
    }
}
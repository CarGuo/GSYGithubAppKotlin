package com.shuyu.github.kotlin.common.gsyimageloader.gsygiideloader

import android.graphics.drawable.Drawable

import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.shuyu.github.kotlin.common.gsyimageloader.GSYImageLoader

import java.io.File

/**
 * Glide 图片下载对象
 * Created by guoshuyu on 2018/1/18.
 */
class GSYImageDownLoadTarget constructor(private val mCallback: GSYImageLoader.Callback?) : SimpleTarget<File>() {

    override fun onResourceReady(resource: File, transition: Transition<in File>?) {
        mCallback?.onSuccess(resource)
    }

    override fun onLoadStarted(placeholder: Drawable?) {
        super.onLoadStarted(placeholder)
        mCallback?.onStart()
    }

    override fun onLoadFailed(errorDrawable: Drawable?) {
        super.onLoadFailed(errorDrawable)
        mCallback?.onFail(null)
    }

}
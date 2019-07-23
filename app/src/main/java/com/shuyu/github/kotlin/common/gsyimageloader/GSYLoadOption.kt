package com.shuyu.github.kotlin.common.gsyimageloader

import android.graphics.Point

/**
 * 图片加载配置
 */
class GSYLoadOption {

    //默认图片
    var mDefaultImg: Int = 0

    //错误图片
    var mErrorImg: Int = 0

    //是否圆形
    var isCircle: Boolean = false

    //是否播放gif
    var isPlayGif: Boolean = true

    //大小
    var mSize: Point? = null

    //图片
    var mUri: Any? = null

    val mTransformations: ArrayList<Any> = ArrayList()

    fun setDefaultImg(defaultImg: Int): GSYLoadOption {
        this.mDefaultImg = defaultImg
        return this
    }

    fun setErrorImg(errorImg: Int): GSYLoadOption {
        this.mErrorImg = errorImg
        return this
    }

    /**
     * 是否圆形，目前支持fresco 、 glide
     */
    fun setCircle(circle: Boolean): GSYLoadOption {
        isCircle = circle
        return this
    }

    /**
     * 是否播放gif，只支持Fresco目前
     */
    fun setPlayGif(playGif: Boolean): GSYLoadOption {
        isPlayGif = playGif
        return this
    }

    /**
     * 目标尺寸
     */
    fun setSize(size: Point?): GSYLoadOption {
        this.mSize = size
        return this
    }

    /**
     * 播放目标 string、uri、int
     */
    fun setUri(uri: Any): GSYLoadOption {
        this.mUri = uri
        return this
    }

    /**
     * 图片处理
     * picasso https://github.com/wasabeef/picasso-transformations
     * glide   https://github.com/wasabeef/glide-transformations
     * fresco  https://github.com/wasabeef/fresco-processors
     */
    fun setTransformations(transform: Any): GSYLoadOption {
        mTransformations.add(transform)
        return this
    }
}

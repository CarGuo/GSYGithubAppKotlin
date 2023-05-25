package com.shuyu.github.kotlin.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import devlight.io.library.ntb.NavigationTabBar

/**
 * 拓展了双击的TabBar
 * Created by guoshuyu
 * Date: 2018-10-22
 */

class GSYNavigationTabBar : NavigationTabBar {

    var isTouchEnable = true


    var doubleTouchListener: TabDoubleClickListener? = null

    /**
     * 双击
     */
    var gestureDetector = GestureDetector(context.applicationContext, object : GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent): Boolean {
            doubleTouchListener?.onDoubleClick(mIndex)
            return super.onDoubleTap(e)
        }
    })


    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onTouchEvent(event: MotionEvent): Boolean {

        if (!isTouchEnable) {
            return true
        }

        super.onTouchEvent(event)

        gestureDetector.onTouchEvent(event)

        return true
    }

    interface TabDoubleClickListener {
        fun onDoubleClick(position: Int)
    }
}
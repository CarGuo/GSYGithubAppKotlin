package com.shuyu.github.kotlin.ui.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

/**
 * 基于 TabLayout 的 TabBar，替代 NavigationTabBar
 * 支持双击回调
 * Created by guoshuyu
 * Date: 2018-10-22
 */
class GSYTabBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TabLayout(context, attrs, defStyleAttr) {

    var isTouchEnable = true

    var doubleTouchListener: TabDoubleClickListener? = null

    private var currentIndex = 0

    /**
     * 双击检测
     */
    private val gestureDetector = GestureDetector(context.applicationContext, object : GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent): Boolean {
            doubleTouchListener?.onDoubleClick(currentIndex)
            return super.onDoubleTap(e)
        }
    })

    init {
        addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: Tab?) {
                currentIndex = tab?.position ?: 0
            }

            override fun onTabUnselected(tab: Tab?) {}

            override fun onTabReselected(tab: Tab?) {}
        })
    }

    /**
     * Tab 数据模型
     */
    data class Model(
        val icon: Drawable?,
        val title: String
    ) {
        class Builder(private val icon: Drawable?) {
            private var title: String = ""

            fun title(title: String): Builder {
                this.title = title
                return this
            }

            fun build(): Model {
                return Model(icon, title)
            }
        }
    }

    /**
     * 设置 models 并创建 tabs
     */
    var models: List<Model>? = null
        set(value) {
            field = value
            removeAllTabs()
            value?.forEach { model ->
                val tab = newTab()
                tab.icon = model.icon
                tab.text = model.title
                addTab(tab)
            }
        }

    /**
     * 关联 ViewPager
     */
    fun setViewPager(viewPager: ViewPager, defaultPosition: Int = 0) {
        setupWithViewPager(viewPager)
        // setupWithViewPager 会清除之前的 tabs，需要重新设置
        models?.forEachIndexed { index, model ->
            getTabAt(index)?.apply {
                icon = model.icon
                text = model.title
            }
        }
        getTabAt(defaultPosition)?.select()
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (!isTouchEnable) {
            return true
        }
        ev?.let { gestureDetector.onTouchEvent(it) }
        return super.onInterceptTouchEvent(ev)
    }

    interface TabDoubleClickListener {
        fun onDoubleClick(position: Int)
    }
}


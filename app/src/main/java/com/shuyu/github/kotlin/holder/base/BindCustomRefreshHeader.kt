package com.shuyu.github.kotlin.holder.base

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.github.ybq.android.spinkit.SpinKitView
import com.github.ybq.android.spinkit.sprite.SpriteContainer
import com.github.ybq.android.spinkit.style.CubeGrid
import com.shuyu.commonrecycler.BindBaseRefreshHeader
import com.shuyu.commonrecycler.xrecycler.base.BaseRefreshHeader
import com.shuyu.github.kotlin.R


/**
 * 继承BindCustomRefreshHeader实现的下拉刷新
 *
 *
 * 如果需要自定义程度更高的，即可继承BaseRefreshHeader
 *
 *
 * Created by guoshuyu on 2017/1/8.
 */

open class BindCustomRefreshHeader : BindBaseRefreshHeader {


    private var customRefreshImg: SpinKitView? = null

    private var customRefreshTxt: TextView? = null

    private var spriteContainer: SpriteContainer? = null

    /**
     * 继承，返回高度
     */
    override var currentMeasuredHeight: Int = 0

    /**
     * 继承
     *
     * @return 返回布局id
     */
    override val layoutId: Int
        get() = R.layout.layout_custom_refresh_header

    /**
     * 继承，根据状态调整显示效果
     *
     * @param state
     */
    override// 显示进度
            // 显示进度
    var state: Int
        get() = super.state
        set(state) {
            if (state == super.state) return

            when (state) {
                BaseRefreshHeader.STATE_REFRESHING -> spriteContainer?.start()
                BaseRefreshHeader.STATE_DONE -> spriteContainer?.stop()
                else -> spriteContainer?.stop()
            }

            when (state) {
                BaseRefreshHeader.STATE_NORMAL -> {
                    if (super.state == BaseRefreshHeader.STATE_RELEASE_TO_REFRESH) {
                    }
                    if (super.state == BaseRefreshHeader.STATE_REFRESHING) {
                    }
                    customRefreshTxt?.text = context.getString(R.string.loading_prepare)
                }
                BaseRefreshHeader.STATE_RELEASE_TO_REFRESH -> if (super.state != BaseRefreshHeader.STATE_RELEASE_TO_REFRESH) {
                    customRefreshTxt?.text = context.getString(R.string.loading_drop)
                }
                BaseRefreshHeader.STATE_REFRESHING -> customRefreshTxt?.text = context.getString(R.string.loading_start)
                BaseRefreshHeader.STATE_DONE -> customRefreshTxt?.text = context.getString(R.string.loading_end)
            }
            super.state = state
        }

    constructor(context: Context) : super(context) {}


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    /**
     * 继承，将view添加到控件中
     *
     * @param container
     */
    override fun addView(container: ViewGroup?) {
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        lp.setMargins(0, 0, 0, 0)
        this.layoutParams = lp
        this.setPadding(0, 0, 0, 0)

        addView(container, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0))
        gravity = Gravity.BOTTOM

        customRefreshImg = container?.findViewById(R.id.custom_refresh_img)
        customRefreshTxt = container?.findViewById(R.id.custom_refresh_txt)


        val animator = CubeGrid()
        customRefreshImg?.setIndeterminateDrawable(animator)


        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        currentMeasuredHeight = measuredHeight

        spriteContainer = animator


    }


}

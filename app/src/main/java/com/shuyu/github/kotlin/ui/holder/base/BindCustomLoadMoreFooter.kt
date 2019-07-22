package com.shuyu.github.kotlin.ui.holder.base

/**
 * Created by guoshuyu
 * Date: 2018-10-23
 */

import android.content.Context
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.ybq.android.spinkit.SpinKitView
import com.github.ybq.android.spinkit.sprite.SpriteContainer
import com.github.ybq.android.spinkit.style.ThreeBounce

import com.shuyu.commonrecycler.xrecycler.base.BaseLoadMoreFooter
import com.shuyu.github.kotlin.R


/**
 * 继承BaseLoadMoreFooter的LoadMore控件
 * Created by guoshuyu on 2017/1/8.
 */

open class BindCustomLoadMoreFooter : BaseLoadMoreFooter {

    private var mImageView: SpinKitView? = null

    private var mText: TextView? = null

    private var spriteContainer: SpriteContainer? = null

    private var prevState = BaseLoadMoreFooter.STATE_LOADING

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
    }


    /**
     * 继承，必须要时需要实现样式
     * @param style
     */
    override fun setProgressStyle(style: Int) {}

    /**
     * 继承，根据状态调整显示效果
     * @param state
     */
    override fun setState(state: Int) {
        when (state) {
            BaseLoadMoreFooter.STATE_LOADING -> {
                mImageView?.visibility = View.VISIBLE
                mText?.text = context.getText(R.string.listview_loading)
                this.visibility = View.VISIBLE
                spriteContainer?.start()
            }
            BaseLoadMoreFooter.STATE_COMPLETE -> {
                if(prevState == BaseLoadMoreFooter.STATE_NOMORE) {
                    return
                }
                mText?.text = context.getText(R.string.listview_loading)
                this.visibility = View.GONE
                spriteContainer?.stop()
            }
            BaseLoadMoreFooter.STATE_NOMORE -> {
                mText?.text = context.getText(R.string.nomore_loading)
                mImageView?.visibility = View.GONE
                this.visibility = View.VISIBLE
                spriteContainer?.stop()
            }
        }
        prevState = state
    }

    /**
     * 初始化view
     */
    private fun initView() {
        gravity = Gravity.CENTER

        layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        setPadding(0, resources.getDimension(R.dimen.textandiconmargin).toInt(), 0,
                3 * resources.getDimension(R.dimen.textandiconmargin).toInt())


        mImageView = SpinKitView(context)
        mImageView?.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        mImageView?.setColor(ContextCompat.getColor(context, R.color.colorPrimary))

        val animator = ThreeBounce()
        mImageView?.setIndeterminateDrawable(animator)



        addView(mImageView)

        mText = TextView(context)
        mText?.text = context.getText(R.string.listview_loading)
        mText?.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
        val res = resources
        val fontSize = res.getDimension(R.dimen.smallTextSize)
        mText?.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)


        val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(resources.getDimension(R.dimen.textandiconmargin).toInt(), 0, 0, 0)

        mText?.layoutParams = layoutParams
        addView(mText)

        spriteContainer = animator
    }
}
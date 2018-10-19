package com.shuyu.github.kotlin.holder

import android.animation.AnimatorSet
import android.content.Context
import android.databinding.ViewDataBinding
import android.view.View
import com.shuyu.commonrecycler.BindRecyclerBaseHolder
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.utils.CommonUtils
import com.shuyu.github.kotlin.databinding.LayoutEventItemBinding
import kotlinx.android.synthetic.main.layout_event_item.view.*


class EventHolder(context: Context, private val v: View, private val dataBing: ViewDataBinding) : BindRecyclerBaseHolder(context, v) {


    /**
     * 必须实现的方法，onCreateViewHolder时调用，
     *
     * @param v layoutId实例后的View
     */
    override fun createView(v: View) {
    }

    /**
     * 必须实现的方法，onBindViewHolder时调用
     *
     * @param model    对应holder的数据实体
     * @param position 位置
     */
    override fun onBind(model: Any, position: Int) {
        val eventModel = model as EventUIModel
        (dataBing as LayoutEventItemBinding).eventUIModel = eventModel
    }

    /**
     * 选择继承，默认返回null，实现后可返回item动画
     */
    override fun getAnimator(view: View): AnimatorSet? {
        return null
    }

    companion object {
        val ID = R.layout.layout_event_item
    }
}

data class EventUIModel(var username: String = "User", var image: String = "",
                        var action: String = "Test", var des: String = "Test", var time:String="---")
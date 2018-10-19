package com.shuyu.github.kotlin.holder

import android.content.Context
import android.databinding.ViewDataBinding
import android.view.View
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.LayoutEventItemBinding
import com.shuyu.github.kotlin.holder.base.DataBindingHolder

/**
 * 事件item
 */
class EventHolder(context: Context, v: View, dataBing: ViewDataBinding) : DataBindingHolder<EventUIModel, LayoutEventItemBinding>(context, v, dataBing) {


    override fun onBind(model: EventUIModel, position: Int, dataBing: LayoutEventItemBinding) {
        dataBing.eventUIModel = model
    }

    companion object {
        const val ID = R.layout.layout_event_item
    }
}

data class EventUIModel(var username: String = "User", var image: String = "",
                        var action: String = "Test", var des: String = "Test", var time: String = "---")
package com.shuyu.github.kotlin.ui.holder

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.LayoutEventItemBinding
import com.shuyu.github.kotlin.model.ui.EventUIModel
import com.shuyu.github.kotlin.module.person.PersonActivity
import com.shuyu.github.kotlin.ui.holder.base.DataBindingHolder

/**
 * 事件item
 */
class EventHolder(context: Context,private val v: View, dataBing: ViewDataBinding) : DataBindingHolder<EventUIModel, LayoutEventItemBinding>(context, v, dataBing) {


    override fun createView(v: View) {
        super.createView(v)
    }

    override fun onBind(model: EventUIModel, position: Int, dataBing: LayoutEventItemBinding) {
        dataBing.eventUIModel = model
        dataBing.eventUserImg.setOnClickListener {
            PersonActivity.gotoPersonInfo(model.username)
        }
    }

    companion object {
        const val ID = R.layout.layout_event_item
    }
}
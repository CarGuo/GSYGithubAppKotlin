package com.shuyu.github.kotlin.ui.holder

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.LayoutEmptyBinding
import com.shuyu.github.kotlin.model.ui.EmptyUIModel
import com.shuyu.github.kotlin.ui.holder.base.DataBindingHolder


class EmptyHolder(context: Context, private val v: View, dataBing: ViewDataBinding) : DataBindingHolder<EmptyUIModel, LayoutEmptyBinding>(context, v, dataBing) {


    override fun createView(v: View) {
        super.createView(v)
    }

    override fun onBind(model: EmptyUIModel, position: Int, dataBing: LayoutEmptyBinding) {

    }

    companion object {
        const val ID = R.layout.layout_empty
    }
}
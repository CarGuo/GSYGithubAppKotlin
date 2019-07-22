package com.shuyu.github.kotlin.ui.holder.base

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import com.shuyu.commonrecycler.BindRecyclerBaseHolder

/**
 * 基类数据绑定Holder，拓展了对DataBinding支持
 * Created by guoshuyu
 * Date: 2018-10-19
 */
abstract class DataBindingHolder<T, D>(context: Context, v: View, private val dataBing: ViewDataBinding) : BindRecyclerBaseHolder(context, v) {

    override fun createView(v: View) {
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBind(model: Any, position: Int) {
        onBind(model as T, position, dataBing as D)
    }

    abstract fun onBind(model: T, position: Int, dataBing: D)
}

package com.shuyu.github.kotlin.ui.holder

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.LayoutPushItemBinding
import com.shuyu.github.kotlin.model.ui.FileUIModel
import com.shuyu.github.kotlin.ui.holder.base.DataBindingHolder


/**
 * 提交文件显示item
 */
class PushHolder(context: Context, private val v: View, dataBing: ViewDataBinding) : DataBindingHolder<FileUIModel, LayoutPushItemBinding>(context, v, dataBing) {


    override fun onBind(model: FileUIModel, position: Int, dataBing: LayoutPushItemBinding) {
        dataBing.fileUIModel = model
    }

    companion object {
        const val ID = R.layout.layout_push_item
    }
}
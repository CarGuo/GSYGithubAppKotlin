package com.shuyu.github.kotlin.ui.holder.base

import android.content.Context
import android.databinding.ViewDataBinding
import android.view.View
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.LayoutFileItemBinding
import com.shuyu.github.kotlin.model.ui.FileUIModel


/**
 * 事件item
 */
class FileHolder(context: Context, private val v: View, dataBing: ViewDataBinding) : DataBindingHolder<FileUIModel, LayoutFileItemBinding>(context, v, dataBing) {


    override fun onBind(model: FileUIModel, position: Int, dataBing: LayoutFileItemBinding) {
        dataBing.fileUIModel = model
    }

    companion object {
        const val ID = R.layout.layout_file_item
    }
}
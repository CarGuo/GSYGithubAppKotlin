package com.shuyu.github.kotlin.ui.holder

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.LayoutFileItemBinding
import com.shuyu.github.kotlin.model.ui.FileUIModel
import com.shuyu.github.kotlin.ui.holder.base.DataBindingHolder


/**
 * 文件显示item
 */
class FileHolder(context: Context, private val v: View, dataBing: ViewDataBinding) : DataBindingHolder<FileUIModel, LayoutFileItemBinding>(context, v, dataBing) {


    override fun onBind(model: FileUIModel, position: Int, dataBing: LayoutFileItemBinding) {
        dataBing.fileUIModel = model
    }

    companion object {
        const val ID = R.layout.layout_file_item
    }
}
package com.shuyu.github.kotlin.ui.holder

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.LayoutCommitItemBinding
import com.shuyu.github.kotlin.model.ui.CommitUIModel
import com.shuyu.github.kotlin.ui.holder.base.DataBindingHolder

/**
 * 事件item
 */
class CommitHolder(context: Context, private val v: View, dataBing: ViewDataBinding) : DataBindingHolder<CommitUIModel, LayoutCommitItemBinding>(context, v, dataBing) {


    override fun createView(v: View) {
        super.createView(v)
    }

    override fun onBind(model: CommitUIModel, position: Int, dataBing: LayoutCommitItemBinding) {
        dataBing.commitUIModel = model
    }

    companion object {
        const val ID = R.layout.layout_commit_item
    }
}
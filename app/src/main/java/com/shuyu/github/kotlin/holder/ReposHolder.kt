package com.shuyu.github.kotlin.holder

import android.content.Context
import android.databinding.ViewDataBinding
import android.view.View
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.LayoutReposItemBinding
import com.shuyu.github.kotlin.holder.base.DataBindingHolder
import com.shuyu.github.kotlin.model.ui.ReposUIModel


/**
 * 事件item
 */
class ReposHolder(context: Context, v: View, dataBing: ViewDataBinding) : DataBindingHolder<ReposUIModel, LayoutReposItemBinding>(context, v, dataBing) {


    override fun onBind(model: ReposUIModel, position: Int, dataBing: LayoutReposItemBinding) {
        dataBing.reposUIModel = model
    }

    companion object {
        const val ID = R.layout.layout_repos_item
    }
}
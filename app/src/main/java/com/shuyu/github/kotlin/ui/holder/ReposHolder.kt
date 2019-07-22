package com.shuyu.github.kotlin.ui.holder

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.LayoutReposItemBinding
import com.shuyu.github.kotlin.model.ui.ReposUIModel
import com.shuyu.github.kotlin.module.person.PersonActivity
import com.shuyu.github.kotlin.ui.holder.base.DataBindingHolder
import kotlinx.android.synthetic.main.layout_repos_item.view.*


/**
 * 仓库item
 */
class ReposHolder(context: Context, private val v: View, dataBing: ViewDataBinding) : DataBindingHolder<ReposUIModel, LayoutReposItemBinding>(context, v, dataBing) {


    override fun onBind(model: ReposUIModel, position: Int, dataBing: LayoutReposItemBinding) {
        dataBing.reposUIModel = model
        v.repos_user_img.setOnClickListener {
            PersonActivity.gotoPersonInfo(model.ownerName)
        }
    }

    companion object {
        const val ID = R.layout.layout_repos_item
    }
}
package com.shuyu.github.kotlin.ui.holder

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.LayoutUserItemBinding
import com.shuyu.github.kotlin.model.ui.UserUIModel
import com.shuyu.github.kotlin.ui.holder.base.DataBindingHolder


/**
 * 用户item
 */
class UserHolder(context: Context, private val v: View, dataBing: ViewDataBinding) : DataBindingHolder<UserUIModel, LayoutUserItemBinding>(context, v, dataBing) {

    override fun onBind(model: UserUIModel, position: Int, dataBing: LayoutUserItemBinding) {
        dataBing.userUIModel = model
    }

    companion object {
        const val ID = R.layout.layout_user_item
    }
}
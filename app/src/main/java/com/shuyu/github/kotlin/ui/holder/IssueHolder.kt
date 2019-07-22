package com.shuyu.github.kotlin.ui.holder

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.LayoutIssueItemBinding
import com.shuyu.github.kotlin.model.ui.IssueUIModel
import com.shuyu.github.kotlin.module.person.PersonActivity
import com.shuyu.github.kotlin.ui.holder.base.DataBindingHolder
import kotlinx.android.synthetic.main.layout_issue_item.view.*

/**
 * Issue item
 */
class IssueHolder(context: Context, private val v: View, dataBing: ViewDataBinding) : DataBindingHolder<IssueUIModel, LayoutIssueItemBinding>(context, v, dataBing) {


    override fun createView(v: View) {
        super.createView(v)
    }

    override fun onBind(model: IssueUIModel, position: Int, dataBing: LayoutIssueItemBinding) {
        dataBing.issueUIModel = model
        v.issue_user_img.setOnClickListener {
            PersonActivity.gotoPersonInfo(model.username)
        }
    }

    companion object {
        const val ID = R.layout.layout_issue_item
    }
}
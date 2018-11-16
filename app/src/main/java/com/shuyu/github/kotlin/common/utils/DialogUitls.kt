package com.shuyu.github.kotlin.common.utils

import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.OnItemClickListener
import com.orhanobut.dialogplus.ViewHolder
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.ui.adapter.TextListAdapter
import kotlinx.android.synthetic.main.layout_issue_edit_dialog.view.*
import org.jetbrains.anko.toast

/**
 * 拓展Context显示Dialog
 * Created by guoshuyu
 * Date: 2018-10-31
 */

fun Context.showIssueEditDialog(title: String, needEditTitle: Boolean, editTitle: String?, editContent: String?, listener: IssueDialogClickListener?) {
    val contentView = LayoutInflater.from(this).inflate(R.layout.layout_issue_edit_dialog, null, false)
    val height = (Resources.getSystem().displayMetrics.heightPixels * 0.6).toInt()
    contentView.issue_dialog_content_layout.layoutParams.height = height
    contentView.issue_dialog_markdown_list.editText = contentView.issue_dialog_edit_content
    val dialog = DialogPlus.newDialog(this)
            .setContentHolder(ViewHolder(contentView))
            .setCancelable(false)
            .setContentBackgroundResource(R.color.transparent)
            .setGravity(Gravity.CENTER)
            .setMargin(30.dp, 0, 30.dp, 0)
            .create()

    contentView.issue_dialog_edit_title.visibility = if (needEditTitle) {
        View.VISIBLE
    } else {
        View.GONE
    }

    contentView.issue_dialog_title.text = title

    editTitle?.apply { contentView.issue_dialog_edit_title.setText(this) }
    editContent?.apply { contentView.issue_dialog_edit_content.setText(this) }

    contentView.issue_dialog_edit_ok.setOnClickListener {
        val titleText = contentView.issue_dialog_edit_title.text?.toString()
        val contentText = contentView.issue_dialog_edit_content.text?.toString()
        if (needEditTitle && titleText.isNullOrBlank()) {
            toast(R.string.issueTitleEmpty)
            return@setOnClickListener
        }
        if (contentText.isNullOrBlank()) {
            toast(R.string.issueContentEmpty)
            return@setOnClickListener
        }
        listener?.onConfirm(dialog, title, titleText, contentText)

    }
    contentView.issue_dialog_edit_cancel.setOnClickListener {
        dialog.dismiss()
    }
    dialog.show()
}


fun Context.showOptionSelectDialog(dataList: ArrayList<String>, onItemClickListener: OnItemClickListener) {
    val dialog = DialogPlus.newDialog(this)
            .setAdapter(TextListAdapter(this, dataList))
            .setGravity(Gravity.CENTER)
            .setOnItemClickListener(onItemClickListener)
            .setExpanded(false)
            .create()
    dialog.show()
}

interface IssueDialogClickListener {
    fun onConfirm(dialog: DialogPlus, title: String, editTitle: String?, editContent: String?)
}
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
import com.shuyu.github.kotlin.databinding.LayoutIssueEditDialogBinding
import com.shuyu.github.kotlin.ui.adapter.TextListAdapter
import org.jetbrains.anko.toast

/**
 * 拓展Context显示Dialog
 * Created by guoshuyu
 * Date: 2018-10-31
 */

fun Context.showIssueEditDialog(title: String, needEditTitle: Boolean, editTitle: String?, editContent: String?, listener: IssueDialogClickListener?) {
    val contentView = LayoutIssueEditDialogBinding.inflate(LayoutInflater.from(this));
    val height = (Resources.getSystem().displayMetrics.heightPixels * 0.6).toInt()
    contentView.issueDialogContentLayout.layoutParams.height = height
    contentView.issueDialogMarkdownList.editText = contentView.issueDialogEditContent
    val dialog = DialogPlus.newDialog(this)
            .setContentHolder(ViewHolder(contentView.root))
            .setCancelable(false)
            .setContentBackgroundResource(R.color.transparent)
            .setGravity(Gravity.CENTER)
            .setMargin(30.dp, 0, 30.dp, 0)
            .create()

    contentView.issueDialogTitle.visibility = if (needEditTitle) {
        View.VISIBLE
    } else {
        View.GONE
    }

    contentView.issueDialogEditTitle.setText(title)

    editTitle?.apply { contentView.issueDialogEditTitle.setText(this) }
    editContent?.apply { contentView.issueDialogEditContent.setText(this) }

    contentView.issueDialogEditOk.setOnClickListener {
        val titleText = contentView.issueDialogEditTitle.text?.toString()
        val contentText = contentView.issueDialogEditContent.text?.toString()
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
    contentView.issueDialogEditCancel.setOnClickListener {
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
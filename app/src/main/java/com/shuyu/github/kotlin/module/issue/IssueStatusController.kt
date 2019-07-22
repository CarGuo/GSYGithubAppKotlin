package com.shuyu.github.kotlin.module.issue

import android.content.Context
import android.widget.AdapterView
import com.orhanobut.dialogplus.DialogPlus
import com.shuyu.commonrecycler.BindSuperAdapter
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.net.ResultCallBack
import com.shuyu.github.kotlin.common.utils.IssueDialogClickListener
import com.shuyu.github.kotlin.common.utils.showIssueEditDialog
import com.shuyu.github.kotlin.model.ui.IssueUIModel
import com.shuyu.github.kotlin.ui.view.GSYControlBar

class IssueStatusController(private val context: Context, private val adapter: BindSuperAdapter?,
                            private val issueDetailFragment: IssueDetailFragment, private val controlBar: GSYControlBar) : IssueDialogClickListener {


    override fun onConfirm(dialog: DialogPlus, title: String, editTitle: String?, editContent: String?) {
        when {
            title.contains(context.getString(R.string.issueComment)) -> {
                issueDetailFragment.getViewModel().commentIssue(context!!, editContent!!, object : ResultCallBack<IssueUIModel> {
                    override fun onSuccess(result: IssueUIModel?) {
                        dialog.dismiss()
                        issueDetailFragment.getRecyclerView()?.layoutManager?.scrollToPosition(adapter?.dataList!!.size - 1)
                    }
                })
            }
            title.contains(context.getString(R.string.issueEdit)) -> {
                issueDetailFragment.getViewModel().editIssue(context!!, editTitle!!, editContent!!)
                dialog.dismiss()
            }
        }
    }

    fun initControlBar(issueUIModel: IssueUIModel?) {
        if (issueUIModel == null) {
            return
        }
        val dataList = getControlList(issueUIModel)
        val issueInfo = issueDetailFragment.getViewModel().issueUIModel
        controlBar.list.clear()
        controlBar.list.addAll(dataList)
        controlBar.listView.adapter?.notifyDataSetChanged()
        controlBar.itemClick = AdapterView.OnItemClickListener { _, _, position, _ ->
            val item = controlBar.list[position]
            when {
                item.contains(context.getString(R.string.issueComment)) -> {
                    context.showIssueEditDialog(context.getString(R.string.issueComment), false, "", "", this)
                }
                item.contains(context.getString(R.string.issueEdit)) -> {
                    context.showIssueEditDialog(context.getString(R.string.issueEdit), true, issueInfo.action, issueInfo.content, this)
                }
                item.contains(context.getString(R.string.issueClose)) -> {
                    issueDetailFragment.getViewModel().changeIssueStatus(context, "closed")
                }
                item.contains(context.getString(R.string.issueOpen)) -> {
                    issueDetailFragment.getViewModel().changeIssueStatus(context, "open")
                }
                item.contains(context.getString(R.string.issueUnlock)) -> {
                    issueDetailFragment.getViewModel().lockIssueStatus(context, false)
                }
                item.contains(context.getString(R.string.issueLocked)) -> {
                    issueDetailFragment.getViewModel().lockIssueStatus(context, true)
                }
            }
        }
    }

    private fun getControlList(issueUIModel: IssueUIModel): ArrayList<String> {
        val list = arrayListOf(context.getString(R.string.issueComment), context.getString(R.string.issueEdit))

        val status = if (issueUIModel.status == "open") {
            context.getString(R.string.issueClose)
        } else {
            context.getString(R.string.issueOpen)
        }
        val lock = if (issueUIModel.locked) {
            context.getString(R.string.issueUnlock)
        } else {
            context.getString(R.string.issueLocked)
        }
        list.add(status)
        list.add(lock)
        return list

    }
}
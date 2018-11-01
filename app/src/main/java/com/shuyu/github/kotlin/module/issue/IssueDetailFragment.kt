package com.shuyu.github.kotlin.module.issue

import android.arch.lifecycle.Observer
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import androidx.core.os.postDelayed
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.OnItemClickListener
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.net.ResultCallBack
import com.shuyu.github.kotlin.common.utils.IssueDialogClickListener
import com.shuyu.github.kotlin.common.utils.copy
import com.shuyu.github.kotlin.common.utils.showIssueEditDialog
import com.shuyu.github.kotlin.common.utils.showOptionSelectDialog
import com.shuyu.github.kotlin.databinding.FragmentIssueDetailBinding
import com.shuyu.github.kotlin.databinding.LayoutIssueHeaderBinding
import com.shuyu.github.kotlin.di.ARouterInjectable
import com.shuyu.github.kotlin.model.ui.IssueUIModel
import com.shuyu.github.kotlin.module.ARouterAddress
import com.shuyu.github.kotlin.module.base.BaseListFragment
import com.shuyu.github.kotlin.module.person.PersonActivity
import com.shuyu.github.kotlin.ui.holder.IssueCommentHolder
import com.shuyu.github.kotlin.ui.holder.base.GSYDataBindingComponent
import kotlinx.android.synthetic.main.fragment_issue_detail.*
import org.jetbrains.anko.toast

/**
 * Created by guoshuyu
 * Date: 2018-10-24
 */

@Route(path = ARouterAddress.IssueDetailFragment)
class IssueDetailFragment : BaseListFragment<FragmentIssueDetailBinding, IssueDetailViewModel>(), ARouterInjectable, IssueDialogClickListener {

    @Autowired
    @JvmField
    var userName = ""

    @Autowired
    @JvmField
    var reposName = ""


    @Autowired
    @JvmField
    var issueNumber = 0

    override fun getLayoutId(): Int {
        return R.layout.fragment_issue_detail
    }

    override fun onCreateView(mainView: View?) {
        super.onCreateView(mainView)
        getViewModel().userName = userName
        getViewModel().reposName = reposName
        getViewModel().issueNumber = issueNumber
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewModel().liveIssueModel.observe(this, Observer { result ->
            initControlBar(result)
        })
    }

    override fun onItemClick(context: Context, position: Int) {
        super.onItemClick(context, position)
        val item = adapter?.dataList?.get(position) as IssueUIModel
        val list = arrayListOf(getString(R.string.copyComment), getString(R.string.issueCommentEdit), getString(R.string.issueCommentDelete))
        val itemListener = OnItemClickListener { dialog, _, _, p ->
            when {
                list[p].contains(getString(R.string.copyComment)) -> {
                    context.copy(item.action)
                    dialog.dismiss()
                    context.toast(R.string.hadCopy)

                }
                list[p].contains(getString(R.string.issueCommentEdit)) -> {
                    dialog.dismiss()
                    Handler().postDelayed(1000) {
                        activity?.showIssueEditDialog(getString(R.string.issueCommentEdit), false, position.toString(), item.action, object : IssueDialogClickListener {
                            override fun onConfirm(dialog: DialogPlus, title: String, editTitle: String?, editContent: String?) {
                                val currentPosition = editTitle!!.toInt()
                                val currentItem = adapter?.dataList!![currentPosition] as IssueUIModel
                                currentItem.action = editContent ?: ""
                                getViewModel().editComment(context, currentItem.status, currentItem)
                                dialog.dismiss()
                            }
                        })
                    }
                }
                list[p].contains(getString(R.string.issueCommentDelete)) -> {
                    dialog.dismiss()
                    getViewModel().deleteComment(context, item.status, object : ResultCallBack<String> {
                        override fun onSuccess(result: String?) {
                            adapter?.dataList?.remove(position)
                            adapter?.notifyDataSetChanged()
                        }
                    })
                }
            }
        }
        context.showOptionSelectDialog(list, itemListener)
    }

    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = true

    override fun getRecyclerView(): RecyclerView? = baseRecycler

    override fun getViewModelClass(): Class<IssueDetailViewModel> = IssueDetailViewModel::class.java

    override fun bindHolder(manager: BindSuperAdapterManager) {
        val binding: LayoutIssueHeaderBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_issue_header,
                null, false, GSYDataBindingComponent())
        binding.issueUIModel = getViewModel().issueUIModel

        binding.issueHeaderImage.setOnClickListener {
            PersonActivity.gotoPersonInfo(getViewModel().issueUIModel.username)
        }

        manager.addHeaderView(binding.root)

        manager.bind(IssueUIModel::class.java, IssueCommentHolder.ID, IssueCommentHolder::class.java)
    }


    override fun onConfirm(dialog: DialogPlus, title: String, editTitle: String?, editContent: String?) {
        when {
            title.contains(getString(R.string.issueComment)) -> {
                getViewModel().commentIssue(context!!, editContent!!, object : ResultCallBack<IssueUIModel> {
                    override fun onSuccess(result: IssueUIModel?) {
                        dialog.dismiss()
                        getRecyclerView()?.layoutManager?.scrollToPosition(adapter?.dataList!!.size - 1)
                    }
                })
            }
            title.contains(getString(R.string.issueEdit)) -> {
                getViewModel().editIssue(context!!, editTitle!!, editContent!!)
                dialog.dismiss()
            }
        }
    }

    private fun initControlBar(issueUIModel: IssueUIModel?) {
        if (issueUIModel == null) {
            return
        }
        val dataList = getControlList(issueUIModel)
        val issueInfo = getViewModel().issueUIModel
        issue_detail_control_bar.list.clear()
        issue_detail_control_bar.list.addAll(dataList)
        issue_detail_control_bar.listView.adapter.notifyDataSetChanged()
        issue_detail_control_bar.itemClick = AdapterView.OnItemClickListener { _, _, position, _ ->
            val item = issue_detail_control_bar.list[position]
            when {
                item.contains(getString(R.string.issueComment)) -> {
                    activity?.showIssueEditDialog(getString(R.string.issueComment), false, "", "", this)
                }
                item.contains(getString(R.string.issueEdit)) -> {
                    activity?.showIssueEditDialog(getString(R.string.issueEdit), true, issueInfo.action, issueInfo.content, this)
                }
                item.contains(getString(R.string.issueClose)) -> {
                    getViewModel().changeIssueStatus(activity!!, "closed")
                }
                item.contains(getString(R.string.issueOpen)) -> {
                    getViewModel().changeIssueStatus(activity!!, "open")
                }
                item.contains(getString(R.string.issueUnlock)) -> {
                    getViewModel().lockIssueStatus(activity!!, false)
                }
                item.contains(getString(R.string.issueLocked)) -> {
                    getViewModel().lockIssueStatus(activity!!, true)
                }
            }
        }
    }

    private fun getControlList(issueUIModel: IssueUIModel): ArrayList<String> {
        val list = arrayListOf(getString(R.string.issueComment), getString(R.string.issueEdit))

        val status = if (issueUIModel.status == "open") {
            getString(R.string.issueClose)
        } else {
            getString(R.string.issueOpen)
        }
        val lock = if (issueUIModel.locked) {
            getString(R.string.issueUnlock)
        } else {
            getString(R.string.issueLocked)
        }
        list.add(status)
        list.add(lock)
        return list

    }
}
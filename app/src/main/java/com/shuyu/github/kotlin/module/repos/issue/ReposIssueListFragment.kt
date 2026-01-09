package com.shuyu.github.kotlin.module.repos.issue

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.orhanobut.dialogplus.DialogPlus
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.net.ResultCallBack
import com.shuyu.github.kotlin.common.utils.IssueDialogClickListener
import com.shuyu.github.kotlin.common.utils.showIssueEditDialog
import com.shuyu.github.kotlin.databinding.FragmentReposIssueListBinding
import com.shuyu.github.kotlin.di.ARouterInjectable
import com.shuyu.github.kotlin.di.annotation.FragmentQualifier
import com.shuyu.github.kotlin.model.ui.IssueUIModel
import com.shuyu.github.kotlin.module.ARouterAddress
import com.shuyu.github.kotlin.module.base.BaseListFragment
import com.shuyu.github.kotlin.module.issue.IssueDetailActivity
import com.shuyu.github.kotlin.ui.holder.IssueHolder
import com.shuyu.github.kotlin.ui.view.GSYTabBar
import com.google.android.material.tabs.TabLayout
import javax.inject.Inject

/**
 * Created by guoshuyu
 * Date: 2018-10-25
 */

@Route(path = ARouterAddress.ReposDetailIssueList)
class ReposIssueListFragment :
    BaseListFragment<FragmentReposIssueListBinding, ReposIssueListViewModel>(), ARouterInjectable,
    IssueDialogClickListener, TabLayout.OnTabSelectedListener {


    @Autowired
    @JvmField
    var reposName = ""

    @Autowired
    @JvmField
    var userName = ""

    @field:FragmentQualifier("IssueList")
    @Inject
    lateinit var reposIssueListTab: MutableList<GSYTabBar.Model>


    @field:FragmentQualifier("IssueList")
    @Inject
    lateinit var statusList: ArrayList<String>

    override fun getLayoutId(): Int {
        return R.layout.fragment_repos_issue_list
    }

    override fun onItemClick(context: Context, position: Int) {
        super.onItemClick(context, position)
        val eventUIModel = adapter?.dataList?.get(position) as IssueUIModel
        eventUIModel.apply {
            IssueDetailActivity.gotoIssueDetail(userName, reposName, this.issueNum)
        }
    }

    override fun onCreateView(mainView: View?) {
        super.onCreateView(mainView)
        getViewModel().userName = userName
        getViewModel().reposName = reposName
        getViewModel().status = statusList[0]
        binding?.issueListViewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.issueListNavigationTabBar?.models = reposIssueListTab
        binding?.issueListNavigationTabBar?.addOnTabSelectedListener(this)
        binding?.issueListNavigationTabBar?.getTabAt(0)?.select()
        binding?.issueListCreateButton?.setOnClickListener {
            activity?.showIssueEditDialog(getString(R.string.issue), true, "", "", this)
        }
    }

    override fun getViewModelClass(): Class<ReposIssueListViewModel> =
        ReposIssueListViewModel::class.java

    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = true

    override fun getRecyclerView(): RecyclerView? = binding?.baseRecycler

    override fun bindHolder(manager: BindSuperAdapterManager) {
        manager.bind(IssueUIModel::class.java, IssueHolder.ID, IssueHolder::class.java)
    }

    override fun onConfirm(
        dialog: DialogPlus, title: String, editTitle: String?, editContent: String?
    ) {
        getViewModel().createIssue(
            requireActivity(),
            editTitle!!,
            editContent!!,
            object : ResultCallBack<IssueUIModel> {
                override fun onSuccess(result: IssueUIModel?) {
                    result?.apply {
                        adapter?.dataList?.add(0, result)
                        adapter?.notifyDataSetChanged()
                        getRecyclerView()?.layoutManager?.scrollToPosition(0)
                        dialog.dismiss()
                    }
                }
            })
    }

    override fun refreshComplete() {
        super.refreshComplete()
        binding?.issueListNavigationTabBar?.isTouchEnable = true
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        val index = tab?.position ?: 0
        binding?.issueListNavigationTabBar?.isTouchEnable = false
        getViewModel().status = statusList[index]
        showRefresh()
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }
}
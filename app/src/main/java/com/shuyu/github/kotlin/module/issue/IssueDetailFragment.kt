package com.shuyu.github.kotlin.module.issue

import android.arch.lifecycle.Observer
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.github.kotlin.R
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

/**
 * Created by guoshuyu
 * Date: 2018-10-24
 */

@Route(path = ARouterAddress.IssueDetailFragment)
class IssueDetailFragment : BaseListFragment<FragmentIssueDetailBinding, IssueDetailViewModel>(), ARouterInjectable {

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


    private fun initControlBar(issueUIModel: IssueUIModel?) {
        if (issueUIModel == null) {
            return
        }
        val dataList = getControlList(issueUIModel)
        issue_detail_control_bar.list.clear()
        issue_detail_control_bar.list.addAll(dataList)
        issue_detail_control_bar.listView.adapter.notifyDataSetChanged()
        issue_detail_control_bar.itemClick = AdapterView.OnItemClickListener { _, _, position, _ ->
            val item = issue_detail_control_bar.list[position]
            when {
                item.contains(getString(R.string.issueComment)) -> {

                }
                item.contains(getString(R.string.issueEdit)) -> {

                }
                item.contains(getString(R.string.issueClose)) -> {

                }
                item.contains(getString(R.string.issueOpen)) -> {

                }
                item.contains(getString(R.string.issueUnlock)) -> {

                }
                item.contains(getString(R.string.issueLocked)) -> {

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
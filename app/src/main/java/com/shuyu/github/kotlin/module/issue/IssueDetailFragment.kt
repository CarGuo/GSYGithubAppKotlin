package com.shuyu.github.kotlin.module.issue

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.FragmentListBinding
import com.shuyu.github.kotlin.databinding.LayoutIssueHeaderBinding
import com.shuyu.github.kotlin.di.ARouterInjectable
import com.shuyu.github.kotlin.model.ui.IssueUIModel
import com.shuyu.github.kotlin.module.ARouterAddress
import com.shuyu.github.kotlin.module.base.BaseListFragment
import com.shuyu.github.kotlin.ui.holder.IssueHolder
import com.shuyu.github.kotlin.ui.holder.base.GSYDataBindingComponent
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * Created by guoshuyu
 * Date: 2018-10-24
 */

@Route(path = ARouterAddress.IssueDetailFragment)
class IssueDetailFragment : BaseListFragment<FragmentListBinding, IssueDetailViewModel>(), ARouterInjectable {

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
        return R.layout.fragment_list
    }

    override fun onCreateView(mainView: View?) {
        super.onCreateView(mainView)
        getViewModel().userName = userName
        getViewModel().reposName = reposName
        getViewModel().issueNumber = issueNumber
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
        manager.addHeaderView(binding.root)

        manager.bind(IssueUIModel::class.java, IssueHolder.ID, IssueHolder::class.java)
    }

}
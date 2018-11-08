package com.shuyu.github.kotlin.module.repos.action

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.FragmentListBinding
import com.shuyu.github.kotlin.databinding.LayoutReposHeaderBinding
import com.shuyu.github.kotlin.di.ARouterInjectable
import com.shuyu.github.kotlin.model.ui.EventUIModel
import com.shuyu.github.kotlin.module.ARouterAddress
import com.shuyu.github.kotlin.module.base.BaseListFragment
import com.shuyu.github.kotlin.ui.holder.EventHolder
import com.shuyu.github.kotlin.ui.holder.base.GSYDataBindingComponent
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * Created by guoshuyu
 * Date: 2018-10-25
 */

@Route(path = ARouterAddress.ReposDetailActionList)
class ReposActionListFragment : BaseListFragment<FragmentListBinding, ReposActionViewModel>(), ARouterInjectable {

    @Autowired
    @JvmField
    var reposName = ""

    @Autowired
    @JvmField
    var userName = ""


    override fun getLayoutId(): Int {
        return R.layout.fragment_list
    }

    override fun onItemClick(context: Context, position: Int) {
        super.onItemClick(context, position)
    }

    override fun onCreateView(mainView: View?) {
        super.onCreateView(mainView)
        getViewModel().reposName = reposName
        getViewModel().userName = userName
    }

    override fun getViewModelClass(): Class<ReposActionViewModel> = ReposActionViewModel::class.java

    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = true

    override fun getRecyclerView(): RecyclerView? = baseRecycler

    override fun bindHolder(manager: BindSuperAdapterManager) {
        val binding: LayoutReposHeaderBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_repos_header,
                null, false, GSYDataBindingComponent())

        binding.reposUIModel = getViewModel().reposUIModel
        binding.actionViewModel = getViewModel()

        manager.addHeaderView(binding.root)

        manager.bind(EventUIModel::class.java, EventHolder.ID, EventHolder::class.java)
    }
}
package com.shuyu.github.kotlin.module.repos.action

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.FragmentListBinding
import com.shuyu.github.kotlin.databinding.LayoutReposHeaderBinding
import com.shuyu.github.kotlin.di.ARouterInjectable
import com.shuyu.github.kotlin.model.ui.CommitUIModel
import com.shuyu.github.kotlin.model.ui.EventUIModel
import com.shuyu.github.kotlin.module.ARouterAddress
import com.shuyu.github.kotlin.module.base.BaseListFragment
import com.shuyu.github.kotlin.module.push.PushDetailActivity
import com.shuyu.github.kotlin.ui.holder.CommitHolder
import com.shuyu.github.kotlin.ui.holder.EventHolder
import com.shuyu.github.kotlin.ui.holder.base.GSYDataBindingComponent
import devlight.io.library.ntb.NavigationTabBar
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * Created by guoshuyu
 * Date: 2018-10-25
 */

@Route(path = ARouterAddress.ReposDetailActionList)
class ReposActionListFragment : BaseListFragment<FragmentListBinding, ReposActionViewModel>(), ARouterInjectable, NavigationTabBar.OnTabBarSelectedIndexListener {

    @Autowired
    @JvmField
    var reposName = ""

    @Autowired
    @JvmField
    var userName = ""

    var headerBinding: LayoutReposHeaderBinding? = null


    override fun getLayoutId(): Int {
        return R.layout.fragment_list
    }

    override fun onItemClick(context: Context, position: Int) {
        super.onItemClick(context, position)
        val item = adapter?.dataList?.get(position)
        if (item is CommitUIModel) {
            PushDetailActivity.gotoPushDetail(userName, reposName, item.sha)
        }
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
        headerBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_repos_header,
                null, false, GSYDataBindingComponent())

        headerBinding?.reposUIModel = getViewModel().reposUIModel
        headerBinding?.actionViewModel = getViewModel()

        headerBinding?.reposActionTabBar?.models = listOf(
                NavigationTabBar.Model.Builder(null,
                        Color.parseColor("#00000000"))
                        .title(getString(R.string.reposActivity))
                        .build(),
                NavigationTabBar.Model.Builder(null,
                        Color.parseColor("#00000000"))
                        .title(getString(R.string.reposPush))
                        .build()
        )

        headerBinding?.reposActionTabBar?.onTabBarSelectedIndexListener = this
        headerBinding?.reposActionTabBar?.modelIndex = 0

        manager.addHeaderView(headerBinding!!.root)

        manager.bind(EventUIModel::class.java, EventHolder.ID, EventHolder::class.java)
        manager.bind(CommitUIModel::class.java, CommitHolder.ID, CommitHolder::class.java)
    }

    override fun refreshComplete() {
        super.refreshComplete()
        headerBinding?.reposActionTabBar?.isTouchEnable = true
    }

    override fun onEndTabSelected(model: NavigationTabBar.Model?, index: Int) {

    }

    override fun onStartTabSelected(model: NavigationTabBar.Model?, index: Int) {
        headerBinding?.reposActionTabBar?.isTouchEnable = false
        getViewModel().showType = index
        showRefresh()
    }
}
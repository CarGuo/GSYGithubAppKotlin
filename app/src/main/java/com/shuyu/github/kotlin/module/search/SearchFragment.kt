package com.shuyu.github.kotlin.module.search

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.FragmentSearchBinding
import com.shuyu.github.kotlin.di.annotation.FragmentQualifier
import com.shuyu.github.kotlin.model.ui.ReposUIModel
import com.shuyu.github.kotlin.model.ui.UserUIModel
import com.shuyu.github.kotlin.module.ARouterAddress
import com.shuyu.github.kotlin.module.base.BaseListFragment
import com.shuyu.github.kotlin.module.person.PersonActivity
import com.shuyu.github.kotlin.module.repos.ReposDetailActivity
import com.shuyu.github.kotlin.ui.holder.ReposHolder
import com.shuyu.github.kotlin.ui.holder.UserHolder
import devlight.io.library.ntb.NavigationTabBar
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

/**
 * 搜索
 * Created by guoshuyu
 * Date: 2018-11-02
 */
@Route(path = ARouterAddress.SearchFragment)
class SearchFragment : BaseListFragment<FragmentSearchBinding, SearchViewModel>(), NavigationTabBar.OnTabBarSelectedIndexListener {

    @field:FragmentQualifier("Search")
    @Inject
    lateinit var searchTabList: MutableList<NavigationTabBar.Model>

    var searchFilterController: SearchFilterController? = null

    override fun onCreateView(mainView: View?) {
        super.onCreateView(mainView)
        binding?.searchViewModel = getViewModel()
    }

    override fun onItemClick(context: Context, position: Int) {
        super.onItemClick(context, position)
        val item = adapter?.dataList?.get(position)
        when (item) {
            is UserUIModel -> {
                PersonActivity.gotoPersonInfo(item.login!!)
            }
            is ReposUIModel -> {
                ReposDetailActivity.gotoReposDetail(item.ownerName, item.repositoryName)
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //初始化搜索类型控件
        search_type_bar.models = searchTabList
        search_type_bar.onTabBarSelectedIndexListener = this
        search_type_bar.modelIndex = 0

        //初始化搜索过滤器
        searchFilterController = SearchFilterController(activity, getViewModel())
    }

    override fun getLayoutId(): Int = R.layout.fragment_search


    override fun getRecyclerView(): RecyclerView? = baseRecycler

    override fun enableRefresh(): Boolean = false

    override fun enableLoadMore(): Boolean = true

    override fun bindHolder(manager: BindSuperAdapterManager) {
        manager.bind(ReposUIModel::class.java, ReposHolder.ID, ReposHolder::class.java)
        manager.bind(UserUIModel::class.java, UserHolder.ID, UserHolder::class.java)
    }

    override fun getViewModelClass(): Class<SearchViewModel> = SearchViewModel::class.java

    override fun onRefresh() {
        super.onRefresh()
        getViewModel().refresh(activity!!)
    }

    override fun onLoadMore() {
        super.onLoadMore()
        getViewModel().loadMore(activity!!)
    }

    /**
     * 切换tab改变搜索类型
     */
    override fun onEndTabSelected(model: NavigationTabBar.Model?, index: Int) {
    }

    override fun onStartTabSelected(model: NavigationTabBar.Model?, index: Int) {
        getViewModel().type = if (index == 1) {
            SearchViewModel.USER
        } else {
            SearchViewModel.REPOSITORY
        }
        onRefresh()
    }
}
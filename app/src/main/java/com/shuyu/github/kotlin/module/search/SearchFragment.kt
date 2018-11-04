package com.shuyu.github.kotlin.module.search

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.FragmentSearchBinding
import com.shuyu.github.kotlin.di.annotation.FragmentQualifier
import com.shuyu.github.kotlin.module.ARouterAddress
import com.shuyu.github.kotlin.module.base.BaseListFragment
import devlight.io.library.ntb.NavigationTabBar
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

/**
 * Created by guoshuyu
 * Date: 2018-11-02
 */
@Route(path = ARouterAddress.SearchFragment)
class SearchFragment : BaseListFragment<FragmentSearchBinding, SearchViewModel>(), NavigationTabBar.OnTabBarSelectedIndexListener {
    
    @field:FragmentQualifier("Search")
    @Inject
    lateinit var searchTabList: MutableList<NavigationTabBar.Model>

    override fun onCreateView(mainView: View?) {
        super.onCreateView(mainView)
    }

    override fun onItemClick(context: Context, position: Int) {
        super.onItemClick(context, position)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_type_bar.models = searchTabList
        search_type_bar.onTabBarSelectedIndexListener = this
        search_type_bar.modelIndex = 0

    }

    override fun getLayoutId(): Int = R.layout.fragment_search


    override fun getRecyclerView(): RecyclerView? = baseRecycler


    override fun bindHolder(manager: BindSuperAdapterManager) {

    }

    override fun getViewModelClass(): Class<SearchViewModel> = SearchViewModel::class.java


    override fun onEndTabSelected(model: NavigationTabBar.Model?, index: Int) {
    }

    override fun onStartTabSelected(model: NavigationTabBar.Model?, index: Int) {
    }
}
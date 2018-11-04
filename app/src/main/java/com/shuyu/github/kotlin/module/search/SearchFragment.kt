package com.shuyu.github.kotlin.module.search

import android.content.Context
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.FragmentSearchBinding
import com.shuyu.github.kotlin.di.annotation.FragmentQualifier
import com.shuyu.github.kotlin.module.ARouterAddress
import com.shuyu.github.kotlin.module.base.BaseListFragment
import dagger.Provides
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

    var sortList = arrayListOf(
            DividerDrawerItem(),
            PrimaryDrawerItem().withIdentifier(1).withName("111").withSelectable(false).withEnabled(false),
            DividerDrawerItem(),
            SecondaryDrawerItem().withIdentifier(2).withName("111"),
            SecondaryDrawerItem().withIdentifier(2).withName("111")
    )

    var languageList = arrayListOf(
            DividerDrawerItem(),
            PrimaryDrawerItem().withIdentifier(1).withName("aaa").withSelectable(false).withEnabled(false),
            DividerDrawerItem(),
            SecondaryDrawerItem().withIdentifier(2).withName("aaa"),
            SecondaryDrawerItem().withIdentifier(2).withName("aaa"),
            SecondaryDrawerItem().withIdentifier(2).withName("aaa"),
            SecondaryDrawerItem().withIdentifier(2).withName("aaa"),
            SecondaryDrawerItem().withIdentifier(2).withName("aaa"),
            SecondaryDrawerItem().withIdentifier(2).withName("aaa"),
            SecondaryDrawerItem().withIdentifier(2).withName("aaa"),
            SecondaryDrawerItem().withIdentifier(2).withName("aaa"),
            SecondaryDrawerItem().withIdentifier(2).withName("aaa"),
            SecondaryDrawerItem().withIdentifier(2).withName("aaa")
    )
    var statusList = arrayListOf(
            DividerDrawerItem(),
            PrimaryDrawerItem().withIdentifier(1).withName("---").withSelectable(false).withEnabled(false),
            DividerDrawerItem(),
            SecondaryDrawerItem().withIdentifier(2).withName("---"),
            SecondaryDrawerItem().withIdentifier(2).withName("---")
    )

    var drawer: Drawer? = null

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

        val filterList = arrayListOf<IDrawerItem<*, *>>()
        filterList.addAll(sortList)
        filterList.addAll(languageList)
        filterList.addAll(statusList)

        drawer = DrawerBuilder()
                .withActivity(activity!!)
                .withDrawerGravity(GravityCompat.END)
                .withMultiSelect(true)
                .withDrawerItems(filterList)
                .addDrawerItems().build()
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
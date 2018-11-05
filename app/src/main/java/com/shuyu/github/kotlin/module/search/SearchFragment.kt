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
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
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
 * Created by guoshuyu
 * Date: 2018-11-02
 */
@Route(path = ARouterAddress.SearchFragment)
class SearchFragment : BaseListFragment<FragmentSearchBinding, SearchViewModel>(), NavigationTabBar.OnTabBarSelectedIndexListener {

    @field:FragmentQualifier("Search")
    @Inject
    lateinit var searchTabList: MutableList<NavigationTabBar.Model>

    companion object {
        const val SORT_ID = 1000L
        const val LANGUAGE_ID = 2000L
        const val STATUS_ID = 3000L
    }

    var sortList: ArrayList<IDrawerItem<*, *>> = arrayListOf(
            DividerDrawerItem(),
            PrimaryDrawerItem().withIdentifier(SORT_ID).withName(R.string.filterSort)
                    .withSelectable(false).withEnabled(false)
                    .withTextColorRes(R.color.subLightTextColor),
            DividerDrawerItem(),
            PrimaryDrawerItem().withIdentifier(SORT_ID + 1).withName("desc").withTag("desc").withSetSelected(true),
            PrimaryDrawerItem().withIdentifier(SORT_ID + 2).withName("asc").withTag("asc")
    )

    var languageList: ArrayList<IDrawerItem<*, *>> = arrayListOf(
            DividerDrawerItem(),
            PrimaryDrawerItem().withIdentifier(LANGUAGE_ID).withName(R.string.filterLanguage)
                    .withSelectable(false).withEnabled(false),
            DividerDrawerItem(),
            PrimaryDrawerItem().withIdentifier(LANGUAGE_ID + 1).withName(R.string.filterAll).withTag("").withSetSelected(true),
            PrimaryDrawerItem().withIdentifier(LANGUAGE_ID + 2).withName("Java").withTag("Java"),
            PrimaryDrawerItem().withIdentifier(LANGUAGE_ID + 3).withName("Dart").withTag("Dart"),
            PrimaryDrawerItem().withIdentifier(LANGUAGE_ID + 4).withName("Objective-C").withTag("Objective-C"),
            PrimaryDrawerItem().withIdentifier(LANGUAGE_ID + 5).withName("Swift").withTag("Swift"),
            PrimaryDrawerItem().withIdentifier(LANGUAGE_ID + 6).withName("PHP").withTag("PHP"),
            PrimaryDrawerItem().withIdentifier(LANGUAGE_ID + 7).withName("C++").withTag("C++"),
            PrimaryDrawerItem().withIdentifier(LANGUAGE_ID + 8).withName("C").withTag("C"),
            PrimaryDrawerItem().withIdentifier(LANGUAGE_ID + 9).withName("HTML").withTag("HTML"),
            PrimaryDrawerItem().withIdentifier(LANGUAGE_ID + 10).withName("CSS").withTag("CSS")
    )
    var statusList: ArrayList<IDrawerItem<*, *>> = arrayListOf(
            DividerDrawerItem(),
            PrimaryDrawerItem().withIdentifier(STATUS_ID).withName(R.string.filerType)
                    .withSelectable(false).withEnabled(false),
            DividerDrawerItem(),
            PrimaryDrawerItem().withIdentifier(STATUS_ID + 1).withName(R.string.best_match).withTag("best%20match").withSetSelected(true),
            PrimaryDrawerItem().withIdentifier(STATUS_ID + 2).withName(R.string.stars).withTag("stars"),
            PrimaryDrawerItem().withIdentifier(STATUS_ID + 3).withName(R.string.forks).withTag("forks"),
            PrimaryDrawerItem().withIdentifier(STATUS_ID + 4).withName(R.string.updated).withTag("updated")
    )

    var drawer: Drawer? = null

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
        search_type_bar.models = searchTabList
        search_type_bar.onTabBarSelectedIndexListener = this
        search_type_bar.modelIndex = 0

        val filterList = arrayListOf<IDrawerItem<*, *>>()
        filterList.addAll(statusList)
        filterList.addAll(sortList)
        filterList.addAll(languageList)

        fun clearSelect(clearList: ArrayList<IDrawerItem<*, *>>) {
            clearList.forEach {
                it.withSetSelected(false)
            }
        }
        drawer?.currentSelectedPosition
        drawer = DrawerBuilder()
                .withActivity(activity!!)
                .withDrawerGravity(GravityCompat.
                        END)
                .withDrawerItems(filterList)
                .withMultiSelect(true)
                .withOnDrawerItemClickListener { view, position, drawerItem ->
                    when (drawerItem.identifier / 1000 * 1000) {
                        SORT_ID -> {
                            clearSelect(sortList)
                            drawerItem.withSetSelected(true)
                        }
                        LANGUAGE_ID -> {
                            clearSelect(languageList)
                            drawerItem.withSetSelected(true)
                        }
                        STATUS_ID -> {
                            clearSelect(statusList)
                            drawerItem.withSetSelected(true)
                        }
                    }
                    drawer?.adapter?.notifyAdapterDataSetChanged()
                    true
                }.build()

        statusList[3].withSetSelected(true)
        sortList[3].withSetSelected(true)
        languageList[3].withSetSelected(true)

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
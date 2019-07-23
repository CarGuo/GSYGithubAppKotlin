package com.shuyu.github.kotlin.module.search

import android.app.Activity
import android.view.View
import androidx.core.view.GravityCompat
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.module.list.GeneralFilterController

/**
 * 搜索过滤控制器
 * Created by guoshuyu
 * Date: 2018-11-05
 */
class SearchFilterController(private val activity: Activity?, private val searchViewModel: SearchViewModel) {


    companion object {
        const val SORT_ID = 1000L
        const val LANGUAGE_ID = 2000L
        const val STATUS_ID = 3000L
    }

    val sortList: ArrayList<IDrawerItem<*>> = arrayListOf(
            DividerDrawerItem(),
            PrimaryDrawerItem().withIdentifier(SORT_ID).withName(R.string.filterSort)
                    .withSelectable(false).withEnabled(false)
                    .withTextColorRes(R.color.subLightTextColor),
            DividerDrawerItem(),
            PrimaryDrawerItem().withIdentifier(SORT_ID + 1).withName("desc").withTag("desc").withSelected(true),
            PrimaryDrawerItem().withIdentifier(SORT_ID + 2).withName("asc").withTag("asc")
    )

    val languageList: ArrayList<IDrawerItem<*>> = arrayListOf(
            DividerDrawerItem(),
            PrimaryDrawerItem().withIdentifier(LANGUAGE_ID).withName(R.string.filterLanguage)
                    .withSelectable(false).withEnabled(false),
            DividerDrawerItem(),
            PrimaryDrawerItem().withIdentifier(LANGUAGE_ID + 1).withName(R.string.filterAll).withTag("").withSelected(true),
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
    val statusList: ArrayList<IDrawerItem<*>> = arrayListOf(
            DividerDrawerItem(),
            PrimaryDrawerItem().withIdentifier(STATUS_ID).withName(R.string.filerType)
                    .withSelectable(false).withEnabled(false),
            DividerDrawerItem(),
            PrimaryDrawerItem().withIdentifier(STATUS_ID + 1).withName(R.string.best_match).withTag("best%20match").withSelected(true),
            PrimaryDrawerItem().withIdentifier(STATUS_ID + 2).withName(R.string.stars).withTag("stars"),
            PrimaryDrawerItem().withIdentifier(STATUS_ID + 3).withName(R.string.forks).withTag("forks"),
            PrimaryDrawerItem().withIdentifier(STATUS_ID + 4).withName(R.string.updated).withTag("updated")
    )


    var drawer: Drawer? = null

    init {

        val filterList = arrayListOf<IDrawerItem< *>>()
        filterList.addAll(statusList)
        filterList.addAll(sortList)
        filterList.addAll(languageList)

        fun clearSelect(clearList: ArrayList<IDrawerItem<*>>) {
            clearList.forEach {
                it.isSelected = false
            }
        }
        drawer?.currentSelectedPosition
        drawer = DrawerBuilder()
                .withActivity(activity!!)
                .withDrawerGravity(GravityCompat.END)
                .withDrawerItems(filterList)
                .withMultiSelect(true)

                .withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                    override fun onItemClick(view: View?, position: Int, drawerItem: IDrawerItem<*>): Boolean {

                        when (drawerItem.identifier / 1000 * 1000) {
                            SORT_ID -> {
                                clearSelect(sortList)
                                drawerItem.isSelected = true
                                searchViewModel.order = filterList[position].tag as String
                            }
                            LANGUAGE_ID -> {
                                clearSelect(languageList)
                                drawerItem.isSelected = true
                                searchViewModel.language = filterList[position].tag as String
                            }
                            STATUS_ID -> {
                                clearSelect(statusList)
                                drawerItem.isSelected = true
                                searchViewModel.sort = filterList[position].tag as String
                            }
                        }
                        drawer?.adapter?.notifyAdapterDataSetChanged()
                        drawer?.closeDrawer()
                        searchViewModel.refresh(activity)
                        return true
                    }
                }).build()

        statusList[3].isSelected = true
        sortList[3].isSelected = true
        languageList[3].isSelected = true

    }


}
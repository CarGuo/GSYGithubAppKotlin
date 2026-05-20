package com.shuyu.github.kotlin.module.list

import android.view.View
import androidx.core.view.GravityCompat
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.compat.DrawerBuilderShim
import com.shuyu.github.kotlin.common.compat.DrawerShim
import com.shuyu.github.kotlin.common.compat.withEnabled
import com.shuyu.github.kotlin.common.compat.withIdentifier
import com.shuyu.github.kotlin.common.compat.withName
import com.shuyu.github.kotlin.common.compat.withSelectable
import com.shuyu.github.kotlin.common.compat.withSelected
import com.shuyu.github.kotlin.common.compat.withTag
import com.shuyu.github.kotlin.module.base.BaseListFragment

/**
 * 通用列排序控制器
 * Created by guoshuyu
 * Date: 2018-11-05
 */
class GeneralFilterController(private val fragment: BaseListFragment<*, *>?, private val generalListViewModel: GeneralListViewModel) {


    companion object {
        const val STATUS_ID = 3000L
    }


    val statusList: ArrayList<IDrawerItem<*>> = arrayListOf(
            DividerDrawerItem(),
            PrimaryDrawerItem().withIdentifier(STATUS_ID).withName(R.string.filerType)
                    .withSelectable(false).withEnabled(false),
            DividerDrawerItem(),
            PrimaryDrawerItem().withIdentifier(STATUS_ID + 1).withName(R.string.filterPushed).withTag("pushed").withSelected(true),
            PrimaryDrawerItem().withIdentifier(STATUS_ID + 2).withName(R.string.filterCreated).withTag("created"),
            PrimaryDrawerItem().withIdentifier(STATUS_ID + 4).withName(R.string.filterFullName).withTag("full_name")
    )


    internal var drawer: DrawerShim? = null

    init {

        val filterList = arrayListOf<IDrawerItem<*>>()
        filterList.addAll(statusList)

        fun clearSelect(clearList: ArrayList<IDrawerItem<*>>) {
            clearList.forEach {
                it.isSelected = false
            }
        }
        drawer?.currentSelectedPosition
        drawer = DrawerBuilderShim()
                .withActivity(fragment!!.activity!!)
                .withDrawerGravity(GravityCompat.END)
                .withDrawerItems(filterList)
                .withMultiSelect(true)
                .withOnDrawerItemClickListener(object : DrawerBuilderShim.DrawerItemClickListener {
                    override fun onItemClick(view: View?, position: Int, drawerItem: IDrawerItem<*>): Boolean {
                        when (drawerItem.identifier / 1000 * 1000) {
                            STATUS_ID -> {
                                clearSelect(statusList)
                                drawerItem.isSelected = true
                                generalListViewModel.sort = filterList[position].tag as String
                            }
                        }
                        drawer?.adapter?.notifyAdapterDataSetChanged()
                        drawer?.closeDrawer()
                        fragment.showRefresh()
                        return true
                    }
                }).build()

        statusList[3].isSelected = true
        generalListViewModel.sort = filterList[3].tag as String

    }


}
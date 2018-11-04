package com.shuyu.github.kotlin.module.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.view.Menu
import android.view.MenuItem
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.module.ARouterAddress
import com.shuyu.github.kotlin.module.base.BaseFragmentActivity

/**
 * Created by guoshuyu
 * Date: 2018-11-02
 */
@Route(path = ARouterAddress.SearchActivity)
class SearchActivity : BaseFragmentActivity() {
    companion object {

        fun gotoSearchActivity() {
            getRouterNavigation(ARouterAddress.SearchActivity).navigation()
        }

        fun getRouterNavigation(uri: String): Postcard {
            return ARouter.getInstance()
                    .build(uri)
        }
    }


    private var drawer: Drawer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val item1 = PrimaryDrawerItem().withIdentifier(1).withName(R.string.app_name)
        val item2 = SecondaryDrawerItem().withIdentifier(2).withName(R.string.app_name)

        drawer = DrawerBuilder()
                .withActivity(this)
                .withDrawerGravity(GravityCompat.END)
                .withMultiSelect(true)
                .addDrawerItems(
                        item1,
                        DividerDrawerItem(),
                        item2,
                        SecondaryDrawerItem().withName(R.string.app_name)
                ).build()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu_filter, menu)
        return true
    }


    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_filter -> {
                drawer?.openDrawer()
            }
        }
        return true
    }


    override fun getToolBarTitle(): String = getString(R.string.search)
    override fun getInitFragment(): Fragment = SearchFragment()
}
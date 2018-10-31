package com.shuyu.github.kotlin.module.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.LayoutInflaterCompat
import android.support.v7.app.AppCompatActivity
import androidx.core.net.toUri
import com.mikepenz.iconics.context.IconicsLayoutInflater2
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.config.AppConfig
import com.shuyu.github.kotlin.common.utils.GSYPreference
import com.shuyu.github.kotlin.model.AppGlobalModel
import com.shuyu.github.kotlin.module.StartActivity
import com.shuyu.github.kotlin.module.dynamic.DynamicFragment
import com.shuyu.github.kotlin.ui.adapter.FragmentPagerViewAdapter
import com.shuyu.github.kotlin.ui.view.GSYNavigationTabBar
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import devlight.io.library.ntb.NavigationTabBar
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.clearTask
import javax.inject.Inject


/**
 * 主页
 */
class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var globalModel: AppGlobalModel

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    /**
     * fragment列表
     */
    @Inject
    lateinit var mainFragmentList: MutableList<Fragment>

    /**
     * tab列表
     */
    @Inject
    lateinit var mainTabModel: MutableList<NavigationTabBar.Model>


    private var basicStorage: String by GSYPreference(AppConfig.USER_BASIC_CODE, "")

    private var tokenStorage: String by GSYPreference(AppConfig.ACCESS_TOKEN, "")

    private var userInfoStorage: String by GSYPreference(AppConfig.USER_INFO, "")


    override fun onCreate(savedInstanceState: Bundle?) {
        LayoutInflaterCompat.setFactory2(layoutInflater, IconicsLayoutInflater2(delegate))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        home_view_pager.adapter = FragmentPagerViewAdapter(mainFragmentList, supportFragmentManager)
        home_navigation_tab_bar.models = mainTabModel
        home_navigation_tab_bar.setViewPager(home_view_pager, 0)
        home_view_pager.offscreenPageLimit = mainFragmentList.size

        home_navigation_tab_bar.doubleTouchListener = object : GSYNavigationTabBar.TabDoubleClickListener {
            override fun onDoubleClick(position: Int) {
                if (position == 0) {
                    val fragment = mainFragmentList[position] as DynamicFragment
                    fragment.showRefresh()
                }

            }
        }

        DrawerBuilder()
                .withActivity(this)
                .withToolbar(home_tool_bar)
                .withSelectedItem(-1)
                .addDrawerItems(
                        PrimaryDrawerItem().withName(R.string.LoginOut)
                                .withTextColorRes(R.color.red).withOnDrawerItemClickListener { view, position, drawerItem ->

                                    basicStorage = ""
                                    tokenStorage = ""
                                    userInfoStorage = ""
                                    val intent = Intent(view.context, StartActivity::class.java)
                                    intent.clearTask()
                                    startActivity(intent)

                                    drawerItem.withSetSelected(false)
                                    true
                                }
                )
                .withAccountHeader(AccountHeaderBuilder().withActivity(this)
                        .addProfiles(ProfileDrawerItem().withName(globalModel.userObservable.login)
                                .withSetSelected(false)
                                .withIcon(globalModel.userObservable.avatarUrl?.toUri())
                                .withEmail(globalModel.userObservable.email ?: ""))
                        .withHeaderBackground(R.color.colorPrimary)
                        .withSelectionListEnabled(false)
                        .build()).build()
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector
}

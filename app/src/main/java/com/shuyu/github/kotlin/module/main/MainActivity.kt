package com.shuyu.github.kotlin.module.main

import android.os.Bundle
import androidx.core.view.LayoutInflaterCompat
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.mikepenz.iconics.context.IconicsLayoutInflater2
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.utils.Debuger
import com.shuyu.github.kotlin.model.AppGlobalModel
import com.shuyu.github.kotlin.module.dynamic.DynamicFragment
import com.shuyu.github.kotlin.module.search.SearchActivity
import com.shuyu.github.kotlin.repository.IssueRepository
import com.shuyu.github.kotlin.repository.LoginRepository
import com.shuyu.github.kotlin.repository.ReposRepository
import com.shuyu.github.kotlin.ui.adapter.FragmentPagerViewAdapter
import com.shuyu.github.kotlin.ui.view.GSYNavigationTabBar
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import devlight.io.library.ntb.NavigationTabBar
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


/**
 * 主页
 */
class MainActivity : AppCompatActivity(), HasSupportFragmentInjector, Toolbar.OnMenuItemClickListener {

    companion object {
        init {
            System.loadLibrary("native-gsy")
        }
    }


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


    @Inject
    lateinit var loginRepository: LoginRepository


    @Inject
    lateinit var repositoryRepository: ReposRepository


    @Inject
    lateinit var issueRepository: IssueRepository

    private val exitLogic = MainExitLogic(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        LayoutInflaterCompat.setFactory2(layoutInflater, IconicsLayoutInflater2(delegate))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewPager()

        initToolbar()

        MainDrawerController(this, home_tool_bar, loginRepository, issueRepository, repositoryRepository, globalModel)


        Debuger.printfWarning(stringFromJNI())

    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
        return true
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_search -> {
                SearchActivity.gotoSearchActivity()
            }
        }
        return true
    }

    override fun onBackPressed() {
        exitLogic.backPress()
    }

    private fun initViewPager() {
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
    }

    private fun initToolbar() {
        setSupportActionBar(home_tool_bar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowTitleEnabled(false)
        }
        home_tool_bar.setTitle(R.string.app_name)
        home_tool_bar.setOnMenuItemClickListener(this)
    }

    external fun stringFromJNI(): String

}

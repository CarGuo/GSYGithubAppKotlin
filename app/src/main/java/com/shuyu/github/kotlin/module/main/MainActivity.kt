package com.shuyu.github.kotlin.module.main

import android.os.Bundle
import androidx.core.view.LayoutInflaterCompat
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.mikepenz.iconics.context.IconicsLayoutInflater2
import com.shuyu.github.kotlin.BuildConfig
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.utils.Debuger
import com.shuyu.github.kotlin.databinding.ActivityMainBinding
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
import javax.inject.Inject


/**
 * 主页
 */
class MainActivity : AppCompatActivity(), HasSupportFragmentInjector,
    Toolbar.OnMenuItemClickListener {

    companion object {
        init {
            if (BuildConfig.NEED_CMAKE_TEST) {
                System.loadLibrary("native-gsy")
            }
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

    private lateinit var vb: ActivityMainBinding;

    @Inject
    lateinit var issueRepository: IssueRepository

    private val exitLogic = MainExitLogic(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        LayoutInflaterCompat.setFactory2(layoutInflater, IconicsLayoutInflater2(delegate))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vb = ActivityMainBinding.inflate(layoutInflater);

        initViewPager()

        initToolbar()

        MainDrawerController(
            this,
            vb.homeToolBar,
            loginRepository,
            issueRepository,
            repositoryRepository,
            globalModel
        )

        if (BuildConfig.NEED_CMAKE_TEST) {
            Debuger.printfWarning(stringFromJNI())
        }

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
        vb.homeViewPager.adapter = FragmentPagerViewAdapter(mainFragmentList, supportFragmentManager)
        vb.homeNavigationTabBar.models = mainTabModel
        vb.homeNavigationTabBar.setViewPager(vb.homeViewPager, 0)
        vb.homeViewPager.offscreenPageLimit = mainFragmentList.size

        vb.homeNavigationTabBar.doubleTouchListener =
            object : GSYNavigationTabBar.TabDoubleClickListener {
                override fun onDoubleClick(position: Int) {
                    if (position == 0) {
                        val fragment = mainFragmentList[position] as DynamicFragment
                        fragment.showRefresh()
                    }

                }
            }
    }

    private fun initToolbar() {
        setSupportActionBar(vb.homeToolBar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowTitleEnabled(false)
        }
        vb.homeToolBar.setTitle(R.string.app_name)
        vb.homeToolBar.setOnMenuItemClickListener(this)
    }

    external fun stringFromJNI(): String

}

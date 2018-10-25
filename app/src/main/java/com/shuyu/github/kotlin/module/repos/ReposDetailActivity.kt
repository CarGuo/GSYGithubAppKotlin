package com.shuyu.github.kotlin.module.repos

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.LayoutInflaterCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mikepenz.iconics.context.IconicsLayoutInflater2
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.di.ARouterInjectable
import com.shuyu.github.kotlin.module.ARouterAddress
import com.shuyu.github.kotlin.module.repos.action.ReposActionListFragment
import com.shuyu.github.kotlin.module.repos.file.ReposFileListFragment
import com.shuyu.github.kotlin.module.repos.issue.ReposIssueListFragment
import com.shuyu.github.kotlin.module.repos.readme.ReposReadmeFragment
import com.shuyu.github.kotlin.ui.adapter.FragmentPagerViewAdapter
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import devlight.io.library.ntb.NavigationTabBar
import kotlinx.android.synthetic.main.activity_repos_detail.*
import javax.inject.Inject

/**
 * Created by guoshuyu
 * Date: 2018-10-25
 */

@Route(path = ARouterAddress.ReposDetailActivity)
class ReposDetailActivity : AppCompatActivity(), HasSupportFragmentInjector, ARouterInjectable {

    companion object {

        fun gotoReposDetail(userName: String, reposName: String) {
            getRouterNavigation(ARouterAddress.ReposDetailActivity, userName, reposName).navigation()
        }

        fun getRouterNavigation(uri: String, userName: String, reposName: String): Postcard {
            return ARouter.getInstance()
                    .build(uri)
                    .withString("userName", userName)
                    .withString("reposName", reposName)
        }
    }

    @Autowired
    @JvmField
    var reposName = ""

    @Autowired
    @JvmField
    var userName = ""


    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>


    /**
     * tab列表
     */
    @Inject
    lateinit var tabModel: MutableList<NavigationTabBar.Model>

    override fun onCreate(savedInstanceState: Bundle?) {
        LayoutInflaterCompat.setFactory2(layoutInflater, IconicsLayoutInflater2(delegate))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repos_detail)

        initTitle()

        val fragmentList = getFragmentList()
        repos_detail_view_pager.adapter = FragmentPagerViewAdapter(fragmentList, supportFragmentManager)
        repos_detail_tab_bar.models = tabModel
        repos_detail_tab_bar.setViewPager(repos_detail_view_pager, 0)
        repos_detail_view_pager.offscreenPageLimit = fragmentList.size

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    private fun initTitle() {
        setSupportActionBar(repos_detail_toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowTitleEnabled(false)
        }
        repos_detail_toolbar.title = reposName
    }

    private fun getFragmentList(): ArrayList<Fragment> {
        val fragmentReadme = getRouterNavigation(ARouterAddress.ReposDetailReadme, userName, reposName).navigation() as ReposReadmeFragment
        val fragmentActionList = getRouterNavigation(ARouterAddress.ReposDetailActionList, userName, reposName).navigation() as ReposActionListFragment
        val fragmentFileList = getRouterNavigation(ARouterAddress.ReposDetailFileList, userName, reposName).navigation() as ReposFileListFragment
        val fragmentIssueList = getRouterNavigation(ARouterAddress.ReposDetailIssueList, userName, reposName).navigation() as ReposIssueListFragment
        return arrayListOf(fragmentReadme, fragmentActionList, fragmentFileList, fragmentIssueList)
    }

}

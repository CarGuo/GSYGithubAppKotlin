package com.shuyu.github.kotlin.module.repos

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.LayoutInflaterCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.AdapterView
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


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    /**
     * tab列表
     */
    @Inject
    lateinit var tabModel: MutableList<NavigationTabBar.Model>


    private lateinit var viewModel: ReposDetailViewModel

    private lateinit var fragmentReadme: ReposReadmeFragment
    private lateinit var fragmentActionList: ReposActionListFragment
    private lateinit var fragmentFileList: ReposFileListFragment
    private lateinit var fragmentIssueList: ReposIssueListFragment

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


        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ReposDetailViewModel::class.java)
        viewModel.getReposStatus(userName, reposName)

        viewModel.starredStatus.observe(this, Observer { result ->
            initControlBar()
        })
        viewModel.watchedStatus.observe(this, Observer { result ->
            initControlBar()
        })

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
        fragmentReadme = getRouterNavigation(ARouterAddress.ReposDetailReadme, userName, reposName).navigation() as ReposReadmeFragment
        fragmentActionList = getRouterNavigation(ARouterAddress.ReposDetailActionList, userName, reposName).navigation() as ReposActionListFragment
        fragmentFileList = getRouterNavigation(ARouterAddress.ReposDetailFileList, userName, reposName).navigation() as ReposFileListFragment
        fragmentIssueList = getRouterNavigation(ARouterAddress.ReposDetailIssueList, userName, reposName).navigation() as ReposIssueListFragment
        return arrayListOf(fragmentReadme, fragmentActionList, fragmentFileList, fragmentIssueList)
    }

    private fun initControlBar() {
        val dataList = getControlList()
        repos_detail_control_bar.list.clear()
        repos_detail_control_bar.list.addAll(dataList)
        repos_detail_control_bar.listView.adapter.notifyDataSetChanged()
        repos_detail_control_bar.itemClick = AdapterView.OnItemClickListener { _, _, position, _ ->
            val item = repos_detail_control_bar.list[position]
            when {
                item.toLowerCase().contains("star") -> {
                    viewModel.changeStarStatus(this, userName, reposName)
                }
                item.toLowerCase().contains("watch") -> {
                    viewModel.changeWatchStatus(this, userName, reposName)
                }
                item.contains("fork") -> {
                    viewModel.forkRepository(this, userName, reposName)
                }
            }
        }
    }

    private fun getControlList(): ArrayList<String> {
        val controlList = arrayListOf<String>()
        val starStatus = viewModel.starredStatus.value
        val watchStatus = viewModel.watchedStatus.value
        if (starStatus != null) {
            val star = if (starStatus) "{GSY-REPOS_ITEM_STARED} unStar" else "{GSY-REPOS_ITEM_STAR} star"
            controlList.add(star)
        }

        if (watchStatus != null) {
            val watch = if (watchStatus) "{GSY-REPOS_ITEM_WATCHED} unWatch" else "{GSY-REPOS_ITEM_WATCH} watch"
            controlList.add(watch)
        }

        if (starStatus != null && watchStatus != null) {
            controlList.add("{GSY-REPOS_ITEM_FORK} fork")
        }
        return controlList
    }

}

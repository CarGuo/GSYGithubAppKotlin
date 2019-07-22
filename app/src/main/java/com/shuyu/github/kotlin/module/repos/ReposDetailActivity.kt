package com.shuyu.github.kotlin.module.repos

import android.os.Bundle
import androidx.core.view.LayoutInflaterCompat
import android.widget.AdapterView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mikepenz.iconics.context.IconicsLayoutInflater2
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.utils.CommonUtils
import com.shuyu.github.kotlin.common.utils.copy
import com.shuyu.github.kotlin.di.ARouterInjectable
import com.shuyu.github.kotlin.module.ARouterAddress
import com.shuyu.github.kotlin.module.base.BaseActivity
import com.shuyu.github.kotlin.module.repos.action.ReposActionListFragment
import com.shuyu.github.kotlin.module.repos.file.ReposFileListFragment
import com.shuyu.github.kotlin.module.repos.issue.ReposIssueListFragment
import com.shuyu.github.kotlin.module.repos.readme.ReposReadmeFragment
import com.shuyu.github.kotlin.ui.adapter.FragmentPagerViewAdapter
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import devlight.io.library.ntb.NavigationTabBar
import kotlinx.android.synthetic.main.activity_repos_detail.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.share
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * 仓库详情页面
 * Created by guoshuyu
 * Date: 2018-10-25
 */

@Route(path = ARouterAddress.ReposDetailActivity)
class ReposDetailActivity : BaseActivity(), HasSupportFragmentInjector, ARouterInjectable {

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

    override fun supportFragmentInjector() = dispatchingAndroidInjector


    override fun getToolBar(): Toolbar = repos_detail_toolbar

    override fun getLayoutId(): Int = R.layout.activity_repos_detail

    override fun getToolBarTitle(): String = reposName

    override fun actionOpenByBrowser() {
        browse(CommonUtils.getReposHtmlUrl(userName, reposName))
    }

    override fun actionCopy() {
        copy(CommonUtils.getReposHtmlUrl(userName, reposName))
        toast(R.string.hadCopy)
    }

    override fun actionShare() {
        share(CommonUtils.getReposHtmlUrl(userName, reposName))
    }


    private fun getFragmentList(): ArrayList<Fragment> {
        fragmentReadme = getRouterNavigation(ARouterAddress.ReposDetailReadme, userName, reposName).navigation() as ReposReadmeFragment
        fragmentActionList = getRouterNavigation(ARouterAddress.ReposDetailActionList, userName, reposName).navigation() as ReposActionListFragment
        fragmentFileList = getRouterNavigation(ARouterAddress.ReposDetailFileList, userName, reposName).navigation() as ReposFileListFragment
        fragmentIssueList = getRouterNavigation(ARouterAddress.ReposDetailIssueList, userName, reposName).navigation() as ReposIssueListFragment
        return arrayListOf(fragmentReadme, fragmentActionList, fragmentFileList, fragmentIssueList)
    }

    /**
     * 初始化底部仓库状态控制器
     */
    private fun initControlBar() {
        val dataList = getControlList()
        repos_detail_control_bar.list.clear()
        repos_detail_control_bar.list.addAll(dataList)
        repos_detail_control_bar.listView.adapter?.notifyDataSetChanged()
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

    /**
     * 获取底部仓库状态数据
     */
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

package com.shuyu.github.kotlin.module.issue

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.di.ARouterInjectable
import com.shuyu.github.kotlin.module.ARouterAddress
import com.shuyu.github.kotlin.module.base.BaseFragmentActivity
import com.shuyu.github.kotlin.module.repos.ReposDetailActivity


/**
 * Created by guoshuyu
 * Date: 2018-10-24
 */
@Route(path = ARouterAddress.IssueDetailActivity)
class IssueDetailActivity : BaseFragmentActivity(), ARouterInjectable {

    @Autowired
    @JvmField
    var userName = ""

    @Autowired
    @JvmField
    var reposName = ""


    @Autowired
    @JvmField
    var issueNumber = 0

    companion object {
        fun gotoIssueDetail(userName: String, reposName: String, issueNumber: Int) {
            getRouterNavigation(ARouterAddress.IssueDetailActivity, userName, reposName, issueNumber).navigation()
        }

        fun getRouterNavigation(uri: String, userName: String, reposName: String, issueNumber: Int): Postcard {
            return ARouter.getInstance()
                    .build(uri)
                    .withString("userName", userName)
                    .withString("reposName", reposName)
                    .withInt("issueNumber", issueNumber)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_home -> {
                ReposDetailActivity.gotoReposDetail(userName, reposName)
            }
        }
        return super.onMenuItemClick(item)
    }

    override fun getInitFragment(): IssueDetailFragment {
        return getRouterNavigation(ARouterAddress.IssueDetailFragment, userName, reposName, issueNumber).navigation() as IssueDetailFragment
    }

    override fun getToolBarTitle(): String = "$userName/$reposName"
}
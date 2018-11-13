package com.shuyu.github.kotlin.module.notify

import android.view.Menu
import android.view.MenuItem
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.di.ARouterInjectable
import com.shuyu.github.kotlin.module.ARouterAddress
import com.shuyu.github.kotlin.module.base.BaseFragmentActivity

/**
 * 通知
 * Created by guoshuyu
 * Date: 2018-11-12
 */
@Route(path = ARouterAddress.NotifyActivity)
class NotifyActivity : BaseFragmentActivity(), ARouterInjectable {

    private var fragment: NotifyFragment? = null

    companion object {

        fun gotoNotify() {
            getRouterNavigation(ARouterAddress.NotifyActivity).navigation()
        }

        fun getRouterNavigation(uri: String): Postcard {
            return ARouter.getInstance()
                    .build(uri)
        }
    }


    override fun getToolBarTitle(): String = getString(R.string.notify)

    override fun getInitFragment(): NotifyFragment {
        fragment = getRouterNavigation(ARouterAddress.NotifyFragment).navigation() as NotifyFragment
        return fragment!!
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_notify_menu, menu)
        return true
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_read -> {
                fragment?.setAllNotificationAsRead(this)
            }
        }
        return true
    }

}
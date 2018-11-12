package com.shuyu.github.kotlin.module.notify

import android.support.v4.app.Fragment
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

    override fun getInitFragment(): Fragment {
        return getRouterNavigation(ARouterAddress.NotifyFragment).navigation() as NotifyFragment
    }
}
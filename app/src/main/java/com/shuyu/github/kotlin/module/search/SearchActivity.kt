package com.shuyu.github.kotlin.module.search

import android.support.v4.app.Fragment
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
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

    override fun getToolBarTitle(): String = getString(R.string.search)
    override fun getInitFragment(): Fragment = SearchFragment()
}
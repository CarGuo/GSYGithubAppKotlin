package com.shuyu.github.kotlin.module.list

import android.os.Bundle
import android.support.v4.app.Fragment
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.shuyu.github.kotlin.di.ARouterInjectable
import com.shuyu.github.kotlin.module.ARouterAddress
import com.shuyu.github.kotlin.module.base.BaseFragmentActivity

/**
 * Created by guoshuyu
 * Date: 2018-11-08
 */
@Route(path = ARouterAddress.GeneralListActivity)
class GeneralListActivity : BaseFragmentActivity(), ARouterInjectable {

    @Autowired
    @JvmField
    var userName = ""


    @Autowired
    @JvmField
    var reposName = ""


    @Autowired
    @JvmField
    var title = ""


    @Autowired
    @JvmField
    var requestType: GeneralEnum? = null


    companion object {
        fun gotoGeneralList(userName: String, reposName: String, title: String, requestType: GeneralEnum?) {
            getRouterNavigation(ARouterAddress.GeneralListActivity, userName, reposName, title, requestType).navigation()
        }

        fun getRouterNavigation(uri: String, userName: String, reposName: String, title: String, requestType: GeneralEnum?): Postcard {
            return ARouter.getInstance()
                    .build(uri)
                    .withString("userName", userName)
                    .withString("reposName", reposName)
                    .withString("title", title)
                    .withSerializable("requestType", requestType)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getInitFragment(): Fragment {
        return getRouterNavigation(ARouterAddress.GeneralListFragment, userName, reposName, title, requestType).navigation() as Fragment
    }

    override fun getToolBarTitle(): String = title
}
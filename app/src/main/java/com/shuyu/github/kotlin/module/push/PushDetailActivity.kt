package com.shuyu.github.kotlin.module.push

import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.shuyu.github.kotlin.di.ARouterInjectable
import com.shuyu.github.kotlin.module.ARouterAddress
import com.shuyu.github.kotlin.module.base.BaseFragmentActivity

/**
 * Created by guoshuyu
 * Date: 2018-11-15
 */
@Route(path = ARouterAddress.PushDetailActivity)
class PushDetailActivity : BaseFragmentActivity(), ARouterInjectable {

    @Autowired
    @JvmField
    var reposName = ""

    @Autowired
    @JvmField
    var userName = ""


    @Autowired
    @JvmField
    var sha = ""

    companion object {
        fun gotoPushDetail(userName: String, reposName: String, sha: String) {
            getRouterNavigation(ARouterAddress.PushDetailActivity, userName, reposName, sha).navigation()
        }

        fun getRouterNavigation(uri: String, userName: String, reposName: String, sha: String): Postcard {
            return ARouter.getInstance()
                    .build(uri)
                    .withString("userName", userName)
                    .withString("reposName", reposName)
                    .withString("sha", sha)
        }
    }


    override fun getToolBarTitle(): String = reposName

    override fun getInitFragment(): PushDetailFragment {
        return getRouterNavigation(ARouterAddress.PushDetailFragment, userName, reposName, sha).navigation() as PushDetailFragment
    }
}
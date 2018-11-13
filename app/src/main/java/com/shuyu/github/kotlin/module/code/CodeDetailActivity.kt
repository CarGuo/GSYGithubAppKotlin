package com.shuyu.github.kotlin.module.code

import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.shuyu.github.kotlin.di.ARouterInjectable
import com.shuyu.github.kotlin.module.ARouterAddress
import com.shuyu.github.kotlin.module.base.BaseFragmentActivity

/**
 * Created by guoshuyu
 * Date: 2018-10-29
 */

@Route(path = ARouterAddress.CodeDetailActivity)
class CodeDetailActivity : BaseFragmentActivity(), ARouterInjectable {

    companion object {

        fun gotoCodeDetail(userName: String, reposName: String, path: String, url: String) {
            getRouterNavigation(ARouterAddress.CodeDetailActivity, userName, reposName, path, url).navigation()
        }

        fun getRouterNavigation(uri: String, userName: String, reposName: String, path: String, url: String): Postcard {
            return ARouter.getInstance()
                    .build(uri)
                    .withString("path", path)
                    .withString("url", url)
                    .withString("userName", userName)
                    .withString("reposName", reposName)
        }
    }

    @Autowired
    @JvmField
    var path = ""


    @Autowired
    @JvmField
    var url = ""

    @Autowired
    @JvmField
    var reposName = ""

    @Autowired
    @JvmField
    var userName = ""


    override fun getToolBarTitle(): String = path

    override fun getInitFragment(): CodeDetailFragment = getRouterNavigation(ARouterAddress.CodeDetailFragment, userName, reposName, path, url).navigation() as CodeDetailFragment


}
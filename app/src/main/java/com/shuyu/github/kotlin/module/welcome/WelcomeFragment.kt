package com.shuyu.github.kotlin.module.welcome

import android.os.Bundle
import android.view.View
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.config.AppConfig
import com.shuyu.github.kotlin.common.utils.GSYPreference
import com.shuyu.github.kotlin.databinding.FragmentWelcomeBinding
import com.shuyu.github.kotlin.module.base.BaseFragment

/**
 * 欢迎页
 * Created by guoshuyu
 * Date: 2018-09-30
 */
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>() {

    /***
     * 委托属性，GSYPreference 把取值和存值的操作代理给 accessTokenStorage
     * 后续的赋值和取值最终是操作的 GSYPreference 得 setValue 和 getValue 函数
     */
    private var accessTokenStorage by GSYPreference(AppConfig.ACCESS_TOKEN, "")


    override fun onCreateView(mainView: View) {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_welcome
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //todo 判断是否有用户信息
        if (accessTokenStorage.isEmpty()) {
            ///去登录页
            navigationPopUpTo(view, null, R.id.action_nav_wel_to_login, false)
        } else {
            ///去主页
            //navigationPopUpTo(view, null, R.id.action_nav_login_to_main, true)
            navigationPopUpTo(view, null, R.id.action_nav_wel_to_login, false)
        }


    }

}
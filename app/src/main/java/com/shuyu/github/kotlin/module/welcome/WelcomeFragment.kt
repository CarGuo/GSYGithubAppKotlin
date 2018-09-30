package com.shuyu.github.kotlin.module.welcome

import android.os.Bundle
import android.view.View
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.module.base.BaseFragment

/**
 * Created by guoshuyu
 * Date: 2018-09-30
 */
class WelcomeFragment : BaseFragment() {


    override fun onCreateView(mainView: View) {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_welcome
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO 判断是否登录
        view.postDelayed({
            ///去登录页不需要finish
            navigationPopUpTo(view, null, R.id.action_nav_wel_to_login,false)
        }, 2000)

    }

}
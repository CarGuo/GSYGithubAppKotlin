package com.shuyu.github.kotlin.module.login

import android.os.Bundle
import android.view.View
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.module.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * Created by guoshuyu
 * Date: 2018-09-28
 */
class LoginFragment : BaseFragment() {

    override fun onCreateView(mainView: View) {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login_submit_btn.setOnClickListener {
            ///去主页需要finish
            navigationPopUpTo(view, null, R.id.action_nav_login_to_main, true)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }
}
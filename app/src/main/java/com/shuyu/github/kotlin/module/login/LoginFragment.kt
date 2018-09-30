package com.shuyu.github.kotlin.module.login

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.utils.Debuger
import com.shuyu.github.kotlin.di.Injectable
import com.shuyu.github.kotlin.module.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

/**
 * Created by guoshuyu
 * Date: 2018-09-28
 */
class LoginFragment : BaseFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        exitFull()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(mainView: View) {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login_submit_btn.setOnClickListener {
            ///去主页需要finish
            navigationPopUpTo(view, null, R.id.action_nav_login_to_main, true)
        }


        loginViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(LoginViewModel::class.java)

        Debuger.printfError(loginViewModel.toString())
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }
}
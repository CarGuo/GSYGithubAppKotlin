package com.shuyu.github.kotlin.module.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import androidx.core.widget.toast
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.FragmentLoginBinding
import com.shuyu.github.kotlin.module.base.BaseFragment
import javax.inject.Inject

/**
 * 登录页
 * Created by guoshuyu
 * Date: 2018-09-28
 */
class LoginFragment: BaseFragment<FragmentLoginBinding> () {

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

        loginViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(LoginViewModel::class.java)

        binding.loginViewModel = loginViewModel

        loginViewModel.loginResult.observe(this, Observer { result ->
            //根据结果返回，跳转主页
            if (result != null && result == true) {
                loginViewModel.getCurrentUserInfo()
                //navigationPopUpTo(view, null, R.id.action_nav_login_to_main, true)
            } else {
                activity?.toast(R.string.LoginFailTip)
            }
        })

        loginViewModel.userInfo.observe(this, Observer { result ->
            //根据结果返回，跳转主页
            if (result != null) {
                navigationPopUpTo(view, null, R.id.action_nav_login_to_main, true)
            } else {
                activity?.toast(R.string.LoginFailTip)
            }
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }
}
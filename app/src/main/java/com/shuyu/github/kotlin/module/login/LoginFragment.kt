package com.shuyu.github.kotlin.module.login

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.FragmentLoginBinding
import com.shuyu.github.kotlin.module.base.BaseFragment
import org.jetbrains.anko.toast
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

    override fun onCreateView(mainView: View?) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(LoginViewModel::class.java)

        binding?.loginViewModel = loginViewModel

        loginViewModel.loginResult.observe(this, Observer { result ->
            //根据结果返回，跳转主页
            if (result != null && result == true) {
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
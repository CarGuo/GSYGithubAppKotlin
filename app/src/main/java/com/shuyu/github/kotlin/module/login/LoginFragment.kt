package com.shuyu.github.kotlin.module.login

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.compat.toast
import com.shuyu.github.kotlin.databinding.FragmentLoginBinding
import com.shuyu.github.kotlin.module.base.BaseFragment
import javax.inject.Inject

/**
 * 登录页
 * Created by guoshuyu
 * Date: 2018-09-28
 */
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

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

        loginViewModel =
            ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        binding?.loginViewModel = loginViewModel

        loginViewModel.loginResult.observe(viewLifecycleOwner, Observer { result ->
            if (result == true) {
                loginViewModel.loginResult.value = null
                navigationPopUpTo(view, null, R.id.action_nav_login_to_main, true, true)
            } else if (result == false) {
                loginViewModel.loginResult.value = null
                activity?.toast(R.string.LoginFailTip)
            }
        })

        binding!!.loginSubmitBtn.setOnClickListener {
            navigationPopUpTo(view, null, R.id.action_nav_oauth_to_web, false, false)
        }

        binding!!.loginTokenBtn.setOnClickListener {
            showTokenDialog()
        }


    }

    private fun showTokenDialog() {
        val ctx = context ?: return
        val input = EditText(ctx).apply {
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            setHint(R.string.LoginTokenHint)
            setSingleLine(true)
        }
        AlertDialog.Builder(ctx)
            .setTitle(R.string.LoginTokenDialogTitle)
            .setView(input)
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                val token = input.text?.toString()?.trim().orEmpty()
                if (token.isEmpty()) {
                    ctx.toast(R.string.LoginTokenEmptyTip)
                } else {
                    loginViewModel.loginWithToken(ctx, token)
                }
                dialog.dismiss()
            }
            .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }
}
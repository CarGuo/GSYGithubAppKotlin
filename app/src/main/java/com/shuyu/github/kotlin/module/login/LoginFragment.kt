package com.shuyu.github.kotlin.module.login

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.utils.colorInt
import com.mikepenz.iconics.utils.icon
import com.mikepenz.iconics.utils.sizeDp
import com.mikepenz.iconics.view.IconicsImageView
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
        val content = LayoutInflater.from(ctx)
            .inflate(R.layout.dialog_login_token, null, false)
        val input = content.findViewById<EditText>(R.id.login_token_input)
        val toggle = content.findViewById<IconicsImageView>(R.id.login_token_visibility)
        val helpLink = content.findViewById<TextView>(R.id.login_token_help_link)

        // 默认遮蔽 token 字符
        input.transformationMethod = PasswordTransformationMethod.getInstance()
        toggle.icon = buildEyeIcon(ctx, masked = true)
        toggle.setOnClickListener {
            val masked = input.transformationMethod is PasswordTransformationMethod
            input.transformationMethod = if (masked) null
            else PasswordTransformationMethod.getInstance()
            toggle.icon = buildEyeIcon(ctx, masked = !masked)
            // 切换后保持光标在末尾
            input.setSelection(input.text?.length ?: 0)
        }

        helpLink.setOnClickListener {
            runCatching {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(getString(R.string.LoginTokenHelpUrl))
                )
                startActivity(intent)
            }
        }

        AlertDialog.Builder(ctx)
            .setTitle(R.string.LoginTokenDialogTitle)
            .setView(content)
            .setPositiveButton(R.string.ok) { dialog, _ ->
                val token = input.text?.toString()?.trim().orEmpty()
                if (token.isEmpty()) {
                    ctx.toast(R.string.LoginTokenEmptyTip)
                } else {
                    loginViewModel.loginWithToken(ctx, token)
                }
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }

    private fun buildEyeIcon(ctx: Context, masked: Boolean): IconicsDrawable =
        IconicsDrawable(ctx).apply {
            icon(if (masked) "cmd-eye-outline" else "cmd-eye-off-outline")
            colorInt = ContextCompat.getColor(ctx, R.color.subTextColor)
            sizeDp = 22
        }
}
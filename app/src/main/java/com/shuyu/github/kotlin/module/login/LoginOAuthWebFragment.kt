package com.shuyu.github.kotlin.module.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.shuyu.github.kotlin.BuildConfig
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.FragmentLoginOauthBinding
import com.shuyu.github.kotlin.module.base.BaseFragment
import org.jetbrains.anko.toast
import javax.inject.Inject

class LoginOAuthFragment : BaseFragment<FragmentLoginOauthBinding>() {

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
            ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)

        binding?.loginViewModel = loginViewModel

        loginViewModel.loginResult.observe(viewLifecycleOwner, Observer { result ->
            //根据结果返回，跳转主页
            if (result != null && result == true) {
                navigationPopUpTo(view, null, R.id.action_nav_login_to_main, true, true)
            } else {
                activity?.toast(R.string.LoginFailTip)
            }
        })
        
        // Check if we're being called back from OAuth
        activity?.intent?.data?.let { uri ->
            if (uri.scheme == "gsygithubapp" && uri.host == "authed") {
                val code = uri.getQueryParameter("code")
                if (code != null) {
                    binding!!.oauthWebviewLoadingBar.visibility = View.VISIBLE
                    context?.let { ctx ->
                        loginViewModel.oauth(ctx, code)
                    }
                    // Clear the intent data to avoid re-processing
                    activity?.intent?.data = null
                    return
                }
            }
        }
        
        // Launch OAuth in external browser instead of WebView
        launchOAuthInBrowser()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_login_oauth
    }

    private fun launchOAuthInBrowser() {
        val url = "https://github.com/login/oauth/authorize?" +
                "client_id=${BuildConfig.CLIENT_ID}&" +
                "state=app&" +
                "redirect_uri=gsygithubapp://authed"
        
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            // Add FLAG_ACTIVITY_NEW_TASK to open in external browser
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (e: Exception) {
            activity?.toast(R.string.LoginFailTip)
        }
    }
}
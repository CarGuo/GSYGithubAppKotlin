package com.shuyu.github.kotlin.module.login

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebSettings.LOAD_CACHE_ELSE_NETWORK
import android.webkit.WebView
import android.webkit.WebViewClient
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
        initWeb()

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_login_oauth
    }


    private fun initWeb() {
        val settings = binding!!.oauthWebview.settings
        settings.javaScriptEnabled = true
        settings.loadWithOverviewMode = true
        settings.builtInZoomControls = false
        settings.displayZoomControls = false
        settings.domStorageEnabled = true
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        settings.cacheMode = LOAD_CACHE_ELSE_NETWORK
        
        // Set a standard Chrome User-Agent to avoid GitHub detecting WebView
        var userAgent = settings.userAgentString
        
        // Remove WebView identifier if present
        if (userAgent.contains(" wv)")) {
            userAgent = userAgent.replace(" wv)", ")")
        }
        
        // Ensure Chrome is present in User-Agent
        if (!userAgent.contains("Chrome")) {
            userAgent = "$userAgent Chrome/122.0.0.0 Mobile Safari/537.36"
        }
        
        settings.userAgentString = userAgent

        val webViewClient: WebViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                binding!!.oauthWebviewLoadingBar.visibility = View.GONE
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?, request: WebResourceRequest?
            ): Boolean {
                if (request != null && request.url != null && request.url.toString()
                        .startsWith("gsygithubapp://authed")
                ) {
                    val code = request.url.getQueryParameter("code")
                    if (code != null) {
                        loginViewModel.oauth(context!!, code)
                    };
                    return true
                }
                return false
            }
        }


        binding!!.oauthWebview.webViewClient = webViewClient


        val url =
            "https://github.com/login/oauth/authorize?" + "client_id=${BuildConfig.CLIENT_ID}&" + "state=app&redirect_uri=gsygithubapp://authed";

        binding!!.oauthWebview.loadUrl(url)
    }
}
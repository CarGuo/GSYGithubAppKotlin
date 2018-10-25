package com.shuyu.github.kotlin.module.repos.readme

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.FragmentReposReadmeBinding
import com.shuyu.github.kotlin.di.ARouterInjectable
import com.shuyu.github.kotlin.module.ARouterAddress
import com.shuyu.github.kotlin.module.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_repos_readme.*
import javax.inject.Inject

/**
 * Created by guoshuyu
 * Date: 2018-10-25
 */
@Route(path = ARouterAddress.ReposDetailReadme)
class ReposReadmeFragment : BaseFragment<FragmentReposReadmeBinding>(), ARouterInjectable {


    @Autowired
    @JvmField
    var reposName = ""

    @Autowired
    @JvmField
    var userName = ""


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ReposReadmeViewModel

    override fun onCreateView(mainView: View?) {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ReposReadmeViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val settings = repos_readme_web.settings
        settings?.javaScriptEnabled = true
        settings?.loadWithOverviewMode = true
        settings?.builtInZoomControls = false
        settings?.displayZoomControls = false
        settings?.domStorageEnabled = true
        settings?.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        settings?.setAppCacheEnabled(true)
        view.isVerticalScrollBarEnabled = false


        viewModel.htmlData.observe(this, Observer {
            repos_readme_web.loadData(it, "text/html", "utf-8")
        })
        viewModel.getReadmeHtml(userName, reposName)
    }

    override fun getLayoutId() = R.layout.fragment_repos_readme
}
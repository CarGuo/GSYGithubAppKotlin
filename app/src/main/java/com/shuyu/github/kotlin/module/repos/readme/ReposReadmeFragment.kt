package com.shuyu.github.kotlin.module.repos.readme

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.FragmentReposReadmeBinding
import com.shuyu.github.kotlin.di.ARouterInjectable
import com.shuyu.github.kotlin.module.ARouterAddress
import com.shuyu.github.kotlin.module.base.BaseFragment
import javax.inject.Inject

/**
 * Readme
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
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(ReposReadmeViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.reposReadmeWeb?.spinKit?.visibility = View.VISIBLE
        viewModel.htmlData.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrBlank()) {
                return@Observer
            }
            binding?.reposReadmeWeb?.spinKit?.visibility = View.GONE
            binding?.reposReadmeWeb?.webView?.requestIntercept = false
            binding?.reposReadmeWeb?.webView?.settings?.defaultTextEncodingName =
                "UTF-8"//设置默认为utf-8
            binding?.reposReadmeWeb?.webView?.loadDataWithBaseURL(
                null, it, "text/html", "utf-8", null
            )

        })
        viewModel.getReadmeHtml(userName, reposName)
    }

    override fun getLayoutId() = R.layout.fragment_repos_readme
}
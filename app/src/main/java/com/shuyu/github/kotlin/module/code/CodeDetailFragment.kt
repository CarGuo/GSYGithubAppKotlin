package com.shuyu.github.kotlin.module.code

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.utils.CommonUtils
import com.shuyu.github.kotlin.common.utils.copy
import com.shuyu.github.kotlin.databinding.FragmentCodeDetailBinding
import com.shuyu.github.kotlin.di.ARouterInjectable
import com.shuyu.github.kotlin.module.ARouterAddress
import com.shuyu.github.kotlin.module.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_code_detail.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.share
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Created by guoshuyu
 * Date: 2018-10-29
 */

@Route(path = ARouterAddress.CodeDetailFragment)
class CodeDetailFragment : BaseFragment<FragmentCodeDetailBinding>(), ARouterInjectable {

    @Autowired
    @JvmField
    var path = ""


    @Autowired
    @JvmField
    var url = ""

    @Autowired
    @JvmField
    var reposName = ""

    @Autowired
    @JvmField
    var userName = ""


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: CodeDetailViewModel

    override fun onCreateView(mainView: View?) {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(CodeDetailViewModel::class.java)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        code_detail_web



        code_detail_web.spinKit.visibility = View.VISIBLE
        viewModel.htmlData.observe(this, Observer {
            if (it.isNullOrBlank()) {
                return@Observer
            }
            code_detail_web.spinKit.visibility = View.GONE
            code_detail_web.webView.requestIntercept = false
            code_detail_web.webView.loadData(it, "text/html", "utf-8")

        })
        viewModel.getCodeHtml(userName, reposName, url)
    }

    override fun getLayoutId(): Int = R.layout.fragment_code_detail


    override fun actionOpenByBrowser() {
        context?.browse(CommonUtils.getFileHtmlUrl(userName, reposName, url))
    }

    override fun actionCopy() {
        context?.copy(CommonUtils.getFileHtmlUrl(userName, reposName, url))
        context?.toast(R.string.hadCopy)
    }

    override fun actionShare() {
        context?.share(CommonUtils.getFileHtmlUrl(userName, reposName, url))
    }
}
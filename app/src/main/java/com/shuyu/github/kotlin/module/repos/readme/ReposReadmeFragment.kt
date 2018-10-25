package com.shuyu.github.kotlin.module.repos.readme

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.FragmentReposReadmeBinding
import com.shuyu.github.kotlin.di.ARouterInjectable
import com.shuyu.github.kotlin.module.ARouterAddress
import com.shuyu.github.kotlin.module.base.BaseFragment

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


    override fun onCreateView(mainView: View?) {

    }

    override fun getLayoutId() = R.layout.fragment_repos_readme
}
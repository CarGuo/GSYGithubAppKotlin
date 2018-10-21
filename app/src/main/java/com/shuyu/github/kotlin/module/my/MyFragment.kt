package com.shuyu.github.kotlin.module.my

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.FragmentMyBinding
import com.shuyu.github.kotlin.model.AppGlobalModel
import com.shuyu.github.kotlin.module.base.BaseListFragment
import com.shuyu.github.kotlin.module.base.BaseViewModel
import com.shuyu.github.kotlin.module.dynamic.DynamicViewModel
import javax.inject.Inject

/**
 * 我的
 * Created by guoshuyu
 * Date: 2018-09-28
 */

class MyFragment : BaseListFragment<FragmentMyBinding, DynamicViewModel>() {

    @Inject
    lateinit var appGlobalModel: AppGlobalModel

    override fun getLayoutId(): Int {
        return R.layout.fragment_my
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.globalModel = appGlobalModel
    }

    override fun getViewModelClass(): Class<DynamicViewModel>  = DynamicViewModel::class.java

    override fun getRecyclerView(): RecyclerView?= null

    override fun bindHolder(manager: BindSuperAdapterManager) {
    }
}
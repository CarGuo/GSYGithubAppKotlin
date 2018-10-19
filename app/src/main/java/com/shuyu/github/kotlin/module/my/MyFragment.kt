package com.shuyu.github.kotlin.module.my

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.module.base.BaseListFragment

/**
 * 我的
 * Created by guoshuyu
 * Date: 2018-09-28
 */

class MyFragment : BaseListFragment<ViewDataBinding>() {

    override fun onCreateView(mainView: View) {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_list
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun getAdapterManager(): BindSuperAdapterManager? = null

    override fun getRecyclerView(): RecyclerView?= null

    override fun getDataList(): ArrayList<Any> = ArrayList()

    override fun bindHolder(manager: BindSuperAdapterManager) {
    }
}
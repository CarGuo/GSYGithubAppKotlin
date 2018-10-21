package com.shuyu.github.kotlin.module.dynamic

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.FragmentListBinding
import com.shuyu.github.kotlin.holder.EventHolder
import com.shuyu.github.kotlin.holder.base.BindingDataRecyclerManager
import com.shuyu.github.kotlin.model.bean.TrendingRepoModel
import com.shuyu.github.kotlin.model.ui.EventUIModel
import com.shuyu.github.kotlin.module.base.BaseListFragment
import com.shuyu.github.kotlin.module.base.BaseViewModel
import com.shuyu.github.kotlin.module.base.LoadState
import com.shuyu.github.kotlin.module.base.autoCleared
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

/**
 * 动态
 * Created by guoshuyu
 * Date: 2018-09-28
 */

class DynamicFragment : BaseListFragment<FragmentListBinding, DynamicViewModel>() {


    override fun getLayoutId(): Int {
        return R.layout.fragment_list
    }

    override fun onItemClick(context: Context, position: Int) {
        super.onItemClick(context, position)
    }

    override fun getViewModelClass(): Class<DynamicViewModel> = DynamicViewModel::class.java

    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = true

    override fun getRecyclerView(): RecyclerView? = baseRecycler

    override fun bindHolder(manager: BindSuperAdapterManager) {
        manager.bind(EventUIModel::class.java, EventHolder.ID, EventHolder::class.java)
    }
}
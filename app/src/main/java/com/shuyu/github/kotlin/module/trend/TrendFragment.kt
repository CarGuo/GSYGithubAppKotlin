package com.shuyu.github.kotlin.module.trend

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.FragmentListBinding
import com.shuyu.github.kotlin.holder.ReposHolder
import com.shuyu.github.kotlin.holder.base.BindingDataRecyclerManager
import com.shuyu.github.kotlin.model.ui.ReposUIModel
import com.shuyu.github.kotlin.module.base.BaseListFragment
import com.shuyu.github.kotlin.module.base.BaseViewModel
import com.shuyu.github.kotlin.module.base.autoCleared
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

/**
 * 趋势
 * Created by guoshuyu
 * Date: 2018-09-28
 */

class TrendFragment : BaseListFragment<FragmentListBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var trendViewModel: TrendViewModel

    private var normalAdapterManager by autoCleared<BindingDataRecyclerManager>()

    override fun onCreateView(mainView: View) {
        normalAdapterManager = BindingDataRecyclerManager()
        trendViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(TrendViewModel::class.java)
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_list
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trendViewModel.dataList.observe(this, Observer { items ->
            adapter.dataList = items
            adapter.notifyDataSetChanged()
        })

        showRefresh()
    }

    override fun onItemClick(context: Context, position: Int) {
        super.onItemClick(context, position)
    }

    override fun getViewModel(): BaseViewModel? {
        return trendViewModel
    }

    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = false

    override fun getAdapterManager(): BindSuperAdapterManager? = normalAdapterManager

    override fun getRecyclerView(): RecyclerView? = baseRecycler

    override fun getDataList(): ArrayList<Any> = arrayListOf()

    override fun bindHolder(manager: BindSuperAdapterManager) {
        manager.bind(ReposUIModel::class.java, ReposHolder.ID, ReposHolder::class.java)
    }
}
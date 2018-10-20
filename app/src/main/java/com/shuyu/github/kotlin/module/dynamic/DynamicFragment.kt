package com.shuyu.github.kotlin.module.dynamic

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.FragmentListBinding
import com.shuyu.github.kotlin.holder.EventHolder
import com.shuyu.github.kotlin.holder.base.BindingDataRecyclerManager
import com.shuyu.github.kotlin.model.ui.EventUIModel
import com.shuyu.github.kotlin.module.base.BaseListFragment
import com.shuyu.github.kotlin.module.base.autoCleared
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

/**
 * 动态
 * Created by guoshuyu
 * Date: 2018-09-28
 */

class DynamicFragment : BaseListFragment<FragmentListBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    lateinit var dynamicViewModel: DynamicViewModel

    private var normalAdapterManager by autoCleared<BindingDataRecyclerManager>()

    override fun onCreateView(mainView: View) {
        normalAdapterManager = BindingDataRecyclerManager()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_list
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dynamicViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(DynamicViewModel::class.java)

        dynamicViewModel.eventDataList.observe(this, Observer { items->
            adapter.dataList = items
            adapter.notifyDataSetChanged()
        })

    }

    override fun onItemClick(context: Context, position: Int) {
        super.onItemClick(context, position)
    }

    override fun onRefresh() {
        super.onRefresh()
    }

    override fun onLoadMore() {
        super.onLoadMore()
    }

    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = true

    override fun getAdapterManager(): BindSuperAdapterManager? = normalAdapterManager

    override fun getRecyclerView(): RecyclerView? = baseRecycler

    override fun getDataList(): ArrayList<Any> = arrayListOf(EventUIModel(), EventUIModel(), EventUIModel(), EventUIModel(), EventUIModel(), EventUIModel(), EventUIModel(), EventUIModel(), EventUIModel(), EventUIModel(), EventUIModel(), EventUIModel(), EventUIModel(), EventUIModel())

    override fun bindHolder(manager: BindSuperAdapterManager) {
        manager.bind(EventUIModel::class.java, EventHolder.ID, EventHolder::class.java)
    }
}
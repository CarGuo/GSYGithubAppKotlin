package com.shuyu.github.kotlin.module.dynamic

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.FragmentListBinding
import com.shuyu.github.kotlin.holder.EventHolder
import com.shuyu.github.kotlin.holder.EventUIModel
import com.shuyu.github.kotlin.holder.base.BindingDataRecyclerManager
import com.shuyu.github.kotlin.module.base.BaseListFragment
import com.shuyu.github.kotlin.module.base.autoCleared
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * 动态
 * Created by guoshuyu
 * Date: 2018-09-28
 */

class DynamicFragment : BaseListFragment<FragmentListBinding>() {

    private var normalAdapterManager by autoCleared<BindingDataRecyclerManager>()

    override fun onCreateView(mainView: View) {
        normalAdapterManager = BindingDataRecyclerManager()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_list
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
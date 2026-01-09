package com.shuyu.github.kotlin.module.notify

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.utils.EventUtils
import com.shuyu.github.kotlin.databinding.FragmentNotifyBinding
import com.shuyu.github.kotlin.di.ARouterInjectable
import com.shuyu.github.kotlin.model.ui.EventUIModel
import com.shuyu.github.kotlin.module.ARouterAddress
import com.shuyu.github.kotlin.module.base.BaseListFragment
import com.shuyu.github.kotlin.ui.holder.EventHolder
import com.shuyu.github.kotlin.ui.view.GSYTabBar
import com.google.android.material.tabs.TabLayout
import javax.inject.Inject

/**
 * 通知
 * Created by guoshuyu
 * Date: 2018-11-12
 */

@Route(path = ARouterAddress.NotifyFragment)
class NotifyFragment : BaseListFragment<FragmentNotifyBinding, NotifyViewModel>(),
    ARouterInjectable, TabLayout.OnTabSelectedListener {


    @Inject
    lateinit var TabList: MutableList<GSYTabBar.Model>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.notifyTabBar.models = TabList
        binding!!.notifyTabBar.addOnTabSelectedListener(this)
        binding!!.notifyTabBar.getTabAt(0)?.select()
    }

    override fun onItemClick(context: Context, position: Int) {
        super.onItemClick(context, position)
        val item = adapter?.dataList?.get(position) as EventUIModel
        getViewModel().setNotificationAsRead(item.threadId)
        EventUtils.evenAction(activity, item)
        notifyDelete(position, 1)
    }

    override fun getRecyclerView(): RecyclerView? = binding?.baseRecycler

    override fun bindHolder(manager: BindSuperAdapterManager) {
        manager.bind(EventUIModel::class.java, EventHolder.ID, EventHolder::class.java)
    }

    override fun getViewModelClass(): Class<NotifyViewModel> = NotifyViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_notify

    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = true

    override fun refreshComplete() {
        super.refreshComplete()
        binding!!.notifyTabBar.isTouchEnable = true
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        val index = tab?.position ?: 0
        binding!!.notifyTabBar.isTouchEnable = false
        when (index) {
            0 -> {
                getViewModel().all = null
                getViewModel().participating = null
            }

            1 -> {
                getViewModel().all = false
                getViewModel().participating = true
            }

            2 -> {
                getViewModel().all = true
                getViewModel().participating = false
            }
        }
        showRefresh()
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    fun setAllNotificationAsRead(context: Context) {
        getViewModel().setAllNotificationAsRead(context)
        adapter?.dataList?.clear()
        adapter?.notifyDataSetChanged()
    }
}
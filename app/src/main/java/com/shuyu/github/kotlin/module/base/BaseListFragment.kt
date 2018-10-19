package com.shuyu.github.kotlin.module.base

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shuyu.commonrecycler.BindSuperAdapter
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.commonrecycler.listener.OnItemClickListener
import com.shuyu.commonrecycler.listener.OnLoadingListener

/**
 * Created by guoshuyu
 * Date: 2018-10-19
 */
abstract class BaseListFragment<T : ViewDataBinding> : BaseFragment<T>(), OnItemClickListener, OnLoadingListener {


    var adapter by autoCleared<BindSuperAdapter>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                getLayoutId(),
                container,
                false)
        onCreateView(binding.root)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
    }

    /**
     * item点击
     */
    override fun onItemClick(context: Context, position: Int) {

    }

    /**
     * 刷新
     */
    override fun onRefresh() {
        refreshComplete()
    }

    /**
     * 加载更多
     */
    override fun onLoadMore() {
        loadMoreComplete()
    }

    /**
     * 通用adapter的管理器，为空即不走 @link[initList] 的初始化
     */
    abstract fun getAdapterManager(): BindSuperAdapterManager?

    /**
     * 当前 recyclerView，为空即不走 @link[initList] 的初始化
     */
    abstract fun getRecyclerView(): RecyclerView?

    /**
     * 当前数据列表
     */
    abstract fun getDataList(): ArrayList<Any>

    /**
     * 绑定Item
     */
    abstract fun bindHolder(manager: BindSuperAdapterManager)

    /**
     * 是否需要下拉刷新
     */
    open fun enableRefresh(): Boolean = false

    /**
     * 是否需要下拉刷新
     */
    open fun enableLoadMore(): Boolean = false


    open fun refreshComplete() {
        getAdapterManager()?.refreshComplete()
    }

    open fun loadMoreComplete() {
        getAdapterManager()?.loadMoreComplete()
    }


    fun initList() {
        val manager = getAdapterManager()
        if (activity != null && manager != null && getRecyclerView() != null) {
            manager.setPullRefreshEnabled(enableRefresh())
                    .setLoadingMoreEnabled(enableLoadMore())
                    .setOnItemClickListener(this)
                    .setLoadingListener(this)
            bindHolder(manager)
            adapter = BindSuperAdapter(activity as Context, manager, getDataList())
            getRecyclerView()?.layoutManager = LinearLayoutManager(activity!!)
            getRecyclerView()?.adapter = adapter
        }
    }
}
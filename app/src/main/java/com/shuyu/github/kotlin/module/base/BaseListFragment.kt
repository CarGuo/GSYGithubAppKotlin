package com.shuyu.github.kotlin.module.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shuyu.commonrecycler.BindSuperAdapter
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.commonrecycler.listener.OnItemClickListener
import com.shuyu.commonrecycler.listener.OnLoadingListener
import com.shuyu.github.kotlin.model.ui.EmptyUIModel
import com.shuyu.github.kotlin.ui.holder.EmptyHolder
import com.shuyu.github.kotlin.ui.holder.base.BindCustomLoadMoreFooter
import com.shuyu.github.kotlin.ui.holder.base.BindCustomRefreshHeader
import com.shuyu.github.kotlin.ui.holder.base.BindingDataRecyclerManager
import javax.inject.Inject

/**
 * 基础列表
 * Created by guoshuyu
 * Date: 2018-10-19
 */
abstract class BaseListFragment<T : ViewDataBinding, R : BaseViewModel> : BaseFragment<T>(), OnItemClickListener, OnLoadingListener {

    protected var normalAdapterManager by autoCleared<BindingDataRecyclerManager>()

    private lateinit var baseViewModel: R

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var adapter by autoCleared<BindSuperAdapter>()

    override fun onCreateView(mainView: View?) {
        normalAdapterManager = BindingDataRecyclerManager()
        baseViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(getViewModelClass())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()

        getViewModel().loading.observe(this, Observer {
            when (it) {
                LoadState.RefreshDone -> {
                    refreshComplete()
                }
                LoadState.LoadMoreDone -> {
                    loadMoreComplete()
                }
                LoadState.Refresh -> {
                    ///刷新时清空旧数据
                }
            }
        })

        getViewModel().dataList.observe(this, Observer { items ->
            items?.apply {
                if (items.size > 0) {
                    if (getViewModel().isFirstData()) {
                        adapter?.dataList?.clear()
                    }
                    val currentSize: Int = adapter?.dataList?.size ?: 0
                    adapter?.dataList?.addAll(items)
                    if (currentSize == 0) {
                        notifyChanged()
                    } else {
                        notifyInsert(currentSize, items.size)
                    }
                } else {
                    if (getViewModel().isFirstData()) {
                        adapter?.dataList?.clear()
                        notifyChanged()
                    }
                }
            }
        })

        getViewModel().needMore.observe(this, Observer { it ->
            it?.apply {
                normalAdapterManager?.setNoMore(!it)
            }
        })

        showRefresh()
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
        getViewModel().refresh()
    }

    /**
     * 加载更多
     */
    override fun onLoadMore() {
        getViewModel().loadMore()
    }

    /**
     * 当前 recyclerView，为空即不走 @link[initList] 的初始化
     */
    abstract fun getRecyclerView(): RecyclerView?

    /**
     * 绑定Item
     */
    abstract fun bindHolder(manager: BindSuperAdapterManager)

    /**
     * ViewModel Class
     */
    abstract fun getViewModelClass(): Class<R>

    /**
     * ViewModel
     */
    open fun getViewModel(): R = baseViewModel

    /**
     * 是否需要下拉刷新
     */
    open fun enableRefresh(): Boolean = false

    /**
     * 是否需要下拉刷新
     */
    open fun enableLoadMore(): Boolean = false


    open fun refreshComplete() {
        normalAdapterManager?.refreshComplete()
    }

    open fun loadMoreComplete() {
        normalAdapterManager?.loadMoreComplete()
    }

    open fun showRefresh() {
        normalAdapterManager?.setRefreshing(true)
    }

    open fun isLoading(): Boolean = getViewModel().isLoading()

    open fun notifyInsert(position: Int, count: Int) {
        adapter?.notifyItemRangeInserted(position + adapter!!.absFirstPosition(), count)
    }

    open fun notifyDelete(position: Int, count: Int) {
        adapter?.dataList?.removeAt(position)
        adapter?.notifyItemRangeRemoved(position + adapter!!.absFirstPosition(), count)
    }

    open fun notifyChanged() {
        adapter?.notifyDataSetChanged()
    }

    fun initList() {
        if (activity != null && getRecyclerView() != null) {
            normalAdapterManager?.setPullRefreshEnabled(enableRefresh())
                    ?.setLoadingMoreEnabled(enableLoadMore())
                    ?.setOnItemClickListener(this)
                    ?.setLoadingListener(this)
                    ?.setRefreshHeader(BindCustomRefreshHeader(activity!!))
                    ?.setFootView(BindCustomLoadMoreFooter(activity!!))
                    ?.setLoadingMoreEmptyEnabled(false)
                    ?.bindEmpty(EmptyUIModel(), EmptyHolder.ID, EmptyHolder::class.java)
            normalAdapterManager?.apply {
                bindHolder(this)
                adapter = BindSuperAdapter(activity as Context, this, arrayListOf())
                getRecyclerView()?.layoutManager = LinearLayoutManager(activity!!)
                getRecyclerView()?.adapter = adapter
            }
        }
    }
}
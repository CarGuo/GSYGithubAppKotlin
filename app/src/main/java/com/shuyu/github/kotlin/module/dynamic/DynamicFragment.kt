package com.shuyu.github.kotlin.module.dynamic

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.shuyu.commonrecycler.BindSuperAdapter
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.commonrecycler.listener.OnItemClickListener
import com.shuyu.commonrecycler.listener.OnLoadingListener
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.FragmentListBinding
import com.shuyu.github.kotlin.holder.EventHolder
import com.shuyu.github.kotlin.holder.EventUIModel
import com.shuyu.github.kotlin.module.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * 动态
 * Created by guoshuyu
 * Date: 2018-09-28
 */

class DynamicFragment : BaseFragment<FragmentListBinding>() {

    private val normalAdapterManager = BindSuperAdapterManager()

    override fun onCreateView(mainView: View) {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_list
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        normalAdapterManager.bind(EventUIModel::class.java, EventHolder.ID, EventHolder::class.java)
                .setPullRefreshEnabled(true)
                .setLoadingMoreEnabled(true)
                .setOnItemClickListener(object : OnItemClickListener {
                    override fun onItemClick(context: Context, position: Int) {

                    }
                }).setLoadingListener(object : OnLoadingListener {
                    override fun onRefresh() {

                    }

                    override fun onLoadMore() {

                    }
                })

        val adapter = BindSuperAdapter(activity!!, normalAdapterManager, arrayListOf(EventUIModel(), EventUIModel(), EventUIModel(), EventUIModel(), EventUIModel(), EventUIModel(), EventUIModel(), EventUIModel(), EventUIModel(), EventUIModel(), EventUIModel(), EventUIModel(), EventUIModel(), EventUIModel()))

        baseRecycler.layoutManager = LinearLayoutManager(activity!!)

        baseRecycler.adapter = adapter


    }
}
package com.shuyu.github.kotlin.module.my

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.FragmentMyBinding
import com.shuyu.github.kotlin.databinding.LayoutUserHeaderBinding
import com.shuyu.github.kotlin.holder.EventHolder
import com.shuyu.github.kotlin.holder.base.GSYDataBindingComponent
import com.shuyu.github.kotlin.model.AppGlobalModel
import com.shuyu.github.kotlin.model.ui.EventUIModel
import com.shuyu.github.kotlin.module.base.BaseListFragment
import kotlinx.android.synthetic.main.fragment_my.*
import javax.inject.Inject

/**
 * 我的
 * Created by guoshuyu
 * Date: 2018-09-28
 */

class MyFragment : BaseListFragment<FragmentMyBinding, MyViewModel>() {

    @Inject
    lateinit var appGlobalModel: AppGlobalModel

    override fun getLayoutId(): Int {
        return R.layout.fragment_my
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun getViewModelClass(): Class<MyViewModel> = MyViewModel::class.java

    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = true

    override fun getRecyclerView(): RecyclerView? = fragment_my_recycler

    override fun bindHolder(manager: BindSuperAdapterManager) {
        val binding: LayoutUserHeaderBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_user_header,
                null, false, GSYDataBindingComponent())
        binding.userUIModel = appGlobalModel.userObservable
        manager.addHeaderView(binding.root)

        manager.bind(EventUIModel::class.java, EventHolder.ID, EventHolder::class.java)
    }
}
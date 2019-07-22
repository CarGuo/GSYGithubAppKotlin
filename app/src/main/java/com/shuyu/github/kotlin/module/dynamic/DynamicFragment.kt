package com.shuyu.github.kotlin.module.dynamic

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.utils.EventUtils
import com.shuyu.github.kotlin.databinding.FragmentListBinding
import com.shuyu.github.kotlin.model.ui.EventUIModel
import com.shuyu.github.kotlin.module.base.BaseListFragment
import com.shuyu.github.kotlin.ui.holder.EventHolder
import kotlinx.android.synthetic.main.fragment_list.*

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
        EventUtils.evenAction(activity, adapter?.dataList?.get(position) as EventUIModel)
    }

    override fun getViewModelClass(): Class<DynamicViewModel> = DynamicViewModel::class.java

    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = true

    override fun getRecyclerView(): RecyclerView? = baseRecycler

    override fun bindHolder(manager: BindSuperAdapterManager) {
        manager.bind(EventUIModel::class.java, EventHolder.ID, EventHolder::class.java)
    }
}
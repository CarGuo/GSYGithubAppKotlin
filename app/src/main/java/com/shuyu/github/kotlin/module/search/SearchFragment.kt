package com.shuyu.github.kotlin.module.search

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.FragmentSearchBinding
import com.shuyu.github.kotlin.module.ARouterAddress
import com.shuyu.github.kotlin.module.base.BaseListFragment
import kotlinx.android.synthetic.main.fragment_search.*

/**
 * Created by guoshuyu
 * Date: 2018-11-02
 */
@Route(path = ARouterAddress.SearchFragment)
class SearchFragment : BaseListFragment<FragmentSearchBinding, SearchViewModel>() {

    override fun onCreateView(mainView: View?) {
        super.onCreateView(mainView)
    }

    override fun onItemClick(context: Context, position: Int) {
        super.onItemClick(context, position)
    }


    override fun getLayoutId(): Int = R.layout.fragment_search


    override fun getRecyclerView(): RecyclerView? = baseRecycler


    override fun bindHolder(manager: BindSuperAdapterManager) {

    }

    override fun getViewModelClass(): Class<SearchViewModel> = SearchViewModel::class.java
}
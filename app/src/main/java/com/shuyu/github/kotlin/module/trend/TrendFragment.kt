package com.shuyu.github.kotlin.module.trend

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.FragmentTrendBinding
import com.shuyu.github.kotlin.holder.ReposHolder
import com.shuyu.github.kotlin.model.ui.ReposUIModel
import com.shuyu.github.kotlin.module.base.BaseListFragment
import com.shuyu.github.kotlin.ui.adapter.ListDropDownAdapter
import kotlinx.android.synthetic.main.fragment_trend.*


/**
 * 趋势
 * Created by guoshuyu
 * Date: 2018-09-28
 */

class TrendFragment : BaseListFragment<FragmentTrendBinding, TrendViewModel>() {

    private val citys = listOf("不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州")

    private val ages = listOf("不限", "18岁以下", "18-22岁", "23-26岁", "27-35岁", "35岁以上")

    private lateinit var baseRecycler: RecyclerView

    override fun getLayoutId(): Int {
        return R.layout.fragment_trend
    }

    override fun onCreateView(mainView: View?) {
        super.onCreateView(mainView)
        //init context view
        baseRecycler = RecyclerView(activity)
        baseRecycler.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val sexView = ListView(activity)
        sexView.dividerHeight = 0
        val sexAdapter = ListDropDownAdapter(context!!, ages)
        sexView.adapter = sexAdapter

        val sexView2 = ListView(activity)
        sexView2.dividerHeight = 0
        val sexAdapter2 = ListDropDownAdapter(context!!, citys)
        sexView2.adapter = sexAdapter2


        val popupViews = ArrayList<View>()
        popupViews.add(sexView)
        popupViews.add(sexView2)

        val headers = listOf("城市", "城市2")

        trend_drop_menu.setDropDownMenu(headers, popupViews, baseRecycler)

    }

    override fun onItemClick(context: Context, position: Int) {
        super.onItemClick(context, position)
    }

    override fun getViewModelClass(): Class<TrendViewModel> = TrendViewModel::class.java

    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = false

    override fun getRecyclerView(): RecyclerView? = baseRecycler

    override fun bindHolder(manager: BindSuperAdapterManager) {
        manager.bind(ReposUIModel::class.java, ReposHolder.ID, ReposHolder::class.java)
    }
}
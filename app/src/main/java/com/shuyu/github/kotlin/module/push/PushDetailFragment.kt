package com.shuyu.github.kotlin.module.push

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.FragmentListBinding
import com.shuyu.github.kotlin.databinding.LayoutPushHeaderBinding
import com.shuyu.github.kotlin.di.ARouterInjectable
import com.shuyu.github.kotlin.model.ui.PushUIModel
import com.shuyu.github.kotlin.module.ARouterAddress
import com.shuyu.github.kotlin.module.base.BaseListFragment
import com.shuyu.github.kotlin.ui.holder.PushHolder
import com.shuyu.github.kotlin.ui.holder.base.GSYDataBindingComponent
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * Created by guoshuyu
 * Date: 2018-11-15
 */

@Route(path = ARouterAddress.PushDetailFragment)
class PushDetailFragment : BaseListFragment<FragmentListBinding, PushDetailViewModel>(), ARouterInjectable {
    @Autowired
    @JvmField
    var reposName = ""

    @Autowired
    @JvmField
    var userName = ""


    @Autowired
    @JvmField
    var sha = ""

    override fun onCreateView(mainView: View?) {
        super.onCreateView(mainView)
        getViewModel().userName = userName
        getViewModel().reposName = reposName
        getViewModel().sha = sha
    }

    override fun onItemClick(context: Context, position: Int) {
    }

    override fun getRecyclerView(): RecyclerView? = baseRecycler


    override fun getViewModelClass(): Class<PushDetailViewModel> = PushDetailViewModel::class.java

    override fun enableRefresh(): Boolean = true

    override fun actionOpenByBrowser() {

    }

    override fun actionCopy() {

    }

    override fun actionShare() {

    }

    override fun getLayoutId(): Int = R.layout.fragment_list

    override fun bindHolder(manager: BindSuperAdapterManager) {
        val binding: LayoutPushHeaderBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_push_header,
                null, false, GSYDataBindingComponent())
        binding.pushUIModel = getViewModel().pushUIModel
        manager.addHeaderView(binding.root)
        manager.bind(PushUIModel::class.java, PushHolder.ID, PushHolder::class.java)
    }
}
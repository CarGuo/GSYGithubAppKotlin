package com.shuyu.github.kotlin.module.list

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.databinding.FragmentListBinding
import com.shuyu.github.kotlin.di.ARouterInjectable
import com.shuyu.github.kotlin.model.ui.EventUIModel
import com.shuyu.github.kotlin.model.ui.ReposUIModel
import com.shuyu.github.kotlin.model.ui.UserUIModel
import com.shuyu.github.kotlin.module.ARouterAddress
import com.shuyu.github.kotlin.module.base.BaseListFragment
import com.shuyu.github.kotlin.module.person.PersonActivity
import com.shuyu.github.kotlin.module.repos.ReposDetailActivity
import com.shuyu.github.kotlin.ui.holder.EventHolder
import com.shuyu.github.kotlin.ui.holder.ReposHolder
import com.shuyu.github.kotlin.ui.holder.UserHolder
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * 通用列表
 * Created by guoshuyu
 * Date: 2018-11-08
 */

@Route(path = ARouterAddress.GeneralListFragment)
class GeneralListFragment : BaseListFragment<FragmentListBinding, GeneralListViewModel>(), ARouterInjectable {

    @Autowired
    @JvmField
    var reposName = ""

    @Autowired
    @JvmField
    var userName = ""

    @Autowired
    @JvmField
    var requestType: GeneralEnum? = null


    var filterController: GeneralFilterController? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_list
    }

    override fun onItemClick(context: Context, position: Int) {
        super.onItemClick(context, position)
        val item = adapter?.dataList?.get(position)
        when (item) {
            is EventUIModel -> {

            }
            is UserUIModel -> {
                PersonActivity.gotoPersonInfo(item.login!!)
            }
            is ReposUIModel -> {
                ReposDetailActivity.gotoReposDetail(item.ownerName, item.repositoryName)
            }
        }
    }

    override fun onCreateView(mainView: View?) {
        super.onCreateView(mainView)
        getViewModel().requestType = requestType
        getViewModel().reposName = reposName
        getViewModel().userName = userName
        filterController = GeneralFilterController(this, getViewModel())
    }

    override fun getViewModelClass(): Class<GeneralListViewModel> = GeneralListViewModel::class.java

    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = true

    override fun getRecyclerView(): RecyclerView? = baseRecycler

    override fun bindHolder(manager: BindSuperAdapterManager) {
        manager.bind(EventUIModel::class.java, EventHolder.ID, EventHolder::class.java)
        manager.bind(UserUIModel::class.java, UserHolder.ID, UserHolder::class.java)
        manager.bind(ReposUIModel::class.java, ReposHolder.ID, ReposHolder::class.java)
    }
}
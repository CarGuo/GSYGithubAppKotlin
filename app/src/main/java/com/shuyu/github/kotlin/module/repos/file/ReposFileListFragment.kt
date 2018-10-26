package com.shuyu.github.kotlin.module.repos.file

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.utils.dp
import com.shuyu.github.kotlin.databinding.FragmentListBinding
import com.shuyu.github.kotlin.di.ARouterInjectable
import com.shuyu.github.kotlin.model.ui.FileUIModel
import com.shuyu.github.kotlin.module.ARouterAddress
import com.shuyu.github.kotlin.module.base.BaseListFragment
import com.shuyu.github.kotlin.ui.holder.base.FileHolder
import com.shuyu.github.kotlin.ui.view.HorizontalTextList
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * Created by guoshuyu
 * Date: 2018-10-25
 */

@Route(path = ARouterAddress.ReposDetailFileList)
class ReposFileListFragment : BaseListFragment<FragmentListBinding, ReposFileViewModel>(), ARouterInjectable {


    @Autowired
    @JvmField
    var reposName = ""

    @Autowired
    @JvmField
    var userName = ""

    lateinit var selectList: HorizontalTextList

    override fun getLayoutId(): Int {
        return R.layout.fragment_list
    }

    override fun onItemClick(context: Context, position: Int) {
        super.onItemClick(context, position)
    }

    override fun onCreateView(mainView: View?) {
        super.onCreateView(mainView)
        getViewModel().userName = userName
        getViewModel().reposName = reposName
    }

    override fun getViewModelClass(): Class<ReposFileViewModel> = ReposFileViewModel::class.java

    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = false

    override fun getRecyclerView(): RecyclerView? = baseRecycler

    override fun bindHolder(manager: BindSuperAdapterManager) {
        val layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 50.dp)
        selectList = HorizontalTextList(context!!)
        selectList.layoutParams = layoutParams
        clearSelectList()
        manager.addHeaderView(selectList)

        manager.bind(FileUIModel::class.java, FileHolder.ID, FileHolder::class.java)
    }

    private fun clearSelectList() {
        selectList.list.clear()
        selectList.list.add(".")
    }

    private fun addSelectList(item: String) {
        selectList.list.add(item)
        selectList.listView.adapter.notifyDataSetChanged()
    }

    private fun deleteSelect(position: Int) {
        val result = selectList.list.subList(0, position)
        selectList.list.clear()
        selectList.list.addAll(result)
        selectList.listView.adapter.notifyDataSetChanged()
    }
}
package com.shuyu.github.kotlin.module.repos.file

import android.arch.lifecycle.Observer
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.utils.dp
import com.shuyu.github.kotlin.common.utils.toSplitString
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
        if (isLoading()) {
            return
        }
        val item = adapter?.dataList?.get(position)
        item?.apply {
            val itemData = this as FileUIModel
            if (itemData.type == "file") {
                //todo gotoCodeDetail
            } else {
                addSelectList(itemData.title)
                getViewModel().path = selectList.list.toSplitString()
                showRefresh()
            }
        }
    }

    override fun onCreateView(mainView: View?) {
        super.onCreateView(mainView)
        getViewModel().userName = userName
        getViewModel().reposName = reposName
        getViewModel().dataList.observe(this, Observer { items ->
            items?.apply {
                if (items.size > 0) {
                    adapter?.dataList?.addAll(items)
                    adapter?.notifyDataSetChanged()
                }
            }
        })
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
        selectList.itemClick = AdapterView.OnItemClickListener { _, _, position, _ ->
            if (isLoading()) {
                return@OnItemClickListener
            }
            if (position == 0) {
                clearSelectList()
                getViewModel().path = ""
            } else {
                deleteSelect(position)
                getViewModel().path = selectList.list.toSplitString()
            }
            showRefresh()
        }
        clearSelectList()
        manager.addHeaderView(selectList)

        manager.bind(FileUIModel::class.java, FileHolder.ID, FileHolder::class.java)
    }

    private fun clearSelectList() {
        selectList.list.clear()
        selectList.list.add(".")
        selectList.listView.adapter.notifyDataSetChanged()
    }

    private fun addSelectList(item: String) {
        selectList.list.add(item)
        selectList.listView.adapter.notifyDataSetChanged()
    }

    private fun deleteSelect(position: Int) {
        val nextList = arrayListOf<String>()
        val result = selectList.list.subList(0, position + 1)
        nextList.addAll(result)
        selectList.list.clear()
        selectList.list.addAll(nextList)
        selectList.listView.adapter.notifyDataSetChanged()
    }
}
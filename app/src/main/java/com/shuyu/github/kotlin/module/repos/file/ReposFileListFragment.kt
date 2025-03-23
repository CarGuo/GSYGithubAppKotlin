package com.shuyu.github.kotlin.module.repos.file

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.utils.CommonUtils
import com.shuyu.github.kotlin.common.utils.toSplitString
import com.shuyu.github.kotlin.databinding.FragmentReposFileListBinding
import com.shuyu.github.kotlin.di.ARouterInjectable
import com.shuyu.github.kotlin.model.ui.FileUIModel
import com.shuyu.github.kotlin.module.ARouterAddress
import com.shuyu.github.kotlin.module.base.BaseListFragment
import com.shuyu.github.kotlin.module.code.CodeDetailActivity
import com.shuyu.github.kotlin.ui.holder.FileHolder

/**
 * Created by guoshuyu
 * Date: 2018-10-25
 */

@Route(path = ARouterAddress.ReposDetailFileList)
class ReposFileListFragment : BaseListFragment<FragmentReposFileListBinding, ReposFileViewModel>(),
    ARouterInjectable {


    @Autowired
    @JvmField
    var reposName = ""

    @Autowired
    @JvmField
    var userName = ""

    override fun getLayoutId(): Int {
        return R.layout.fragment_repos_file_list
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
                val isImage = CommonUtils.isImageEnd(itemData.title)
                if (isImage) {
                    val path =
                        binding?.reposFileSelectHeader?.list?.toSplitString() + "/" + itemData.title
                    val url = CommonUtils.getFileHtmlUrl(userName, reposName, path) + "?raw=true"
                    CommonUtils.launchUrl(requireActivity(), url)
                } else {
                    CodeDetailActivity.gotoCodeDetail(
                        userName,
                        reposName,
                        itemData.title,
                        (binding!!.reposFileSelectHeader.list.toSplitString() + "/" + itemData.title).replace(
                            "/./", "/"
                        )
                    )
                }
            } else {
                addSelectList(itemData.title)
                getViewModel().path = binding!!.reposFileSelectHeader.list.toSplitString()
                showRefresh()
            }
        }
    }

    override fun onCreateView(mainView: View?) {
        super.onCreateView(mainView)
        getViewModel().userName = userName
        getViewModel().reposName = reposName
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewModel().dataList.removeObservers(this)
        getViewModel().dataList.observe(viewLifecycleOwner, Observer { items ->
            items?.apply {
                if (items.size > 0) {
                    adapter?.dataList = items
                    adapter?.notifyDataSetChanged()
                }
            }
        })
    }

    override fun getViewModelClass(): Class<ReposFileViewModel> = ReposFileViewModel::class.java

    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = false

    override fun getRecyclerView(): RecyclerView? = binding?.baseRecycler

    override fun bindHolder(manager: BindSuperAdapterManager) {

        binding!!.reposFileSelectHeader.itemClick =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                if (isLoading()) {
                    return@OnItemClickListener
                }
                if (position == 0) {
                    clearSelectList()
                    getViewModel().path = ""
                } else {
                    deleteSelect(position)
                    getViewModel().path = binding!!.reposFileSelectHeader.list.toSplitString()
                }
                showRefresh()
            }
        clearSelectList()

        manager.bind(FileUIModel::class.java, FileHolder.ID, FileHolder::class.java)
    }

    private fun clearSelectList() {
        binding!!.reposFileSelectHeader.list.clear()
        binding!!.reposFileSelectHeader.list.add(".")
        binding!!.reposFileSelectHeader.listView.adapter?.notifyDataSetChanged()
    }

    private fun addSelectList(item: String) {
        binding!!.reposFileSelectHeader.list.add(item)
        binding!!.reposFileSelectHeader.listView.adapter?.notifyDataSetChanged()
    }

    private fun deleteSelect(position: Int) {
        val nextList = arrayListOf<String>()
        val result = binding!!.reposFileSelectHeader.list.subList(0, position + 1)
        nextList.addAll(result)
        binding!!.reposFileSelectHeader.list.clear()
        binding!!.reposFileSelectHeader.list.addAll(nextList)
        binding!!.reposFileSelectHeader.listView.adapter?.notifyDataSetChanged()
    }
}
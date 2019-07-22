package com.shuyu.github.kotlin.module.push

import android.content.Context
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.utils.CommonUtils
import com.shuyu.github.kotlin.common.utils.copy
import com.shuyu.github.kotlin.databinding.FragmentListBinding
import com.shuyu.github.kotlin.databinding.LayoutPushHeaderBinding
import com.shuyu.github.kotlin.di.ARouterInjectable
import com.shuyu.github.kotlin.model.ui.FileUIModel
import com.shuyu.github.kotlin.module.ARouterAddress
import com.shuyu.github.kotlin.module.base.BaseListFragment
import com.shuyu.github.kotlin.module.code.CodeDetailActivity
import com.shuyu.github.kotlin.module.person.PersonActivity
import com.shuyu.github.kotlin.ui.holder.PushHolder
import com.shuyu.github.kotlin.ui.holder.base.GSYDataBindingComponent
import kotlinx.android.synthetic.main.fragment_list.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.share
import org.jetbrains.anko.toast

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
        val item = adapter?.dataList?.get(position) as FileUIModel
        CodeDetailActivity.gotoCodeDetailLocal(item.title, item.patch)
    }

    override fun getRecyclerView(): RecyclerView? = baseRecycler


    override fun getViewModelClass(): Class<PushDetailViewModel> = PushDetailViewModel::class.java

    override fun enableRefresh(): Boolean = true

    override fun actionOpenByBrowser() {
        context?.browse(CommonUtils.getCommitHtmlUrl(userName, reposName, sha))
    }

    override fun actionCopy() {
        context?.copy(CommonUtils.getCommitHtmlUrl(userName, reposName, sha))
        context?.toast(R.string.hadCopy)
    }

    override fun actionShare() {
        context?.share(CommonUtils.getCommitHtmlUrl(userName, reposName, sha))
    }

    override fun getLayoutId(): Int = R.layout.fragment_list

    override fun bindHolder(manager: BindSuperAdapterManager) {
        val binding: LayoutPushHeaderBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_push_header,
                null, false, GSYDataBindingComponent())
        binding.pushUIModel = getViewModel().pushUIModel
        binding.pushHeaderImage.setOnClickListener {
            PersonActivity.gotoPersonInfo(getViewModel().pushUIModel.pushUserName)
        }
        manager.addHeaderView(binding.root)
        manager.bind(FileUIModel::class.java, PushHolder.ID, PushHolder::class.java)
    }
}
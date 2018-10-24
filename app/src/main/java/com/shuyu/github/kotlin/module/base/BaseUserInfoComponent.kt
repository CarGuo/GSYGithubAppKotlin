package com.shuyu.github.kotlin.module.base

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.net.ResultCallBack
import com.shuyu.github.kotlin.databinding.FragmentUserInfoBinding
import com.shuyu.github.kotlin.databinding.LayoutUserHeaderBinding
import com.shuyu.github.kotlin.holder.EventHolder
import com.shuyu.github.kotlin.holder.base.GSYDataBindingComponent
import com.shuyu.github.kotlin.model.ui.EventUIModel
import com.shuyu.github.kotlin.model.ui.UserUIModel
import com.shuyu.github.kotlin.repository.UserRepository
import kotlinx.android.synthetic.main.fragment_user_info.*

/**
 * Created by guoshuyu
 * Date: 2018-10-24
 */

abstract class BaseUserInfoFragment<T: BaseUserInfoViewModel>: BaseListFragment<FragmentUserInfoBinding, T>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_user_info
    }

    override fun onCreateView(mainView: View?) {
        super.onCreateView(mainView)
        getViewModel().login = getUserName()
    }

    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = true

    override fun getRecyclerView(): RecyclerView? = fragment_my_recycler

    override fun bindHolder(manager: BindSuperAdapterManager) {
        val binding: LayoutUserHeaderBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_user_header,
                null, false, GSYDataBindingComponent())
        binding.userUIModel = getViewModel().getUserModel()
        manager.addHeaderView(binding.root)

        manager.bind(EventUIModel::class.java, EventHolder.ID, EventHolder::class.java)
    }

    abstract fun getUserName(): String?
}


abstract class BaseUserInfoViewModel constructor(private val userRepository: UserRepository) : BaseViewModel(), ResultCallBack<ArrayList<Any>> {

    var login: String? = null

    override fun loadDataByRefresh() {
        userRepository.getPersonInfo(null, this, login)
    }

    override fun loadDataByLoadMore() {
        userRepository.getUserEvent(getUserModel().login, this, page)
    }

    override fun onSuccess(result: ArrayList<Any>?) {
        commitResult(result)
        completeLoadData()
    }

    override fun onFailure() {
        completeLoadData()
    }

    abstract fun getUserModel():UserUIModel
}
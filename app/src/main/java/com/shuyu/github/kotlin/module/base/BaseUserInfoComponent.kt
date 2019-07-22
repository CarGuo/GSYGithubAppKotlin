package com.shuyu.github.kotlin.module.base

import android.app.Application
import android.content.Context
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.utils.EventUtils
import com.shuyu.github.kotlin.databinding.FragmentUserInfoBinding
import com.shuyu.github.kotlin.databinding.LayoutUserHeaderBinding
import com.shuyu.github.kotlin.model.ui.EventUIModel
import com.shuyu.github.kotlin.model.ui.UserUIModel
import com.shuyu.github.kotlin.module.list.GeneralEnum
import com.shuyu.github.kotlin.module.list.GeneralListActivity
import com.shuyu.github.kotlin.module.person.PersonActivity
import com.shuyu.github.kotlin.repository.UserRepository
import com.shuyu.github.kotlin.ui.holder.EventHolder
import com.shuyu.github.kotlin.ui.holder.UserHolder
import com.shuyu.github.kotlin.ui.holder.base.GSYDataBindingComponent
import kotlinx.android.synthetic.main.fragment_user_info.*
import org.jetbrains.anko.toast

/**
 * 基础用户显示
 * Created by guoshuyu
 * Date: 2018-10-24
 */

abstract class BaseUserInfoFragment<T : BaseUserInfoViewModel> : BaseListFragment<FragmentUserInfoBinding, T>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_user_info
    }

    override fun onCreateView(mainView: View?) {
        super.onCreateView(mainView)
        getViewModel().login = getLoginName()
    }


    override fun onItemClick(context: Context, position: Int) {
        super.onItemClick(context, position)
        val item = adapter?.dataList?.get(position)
        when (item) {
            is EventUIModel -> {
                EventUtils.evenAction(activity, adapter?.dataList?.get(position) as EventUIModel)
            }
            is UserUIModel -> {
                PersonActivity.gotoPersonInfo(item.login!!)
            }
        }
    }

    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = true

    override fun getRecyclerView(): RecyclerView? = fragment_my_recycler

    override fun bindHolder(manager: BindSuperAdapterManager) {
        val binding: LayoutUserHeaderBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_user_header,
                null, false, GSYDataBindingComponent())
        binding.userUIModel = getViewModel().getUserModel()
        binding.baseUserViewModel = getViewModel()
        binding.userHeaderNotify.visibility = View.GONE
        manager.addHeaderView(binding.root)
        bindHeader(binding)

        manager.bind(EventUIModel::class.java, EventHolder.ID, EventHolder::class.java)
        manager.bind(UserUIModel::class.java, UserHolder.ID, UserHolder::class.java)
    }

    open fun bindHeader(binding: LayoutUserHeaderBinding) {
    }

    abstract fun getLoginName(): String?
}


abstract class BaseUserInfoViewModel constructor(private val userRepository: UserRepository, private val application: Application) : BaseViewModel(application) {

    val foucsIcon = ObservableField<String>()

    var login: String? = null

    override fun loadDataByRefresh() {
        userRepository.getPersonInfo(null, this, login)
    }

    override fun loadDataByLoadMore() {
        if (getUserModel().type == "Organization") {
            userRepository.getOrgMembers(getUserModel().login, this, page)
        } else {
            userRepository.getUserEvent(getUserModel().login, this, page)
        }
    }


    fun onTabIconClick(v: View?) {
        getUserModel().login?.apply {
            when (v?.id) {
                R.id.user_header_repos -> {
                    GeneralListActivity.gotoGeneralList(this, "", login ?: ""+" "
                    +application.getString(R.string.FollowedText), GeneralEnum.UserRepository, true)
                }
                R.id.user_header_fan -> {
                    GeneralListActivity.gotoGeneralList(this, "", login ?: ""+" "
                    +application.getString(R.string.FollowersText), GeneralEnum.UserFollower)
                }
                R.id.user_header_focus -> {
                    GeneralListActivity.gotoGeneralList(this, "", login ?: ""+" "
                    +application.getString(R.string.FollowedText), GeneralEnum.UserFollowed)
                }
                R.id.user_header_star -> {
                    GeneralListActivity.gotoGeneralList(this, "", login ?: ""+" "
                    +application.getString(R.string.FollowedText), GeneralEnum.UserStar)
                }
                R.id.user_header_honor -> {
                    v.context.toast(R.string.user100Honor)
                }
            }
        }
    }

    abstract fun getUserModel(): UserUIModel


    open fun onFocusClick(v: View?) {

    }
}
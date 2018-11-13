package com.shuyu.github.kotlin.module.notify

import android.app.Application
import android.content.Context
import com.shuyu.github.kotlin.module.base.BaseViewModel
import com.shuyu.github.kotlin.repository.UserRepository
import javax.inject.Inject

/**
 * Created by guoshuyu
 * Date: 2018-11-12
 */

class NotifyViewModel @Inject constructor(application: Application, private val userRepository: UserRepository) : BaseViewModel(application) {

    var all: Boolean? = null
    var participating: Boolean? = null

    override fun loadDataByRefresh() {
        loadData()
    }

    override fun loadDataByLoadMore() {
        loadData()
    }

    private fun loadData() {
        userRepository.getNotify(all, participating, page, this)
    }

    fun setAllNotificationAsRead(context: Context) {
        userRepository.setAllNotificationAsRead(context)
    }

    fun setNotificationAsRead(id: String) {
        userRepository.setNotificationAsRead(id)
    }
}
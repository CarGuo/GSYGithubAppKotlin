package com.shuyu.github.kotlin.module.my

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.net.ResultCallBack
import com.shuyu.github.kotlin.model.AppGlobalModel
import com.shuyu.github.kotlin.module.base.BaseUserInfoViewModel
import com.shuyu.github.kotlin.repository.UserRepository
import javax.inject.Inject

class MyViewModel @Inject constructor(private val userRepository: UserRepository, private val globalAppModel: AppGlobalModel, private val application: Application) : BaseUserInfoViewModel(userRepository, application) {

    val notifyColor = MutableLiveData<Int>()

    override fun loadDataByRefresh() {
        super.loadDataByRefresh()
        userRepository.getNotify(null, null, 1, object : ResultCallBack<ArrayList<Any>> {
            override fun onSuccess(result: ArrayList<Any>?) {
                notifyColor.value = if (result == null || result.size == 0) {
                    ContextCompat.getColor(application, R.color.subTextColor)
                } else {
                    ContextCompat.getColor(application, R.color.actionBlue)
                }
            }
            override fun onFailure() {
            }
        })

    }

    override fun getUserModel() = globalAppModel.userObservable
}
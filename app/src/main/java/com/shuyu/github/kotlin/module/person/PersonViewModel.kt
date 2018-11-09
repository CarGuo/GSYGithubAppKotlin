package com.shuyu.github.kotlin.module.person

import android.app.Application
import android.view.View
import com.shuyu.github.kotlin.common.net.ResultCallBack
import com.shuyu.github.kotlin.model.bean.User
import com.shuyu.github.kotlin.model.conversion.UserConversion
import com.shuyu.github.kotlin.model.ui.UserUIModel
import com.shuyu.github.kotlin.module.base.BaseUserInfoViewModel
import com.shuyu.github.kotlin.repository.UserRepository
import javax.inject.Inject

/**
 * 用户详情页面 VM
 */
class PersonViewModel @Inject constructor(private val userRepository: UserRepository, private val application: Application) : BaseUserInfoViewModel(userRepository, application) {

    val userObservable = UserUIModel()

    private var isFocus = false

    override fun loadDataByRefresh() {
        userRepository.getPersonInfo(object : ResultCallBack<User> {

            override fun onCacheSuccess(result: User?) {
                result?.apply {
                    UserConversion.cloneDataFromUser(application, this, userObservable)
                    checkFocus(this.login)
                }
            }

            override fun onSuccess(result: User?) {
                result?.apply {
                    UserConversion.cloneDataFromUser(application, this, userObservable)
                }
            }

            override fun onFailure() {

            }
        }, this, login)
    }

    override fun getUserModel() = userObservable

    private fun checkFocus(login: String?) {
        userRepository.checkFocus(login, object : ResultCallBack<Boolean> {
            override fun onSuccess(result: Boolean?) {
                result?.apply {
                    isFocus = result
                    foucsIcon.set(getFocusIcon())
                }
            }
        })
    }

    override fun onFocusClick(v: View?) {
        userRepository.doFocus(v!!.context, userObservable.login, isFocus, object : ResultCallBack<Boolean> {
            override fun onSuccess(result: Boolean?) {
                isFocus = isFocus.not()
                foucsIcon.set(getFocusIcon())
            }
        })
    }

    private fun getFocusIcon(): String {
        return if (isFocus) {
            "GSY-FOCUS"
        } else {
            "GSY-UN_FOCUS"
        }
    }
}
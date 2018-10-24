package com.shuyu.github.kotlin.module.my

import com.shuyu.github.kotlin.module.base.BaseUserInfoFragment
/**
 * 我的
 * Created by guoshuyu
 * Date: 2018-09-28
 */

open class MyFragment : BaseUserInfoFragment<MyViewModel>() {

    override fun getViewModelClass(): Class<MyViewModel> = MyViewModel::class.java

    override fun getUserName(): String? = null
}
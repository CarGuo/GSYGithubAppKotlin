package com.shuyu.github.kotlin.module.person

import com.shuyu.github.kotlin.module.base.BaseUserInfoFragment
import com.shuyu.github.kotlin.module.my.MyViewModel

/**
 * Created by guoshuyu
 * Date: 2018-10-24
 */


class PersonFragment : BaseUserInfoFragment<MyViewModel>() {

    override fun getViewModelClass(): Class<MyViewModel> = MyViewModel::class.java

    override fun getUserName(): String? = null
}
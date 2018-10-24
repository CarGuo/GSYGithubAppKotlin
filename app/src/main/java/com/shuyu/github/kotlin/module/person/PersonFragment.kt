package com.shuyu.github.kotlin.module.person

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.shuyu.github.kotlin.di.ARouterInjectable
import com.shuyu.github.kotlin.module.ARouterAddress
import com.shuyu.github.kotlin.module.base.BaseUserInfoFragment

/**
 * Created by guoshuyu
 * Date: 2018-10-24
 */

@Route(path = ARouterAddress.PersonFragment)
class PersonFragment : BaseUserInfoFragment<PersonViewModel>(), ARouterInjectable {

    @Autowired
    @JvmField
    var userName = ""

    override fun getViewModelClass(): Class<PersonViewModel> = PersonViewModel::class.java

    override fun getLoginName(): String? = userName
}
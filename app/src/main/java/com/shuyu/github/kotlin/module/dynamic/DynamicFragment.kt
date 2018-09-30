package com.shuyu.github.kotlin.module.dynamic

import android.os.Bundle
import android.view.View
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.module.base.BaseFragment

/**
 * 动态
 * Created by guoshuyu
 * Date: 2018-09-28
 */

class DynamicFragment : BaseFragment() {

    override fun onCreateView(mainView: View) {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_list
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
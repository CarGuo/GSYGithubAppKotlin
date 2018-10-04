package com.shuyu.github.kotlin.module.my

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.View
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.module.base.BaseFragment

/**
 * 我的
 * Created by guoshuyu
 * Date: 2018-09-28
 */

class MyFragment : BaseFragment<ViewDataBinding>() {

    override fun onCreateView(mainView: View) {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_list
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
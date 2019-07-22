package com.shuyu.github.kotlin.module.my

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import com.shuyu.github.kotlin.databinding.LayoutUserHeaderBinding
import com.shuyu.github.kotlin.module.base.BaseUserInfoFragment
import com.shuyu.github.kotlin.module.notify.NotifyActivity

/**
 * 我的
 * Created by guoshuyu
 * Date: 2018-09-28
 */
class MyFragment : BaseUserInfoFragment<MyViewModel>() {

    override fun getViewModelClass(): Class<MyViewModel> = MyViewModel::class.java

    override fun getLoginName(): String? = null

    override fun bindHeader(binding: LayoutUserHeaderBinding) {
        binding.userHeaderNotify.visibility = View.VISIBLE
        binding.userHeaderNotify.setOnClickListener {
            startActivityForResult(Intent(context, NotifyActivity::class.java), 1000)
        }
        getViewModel().notifyColor.observe(this, Observer { result ->
            result?.apply {
                binding.userHeaderNotify.setTextColor(this)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        showRefresh()
    }
}
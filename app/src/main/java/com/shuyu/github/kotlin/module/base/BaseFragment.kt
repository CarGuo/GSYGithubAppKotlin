package com.shuyu.github.kotlin.module.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.shuyu.github.kotlin.di.Injectable
import com.shuyu.github.kotlin.ui.holder.base.GSYDataBindingComponent

/**
 * 基类Fragment
 * Created by guoshuyu
 * Date: 2018-09-30
 */
abstract class BaseFragment<T : ViewDataBinding> : Fragment(), Injectable {

    /**
     * 根据Fragment动态清理和获取binding对象
     */
    var binding by autoCleared<T>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                getLayoutId(),
                container,
                false,
                GSYDataBindingComponent())
        onCreateView(binding?.root)
        return binding?.root
    }

    open fun actionOpenByBrowser() {

    }

    open fun actionCopy() {

    }

    open fun actionShare() {

    }

    abstract fun onCreateView(mainView: View?)

    abstract fun getLayoutId(): Int

    /**
     * Navigation 的页面跳转
     */
    fun navigationPopUpTo(view: View, args: Bundle?, actionId: Int, finishStack: Boolean) {
        val controller = Navigation.findNavController(view)
        controller.navigate(actionId,
                args, NavOptions.Builder().setPopUpTo(controller.graph.id, true).build())
        if (finishStack) {
            activity?.finish()
        }
    }

    fun enterFull() {
        activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
    }

    fun exitFull() {
        activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
    }
}
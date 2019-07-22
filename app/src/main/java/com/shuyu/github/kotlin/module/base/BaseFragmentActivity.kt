package com.shuyu.github.kotlin.module.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.shuyu.github.kotlin.R
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_fragment_container.*
import javax.inject.Inject

/**
 * Created by guoshuyu
 * Date: 2018-10-24
 */
abstract class BaseFragmentActivity : BaseActivity(), HasSupportFragmentInjector, Toolbar.OnMenuItemClickListener, PopupMenu.OnMenuItemClickListener {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private var fragment: BaseFragment<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragment = getInitFragment()
        addFragment(fragment!!, R.id.activity_fragment_container_id)
    }

    override fun getLayoutId(): Int = R.layout.activity_fragment_container

    override fun actionOpenByBrowser() {
        fragment?.actionOpenByBrowser()
    }

    override fun actionCopy() {
        fragment?.actionCopy()
    }

    override fun actionShare() {
        fragment?.actionShare()
    }

    override fun getToolBar(): Toolbar = activity_fragment_container_toolbar

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    abstract fun getInitFragment(): BaseFragment<*>
}

fun Context.startActivity(cls: Class<*>) {
    startActivity(Intent(this, cls))
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { add(frameId, fragment) }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { replace(frameId, fragment) }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}


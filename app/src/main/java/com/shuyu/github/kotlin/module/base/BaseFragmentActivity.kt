package com.shuyu.github.kotlin.module.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import com.shuyu.github.kotlin.R
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_fragment_container.*
import javax.inject.Inject

/**
 * Created by guoshuyu
 * Date: 2018-10-24
 */
abstract class BaseFragmentActivity : AppCompatActivity(), HasSupportFragmentInjector, Toolbar.OnMenuItemClickListener, PopupMenu.OnMenuItemClickListener {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_container)

        setSupportActionBar(activity_fragment_container_toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowTitleEnabled(false)
        }

        activity_fragment_container_toolbar.setOnMenuItemClickListener(this)

        activity_fragment_container_toolbar.title = getToolBarTitle()

        addFragment(getInitFragment(), R.id.activity_fragment_container_id)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_default_menu, menu)
        return true
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_more -> {
                val pop = PopupMenu(this, activity_fragment_container_toolbar)
                pop.menuInflater.inflate(R.menu.toolbar_default_pop_menu, pop.menu)
                pop.gravity = Gravity.END
                pop.show()
                pop.setOnMenuItemClickListener(this)
            }
            R.id.action_browser -> {
                actionOpenByBrowser()
            }
            R.id.action_copy -> {
                actionCopy()
            }
        }
        return true
    }

    open fun actionOpenByBrowser() {

    }

    open fun actionCopy() {

    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    abstract fun getToolBarTitle(): String

    abstract fun getInitFragment(): Fragment
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


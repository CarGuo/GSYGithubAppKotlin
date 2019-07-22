package com.shuyu.github.kotlin.module.base

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import com.shuyu.github.kotlin.R

/**
 * Created by guoshuyu
 * Date: 2018-10-24
 */
abstract class BaseActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener, PopupMenu.OnMenuItemClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initTitle()
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
                val pop = PopupMenu(this, getToolBar())
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
            R.id.action_share -> {
                actionShare()
            }
        }
        return true
    }

    open fun actionOpenByBrowser() {
    }

    open fun actionCopy() {
    }

    open fun actionShare() {
    }


    /**
     * 初始化title
     */
    private fun initTitle() {
        setSupportActionBar(getToolBar())
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowTitleEnabled(false)
        }
        getToolBar().title = getToolBarTitle()
        getToolBar().setOnMenuItemClickListener(this)
    }

    abstract fun getToolBarTitle(): String

    abstract fun getToolBar(): Toolbar

    abstract fun getLayoutId(): Int
}


package com.shuyu.github.kotlin.common.compat

import android.app.Activity
import android.net.Uri
import android.view.ContextThemeWrapper
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.R as MaterialR
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.children
import androidx.drawerlayout.widget.DrawerLayout
import com.mikepenz.materialdrawer.holder.ImageHolder
import com.mikepenz.materialdrawer.holder.StringHolder
import com.mikepenz.materialdrawer.model.AbstractDrawerItem
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IProfile
import com.mikepenz.materialdrawer.widget.AccountHeaderView
import com.mikepenz.materialdrawer.widget.MaterialDrawerSliderView

internal class DrawerShim(
    val drawerLayout: DrawerLayout,
    val slider: MaterialDrawerSliderView,
    private val gravity: Int
) {

    val adapter
        get() = slider.adapter

    val currentSelectedPosition: Int
        get() = slider.selectedItemPosition

    fun openDrawer() {
        drawerLayout.openDrawer(gravity)
    }

    fun closeDrawer() {
        if (drawerLayout.isDrawerOpen(gravity)) {
            drawerLayout.closeDrawer(gravity)
        }
    }
}

internal class DrawerBuilderShim {

    private var activity: Activity? = null
    private var toolbar: Toolbar? = null
    private var gravity: Int = GravityCompat.START
    private var selectedItem: Long = -1L
    private var multiSelect: Boolean = false
    private val items: MutableList<IDrawerItem<*>> = mutableListOf()
    private var clickListener: ((View?, Int, IDrawerItem<*>) -> Boolean)? = null
    private var accountHeader: AccountHeaderView? = null

    fun withActivity(activity: Activity): DrawerBuilderShim {
        this.activity = activity
        return this
    }

    fun withToolbar(toolbar: Toolbar): DrawerBuilderShim {
        this.toolbar = toolbar
        return this
    }

    fun withDrawerGravity(gravity: Int): DrawerBuilderShim {
        this.gravity = gravity
        return this
    }

    fun withSelectedItem(id: Long): DrawerBuilderShim {
        this.selectedItem = id
        return this
    }

    fun withMultiSelect(multiSelect: Boolean): DrawerBuilderShim {
        this.multiSelect = multiSelect
        return this
    }

    fun addDrawerItems(vararg drawerItems: IDrawerItem<*>): DrawerBuilderShim {
        items.addAll(drawerItems)
        return this
    }

    fun withDrawerItems(drawerItems: List<IDrawerItem<*>>): DrawerBuilderShim {
        items.clear()
        items.addAll(drawerItems)
        return this
    }

    fun withOnDrawerItemClickListener(listener: DrawerItemClickListener): DrawerBuilderShim {
        this.clickListener = { v, position, item -> listener.onItemClick(v, position, item) }
        return this
    }

    fun withAccountHeader(header: AccountHeaderView): DrawerBuilderShim {
        this.accountHeader = header
        return this
    }

    fun build(): DrawerShim {
        val act = requireNotNull(activity) { "Activity must be provided to DrawerBuilder" }
        val rootContent = act.findViewById<ViewGroup>(android.R.id.content)
        val originalChild = rootContent.getChildAt(0)
            ?: throw IllegalStateException("Activity content view is empty when building drawer")

        val existingDrawer = if (originalChild is DrawerLayout) originalChild else null
        val drawerLayout: DrawerLayout = existingDrawer ?: DrawerLayout(act).apply {
            id = View.generateViewId()
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            fitsSystemWindows = true
        }

        if (existingDrawer == null) {
            rootContent.removeView(originalChild)
            val contentParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            drawerLayout.addView(originalChild, contentParams)
            rootContent.addView(drawerLayout)
        }

        val existingSlider = drawerLayout.children
            .filterIsInstance<MaterialDrawerSliderView>()
            .firstOrNull { (it.layoutParams as? DrawerLayout.LayoutParams)?.gravity == gravity }

        val slider = existingSlider ?: MaterialDrawerSliderView(
            ContextThemeWrapper(act, MaterialR.style.Theme_Material3_DayNight_NoActionBar)
        ).apply {
            val lp = DrawerLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            lp.gravity = this@DrawerBuilderShim.gravity
            layoutParams = lp
            fitsSystemWindows = true
            drawerLayout.addView(this)
        }

        accountHeader?.let { header ->
            header.attachToSliderView(slider)
        }

        if (items.isNotEmpty()) {
            slider.itemAdapter.add(items)
        }

        slider.multiSelect = multiSelect

        if (selectedItem >= 0) {
            slider.setSelection(selectedItem, false)
        } else {
            slider.setSelection(-1L, false)
        }

        clickListener?.let { listener ->
            slider.onDrawerItemClickListener = { view, item, position ->
                listener(view, position, item)
            }
        }

        toolbar?.let { tb ->
            val appCompatAct = act as? AppCompatActivity
            if (appCompatAct != null) {
                appCompatAct.setSupportActionBar(tb)
                val toggle = ActionBarDrawerToggle(
                    appCompatAct, drawerLayout, tb, 0, 0
                )
                drawerLayout.addDrawerListener(toggle)
                toggle.syncState()
                appCompatAct.supportActionBar?.setDisplayHomeAsUpEnabled(true)
                appCompatAct.supportActionBar?.setHomeButtonEnabled(true)
            }
        }

        return DrawerShim(drawerLayout, slider, gravity)
    }

    fun interface DrawerItemClickListener {
        fun onItemClick(view: View?, position: Int, drawerItem: IDrawerItem<*>): Boolean
    }
}

internal class AccountHeaderBuilderShim {
    private var activity: Activity? = null
    private val profiles: MutableList<IProfile> = mutableListOf()
    @ColorRes private var headerBackgroundRes: Int = 0
    @ColorRes private var textColorRes: Int = 0
    private var selectionListEnabled: Boolean = true

    fun withActivity(activity: Activity): AccountHeaderBuilderShim {
        this.activity = activity
        return this
    }

    fun addProfiles(vararg items: IProfile): AccountHeaderBuilderShim {
        profiles.addAll(items)
        return this
    }

    fun withHeaderBackground(@ColorRes colorRes: Int): AccountHeaderBuilderShim {
        this.headerBackgroundRes = colorRes
        return this
    }

    fun withTextColorRes(@ColorRes colorRes: Int): AccountHeaderBuilderShim {
        this.textColorRes = colorRes
        return this
    }

    fun withSelectionListEnabled(enabled: Boolean): AccountHeaderBuilderShim {
        this.selectionListEnabled = enabled
        return this
    }

    fun build(): AccountHeaderView {
        val act = requireNotNull(activity) { "Activity must be provided to AccountHeaderBuilder" }
        val header = AccountHeaderView(
            ContextThemeWrapper(act, MaterialR.style.Theme_Material3_DayNight_NoActionBar),
            compact = false
        )
        if (headerBackgroundRes != 0) {
            header.headerBackground =
                ImageHolder(ContextCompat.getDrawable(act, headerBackgroundRes))
        }
        header.selectionListEnabledForSingleProfile = selectionListEnabled
        if (profiles.isNotEmpty()) {
            header.addProfiles(*profiles.toTypedArray())
        }
        return header
    }
}

internal fun PrimaryDrawerItem.withName(@StringRes res: Int): PrimaryDrawerItem =
    apply { name = StringHolder(res) }

internal fun PrimaryDrawerItem.withName(name: String): PrimaryDrawerItem =
    apply { this.name = StringHolder(name) }

internal fun PrimaryDrawerItem.withTextColorRes(@ColorRes res: Int): PrimaryDrawerItem =
    apply { /* MaterialDrawer 8.x: textColor is ColorStateList?, skip res-based setter */ }

internal fun PrimaryDrawerItem.withIdentifier(id: Long): PrimaryDrawerItem =
    apply { identifier = id }

internal fun PrimaryDrawerItem.withTag(tag: Any?): PrimaryDrawerItem =
    apply { this.tag = tag }

internal fun PrimaryDrawerItem.withSelected(selected: Boolean): PrimaryDrawerItem =
    apply { isSelected = selected }

internal fun PrimaryDrawerItem.withSelectable(selectable: Boolean): PrimaryDrawerItem =
    apply { isSelectable = selectable }

internal fun PrimaryDrawerItem.withEnabled(enabled: Boolean): PrimaryDrawerItem =
    apply { isEnabled = enabled }

internal fun PrimaryDrawerItem.withOnDrawerItemClickListener(
    listener: DrawerBuilderShim.DrawerItemClickListener
): PrimaryDrawerItem = apply {
    onDrawerItemClickListener = { v, item, pos -> listener.onItemClick(v, pos, item) }
}

internal fun ProfileDrawerItem.withName(name: String): ProfileDrawerItem =
    apply { this.name = StringHolder(name) }

internal fun ProfileDrawerItem.withSelected(selected: Boolean): ProfileDrawerItem =
    apply { isSelected = selected }

internal fun ProfileDrawerItem.withTextColorRes(@ColorRes res: Int): ProfileDrawerItem =
    apply { /* MaterialDrawer 8.x: textColor is ColorStateList?, skip res-based setter */ }

internal fun ProfileDrawerItem.withIcon(uri: Uri): ProfileDrawerItem =
    apply { icon = ImageHolder(uri) }

internal fun ProfileDrawerItem.withEmail(email: String): ProfileDrawerItem =
    apply { description = StringHolder(email) }

internal typealias DrawerCompat = DrawerShim
internal typealias DrawerBuilderCompat = DrawerBuilderShim
internal typealias AccountHeaderBuilderCompat = AccountHeaderBuilderShim

package com.shuyu.github.kotlin.module.main

import android.support.v7.widget.Toolbar
import androidx.core.net.toUri
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.orhanobut.dialogplus.DialogPlus
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.utils.IssueDialogClickListener
import com.shuyu.github.kotlin.common.utils.showIssueEditDialog
import com.shuyu.github.kotlin.model.AppGlobalModel
import com.shuyu.github.kotlin.model.bean.Issue
import com.shuyu.github.kotlin.repository.IssueRepository
import com.shuyu.github.kotlin.repository.LoginRepository
import org.jetbrains.anko.alert

/**
 * Created by guoshuyu
 * Date: 2018-11-09
 */


class MainDrawerController(private val activity: MainActivity, toolbar: Toolbar,
                           loginRepository: LoginRepository,
                           private val issueRepository: IssueRepository,
                           globalModel: AppGlobalModel) {

    var drawer: Drawer? = null

    init {
        drawer = DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withSelectedItem(-1)
                .addDrawerItems(
                        PrimaryDrawerItem().withName(R.string.feedback)
                                .withTextColorRes(R.color.colorPrimary).withOnDrawerItemClickListener { view, position, drawerItem ->
                                    feedback()
                                    unSelect(drawerItem)
                                    true
                                }
                )
                .addDrawerItems(
                        PrimaryDrawerItem().withName(R.string.about)
                                .withTextColorRes(R.color.colorPrimary).withOnDrawerItemClickListener { view, position, drawerItem ->
                                    showAboutDialog()
                                    unSelect(drawerItem)
                                    true
                                }
                )
                .addDrawerItems(
                        PrimaryDrawerItem().withName(R.string.LoginOut)
                                .withTextColorRes(R.color.red).withOnDrawerItemClickListener { view, position, drawerItem ->
                                    loginRepository.logout(view.context)
                                    unSelect(drawerItem)
                                    true
                                }
                )
                .withAccountHeader(AccountHeaderBuilder().withActivity(activity)
                        .addProfiles(ProfileDrawerItem().withName(globalModel.userObservable.login)
                                .withSetSelected(false)
                                .withIcon(globalModel.userObservable.avatarUrl?.toUri())
                                .withEmail(globalModel.userObservable.email ?: ""))
                        .withHeaderBackground(R.color.colorPrimary)
                        .withSelectionListEnabled(false)
                        .build()).build()
    }


    private fun feedback() {
        activity.showIssueEditDialog(activity.getString(R.string.feedback), false, "", "", object : IssueDialogClickListener {
            override fun onConfirm(dialog: DialogPlus, title: String, editTitle: String?, editContent: String?) {
                val issue = Issue()
                issue.title = activity.getString(R.string.feedback)
                issue.body = editContent
                issueRepository.createIssue(activity, "CarGUo", "GSYGithubAppKotlin", issue, null)
                dialog.dismiss()
            }
        })

    }

    private fun showAboutDialog() {
        val manager = activity.packageManager.getPackageInfo(activity.packageName, 0)

        activity.alert {
            this.title = activity.getString(R.string.app_name)
            this.message = activity.getString(R.string.version) + " : " + manager.versionName
            this.show()
        }
    }

    private fun unSelect(drawerItem: IDrawerItem<*, *>) {
        drawerItem.withSetSelected(false)
        drawer?.adapter?.notifyAdapterDataSetChanged()
    }
}
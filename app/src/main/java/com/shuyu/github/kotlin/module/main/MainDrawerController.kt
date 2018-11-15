package com.shuyu.github.kotlin.module.main

import android.app.Activity
import android.content.Context
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
import com.shuyu.github.kotlin.common.net.ResultCallBack
import com.shuyu.github.kotlin.common.utils.IssueDialogClickListener
import com.shuyu.github.kotlin.common.utils.getVersionName
import com.shuyu.github.kotlin.common.utils.showIssueEditDialog
import com.shuyu.github.kotlin.model.AppGlobalModel
import com.shuyu.github.kotlin.model.bean.Issue
import com.shuyu.github.kotlin.model.bean.Release
import com.shuyu.github.kotlin.module.repos.ReposDetailActivity
import com.shuyu.github.kotlin.repository.IssueRepository
import com.shuyu.github.kotlin.repository.LoginRepository
import com.shuyu.github.kotlin.repository.ReposRepository
import org.jetbrains.anko.*

/**
 * 主页Drawer控制器
 * Created by guoshuyu
 * Date: 2018-11-09
 */
class MainDrawerController(private val activity: Activity, toolbar: Toolbar,
                           loginRepository: LoginRepository,
                           private val issueRepository: IssueRepository,
                           private val reposRepository: ReposRepository,
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
                        PrimaryDrawerItem().withName(R.string.update)
                                .withTextColorRes(R.color.colorPrimary).withOnDrawerItemClickListener { view, position, drawerItem ->
                                    checkUpdate(true)
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


        checkUpdate(false)
    }


    private fun feedback() {
        activity.showIssueEditDialog(activity.getString(R.string.feedback), false, "", "", object : IssueDialogClickListener {
            override fun onConfirm(dialog: DialogPlus, title: String, editTitle: String?, editContent: String?) {
                val issue = Issue()
                issue.title = activity.getString(R.string.feedback)
                issue.body = editContent
                issueRepository.createIssue(activity, "CarGuo", "GSYGithubAppKotlin", issue, null)
                dialog.dismiss()
            }
        })

    }

    private fun showAboutDialog() {
        activity.alert {
            this.iconResource = R.drawable.logo
            this.title = activity.getString(R.string.app_name)
            this.message = activity.getString(R.string.version) + ": " + activity.getVersionName() + "\nhttps://github.com/CarGuo/GSYGithubAppKotlin"
            this.negativeButton(R.string.open) {
                ReposDetailActivity.gotoReposDetail("CarGuo", "GSYGithubAppKotlin")
            }
            this.positiveButton(R.string.cancel) {
                it.dismiss()
            }
            this.show()
        }
    }

    private fun unSelect(drawerItem: IDrawerItem<*, *>) {
        drawerItem.withSetSelected(false)
        drawer?.adapter?.notifyAdapterDataSetChanged()
    }

    private fun checkUpdate(needTip: Boolean = false) {
        reposRepository.checkoutUpDate(activity, object : ResultCallBack<Release> {
            override fun onSuccess(result: Release?) {
                result?.name?.apply {
                    showUpdateDialog(activity, this, result.body
                            ?: "", "https://www.pgyer.com/XGtw")
                    return
                }
                if (needTip) {
                    activity.toast(R.string.newestVersion)
                }
            }
        })
    }

    private fun showUpdateDialog(context: Context, version: String, message: String, url: String) {
        activity.alert {
            this.iconResource = R.drawable.logo
            this.title = activity.getString(R.string.app_name)
            this.message = "$version: \n$message"
            this.cancelButton {
                it.dismiss()
            }
            this.okButton {
                context.browse(url)
            }
            this.show()
        }
    }
}
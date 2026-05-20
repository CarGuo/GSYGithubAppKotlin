package com.shuyu.github.kotlin.module.main

import android.app.Activity
import android.content.Context
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.URLSpan
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.orhanobut.dialogplus.DialogPlus
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.compat.AccountHeaderBuilderShim
import com.shuyu.github.kotlin.common.compat.DrawerBuilderShim
import com.shuyu.github.kotlin.common.compat.DrawerShim
import com.shuyu.github.kotlin.common.compat.alert
import com.shuyu.github.kotlin.common.compat.browse
import com.shuyu.github.kotlin.common.compat.toast
import com.shuyu.github.kotlin.common.compat.withEmail
import com.shuyu.github.kotlin.common.compat.withIcon
import com.shuyu.github.kotlin.common.compat.withName
import com.shuyu.github.kotlin.common.compat.withOnDrawerItemClickListener
import com.shuyu.github.kotlin.common.compat.withSelected
import com.shuyu.github.kotlin.common.compat.withTextColorRes
import com.shuyu.github.kotlin.common.net.ResultCallBack
import com.shuyu.github.kotlin.common.utils.IssueDialogClickListener
import com.shuyu.github.kotlin.common.utils.getVersionName
import com.shuyu.github.kotlin.common.utils.showIssueEditDialog
import com.shuyu.github.kotlin.model.AppGlobalModel
import com.shuyu.github.kotlin.model.bean.Issue
import com.shuyu.github.kotlin.model.bean.Release
import com.shuyu.github.kotlin.module.info.UserInfoActivity
import com.shuyu.github.kotlin.module.repos.ReposDetailActivity
import com.shuyu.github.kotlin.repository.IssueRepository
import com.shuyu.github.kotlin.repository.LoginRepository
import com.shuyu.github.kotlin.repository.ReposRepository

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

    internal var drawer: DrawerShim? = null

    init {
        drawer = DrawerBuilderShim()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withSelectedItem(-1)
                .addDrawerItems(
                        PrimaryDrawerItem().withName(R.string.feedback)
                                .withTextColorRes(R.color.colorPrimary)
                                .withOnDrawerItemClickListener(object : DrawerBuilderShim.DrawerItemClickListener {
                                    override fun onItemClick(view: View?, position: Int, drawerItem: IDrawerItem<*>): Boolean {
                                        feedback()
                                        unSelect(drawerItem)
                                        return true
                                    }
                                })
                )
                .addDrawerItems(
                        PrimaryDrawerItem().withName(R.string.person)
                                .withTextColorRes(R.color.colorPrimary)
                                .withOnDrawerItemClickListener(object : DrawerBuilderShim.DrawerItemClickListener {
                                    override fun onItemClick(view: View?, position: Int, drawerItem: IDrawerItem<*>): Boolean {
                                        UserInfoActivity.gotoUserInfo()
                                        unSelect(drawerItem)
                                        return true
                                    }
                                })
                )
                .addDrawerItems(
                        PrimaryDrawerItem().withName(R.string.update)
                                .withTextColorRes(R.color.colorPrimary)
                                .withOnDrawerItemClickListener(object : DrawerBuilderShim.DrawerItemClickListener {
                                    override fun onItemClick(view: View?, position: Int, drawerItem: IDrawerItem<*>): Boolean {
                                        checkUpdate(true)
                                        unSelect(drawerItem)
                                        return true
                                    }
                                })
                )
                .addDrawerItems(
                        PrimaryDrawerItem().withName(R.string.about)
                                .withTextColorRes(R.color.colorPrimary)
                                .withOnDrawerItemClickListener(object : DrawerBuilderShim.DrawerItemClickListener {
                                    override fun onItemClick(view: View?, position: Int, drawerItem: IDrawerItem<*>): Boolean {
                                        showAboutDialog()
                                        unSelect(drawerItem)
                                        return true
                                    }
                                })
                )
                .addDrawerItems(
                        PrimaryDrawerItem().withName(R.string.LoginOut)
                                .withTextColorRes(R.color.red)
                                .withOnDrawerItemClickListener(object : DrawerBuilderShim.DrawerItemClickListener {
                                    override fun onItemClick(view: View?, position: Int, drawerItem: IDrawerItem<*>): Boolean {
                                        loginRepository.logout(activity)
                                        unSelect(drawerItem)
                                        return true
                                    }
                                })
                )
                .withAccountHeader(AccountHeaderBuilderShim()
                        .withActivity(activity)
                        .addProfiles(ProfileDrawerItem().withName(globalModel.userObservable.login ?: "")
                                .withSelected(false)
                                .withTextColorRes(R.color.white)
                                .withIcon(globalModel.userObservable.avatarUrl?.toUri()!!)
                                .withEmail(globalModel.userObservable.email ?: ""))
                        .withHeaderBackground(R.color.colorPrimary)
                        .withTextColorRes(R.color.white)
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
        val start =  activity.getString(R.string.version) + ": " + activity.getVersionName() + "\n"
        val url  = "https://github.com/CarGuo/GSYGithubAppKotlin"
        val span = SpannableStringBuilder(start + url)
        span.setSpan(URLSpan(url), start.length, start.length + url.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)

        activity.alert {
            this.iconResource = R.drawable.logo
            this.title = activity.getString(R.string.app_name)
            this.message = span
            this.negativeButton(R.string.open) {
                ReposDetailActivity.gotoReposDetail("CarGuo", "GSYGithubAppKotlin")
            }
            this.positiveButton(R.string.cancel) {
                it.dismiss()
            }
            this.show()
        }
    }

    private fun unSelect(drawerItem: IDrawerItem<*>) {
        drawerItem.isSelected = false
        drawer?.adapter?.notifyAdapterDataSetChanged()
    }

    private fun checkUpdate(needTip: Boolean = false) {
        reposRepository.checkoutUpDate(activity, object : ResultCallBack<Release> {
            override fun onSuccess(result: Release?) {
                result?.name?.apply {
                    showUpdateDialog(activity, this, result.body
                            ?: "", "https://github.com/CarGuo/GSYGithubAppKotlin/releases")
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
package com.shuyu.github.kotlin.module.main

import android.app.Activity
import android.content.Context
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.URLSpan
import android.view.View
import androidx.appcompat.widget.Toolbar
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
import com.shuyu.github.kotlin.module.info.UserInfoActivity
import com.shuyu.github.kotlin.module.list.GeneralFilterController
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
                                .withTextColorRes(R.color.colorPrimary)
                                .withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
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
                                .withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
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
                                .withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
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
                                .withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
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
                                .withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                                    override fun onItemClick(view: View?, position: Int, drawerItem: IDrawerItem<*>): Boolean {
                                        loginRepository.logout(view!!.context)
                                        unSelect(drawerItem)
                                        return true
                                    }
                                })
                )
                .withAccountHeader(AccountHeaderBuilder()
                        .withActivity(activity)
                        .addProfiles(ProfileDrawerItem().withName(globalModel.userObservable.login)
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
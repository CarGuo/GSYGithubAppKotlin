package com.shuyu.github.kotlin.module.repos.action

import android.app.Application
import android.view.View
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.net.ResultCallBack
import com.shuyu.github.kotlin.model.ui.ReposUIModel
import com.shuyu.github.kotlin.module.base.BaseViewModel
import com.shuyu.github.kotlin.module.list.GeneralEnum
import com.shuyu.github.kotlin.module.list.GeneralListActivity
import com.shuyu.github.kotlin.repository.ReposRepository
import org.jetbrains.anko.runOnUiThread
import javax.inject.Inject

/**
 * Created by guoshuyu
 * Date: 2018-10-26
 */

class ReposActionViewModel @Inject constructor(private val reposRepository: ReposRepository, private val application: Application) : BaseViewModel(application) {

    val reposUIModel = ReposUIModel()

    var userName: String = ""

    var reposName: String = ""

    var showType = 0

    override fun loadDataByRefresh() {
        reposRepository.getRepoInfo(userName, reposName, object : ResultCallBack<ReposUIModel> {

            override fun onCacheSuccess(result: ReposUIModel?) {
                application.runOnUiThread {
                    result?.apply {
                        reposUIModel.cloneFrom(this)
                    }
                }
            }

            override fun onSuccess(result: ReposUIModel?) {
                result?.apply {
                    reposUIModel.cloneFrom(this)
                }
            }

            override fun onFailure() {
            }
        })
        loadData()
    }

    override fun loadDataByLoadMore() {
        loadData()
    }

    private fun loadData() {
        clearWhenRefresh()
        when (showType) {
            0 -> {
                reposRepository.getReposEvents(userName, reposName, this, page)
            }
            1 -> {
                reposRepository.getReposCommits(userName, reposName, this, page)
            }
        }
    }

    fun onTabIconClick(v: View?) {
        when (v?.id) {
            R.id.repos_header_star -> {
                GeneralListActivity.gotoGeneralList(userName, reposName, "$reposName star", GeneralEnum.RepositoryStarUser)
            }
            R.id.repos_header_fork -> {
                GeneralListActivity.gotoGeneralList(userName, reposName, "$reposName star", GeneralEnum.RepositoryForkUser)
            }
            R.id.repos_header_watch -> {
                GeneralListActivity.gotoGeneralList(userName, reposName, "$reposName watch", GeneralEnum.RepositoryWatchUser)
            }
            R.id.repos_header_issue -> {
                //todo show issue info
            }
        }
    }

}
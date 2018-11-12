package com.shuyu.github.kotlin.module.list

import android.app.Application
import com.shuyu.github.kotlin.module.base.BaseViewModel
import com.shuyu.github.kotlin.repository.ReposRepository
import com.shuyu.github.kotlin.repository.UserRepository
import javax.inject.Inject

/**
 * 通用列表VM
 * Created by guoshuyu
 * Date: 2018-11-08
 */

class GeneralListViewModel @Inject constructor(private val userRepository: UserRepository, private val reposRepository: ReposRepository, private val application: Application) : BaseViewModel(application) {

    var reposName = ""

    var userName = ""

    var sort = ""

    var requestType: GeneralEnum? = null


    override fun loadDataByRefresh() {
        loadData()
    }

    override fun loadDataByLoadMore() {
        loadData()
    }

    private fun loadData() {
        when (requestType) {
            GeneralEnum.UserFollower -> {
                userRepository.getUserFollower(userName, page, this)
            }
            GeneralEnum.UserFollowed -> {
                userRepository.getUserFollowed(userName, page, this)
            }
            GeneralEnum.UserRepository -> {
                reposRepository.getUserRepos(userName, page,  sort, this)
            }
            GeneralEnum.UserStar -> {
                reposRepository.getUserStarRepos(userName, page, this)
            }
            GeneralEnum.RepositoryStarUser -> {
                userRepository.getRepositoryStarUser(userName, reposName, page, this)
            }
            GeneralEnum.RepositoryForkUser -> {
                reposRepository.getReposFork(userName, reposName, page, this)
            }
            GeneralEnum.RepositoryWatchUser -> {
                userRepository.getRepositoryWatchUser(userName, reposName, page, this)
            }
        }
    }
}
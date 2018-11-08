package com.shuyu.github.kotlin.module.list

import android.app.Application
import com.shuyu.github.kotlin.module.base.BaseViewModel
import com.shuyu.github.kotlin.repository.ReposRepository
import com.shuyu.github.kotlin.repository.UserRepository
import javax.inject.Inject

/**
 * Created by guoshuyu
 * Date: 2018-11-08
 */

class GeneralListViewModel @Inject constructor(private val userRepository: UserRepository, private val reposRepository: ReposRepository, private val application: Application) : BaseViewModel(application) {

    var reposName = ""

    var userName = ""

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
            }
            GeneralEnum.UserStar -> {
            }
            GeneralEnum.RepositoryStarUser -> {
                userRepository.getRepositoryStarUser(userName, reposName, page, this)
            }
            GeneralEnum.RepositoryForkUser -> {
            }
            GeneralEnum.RepositoryWatchUser -> {
                userRepository.getRepositoryWatchUser(userName, reposName, page, this)
            }
        }
    }
}
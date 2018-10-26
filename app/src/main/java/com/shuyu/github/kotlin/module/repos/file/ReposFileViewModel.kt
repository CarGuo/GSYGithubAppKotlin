package com.shuyu.github.kotlin.module.repos.file

import android.app.Application
import com.shuyu.github.kotlin.model.ui.ReposUIModel
import com.shuyu.github.kotlin.module.base.BaseViewModel
import com.shuyu.github.kotlin.repository.ReposRepository
import javax.inject.Inject

/**
 * Created by guoshuyu
 * Date: 2018-10-26
 */

class ReposFileViewModel @Inject constructor(private val reposRepository: ReposRepository, application: Application) : BaseViewModel(application) {

    val reposUIModel = ReposUIModel()

    var path: String = ""

    var userName: String = ""

    var reposName: String = ""


    override fun loadDataByRefresh() {
        reposRepository.getFiles(userName, reposName, path, this)
    }

    override fun loadDataByLoadMore() {

    }


}
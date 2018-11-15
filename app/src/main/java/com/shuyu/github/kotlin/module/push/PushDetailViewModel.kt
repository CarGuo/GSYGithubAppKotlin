package com.shuyu.github.kotlin.module.push

import android.app.Application
import com.shuyu.github.kotlin.common.net.ResultCallBack
import com.shuyu.github.kotlin.model.ui.PushUIModel
import com.shuyu.github.kotlin.module.base.BaseViewModel
import com.shuyu.github.kotlin.repository.ReposRepository
import org.jetbrains.anko.runOnUiThread
import javax.inject.Inject

/**
 * Created by guoshuyu
 * Date: 2018-11-15
 */
class PushDetailViewModel @Inject constructor(private val application: Application, private val reposRepository: ReposRepository) : BaseViewModel(application) {

    var reposName = ""

    var userName = ""

    var sha = ""

    val pushUIModel = PushUIModel()

    override fun loadDataByRefresh() {
        reposRepository.getPushDetailInfo(userName, reposName, sha, object : ResultCallBack<PushUIModel> {
            override fun onSuccess(result: PushUIModel?) {
                application.runOnUiThread {
                    result?.apply {
                        pushUIModel.cloneFrom(this)
                    }
                }
            }

            override fun onFailure() {
            }
        }, this)
    }

    override fun loadDataByLoadMore() {

    }

}
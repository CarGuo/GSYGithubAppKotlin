package com.shuyu.github.kotlin.module.repos

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shuyu.github.kotlin.common.net.ResultCallBack
import com.shuyu.github.kotlin.repository.ReposRepository
import javax.inject.Inject

/**
 * Created by guoshuyu
 * Date: 2018-10-29
 */
class ReposDetailViewModel @Inject constructor(private val reposRepository: ReposRepository, private val application: Application) : ViewModel() {

    val starredStatus = MutableLiveData<Boolean>()
    val watchedStatus = MutableLiveData<Boolean>()

    fun getReposStatus(userName: String, reposName: String) {
        reposRepository.getReposStatus(userName, reposName, object : ResultCallBack<HashMap<String, Boolean>> {
            override fun onSuccess(result: HashMap<String, Boolean>?) {
                result?.apply {
                    starredStatus.value = this["starred"]
                    watchedStatus.value = this["watched"]
                }
            }

            override fun onFailure() {

            }
        })
    }

}
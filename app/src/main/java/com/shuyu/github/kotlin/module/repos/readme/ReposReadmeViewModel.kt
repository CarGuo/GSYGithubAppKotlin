package com.shuyu.github.kotlin.module.repos.readme

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shuyu.github.kotlin.common.net.ResultCallBack
import com.shuyu.github.kotlin.repository.ReposRepository
import javax.inject.Inject

/**
 * Created by guoshuyu
 * Date: 2018-10-25
 */
class ReposReadmeViewModel @Inject constructor(private val reposRepository: ReposRepository) : ViewModel() {

    val htmlData = MutableLiveData<String>()


    init {
        htmlData.value = ""
    }

    fun getReadmeHtml(userName: String, reposName: String) {
        reposRepository.getReposReadme(object : ResultCallBack<String> {
            override fun onSuccess(result: String?) {
                htmlData.value = result!!
            }

            override fun onFailure() {

            }
        }, userName, reposName)
    }
}
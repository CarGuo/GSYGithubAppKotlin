package com.shuyu.github.kotlin.module.code

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shuyu.github.kotlin.common.net.ResultCallBack
import com.shuyu.github.kotlin.repository.ReposRepository
import javax.inject.Inject

/**
 * Created by guoshuyu
 * Date: 2018-10-29
 */
class CodeDetailViewModel @Inject constructor(private val reposRepository: ReposRepository) : ViewModel() {

    val htmlData = MutableLiveData<String>()


    init {
        htmlData.value = ""
    }

    fun getCodeHtml(userName: String, reposName: String, path: String) {
        reposRepository.getRepoFilesDetail(userName, reposName, path, object : ResultCallBack<String> {
            override fun onSuccess(result: String?) {
                htmlData.value = result!!
            }

            override fun onFailure() {

            }
        })
    }
}
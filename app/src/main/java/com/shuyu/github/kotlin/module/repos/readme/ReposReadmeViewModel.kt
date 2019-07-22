package com.shuyu.github.kotlin.module.repos.readme

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shuyu.github.kotlin.common.net.ResultCallBack
import com.shuyu.github.kotlin.repository.ReposRepository
import org.jetbrains.anko.runOnUiThread
import javax.inject.Inject

/**
 * Readme VM
 * Created by guoshuyu
 * Date: 2018-10-25
 */
class ReposReadmeViewModel @Inject constructor(private val reposRepository: ReposRepository, private val application: Application) : ViewModel() {

    val htmlData = MutableLiveData<String>()


    init {
        htmlData.value = ""
    }

    fun getReadmeHtml(userName: String, reposName: String) {
        reposRepository.getReposReadme(object : ResultCallBack<String> {


            override fun onCacheSuccess(result: String?) {
                application.runOnUiThread {
                    htmlData.value = result ?: ""
                }
            }

            override fun onSuccess(result: String?) {

                htmlData.value = result ?: ""
            }

            override fun onFailure() {

            }
        }, userName, reposName)
    }
}
package com.shuyu.github.kotlin.module.search

import android.app.Application
import android.content.Context
import android.view.KeyEvent
import android.view.View
import androidx.databinding.ObservableField
import com.shuyu.github.kotlin.module.base.BaseViewModel
import com.shuyu.github.kotlin.module.base.LoadState
import com.shuyu.github.kotlin.repository.SearchRepository
import javax.inject.Inject

/**
 * 搜索VM
 * Created by guoshuyu
 * Date: 2018-11-02
 */
class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository, private val application: Application) : BaseViewModel(application) {

    companion object {
        const val REPOSITORY = 0//仓库
        const val USER = 1//人
    }

    /**
     * 搜索关键帧，双向绑定到layout
     */
    val query = ObservableField<String>()

    /**
     * 搜索类型
     */
    var type = REPOSITORY
    /**
     * 排列依据
     */
    var sort = "best%20match"
    /**
     * 排列顺序
     */
    var order = "desc"
    /**
     * 排列语言
     */
    var language = ""

    override fun loadDataByRefresh() {
    }

    override fun loadDataByLoadMore() {
    }

    fun refresh(context: Context) {
        if (isLoading()) {
            return
        }
        page = 1
        loading.value = LoadState.Refresh
        loadData(context)
    }

    fun loadMore(context: Context) {
        if (isLoading()) {
            return
        }
        page++
        loading.value = LoadState.LoadMore
        loadData(context)
    }

    private fun loadData(context: Context) {
        var searchQ = query.get()
        if (searchQ.isNullOrBlank()) {
            return
        }
        if (type == REPOSITORY) {
            if (language.isNotBlank()) {
                searchQ = "$searchQ+language:$language"
            }
            searchRepository.searchRepos(context, searchQ!!, sort, order, page, this)
        } else if (type == USER) {
            searchRepository.searchUsers(context, searchQ!!, sort, order, page, this)
        }
    }

    fun onSearchKeyListener(v: View, keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            val searchQ = query.get()
            if (searchQ.isNullOrBlank()) {
                return false
            }
            loadData(v.context)
            return true
        }
        return false
    }

    /**
     * 通过DataBinding在XML绑定的点击方法
     */
    fun onSearchClick(view: View) {
        loadData(view.context)
    }

}
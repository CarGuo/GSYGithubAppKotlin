package com.shuyu.github.kotlin.module.base


/**
 * 上下拉加载状态
 */
enum class LoadState {
    Refresh,
    LoadMore,
    RefreshDone,
    LoadMoreDone,
    NONE
}
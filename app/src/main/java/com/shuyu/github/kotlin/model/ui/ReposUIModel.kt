package com.shuyu.github.kotlin.model.ui

data class ReposUIModel(
        var ownerName: String = "--",
        var ownerPic: String = "",
        var repositoryName: String = "---",
        var repositoryStar: String = "---",
        var repositoryFork: String = "---",
        var repositoryWatch: String = "---",
        var hideWatchIcon: Boolean = true,
        var repositoryType: String = "---",
        var repositoryDes: String = "--"
)
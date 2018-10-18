package com.shuyu.github.kotlin.model

import java.util.ArrayList


class RepoCommitExt : RepoCommit() {

    val files: ArrayList<CommitFile>? = null
    val stats: CommitStats? = null
}

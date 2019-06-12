package com.shuyu.github.kotlin.model.conversion

import com.shuyu.github.kotlin.model.bean.TrendingRepoModel
import java.lang.Exception

/**
 * Html String 到 趋势相关实体转换
 * Created by guoshuyu
 * Date: 2018-10-29
 */
val TAGS = hashMapOf(
        Pair("meta", hashMapOf(Pair("start", "<span class=\"d-inline-block float-sm-right\""), Pair("end", "</span>"))),
        Pair("starCount", hashMapOf(Pair("start", "<a class=\"muted-link d-inline-block mr-3\""), Pair("flag", "/stargazers\">"), Pair("end", "</a>"))),
        Pair("forkCount", hashMapOf(Pair("start", "<a class=\"muted-link d-inline-block mr-3\""), Pair("flag", "/network"), Pair("end", "</a>")))
)

object TrendConversion {
    fun htmlToRepo(resultData: String): ArrayList<TrendingRepoModel> {
        mapOf<String, String>()
        var responseData = ""
        try {
            responseData = resultData.replace(Regex("\n"), "")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val repos = ArrayList<TrendingRepoModel>()
        var splitWithH3 = responseData.split("<article")
        splitWithH3 = splitWithH3.subList(1, splitWithH3.size)

        for (i in 0 until splitWithH3.size) {
            val repo = TrendingRepoModel()
            val html = splitWithH3[i]

            parseRepoBaseInfo(repo, html)

            val metaNoteContent = parseContentWithNote(html, "class=\"f6 text-gray mt-2\">", "</div>")
            repo.meta = parseRepoLabelWithTag(repo, metaNoteContent, TAGS["meta"]!!)
            repo.starCount = parseRepoLabelWithTag(repo, metaNoteContent, TAGS["starCount"]!!)
            repo.forkCount = parseRepoLabelWithTag(repo, metaNoteContent, TAGS["forkCount"]!!)

            parseRepoLang(repo, metaNoteContent)
            parseRepoContributors(repo, metaNoteContent)
            repos.add(repo)
        }
        return repos
    }

    private fun parseContentWithNote(htmlStr: String, startFlag: String, endFlag: String): String {
        var noteStar = htmlStr.indexOf(startFlag)
        if (noteStar == -1) {
            return ""
        } else {
            noteStar += startFlag.length
        }

        val noteEnd = htmlStr.indexOf(endFlag, noteStar)
        val content = htmlStr.substring(noteStar, noteEnd)
        return content.trim()
    }

    private fun parseRepoBaseInfo(repo: TrendingRepoModel, htmlBaseInfo: String) {
        val urlIndex = htmlBaseInfo.indexOf("<a href=\"") + "<a href=\"".length
        val url = htmlBaseInfo.substring(urlIndex, htmlBaseInfo.indexOf("\">", urlIndex))
        repo.url = url
        repo.fullName = url.substring(1, url.length)
        if (repo.fullName.indexOf('/') != -1) {
            repo.name = repo.fullName.split('/')[0]
            repo.reposName = repo.fullName.split('/')[1]
        }

        var description = parseContentWithNote(htmlBaseInfo, "<p class=\"col-9 text-gray my-1 pr-4\">", "</p>")
        if (description.isNotEmpty()) {
            val reg = "<g-emoji.*?>.+?</g-emoji>"
            val tag = Regex(reg)
            val tags = tag.findAll(description)
            for (m in tags) {
                val match = m.groups[0]?.value?.replace(Regex("<g-emoji.*?>"), "")?.replace(Regex("</g-emoji>"), "")
                description = description.replace(Regex(m.groups[0]?.value!!), match!!)
            }
        }
        repo.description = description
    }

    private fun parseRepoLabelWithTag(repo: TrendingRepoModel, noteContent: String, tag: Map<String, String>): String {
        val startFlag = if (TAGS["starCount"] == tag || TAGS["forkCount"] == tag) {
            tag["start"] + " href=\"/" + repo.fullName + tag["flag"]
        } else {
            tag["start"]!!
        }
        val content = parseContentWithNote(noteContent, startFlag, tag["end"]!!)
        return if (content.indexOf("</svg>") != -1 && (content.indexOf("</svg>") + "</svg>".length <= content.length)) {
            val metaContent = content.substring(content.indexOf("</svg>") + "</svg>".length, content.length)
            metaContent.trim()
        } else {
            content.trim()
        }
    }

    private fun parseRepoLang(repo: TrendingRepoModel, metaNoteContent: String) {
        val content = parseContentWithNote(metaNoteContent, "programmingLanguage\">", "</span>")
        repo.language = content.trim()
    }

    private fun parseRepoContributors(repo: TrendingRepoModel, htmlContributorsString: String) {
        val htmlContributors = parseContentWithNote(htmlContributorsString, "Built by", "</a>")
        val splitWitSemicolon = htmlContributors.split("\"")
        if (splitWitSemicolon.size < 2) {
            repo.contributors = arrayListOf("")
            return
        }
        repo.contributorsUrl = splitWitSemicolon[1]
        val contributors = ArrayList<String>()

        for (i in 0 until splitWitSemicolon.size) {
            val url = splitWitSemicolon[i]
            if (url.indexOf("http") != -1) {
                contributors.add(url)
            }
        }

        repo.contributors = contributors
    }

}
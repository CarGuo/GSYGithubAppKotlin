package com.shuyu.github.kotlin.common.utils

import android.content.Context
import com.shuyu.github.kotlin.R

/**
 * Created by guoshuyu
 * Date: 2018-10-25
 */

object HtmlUtils {
    fun generateCode2HTml(context: Context, mdData: String?, backgroundColorId: Int, lang: String = "java", userBR: Boolean = true): String {
        val currentData = if (mdData != null && mdData.indexOf("<code>") == -1) {
            "<body>\n<pre class=\"pre\">\n<code lang='$lang'>\n$mdData</code>\n</pre>\n</body>\n"
        } else {
            "<body>\n<pre class=\"pre\">\n$mdData</pre>\n</body>\n"
        }
        return generateHtml(context, currentData, backgroundColorId, userBR)
    }

    fun generateHtml(context: Context, mdData: String?, backgroundColorId: Int = R.color.white, userBR: Boolean = true): String {
        if (mdData == null) {
            return ""
        }
        val backgroundColor: String = context.colorIdToString(backgroundColorId)
        var mdDataCode: String = mdData
        var regExCode = "<[\\s]*?code[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?code[\\s]*?>"
        var regExPre = "<[\\s]*?pre[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?pre[\\s]*?>"


        try {
            val exp = Regex(regExCode)
            val tags = exp.findAll(mdData)
            for (m in tags) {
                val matchValue = m.groups[0]?.value
                val match = matchValue?.replace("\n", "\n\r<br>")
                matchValue?.apply {
                    match?.apply {
                        mdDataCode = mdDataCode?.replace(matchValue, match)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            val exp = Regex(regExPre)
            val tags = exp.findAll(mdDataCode)
            for (m in tags) {
                val matchValue = m.groups[0]?.value
                if (matchValue?.indexOf("<code>") != null && matchValue.indexOf("<code>") < 0) {
                    val match = matchValue.replace("\n", "\n\r<br>")
                    mdDataCode = mdDataCode.replace(matchValue, match)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            val exp = Regex("<pre>(([\\s\\S])*?)</pre>")
            val tags = exp.findAll(mdDataCode)
            for (m in tags) {
                val matchValue = m.groups[0]?.value
                if (matchValue?.indexOf("<code>") != null && matchValue.indexOf("<code>") < 0) {
                    val match = matchValue.replace("\n", "\n\r<br>")
                    mdDataCode = mdDataCode.replace(matchValue, match)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            val exp = Regex("href=\"(.*?)\"")
            val tags = exp.findAll(mdDataCode)
            for (m in tags) {
                val capture = m.groups[0]?.value
                if (capture?.indexOf("<code>") != null && capture.indexOf("http://") < 0 && capture.indexOf("https://") < 0 && capture.indexOf("#") != 0) {
                    mdDataCode = mdDataCode.replace(capture, "gsygithub://$capture")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return generateCodeHtml(context, mdDataCode, false, backgroundColor, userBR)
    }

    /**
     * style for mdHTml
     */
    fun generateCodeHtml(context: Context, mdHTML: String, wrap: Boolean, backgroundColor: String?, userBR: Boolean = true): String {
        return "<html>\n" +
                "<head>\n" +
                "<meta charset=\"utf-8\" />\n" +
                "<title></title>\n" +
                "<meta name=\"viewport\" content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\"/>" +
                "<link href=\"https://cdn.bootcss.com/highlight.js/9.12.0/styles/dracula.min.css\" rel=\"stylesheet\">\n" +
                "<script src=\"https://cdn.bootcss.com/highlight.js/9.12.0/highlight.min.js\"></script>  " +
                "<script>hljs.configure({'useBR': " +
                userBR.toString() +
                "});hljs.initHighlightingOnLoad();</script> " +
                "<script type=\"text/javascript\" charset=\"utf-8\">"+
                "function setTouch() {" +
                "console.log('**************2*************');"+
                "var list =  Array.from(document.getElementsByTagName(\"pre\")); "+
                "list.forEach(function(value,index){\n" +
                "   value.addEventListener('touchstart', function(e){\n" +
                "       window.GSYWebView.requestEvent(true);" +
                "   });\n" +
                "   value.addEventListener('touchend', function(e){\n" +
                "       window.GSYWebView.requestEvent(false);" +
                "   });"+
                "});"+
                "console.log('**************3*************');"+
                "}"+
                "window.onload=setTouch;" +
                "</script>" +
                "<style>" +
                "body{background: " +
                backgroundColor +
                ";\n" +
                "word-wrap:break-word;\n" +
                "}" +
                "a {color:" +
                "#0000FF" +
                " !important;}" +
                ".highlight pre, pre {" +
                " word-wrap: " +
                (if (wrap) "break-word" else "normal") +
                "; " +
                " white-space: " +
                (if (wrap) "pre-wrap" else "pre") +
                "; " +
                "}" +
                "thead, tr {" +
                "background:" +
                context.colorIdToString(R.color.miWhite) +
                ";}" +
                "td, th {" +
                "padding: 5px 10px;" +
                "font-size: 12px;" +
                "direction:hor" +
                "}" +
                ".highlight {overflow: scroll; background: " +
                context.colorIdToString(R.color.miWhite) +
                "}" +
                "tr:nth-child(even) {" +
                "background:" +
                context.colorIdToString(R.color.colorPrimary) +
                ";" +
                "color:" +
                context.colorIdToString(R.color.miWhite) +
                ";" +
                "}" +
                "tr:nth-child(odd) {" +
                "background: " +
                context.colorIdToString(R.color.miWhite) +
                ";" +
                "color:" +
                context.colorIdToString(R.color.colorPrimary) +
                ";" +
                "}" +
                "th {" +
                "font-size: 14px;" +
                "color:" +
                context.colorIdToString(R.color.miWhite) +
                ";" +
                "background:" +
                context.colorIdToString(R.color.colorPrimary) +
                ";" +
                "}" +
                "</style>" +
                "</head>\n" +
                "<body>\n" +
                mdHTML +
                "</body>\n" +
                "</html>"
    }

    fun parseDiffSource(diffSource: String?, wrap: Boolean): String {
        if (diffSource == null) {
            return ""
        }
        val lines = diffSource.split("\n")
        var source = ""
        var addStartLine = -1
        var removeStartLine = -1
        var addLineNum = 0
        var removeLineNum = 0
        var normalLineNum = 0
        for (i in 0 until lines.size) {
            val line = lines[i]
            var lineNumberStr = ""
            var classStr = ""
            var curAddNumber = -1
            var curRemoveNumber = -1

            if (line.indexOf("+") == 0) {
                classStr = "class=\"hljs-addition\";"
                curAddNumber = addStartLine + normalLineNum + addLineNum
                addLineNum++;
            } else if (line.indexOf("-") == 0) {
                classStr = "class=\"hljs-deletion\";"
                curRemoveNumber = removeStartLine + normalLineNum + removeLineNum
                removeLineNum++;
            } else if (line.indexOf("@@") == 0) {
                classStr = "class=\"hljs-literal\";"
                removeStartLine = getRemoveStartLine(line)
                addStartLine = getAddStartLine(line)
                addLineNum = 0
                removeLineNum = 0
                normalLineNum = 0
            } else if (!(line.indexOf("\\") == 0)) {
                curAddNumber = addStartLine + normalLineNum + addLineNum
                curRemoveNumber = removeStartLine + normalLineNum + removeLineNum
                normalLineNum++
            }
            lineNumberStr =
                    getDiffLineNumber(if (curRemoveNumber == -1) "" else (curRemoveNumber.toString() + ""), if (curAddNumber == -1) "" else (curAddNumber.toString() + ""))
            source = source + "\n" + "<div " + classStr + ">" + if (wrap) "" else (lineNumberStr + getBlank(1)) + line + "</div>"
        }

        return source
    }

    fun getRemoveStartLine(line: String): Int {
        return try {
            line.substring(line.indexOf("-") + 1, line.indexOf(",")).toInt()
        } catch (e: Exception) {
            1
        }
    }

    fun getAddStartLine(line: String): Int {
        return try {
            line.substring(line.indexOf("+") + 1, line.indexOf(",", line.indexOf("+"))).toInt()
        } catch (e: Exception) {
            1;
        }
    }

    fun getDiffLineNumber(removeNumber: String, addNumber: String): String {
        val minLength = 4
        return getBlank(minLength - removeNumber.length) + removeNumber + getBlank(1) + getBlank(minLength - addNumber.length) + addNumber;
    }

    fun getBlank(num: Int): String {
        var builder = ""
        for (i in 0 until num) {
            builder += " "
        }
        return builder
    }

    fun resolveHtmlFile(context: Context, res: String, defaultLang: String): String {

        val startTag = "class=\"instapaper_body "
        val startLang = res.indexOf(startTag)
        val endLang = res.indexOf("\" data-path=\"")
        var lang = ""
        if (startLang >= 0 && endLang >= 0) {
            var tmpLang = res.substring(startLang + startTag.length, endLang)
            lang = formName(tmpLang.toLowerCase());
        }
        if (lang.isBlank()) {
            lang = defaultLang
        }
        return if ("markdown" == lang) {
            generateHtml(context, res, R.color.miWhite)
        } else {
            generateCode2HTml(context, res, R.color.miWhite, lang)
        }

    }

    fun formName(name: String): String {
        var result = ""
        when (name) {
            "sh" -> result = "shell"
            "js" -> result = "javascript"
            "kt" -> result = "kotlin"
            "c",
            "cpp" -> result = "cpp"
            "md" -> result = "markdown"
            "html" -> result = "xml"
        }
        return result
    }
}

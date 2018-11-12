package com.shuyu.github.kotlin.common.style

import android.content.Context
import br.tiagohm.markdownview.css.styles.Bootstrap
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.utils.colorIdToString

/**
 * 当前没有使用
 * Created by guoshuyu
 * Date: 2018-10-30
 */
class GSYMarkdownStyle(context: Context) : Bootstrap() {
    init {
        this.addRule("body", *arrayOf("line-height: 1.6", "padding: 0px", "color: ${context.colorIdToString(R.color.white)}", "background-color: ${context.colorIdToString(R.color.colorPrimary)}"))
        this.addRule("h1", *arrayOf("font-size: 28px"))
        this.addRule("h2", *arrayOf("font-size: 24px"))
        this.addRule("h3", *arrayOf("font-size: 18px"))
        this.addRule("h4", *arrayOf("font-size: 16px"))
        this.addRule("h5", *arrayOf("font-size: 14px"))
        this.addRule("h6", *arrayOf("font-size: 14px"))
        this.addRule("pre", *arrayOf("position: relative", "padding: 14px 10px", "border: 0", "border-radius: 3px", "background-color: #f6f8fa"))
        this.addRule("pre code", *arrayOf("position: relative", "line-height: 1.45", "background-color: transparent"))
        this.addRule("table tr:nth-child(2n)", *arrayOf("background-color: #f6f8fa"))
        this.addRule("table th", *arrayOf("padding: 6px 13px", "border: 1px solid #dfe2e5"))
        this.addRule("table td", *arrayOf("padding: 6px 13px", "border: 1px solid #dfe2e5"))
        this.addRule("kbd", *arrayOf("color: #444d56", "font-family: Consolas, \"Liberation Mono\", Menlo, Courier, monospace", "background-color: #fcfcfc", "border: solid 1px #c6cbd1", "border-bottom-color: #959da5", "border-radius: 3px", "box-shadow: inset 0 -1px 0 #959da5"))
        this.addRule("pre[language]::before", *arrayOf("content: attr(language)", "position: absolute", "top: 0", "right: 5px", "padding: 2px 1px", "text-transform: uppercase", "color: #666", "font-size: 8.5px"))
        this.addRule("pre:not([language])", *arrayOf("padding: 6px 10px"))
        this.addRule(".footnotes li p:last-of-type", *arrayOf("display: inline"))
        this.addRule(".yt-player", *arrayOf("box-shadow: 0px 0px 12px rgba(0,0,0,0.2)"))
        this.addRule(".scrollup", *arrayOf("background-color: #00BF4C"))
        this.addRule(".hljs-comment", *arrayOf("color: #8e908c"))
        this.addRule(".hljs-quote", *arrayOf("color: #8e908c"))
        this.addRule(".hljs-variable", *arrayOf("color: #c82829"))
        this.addRule(".hljs-template-variable", *arrayOf("color: #c82829"))
        this.addRule(".hljs-tag", *arrayOf("color: #c82829"))
        this.addRule(".hljs-name", *arrayOf("color: #c82829"))
        this.addRule(".hljs-selector-id", *arrayOf("color: #c82829"))
        this.addRule(".hljs-selector-class", *arrayOf("color: #c82829"))
        this.addRule(".hljs-regexp", *arrayOf("color: #c82829"))
        this.addRule(".hljs-deletion", *arrayOf("color: #c82829"))
        this.addRule(".hljs-number", *arrayOf("color: #f5871f"))
        this.addRule(".hljs-built_in", *arrayOf("color: #f5871f"))
        this.addRule(".hljs-builtin-name", *arrayOf("color: #f5871f"))
        this.addRule(".hljs-literal", *arrayOf("color: #f5871f"))
        this.addRule(".hljs-type", *arrayOf("color: #f5871f"))
        this.addRule(".hljs-params", *arrayOf("color: #f5871f"))
        this.addRule(".hljs-meta", *arrayOf("color: #f5871f"))
        this.addRule(".hljs-link", *arrayOf("color: #f5871f"))
        this.addRule(".hljs-attribute", *arrayOf("color: #eab700"))
        this.addRule(".hljs-string", *arrayOf("color: #718c00"))
        this.addRule(".hljs-symbol", *arrayOf("color: #718c00"))
        this.addRule(".hljs-bullet", *arrayOf("color: #718c00"))
        this.addRule(".hljs-addition", *arrayOf("color: #718c00"))
        this.addRule(".hljs-title", *arrayOf("color: #4271ae"))
        this.addRule(".hljs-section", *arrayOf("color: #4271ae"))
        this.addRule(".hljs-keyword", *arrayOf("color: #8959a8"))
        this.addRule(".hljs-selector-tag", *arrayOf("color: #8959a8"))
    }
}

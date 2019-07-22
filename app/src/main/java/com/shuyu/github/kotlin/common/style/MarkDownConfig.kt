package com.shuyu.github.kotlin.common.style

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.TextUtils
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.MarkwonPlugin
import io.noties.markwon.image.AsyncDrawableLoader
import io.noties.markwon.syntax.Prism4jTheme
import io.noties.markwon.syntax.Prism4jThemeDarkula
import io.noties.markwon.syntax.SyntaxHighlight
import io.noties.prism4j.AbsVisitor
import io.noties.prism4j.Prism4j
import io.noties.prism4j.annotations.PrismBundle
import io.noties.markwon.core.spans.LastLineSpacingSpan
import io.noties.markwon.RenderProps
import io.noties.markwon.MarkwonConfiguration
import io.noties.markwon.SpanFactory
import io.noties.markwon.MarkwonSpansFactory
import io.noties.markwon.ext.tables.TablePlugin
import io.noties.markwon.html.HtmlPlugin
import io.noties.markwon.image.AsyncDrawable
import io.noties.markwon.image.glide.GlideImagesPlugin
import io.noties.markwon.linkify.LinkifyPlugin
import io.noties.markwon.recycler.table.TableEntryPlugin
import io.noties.markwon.syntax.SyntaxHighlightPlugin


/**
 * markdown文件显示配置
 */

@PrismBundle(includeAll = true, grammarLocatorClassName = ".MyGrammarLocator")
object MarkDownConfig {

    fun getConfig(context: Context): ArrayList<AbstractMarkwonPlugin> {
        val prism4j = Prism4j(MyGrammarLocator())
        return arrayListOf(GlideImagesPlugin.create(context.applicationContext),
                GlideImagesPlugin.create(context),
                LinkifyPlugin.create(),
                HtmlPlugin.create(),
                TablePlugin.create(context),
                TableEntryPlugin.create(context.applicationContext),
                SyntaxHighlightPlugin.create(prism4j, Prism4jThemeDarkula.create(), "java"))
    }

}


class GSYPrism4jSyntaxHighlight protected constructor(
        private val prism4j: Prism4j,
        private val theme: Prism4jTheme,
        private val fallback: String) : SyntaxHighlight {

    override fun highlight(info: String?, code: String): CharSequence {
        // if info is null, do not highlight -> LICENCE footer very commonly wrapped inside code
        // block without syntax name specified (so, do not highlight)
        return if (info == null)
            highlightNoLanguageInfo(code)
        else
            highlightWithLanguageInfo(info, code)
    }

    protected fun highlightNoLanguageInfo(code: String): CharSequence {
        return code
    }

    protected fun highlightWithLanguageInfo(info: String, code: String): CharSequence {

        val out: CharSequence

        var language = ""
        var grammar: Prism4j.Grammar? = null
        run {
            var _language = info
            var _grammar: Prism4j.Grammar? = prism4j.grammar(info)
            if (_grammar == null && !TextUtils.isEmpty(fallback)) {
                _language = fallback
                _grammar = prism4j.grammar(fallback)
            }
            language = _language
            grammar = _grammar
        }

        out = if (grammar != null) {
            highlight(language, grammar!!, code)
        } else {
            code
        }

        return out
    }

    protected fun highlight(language: String, grammar: Prism4j.Grammar, code: String): CharSequence {
        val builder = SpannableStringBuilder()
        val visitor = GSYPrism4jSyntaxVisitor(language, theme, builder)
        if (code.isNotBlank()) {
            visitor.visit(prism4j.tokenize(code, grammar))
        }
        return builder
    }

    protected fun prism4j(): Prism4j {
        return prism4j
    }

    protected fun theme(): Prism4jTheme {
        return theme
    }

    protected fun fallback(): String? {
        return fallback
    }

    companion object {

        fun create(
                prism4j: Prism4j,
                theme: Prism4jTheme): GSYPrism4jSyntaxHighlight {
            return GSYPrism4jSyntaxHighlight(prism4j, theme, "")
        }

        fun create(
                prism4j: Prism4j,
                theme: Prism4jTheme,
                fallback: String?): GSYPrism4jSyntaxHighlight {
            return GSYPrism4jSyntaxHighlight(prism4j, theme, fallback ?: "")
        }
    }
}


internal class GSYPrism4jSyntaxVisitor(
        private val language: String,
        private val theme: Prism4jTheme,
        private val builder: SpannableStringBuilder) : AbsVisitor() {

    override fun visitText(text: Prism4j.Text) {
        builder.append(text.literal())
    }

    override fun visitSyntax(syntax: Prism4j.Syntax) {

        val start = builder.length
        visit(syntax.children())
        val end = builder.length

        if (end != start) {
            theme.apply(language, syntax, builder, start, end)
        }
    }
}

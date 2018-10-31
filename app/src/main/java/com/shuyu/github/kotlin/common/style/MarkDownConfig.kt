package com.shuyu.github.kotlin.common.style

import android.content.Context
import com.shuyu.github.kotlin.GSYGithubApplication
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.utils.dp
import ru.noties.markwon.SpannableConfiguration
import ru.noties.markwon.il.AsyncDrawableLoader
import ru.noties.markwon.syntax.Prism4jSyntaxHighlight
import ru.noties.markwon.syntax.Prism4jThemeDarkula
import ru.noties.prism4j.Prism4j
import ru.noties.prism4j.annotations.PrismBundle


@PrismBundle(includeAll = true)
object MarkDownConfig {

    fun getConfig(context: Context): SpannableConfiguration {
        val drawable = GSYGithubApplication.instance.getDrawable(R.drawable.logo)
        drawable.setBounds(0, 0, 50.dp, 50.dp)
        val loader = AsyncDrawableLoader.builder()
                .resources(context.resources)
                .errorDrawable(drawable)
                .build()

        val prism4j = Prism4j(GrammarLocatorDef())
        val highlight =
                Prism4jSyntaxHighlight.create(prism4j, Prism4jThemeDarkula.create(), "java")


        return SpannableConfiguration.builder(context)
                .asyncDrawableLoader(loader)
                .softBreakAddsNewLine(true)
                .syntaxHighlight(highlight)
                .build()
    }

}



package com.shuyu.github.kotlin.common.style

import android.content.Context
import com.shuyu.github.kotlin.R
import ru.noties.markwon.SpannableConfiguration
import ru.noties.markwon.SpannableFactoryDef
import ru.noties.markwon.il.AsyncDrawableLoader
import ru.noties.markwon.renderer.ImageSize
import ru.noties.markwon.renderer.ImageSizeResolver
import ru.noties.markwon.spans.AsyncDrawable
import ru.noties.markwon.spans.AsyncDrawableSpan
import ru.noties.markwon.spans.SpannableTheme
import ru.noties.markwon.syntax.Prism4jSyntaxHighlight
import ru.noties.markwon.syntax.Prism4jThemeDarkula
import ru.noties.prism4j.Prism4j
import ru.noties.prism4j.annotations.PrismBundle
import kotlin.properties.Delegates


@PrismBundle(includeAll = true)
object MarkDownConfig {

    var instance: SpannableConfiguration by Delegates.notNull()

    fun init(context: Context) {
        val loader = AsyncDrawableLoader.builder()
                .resources(context.resources)
                .errorDrawable(context.getDrawable(R.drawable.logo))
                .build()

        val prism4j = Prism4j(GrammarLocatorDef())
        val highlight =
                Prism4jSyntaxHighlight.create(prism4j, Prism4jThemeDarkula.create(), "java")


        instance = SpannableConfiguration.builder(context)
                .asyncDrawableLoader(loader)
                .factory(GSYSpannableFactoryDef())
                .softBreakAddsNewLine(true)
                .syntaxHighlight(highlight)
                .build()
    }

}

class GSYSpannableFactoryDef : SpannableFactoryDef() {
    override fun image(theme: SpannableTheme, destination: String, loader: AsyncDrawable.Loader, imageSizeResolver: ImageSizeResolver, imageSize: ImageSize?, replacementTextIsLink: Boolean): Any? {
        return AsyncDrawableSpan(
                theme,
                AsyncDrawable(
                        destination,
                        loader,
                        imageSizeResolver,
                        imageSize
                ),
                AsyncDrawableSpan.ALIGN_BOTTOM,
                replacementTextIsLink
        )
    }
}



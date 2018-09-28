package com.shuyu.github.kotlin.common.style

import android.content.Context
import android.graphics.Typeface
import com.mikepenz.iconics.typeface.IIcon
import com.mikepenz.iconics.typeface.ITypeface
import java.util.*

/**
 * Created by guoshuyu
 * Date: 2018-09-28
 */
class GSYIconfont : ITypeface {

    companion object {
        private const val TTF_FILE = "iconfont.ttf"
    }

    private var typeface: Typeface? = null

    private var mChars: HashMap<String, Char>? = null

    override fun getIcon(key: String): IIcon {
        return Icon.valueOf(key)
    }

    override fun getCharacters(): HashMap<String, Char>? {
        if (mChars == null) {
            val aChars = HashMap<String, Char>()
            for (v in Icon.values()) {
                aChars[v.name] = v.character
            }
            mChars = aChars
        }

        return mChars
    }

    override fun getMappingPrefix(): String {
        return "gsy"
    }

    override fun getFontName(): String {
        return "GSY Iconfont"
    }

    override fun getVersion(): String {
        return "1.0.0"
    }

    override fun getIconCount(): Int {
        return mChars!!.size
    }

    override fun getIcons(): Collection<String> {
        val icons = LinkedList<String>()

        for (value in Icon.values()) {
            icons.add(value.name)
        }

        return icons
    }

    override fun getAuthor(): String {
        return "Iconfont"
    }

    override fun getUrl(): String {
        return "http://zavoloklom.github.io/material-design-iconic-font/"
    }

    override fun getDescription(): String {
        return "Iconfont."
    }

    override fun getLicense(): String {
        return "SIL OFL 1.1"
    }

    override fun getLicenseUrl(): String {
        return "http://scripts.sil.org/OFL"
    }

    override fun getTypeface(context: Context): Typeface? {
        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.assets,
                        "fonts/$TTF_FILE")
            } catch (e: Exception) {
                return null
            }

        }
        return typeface
    }

    enum class Icon constructor(internal var character: Char) : IIcon {

        HOME('\ue624'),
        MORE('\ue674'),
        SEARCH('\ue61c'),

        MAIN_DT('\ue684'),
        MAIN_QS('\ue818'),
        MAIN_MY('\ue6d0'),
        MAIN_SEARCH('\ue61c'),

        LOGIN_USER('\ue666'),
        LOGIN_PW('\ue60e'),

        REPOS_ITEM_USER('\ue63e'),
        REPOS_ITEM_STAR('\ue643'),
        REPOS_ITEM_FORK('\ue67e'),
        REPOS_ITEM_ISSUE('\ue661'),

        REPOS_ITEM_STARED('\ue698'),
        REPOS_ITEM_WATCH('\ue681'),
        REPOS_ITEM_WATCHED('\ue629'),
        REPOS_ITEM_FILE('\uea77'),
        REPOS_ITEM_NEXT('\ue610'),

        USER_ITEM_COMPANY('\ue63e'),
        USER_ITEM_LOCATION('\ue7e6'),
        USER_ITEM_LINK('\ue670'),
        USER_NOTIFY('\ue600'),

        ISSUE_ITEM_ISSUE('\ue661'),
        ISSUE_ITEM_COMMENT('\ue6ba'),
        ISSUE_ITEM_ADD('\ue662'),

        NOTIFY_ALL_READ('\ue62f');


        override fun getFormattedName(): String {
            return "{$name}"
        }

        override fun getCharacter(): Char {
            return character
        }

        override fun getName(): String {
            return name
        }

        override fun getTypeface(): ITypeface {
            return GSYIconfont()
        }

    }
}
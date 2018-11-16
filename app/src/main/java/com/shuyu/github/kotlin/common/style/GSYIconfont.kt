package com.shuyu.github.kotlin.common.style

import android.content.Context
import android.graphics.Typeface
import com.mikepenz.iconics.typeface.IIcon
import com.mikepenz.iconics.typeface.ITypeface
import java.util.*

/**
 * 自定义字体库图标
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
        return "GSY"
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

        GSY_HOME('\ue624'),
        GSY_MORE('\ue674'),
        GSY_SEARCH('\ue61c'),
        GSY_EMAIL('\ue685'),
        GSY_FOCUS('\ue60a'),
        GSY_UN_FOCUS('\ue60b'),
        GSY_DES('\ue6c4'),

        GSY_MAIN_DT('\ue684'),
        GSY_MAIN_QS('\ue818'),
        GSY_MAIN_MY('\ue6d0'),
        GSY_MAIN_SEARCH('\ue61c'),

        GSY_LOGIN_USER('\ue666'),
        GSY_LOGIN_PW('\ue60e'),

        GSY_REPOS_ITEM_USER('\ue63e'),
        GSY_REPOS_ITEM_STAR('\ue643'),
        GSY_REPOS_ITEM_FORK('\ue67e'),
        GSY_REPOS_ITEM_ISSUE('\ue661'),
        GSY_REPOS_ITEM_OPEN('\ue653'),
        GSY_REPOS_ITEM_ALL('\ue603'),
        GSY_REPOS_ITEM_CLOSE('\ue65c'),

        GSY_REPOS_ITEM_STARED('\ue698'),
        GSY_REPOS_ITEM_WATCH('\ue681'),
        GSY_REPOS_ITEM_WATCHED('\ue629'),
        GSY_REPOS_ITEM_FILE('\uea77'),
        GSY_REPOS_ITEM_NEXT('\ue610'),

        GSY_USER_ITEM_COMPANY('\ue63e'),
        GSY_USER_ITEM_LOCATION('\ue7e6'),
        GSY_USER_ITEM_LINK('\ue670'),
        GSY_USER_NOTIFY('\ue600'),

        GSY_ISSUE_ITEM_ISSUE('\ue661'),
        GSY_ISSUE_ITEM_COMMENT('\ue6ba'),
        GSY_ISSUE_ITEM_ADD('\ue662'),

        GSY_NOTIFY_ALL_READ('\ue62f'),

        GSY_PUSH_ADD('\ue605'),
        GSY_PUSH_REDUCE('\ue63d'),
        GSY_PUSH_EDIT('\ue611'),


        GSY_MD_1('\ue60c'),
        GSY_MD_2('\ue620'),
        GSY_MD_3('\ue621'),
        GSY_MD_4('\ue654'),
        GSY_MD_5('\ue613'),
        GSY_MD_6('\ue63a'),
        GSY_MD_7('\uea77'),
        GSY_MD_8('\ue670'),
        GSY_MD_9('\ue651');


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
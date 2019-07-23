package com.shuyu.github.kotlin.common.style

import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import com.mikepenz.iconics.Iconics
import com.mikepenz.iconics.typeface.IIcon
import com.mikepenz.iconics.typeface.ITypeface
import com.shuyu.github.kotlin.R
import java.util.*

/**
 * 自定义字体库图标
 * Created by guoshuyu
 * Date: 2018-09-28
 */
@Suppress("EnumEntryName")
object GSYIconfont : ITypeface {

    override val fontRes: Int
        get() = R.font.iconfont

    override val characters: Map<String, Char> by lazy {
        Icon.values().associate { it.name to it.character }
    }

    override val mappingPrefix: String
        get() = "GSY"

    override val fontName: String
        get() = "GSY Iconfont"

    override val version: String
        get() = "1.0.0"

    override val iconCount: Int
        get() = characters.size

    override val icons: List<String>
        get() = characters.keys.toCollection(LinkedList())

    override val author: String
        get() = "Iconfont"

    override val url: String
        get() = "https://github.com/github/octicons"

    override val description: String
        get() = "GitHub's icon font https://octicons.github.com/"

    override val license: String
        get() = " SIL OFL 1.1"

    override val licenseUrl: String
        get() = "http://scripts.sil.org/OFL"

    override fun getIcon(key: String): IIcon = Icon.valueOf(key)

    override val rawTypeface: Typeface
        get() {
            return ResourcesCompat.getFont(Iconics.applicationContext, fontRes)!!
        }

    enum class Icon constructor(override val character: Char) : IIcon {

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

        override val typeface: ITypeface by lazy { GSYIconfont }
    }
}
package com.shuyu.github.kotlin.common.utils

import android.content.ClipData
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import androidx.core.content.ContextCompat
import android.util.TypedValue
import android.util.TypedValue.COMPLEX_UNIT_DIP
import android.util.TypedValue.COMPLEX_UNIT_SP
import java.util.regex.Pattern

/**
 * 拓展类
 * Created by guoshuyu
 * Date: 2018-10-24
 */

private val metrics = Resources.getSystem().displayMetrics

val Float.dp: Float
    get() = TypedValue.applyDimension(COMPLEX_UNIT_DIP, this, metrics)

val Int.dp: Int
    get() = TypedValue.applyDimension(COMPLEX_UNIT_DIP, this.toFloat(), metrics).toInt()

val Float.sp: Float
    get() = TypedValue.applyDimension(COMPLEX_UNIT_SP, this, metrics)

val Int.sp: Int
    get() = TypedValue.applyDimension(COMPLEX_UNIT_SP, this.toFloat(), metrics).toInt()

val Number.px: Number
    get() = this

val Number.px2dp: Int
    get() = (this.toFloat() / metrics.density).toInt()

val Number.px2sp: Int
    get() = (this.toFloat() / metrics.scaledDensity).toInt()

/**
 * 拓展颜色转String
 */
fun Context.colorIdToString(colorId: Int): String {
    val stringBuffer = StringBuffer()
    val color = ContextCompat.getColor(this, colorId)
    stringBuffer.append("#")
    stringBuffer.append(Integer.toHexString(Color.red(color)))
    stringBuffer.append(Integer.toHexString(Color.green(color)))
    stringBuffer.append(Integer.toHexString(Color.blue(color)))
    return stringBuffer.toString()
}

/**
 * 拓展复制到粘粘版
 */
fun Context.copy(string: String) {
    val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE)
            as android.content.ClipboardManager
    val clip = ClipData.newPlainText("", string)
    clipboardManager.primaryClip = clip
}

/**
 * 拓展获取版本号
 */
fun Context.getVersionName(): String {
    val manager = packageManager.getPackageInfo(packageName, 0)
    return manager.versionName
}


/**
 * 拓展列表到文本转化
 */
fun ArrayList<String>.toSplitString(): String {
    var result = ""
    this.forEach {
        result = "$result/$it"
    }
    return result.replace("./", "")
}

/**
 * 拓展String版本号对比
 */
fun String.compareVersion(v2: String?): String? {
    if (v2 == null || v2.isEmpty()) return null
    val regEx = "[^0-9]"
    val p = Pattern.compile(regEx)
    var s1: String = p.matcher(this).replaceAll("").trim()
    var s2: String = p.matcher(v2).replaceAll("").trim()

    val cha: Int = s1.length - s2.length
    val buffer = StringBuffer()
    var i = 0
    while (i < Math.abs(cha)) {
        buffer.append("0")
        ++i
    }

    if (cha > 0) {
        buffer.insert(0, s2)
        s2 = buffer.toString()
    } else if (cha < 0) {
        buffer.insert(0, s1)
        s1 = buffer.toString()
    }

    val s1Int = s1.toInt()
    val s2Int = s2.toInt()

    return if (s1Int > s2Int) this
    else v2
}
package com.shuyu.github.kotlin.common.compat

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

internal class AlertBuilder(private val context: Context) {
    var title: CharSequence = ""
    var message: CharSequence = ""

    @DrawableRes
    var iconResource: Int = 0

    private var positiveText: CharSequence? = null
    private var positiveListener: ((DialogInterface) -> Unit)? = null
    private var negativeText: CharSequence? = null
    private var negativeListener: ((DialogInterface) -> Unit)? = null
    private var neutralText: CharSequence? = null
    private var neutralListener: ((DialogInterface) -> Unit)? = null

    fun positiveButton(@StringRes textId: Int, action: (DialogInterface) -> Unit) {
        positiveText = context.getString(textId)
        positiveListener = action
    }

    fun negativeButton(@StringRes textId: Int, action: (DialogInterface) -> Unit) {
        negativeText = context.getString(textId)
        negativeListener = action
    }

    fun cancelButton(action: (DialogInterface) -> Unit) {
        neutralText = context.getString(android.R.string.cancel)
        neutralListener = action
    }

    fun okButton(action: (DialogInterface) -> Unit) {
        positiveText = context.getString(android.R.string.ok)
        positiveListener = action
    }

    fun show(): AlertDialog {
        val builder = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
        if (iconResource != 0) builder.setIcon(iconResource)
        positiveText?.let { t ->
            builder.setPositiveButton(t) { d, _ -> positiveListener?.invoke(d) }
        }
        negativeText?.let { t ->
            builder.setNegativeButton(t) { d, _ -> negativeListener?.invoke(d) }
        }
        neutralText?.let { t ->
            builder.setNeutralButton(t) { d, _ -> neutralListener?.invoke(d) }
        }
        return builder.show()
    }
}

internal fun Context.alert(init: AlertBuilder.() -> Unit) = AlertBuilder(this).apply(init)
internal fun Activity.alert(init: AlertBuilder.() -> Unit) = AlertBuilder(this).apply(init)
internal fun Fragment.alert(init: AlertBuilder.() -> Unit) = AlertBuilder(requireContext()).apply(init)

package com.shuyu.github.kotlin.common.compat

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

internal fun Context.toast(message: CharSequence): Toast =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).apply { show() }

internal fun Context.toast(@StringRes resId: Int): Toast =
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).apply { show() }

internal fun Fragment.toast(message: CharSequence): Toast =
    requireContext().toast(message)

internal fun Fragment.toast(@StringRes resId: Int): Toast =
    requireContext().toast(resId)

internal fun Context.browse(url: String, newTask: Boolean = false): Boolean {
    return try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        if (newTask) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

internal fun Context.share(text: String, subject: String = ""): Boolean {
    return try {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(intent, null))
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

internal val Intent.clearTask: Intent
    get() = this.apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) }

internal val Intent.clearTop: Intent
    get() = this.apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) }

internal val Intent.singleTop: Intent
    get() = this.apply { addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP) }

internal val Intent.newTask: Intent
    get() = this.apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }

internal fun Context.runOnUiThread(action: () -> Unit) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        action()
    } else {
        Handler(Looper.getMainLooper()).post(action)
    }
}

internal fun Fragment.runOnUiThread(action: () -> Unit) {
    activity?.runOnUiThread { action() }
}

internal fun ViewModel.runOnUiThread(action: () -> Unit) {
    Handler(Looper.getMainLooper()).post(action)
}

internal fun Any.runOnUiThread(action: () -> Unit) {
    Handler(Looper.getMainLooper()).post(action)
}

internal fun doAsync(task: () -> Unit) {
    Thread { task() }.start()
}

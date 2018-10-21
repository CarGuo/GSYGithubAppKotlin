package com.shuyu.github.kotlin.module.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v4.app.Fragment
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * A lazy property that gets cleaned up when the fragment is destroyed.
 *
 * Accessing this variable in a destroyed fragment will throw NPE.
 */
class AutoClearedValue<T : Any?>(val fragment: Fragment) : ReadWriteProperty<Fragment, T?> {
    private var _value: T? = null

    init {
        fragment.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                _value = null
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T? {
        return _value
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T?) {
        _value = value
    }
}

/**
 * Creates an [AutoClearedValue] associated with this fragment.
 */
fun <T : Any?> Fragment.autoCleared() = AutoClearedValue<T?>(this)
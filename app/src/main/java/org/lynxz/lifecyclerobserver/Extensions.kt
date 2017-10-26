package org.lynxz.lifecyclerobserver

import android.app.Activity
import android.app.Application
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.widget.Toast

/**
 * Created by lynxz on 13/01/2017.
 * 常用扩展函数
 */
fun CharSequence.isEmpty(): Boolean {
    return TextUtils.isEmpty(this)
}

fun Fragment.showToast(msg: String) {
    activity.showToast(msg)
}

fun Fragment.showToast(msgId: Int) {
    activity.showToast(msgId)
}

fun Activity.showToast(msgId: Int) {
    application.showToast(msgId)
}

fun Activity.showToast(msg: String) {
    application.showToast(msg)
}

fun Application?.showToast(msg: String) {
    this?.let {
        Toast.makeText(this.applicationContext, msg, Toast.LENGTH_SHORT).show()
    }
}

fun Application?.showToast(msgId: Int) {
    this?.let {
        Toast.makeText(this.applicationContext, msgId, Toast.LENGTH_SHORT).show()
    }
}

fun Fragment.getStringRes(@StringRes resId: Int): String {
    return this.activity.resources.getString(resId)
}

inline fun debugConf(code: () -> Unit) {
    if (BuildConfig.DEBUG) {
        code()
    }
}
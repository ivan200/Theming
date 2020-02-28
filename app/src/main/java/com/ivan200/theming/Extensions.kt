package com.ivan200.theming

import android.util.Log
import android.view.View
import java.text.SimpleDateFormat
import java.util.*

//
// Created by Ivan200 on 17.02.2020.
//
fun printToLogThis(message: String = "") {
    val stackTrace = Thread.currentThread().stackTrace
    val index1 = stackTrace.indexOfFirst { it.methodName.contains("printToLogThis") }
    val index2 = stackTrace.withIndex().indexOfFirst {
        it.index > index1 && !it.value.methodName.contains("printToLogThis")
    }
    if (stackTrace.size > index2) {
        val stackTraceElement = stackTrace[index2]
        val time = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault()).format(System.currentTimeMillis())
        Log.w("Log", "$time $stackTraceElement $message")
    }
}

fun Boolean?.toInt() = if(this == null) -1 else if(this) 1 else 0

fun <T:View> T.show() : T {
    if (visibility != View.VISIBLE) visibility = View.VISIBLE
    return this
}

fun <T : View> T.hide(): T {
    if (visibility != View.GONE) visibility = View.GONE
    return this
}

fun <T : View> T.invisible() : T {
    if (visibility != View.INVISIBLE) visibility = View.INVISIBLE
    return this
}

fun <T : View> T.showIf(condition: () -> Boolean): T {
    return if (condition()) show() else hide()
}

fun <T : View> T.hideIf(condition: () -> Boolean): T {
    return if (condition()) hide() else show()
}

fun <T : View> T.invisibleIf(condition: () -> Boolean): T {
    return if (condition()) invisible() else show()
}

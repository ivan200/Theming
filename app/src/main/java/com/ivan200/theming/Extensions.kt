package com.ivan200.theming

import android.view.View

//
// Created by Ivan200 on 17.02.2020.
//


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
package com.ivan200.theming

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

//
// Created by Ivan200 on 17.02.2020.
//

fun <T: Number> T?.orElse(number: T) =
    if (this != null && this != 0) this else number

//fun Int?.orElse(int: Int) =
//    if (this != null && this != 0) this else int
//
//fun Int?.or(int: Int?) =
//    if (this != null && this != 0) this else int


fun Long?.orElse(long: Long) =
    if (this != null && this != 0L) this else long

fun Long?.or(long: Long?) =
    if (this != null && this != 0L) this else long


inline fun <reified T : Activity> Context.launchActivity(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = Intent(this, T::class.java)
    intent.init()
    startActivity(intent, options)

}

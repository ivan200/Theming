package com.ivan200.theming.preference

import android.content.Context
import com.ivan200.theming.Theming
import com.ivan200.theming.prefs
import com.ivan200.theming.toInt
import kotlin.reflect.KMutableProperty0

//
// Created by Ivan200 on 21.02.2020.
//

abstract class Setting(val context: Context) {
    abstract val title: String
    open val subtitle: String get() = ""
    open val defaultValue: Int get() = -1
    open var value: Int
        get() = -1
        set(value) = Unit
}

abstract class SettingPref(context: Context): Setting(context) {
    override val title: String get() = prefProperty.name.capitalize()
    abstract val prefProperty: KMutableProperty0<Int>
    override var value: Int
        get() = prefProperty.get()
        set(value)  =  prefProperty.set(value)
}

abstract class ColorPref(context: Context) : SettingPref(context)
abstract class CheckPref(context: Context) : SettingPref(context)

class COLOR_PRIMARY(context: Context) : ColorPref(context) {
    override val prefProperty get() = context.prefs::colorPrimary
    override val defaultValue: Int get() = Theming.colorPrimary
    override val subtitle: String get() = "Основной цвет подсветки"
}
class COLOR_BACKGROUND(context: Context) : ColorPref(context) {
    override val prefProperty get() = context.prefs::colorBackground
    override val defaultValue: Int get() = Theming.colorBackground
    override val subtitle: String get() = "Цвет фона"
}
class NAVBAR_DRAW_SYSTEM_BAR(context: Context) : CheckPref(context) {
    override val prefProperty get() = context.prefs::navBarDrawSystemBar
    override val defaultValue: Int get() = Theming.navBarDrawSystemBar.toInt()
    override val subtitle: String get() = "Отрисовывать ли системную плашку под навбаром если его цвет прозрачный"
}

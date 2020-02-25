package com.ivan200.theming.preference

import android.content.Context
import com.ivan200.theming.Theming
import kotlin.reflect.KMutableProperty0

//
// Created by Ivan200 on 21.02.2020.
//

abstract class Setting(val context: Context) {
    abstract val title: String
    open val subTitle: String get() = ""
}

abstract class SettingPref<T>(context: Context) : Setting(context) {
    override val title: String get() = prefProperty.name.capitalize()
    abstract val prefProperty: KMutableProperty0<T?>
    open var value: T?
        get() = prefProperty.get()
        set(value)  =  prefProperty.set(value)
    abstract val defaultValue: T
    open val anyValue get() = value ?: defaultValue
    open val useDefault get() = value == null
}

abstract class ColorPref(context: Context) : SettingPref<Int>(context)
abstract class CheckPref(context: Context) : SettingPref<Boolean>(context)

class SECTION_DEFAULTS(context: Context) : Setting(context) {
    override val title: String get() = "Основные цвета"
}

class COLOR_PRIMARY(context: Context) : ColorPref(context) {
    override val prefProperty get() = Prefs::colorPrimary
    override val defaultValue: Int get() = Theming.colorPrimary
    override val subTitle: String get() = "Основной цвет подсветки"
}
class COLOR_BACKGROUND(context: Context) : ColorPref(context) {
    override val prefProperty get() = Prefs::colorBackground
    override val defaultValue: Int get() = Theming.colorBackground
    override val subTitle: String get() = "Цвет фона"
}
class NAVBAR_DRAW_SYSTEM_BAR(context: Context) : CheckPref(context) {
    override val prefProperty get() = Prefs::navBarDrawSystemBar
    override val defaultValue: Boolean get() = Theming.navBarDrawSystemBar
    override val subTitle: String get() = "Отрисовывать ли системную плашку под навбаром если его цвет прозрачный"
}


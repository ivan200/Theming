package com.ivan200.theming.settings

import android.content.Context
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

    abstract val defaultValue: ()-> T
    open val anyValue get() = value ?: defaultValue.invoke()
    open val useDefault get() = value == null
}

class ColorSetting(context: Context,
                   override val prefProperty: KMutableProperty0<Int?>,
                   override val defaultValue: ()-> Int,
                   override val subTitle: String ) : SettingPref<Int>(context)

class CheckSetting(context: Context,
                   override val prefProperty: KMutableProperty0<Boolean?>,
                   override val defaultValue: ()-> Boolean,
                   override val subTitle: String ) : SettingPref<Boolean>(context)

class HeaderSetting(context: Context,
                    override val title: String) : Setting(context)
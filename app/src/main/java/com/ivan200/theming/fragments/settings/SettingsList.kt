package com.ivan200.theming.fragments.settings

import android.content.Context
import com.ivan200.theming.Theming
import com.ivan200.theming.preferences.Prefs
import com.ivan200.theminglib.ThemeColor
import com.ivan200.theminglib.ThemeFlag

//
// Created by Ivan200 on 21.02.2020.
//

abstract class Setting(val context: Context) {
    abstract val title: String
    open val subTitle: String get() = ""
}

class ColorSetting(context: Context,
                   val themeColor: ThemeColor,
                   override val subTitle: String = "",
                   val allowTransparent: Boolean = false
                   ) : Setting(context){
    override val title: String get() = themeColor.name.capitalize()
    open var value: Int?
        get() = Prefs.getIntPref(themeColor.name)
        set(value) = Prefs.setIntPref(themeColor.name, value)
    open val anyValue get() = value ?: defaultValue
    open val defaultValue get() = Theming.getColor(themeColor)
    open val useDefault get() = value == null
}

class CheckSetting(context: Context,
                   val themeFlag: ThemeFlag,
                   override val subTitle: String ) : Setting(context){
    override val title: String get() = themeFlag.name.capitalize()
    open var value: Boolean?
        get() = Prefs.getBoolPref(themeFlag.name)
        set(value) = Prefs.setBoolPref(themeFlag.name, value)
    open val anyValue get() = value ?: defaultValue
    open val defaultValue get() = Theming.getFlag(themeFlag)
    open val useDefault get() = value == null
}

class HeaderSetting(context: Context,
                    override val title: String) : Setting(context)
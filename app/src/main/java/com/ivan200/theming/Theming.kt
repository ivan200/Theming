package com.ivan200.theming

import com.ivan200.theming.preferences.Prefs
import com.ivan200.theminglib.ThemeColor
import com.ivan200.theminglib.ThemeFlag
import com.ivan200.theminglib.ThemingBase

object Theming : ThemingBase(){
//    override val colorPrimary: Int get() = Prefs.colorPrimary ?: super.colorPrimary
//    override val colorBackground: Int get() = Prefs.colorBackground ?: super.colorBackground


    override fun getColor(color: ThemeColor): Int {
        val newColor = Prefs.getIntPref(color.name)

//        val newColor = when(color){
//            ThemeColor.colorPrimary -> Prefs.getIntPref(ThemeColor.colorPrimary.name)
//            ThemeColor.colorBackground -> Prefs.getIntPref(ThemeColor.colorBackground.name)
//            else -> null
//        }
        return newColor ?: super.getColor(color)
    }

    override fun getFlag(flag: ThemeFlag): Boolean {
        val newFlag = Prefs.getBoolPref(flag.name)
        return newFlag ?: super.getFlag(flag)
    }

}
package com.ivan200.theming

import com.ivan200.theming.preferences.Prefs
import com.ivan200.theminglib.ThemingBase

object Theming : ThemingBase(){
    override val colorPrimary: Int get() = Prefs.colorPrimary ?: super.colorPrimary
    override val colorBackground: Int get() = Prefs.colorBackground ?: super.colorBackground


}
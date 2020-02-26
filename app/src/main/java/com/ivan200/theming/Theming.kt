package com.ivan200.theming

import com.ivan200.theming.preferences.Prefs
import com.ivan200.theminglib.ThemingBase

object Theming : ThemingBase(){

//    var colors = hashMapOf<String, Int>()
//
//    fun init(colors: HashMap<String, Int>){
//        this.colors = colors
//    }

//    override var colorPrimary: Int = colors["main"] ?: Prefs.colorPrimary
//    override var colorBackground: Int = colors["background"] ?: Prefs.colorBackground

    override val colorPrimary: Int get() = Prefs.colorPrimary ?: super.colorPrimary
    override val colorBackground: Int get() = Prefs.colorBackground ?: super.colorBackground


//    override val colorProgressBar: Int
//        get() = super.colorProgressBar
}
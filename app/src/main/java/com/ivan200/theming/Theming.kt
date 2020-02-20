package com.ivan200.theming

import android.graphics.Color
import com.ivan200.theminglib.ThemingBase

object Theming : ThemingBase(){

//    var colors = hashMapOf<String, Int>()
//
//    fun init(colors: HashMap<String, Int>){
//        this.colors = colors
//    }

//    override var colorPrimary: Int = colors["main"] ?: Prefs.colorPrimary
//    override var colorBackground: Int = colors["background"] ?: Prefs.colorBackground


    override val colorPrimary: Int get() = Color.parseColor("#54a9ff")

//light
//    override val colorBackground: Int get() = Color.parseColor("#f5f5f5")

//dark
    override val colorBackground: Int get() = Color.parseColor("#303030")


//    override val colorProgressBar: Int
//        get() = super.colorProgressBar

}
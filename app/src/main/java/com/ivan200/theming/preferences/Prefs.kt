package com.ivan200.theming.preferences

//
// Created by Ivan200 on 21.02.2020.
//

object Prefs : PrefsBase() {
    var colorPrimary by IntPref()
    var colorBackground by IntPref()
    var navBarDrawSystemBar by BooleanPref()
}
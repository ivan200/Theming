package com.ivan200.theming.preference

import android.content.Context

//
// Created by Ivan200 on 21.02.2020.
//

class Prefs(context: Context) : PrefsBase(context) {
    var colorPrimary by AnyPref(-1)
    var colorBackground by AnyPref(-1)
    var navBarDrawSystemBar by AnyPref(-1)
}
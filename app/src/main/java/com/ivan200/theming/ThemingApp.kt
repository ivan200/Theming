package com.ivan200.theming

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.ivan200.theming.preferences.Prefs

//
// Created by Ivan200 on 25.02.2020.
//

class ThemingApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        Prefs.init(this)
    }
}

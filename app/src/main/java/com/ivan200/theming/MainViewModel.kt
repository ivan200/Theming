package com.ivan200.theming

import androidx.annotation.ColorInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ivan200.theming.fragments.settings.CheckSetting
import com.ivan200.theming.fragments.settings.ColorSetting
import com.ivan200.theming.fragments.settings.Setting
import com.ivan200.theming.utils.Event

//
// Created by Ivan200 on 03.03.2020.
//

class MainViewModel : ViewModel(){
    open var currentPrefsScrollPos = -1
    open var currentPrefsScrollOffset = 0

    var currentChangingSetting: Setting? = null
    fun setChangingSetting(setting: Setting?){
        this.currentChangingSetting = setting
    }

    fun onColorSelected(@ColorInt color: Int?){
        (currentChangingSetting as? ColorSetting)?.apply {
            value = color
            settingChanged()
        }
    }

    fun onCheckChanged(flag: Boolean?){
        (currentChangingSetting as? CheckSetting)?.apply {
            value = flag
            settingChanged()
        }
    }

    private val _settingChanged = MutableLiveData<Event<Unit>>()
    val settingChanged: LiveData<Event<Unit>> = _settingChanged
    private fun settingChanged() {
        _settingChanged.postValue(Event(Unit))
    }


}
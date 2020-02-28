package com.ivan200.theming

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ivan200.theming.fragments.DialogFragmentBuilder
import com.ivan200.theming.fragments.settings.CheckSetting
import com.ivan200.theming.fragments.settings.ColorSetting
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener

class MainActivity : AppCompatActivity(R.layout.activity_main), ColorPickerDialogListener {
    var anySettingChangeListener: (() -> Unit)? = null
    var currentChangingSetting: ColorSetting? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Theming.themeActivity(this)
    }

    override fun onDialogDismissed(dialogId: Int) {
        currentChangingSetting = null
    }

    override fun onColorSelected(dialogId: Int, color: Int) {
        currentChangingSetting?.value = color
        anySettingChangeListener?.invoke()
    }

    fun changeColorSetting(setting: ColorSetting) {
        currentChangingSetting = setting
        ColorPickerDialog.newBuilder()
            .setAllowPresets(false)
            .setShowAlphaSlider(setting.allowTransparent)
            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
            .setColor(setting.anyValue)
            .show(this)
    }

    fun clearColorSetting(setting: ColorSetting) {
        setting.value = null
        anySettingChangeListener?.invoke()
    }


    fun changeFlagSetting(setting: CheckSetting){
        setting.value = !setting.anyValue
        anySettingChangeListener?.invoke()
    }
    fun clearFlagSetting(setting: CheckSetting) {
        setting.value = null
        anySettingChangeListener?.invoke()
    }


    fun showSimpleDialog(){
        DialogFragmentBuilder()
            .withMessage("DialogFragment")
            .withPositiveButton("Ok")
            .withNegativeButton("Cancel")
            .withNeutralButton("Unknown")
            .show(this, 5)
    }
}


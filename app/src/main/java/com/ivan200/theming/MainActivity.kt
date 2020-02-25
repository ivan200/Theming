package com.ivan200.theming

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ivan200.theming.preference.ColorPickerDialogCaller
import com.ivan200.theming.preference.ColorPref
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener

class MainActivity : AppCompatActivity(R.layout.activity_main), ColorPickerDialogListener, ColorPickerDialogCaller {
    var anyColorChangeListener: (() -> Unit)? = null
    var currentChangingSetting: ColorPref? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Theming.themeActivity(this)
    }

    override fun onDialogDismissed(dialogId: Int) {
        currentChangingSetting = null
    }

    override fun onColorSelected(dialogId: Int, color: Int) {
        currentChangingSetting?.value = color
        anyColorChangeListener?.invoke()
    }

    override fun changeColorSetting(setting: ColorPref) {
        currentChangingSetting = setting
        ColorPickerDialog.newBuilder()
            .setAllowPresets(false)
            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
            .setColor(setting.anyValue)
            .show(this)
    }

    fun clearColorSetting(setting: ColorPref) {
        setting.value = null
        anyColorChangeListener?.invoke()
    }
}


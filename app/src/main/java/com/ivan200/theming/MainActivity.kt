package com.ivan200.theming

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ivan200.theming.fragments.settings.CheckSetting
import com.ivan200.theming.fragments.settings.ColorSetting
import com.ivan200.theming.utils.AlertDialogFragmentBuilder
import com.ivan200.theminglib.ThemeUtils
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener

class MainActivity : AppCompatActivity(R.layout.activity_main), ColorPickerDialogListener {
    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this@MainActivity).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Theming.themeActivity(this)
    }

    override fun onDialogDismissed(dialogId: Int) {
        mainViewModel.setChangingSetting(null)
    }

    override fun onColorSelected(dialogId: Int, color: Int) {
        mainViewModel.onColorSelected(color)
    }

    fun changeColorSetting(setting: ColorSetting) {
        mainViewModel.setChangingSetting(setting)
        ColorPickerDialog.newBuilder()
            .setAllowPresets(false)
            .setShowAlphaSlider(setting.allowTransparent)
            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
            .setColor(setting.anyValue)
            .show(this)
    }

    fun clearColorSetting(setting: ColorSetting) {
        mainViewModel.setChangingSetting(setting)
        mainViewModel.onColorSelected(null)
    }


    fun changeFlagSetting(setting: CheckSetting){
        mainViewModel.setChangingSetting(setting)
        mainViewModel.onCheckChanged(!setting.anyValue)
    }
    fun clearFlagSetting(setting: CheckSetting) {
        mainViewModel.setChangingSetting(setting)
        mainViewModel.onCheckChanged(null)
    }

    fun showSimpleAlertDialog(){
        val icon = ThemeUtils.getDrawableResCompat(this, android.R.attr.alertDialogIcon)
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Alert")
            .setMessage("AlertDialog")
            .setIcon(icon)
            .setPositiveButton("Ok") { dialog, id -> }
            .setNegativeButton("Cancel") { dialog, id -> }
            .setNeutralButton("Unknown") { dialog, id -> }
            .setNeutralButtonIcon(ThemeUtils.getDrawableResCompat(this, android.R.attr.alertDialogIcon))
            .show()
        Theming.themeAlertDialog(alertDialog)
    }

    fun showSimpleDialogFragment(){
        AlertDialogFragmentBuilder()
            .withErrorIcon()
            .withMessage("DialogFragment")
            .withPositiveButton("Ok")
            .withNegativeButton("Cancel")
            .withNeutralButton("Unknown")
            .show(this, 5)
    }
}


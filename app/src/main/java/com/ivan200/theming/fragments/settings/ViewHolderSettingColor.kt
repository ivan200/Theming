package com.ivan200.theming.fragments.settings

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ivan200.theming.MainActivity
import com.ivan200.theming.R
import com.ivan200.theming.Theming
import com.ivan200.theming.showIf
import com.ivan200.theminglib.ThemeUtils

//
// Created by Ivan200 on 25.02.2020.
//

class ViewHolderSettingColor(
    view: View,
    val mainActivity: MainActivity
) : RecyclerView.ViewHolder(view), AdapterSettings.PrefBinder, View.OnClickListener {
    companion object {
        val layoutId: Int = R.layout.cell_setting_color
    }

    val title: TextView get() = itemView.findViewById(R.id.tv_title)
    val subTitle: TextView get() = itemView.findViewById(R.id.tv_subtitle)
    val colorView: View get() = itemView.findViewById(R.id.v_color)
    val colorViewBorder: FrameLayout get() = itemView.findViewById(R.id.v_color_border)
    val btnClear: ImageButton get() = itemView.findViewById(R.id.btn_clear)

    private lateinit var _setting: ColorSetting

    init {
        itemView.setOnClickListener(this)
        btnClear.setOnClickListener(this::onBtnClearClick)
    }

    fun colorize() {
        Theming.themeCellBg(itemView)
        Theming.themeViews(title)
        Theming.themeTextViewSecondary(subTitle)
        Theming.themeIcon(btnClear)
    }

    override fun bind(setting: Setting) {
        if (setting !is ColorSetting) return
        _setting = setting
        colorize()
        title.text = setting.title
        subTitle.showIf { setting.subTitle.isNotEmpty() }
        subTitle.text = setting.subTitle
        btnClear.showIf { !setting.useDefault }
        colorView.setBackgroundColor(setting.anyValue)
        colorViewBorder.setBackgroundColor(ThemeUtils.invertColorBrightness(setting.anyValue))
    }

    fun onBtnClearClick(v: View) {
        mainActivity.clearColorSetting(_setting)
    }

    override fun onClick(v: View?) {
        mainActivity.changeColorSetting(_setting)
    }
}

package com.ivan200.theming.fragments.settings

import android.view.View
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ivan200.theming.MainActivity
import com.ivan200.theming.R
import com.ivan200.theming.Theming
import com.ivan200.theming.showIf

//
// Created by Ivan200 on 25.02.2020.
//

class ViewHolderSettingCheckBox (
    view: View,
    val activity: MainActivity
) : RecyclerView.ViewHolder(view), AdapterSettings.PrefBinder,  View.OnClickListener {
    companion object {
        val layoutId: Int = R.layout.cell_setting_checkbox
    }

    val title : TextView get() = itemView.findViewById(R.id.tv_title)
    val subTitle : TextView get() = itemView.findViewById(R.id.tv_subtitle)
    val cbSetting: CheckBox get() = itemView.findViewById(R.id.cb_setting)
    val btnClear: ImageButton get() = itemView.findViewById(R.id.btn_clear)

    private lateinit var _setting: CheckSetting

    init {
        itemView.setOnClickListener(this)
        btnClear.setOnClickListener(this::onBtnClearClick)
    }

    // Обычно применение цветов достаточно в блоке init,
    // но так как это список цветов, и на этом экране можно их менять,
    // приходится применять цвета на каждый bind
    fun colorize(){
        Theming.themeCellBg(itemView)
        Theming.themeViews(title, cbSetting)
        Theming.themeIcon(btnClear)
        Theming.themeTextViewSecondary(subTitle)
    }

    override fun bind(setting: Setting) {
        if (setting !is CheckSetting) return
        colorize()
        _setting = setting
        title.text = setting.title

        subTitle.showIf { setting.subTitle.isNotEmpty() }
        subTitle.text = setting.subTitle
        btnClear.showIf { !setting.useDefault }
        cbSetting.isChecked = setting.anyValue
        cbSetting.isEnabled = setting.useDefault
    }

    fun onBtnClearClick(v: View){
        activity.clearFlagSetting(_setting)
    }

    override fun onClick(v: View?) {
        activity.changeFlagSetting(_setting)
//        activity.showSimpleDialog()
    }
}
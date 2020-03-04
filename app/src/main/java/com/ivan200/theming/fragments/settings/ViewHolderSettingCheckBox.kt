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

    val btnClear: ImageButton get() = itemView.findViewById(R.id.btn_clear)
    val cbSetting: CheckBox get() = itemView.findViewById(R.id.cb_setting)

    private lateinit var _setting: CheckSetting

    init {
        itemView.setOnClickListener(this)
        btnClear.setOnClickListener(this::onBtnClearClick)
    }

    fun colorize(){
        Theming.themeCellBg(itemView)
        Theming.themeIcon(btnClear)
        Theming.themeTextViewSecondary(subTitle)
        Theming.themeViews(title, cbSetting)
    }

    override fun bind(setting: Setting) {
        if (setting !is CheckSetting) return
        _setting = setting
        colorize()

        title.text = setting.title
        subTitle.showIf { setting.subTitle.isNotEmpty() }
        subTitle.text = setting.subTitle
        btnClear.showIf { !setting.useDefault }

        cbSetting.isChecked = _setting.anyValue
        cbSetting.isEnabled = _setting.useDefault
    }

    fun onBtnClearClick(v: View){
        activity.clearFlagSetting(_setting)
    }

    override fun onClick(v: View?) {
        activity.changeFlagSetting(_setting)
    }
}
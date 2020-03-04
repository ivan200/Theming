package com.ivan200.theming.fragments.settings

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ivan200.theming.R
import com.ivan200.theming.Theming

//
// Created by Ivan200 on 25.02.2020.
//

class ViewHolderSettingHeader (view: View) : RecyclerView.ViewHolder(view), AdapterSettings.PrefBinder {
    companion object {
        val layoutId: Int = R.layout.cell_setting_header
    }

    val title : TextView get() = itemView.findViewById(R.id.tv_title)
    val divider : View get() = itemView.findViewById(R.id.v_divider)

    fun colorize(){
        Theming.themeViewBack(itemView)
        Theming.themeTextViewSecondary(title)
        Theming.themeDivider(divider)
    }

    override fun bind(setting: Setting) {
        if(setting !is HeaderSetting) return
        colorize()
        title.text = setting.title
    }
}
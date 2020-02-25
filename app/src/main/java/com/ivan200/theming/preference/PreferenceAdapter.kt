package com.ivan200.theming.preference

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ivan200.theming.R
import com.ivan200.theming.Theming
import com.ivan200.theming.showIf

//
// Created by Ivan200 on 21.02.2020.
//

class PreferenceAdapter (val activity: Activity, val list: List<Setting> ) : RecyclerView.Adapter<PreferenceAdapter.PreferenceViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreferenceViewHolder {
        return PreferenceViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.cell_setting, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PreferenceViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class PreferenceViewHolder (view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val title : TextView get() = itemView.findViewById(R.id.tv_title)
        val subTitle : TextView get() = itemView.findViewById(R.id.tv_subtitle)
        val colorView : View get() = itemView.findViewById(R.id.v_color)
        val llCheckBox: FrameLayout get() = itemView.findViewById(R.id.fl_checkbox)
        val cbSetting: CheckBox get() = itemView.findViewById(R.id.cb_setting)
        val btnClear: ImageButton get() = itemView.findViewById(R.id.btn_clear)

        private lateinit var _setting: Setting

        init {
            itemView.setOnClickListener(this)
            btnClear.setOnClickListener(this::onBtnClearClick)

            Theming.themeCellBg(itemView)
            Theming.themeViews(title, cbSetting)
            Theming.themeIcon(btnClear)
            Theming.themeTextView(subTitle, isSecondary = true)
        }

        fun bind(setting: Setting) {
            _setting = setting
            title.text = setting.title

            subTitle.showIf { setting.subtitle.isNotEmpty() }
            subTitle.text = setting.subtitle

            var value = setting.value
            val useDefault = value < 0
            if(useDefault){
                value = setting.defaultValue
            }

            btnClear.showIf { !useDefault }
            colorView.showIf { setting is ColorPref }
            llCheckBox.showIf { setting is CheckPref }

            when (setting) {
                is ColorPref -> {
                    colorView.setBackgroundColor(value)
                }
                is CheckPref -> {
                    when(value){
                        0 -> cbSetting.isChecked = false
                        1 -> cbSetting.isChecked = true
                    }
                    cbSetting.isEnabled = useDefault
                }
            }
        }

        fun onBtnClearClick(v: View){
            _setting.value = -1
            bind(_setting)
        }

        override fun onClick(v: View?) {


        }
    }
}
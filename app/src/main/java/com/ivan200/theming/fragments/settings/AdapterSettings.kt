package com.ivan200.theming.fragments.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ivan200.theming.MainActivity

//
// Created by Ivan200 on 21.02.2020.
//

class AdapterSettings(
    val activity: MainActivity,
    val list: List<Setting>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface PrefBinder {
        fun bind(setting: Setting)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is ColorSetting -> ViewHolderSettingColor.layoutId
            is CheckSetting -> ViewHolderSettingCheckBox.layoutId
            is HeaderSetting -> ViewHolderSettingHeader.layoutId
            else -> throw NotImplementedError()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)

        return when (viewType) {
            ViewHolderSettingColor.layoutId -> ViewHolderSettingColor(view, activity)
            ViewHolderSettingCheckBox.layoutId -> ViewHolderSettingCheckBox(view, activity)
            ViewHolderSettingHeader.layoutId -> ViewHolderSettingHeader(view)
            else -> throw NotImplementedError()
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? PrefBinder)?.bind(list[position])
    }
}
package com.ivan200.theming.preference

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ivan200.theming.MainActivity

//
// Created by Ivan200 on 21.02.2020.
//

class PreferenceAdapter(
    val activity: MainActivity,
    val list: List<Setting>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface PrefBinder {
        fun bind(setting: Setting)
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is ColorPref -> ViewHolderPrefColor.layoutId
            is CheckPref -> ViewHolderPrefCheckBox.layoutId
            else -> ViewHolderPrefHeader.layoutId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)

        return when (viewType) {
            ViewHolderPrefColor.layoutId -> ViewHolderPrefColor(view, activity)
            ViewHolderPrefCheckBox.layoutId -> ViewHolderPrefCheckBox(view, activity)
            else -> ViewHolderPrefHeader(view)
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? PrefBinder)?.bind(list[position])
    }
}
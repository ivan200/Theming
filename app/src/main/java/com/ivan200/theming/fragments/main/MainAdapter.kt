package com.ivan200.theming.fragments.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ivan200.theming.R
import com.ivan200.theming.Theming

class MainCellInfo(val title: String, val onClick: View.OnClickListener)

class MainAdapter(private val list: List<MainCellInfo>) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
        MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.cell_main_list, parent, false)
        )

    override fun getItemCount(): Int = list.size
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) =
        holder.bind(list[position])

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView get() = itemView.findViewById(R.id.tv_title)
        private lateinit var _mainCellInfo: MainCellInfo

        init {
            Theming.themeCellBg(itemView)
            Theming.themeView(title)
            itemView.setOnClickListener {
                _mainCellInfo.onClick.onClick(it)
            }
        }

        fun bind(mainCellInfo: MainCellInfo) {
            _mainCellInfo = mainCellInfo
            title.text = mainCellInfo.title
        }
    }
}
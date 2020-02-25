package com.ivan200.theming.preference

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ivan200.theming.BaseFragment
import com.ivan200.theming.R
import com.ivan200.theming.Theming

//
// Created by Ivan200 on 25.02.2020.
//

class FragmentPreferences : BaseFragment(R.layout.fragment_prefs) {
    val recyclerView get() = requireView().findViewById<RecyclerView>(R.id.rv_prefs)
    val adapter by lazy { PreferenceAdapter(requireActivity(), getPrefsList()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        mActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Theming.themeViewAndSubviews(view)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    fun getPrefsList(): List<Setting> {
        val c = requireContext()
        return listOf(
            COLOR_PRIMARY(c),
            COLOR_BACKGROUND(c),
            NAVBAR_DRAW_SYSTEM_BAR(c)
        )
    }

}
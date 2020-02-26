package com.ivan200.theming.settings

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ivan200.theming.BaseFragment
import com.ivan200.theming.R
import com.ivan200.theming.Theming
import com.ivan200.theming.showIf

//
// Created by Ivan200 on 25.02.2020.
//

class FragmentSettings : BaseFragment(R.layout.fragment_prefs) {
    val recyclerView get() = requireView().findViewById<RecyclerView>(R.id.rv_prefs)
    val fab get() = requireView().findViewById<FloatingActionButton>(R.id.fab)
    val adapter by lazy { AdapterSettings(mActivity, getPrefsList()) }

    var hasColorChanges = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        mActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Theming.themeViewAndSubviews(view)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        fab.showIf { hasColorChanges }
        fab.setOnClickListener {
            hasColorChanges = false
            mActivity.recreate()
        }

        mActivity.anyColorChangeListener = this::onAnyPrefChanged
    }

    fun onAnyPrefChanged(){
        hasColorChanges = true
        if(view != null) {
            fab.show()

            adapter.notifyDataSetChanged()
        }
    }

    fun getPrefsList(): List<Setting> {
        val c = requireContext()
        return listOf(
            SECTION_DEFAULTS(c),
            COLOR_PRIMARY(c),
            COLOR_BACKGROUND(c),
            NAVBAR_DRAW_SYSTEM_BAR(c)
        )
    }
}
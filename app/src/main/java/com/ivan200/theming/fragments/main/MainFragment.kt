package com.ivan200.theming.fragments.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ivan200.theming.*
import com.ivan200.theming.fragments.BaseFragment


//
// Created by Ivan200 on 14.02.2020.
//

class MainFragment : BaseFragment(R.layout.fragment_main), Toolbar.OnMenuItemClickListener {
    private val navigateLogin = Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_loginFragment, Bundle())
    private val navigateProgress = Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_fragmentProgress, Bundle())
    private val navigatePrefs = Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_fragmentPreferences, Bundle())
    private val navigateButtons = Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_fragmentButtons, Bundle())

    val recyclerView get() = requireView().findViewById<RecyclerView>(R.id.rv_main)
    val adapter by lazy {
        MainAdapter(
            getMainCellList()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        toolbar?.setOnMenuItemClickListener(this)
        Theming.themeViews(toolbar!!, view)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    fun getMainCellList() : List<MainCellInfo>{
        return listOf(
            MainCellInfo(
                "ProgressBars",
                navigateProgress
            ),
            MainCellInfo(
                "Buttons",
                navigateButtons
            ),
            MainCellInfo(
                "Inputs",
                navigateLogin
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (this.javaClass.name == (findNavController().currentDestination as FragmentNavigator.Destination).className) {
            menu.clear()
            inflater.inflate(R.menu.view_main_actions, menu)
            Theming.themeAllMenuIcons(menu)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.main_icon_settings) {
            navigatePrefs.onClick(requireView())
        }
        return true
    }

}
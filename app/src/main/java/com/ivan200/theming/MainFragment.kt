package com.ivan200.theming

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController


//
// Created by Ivan200 on 14.02.2020.
//

class MainFragment : BaseFragment(R.layout.fragment_main), Toolbar.OnMenuItemClickListener {
    private val navigateLogin = Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_loginFragment, Bundle())
    private val navigateProgress = Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_fragmentProgress, Bundle())
    private val navigatePrefs = Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_fragmentPreferences, Bundle())

    val button1 get() = requireView().findViewById<Button>(R.id.button1)
    val button2 get() = requireView().findViewById<Button>(R.id.button2)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        toolbar?.setOnMenuItemClickListener(this)


        Theming.themeViews(button1, toolbar!!, view)

        button1.setOnClickListener {
            navigateProgress.onClick(it)

//            ColorPickerDialog.newBuilder()
//                .setAllowPresets(false)
//                .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
//                .setColor(Color.BLACK)
//                .show(activity)

        }
        button2.setOnClickListener {
//            mActivity.launchActivity<SecondActivity>()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (this.javaClass.name == (findNavController().currentDestination as FragmentNavigator.Destination).className) {
            menu.clear()
            inflater.inflate(R.menu.view_main_actions, menu)
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
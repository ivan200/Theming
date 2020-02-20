package com.ivan200.theming

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.navigation.Navigation


//
// Created by Ivan200 on 14.02.2020.
//

class MainFragment : BaseFragment(R.layout.fragment_main){
    private val navigateLogin = Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_loginFragment, Bundle())
    private val navigateProgress = Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_fragmentProgress, Bundle())

    val button1 get() = requireView().findViewById<Button>(R.id.button1)
    val button2 get() = requireView().findViewById<Button>(R.id.button2)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

}
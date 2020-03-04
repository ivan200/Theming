package com.ivan200.theming.fragments.buttons

import android.os.Bundle
import android.view.View
import com.ivan200.theming.R
import com.ivan200.theming.Theming
import com.ivan200.theming.fragments.BaseFragment

//
// Created by Ivan200 on 14.02.2020.
//

class FragmentButtons : BaseFragment(R.layout.fragment_buttons) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setbackButton()
        Theming.themeViewAndSubviews(view)
    }
}
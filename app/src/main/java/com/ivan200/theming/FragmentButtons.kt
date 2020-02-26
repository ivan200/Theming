package com.ivan200.theming

import android.os.Bundle
import android.view.View

//
// Created by Ivan200 on 14.02.2020.
//

class FragmentButtons : BaseFragment(R.layout.fragment_buttons) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        mActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Theming.themeViewAndSubviews(view)
    }
}
package com.ivan200.theming.fragments

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.ivan200.theming.MainActivity
import com.ivan200.theming.R

//
// Created by Ivan200 on 18.02.2020.
//

abstract class BaseFragment(@LayoutRes val contentLayoutId: Int) : Fragment(contentLayoutId){
    val mActivity get() = activity as MainActivity
    val title  = this::class.java.simpleName
    var toolbar: Toolbar? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(requireView())
    }

    fun setupToolbar(view: View){
        view.findViewById<Toolbar>(R.id.toolbar)?.apply {
            toolbar = this
            mActivity.setSupportActionBar(this)
            mActivity.title = this@BaseFragment.title
            setNavigationOnClickListener { mActivity.onBackPressed() }
        }
    }
}
package com.ivan200.theming.fragments

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.ivan200.theming.MainActivity
import com.ivan200.theming.R
import com.ivan200.theminglib.ThemeUtils

//
// Created by Ivan200 on 18.02.2020.
//

abstract class BaseFragment(@LayoutRes val contentLayoutId: Int) : Fragment(contentLayoutId) {
    val mActivity get() = activity as MainActivity
    val title = this::class.java.simpleName
    var toolbar: Toolbar? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(requireView())
    }

    fun setupToolbar(view: View) {
        view.findViewById<Toolbar>(R.id.toolbar)?.apply {
            toolbar = this
            mActivity.title = this@BaseFragment.title
            if ((mActivity as Activity) is AppCompatActivity) {
                (mActivity as AppCompatActivity).setSupportActionBar(this)
            } else {
                toolbar?.title = this@BaseFragment.title
            }
            setNavigationOnClickListener { mActivity.onBackPressed() }
        }
    }

    fun setbackButton() {
        setHasOptionsMenu(true)
        if ((mActivity as Activity) is AppCompatActivity) {
            (mActivity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            toolbar?.navigationIcon = ThemeUtils.getDrawableResCompat(requireContext(), android.R.attr.homeAsUpIndicator)
            toolbar?.setNavigationOnClickListener { mActivity.onBackPressed() }
        }
    }
}

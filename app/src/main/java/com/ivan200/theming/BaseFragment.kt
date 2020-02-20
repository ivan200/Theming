package com.ivan200.theming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

//
// Created by Ivan200 on 18.02.2020.
//

abstract class BaseFragment(@LayoutRes val contentLayoutId: Int) : Fragment(contentLayoutId){
    val mActivity get() = activity as MainActivity
    val title  = this::class.java.simpleName
    var toolbar: Toolbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        setupToolbar(view!!)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Theming.themeViewAndSubviews(view)
    }

    fun setupToolbar(view: View){
        view.findViewById<Toolbar>(R.id.toolbar)?.apply {
            toolbar = this
            mActivity.setSupportActionBar(this)
            mActivity.title = title
            setNavigationOnClickListener { mActivity.onBackPressed() }
        }
    }
}
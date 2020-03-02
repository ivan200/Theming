package com.ivan200.theming.fragments.settings

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ivan200.theming.R
import com.ivan200.theming.Theming
import com.ivan200.theming.fragments.BaseFragment
import com.ivan200.theming.showIf
import com.ivan200.theminglib.ThemeColor
import com.ivan200.theminglib.ThemeFlag

//
// Created by Ivan200 on 25.02.2020.
//

class FragmentSettings : BaseFragment(R.layout.fragment_prefs) {
    val recyclerView get() = requireView().findViewById<RecyclerView>(R.id.rv_prefs)
    val fab get() = requireView().findViewById<FloatingActionButton>(R.id.fab)
    val adapter by lazy { AdapterSettings(mActivity, getPrefsList()) }

    var hasColorChanges = false
    var layoutManager: LinearLayoutManager? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        mActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Theming.themeViewAndSubviews(view)

        layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        fab.showIf { hasColorChanges }
        fab.setOnClickListener {
            hasColorChanges = false
            mActivity.recreate()
        }

        mActivity.anySettingChangeListener = this::onAnyPrefChanged

        savedInstanceState?.apply {
            val positionIndex = this.getInt("positionIndex", -1)
            val offset = this.getInt("offset", 0)
            if (positionIndex != -1) {
                layoutManager?.scrollToPositionWithOffset(positionIndex, offset)
            }
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        if(view != null && layoutManager != null) {
            outState.putInt("positionIndex", layoutManager!!.findFirstVisibleItemPosition())
            outState.putInt("offset", recyclerView.getChildAt(0)?.let { it.top - recyclerView.paddingTop } ?: 0)
        }
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
            HeaderSetting(mActivity, "Основные цвета"),

            ColorSetting(mActivity, ThemeColor.colorPrimary, "Основной цвет подсветки"),
            ColorSetting(mActivity, ThemeColor.colorBackground, "Цвет фона"),

            ColorSetting(mActivity, ThemeColor.colorFine, ""),
            ColorSetting(mActivity, ThemeColor.colorError, ""),

            HeaderSetting(mActivity, "Системные компоненты"),
            ColorSetting(mActivity, ThemeColor.colorWindowBackground, ""),
            ColorSetting(mActivity, ThemeColor.colorStatusBar, "", true),
            ColorSetting(mActivity, ThemeColor.colorStatusBarDark, "", true),
            CheckSetting(mActivity, ThemeFlag.statusDrawSystemBar, ""),
            CheckSetting(mActivity, ThemeFlag.statusLightTheme, ""),

            ColorSetting(mActivity, ThemeColor.colorNavBar, "", true),
            ColorSetting(mActivity, ThemeColor.colorNavBarDark, ""),
            CheckSetting(mActivity, ThemeFlag.navBarDrawSystemBar, ""),
            CheckSetting(mActivity, ThemeFlag.navBarLightTheme, ""),
            ColorSetting(mActivity, ThemeColor.colorNavBarDivider, ""),

            ColorSetting(mActivity, ThemeColor.colorEdgeGlow, ""),
            ColorSetting(mActivity, ThemeColor.colorEdgeGlowTop, ""),
            ColorSetting(mActivity, ThemeColor.colorEdgeGlowBottom, ""),
            ColorSetting(mActivity, ThemeColor.colorEdgeGlowLeft, ""),
            ColorSetting(mActivity, ThemeColor.colorEdgeGlowRight, ""),

            HeaderSetting(mActivity, "Текст"),
            ColorSetting(mActivity, ThemeColor.colorText, ""),
            ColorSetting(mActivity, ThemeColor.colorTextHint, "", true),

            ColorSetting(mActivity, ThemeColor.colorDivider, "", true),

            HeaderSetting(mActivity, "Экшенбар"),
            ColorSetting(mActivity, ThemeColor.colorActionBar, ""),
            ColorSetting(mActivity, ThemeColor.colorActionBarText, ""),
            ColorSetting(mActivity, ThemeColor.colorActionBarTextSecondary, ""),
            ColorSetting(mActivity, ThemeColor.colorActionBarIcons, ""),

            HeaderSetting(mActivity, "Поля ввода"),
            ColorSetting(mActivity, ThemeColor.colorInputPasswordEye, ""),
            ColorSetting(mActivity, ThemeColor.colorInputHelper, ""),
            ColorSetting(mActivity, ThemeColor.colorInputError, ""),
            ColorSetting(mActivity, ThemeColor.colorInputHintFocused, ""),
            ColorSetting(mActivity, ThemeColor.colorInputHint, ""),
            ColorSetting(mActivity, ThemeColor.colorInputText, ""),
            ColorSetting(mActivity, ThemeColor.colorInputBottomLine, ""),
            ColorSetting(mActivity, ThemeColor.colorInputCursor, ""),
            ColorSetting(mActivity, ThemeColor.colorInputHighlight, ""),
            ColorSetting(mActivity, ThemeColor.colorInputHandles, ""),

            HeaderSetting(mActivity, "Прогресс"),
            ColorSetting(mActivity, ThemeColor.colorProgressBar, ""),
            ColorSetting(mActivity, ThemeColor.colorProgressBarSecondary, ""),
            ColorSetting(mActivity, ThemeColor.colorProgressBarBackground, ""),
            ColorSetting(mActivity, ThemeColor.colorSeekBarThumb, ""),
            ColorSetting(mActivity, ThemeColor.colorSeekBarTickMark, ""),

            HeaderSetting(mActivity, "Кнопки"),
            ColorSetting(mActivity, ThemeColor.colorButtonBackground, ""),
            ColorSetting(mActivity, ThemeColor.colorButtonText, ""),

            HeaderSetting(mActivity, "Чекбокс"),
            ColorSetting(mActivity, ThemeColor.colorCheckBoxActive, ""),
            ColorSetting(mActivity, ThemeColor.colorCheckBoxInactive, ""),

            HeaderSetting(mActivity, "Радиокнопки"),
            ColorSetting(mActivity, ThemeColor.colorRadioActive, ""),
            ColorSetting(mActivity, ThemeColor.colorRadioInactive, ""),

            HeaderSetting(mActivity, "Переключатель"),
            ColorSetting(mActivity, ThemeColor.colorSwitchActive, ""),
            ColorSetting(mActivity, ThemeColor.colorSwitchInactive, ""),

            HeaderSetting(mActivity, "Круглая кнопка"),
            ColorSetting(mActivity, ThemeColor.colorFabBackground, ""),
            ColorSetting(mActivity, ThemeColor.colorFabIcon, ""),

            HeaderSetting(mActivity, "Диалоги"),
            ColorSetting(mActivity, ThemeColor.colorAlertBackground, ""),
            ColorSetting(mActivity, ThemeColor.colorAlertTitle, ""),
            ColorSetting(mActivity, ThemeColor.colorAlertIcon, ""),
            ColorSetting(mActivity, ThemeColor.colorAlertMessage, ""),
            ColorSetting(mActivity, ThemeColor.colorAlertButtons, ""),

            HeaderSetting(mActivity, "Иконки"),
            ColorSetting(mActivity, ThemeColor.colorIcon, ""),

            HeaderSetting(mActivity, "Нижняя панель"),
            ColorSetting(mActivity, ThemeColor.colorBottomNavBackground, ""),
            ColorSetting(mActivity, ThemeColor.colorBottomNavIcon, ""),
            ColorSetting(mActivity, ThemeColor.colorBottomNavIconSelected, ""),
            ColorSetting(mActivity, ThemeColor.colorBottomNavText, ""),
            ColorSetting(mActivity, ThemeColor.colorBottomNavTextSelected, "")

        )
    }
}
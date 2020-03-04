package com.ivan200.theming.fragments.settings

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ivan200.theming.MainViewModel
import com.ivan200.theming.R
import com.ivan200.theming.Theming
import com.ivan200.theming.fragments.BaseFragment
import com.ivan200.theming.utils.Event
import com.ivan200.theminglib.ThemeColor
import com.ivan200.theminglib.ThemeFlag

//
// Created by Ivan200 on 25.02.2020.
//

class FragmentSettings : BaseFragment(R.layout.fragment_prefs) {
    val recyclerView get() = requireView().findViewById<RecyclerView>(R.id.rv_prefs)
    val fab get() = requireView().findViewById<FloatingActionButton>(R.id.fab)
    val adapter by lazy { AdapterSettings(mActivity, getPrefsList()) }

    var layoutManager: LinearLayoutManager? = null

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setbackButton()
        Theming.themeViewAndSubviews(view)

        layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        fab.setOnClickListener {
            fab.hide()
            mActivity.recreate()
        }

        mainViewModel.apply {
            settingChanged.observe(viewLifecycleOwner, settingChangedObserver)
            if (currentPrefsScrollPos != -1) {
                layoutManager?.scrollToPositionWithOffset(currentPrefsScrollPos, currentPrefsScrollOffset)
            }
        }
    }

    var settingChangedObserver = Observer<Event<Unit>> {
        it?.get()?.apply {
            fab.show()
            adapter.notifyDataSetChanged()
        }
    }

    override fun onStop() {
        mainViewModel.currentPrefsScrollPos = layoutManager!!.findFirstVisibleItemPosition()
        mainViewModel.currentPrefsScrollOffset = recyclerView.getChildAt(0)?.let { it.top - recyclerView.paddingTop } ?: 0
        super.onStop()
    }

    fun getPrefsList(): List<Setting> {
        val c = requireContext()
        return listOf(
            HeaderSetting(mActivity, "Основные цвета"),

            ColorSetting(mActivity, ThemeColor.colorPrimary, "Основной цвет подсветки"),
            ColorSetting(mActivity, ThemeColor.colorBackground, "Цвет фона"),

            ColorSetting(mActivity, ThemeColor.colorFine, "Цвет хорошего результата"),
            ColorSetting(mActivity, ThemeColor.colorError, "Цвет плохого результата"),

            HeaderSetting(mActivity, "Системные компоненты"),
            ColorSetting(mActivity, ThemeColor.colorWindowBackground, "Цвет фона окна, на случай если статусбар прозрачный"),
            ColorSetting(mActivity, ThemeColor.colorStatusBar, "Цвет статусбара", true),
            ColorSetting(mActivity, ThemeColor.colorStatusBarDark, "Цвет статусбара на апи 21-22 (Android 5), где текст на статусбаре всегда белый", true),
            CheckSetting(mActivity, ThemeFlag.statusDrawSystemBar, "Отрисовывать ли системную плашку под статусбаром если его цвет прозрачный"),
            CheckSetting(mActivity, ThemeFlag.statusLightTheme, "Скзать системе что статусбар светлый, чтобы значки стали чёрного цвета (поддерживается на на апи 23+ (Android 6+)"),

            ColorSetting(mActivity, ThemeColor.colorNavBar, "Цвет панели навигации", true),
            ColorSetting(mActivity, ThemeColor.colorNavBarDark, "Цвет панели навигации на апи 21-25 (Android 5-7), где кнопки всегда белые"),
            CheckSetting(mActivity, ThemeFlag.navBarDrawSystemBar, "Отрисовывать ли системную плашку под панелью навигации, если она прозрачная"),
            CheckSetting(mActivity, ThemeFlag.navBarLightTheme, "Скзать системе что панель навигации светлая, чтобы значки стали чёрного цвета (поддерживается на на апи 26+ (Android 8+)"),
            ColorSetting(mActivity, ThemeColor.colorNavBarDivider, "Цвет разделителя панели навигаци, включается на апи 28+ (Android 9+)"),

            ColorSetting(mActivity, ThemeColor.colorEdgeGlow, "Цвет подсветки RecyclerView и ListView при скролле. От него наследуются цвета разных сторон подсветки, и также используется на апи<21 (Android <5) для всех строн RecyclerView"),
            ColorSetting(mActivity, ThemeColor.colorEdgeGlowTop, "Цвет подсветки верхней границы ScrollView, ListView и RecyclerView"),
            ColorSetting(mActivity, ThemeColor.colorEdgeGlowBottom, "Цвет подсветки нижней границы ScrollView, ListView и RecyclerView"),
            ColorSetting(mActivity, ThemeColor.colorEdgeGlowLeft, "Цвет подсветки левой границы HorizontalScrollView, ViewPager и RecyclerView"),
            ColorSetting(mActivity, ThemeColor.colorEdgeGlowRight, "Цвет подсветки правой границы HorizontalScrollView, ViewPager и RecyclerView"),

            HeaderSetting(mActivity, "Текст"),
            ColorSetting(mActivity, ThemeColor.colorText, "Цвет текста"),
            ColorSetting(mActivity, ThemeColor.colorTextHint, "Цвет хинта текста", true),

            ColorSetting(mActivity, ThemeColor.colorDivider, "Цвет разделителя в ячейках", true),

            HeaderSetting(mActivity, "Панель действий"),
            ColorSetting(mActivity, ThemeColor.colorActionBar, "Цвет верхнего экшенбара", true),
            ColorSetting(mActivity, ThemeColor.colorActionBarText, "Цвет текста верхнего экшенбара"),
            ColorSetting(mActivity, ThemeColor.colorActionBarTextSecondary, "Цвет второго текста верхнего экшенбара"),
            ColorSetting(mActivity, ThemeColor.colorActionBarIcons, "Цвет иконок верхнего экшенбара"),

            HeaderSetting(mActivity, "Панель навигации"),
            ColorSetting(mActivity, ThemeColor.colorBottomNavBackground, "Цвет фона нижней панели"),
            ColorSetting(mActivity, ThemeColor.colorBottomNavIcon, "Цвет иконок нижней панели"),
            ColorSetting(mActivity, ThemeColor.colorBottomNavIconSelected, "Цвет иконки выбранной вкладки нижней панели"),
            ColorSetting(mActivity, ThemeColor.colorBottomNavText, "Цвет текста нижней панели"),
            ColorSetting(mActivity, ThemeColor.colorBottomNavTextSelected, "Цвет текста выбранной вкладки нижней панели"),

            HeaderSetting(mActivity, "Поля ввода"),
            ColorSetting(mActivity, ThemeColor.colorInputPasswordEye, "Цвет иконки глаза скрывающей пароль"),
            ColorSetting(mActivity, ThemeColor.colorInputHelper, "Цвет подсвечиваемого помощника"),
            ColorSetting(mActivity, ThemeColor.colorInputError, "Цвет подсвечиваемой ошибки"),
            ColorSetting(mActivity, ThemeColor.colorInputHintFocused, "Цвет верхней надписи при активации данного поля ввода"),
            ColorSetting(mActivity, ThemeColor.colorInputHint, "Цвет хинта вводимого текста"),
            ColorSetting(mActivity, ThemeColor.colorInputText, "Цвет вводимого текста"),
            ColorSetting(mActivity, ThemeColor.colorInputBottomLine, "Цвет полоски под полем ввода"),
            ColorSetting(mActivity, ThemeColor.colorInputCursor, "Цвет моргающего курсора"),
            ColorSetting(mActivity, ThemeColor.colorInputHighlight, "Цвет выделения текста"),
            ColorSetting(mActivity, ThemeColor.colorInputHandles, "Цвет захватов при выделении  текста"),

            HeaderSetting(mActivity, "Прогресс"),
            ColorSetting(mActivity, ThemeColor.colorProgressBar, "Цвет прогрессбара"),
            ColorSetting(mActivity, ThemeColor.colorProgressBarSecondary, "Цвет второго прогрессбара (автоматически затеняется)"),
            ColorSetting(mActivity, ThemeColor.colorProgressBarBackground, "Цвет фона прогрессбара"),
            ColorSetting(mActivity, ThemeColor.colorSeekBarThumb, "Цвет ручки сикбара"),
            ColorSetting(mActivity, ThemeColor.colorSeekBarTickMark, "Цвет точек на сикбаре с делениями"),

            HeaderSetting(mActivity, "Кнопки"),
            ColorSetting(mActivity, ThemeColor.colorButtonBackground, "Цвет фона кнопки"),
            ColorSetting(mActivity, ThemeColor.colorButtonText, "Цвет текста кнопки"),

            HeaderSetting(mActivity, "Чекбокс"),
            ColorSetting(mActivity, ThemeColor.colorCheckBoxActive, "Цвет чекбокса"),
            ColorSetting(mActivity, ThemeColor.colorCheckBoxInactive, "Цвет неактивированного чекбокса"),

            HeaderSetting(mActivity, "Радиокнопки"),
            ColorSetting(mActivity, ThemeColor.colorRadioActive, "Цвет радиокнопки"),
            ColorSetting(mActivity, ThemeColor.colorRadioInactive, "Цвет неактивированной радиокнопки"),

            HeaderSetting(mActivity, "Переключатель"),
            ColorSetting(mActivity, ThemeColor.colorSwitchActive, "Цвет переключателя"),
            ColorSetting(mActivity, ThemeColor.colorSwitchInactive, "Цвет неактивированного переключателя"),

            HeaderSetting(mActivity, "Круглая кнопка"),
            ColorSetting(mActivity, ThemeColor.colorFabBackground, "Цвет фона круглой кнопки"),
            ColorSetting(mActivity, ThemeColor.colorFabIcon, "Цвет иконки на круглой кнопке"),

            HeaderSetting(mActivity, "Диалоги"),
            ColorSetting(mActivity, ThemeColor.colorAlertBackground, "Цвет фона диалога"),
            ColorSetting(mActivity, ThemeColor.colorAlertTitle, "Цвет заголовка диалога"),
            ColorSetting(mActivity, ThemeColor.colorAlertIcon, "Цвет иконки диалога"),
            ColorSetting(mActivity, ThemeColor.colorAlertMessage, "Цвет текста диалога"),
            ColorSetting(mActivity, ThemeColor.colorAlertButtons, "Цвет кнопок диалога"),
            ColorSetting(mActivity, ThemeColor.colorAlertButtonPositive, "Цвет позитивной кнопки далога"),
            ColorSetting(mActivity, ThemeColor.colorAlertButtonNegative, "Цвет негативной кнопки далога"),
            ColorSetting(mActivity, ThemeColor.colorAlertButtonNeutral, "Цвет нейтральной кнопки далога"),

            HeaderSetting(mActivity, "Иконки"),
            ColorSetting(mActivity, ThemeColor.colorIcon, "Цвет иконок")

        )
    }
}
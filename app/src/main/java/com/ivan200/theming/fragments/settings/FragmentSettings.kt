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

            ColorSetting(mActivity, ThemeColor.ColorPrimary, "Основной цвет подсветки"),
            ColorSetting(mActivity, ThemeColor.ColorBackground, "Цвет фона"),

            ColorSetting(mActivity, ThemeColor.ColorFine, "Цвет хорошего результата"),
            ColorSetting(mActivity, ThemeColor.ColorError, "Цвет плохого результата"),

            HeaderSetting(mActivity, "Системные компоненты"),
            ColorSetting(mActivity, ThemeColor.ColorWindowBackground, "Цвет фона окна, на случай если статусбар прозрачный"),
            ColorSetting(mActivity, ThemeColor.ColorStatusBar, "Цвет статусбара", true),
            ColorSetting(mActivity, ThemeColor.ColorStatusBarDark, "Цвет статусбара на апи 21-22 (Android 5), где текст на статусбаре всегда белый", true),
            CheckSetting(mActivity, ThemeFlag.StatusDrawSystemBar, "Отрисовывать ли системную плашку под статусбаром если его цвет прозрачный"),
            CheckSetting(mActivity, ThemeFlag.StatusLightTheme, "Скзать системе что статусбар светлый, чтобы значки стали чёрного цвета, поддерживается на на апи 23+ (Android 6+)"),

            ColorSetting(mActivity, ThemeColor.ColorNavBar, "Цвет панели навигации", true),
            ColorSetting(mActivity, ThemeColor.ColorNavBarDark, "Цвет панели навигации на апи 21-25 (Android 5-7), где кнопки всегда белые"),
            CheckSetting(mActivity, ThemeFlag.NavBarDrawSystemBar, "Отрисовывать ли системную плашку под панелью навигации, если она прозрачная"),
            CheckSetting(mActivity, ThemeFlag.NavBarLightTheme, "Скзать системе что панель навигации светлая, чтобы значки стали чёрного цвета, поддерживается на на апи 26+ (Android 8+)"),
            ColorSetting(mActivity, ThemeColor.ColorNavBarDivider, "Цвет разделителя панели навигаци, включается на апи 28+ (Android 9+)"),

            ColorSetting(mActivity, ThemeColor.ColorEdgeGlow, "Цвет подсветки RecyclerView и ListView при скролле. От него наследуются цвета разных сторон подсветки, и также используется на апи<21 (Android <5) для всех строн RecyclerView"),
            ColorSetting(mActivity, ThemeColor.ColorEdgeGlowTop, "Цвет подсветки верхней границы ScrollView, ListView и RecyclerView"),
            ColorSetting(mActivity, ThemeColor.ColorEdgeGlowBottom, "Цвет подсветки нижней границы ScrollView, ListView и RecyclerView"),
            ColorSetting(mActivity, ThemeColor.ColorEdgeGlowLeft, "Цвет подсветки левой границы HorizontalScrollView, ViewPager и RecyclerView"),
            ColorSetting(mActivity, ThemeColor.ColorEdgeGlowRight, "Цвет подсветки правой границы HorizontalScrollView, ViewPager и RecyclerView"),

            HeaderSetting(mActivity, "Текст"),
            ColorSetting(mActivity, ThemeColor.ColorText, "Цвет текста"),
            ColorSetting(mActivity, ThemeColor.ColorTextHint, "Цвет хинта текста", true),

            ColorSetting(mActivity, ThemeColor.ColorDivider, "Цвет разделителя в ячейках", true),

            HeaderSetting(mActivity, "Панель действий"),
            ColorSetting(mActivity, ThemeColor.ColorActionBar, "Цвет верхнего экшенбара", true),
            ColorSetting(mActivity, ThemeColor.ColorActionBarText, "Цвет текста верхнего экшенбара"),
            ColorSetting(mActivity, ThemeColor.ColorActionBarTextSecondary, "Цвет второго текста верхнего экшенбара"),
            ColorSetting(mActivity, ThemeColor.ColorActionBarIcons, "Цвет иконок верхнего экшенбара"),

            HeaderSetting(mActivity, "Панель навигации"),
            ColorSetting(mActivity, ThemeColor.ColorBottomNavBackground, "Цвет фона нижней панели"),
            ColorSetting(mActivity, ThemeColor.ColorBottomNavIcon, "Цвет иконок нижней панели"),
            ColorSetting(mActivity, ThemeColor.ColorBottomNavIconSelected, "Цвет иконки выбранной вкладки нижней панели"),
            ColorSetting(mActivity, ThemeColor.ColorBottomNavText, "Цвет текста нижней панели"),
            ColorSetting(mActivity, ThemeColor.ColorBottomNavTextSelected, "Цвет текста выбранной вкладки нижней панели"),

            HeaderSetting(mActivity, "Поля ввода"),
            ColorSetting(mActivity, ThemeColor.ColorInputPasswordEye, "Цвет иконки глаза скрывающей пароль"),
            ColorSetting(mActivity, ThemeColor.ColorInputHelper, "Цвет подсвечиваемого помощника"),
            ColorSetting(mActivity, ThemeColor.ColorInputError, "Цвет подсвечиваемой ошибки"),
            ColorSetting(mActivity, ThemeColor.ColorInputHintFocused, "Цвет верхней надписи при активации данного поля ввода"),
            ColorSetting(mActivity, ThemeColor.ColorInputHint, "Цвет хинта вводимого текста"),
            ColorSetting(mActivity, ThemeColor.ColorInputText, "Цвет вводимого текста"),
            ColorSetting(mActivity, ThemeColor.ColorInputBottomLine, "Цвет полоски под полем ввода"),
            ColorSetting(mActivity, ThemeColor.ColorInputCursor, "Цвет моргающего курсора"),
            ColorSetting(mActivity, ThemeColor.ColorInputHighlight, "Цвет выделения текста"),
            ColorSetting(mActivity, ThemeColor.ColorInputHandles, "Цвет захватов при выделении текста"),

            HeaderSetting(mActivity, "Прогресс"),
            ColorSetting(mActivity, ThemeColor.ColorProgressBar, "Цвет прогрессбара"),
            ColorSetting(mActivity, ThemeColor.ColorProgressBarSecondary, "Цвет второго прогрессбара (автоматически затеняется)"),
            ColorSetting(mActivity, ThemeColor.ColorProgressBarBackground, "Цвет фона прогрессбара"),
            ColorSetting(mActivity, ThemeColor.ColorSeekBarThumb, "Цвет ручки сикбара"),
            ColorSetting(mActivity, ThemeColor.ColorSeekBarTickMark, "Цвет точек на сикбаре с делениями"),

            HeaderSetting(mActivity, "Кнопки"),
            ColorSetting(mActivity, ThemeColor.ColorButtonBackground, "Цвет фона кнопки"),
            ColorSetting(mActivity, ThemeColor.ColorButtonText, "Цвет текста кнопки"),

            HeaderSetting(mActivity, "Чекбокс"),
            ColorSetting(mActivity, ThemeColor.ColorCheckBoxActive, "Цвет чекбокса"),
            ColorSetting(mActivity, ThemeColor.ColorCheckBoxInactive, "Цвет неактивированного чекбокса"),

            HeaderSetting(mActivity, "Радиокнопки"),
            ColorSetting(mActivity, ThemeColor.ColorRadioActive, "Цвет радиокнопки"),
            ColorSetting(mActivity, ThemeColor.ColorRadioInactive, "Цвет неактивированной радиокнопки"),

            HeaderSetting(mActivity, "Переключатель"),
            ColorSetting(mActivity, ThemeColor.ColorSwitchActive, "Цвет переключателя"),
            ColorSetting(mActivity, ThemeColor.ColorSwitchInactive, "Цвет неактивированного переключателя"),

            HeaderSetting(mActivity, "Круглая кнопка"),
            ColorSetting(mActivity, ThemeColor.ColorFabBackground, "Цвет фона круглой кнопки"),
            ColorSetting(mActivity, ThemeColor.ColorFabIcon, "Цвет иконки на круглой кнопке"),

            HeaderSetting(mActivity, "Диалоги"),
            ColorSetting(mActivity, ThemeColor.ColorAlertBackground, "Цвет фона диалога"),
            ColorSetting(mActivity, ThemeColor.ColorAlertTitle, "Цвет заголовка диалога"),
            ColorSetting(mActivity, ThemeColor.ColorAlertIcon, "Цвет иконки диалога"),
            ColorSetting(mActivity, ThemeColor.ColorAlertMessage, "Цвет текста диалога"),
            ColorSetting(mActivity, ThemeColor.ColorAlertButtons, "Цвет кнопок диалога"),
            ColorSetting(mActivity, ThemeColor.ColorAlertButtonPositive, "Цвет позитивной кнопки далога"),
            ColorSetting(mActivity, ThemeColor.ColorAlertButtonNegative, "Цвет негативной кнопки далога"),
            ColorSetting(mActivity, ThemeColor.ColorAlertButtonNeutral, "Цвет нейтральной кнопки далога"),

            HeaderSetting(mActivity, "Иконки"),
            ColorSetting(mActivity, ThemeColor.ColorIcon, "Цвет иконок")
        )
    }
}
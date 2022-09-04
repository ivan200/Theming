package com.ivan200.theminglib

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.InsetDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.RotateDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Build
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.AbsListView
import android.widget.AbsSeekBar
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.ScrollView
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.view.ViewCompat
import androidx.core.widget.CompoundButtonCompat
import androidx.core.widget.ImageViewCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.ivan200.theminglib.ThemeColor.*
import com.ivan200.theminglib.ThemeFlag.NavBarDrawSystemBar
import com.ivan200.theminglib.ThemeFlag.NavBarLightTheme
import com.ivan200.theminglib.ThemeFlag.StatusDrawSystemBar
import com.ivan200.theminglib.ThemeFlag.StatusLightTheme
import com.ivan200.theminglib.ThemeUtils.createStateDrawable
import com.ivan200.theminglib.ThemeUtils.dpToPx
import com.ivan200.theminglib.ThemeUtils.getFieldByName
import com.ivan200.theminglib.ThemeUtils.isColorBright
import com.ivan200.theminglib.ThemeUtils.setBackgroundColorSavePadding
import com.ivan200.theminglib.ThemeUtils.setBackgroundResourceSavePadding
import com.ivan200.theminglib.ThemeUtils.setBackgroundSavePadding
import com.ivan200.theminglib.ThemeUtils.setDecorFlag
import com.ivan200.theminglib.ThemeUtils.setWindowFlag
import com.ivan200.theminglib.ThemeUtils.spToPx
import com.ivan200.theminglib.ThemeUtils.tinted
import java.lang.reflect.Field
import java.util.Arrays
import java.util.EnumMap
import kotlin.math.sqrt

//
// Created by Ivan200 on 28.11.2019.
//
@Suppress("MemberVisibilityCanBePrivate")
abstract class ThemingBase {
    val mColors: EnumMap<ThemeColor, () -> Int> =
        EnumMap<ThemeColor, () -> Int>(ThemeColor::class.java).apply {
            put(ColorPrimary) { Color.parseColor("#54a9ff") }
            put(ColorBackground) { Color.parseColor("#303030") }
            put(ColorFine) { Color.parseColor("#4CAF50") }
            put(ColorError) { Color.parseColor("#D50000") }
            put(ColorWindowBackground) { ColorBackground.intColor }
            put(ColorStatusBar) { Color.BLACK }
            put(ColorStatusBarDark) { Color.BLACK }
            put(ColorNavBar) { Color.BLACK }
            put(ColorNavBarDark) { Color.BLACK }
            put(ColorNavBarDivider) { Color.TRANSPARENT }
            put(ColorEdgeGlow) { ColorPrimary.intColor }
            put(ColorEdgeGlowTop) { ColorEdgeGlow.intColor }
            put(ColorEdgeGlowBottom) { ColorEdgeGlow.intColor }
            put(ColorEdgeGlowLeft) { ColorEdgeGlow.intColor }
            put(ColorEdgeGlowRight) { ColorEdgeGlow.intColor }
            put(ColorText) { getTextColor(ColorBackground.intColor) }
            put(ColorTextHint) { getHintColor(ColorText.intColor) }
            put(ColorDivider) { getDividerColor(ColorText.intColor) }
            put(ColorInputPasswordEye) { ColorTextHint.intColor }
            put(ColorInputHelper) { ColorFine.intColor }
            put(ColorInputError) { ColorError.intColor }
            put(ColorInputHintFocused) { ColorPrimary.intColor }
            put(ColorInputHint) { ColorTextHint.intColor }
            put(ColorInputText) { ColorText.intColor }
            put(ColorInputBottomLine) { ColorPrimary.intColor }
            put(ColorInputCursor) { ColorPrimary.intColor }
            put(ColorInputHighlight) { ColorPrimary.intColor }
            put(ColorInputHandles) { ColorPrimary.intColor }
            put(ColorProgressBar) { ColorPrimary.intColor }
            put(ColorProgressBarSecondary) { ColorProgressBar.intColor }
            put(ColorProgressBarBackground) { getSecondaryTextColor(ColorText.intColor) }
            put(ColorSeekBarThumb) { ColorProgressBar.intColor }
            put(ColorSeekBarTickMark) { ColorProgressBarBackground.intColor }
            put(ColorButtonBackground) { ColorPrimary.intColor }
            put(ColorButtonText) { getTextColor(ColorButtonBackground.intColor) }
            put(ColorCheckBoxActive) { ColorPrimary.intColor }
            put(ColorCheckBoxInactive) { ColorTextHint.intColor }
            put(ColorRadioActive) { ColorPrimary.intColor }
            put(ColorRadioInactive) { ColorTextHint.intColor }
            put(ColorSwitchActive) { ColorPrimary.intColor }
            put(ColorSwitchInactive) { Color.parseColor(if (isLightTheme) "#ececec" else "#b9b9b9") }
            put(ColorFabBackground) { ColorPrimary.intColor }
            put(ColorFabIcon) { getTextColor(ColorFabBackground.intColor) }
            put(ColorAlertBackground) { ColorBackground.intColor }
            put(ColorAlertTitle) { ColorText.intColor }
            put(ColorAlertIcon) { ColorText.intColor }
            put(ColorAlertMessage) { ColorText.intColor }
            put(ColorAlertButtons) { ColorPrimary.intColor }
            put(ColorAlertButtonPositive) { ColorAlertButtons.intColor }
            put(ColorAlertButtonNegative) { ColorAlertButtons.intColor }
            put(ColorAlertButtonNeutral) { ColorAlertButtons.intColor }
            put(ColorIcon) { ColorText.intColor }
            put(ColorActionBar) { ColorPrimary.intColor }
            put(ColorActionBarText) { getTextColor(ColorActionBar.intColor) }
            put(ColorActionBarTextSecondary) { getSecondaryTextColor(ColorActionBarText.intColor) }
            put(ColorActionBarIcons) { ColorActionBarText.intColor }
            put(ColorBottomNavBackground) { ColorBackground.intColor }
            put(ColorBottomNavIcon) { getSecondaryTextColor(ColorText.intColor) }
            put(ColorBottomNavIconSelected) { ColorPrimary.intColor }
            put(ColorBottomNavText) { getSecondaryTextColor(ColorText.intColor) }
            put(ColorBottomNavTextSelected) { ColorPrimary.intColor }
        }

    val mFlags: EnumMap<ThemeFlag, () -> Boolean> =
        EnumMap<ThemeFlag, () -> Boolean>(ThemeFlag::class.java).apply {
            put(StatusDrawSystemBar) { true }
            put(StatusLightTheme) { isColorBright(ColorStatusBar.intColor) }
            put(NavBarDrawSystemBar) { true }
            put(NavBarLightTheme) { isColorBright(ColorNavBar.intColor) }
        }

    /** Цвет текста **/
    open val colorTextStateList: ColorStateList get() = getTextStateList(ColorText.intColor)

    /** Цвет вводимого текста **/
    open val colorInputTextStateList: ColorStateList get() = getTextStateList(ColorInputText.intColor)

    val isLightTheme: Boolean get() = isColorBright(ColorBackground.intColor)
    private val alphaCompound = 0.3

    val ThemeColor.intColor: Int get() = getColor(this)
    open fun getColor(color: ThemeColor): Int {
        return mColors[color]!!.invoke()
    }

    val ThemeFlag.boolValue: Boolean get() = getFlag(this)
    open fun getFlag(flag: ThemeFlag): Boolean {
        return mFlags[flag]!!.invoke()
    }

    // Вызывать внутри onCreate
    fun themeActivity(activity: Activity) {
        themeWindowBackground(activity.window)
        themeStatusBar(activity.window)
        themeNavigationBar(activity.window)
        ThemeEdgeEffect.themeOverScrollGlowColor(activity.resources, ColorEdgeGlow.intColor)
    }

    fun themeView(v: View): Boolean {
        when (v) {
            is ViewPager,
            is HorizontalScrollView,
            is NestedScrollView,
            is RecyclerView,
            is AbsListView,
            is ScrollView -> ThemeEdgeEffect.setColor(
                v,
                Rect(
                    ColorEdgeGlowLeft.intColor,
                    ColorEdgeGlowTop.intColor,
                    ColorEdgeGlowRight.intColor,
                    ColorEdgeGlowBottom.intColor
                )
            )
            is TextInputLayout -> themeTextInputLayout(v)
            is AppCompatEditText -> themeEditText(v)
            is SeekBar -> themeSeekBar(v)
            is ProgressBar -> themeProgressBar(v)
            is CheckBox -> themeCheckBox(v)
            is RadioButton -> themeRadioButton(v)
            is Switch -> themeSwitch(v)
            is SwitchCompat -> themeSwitch(v)
            is BottomNavigationView -> themeBottomNavigation(v)
            is FloatingActionButton -> themeFab(v)
            is Toolbar -> themeToolbar(v)
            is Button -> themeButton(v)
            is TextView -> themeTextView(v)
            is ImageView -> themeImageView(v)
            else -> return false
        }
        return true
    }

    private fun needThemeSubViews(view: View, viewThemed: Boolean): Boolean {
        if (view::class.java.simpleName == "ViewPager2") {
            return false
        }
        return if (viewThemed) {
            view is ScrollView || view is TextInputLayout
        } else {
            view is ViewGroup
        }
    }

    // применяет цвета для списка вьюшек
    fun themeViews(vararg view: View?) {
        view.forEach {
            if (it != null) {
                val viewThemed = themeView(it)
                if (needThemeSubViews(it, viewThemed)) {
                    themeViewBack(it)
                }
            }
        }
    }

    // применяет цвета для вьюшки и всех её сабвьюшек
    fun themeViewAndSubviews(view: View) {
        themeView(view)
        if (view is ViewGroup) {
            themeViewBack(view)
            themeChild(view)
        }
    }

    private fun themeChild(view: ViewGroup) {
        for (i in 0 until view.childCount) {
            val v = view.getChildAt(i)
            val viewThemed = themeView(v)
            if (needThemeSubViews(v, viewThemed)) {
                if (v is ViewGroup) {
                    themeChild(v)
                }
            }
        }
    }

    fun themeWindowBackground(window: Window) {
        window.setBackgroundDrawable(ColorDrawable(ColorWindowBackground.intColor))
    }

    fun themeStatusBar(
        window: Window,
        @ColorInt color: Int? = null,
        @ColorInt colorDark: Int? = null,
        lightTheme: Boolean? = null,
        drawSystemBar: Boolean? = null
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        val colorStatus = color ?: ColorStatusBar.intColor
        val colorStatusDark = colorDark ?: ColorStatusBarDark.intColor
        val isLightTheme = lightTheme ?: StatusLightTheme.boolValue
        val drawBar = drawSystemBar ?: StatusDrawSystemBar.boolValue

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setWindowFlag(window, WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS, true)
        }
        if (Color.alpha(colorStatus) == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                setWindowFlag(window, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, drawBar)
                if (!drawBar) {
                    window.statusBarColor = colorStatus
                }
            } else {
                setWindowFlag(window, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
            }
            setDecorFlag(window, View.SYSTEM_UI_FLAG_LAYOUT_STABLE, true)
            setDecorFlag(window, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN, true)
            if (!drawBar && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setDecorFlag(window, View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR, isLightTheme)
            }
        } else {
            setWindowFlag(window, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    window.statusBarColor = colorStatusDark
                } else {
                    window.statusBarColor = colorStatus
                    setDecorFlag(window, View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR, isLightTheme)
                }
            }
        }
    }

    fun themeNavigationBar(
        window: Window,
        @ColorInt color: Int? = null,
        @ColorInt colorDark: Int? = null,
        @ColorInt colorDivider: Int? = null,
        lightTheme: Boolean? = null,
        drawSystemBar: Boolean? = null
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }

        val colorNav = color ?: ColorNavBar.intColor
        val colorNavDark = colorDark ?: ColorNavBarDark.intColor
        val colorDiv = colorDivider ?: ColorNavBarDivider.intColor
        val isLightTheme = lightTheme ?: NavBarLightTheme.boolValue
        val drawBar = drawSystemBar ?: NavBarDrawSystemBar.boolValue

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setWindowFlag(window, WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS, true)
        }

        if (Color.alpha(colorNav) == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                setWindowFlag(window, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, drawBar)
                if (!drawBar) {
                    window.navigationBarColor = colorNav
                }
            } else {
                setWindowFlag(window, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, true)
            }

            setDecorFlag(window, View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION, true)
            if (!drawBar && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                setDecorFlag(window, View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR, isLightTheme)
            }
        } else {
            setWindowFlag(window, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                    window.navigationBarColor = colorNavDark
                } else {
                    window.navigationBarColor = colorNav
                    setDecorFlag(window, View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR, isLightTheme)
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                window.navigationBarDividerColor = colorDiv
            }
        }
    }

    fun themeViewBack(layout: View, @ColorInt color: Int? = null) {
        setBackgroundColorSavePadding(layout, color ?: ColorBackground.intColor)
    }

    fun themeSeekBar(seek: SeekBar, @ColorInt colorTickMark: Int? = null, @ColorInt colorThumb: Int? = null) {
        val tickColor = colorTickMark ?: ColorSeekBarTickMark.intColor
        val thumbColor = colorThumb ?: ColorSeekBarThumb.intColor

        try {
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                    seek.tickMarkTintList = ColorStateList.valueOf(tickColor)
                }
                seek is AppCompatSeekBar -> {
                    val editor = AppCompatSeekBar::class.java.getFieldByName("mAppCompatSeekBarHelper")?.get(seek) ?: return
                    editor.javaClass.getFieldByName("mTickMark")?.let { field: Field ->
                        (field.get(editor) as? Drawable)?.let { field.set(editor, it.tinted(tickColor)) }
                    }
                }
                else -> {
                    AbsSeekBar::class.java.getFieldByName("mTickMark")?.let { field: Field ->
                        (field.get(seek) as? Drawable)?.let { field.set(seek, it.tinted(tickColor)) }
                    }
                }
            }
        } catch (t: Throwable) {
            t.printStackTrace()
        }
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                seek.thumbTintList = ColorStateList.valueOf(thumbColor)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN -> {
                seek.thumb = seek.thumb.tinted(thumbColor)
            }
            else -> {
                AbsSeekBar::class.java.getFieldByName("mThumb")?.let { field: Field ->
                    (field.get(seek) as? Drawable)?.let { field.set(seek, it.tinted(thumbColor)) }
                }
            }
        }
        themeProgressBar(seek)
    }

    @Suppress("DEPRECATION")
    fun themeProgressBar(progress: ProgressBar) {
        val color = ColorProgressBar.intColor
        val colorSecondary = ColorProgressBarSecondary.intColor
        val colorBackground = ColorProgressBarBackground.intColor

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (progress.isIndeterminate) {
                progress.indeterminateTintList = ColorStateList.valueOf(color)
            } else {
                progress.progressTintList = ColorStateList.valueOf(color)
                progress.progressBackgroundTintList = ColorStateList.valueOf(colorBackground)
                progress.secondaryProgressTintList = ColorStateList.valueOf(colorSecondary)
                val colorRipple = ColorUtils.setAlphaComponent(colorBackground, (255 * alphaCompound).toInt())
                progress.background = RippleDrawable(ColorStateList.valueOf(colorRipple), null, null)
            }
            return
        }
        // circular progress have no progressDrawable
        if (progress.progressDrawable == null) {
            (progress.indeterminateDrawable as? LayerDrawable)?.apply {
                if (numberOfLayers == 1) {
                    setId(0, android.R.id.progress)
                    val progressDrawable = findDrawableByLayerId(android.R.id.progress).mutate()
                    ThemeUtils.tintDrawableWithMatrix(progressDrawable, color)
                } else if (numberOfLayers >= 2) {
                    setId(0, android.R.id.progress)
                    setId(1, android.R.id.secondaryProgress)
                    val progressDrawable = findDrawableByLayerId(android.R.id.progress).mutate()
                    progressDrawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
                }
            }
        } else {
            (progress.progressDrawable as? LayerDrawable)?.apply {
                findDrawableByLayerId(android.R.id.progress)?.mutate()
                    ?.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
                findDrawableByLayerId(android.R.id.secondaryProgress)?.mutate()
                    ?.setColorFilter(colorSecondary, PorterDuff.Mode.SRC_ATOP)
                findDrawableByLayerId(android.R.id.background)?.mutate()
                    ?.setColorFilter(colorBackground, PorterDuff.Mode.SRC_ATOP)
            }
            (progress.indeterminateDrawable as? AnimationDrawable)?.apply {
                setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
            }
        }
    }

    fun themeEditText(editText: AppCompatEditText) {
        ViewCompat.setBackgroundTintList(editText, ColorStateList.valueOf(ColorInputBottomLine.intColor)) // нижняя полоска

        editText.setCursorDrawableColor(ColorInputCursor.intColor) // моргающий курсор
        editText.setHandlesColor(ColorInputHandles.intColor) // Захваты выделения

        editText.setTextColor(colorInputTextStateList)
        editText.setHintTextColor(ColorInputHint.intColor)
        editText.highlightColor = ColorInputHighlight.intColor
    }

    fun themeTextView(textView: TextView, @ColorInt color: Int? = null) {
        textView.setTextColor(getTextStateList(color ?: ColorText.intColor, false))
    }

    fun themeTextViewSecondary(textView: TextView, @ColorInt color: Int? = null) {
        textView.setTextColor(getTextStateList(color ?: ColorText.intColor, true))
    }

    fun themeFab(fab: FloatingActionButton) {
        fab.supportBackgroundTintList = ColorStateList.valueOf(ColorFabBackground.intColor)
        fab.supportImageTintList = ColorStateList.valueOf(ColorFabIcon.intColor)

        val pressedRippleColors = getPressedRippleColors(ColorFabBackground.intColor)
        fab.supportBackgroundTintList = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_pressed),
                intArrayOf(-android.R.attr.state_pressed)
            ),
            intArrayOf(
                pressedRippleColors.first,
                ColorFabBackground.intColor
            )
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val rippleColor = ColorUtils.setAlphaComponent(pressedRippleColors.second, (255 * alphaCompound).toInt())
            fab.background = RippleDrawable(ColorStateList.valueOf(rippleColor), null, null)
        }
    }

    @ColorInt
    fun getPressedColor(@ColorInt buttonColor: Int, colorBrightness: Double? = null): Int {
        val brightness = colorBrightness ?: ThemeUtils.getColorBrightness(buttonColor)
        // задаём цвет нажатия от основного цвета (чуть темнее или светлее)
        return ThemeUtils.setColorBrightness(buttonColor, if (brightness > 50) brightness - 20 else brightness + 20)
    }

    fun getPressedRippleColors(@ColorInt buttonColor: Int): Pair<Int, Int> {
        // Задаём цвет выделения - немного темнее или светлее чем основной цвет кнопки
        val colorBrightness = ThemeUtils.getColorBrightness(buttonColor)
        val isMiddleBrightness = colorBrightness > 40 && colorBrightness < 60
        val pressedColor = getPressedColor(buttonColor, colorBrightness)
        val ripple: Int =
            if (isMiddleBrightness) pressedColor // Если кнопка среднеяркого цвета, то риппл красим как цвет нажатия
            else ThemeUtils.invertColorBrightness(buttonColor) // если кнопка белая - риппл чёрный, и наоборот
        return Pair(pressedColor, ripple)
    }

    fun themeImageView(imageView: ImageView, @ColorInt color: Int? = null) {
        ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(color ?: ColorIcon.intColor))
    }

    fun themeIcon(imageView: ImageView) {
        ImageViewCompat.setImageTintList(imageView, getTextStateList(ColorIcon.intColor))
    }

    private fun getSecondaryTextColor(@ColorInt color: Int): Int {
        val isColorDark = !isColorBright(color)
        val alpha = getTextAlpha(isColorDark, isSecondary = true, isDisabled = false)
        return ColorUtils.setAlphaComponent(color, (255 * alpha).toInt())
    }

    fun themeBottomNavigation(bottomNavigationView: BottomNavigationView) {
        bottomNavigationView.itemIconTintList = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_pressed),
                intArrayOf(android.R.attr.state_selected),
                intArrayOf(-android.R.attr.state_selected)
            ),
            intArrayOf(
                getPressedColor(ColorBottomNavIconSelected.intColor),
                ColorBottomNavIconSelected.intColor,
                ColorBottomNavIcon.intColor
            )
        )

        bottomNavigationView.itemTextColor = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_pressed),
                intArrayOf(android.R.attr.state_selected),
                intArrayOf(-android.R.attr.state_selected)
            ),
            intArrayOf(
                getPressedColor(ColorBottomNavTextSelected.intColor),
                ColorBottomNavTextSelected.intColor,
                ColorBottomNavText.intColor
            )
        )

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val pressed = getPressedColor(colorBackground)
//            val colorStateList = ColorStateList(
//                arrayOf(
//                    intArrayOf(android.R.attr.state_pressed),
//                    intArrayOf(android.R.attr.state_selected),
//                    intArrayOf(-android.R.attr.state_selected)
//                ), intArrayOf(
//                    pressed,
//                    Color.TRANSPARENT,
//                    Color.TRANSPARENT
//                )
//            )
//            bottomNavigationView.itemBackground = RippleDrawable(colorStateList, null, null)
//        }

        setBackgroundColorSavePadding(bottomNavigationView, ColorBottomNavBackground.intColor)
    }

    fun themeCellBg(view: View, @ColorInt bgColor: Int? = null) {
        val color = bgColor ?: ColorBackground.intColor

        val brightness = ThemeUtils.getColorBrightness(color)
        val accentColor = if (brightness < 50) Color.WHITE else Color.BLACK
        val pressedColor = ColorUtils.blendARGB(color, accentColor, 0.2F)

        val bg = getAdaptiveRippleDrawable(
            normalColor = color,
            pressedColor = pressedColor
        )
        setBackgroundSavePadding(view, bg)

//        val bgSelectable =
//            ThemeUtils.getDrawableResCompat(view.context, android.R.attr.selectableItemBackground)
//        val layers = LayerDrawable(arrayOf(ColorDrawable(color), bgSelectable))
//        setBackgroundSavePadding(view, layers)
    }

    fun themeDivider(view: View, @ColorInt color: Int? = null) {
        val dividerColor = color ?: ColorDivider.intColor
        themeViewBack(view, dividerColor)
    }

    fun themeButtonText(button: Button, textColor: Int, isPlain: Boolean) {
        val isColorBright = isColorBright(textColor)

        // нажатый цвет текста - миксим с чёрным или белым (чуть темнее или светлее)
        // Если кнопка с цветным фоном, то не меняем нажатый цвет
        val colorPressed = if (isPlain) ColorUtils.blendARGB(
            textColor,
            if (isColorBright) Color.BLACK else Color.WHITE,
            alphaCompound.toFloat()
        ) else textColor

        // цвет текста выключенной кнопки - сильно полупрозрачный
        val colorDisabled = ColorUtils.setAlphaComponent(textColor, (255 * getDisabledAlpha(!isColorBright)).toInt())

        val list = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_enabled),
                intArrayOf(android.R.attr.state_pressed),
                intArrayOf(-android.R.attr.state_pressed)
            ),
            intArrayOf(
                colorDisabled,
                colorPressed,
                textColor
            )
        )
        button.setTextColor(list)
    }

    fun themeButtonPlain(button: Button, textColor: Int? = null) {
        themeButtonText(button, textColor ?: ColorPrimary.intColor, true)

//        val buttonColor = colorBackground
//        val pressedRippleColors = getPressedRippleColors(buttonColor)
//        val bg = getAdaptiveRippleDrawable(
//            normalColor = Color.TRANSPARENT,
//            pressedColor = pressedRippleColors.first,
//            rippleColor = pressedRippleColors.first
//        )
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            button.background = bg
//        } else {
//            button.setBackgroundDrawable(bg)
//        }

        val bgSelectable = ThemeUtils.getAttrResCompat(button.context, android.R.attr.selectableItemBackground)
        setBackgroundResourceSavePadding(button, bgSelectable)
    }

    // Красим кнопку в определённый цвет
    fun themeButton(button: Button, bgColor: Int? = null, textColor: Int? = null) {
        val tColor = textColor ?: if (bgColor != null) getTextColor(bgColor) else ColorButtonText.intColor
        themeButtonText(button, tColor, false)

        val buttonColor = bgColor ?: ColorButtonBackground.intColor
        val pressedRippleColors = getPressedRippleColors(buttonColor)

//        button.supportBackgroundTintList = ColorStateList(
//            arrayOf(
//                intArrayOf(android.R.attr.state_pressed),
//                intArrayOf(-android.R.attr.state_pressed)
//            ), intArrayOf(
//                pressedRippleColors.first,
//                buttonColor
//            )
//        )

        val cornerRadius = 2.dpToPx(button.context)
        val dp4 = 4.dpToPx(button.context).toInt()
        val buttonPadding = Rect(dp4, dp4, dp4, dp4)

        val bg = getAdaptiveRippleDrawable(
            normalColor = buttonColor,
            pressedColor = pressedRippleColors.first,
            rippleColor = pressedRippleColors.second,
            cornerRadius = cornerRadius,
            padding = buttonPadding
        )
        setBackgroundSavePadding(button, bg)
    }

    fun getAdaptiveRippleDrawable(
        @ColorInt normalColor: Int,
        @ColorInt pressedColor: Int,
        @ColorInt rippleColor: Int = pressedColor,
        cornerRadius: Float = 0f,
        padding: Rect? = null
    ): Drawable {
        val isButtonThemeLight = isColorBright(normalColor)
        val disabledAlpha = getDisabledAlpha(isButtonThemeLight)
        val disabledColor = ColorUtils.setAlphaComponent(normalColor, (255 * disabledAlpha).toInt())

        val createStateDrawable = createStateDrawable(
            normalColor,
            pressedColor,
            disabled = disabledColor,
            cornerRadius = cornerRadius,
            padding = padding
        )

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            RippleDrawable(ColorStateList.valueOf(rippleColor), createStateDrawable, getRippleMask(rippleColor))
        } else {
            createStateDrawable
        }
    }

    private fun getRippleMask(color: Int): Drawable? {
        val outerRadii = FloatArray(8)
        // 3 is radius of final ripple,
        // instead of 3 you can give required final radius
        Arrays.fill(outerRadii, 3f)
        val r = RoundRectShape(outerRadii, null, null)
        val shapeDrawable = ShapeDrawable(r)
        shapeDrawable.paint.color = color
        return shapeDrawable
    }

    fun themeTextInputLayout(textInputLayout: TextInputLayout) {
        textInputLayout.setHelperTextColor(ColorStateList.valueOf(ColorInputHelper.intColor)) // Хелпер
        textInputLayout.setErrorTextColor(ColorStateList.valueOf(ColorInputError.intColor)) // Подсветка ошибок
        textInputLayout.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(ColorInputPasswordEye.intColor)) // Глаз сбоку от пароля
        textInputLayout.setEndIconTintList(ColorStateList.valueOf(ColorInputPasswordEye.intColor)) // Глаз сбоку от пароля

        val states = arrayOf(
            intArrayOf(android.R.attr.state_focused),
            intArrayOf(-android.R.attr.state_focused)
        )

        textInputLayout.defaultHintTextColor = ColorStateList(
            states,
            intArrayOf(ColorInputHintFocused.intColor, ColorInputHint.intColor)
        ) // Верхняя надпись, Хинт
//        textInputLayout.boxStrokeColor = accentColor
    }

    fun TextView.setCursorDrawableColor(@ColorInt color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            textCursorDrawable = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(color, color))
                .apply { setSize(2.spToPx(context).toInt(), textSize.toInt()) }
            return
        }

        try {
            val editorField = TextView::class.java.getFieldByName("mEditor")
            val editor = editorField?.get(this) ?: this
            val editorClass: Class<*> = if (editorField != null) editor.javaClass else TextView::class.java
            val cursorRes = TextView::class.java.getFieldByName("mCursorDrawableRes")?.get(this) as? Int ?: return

            val tintedCursorDrawable = ContextCompat.getDrawable(context, cursorRes)?.tinted(color) ?: return

            val cursorField = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                editorClass.getFieldByName("mDrawableForCursor")
            } else {
                null
            }
            if (cursorField != null) {
                cursorField.set(editor, tintedCursorDrawable)
            } else {
                editorClass.getFieldByName("mCursorDrawable", "mDrawableForCursor")
                    ?.set(editor, arrayOf(tintedCursorDrawable, tintedCursorDrawable))
            }
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    @SuppressLint("PrivateApi")
    fun TextView.setHandlesColor(@ColorInt color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val size = 22.spToPx(context).toInt()
            val corner = size.toFloat() / 2
            val inset = 10.spToPx(context).toInt()

            // left drawable
            val drLeft = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(color, color))
            drLeft.setSize(size, size)
            drLeft.cornerRadii = floatArrayOf(corner, corner, 0f, 0f, corner, corner, corner, corner)
            setTextSelectHandleLeft(InsetDrawable(drLeft, inset, 0, inset, inset))

            // right drawable
            val drRight = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(color, color))
            drRight.setSize(size, size)
            drRight.cornerRadii = floatArrayOf(0f, 0f, corner, corner, corner, corner, corner, corner)
            setTextSelectHandleRight(InsetDrawable(drRight, inset, 0, inset, inset))

            // middle drawable
            val drMiddle = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(color, color))
            drMiddle.setSize(size, size)
            drMiddle.cornerRadii = floatArrayOf(0f, 0f, corner, corner, corner, corner, corner, corner)
            val mInset = (sqrt(2f) * corner - corner).toInt()
            val insetDrawable = InsetDrawable(drMiddle, mInset, mInset, mInset, mInset)
            val rotateDrawable = RotateDrawable()
            rotateDrawable.drawable = insetDrawable
            rotateDrawable.toDegrees = 45f
            rotateDrawable.level = 10000
            setTextSelectHandle(rotateDrawable)
            return
        }

        try {
            val editorField = TextView::class.java.getFieldByName("mEditor")
            val editor = editorField?.get(this) ?: this

            val editorClass: Class<*> = if (editorField != null) {
                runCatching { Class.forName("android.widget.Editor") }.getOrNull() ?: editorField.javaClass
            } else {
                TextView::class.java
            }
            val handles = listOf(
                "mSelectHandleLeft" to "mTextSelectHandleLeftRes",
                "mSelectHandleRight" to "mTextSelectHandleRightRes",
                "mSelectHandleCenter" to "mTextSelectHandleRes"
            )
            for (i in 0 until handles.size) {
                editorClass.getFieldByName(handles[i].first)?.let { field: Field ->
                    val drawable = field.get(editor) as? Drawable
                        ?: TextView::class.java.getFieldByName(handles[i].second)?.getInt(this)
                            ?.let { ContextCompat.getDrawable(context, it) }
                    if (drawable != null) field.set(editor, drawable.tinted(color))
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun getTextColor(@ColorInt bgColor: Int): Int {
        return Color.parseColor(if (isColorBright(bgColor)) "#000000" else "#ffffff")
    }

    fun getTextStateList(@ColorInt textColor: Int, isSecondary: Boolean = false): ColorStateList {
        val states = arrayOf(
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf(-android.R.attr.state_enabled)
        )
        val isTextColorDark = !isColorBright(textColor)
        val alpha = getTextAlpha(isTextColorDark, isSecondary, false)
        val alphaDisabled = getTextAlpha(isTextColorDark, isSecondary, true)
        val colorText = ColorUtils.setAlphaComponent(textColor, (255 * alpha).toInt())
        val colorTextDisabled = ColorUtils.setAlphaComponent(textColor, (255 * alphaDisabled).toInt())
        val colors = intArrayOf(colorText, colorTextDisabled)
        return ColorStateList(states, colors)
    }

    @Suppress("LocalVariableName")
    private fun getTextAlpha(isTextColorDark: Boolean, isSecondary: Boolean, isDisabled: Boolean): Double {
        var alphaText = if (isTextColorDark) {
            if (!isSecondary) 0.87  //primary_text_default_material_light
            else 0.54               //secondary_text_default_material_light
        } else {
            if (!isSecondary) 1.0   //primary_text_default_material_dark
            else 0.7                //secondary_text_default_material_dark
        }

        if (isDisabled) {
            alphaText *= getDisabledAlpha(isTextColorDark)
        }

        return alphaText
    }

    // получение альфы в зависимости от текущей темы
    private fun getDisabledAlpha(isThemeLight: Boolean): Double {
        return if (isThemeLight) 0.26       //disabled_alpha_material_light
        else 0.3                            //disabled_alpha_material_dark
    }

    // получение хинта в зависимости от цвета текста
    fun getHintColor(@ColorInt textColor: Int): Int {
        val isTextColorDark = !isColorBright(textColor)
        val alphaHint = getHintAlpha(isTextColorDark)
        return ColorUtils.setAlphaComponent(textColor, (255 * alphaHint).toInt())
    }

    @Suppress("LocalVariableName")
    private fun getHintAlpha(isTextColorDark: Boolean): Double {
        return when {
            isTextColorDark -> 0.38     //hint_alpha_material_light
            else -> 0.5                 //hint_alpha_material_dark
        }
    }

    // получение цвета разделителя в зависимости от цвета текста
    fun getDividerColor(@ColorInt textColor: Int): Int {
        val alphaDivider = getDividerAlpha(!isColorBright(textColor))
        return ColorUtils.setAlphaComponent(textColor, (255 * alphaDivider).toInt())
    }

    private fun getDividerAlpha(isTextColorDark: Boolean): Double =
        if (isTextColorDark) 0.12 else 0.2

    fun themeCheckBox(checkBox: CheckBox, @ColorInt colorActive: Int? = null, @ColorInt colorInactive: Int? = null) {
        val colorA = colorActive ?: ColorCheckBoxActive.intColor
        val colorI = colorInactive ?: ColorCheckBoxInactive.intColor

        CompoundButtonCompat.setButtonTintList(checkBox, getCompoundColors(colorA, colorI))
        checkBox.setTextColor(colorTextStateList)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val rippleColor = ColorUtils.setAlphaComponent(colorA, (255 * alphaCompound).toInt())
            checkBox.background = RippleDrawable(ColorStateList.valueOf(rippleColor), null, null)
        }
    }

    fun themeRadioButton(radioButton: RadioButton, @ColorInt colorActive: Int? = null, @ColorInt colorInactive: Int? = null) {
        val colorA = colorActive ?: ColorRadioActive.intColor
        val colorI = colorInactive ?: ColorRadioInactive.intColor

        CompoundButtonCompat.setButtonTintList(radioButton, getCompoundColors(colorA, colorI))
        radioButton.setTextColor(colorTextStateList)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val rippleColor = ColorUtils.setAlphaComponent(colorA, (255 * alphaCompound).toInt())
            radioButton.background = RippleDrawable(ColorStateList.valueOf(rippleColor), null, null)
        }
    }

    private fun getCompoundColors(@ColorInt colorActive: Int, @ColorInt colorInactive: Int): ColorStateList {
        val disabledAlpha = getDisabledAlpha(!isColorBright(colorInactive))

        return ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked, -android.R.attr.state_enabled),
                intArrayOf(-android.R.attr.state_checked, -android.R.attr.state_enabled),
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(-android.R.attr.state_checked)
            ),
            intArrayOf(
                ColorUtils.setAlphaComponent(colorActive, (255 * disabledAlpha).toInt()),
                ColorUtils.setAlphaComponent(colorInactive, (255 * disabledAlpha).toInt()),
                colorActive,
                colorInactive
            )
        )
    }

    fun themeSwitch(switch: CompoundButton) {
        val checkedFront = ColorSwitchActive.intColor
        val uncheckedFront = ColorSwitchInactive.intColor
        val colorStateTrack = getCompoundColors(checkedFront, uncheckedFront)

        val checkedBack = ColorUtils.setAlphaComponent(checkedFront, (255 * alphaCompound).toInt())
        val uncheckedBack = ColorUtils.setAlphaComponent(ColorText.intColor, (255 * alphaCompound).toInt())
        val colorStateThumb = getCompoundColors(checkedBack, uncheckedBack)

        if (switch is SwitchCompat) {
            switch.thumbTintList = colorStateTrack
            switch.trackTintList = colorStateThumb
        } else if (switch is Switch && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            switch.thumbTintList = colorStateTrack
            switch.trackTintList = colorStateThumb
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            switch.backgroundTintList = colorStateThumb
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            switch.background = RippleDrawable(ColorStateList.valueOf(checkedBack), null, null)
        }

        switch.setTextColor(colorTextStateList)
    }

    // call this after dialog.show() to apply theme to alertDialog
    // or inside/after onStart() of DialogFragment
    fun themeAlertDialog(alertDialog: AlertDialog, view: View? = null) {
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(ColorAlertBackground.intColor))

        val message: TextView? = if (view != null) view.findViewById(android.R.id.message)
        else alertDialog.findViewById(android.R.id.message)

        message?.setTextColor(ColorStateList.valueOf(ColorAlertMessage.intColor))

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(ColorAlertButtonPositive.intColor)
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(ColorAlertButtonNegative.intColor)
        alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL)?.setTextColor(ColorAlertButtonNeutral.intColor)

        val icon: View? = if (view != null) view.findViewById(android.R.id.icon)
        else alertDialog.findViewById(android.R.id.icon)

        (icon as? ImageView)?.drawable?.tinted(ColorAlertIcon.intColor)

        val title = findAlertTitle(icon?.parent as? ViewGroup)
        title?.setTextColor(ColorStateList.valueOf(ColorAlertTitle.intColor))
    }

    private fun findAlertTitle(group: ViewGroup?): TextView? {
        if (group == null) return null
        for (i in 0 until group.childCount) when (val v = group.getChildAt(i)) {
            is TextView -> return v
            is ViewGroup -> findAlertTitle(v)
        }
        return null
    }

    fun themeToolbar(toolbar: Toolbar) {
        themeViewBack(toolbar, ColorActionBar.intColor)
        themeAllMenuIcons(toolbar.menu, ColorActionBarIcons.intColor)

        toolbar.navigationIcon = toolbar.navigationIcon?.tinted(ColorActionBarIcons.intColor)
        toolbar.setTitleTextColor(ColorActionBarText.intColor)
        toolbar.setSubtitleTextColor(ColorActionBarTextSecondary.intColor)
    }

    open fun themeAllMenuIcons(menu: Menu, @ColorInt color: Int? = null) {
        val iconColor = color ?: ColorActionBarIcons.intColor
        for (i in 0 until menu.size()) {
            val item = menu.getItem(i)
            themeMenuItem(item, iconColor)
            if (item.hasSubMenu()) {
                themeAllMenuIcons(item.subMenu, iconColor)
            }
        }
    }

    @SuppressLint("ResourceType")
    fun themeMenuItem(item: MenuItem, color: Int?) {
        val iconColor = color ?: ColorActionBarIcons.intColor
        item.icon = item.icon.tinted(iconColor)
        item.actionView
            ?.findViewById<View?>(1000411) // R.id.expand_activities_button)
            ?.findViewById<ImageView?>(1000095) // R.id.image)
            ?.let {
                if (it.drawable != null) {
                    it.setImageDrawable(it.drawable.tinted(iconColor))
                }
            }
    }
}

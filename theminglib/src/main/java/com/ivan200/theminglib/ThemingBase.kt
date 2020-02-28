package com.ivan200.theminglib

import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Build
import android.view.*
import android.widget.*
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
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.ivan200.theminglib.ThemeColor.*
import com.ivan200.theminglib.ThemeFlag.*
import com.ivan200.theminglib.ThemeUtils.createStateDrawable
import com.ivan200.theminglib.ThemeUtils.dpToPx
import com.ivan200.theminglib.ThemeUtils.isColorBright
import com.ivan200.theminglib.ThemeUtils.setBackgroundColorSavePadding
import com.ivan200.theminglib.ThemeUtils.setBackgroundResourceSavePadding
import com.ivan200.theminglib.ThemeUtils.setBackgroundSavePadding
import com.ivan200.theminglib.ThemeUtils.setDecorFlag
import com.ivan200.theminglib.ThemeUtils.setWindowFlag
import com.ivan200.theminglib.ThemeUtils.spToPx
import java.util.*
import kotlin.math.sqrt


//
// Created by Ivan200 on 28.11.2019.
//
@Suppress("MemberVisibilityCanBePrivate")
abstract class ThemingBase {
    val mColors: EnumMap<ThemeColor, () -> Int> =
        EnumMap<ThemeColor, () -> Int>(ThemeColor::class.java).apply {
            put(colorPrimary) { Color.parseColor("#54a9ff") }
            put(colorBackground) { Color.parseColor("#303030") }
            put(colorFine) { Color.parseColor("#4CAF50") }
            put(colorError) { Color.parseColor("#D50000") }
            put(colorStatusBar) { Color.BLACK }
            put(colorStatusBarDark) { Color.BLACK }
            put(colorNavBar) { Color.BLACK }
            put(colorNavBarDark) { Color.BLACK }
            put(colorNavBarDivider) { Color.TRANSPARENT }
            put(colorOverScroll) { colorPrimary.intColor }
            put(colorText) { getTextColor(colorBackground.intColor) }
            put(colorTextHint) { getHintColor(colorText.intColor) }
            put(colorDivider) { getDividerColor(colorText.intColor) }
            put(colorInputPasswordEye) { colorTextHint.intColor }
            put(colorInputHelper) { colorFine.intColor }
            put(colorInputError) { colorError.intColor }
            put(colorInputHintFocused) { colorPrimary.intColor }
            put(colorInputHint) { colorTextHint.intColor }
            put(colorInputText) { colorText.intColor }
            put(colorInputBottomLine) { colorPrimary.intColor }
            put(colorInputCursor) { colorPrimary.intColor }
            put(colorInputHighlight) { colorPrimary.intColor }
            put(colorInputHandles) { colorPrimary.intColor }
            put(colorProgressBar) { colorPrimary.intColor }
            put(colorProgressBarSecondary) { colorProgressBar.intColor }
            put(colorProgressBarBackground) { getSecondaryTextColor(colorText.intColor) }
            put(colorSeekBarThumb) { colorProgressBar.intColor }
            put(colorSeekBarTickMark) { colorProgressBarBackground.intColor }
            put(colorButtonBackground) { colorPrimary.intColor }
            put(colorButtonText) { getTextColor(colorButtonBackground.intColor) }
            put(colorCheckBoxActive) { colorPrimary.intColor }
            put(colorCheckBoxInactive) { colorTextHint.intColor }
            put(colorRadioActive) { colorPrimary.intColor }
            put(colorRadioInactive) { colorTextHint.intColor }
            put(colorSwitchActive) { colorPrimary.intColor }
            put(colorSwitchInactive) { Color.parseColor(if (isLightTheme) "#ececec" else "#b9b9b9") }
            put(colorFabBackground) { colorPrimary.intColor }
            put(colorFabIcon) { colorText.intColor }
            put(colorAlertBackground) { colorBackground.intColor }
            put(colorAlertTitle) { colorText.intColor }
            put(colorAlertIcon) { colorText.intColor }
            put(colorAlertMessage) { colorText.intColor }
            put(colorAlertButtons) { colorPrimary.intColor }
            put(colorIcon) { colorText.intColor }
            put(colorActionBar) { colorPrimary.intColor }
            put(colorActionBarText) { getTextColor(colorActionBar.intColor) }
            put(colorActionBarTextSecondary) { getSecondaryTextColor(colorActionBarText.intColor) }
            put(colorActionBarIcons) { colorActionBarText.intColor }
            put(colorBottomNavBackground) { colorBackground.intColor }
            put(colorBottomNavIcon) { getSecondaryTextColor(colorText.intColor) }
            put(colorBottomNavIconSelected) { colorPrimary.intColor }
            put(colorBottomNavText) { getSecondaryTextColor(colorText.intColor) }
            put(colorBottomNavTextSelected) { colorPrimary.intColor }
        }

    val mFlags: EnumMap<ThemeFlag, () -> Boolean> =
        EnumMap<ThemeFlag, () -> Boolean>(ThemeFlag::class.java).apply {
            put(statusDrawSystemBar) { true }
            put(statusLightTheme) { isColorBright(colorStatusBar.intColor) }
            put(navBarDrawSystemBar) { true }
            put(navBarLightTheme) { isColorBright(colorNavBar.intColor) }
        }

    /** Цвет текста **/
    open val colorTextStateList: ColorStateList get() = getTextStateList(colorText.intColor)

    /** Цвет вводимого текста **/
    open val colorInputTextStateList: ColorStateList get() = getTextStateList(colorInputText.intColor)

    val isLightTheme: Boolean get() = isColorBright(colorBackground.intColor)
    private val alphaCompound = 0.3

    val ThemeColor.intColor: Int get() = getColor(this)
    open fun getColor(color: ThemeColor): Int {
        return mColors[color]!!.invoke()
    }

    val ThemeFlag.boolValue: Boolean get() = getFlag(this)
    open fun getFlag(flag: ThemeFlag): Boolean {
        return mFlags[flag]!!.invoke()
    }

    //Вызывать внутри onCreate
    fun themeActivity(activity: Activity){
        activity.window.setBackgroundDrawable(ColorDrawable(colorBackground.intColor))

        themeStatusBar(activity.window)
        themeNavigationBar(activity.window)
        themeOverScrollGlowColor(activity.resources, colorOverScroll.intColor)
    }

    fun themeView(v: View): Boolean {
        //skip this classes and it's subviews
        if(v is RecyclerView ||
            v is ListView ||
            v is ViewPager ||
            v::class.java.simpleName == "ViewPager2"){
            return true
        }

        when (v) {
            is TextInputLayout -> {
                themeTextInputLayout(v)
                return false
            }
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

    //применяет цвета для списка вьюшек
    fun themeViews(vararg view: View?) {
        view.forEach {
            if(it != null) {
                val viewThemed = themeView(it)
                if (!viewThemed && it is ViewGroup) {
                    themeViewBack(it)
                }
            }
        }
    }

    //применяет цвета для вьюшки и всех её сабвьюшек
    fun themeViewAndSubviews(view: View) {
        if(view is ViewGroup){
            themeViewBack(view)
            themeChild(view)
        } else{
            themeView(view)
        }
    }

    private fun themeChild(view: ViewGroup){
        for (i in 0 until view.childCount) {
            val v = view.getChildAt(i)
            val viewThemed = themeView(v)
            if(!viewThemed && v is ViewGroup){
                themeChild(v)
            }
        }
    }


    //Перекрашивание цвета оверскролла на всех RecyclerView на api<21. Достаточно вызвать 1 раз в onCreate приложения
    fun themeOverScrollGlowColor(res: android.content.res.Resources, colorID: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            changeResColor(res, "overscroll_glow", colorID)
            changeResColor(res, "overscroll_edge", colorID)
        }
    }

    //Перекрашивание системного ресурса используемого в приложении в определённый цвет
    @Suppress("DEPRECATION")
    private fun changeResColor(res: android.content.res.Resources, resId: String, colorID: Int) {
        try {
            val drawableId = res.getIdentifier(resId, "drawable", "android")
            val drawable = res.getDrawable(drawableId)
            drawable.setColorFilter(res.getColor(colorID), PorterDuff.Mode.SRC_ATOP)
        } catch (ignored: Exception) {
        }
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
        val colorStatus = color ?: colorStatusBar.intColor
        val colorStatusDark = colorDark ?: colorStatusBarDark.intColor
        val isLightTheme =  lightTheme ?: statusLightTheme.boolValue
        val drawBar = drawSystemBar ?: statusDrawSystemBar.boolValue

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setWindowFlag(window, WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS, true)
        }
        if(Color.alpha(colorStatus) == 0){
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

    fun themeNavigationBar (
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

        val colorNav = color ?: colorNavBar.intColor
        val colorNavDark = colorDark ?: colorNavBarDark.intColor
        val colorDiv = colorDivider ?: colorNavBarDivider.intColor
        val isLightTheme =  lightTheme ?: navBarLightTheme.boolValue
        val drawBar = drawSystemBar ?: navBarDrawSystemBar.boolValue

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setWindowFlag(window, WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS, true)
        }

        if(Color.alpha(colorNav) == 0) {
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
        setBackgroundColorSavePadding(layout, color ?: colorBackground.intColor)
    }

    fun themeSeekBar(seek: SeekBar, @ColorInt colorTickMark: Int? = null, @ColorInt colorThumb: Int? = null) {
        val tickColor = colorTickMark ?: colorSeekBarTickMark.intColor
        val thumbColor = colorThumb ?: colorSeekBarThumb.intColor

        try {
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                    seek.tickMarkTintList = ColorStateList.valueOf(tickColor)
                }
                seek is AppCompatSeekBar -> {
                    ThemeUtils.modifyPrivateFieldThroughEditor<Drawable>(
                        seek, AppCompatSeekBar::class.java,
                        "mAppCompatSeekBarHelper", "mTickMark"
                    ) {
                        it?.apply { ThemeUtils.tintDrawable(this, tickColor)}
                    }
                }
                else -> {
                    ThemeUtils.modifyPrivateField<Drawable>(seek, AbsSeekBar::class.java, "mTickMark") {
                        it?.apply {ThemeUtils.tintDrawable(this, tickColor)}
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
                seek.thumb = ThemeUtils.tintDrawable(seek.thumb, thumbColor)
            }
            else -> {
                ThemeUtils.modifyPrivateField<Drawable>(seek, AbsSeekBar::class.java, "mThumb") {
                    it?.apply {ThemeUtils.tintDrawable(this, thumbColor)}
                }
            }
        }
        themeProgressBar(seek)
    }

    @Suppress("DEPRECATION")
    fun themeProgressBar(progress: ProgressBar) {
        val color = colorProgressBar.intColor
        val colorSecondary = colorProgressBarSecondary.intColor
        val colorBackground = colorProgressBarBackground.intColor

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if(progress.isIndeterminate){
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
        //circular progress have no progressDrawable
        if(progress.progressDrawable == null){
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

    fun themeEditText(editText: AppCompatEditText){

        ViewCompat.setBackgroundTintList(editText, ColorStateList.valueOf(colorInputBottomLine.intColor))    //нижняя полоска
        setCursorDrawableColor(editText, colorInputCursor.intColor)          //моргающий курсор
        setHandlesColor(editText, colorInputHandles.intColor)                //Захваты выделения

        editText.setTextColor(colorInputTextStateList)
        editText.setHintTextColor(colorInputHint.intColor)
        editText.highlightColor = colorInputHighlight.intColor
    }

    fun themeTextView(textView: TextView, @ColorInt color: Int? = null) {
        textView.setTextColor(getTextStateList(color ?: colorText.intColor, false))
    }

    fun themeTextViewSecondary(textView: TextView, @ColorInt color: Int? = null) {
        textView.setTextColor(getTextStateList(color ?: colorText.intColor, true))
    }

    fun themeFab(fab: FloatingActionButton){
        fab.supportBackgroundTintList = ColorStateList.valueOf(colorFabBackground.intColor)
        fab.supportImageTintList = ColorStateList.valueOf(colorFabIcon.intColor)

        val pressedRippleColors = getPressedRippleColors(colorFabBackground.intColor)
        fab.supportBackgroundTintList = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_pressed),
                intArrayOf(-android.R.attr.state_pressed)
            ), intArrayOf(
                pressedRippleColors.first,
                colorFabBackground.intColor
            )
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val rippleColor = ColorUtils.setAlphaComponent(pressedRippleColors.second, (255 * alphaCompound).toInt())
            fab.background = RippleDrawable(ColorStateList.valueOf(rippleColor), null, null)
        }
    }


    @ColorInt fun getPressedColor(@ColorInt buttonColor: Int, colorBrightness: Double? = null): Int {
        val brightness = colorBrightness ?: ThemeUtils.getColorBrightness(buttonColor)
        //задаём цвет нажатия от основного цвета (чуть темнее или светлее)
        return ThemeUtils.setColorBrightness(buttonColor,if (brightness > 50) brightness - 20 else brightness + 20)
    }

    fun getPressedRippleColors(@ColorInt buttonColor: Int): Pair<Int, Int> {
        //Задаём цвет выделения - немного темнее или светлее чем основной цвет кнопки
        val colorBrightness = ThemeUtils.getColorBrightness(buttonColor)
        val isMiddleBrightness = colorBrightness > 40 && colorBrightness < 60
        val pressedColor = getPressedColor(buttonColor, colorBrightness)
        val ripple: Int =
            if (isMiddleBrightness) pressedColor      //Если кнопка среднеяркого цвета, то риппл красим как цвет нажатия
            else ThemeUtils.invertColorBrightness(buttonColor)      //если кнопка белая - риппл чёрный, и наоборот
        return Pair(pressedColor, ripple)
    }

    fun themeImageView(imageView: ImageView, @ColorInt color: Int? = null){
        ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(color ?: colorIcon.intColor))
    }

    fun themeIcon(imageView: ImageView){
        ImageViewCompat.setImageTintList(imageView, getTextStateList(colorIcon.intColor))
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
            ), intArrayOf(
                getPressedColor(colorBottomNavIconSelected.intColor),
                colorBottomNavIconSelected.intColor,
                colorBottomNavIcon.intColor
            )
        )

        bottomNavigationView.itemTextColor = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_pressed),
                intArrayOf(android.R.attr.state_selected),
                intArrayOf(-android.R.attr.state_selected)
            ), intArrayOf(
                getPressedColor(colorBottomNavTextSelected.intColor),
                colorBottomNavTextSelected.intColor,
                colorBottomNavText.intColor
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

        setBackgroundColorSavePadding(bottomNavigationView, colorBottomNavBackground.intColor)
    }

    fun themeCellBg(view: View, @ColorInt bgColor: Int? = null) {
        val color = bgColor ?: colorBackground.intColor

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
        val dividerColor = color ?: colorDivider.intColor
        themeViewBack(view, dividerColor)
    }

    fun themeButtonText(button: Button, textColor: Int, isPlain: Boolean){
        val isColorBright = isColorBright(textColor)

        //нажатый цвет текста - миксим с чёрным или белым (чуть темнее или светлее)
        //Если кнопка с цветным фоном, то не меняем нажатый цвет
        val colorPressed = if(isPlain) ColorUtils.blendARGB(
            textColor,
            if (isColorBright) Color.BLACK else Color.WHITE,
            alphaCompound.toFloat()
        ) else textColor

        //цвет текста выключенной кнопки - сильно полупрозрачный
        val colorDisabled = ColorUtils.setAlphaComponent(textColor, (255 * getDisabledAlpha(!isColorBright)).toInt())

        val list = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_enabled),
                intArrayOf(android.R.attr.state_pressed),
                intArrayOf(-android.R.attr.state_pressed)
            ), intArrayOf(
                colorDisabled,
                colorPressed,
                textColor
            )
        )
        button.setTextColor(list)
    }

    fun themeButtonPlain(button: Button, textColor: Int? = null){
        themeButtonText(button, textColor ?: colorPrimary.intColor, true)

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

    //Красим кнопку в определённый цвет
    fun themeButton(button: Button, bgColor: Int? = null, textColor: Int? = null){
        val tColor = textColor ?: if (bgColor != null) getTextColor(bgColor) else colorButtonText.intColor
        themeButtonText(button, tColor, false)

        val buttonColor = bgColor ?: colorButtonBackground.intColor
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

        val cornerRadius = dpToPx(2, button.context)
        val dp4 = dpToPx(4, button.context).toInt()
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

    fun themeTextInputLayout(textInputLayout: TextInputLayout){
        textInputLayout.setHelperTextColor(ColorStateList.valueOf(colorInputHelper.intColor))     //Хелпер
        textInputLayout.setErrorTextColor(ColorStateList.valueOf(colorInputError.intColor))       //Подсветка ошибок
        textInputLayout.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(colorInputPasswordEye.intColor))   //Глаз сбоку от пароля
        textInputLayout.setEndIconTintList(ColorStateList.valueOf(colorInputPasswordEye.intColor))   //Глаз сбоку от пароля

        val states = arrayOf(
            intArrayOf(android.R.attr.state_focused),
            intArrayOf(-android.R.attr.state_focused)
        )

        textInputLayout.defaultHintTextColor = ColorStateList(states,
            intArrayOf(colorInputHintFocused.intColor, colorInputHint.intColor))    //Верхняя надпись, Хинт
//        textInputLayout.boxStrokeColor = accentColor
    }

    fun setCursorDrawableColor(editText: TextView, @ColorInt color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val gradientDrawable = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(color, color))
            gradientDrawable.setSize(spToPx(2, editText.context).toInt(), editText.textSize.toInt())
            editText.textCursorDrawable = gradientDrawable
            return
        }

        try {
            val editorField = try {
                TextView::class.java.getDeclaredField("mEditor").apply { isAccessible = true }
            } catch (t: Throwable) {
                null
            }
            val editor = editorField?.get(editText) ?: editText
            val editorClass: Class<*> = if (editorField == null) TextView::class.java else editor.javaClass

            val tintedCursorDrawable =
                ThemeUtils.getPrivateFieldOrNull<Int>(editText, TextView::class.java, "mCursorDrawableRes")
                    ?.let { ContextCompat.getDrawable(editText.context, it) ?: return }
                    ?.let { ThemeUtils.tintDrawable(it, color) } ?: return

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ThemeUtils.modifyPrivateField<Drawable?>(editor, editorClass, "mDrawableForCursor"){
                    tintedCursorDrawable
                }
            } else {
                ThemeUtils.modifyPrivateField<Array<Drawable?>?>(editor, editorClass, "mDrawableForCursor"){
                    arrayOf(tintedCursorDrawable, tintedCursorDrawable)
                }
            }
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    fun setHandlesColor(textView: TextView, @ColorInt color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val size = spToPx(22, textView.context).toInt()
            val corner = size.toFloat() / 2
            val inset = spToPx(10, textView.context).toInt()

            //left drawable
            val drLeft =
                GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(color, color))
            drLeft.setSize(size, size)
            drLeft.cornerRadii = floatArrayOf(corner, corner, 0f, 0f, corner, corner, corner, corner)
            textView.setTextSelectHandleLeft(InsetDrawable(drLeft, inset, 0, inset, inset))

            //right drawable
            val drRight =
                GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(color, color))
            drRight.setSize(size, size)
            drRight.cornerRadii =
                floatArrayOf(0f, 0f, corner, corner, corner, corner, corner, corner)
            textView.setTextSelectHandleRight(InsetDrawable(drRight, inset, 0, inset, inset))

            //middle drawable
            val drMiddle =
                GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(color, color))
            drMiddle.setSize(size, size)
            drMiddle.cornerRadii =
                floatArrayOf(0f, 0f, corner, corner, corner, corner, corner, corner)
            val mInset = (sqrt(2f) * corner - corner).toInt()
            val insetDrawable = InsetDrawable(drMiddle, mInset, mInset, mInset, mInset)
            val rotateDrawable = RotateDrawable()
            rotateDrawable.drawable = insetDrawable
            rotateDrawable.toDegrees = 45f
            rotateDrawable.level = 10000
            textView.setTextSelectHandle(rotateDrawable)
            return
        }

        try {
            val editorField = try {
                TextView::class.java.getDeclaredField("mEditor")
                    .apply { if (!isAccessible) isAccessible = true }
            } catch (t: Throwable) {
                null
            }
            val editor = if (editorField == null) textView else editorField[textView]
            val editorClass: Class<*> =
                if (editorField == null) TextView::class.java else editor.javaClass

            val handleNames = arrayOf(
                "mSelectHandleLeft",
                "mSelectHandleRight",
                "mSelectHandleCenter"
            )
            val resNames = arrayOf(
                "mTextSelectHandleLeftRes",
                "mTextSelectHandleRightRes",
                "mTextSelectHandleRes"
            )
            for (i in handleNames.indices) {
                ThemeUtils.modifyPrivateField<Drawable?>(editor, editorClass, handleNames[i]) {
                    val img = it
                        ?: ThemeUtils.getPrivateFieldOrNull<Int>(textView, TextView::class.java, resNames[i])
                            ?.run { ContextCompat.getDrawable(textView.context, this) }

                    img?.run { ThemeUtils.tintDrawable(this, color) }
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
        val alphaDisabled  = getTextAlpha(isTextColorDark, isSecondary, true)
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

    //получение альфы в зависимости от текущей темы
    private fun getDisabledAlpha(isThemeLight: Boolean): Double {
        return if (isThemeLight) 0.26       //disabled_alpha_material_light
        else 0.3                            //disabled_alpha_material_dark
    }

    //получение хинта в зависимости от цвета текста
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

    //получение цвета разделителя в зависимости от цвета текста
    fun getDividerColor(@ColorInt textColor: Int): Int {
        val alphaDivider = getDividerAlpha(!isColorBright(textColor))
        return ColorUtils.setAlphaComponent(textColor, (255 * alphaDivider).toInt())
    }

    private fun getDividerAlpha(isTextColorDark: Boolean): Double =
        if (isTextColorDark) 0.12 else 0.2

    fun themeCheckBox(checkBox: CheckBox, @ColorInt colorActive: Int? = null, @ColorInt colorInactive: Int? = null) {
        val colorA = colorActive ?: colorCheckBoxActive.intColor
        val colorI = colorInactive ?: colorCheckBoxInactive.intColor

        CompoundButtonCompat.setButtonTintList(checkBox, getCompoundColors(colorA, colorI))
        checkBox.setTextColor(colorTextStateList)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val rippleColor = ColorUtils.setAlphaComponent(colorA, (255 * alphaCompound).toInt())
            checkBox.background = RippleDrawable(ColorStateList.valueOf(rippleColor), null, null)
        }
    }

    fun themeRadioButton(radioButton: RadioButton, @ColorInt colorActive: Int? = null, @ColorInt colorInactive: Int? = null) {
        val colorA = colorActive ?: colorRadioActive.intColor
        val colorI = colorInactive ?: colorRadioInactive.intColor

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
            ), intArrayOf(
                ColorUtils.setAlphaComponent(colorActive, (255 * disabledAlpha).toInt()),
                ColorUtils.setAlphaComponent(colorInactive, (255 * disabledAlpha).toInt()),
                colorActive,
                colorInactive
            )
        )
    }

    fun themeSwitch(switch: CompoundButton) {
        val checkedFront = colorSwitchActive.intColor
        val uncheckedFront = colorSwitchInactive.intColor
        val colorStateTrack = getCompoundColors(checkedFront, uncheckedFront)

        val checkedBack = ColorUtils.setAlphaComponent(checkedFront, (255 * alphaCompound).toInt())
        val uncheckedBack = ColorUtils.setAlphaComponent(colorText.intColor, (255 * alphaCompound).toInt())
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


    //call this after dialog.show() to apply theme to alertDialog
    fun themeAlertDialog(alertDialog: AlertDialog){
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(colorAlertBackground.intColor))

        val message = alertDialog.findViewById<TextView>(android.R.id.message)
        message?.setTextColor(ColorStateList.valueOf(colorAlertMessage.intColor))

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(colorAlertButtons.intColor)
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(colorAlertButtons.intColor)
        alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL)?.setTextColor(colorAlertButtons.intColor)

        val icon = alertDialog.findViewById<View>(android.R.id.icon)
        (icon as? ImageView)?.drawable?.apply {
            ThemeUtils.tintDrawable(this, colorAlertIcon.intColor)
        }

        val title = findAlertTitle(icon?.parent as? ViewGroup)
        title?.setTextColor(ColorStateList.valueOf(colorAlertTitle.intColor))
    }

    private fun findAlertTitle(group: ViewGroup?): TextView? {
        if(group == null) return null
        for (i in 0 until group.childCount) when (val v = group.getChildAt(i)) {
            is TextView -> return v
            is ViewGroup -> findAlertTitle(v)
        }
        return null
    }

    fun themeToolbar(toolbar: Toolbar) {
        themeViewBack(toolbar, colorActionBar.intColor)
        themeAllMenuIcons(toolbar.menu, colorActionBarIcons.intColor)

        toolbar.navigationIcon = ThemeUtils.tintDrawable(toolbar.navigationIcon, colorActionBarIcons.intColor)
        toolbar.setTitleTextColor(colorActionBarText.intColor)
        toolbar.setSubtitleTextColor(colorActionBarTextSecondary.intColor)
    }

    open fun themeAllMenuIcons(menu: Menu, @ColorInt color: Int? = null) {
        val iconColor = color ?: colorActionBarIcons.intColor
        for (i in 0 until menu.size()) {
            val item = menu.getItem(i)
            themeMenuItem(item, iconColor)
            if(item.hasSubMenu()){
                themeAllMenuIcons(item.subMenu, iconColor)
            }
        }
    }

    fun themeMenuItem(item: MenuItem, color: Int?) {
        val iconColor = color ?: colorActionBarIcons.intColor
        item.icon?.let {
            val tintedDrawable = ThemeUtils.tintDrawable(it, iconColor)
            item.icon = tintedDrawable
        }
        item.actionView
            ?.findViewById<View?>(R.id.expand_activities_button)
            ?.findViewById<ImageView?>(R.id.image)
            ?.let {
                if (it.drawable != null) {
                    it.setImageDrawable(ThemeUtils.tintDrawable(it.drawable, iconColor))
                }
            }
    }

}
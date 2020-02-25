package com.ivan200.theminglib

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
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.ivan200.theminglib.ThemeUtils.createStateDrawable
import com.ivan200.theminglib.ThemeUtils.dpToPx
import com.ivan200.theminglib.ThemeUtils.isColorBright
import com.ivan200.theminglib.ThemeUtils.setBackgroundColorSavePadding
import com.ivan200.theminglib.ThemeUtils.setBackgroundResourceSavePadding
import com.ivan200.theminglib.ThemeUtils.setBackgroundSavePadding
import com.ivan200.theminglib.ThemeUtils.spToPx
import java.util.*
import kotlin.math.sqrt


//
// Created by Ivan200 on 28.11.2019.
//
@Suppress("MemberVisibilityCanBePrivate")
abstract class ThemingBase {
    //base colors
    /** Основной цвет подсветки **/
    open val colorPrimary = Color.parseColor("#54a9ff")
    /** Цвет фона **/
    open val colorBackground = Color.parseColor("#303030")

    //additional colors
    /** Цвет хорошего результата **/
    open val colorFine = Color.parseColor("#4CAF50")
    /** Цвет плохого результата **/
    open val colorError = Color.parseColor("#D50000")

    //System components
    /** Цвет статусбара **/
    open val colorStatusBar: Int get() = Color.BLACK
    /** Цвет статусбара на апи 21-22, где текст на статусбаре всегда белый **/
    open val colorStatusBarDark: Int get() = Color.BLACK
    /** Отрисовывать ли системную плашку под статусбаром если его цвет прозрачный **/
    open val statusDrawSystemBar: Boolean get() = true
    /** Скзать системе что статусбар светлый, чтобы значки стали чёрного цвета **/
    open val statusLightTheme: Boolean get() = isColorBright(colorStatusBar)

    /** Цвет нижнего навбара **/
    open val colorNavBar: Int get() = Color.BLACK
    /** Цвет нижнего навбара на апи 21-25, где кнопки всегда белые **/
    open val colorNavBarDark: Int get() = Color.BLACK
    /** Отрисовывать ли системную плашку под навбаром если его цвет прозрачный **/
    open val navBarDrawSystemBar: Boolean get() = true
    /** Скзать системе что навбар светлый, чтобы значки стали чёрного цвета **/
    open val navBarLightTheme: Boolean get() = isColorBright(colorNavBar)
    /** Цвет разделителя нижнего навбара, включается на апи 28+ **/
    open val colorNavBarDivider: Int get() = Color.TRANSPARENT

    /** Цвет подсветки ресайклервью и листвью при скролле на апи<21 **/
    open val colorOverScroll: Int get() = colorPrimary

    //Text
    /** Цвет текста **/
    open val colorText: Int get() = getTextColor(colorBackground)
    /** Цвет текста **/
    open val colorTextStateList: ColorStateList get() = getTextStateList(colorText)
    /** Цвет хинта текста **/
    open val colorTextHint: Int get() = getHintColor(colorText)

    //Input
    /** Цвет иконки глаза скрывающей пароль **/
    open val colorInputPasswordEye: Int get() = colorTextHint
    /** Цвет подсвечиваемого помощника **/
    open val colorInputHelper: Int get() = colorFine
    /** Цвет подсвечиваемой ошибки **/
    open val colorInputError: Int get() = colorError
    /** Цвет верхней надписи при активации данного поля ввода **/
    open val colorInputHintFocused: Int get() = colorPrimary
    /** Цвет хинта вводимого текста **/
    open val colorInputHint: Int get() = colorTextHint
    /** Цвет вводимого текста **/
    open val colorInputText: ColorStateList get() = colorTextStateList
    /** Цвет полоски под полем ввода **/
    open val colorInputBottomLine: Int get() = colorPrimary
    /** Цвет моргающего курсора **/
    open val colorInputCursor: Int get() = colorPrimary
    /** Цвет выделения текста **/
    open val colorInputHighlight: Int get() = colorPrimary
    /** Цвет захватов при выделении  текста **/
    open val colorInputHandles: Int get() = colorPrimary

    //ProgressBar
    /** Цвет прогрессбара **/
    open val colorProgressBar: Int get() = colorPrimary
    /** Цвет второго прогрессбара **/
    open val colorProgressBarSecondary: Int get() = ColorUtils.setAlphaComponent(colorProgressBar, (255 * getDisabledAlpha(isLightTheme)).toInt())
    /** Цвет фона прогрессбара **/
    open val colorProgressBarBackground: Int get() = getSecondaryTextColor(colorText)
    //SeekBar
    /** Цвет ручки сикбара **/
    open val colorSeekBarThumb: Int get() = colorProgressBar
    /** Цвет точек на сикбаре с делениями **/
    open val colorSeekBarTickMark: Int get() = colorProgressBarBackground


    //Button
    /** Цвет фона кнопки **/
    open val colorButtonBackground: Int get() = colorPrimary
    /** Цвет текста кнопки **/
    open val colorButtonText: Int get() = getTextColor(colorButtonBackground)

    //Checkbox
    /** Цвет чекбокса **/
    open val colorCheckBoxActive: Int get() = colorPrimary
    /** Цвет неактивированного чекбокса **/
    open val colorCheckBoxInactive: Int get() = colorTextHint

    //RadioButton
    /** Цвет радиокнопки **/
    open val colorRadioActive: Int get() = colorPrimary
    /** Цвет неактивированной радиокнопки **/
    open val colorRadioInactive: Int get() = colorTextHint

    //Switch
    /** Цвет переключателя **/
    open val colorSwitchActive: Int get() = colorPrimary
    /** Цвет неактивированного переключателя **/
    open val colorSwitchInactive: Int get() = Color.parseColor(if(isLightTheme) "#ececec" else "#b9b9b9")

    //FloatingActionButton
    /** Цвет фона fab кнопки **/
    open val colorFabBackground: Int get() = colorPrimary
    /** Цвет иконки на fab кнопке **/
    open val colorFabIcon: Int get() = colorText

    //AlertDialog
    /** Цвет фона диалога **/
    open val colorAlertBackground: Int get() = colorBackground
    /** Цвет заголовка диалога **/
    open val colorAlertTitle: Int get() = colorText
    /** Цвет иконки диалога **/
    open val colorAlertIcon: Int get() = colorText
    /** Цвет текста диалога **/
    open val colorAlertMessage: Int get() = colorText
    /** Цвет кнопок  диалога **/
    open val colorAlertButtons: Int get() = colorPrimary

    //ImageView
    /** Цвет иконок **/
    open val colorIcon: Int get() = colorText

    //ActionBar
    /** Цвет верхнего экшенбара **/
    open val colorActionBar: Int get() = colorPrimary
    /** Цвет текста верхнего экшенбара **/
    open val colorActionBarText: Int get() = getTextColor(colorActionBar)
    /** Цвет второго текста верхнего экшенбара **/
    open val colorActionBarTextSecondary: Int get() = getSecondaryTextColor(colorActionBarText)
    /** Цвет иконок верхнего экшенбара **/
    open val colorActionBarIcons: Int get() = colorActionBarText

    //BottomNavigationView
    /** Цвет фона нижнего таббара **/
    open val colorBottomNavBackground: Int get() = colorBackground
    /** Цвет иконок нижнего таббара **/
    open val colorBottomNavIcon: Int get() = getSecondaryTextColor(colorText)
    /** Цвет иконки выбранной вкладки нижнего таббара **/
    open val colorBottomNavIconSelected: Int get() = colorPrimary
    /** Цвет текста нижнего таббара **/
    open val colorBottomNavText: Int get() = getSecondaryTextColor(colorText)
    /** Цвет текста выбранной вкладки нижнего таббара **/
    open val colorBottomNavTextSelected: Int get() = colorPrimary

    val isLightTheme: Boolean get() = isColorBright(colorBackground)
    private val alphaCompound = 0.3

    //Вызывать внутри onCreate
    fun themeActivity(activity: AppCompatActivity){
        themeStatusBar(activity.window)
        themeNavigationBar(activity.window)
        themeOverScrollGlowColor(activity.resources, colorOverScroll)
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
        val colorStatus = color ?: colorStatusBar
        val colorStatusDark = colorDark ?: colorStatusBarDark
        val isLightTheme =  lightTheme ?: statusLightTheme
        val drawBar = drawSystemBar ?: statusDrawSystemBar

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setWindowFlag(window, WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS, true)
        }
        if(colorStatus == Color.TRANSPARENT){
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

        val colorNav = color ?: colorNavBar
        val colorNavDark = colorDark ?: colorNavBarDark
        val colorDiv = colorDivider ?: colorNavBarDivider
        val isLightTheme =  lightTheme ?: navBarLightTheme
        val drawBar = drawSystemBar ?: navBarDrawSystemBar

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setWindowFlag(window, WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS, true)
        }

        if(colorNav == Color.TRANSPARENT) {
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

    fun setWindowFlag(win: Window, bits: Int, state: Boolean) {
        val flags = win.attributes.flags
        win.attributes.flags = if (state) flags or bits else flags and bits.inv()
    }

    fun setDecorFlag(win: Window, bits: Int, state: Boolean) {
        val flags = win.decorView.systemUiVisibility
        win.decorView.systemUiVisibility = if (state) flags or bits else flags and bits.inv()
    }


//    //Перекрашивание нижнего навигейшнбара
//    fun themeNavigationBar(window: Window) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val flagLightNavBar = if (isLightTheme) View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR else View.SYSTEM_UI_FLAG_VISIBLE
//            window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
//                    WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS or
//                    flagLightNavBar
//            window.navigationBarColor = colorNavBar
//        } else if (Build.VERSION.SDK_INT >= 21) {
//            window.navigationBarColor = colorNavBarDark
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            window.navigationBarDividerColor = colorNavBarDivider
//        }
//    }

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
            is AppCompatCheckBox -> themeCheckBox(v)
            is AppCompatRadioButton -> themeRadioButton(v)
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

    fun themeViewBack(layout: View, @ColorInt color: Int? = null) {
        setBackgroundColorSavePadding(layout, color ?: colorBackground)
    }

    fun themeViewAction(view: View, @ColorInt color: Int? = null) {
        setBackgroundColorSavePadding(view, color ?: colorBackground)
    }

    fun themeToolbar(toolbar: Toolbar) {
        themeViewAction(toolbar, colorActionBar)
        for (i in 0 until toolbar.childCount) when (val v = toolbar.getChildAt(i)) {
            is TextView -> themeTextView(v, colorActionBarText)
            is ImageView -> themeImageView(v, colorActionBarIcons)
        }
        toolbar.solidColor
    }

    fun themeSeekBar(seek: AppCompatSeekBar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            seek.tickMarkTintList = ColorStateList.valueOf(colorProgressBar)
            seek.thumbTintList = ColorStateList.valueOf(colorProgressBar)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            seek.thumb = ThemeUtils.tintDrawable(seek.thumb, colorProgressBar)
        }
        themeProgressBar(seek)
    }

    fun themeSeekBar(seek: SeekBar) {
        try {
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                    seek.tickMarkTintList = ColorStateList.valueOf(colorSeekBarTickMark)
                }
                seek is AppCompatSeekBar -> {
                    modifyPrivateFieldThroughEditor<Drawable>(
                        seek, AppCompatSeekBar::class.java,
                        "mAppCompatSeekBarHelper", "mTickMark"
                    ) {
                        it?.apply { ThemeUtils.tintDrawable(this, colorSeekBarTickMark)}
                    }
                }
                else -> {
                    modifyPrivateField<Drawable>(seek, AbsSeekBar::class.java, "mTickMark") {
                        it?.apply {ThemeUtils.tintDrawable(this, colorSeekBarTickMark)}
                    }
                }
            }
        } catch (t: Throwable) {
            t.printStackTrace()
        }
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                seek.thumbTintList = ColorStateList.valueOf(colorSeekBarThumb)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN -> {
                seek.thumb = ThemeUtils.tintDrawable(seek.thumb, colorSeekBarThumb)
            }
            else -> {
                modifyPrivateField<Drawable>(seek, AbsSeekBar::class.java, "mThumb") {
                    it?.apply {ThemeUtils.tintDrawable(this, colorSeekBarThumb)}
                }
            }
        }
        themeProgressBar(seek)
    }

    fun <T> modifyPrivateFieldThroughEditor(
        obj: Any,
        objClass: Class<*>,
        editorName: String,
        fieldName: String,
        modify: (T?) -> T?
    ) {
        try {
            objClass
                .getDeclaredField(editorName)
                .apply { isAccessible = true }
                .get(obj)
                ?.apply { modifyPrivateField(this, this::class.java, fieldName, modify) }
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    fun <T> modifyPrivateField(obj: Any, clazz: Class<*>, fieldName: String, modify: (T?) -> T?) {
        try {
            clazz
                .getDeclaredField(fieldName)
                .apply { isAccessible = true }
                .run {
                    val field = try { get(obj) as T? } catch (ex: NullPointerException) { null }
                    set(obj, modify(field))
                }
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    fun <T> getPrivateFieldOrNull(obj: Any, clazz: Class<*>, fieldName: String): T? {
        var result: T? = null
        try {
            clazz.getDeclaredField(fieldName)
                .apply { isAccessible = true }
                .run {
                    result = try {
                        get(obj) as T?
                    } catch (ex: NullPointerException) {
                        null
                    }
                }
        } catch (t: Throwable) {
            t.printStackTrace()
        }
        return result
    }

    @Suppress("DEPRECATION")
    fun themeProgressBar(progress: ProgressBar) {
        val color = colorProgressBar
        val colorSecondary = Color.WHITE
//            colorProgressBarSecondary
        val colorBackground = colorProgressBarBackground


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if(progress.isIndeterminate){
                progress.indeterminateTintList = ColorStateList.valueOf(color)
            } else {
                progress.progressTintList = ColorStateList.valueOf(color)
                progress.progressBackgroundTintList = ColorStateList.valueOf(colorText)

                val colorRipple = ColorUtils.setAlphaComponent(colorText, (255 * alphaCompound).toInt())
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
                    ?.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
                findDrawableByLayerId(android.R.id.background)?.mutate()
                    ?.setColorFilter(colorBackground, PorterDuff.Mode.SRC_ATOP)
            }
            (progress.indeterminateDrawable as? AnimationDrawable)?.apply {
                setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
            }
        }
    }

    fun themeEditText(editText: AppCompatEditText){
        editText.supportBackgroundTintList = ColorStateList.valueOf(colorInputBottomLine)   //нижняя полоска
        setCursorDrawableColor(editText, colorInputCursor)          //моргающий курсор
        setHandlesColor(editText, colorInputHandles)                //Захваты выделения

        editText.setTextColor(colorInputText)
        editText.setHintTextColor(colorInputHint)
        editText.highlightColor = colorInputHighlight
    }

    fun themeTextView(textView: TextView, @ColorInt color: Int? = null, isSecondary: Boolean = false) {
        textView.setTextColor(getTextStateList(color ?: colorText, isSecondary))
    }

    fun themeFab(fab: FloatingActionButton){
        fab.supportBackgroundTintList = ColorStateList.valueOf(colorFabBackground)
        fab.supportImageTintList = ColorStateList.valueOf(colorFabIcon)

        val pressedRippleColors = getPressedRippleColors(colorFabBackground)
        fab.supportBackgroundTintList = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_pressed),
                intArrayOf(-android.R.attr.state_pressed)
            ), intArrayOf(
                pressedRippleColors.first,
                colorFabBackground
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
        ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(color ?: colorIcon))
    }

    fun themeIcon(imageView: ImageView){
        ImageViewCompat.setImageTintList(imageView, getTextStateList(colorIcon))
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
                getPressedColor(colorBottomNavIconSelected),
                colorBottomNavIconSelected,
                colorBottomNavIcon
            )
        )

        bottomNavigationView.itemTextColor = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_pressed),
                intArrayOf(android.R.attr.state_selected),
                intArrayOf(-android.R.attr.state_selected)
            ), intArrayOf(
                getPressedColor(colorBottomNavTextSelected),
                colorBottomNavTextSelected,
                colorBottomNavText
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

        setBackgroundColorSavePadding(bottomNavigationView, colorBottomNavBackground)
    }

    fun themeCellBg(view: View, bgColor: Int? = null) {
        val color = bgColor ?: colorBackground

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
        themeButtonText(button, textColor ?: colorPrimary, true)

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
        val tColor = textColor ?: if (bgColor != null) getTextColor(bgColor) else colorButtonText
        themeButtonText(button, tColor, false)

        val buttonColor = bgColor ?: colorButtonBackground
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
        textInputLayout.setHelperTextColor(ColorStateList.valueOf(colorInputHelper))     //Хелпер
        textInputLayout.setErrorTextColor(ColorStateList.valueOf(colorInputError))       //Подсветка ошибок
        textInputLayout.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(colorInputPasswordEye))   //Глаз сбоку от пароля
        textInputLayout.setEndIconTintList(ColorStateList.valueOf(colorInputPasswordEye))   //Глаз сбоку от пароля

        val states = arrayOf(
            intArrayOf(android.R.attr.state_focused),
            intArrayOf(-android.R.attr.state_focused)
        )

        textInputLayout.defaultHintTextColor = ColorStateList(states,
            intArrayOf(colorInputHintFocused, colorInputHint))    //Верхняя надпись, Хинт
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
                getPrivateFieldOrNull<Int>(editText, TextView::class.java, "mCursorDrawableRes")
                    ?.let { ContextCompat.getDrawable(editText.context, it) ?: return }
                    ?.let { ThemeUtils.tintDrawable(it, color) } ?: return

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                modifyPrivateField<Drawable?>(editor, editorClass, "mDrawableForCursor"){
                    tintedCursorDrawable
                }
            } else {
                modifyPrivateField<Array<Drawable?>?>(editor, editorClass, "mDrawableForCursor"){
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
                modifyPrivateField<Drawable?>(editor, editorClass, handleNames[i]) {
                    val img = it
                        ?: getPrivateFieldOrNull<Int>(textView, TextView::class.java, resNames[i])
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
        val isTextColorDark = !isColorBright(
            textColor
        )
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

    fun themeCheckBox(checkBox: AppCompatCheckBox, @ColorInt colorActive: Int? = null, @ColorInt colorInactive: Int? = null) {
        val colorA = colorActive ?: colorCheckBoxActive
        val colorI = colorInactive ?: colorCheckBoxInactive
        checkBox.supportButtonTintList = getCompoundColors(colorA, colorI)
        checkBox.setTextColor(colorTextStateList)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val rippleColor = ColorUtils.setAlphaComponent(colorA, (255 * alphaCompound).toInt())
            checkBox.background = RippleDrawable(ColorStateList.valueOf(rippleColor), null, null)
        }
    }

    fun themeRadioButton(radioButton: AppCompatRadioButton, @ColorInt colorActive: Int? = null, @ColorInt colorInactive: Int? = null) {
        val colorA = colorActive ?: colorRadioActive
        val colorI = colorInactive ?: colorRadioInactive

        radioButton.supportButtonTintList = getCompoundColors(colorA, colorI)
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

    fun themeSwitch(switchCompat: SwitchCompat) {
        val checkedFront = colorSwitchActive
        val uncheckedFront = colorSwitchInactive
        val colorStateTrack = getCompoundColors(checkedFront, uncheckedFront)

        val checkedBack = ColorUtils.setAlphaComponent(checkedFront, (255 * alphaCompound).toInt())
        val uncheckedBack = ColorUtils.setAlphaComponent(colorText, (255 * alphaCompound).toInt())
        val colorStateThumb = getCompoundColors(checkedBack, uncheckedBack)

        switchCompat.thumbTintList = colorStateTrack
        switchCompat.trackTintList = colorStateThumb

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            switchCompat.backgroundTintList = colorStateThumb
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            switchCompat.background = RippleDrawable(ColorStateList.valueOf(checkedBack), null, null)
        }

        switchCompat.setTextColor(colorTextStateList)
    }


    //call this after dialog.show() to apply theme to alertDialog
    fun themeAlertDialog(alertDialog: AlertDialog){
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(colorAlertBackground))

        val message = alertDialog.findViewById<TextView>(android.R.id.message)
        message?.setTextColor(ColorStateList.valueOf(colorAlertMessage))

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(colorAlertButtons)
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(colorAlertButtons)
        alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL)?.setTextColor(colorAlertButtons)

        val icon = alertDialog.findViewById<View>(android.R.id.icon)
        (icon as? ImageView)?.drawable?.apply {
            ThemeUtils.tintDrawable(this, colorAlertIcon)
        }

        val title = findAlertTitle(icon?.parent as? ViewGroup)
        title?.setTextColor(ColorStateList.valueOf(colorAlertTitle))
    }

    private fun findAlertTitle(group: ViewGroup?): TextView? {
        if(group == null) return null
        for (i in 0 until group.childCount) when (val v = group.getChildAt(i)) {
            is TextView -> return v
            is ViewGroup -> findAlertTitle(v)
        }
        return null
    }

    open fun themeAllMenuIcons(menu: Menu, @ColorInt color: Int? = null) {
        val iconColor = color?: colorActionBarIcons
        for (i in 0 until menu.size()) {
            val item = menu.getItem(i)
            item.icon?.let {
                val tintedDrawable = ThemeUtils.tintDrawable(it, iconColor)
                item.icon = tintedDrawable
            }
            item.actionView
                ?.findViewById<View>(R.id.expand_activities_button)
                ?.let { it.findViewById<View>(R.id.image) as? ImageView }
                ?.let {
                    val tintedDrawable = ThemeUtils.tintDrawable(it.drawable, iconColor)
                    it.setImageDrawable(tintedDrawable)
                }
        }
    }


}
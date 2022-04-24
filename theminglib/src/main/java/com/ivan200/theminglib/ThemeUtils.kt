package com.ivan200.theminglib

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.ColorMatrixColorFilter
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.drawable.*
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.Window
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.FloatRange
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.ivan200.theminglib.ThemeUtils.spToPx
import java.lang.reflect.Field

//
// Created by Ivan200 on 25.02.2020.
//

object ThemeUtils {
    fun Number.spToPx(context: Context? = null): Float {
        val res = context?.resources ?: android.content.res.Resources.getSystem()
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), res.displayMetrics)
    }

    fun Number.dpToPx(context: Context? = null): Float {
        val res = context?.resources ?: android.content.res.Resources.getSystem()
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), res.displayMetrics)
    }

    fun setWindowFlag(win: Window, bits: Int, state: Boolean) {
        val flags = win.attributes.flags
        win.attributes.flags = if (state) flags or bits else flags and bits.inv()
    }

    fun setDecorFlag(win: Window, bits: Int, state: Boolean) {
        val flags = win.decorView.systemUiVisibility
        win.decorView.systemUiVisibility = if (state) flags or bits else flags and bits.inv()
    }

    //Перекрашивание системного ресурса используемого в приложении в определённый цвет
    @Suppress("DEPRECATION")
    fun changeResColor(res: android.content.res.Resources, resId: String, @ColorInt color: Int) {
        try {
            val drawableId = res.getIdentifier(resId, "drawable", "android")
            val drawable = res.getDrawable(drawableId)
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        } catch (ignored: Exception) {
        }
    }

    fun Drawable.tinted(@ColorInt color: Int): Drawable = when {
        this is VectorDrawableCompat -> {
            this.apply { setTintList(ColorStateList.valueOf(color)) }
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && this is VectorDrawable -> {
            this.apply { setTintList(ColorStateList.valueOf(color)) }
        }
        else -> {
            DrawableCompat.wrap(this)
                .also { DrawableCompat.setTint(it, color) }
                .let { DrawableCompat.unwrap(it) }
        }
    }

    fun tintDrawableWithMatrix(drawable: Drawable, @ColorInt color: Int) {
        val r = Color.red(color)
        val g = Color.green(color)
        val b = Color.blue(color)
        val x = (255f - r) / 255f
        val y = (255f - g) / 255f
        val z = (255f - b) / 255f
        val rx = 0.3f
        val gy = 0.6f
        val bz = 0.1f
        val offset = 25f

        val matrix = floatArrayOf(
            rx * x, gy * x, bz * x, 0f, r - offset,
            rx * y, gy * y, bz * y, 0f, g - offset,
            rx * z, gy * z, bz * z, 0f, b - offset,
            0f, 0f, 0f, 1f, 0f)

        val mFilter = ColorMatrixColorFilter(matrix)
        drawable.colorFilter = mFilter
    }

    fun getColorBrightness(@ColorInt color: Int): Double {
        val outLab = DoubleArray(3)
        ColorUtils.colorToLAB(color, outLab)
        return outLab[0]
    }

    fun isColorBright(@ColorInt color: Int) = getColorBrightness(color) > 50


    @ColorInt
    fun setColorBrightness(@ColorInt color: Int, @FloatRange(from = 0.0, to = 100.0) brightness: Double): Int {
        val outLab = DoubleArray(3)
        ColorUtils.colorToLAB(color, outLab)
        outLab[0] = brightness
        val newColor = ColorUtils.LABToColor(outLab[0], outLab[1], outLab[2])
        return if (Color.alpha(color) == 255) {
            newColor
        } else {
            Color.argb(Color.alpha(color), Color.red(newColor), Color.green(newColor), Color.blue(newColor))
        }
    }

    //Обращение яркости цвета, для нормального использования на тёмной теме цветов светлой
    @ColorInt
    fun invertColorBrightness(@ColorInt color: Int): Int {
        return setColorBrightness(color, 100 - getColorBrightness(color))
    }

    //Получение id ресурса, привязаного к теме через аттрибуты, например android.R.attr.selectableItemBackground
    fun getAttrResCompat(context: Context, @AttrRes id: Int): Int {
        return TypedValue()
            .also { context.theme.resolveAttribute(id, it, true) }
            .let { if (it.resourceId != 0) it.resourceId else it.data }
    }


    //Получение drawable ресурса, привязаного к теме через аттрибуты, например android.R.attr.selectableItemBackground
    fun getDrawableResCompat(context: Context, @AttrRes id: Int): Drawable? {
        return ContextCompat.getDrawable(context, getAttrResCompat(context, id))
    }

    @Suppress("unused")
    fun isNightModeActive(context: Context): Boolean {
        val i = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return i == Configuration.UI_MODE_NIGHT_YES
    }

    fun setBackgroundSavePadding(view: View, background: Drawable){
        resetPaddingWith(view){
            ViewCompat.setBackground(it, background)
        }
    }

    fun setBackgroundResourceSavePadding(view: View, @DrawableRes backgroundResId: Int){
        resetPaddingWith(view){
            it.setBackgroundResource(backgroundResId)
        }
    }

    fun setBackgroundColorSavePadding(view: View, @ColorInt color: Int){
        resetPaddingWith(view){
            it.setBackgroundColor(color)
        }
    }

    fun resetPaddingWith(view: View, func: (View)-> Unit = {}){
        val paddingBottom = view.paddingBottom
        val paddingStart = ViewCompat.getPaddingStart(view)
        val paddingEnd = ViewCompat.getPaddingEnd(view)
        val paddingTop = view.paddingTop
        func(view)
        ViewCompat.setPaddingRelative(view, paddingStart, paddingTop, paddingEnd, paddingBottom)
    }


    /**
     * Creates a new `StateListDrawable` drawable. States that should be provided are "normal",<br></br>
     * "clicked" (pressed) and "checked" (selected). All states are actually integer colors.<br></br>
     * Optionally, `shouldFade` can be set to false to avoid the fading effect.<br></br>
     * <br></br>
     * Note: *[Color.TRANSPARENT] can be used to supply a transparent state.*
     *
     * @param normal Color for the idle state
     * @param clicked Color for the clicked/pressed state
     * @param checked Color for the checked/selected state
     * @param shouldFade Set to true to enable the fading effect, false otherwise
     * @return A [StateListDrawable] drawable object ready for use
     */
    @SuppressLint("InlinedApi", "NewApi")
    fun createStateDrawable(
        @ColorInt normal: Int,
        @ColorInt clicked: Int,
        @ColorInt disabled: Int? = null,
        @ColorInt checked: Int = clicked,
        shouldFade: Boolean = true,
        cornerRadius: Float = 0f,
        padding: Rect? = null
    ): Drawable { // init state arrays
        val fadeDuration = 150

        val normalDrawable = getGrDrawable(normal, cornerRadius, padding)
        val clickedDrawable = getGrDrawable(clicked, cornerRadius, padding)
        val checkedDrawable = getGrDrawable(checked, cornerRadius, padding)
        val focusedDrawable = getGrDrawable(normal, cornerRadius, padding)

        // prepare state list (order of adding states is important!)
        val states = StateListDrawable()
        if(disabled != null){
            val disabledDrawable = getGrDrawable(disabled, cornerRadius, padding)
            states.addState(intArrayOf(-android.R.attr.state_enabled), disabledDrawable)
        }
        states.addState(intArrayOf(android.R.attr.state_pressed), clickedDrawable)
        if (!shouldFade) {
            states.addState(intArrayOf(android.R.attr.state_selected), clickedDrawable)
            states.addState(intArrayOf(android.R.attr.state_focused), focusedDrawable)
            states.addState(intArrayOf(android.R.attr.state_checked), checkedDrawable)
        }
        // add fade effect if applicable
        if (shouldFade) {
            states.addState(intArrayOf(), normalDrawable)
            states.setEnterFadeDuration(0)
            states.setExitFadeDuration(fadeDuration)
        } else {
            states.addState(intArrayOf(android.R.attr.state_activated), clickedDrawable)
            states.addState(intArrayOf(), normalDrawable)
        }
        return states
    }

    fun getGrDrawable(@ColorInt color: Int, cornerRadius: Float = 0f, insets: Rect? = null): Drawable {
        val bounds = 1500

        return GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(color, color))
            .mutate()
            .let { it as GradientDrawable }
            .apply {
                if (cornerRadius > 0) {
                    this.cornerRadius = cornerRadius
                }

                if (color == Color.TRANSPARENT) alpha = 0
                else setBounds(bounds, bounds, bounds, bounds)
            }
            .let {
                insets?.run {
                    InsetDrawable(it, left, top, right, bottom)
                } ?: it
            }
    }

    fun <T> modifyPrivateFieldThroughEditor(obj: Any, objClass: Class<*>, editorName: String, fieldName: String, modify: (T?) -> T?) {
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

//
//    fun <T> getPrivateFieldOrNull(obj: Any, clazz: Class<*>, fieldName: String): T? {
//        var result: T? = null
//        try {
//            clazz.getDeclaredField(fieldName)
//                .apply { isAccessible = true }
//                .run {
//                    result = try {
//                        get(obj) as T?
//                    } catch (ex: NullPointerException) {
//                        null
//                    }
//                }
//        } catch (t: Throwable) {
//            t.printStackTrace()
//        }
//        return result
//    }

    fun Class<*>.getFieldByName(vararg name: String): Field? {
        name.forEach {
            try{
                return this.getDeclaredField(it).apply { isAccessible = true }
            } catch (t: Throwable) { }
        }
        return null
    }
}
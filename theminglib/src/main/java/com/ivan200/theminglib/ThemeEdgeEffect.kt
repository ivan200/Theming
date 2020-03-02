package com.ivan200.theminglib

import android.annotation.TargetApi
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.AbsListView
import android.widget.EdgeEffect
import android.widget.HorizontalScrollView
import android.widget.ScrollView
import androidx.annotation.ColorInt
import androidx.core.widget.EdgeEffectCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import java.lang.reflect.Field


//
// Created by Ivan200 on 02.03.2020.
//

object ThemeEdgeEffect {
    private val FIELD_SCROLL_TOP by lazy {
        getPrivateFieldOrNull(ScrollView::class.java, "mEdgeGlowTop")
    }
    private val FIELD_SCROLL_BOTTOM by lazy {
        getPrivateFieldOrNull(ScrollView::class.java, "mEdgeGlowBottom")
    }

    private val FIELD_PAGER_LEFT by lazy {
        getPrivateFieldOrNull(ViewPager::class.java, "mLeftEdge")
    }
    private val FIELD_PAGER_RIGHT by lazy {
        getPrivateFieldOrNull(ViewPager::class.java, "mRightEdge")
    }

    private val FIELD_HORIZONTAL_LEFT by lazy {
        getPrivateFieldOrNull(HorizontalScrollView::class.java, "mEdgeGlowLeft")
    }
    private val FIELD_HORIZONTAL_RIGHT by lazy {
        getPrivateFieldOrNull(HorizontalScrollView::class.java, "mEdgeGlowRight")
    }

    private val FIELD_NESTED_TOP by lazy {
        getPrivateFieldOrNull(NestedScrollView::class.java, "mEdgeGlowTop")
    }
    private val FIELD_NESTED_BOTTOM by lazy {
        getPrivateFieldOrNull(NestedScrollView::class.java, "mEdgeGlowBottom")
    }

    private val FIELD_LIST_TOP by lazy {
        getPrivateFieldOrNull(AbsListView::class.java, "mEdgeGlowTop")
    }
    private val FIELD_LIST_BOTTOM by lazy {
        getPrivateFieldOrNull(AbsListView::class.java, "mEdgeGlowBottom")
    }

    private val EDGE_EFFECT_CLASS by lazy {
       when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> null
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH -> EdgeEffect::class.java
            else -> {
                try {
                    Class.forName("android.widget.EdgeGlow")
                } catch (e: ClassNotFoundException) {
                    if (BuildConfig.DEBUG) e.printStackTrace()
                    null
                }
            }
        }
    }

    private val EDGE_GLOW_FIELD_EDGE by lazy {
        getPrivateFieldOrNull(EDGE_EFFECT_CLASS, "mEdge")
    }
    private val EDGE_GLOW_FIELD_GLOW by lazy {
        getPrivateFieldOrNull(EDGE_EFFECT_CLASS, "mGlow")
    }
    private val EDGE_EFFECT_COMPAT_FIELD_EDGE_EFFECT  by lazy {
        getPrivateFieldOrNull(EdgeEffectCompat::class.java, "mEdgeEffect")
    }

    fun setColor(view: Any, @ColorInt color: Int) {
        setColor(view, Rect(color,color,color,color))
    }

    fun setColor(view: Any, colors: Rect) {
        try {
            when(view) {
                is AbsListView ->{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        view.topEdgeEffectColor = colors.top
                        view.bottomEdgeEffectColor = colors.bottom
                    } else {
                        FIELD_LIST_TOP?.get(view)?.apply { setFieldColor(this, colors.top) }
                        FIELD_LIST_BOTTOM?.get(view)?.apply { setFieldColor(this, colors.bottom) }
                    }
                }
                is ScrollView ->{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        view.topEdgeEffectColor = colors.top
                        view.bottomEdgeEffectColor = colors.bottom
                    } else {
                        FIELD_SCROLL_TOP?.get(view)?.apply { setFieldColor(this, colors.top) }
                        FIELD_SCROLL_BOTTOM?.get(view)?.apply { setFieldColor(this, colors.bottom) }
                    }
                }
                is HorizontalScrollView ->{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        view.leftEdgeEffectColor = colors.left
                        view.rightEdgeEffectColor = colors.right
                    } else {
                        FIELD_HORIZONTAL_LEFT?.get(view)?.apply { setFieldColor(this, colors.left) }
                        FIELD_HORIZONTAL_RIGHT?.get(view)?.apply { setFieldColor(this, colors.right) }
                    }
                }
                is ViewPager ->{
                    FIELD_PAGER_LEFT?.get(view)?.apply { setFieldColor(this, colors.left) }
                    FIELD_PAGER_RIGHT?.get(view)?.apply { setFieldColor(this, colors.right) }
                }
                is NestedScrollView ->{
                    FIELD_NESTED_TOP?.get(view)?.apply { setFieldColor(this, colors.top) }
                    FIELD_NESTED_BOTTOM?.get(view)?.apply { setFieldColor(this, colors.bottom) }
                }
                is RecyclerView -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        view.edgeEffectFactory = object : RecyclerView.EdgeEffectFactory() {
                            override fun createEdgeEffect(view: RecyclerView, direction: Int): EdgeEffect {
                                return EdgeEffect(view.context).apply {
                                    when(direction){
                                        DIRECTION_LEFT ->  this.color = colors.left
                                        DIRECTION_TOP ->  this.color = colors.top
                                        DIRECTION_BOTTOM ->  this.color = colors.bottom
                                        DIRECTION_RIGHT ->  this.color = colors.right
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (ex: Exception) {
            if (BuildConfig.DEBUG)
                ex.printStackTrace()
        }
    }


    private fun getPrivateFieldOrNull(clazz: Class<*>?, fieldName: String): Field? {
        if(clazz == null) return null
        var field: Field? = null
        try {
            clazz.getDeclaredField(fieldName)
                .apply { isAccessible = true }
                .run {
                    field = this
                }
        } catch (e: Throwable) {
            if (BuildConfig.DEBUG) e.printStackTrace()
        }
        return field
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setFieldColor(forObject: Any?, @ColorInt color: Int) {
        var edgeEffect = forObject
        if (edgeEffect is EdgeEffectCompat) { // EdgeEffectCompat
            EDGE_EFFECT_COMPAT_FIELD_EDGE_EFFECT?.apply {
                edgeEffect = try { this[edgeEffect] } catch (e: Exception) { return }
            }
        }
        if (edgeEffect == null) return
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) { // EdgeGlow or old EdgeEffect
            try {
                (EDGE_GLOW_FIELD_EDGE?.get(edgeEffect) as? Drawable)?.apply {
                    ThemeUtils.tintDrawable(this, color)
                    callback = null // free up any references
                }
                (EDGE_GLOW_FIELD_GLOW?.get(edgeEffect) as? Drawable)?.apply {
                    ThemeUtils.tintDrawable(this, color)
                    callback = null // free up any references
                }
            } catch (ex: Exception) {
                if (BuildConfig.DEBUG)
                    ex.printStackTrace()
            }
        } else { // EdgeEffect
            (edgeEffect as? EdgeEffect)?.color = color
        }
    }

    //Перекрашивание цвета оверскролла на всех RecyclerView на api<21. Достаточно вызвать 1 раз в onCreate приложения
    fun themeOverScrollGlowColor(res: android.content.res.Resources, @ColorInt color: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            ThemeUtils.changeResColor(res, "overscroll_glow", color)
            ThemeUtils.changeResColor(res, "overscroll_edge", color)
        }
    }

}
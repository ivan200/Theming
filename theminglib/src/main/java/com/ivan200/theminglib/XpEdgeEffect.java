package com.ivan200.theminglib;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.AbsListView;
import android.widget.EdgeEffect;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

import androidx.annotation.ColorInt;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.core.widget.EdgeEffectCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

/**
 * @author Eugen on 11. 2. 2016.
 */
@SuppressLint("PrivateApi")
public final class XpEdgeEffect {
    private XpEdgeEffect() {
        throw new AssertionError("No instances!");
    }

    private static Class<ScrollView> CLASS_SCROLL_VIEW = ScrollView.class;
    private static Field SCROLL_VIEW_FIELD_EDGE_GLOW_TOP;
    private static Field SCROLL_VIEW_FIELD_EDGE_GLOW_BOTTOM;

    private static Class<ViewPager> CLASS_VIEW_PAGER = ViewPager.class;
    private static Field VIEW_PAGER_FIELD_LEFT_EDGE;
    private static Field VIEW_PAGER_FIELD_RIGHT_EDGE;

    private static Class<HorizontalScrollView> CLASS_HORIZONTAL_SCROLL_VIEW = HorizontalScrollView.class;
    private static Field HORIZONTAL_SCROLL_VIEW_FIELD_EDGE_GLOW_LEFT;
    private static Field HORIZONTAL_SCROLL_VIEW_FIELD_EDGE_GLOW_RIGHT;

    private static Class<NestedScrollView> CLASS_NESTED_SCROLL_VIEW = NestedScrollView.class;
    private static Field NESTED_SCROLL_VIEW_FIELD_EDGE_GLOW_TOP;
    private static Field NESTED_SCROLL_VIEW_FIELD_EDGE_GLOW_BOTTOM;

    private static Class<RecyclerView> CLASS_RECYCLER_VIEW = RecyclerView.class;
    private static Field RECYCLER_VIEW_FIELD_EDGE_GLOW_TOP;
    private static Field RECYCLER_VIEW_FIELD_EDGE_GLOW_LEFT;
    private static Field RECYCLER_VIEW_FIELD_EDGE_GLOW_RIGHT;
    private static Field RECYCLER_VIEW_FIELD_EDGE_GLOW_BOTTOM;

    private static Class<AbsListView> CLASS_LIST_VIEW = AbsListView.class;
    private static Field LIST_VIEW_FIELD_EDGE_GLOW_TOP;
    private static Field LIST_VIEW_FIELD_EDGE_GLOW_BOTTOM;

    private static Field EDGE_GLOW_FIELD_EDGE;
    private static Field EDGE_GLOW_FIELD_GLOW;

    private static Field EDGE_EFFECT_COMPAT_FIELD_EDGE_EFFECT;


    static void initRecyclerFields(){
        Field edgeGlowTop = null, edgeGlowBottom = null, edgeGlowLeft = null, edgeGlowRight = null;

        for (Field f : CLASS_RECYCLER_VIEW.getDeclaredFields()) {
            switch (f.getName()) {
                case "mTopGlow":
                    f.setAccessible(true);
                    edgeGlowTop = f;
                    break;
                case "mBottomGlow":
                    f.setAccessible(true);
                    edgeGlowBottom = f;
                    break;
                case "mLeftGlow":
                    f.setAccessible(true);
                    edgeGlowLeft = f;
                    break;
                case "mRightGlow":
                    f.setAccessible(true);
                    edgeGlowRight = f;
                    break;
            }
        }

        RECYCLER_VIEW_FIELD_EDGE_GLOW_TOP = edgeGlowTop;
        RECYCLER_VIEW_FIELD_EDGE_GLOW_BOTTOM = edgeGlowBottom;
        RECYCLER_VIEW_FIELD_EDGE_GLOW_LEFT = edgeGlowLeft;
        RECYCLER_VIEW_FIELD_EDGE_GLOW_RIGHT = edgeGlowRight;
    }

    static void initViewPagerFields(){
        Field edgeGlowLeft = null, edgeGlowRight = null;

        for (Field f : CLASS_VIEW_PAGER.getDeclaredFields()) {
            switch (f.getName()) {
                case "mLeftEdge":
                    f.setAccessible(true);
                    edgeGlowLeft = f;
                    break;
                case "mRightEdge":
                    f.setAccessible(true);
                    edgeGlowRight = f;
                    break;
            }
        }

        VIEW_PAGER_FIELD_LEFT_EDGE = edgeGlowLeft;
        VIEW_PAGER_FIELD_RIGHT_EDGE = edgeGlowRight;
    }

    static void initNestedScrollFields(){
        Field edgeGlowTop = null, edgeGlowBottom = null;
        for (Field f : CLASS_NESTED_SCROLL_VIEW.getDeclaredFields()) {
            switch (f.getName()) {
                case "mEdgeGlowTop":
                    f.setAccessible(true);
                    edgeGlowTop = f;
                    break;
                case "mEdgeGlowBottom":
                    f.setAccessible(true);
                    edgeGlowBottom = f;
                    break;
            }
        }

        NESTED_SCROLL_VIEW_FIELD_EDGE_GLOW_TOP = edgeGlowTop;
        NESTED_SCROLL_VIEW_FIELD_EDGE_GLOW_BOTTOM = edgeGlowBottom;
    }

    static void initScrollViewFields(){
        Field edgeGlowTop = null, edgeGlowBottom = null;
        for (Field f : CLASS_SCROLL_VIEW.getDeclaredFields()) {
            switch (f.getName()) {
                case "mEdgeGlowTop":
                    f.setAccessible(true);
                    edgeGlowTop = f;
                    break;
                case "mEdgeGlowBottom":
                    f.setAccessible(true);
                    edgeGlowBottom = f;
                    break;
            }
        }

        SCROLL_VIEW_FIELD_EDGE_GLOW_TOP = edgeGlowTop;
        SCROLL_VIEW_FIELD_EDGE_GLOW_BOTTOM = edgeGlowBottom;
    }

    static void initHorizontalScrollFields(){
        Field edgeGlowLeft = null, edgeGlowRight = null;
        for (Field f : CLASS_HORIZONTAL_SCROLL_VIEW.getDeclaredFields()) {
            switch (f.getName()) {
                case "mEdgeGlowLeft":
                    f.setAccessible(true);
                    edgeGlowLeft = f;
                    break;
                case "mEdgeGlowRight":
                    f.setAccessible(true);
                    edgeGlowRight = f;
                    break;
            }
        }

        HORIZONTAL_SCROLL_VIEW_FIELD_EDGE_GLOW_LEFT = edgeGlowLeft;
        HORIZONTAL_SCROLL_VIEW_FIELD_EDGE_GLOW_RIGHT = edgeGlowRight;

    }

    static void initListViewFields(){
        Field edgeGlowTop = null, edgeGlowBottom = null;
        for (Field f : CLASS_LIST_VIEW.getDeclaredFields()) {
            switch (f.getName()) {
                case "mEdgeGlowTop":
                    f.setAccessible(true);
                    edgeGlowTop = f;
                    break;
                case "mEdgeGlowBottom":
                    f.setAccessible(true);
                    edgeGlowBottom = f;
                    break;
            }
        }

        LIST_VIEW_FIELD_EDGE_GLOW_TOP = edgeGlowTop;
        LIST_VIEW_FIELD_EDGE_GLOW_BOTTOM = edgeGlowBottom;
    }

    static void initEdgeGlowFields(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Field edge = null, glow = null;

            Class cls = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                cls = EdgeEffect.class;
            } else {
                try {
                    cls = Class.forName("android.widget.EdgeGlow");
                } catch (ClassNotFoundException e) {
                    if (BuildConfig.DEBUG) e.printStackTrace();
                }
            }

            if (cls != null) {
                for (Field f : cls.getDeclaredFields()) {
                    switch (f.getName()) {
                        case "mEdge":
                            f.setAccessible(true);
                            edge = f;
                            break;
                        case "mGlow":
                            f.setAccessible(true);
                            glow = f;
                            break;
                    }
                }
            }

            EDGE_GLOW_FIELD_EDGE = edge;
            EDGE_GLOW_FIELD_GLOW = glow;
        } else {
            EDGE_GLOW_FIELD_EDGE = null;
            EDGE_GLOW_FIELD_GLOW = null;
        }

        Field efc = null;
        try {
            efc = EdgeEffectCompat.class.getDeclaredField("mEdgeEffect");
            efc.setAccessible(true);
        } catch (NoSuchFieldException e) {
            if (BuildConfig.DEBUG) e.printStackTrace();
        }
        EDGE_EFFECT_COMPAT_FIELD_EDGE_EFFECT = efc;
    }

    @IntDef({ALWAYS, PRE_HONEYCOMB, PRE_KITKAT, PRE_LOLLIPOP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface EdgeGlowColorApi {}

    public static final int ALWAYS = Integer.MAX_VALUE;
    /** Replace yellow glow in vanilla, blue glow on Samsung. */
    public static final int PRE_HONEYCOMB = Build.VERSION_CODES.HONEYCOMB;
    /** Replace Holo blue glow. */
    public static final int PRE_KITKAT = Build.VERSION_CODES.KITKAT;
    /** Replace Holo grey glow. */
    public static final int PRE_LOLLIPOP = Build.VERSION_CODES.LOLLIPOP;

    public static void setColor(
            @NonNull AbsListView listView, @ColorInt int color, @EdgeGlowColorApi int when) {
        if (Build.VERSION.SDK_INT < when) {
            setColor(listView, color);
        }
    }

    public static void setColor(@NonNull AbsListView listView, @ColorInt int color) {
        try {
            initListViewFields();

            Object ee;
            ee = LIST_VIEW_FIELD_EDGE_GLOW_TOP.get(listView);
            setColor(ee, color);
            ee = LIST_VIEW_FIELD_EDGE_GLOW_BOTTOM.get(listView);
            setColor(ee, color);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setColor(
            @NonNull ScrollView scrollView, @ColorInt int color, @EdgeGlowColorApi int when) {
        if (Build.VERSION.SDK_INT < when) {
            setColor(scrollView, color);
        }
    }

    public static void setColor(@NonNull ScrollView scrollView, @ColorInt int color) {
        try {
            initScrollViewFields();

            Object ee;
            ee = SCROLL_VIEW_FIELD_EDGE_GLOW_TOP.get(scrollView);
            setColor(ee, color);
            ee = SCROLL_VIEW_FIELD_EDGE_GLOW_BOTTOM.get(scrollView);
            setColor(ee, color);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setColor(
            @NonNull HorizontalScrollView scrollView, @ColorInt int color, @EdgeGlowColorApi int when) {
        if (Build.VERSION.SDK_INT < when) {
            setColor(scrollView, color);
        }
    }

    public static void setColor(@NonNull HorizontalScrollView scrollView, @ColorInt int color) {
        try {
            initHorizontalScrollFields();

            Object ee;
            ee = HORIZONTAL_SCROLL_VIEW_FIELD_EDGE_GLOW_LEFT.get(scrollView);
            setColor(ee, color);
            ee = HORIZONTAL_SCROLL_VIEW_FIELD_EDGE_GLOW_RIGHT.get(scrollView);
            setColor(ee, color);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setColor(
            @NonNull ViewPager viewPager, @ColorInt int color, @EdgeGlowColorApi int when) {
        if (Build.VERSION.SDK_INT < when) {
            setColor(viewPager, color);
        }
    }

    public static void setColor(@NonNull ViewPager viewPager, @ColorInt int color) {
        try {
            initViewPagerFields();

            Object ee;
            ee = VIEW_PAGER_FIELD_LEFT_EDGE.get(viewPager);
            setColor(ee, color);
            ee = VIEW_PAGER_FIELD_RIGHT_EDGE.get(viewPager);
            setColor(ee, color);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setColor(@NonNull NestedScrollView scrollView, @ColorInt int color, @EdgeGlowColorApi int when) {
        if (Build.VERSION.SDK_INT < when) {
            setColor(scrollView, color);
        }
    }

    public static void setColor(@NonNull NestedScrollView scrollView, @ColorInt int color) {
        try {
            initNestedScrollFields();

            Object ee;
            ee = NESTED_SCROLL_VIEW_FIELD_EDGE_GLOW_TOP.get(scrollView);
            setColor(ee, color);
            ee = NESTED_SCROLL_VIEW_FIELD_EDGE_GLOW_BOTTOM.get(scrollView);
            setColor(ee, color);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setColor(
            @NonNull RecyclerView scrollView, @ColorInt int color, @EdgeGlowColorApi int when) {
        if (Build.VERSION.SDK_INT < when) {
            setColor(scrollView, color);
        }
    }

    public static void setColor(@NonNull RecyclerView scrollView, @ColorInt int color) {
        try {
            initRecyclerFields();

            Object ee;
            ee = RECYCLER_VIEW_FIELD_EDGE_GLOW_TOP.get(scrollView);
            setColor(ee, color);
            ee = RECYCLER_VIEW_FIELD_EDGE_GLOW_BOTTOM.get(scrollView);
            setColor(ee, color);
            ee = RECYCLER_VIEW_FIELD_EDGE_GLOW_LEFT.get(scrollView);
            setColor(ee, color);
            ee = RECYCLER_VIEW_FIELD_EDGE_GLOW_RIGHT.get(scrollView);
            setColor(ee, color);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void setColor(@NonNull Object edgeEffect, @ColorInt int color) {
        initEdgeGlowFields();

        if (edgeEffect instanceof EdgeEffectCompat) {
            // EdgeEffectCompat
            try {
                edgeEffect = EDGE_EFFECT_COMPAT_FIELD_EDGE_EFFECT.get(edgeEffect);
            } catch (IllegalAccessException e) {
                return;
            }
        }

        if (edgeEffect == null) return;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // EdgeGlow or old EdgeEffect
            try {
                final Drawable mEdge = (Drawable) EDGE_GLOW_FIELD_EDGE.get(edgeEffect);
                final Drawable mGlow = (Drawable) EDGE_GLOW_FIELD_GLOW.get(edgeEffect);
                mEdge.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                mGlow.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                mEdge.setCallback(null); // free up any references
                mGlow.setCallback(null); // free up any references
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        } else {
            // EdgeEffect
            ((EdgeEffect) edgeEffect).setColor(color);
        }
    }
}

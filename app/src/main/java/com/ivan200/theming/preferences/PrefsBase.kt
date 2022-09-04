package com.ivan200.theming.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import kotlin.reflect.KProperty

//
// Created by Ivan200 on 21.02.2020.
//

/**
 * Nullable default preferences
 */
abstract class PrefsBase {
    private lateinit var sp: SharedPreferences

    fun init(context: Context) {
        sp = getSharedPreferences(context)
    }

    protected open fun getSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    abstract inner class AnyPref<T>(val name: String? = null) {
        @Suppress("UNCHECKED_CAST")
        operator fun getValue(thisRef: Any, property: KProperty<*>) =
            getValue(sp, name ?: property.name)

        operator fun setValue(thisRef: Any, property: KProperty<*>, value: T) =
            setValue(sp, name ?: property.name, value)

        abstract fun getValue(prefs: SharedPreferences, key: String): T
        abstract fun setValue(prefs: SharedPreferences, key: String, value: T)
    }

    fun smartEdit(func: SharedPreferences.Editor.() -> Unit) {
        sp.edit().apply { func.invoke(this) }.apply()
    }

    inner class BooleanPref(private val defValue: Boolean? = null, name: String? = null) :
        AnyPref<Boolean?>(name) {
        override fun getValue(prefs: SharedPreferences, key: String): Boolean? {
            return if (prefs.contains(key)) prefs.getBoolean(key, defValue ?: false) else defValue
        }

        override fun setValue(prefs: SharedPreferences, key: String, value: Boolean?) {
            smartEdit { if (value == null) this.remove(key) else putBoolean(key, value) }
        }
    }

    inner class IntPref(private val defValue: Int? = null, name: String? = null) :
        AnyPref<Int?>(name) {
        override fun getValue(prefs: SharedPreferences, key: String): Int? {
            return if (prefs.contains(key)) prefs.getInt(key, defValue ?: 0) else defValue
        }

        override fun setValue(prefs: SharedPreferences, key: String, value: Int?) {
            smartEdit { if (value == null) remove(key) else putInt(key, value) }
        }
    }

    fun getIntPref(key: String, defValue: Int? = null): Int? {
        return if (sp.contains(key)) sp.getInt(key, defValue ?: 0) else defValue
    }

    fun setIntPref(key: String, value: Int?) {
        smartEdit { if (value == null) remove(key) else putInt(key, value) }
    }

    fun getBoolPref(key: String, defValue: Boolean? = null): Boolean? {
        return if (sp.contains(key)) sp.getBoolean(key, defValue ?: false) else defValue
    }

    fun setBoolPref(key: String, value: Boolean?) {
        smartEdit { if (value == null) remove(key) else putBoolean(key, value) }
    }
}

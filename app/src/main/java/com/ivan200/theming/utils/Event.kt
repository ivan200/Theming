package com.ivan200.theming.utils

import java.util.concurrent.atomic.AtomicBoolean

//
// Created by Ivan200 on 17.12.2019.
//
// modified https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150?
/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event<out T>(private val content: T) {
    private var handled = AtomicBoolean(false)
    val isHandled: Boolean get() = handled.get()

    /**
     * Returns the content and prevents its use again.
     */
    fun get(): T? {
        return if(handled.compareAndSet(false, true)) content else null
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peek(): T = content
}
package org.quickness.utils

import android.content.Context

object ContextProvider {
    private var applicationContext: Context? = null
    fun initialize(context: Context) {
        applicationContext = context
    }

    fun getContext(): Context? {
        return applicationContext
    }
}
package com.quickness.shared.utils.providers

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
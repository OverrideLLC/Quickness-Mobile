package com.quickness.shared.utils.providers

import android.content.Context
import androidx.fragment.app.FragmentActivity

object ContextProvider {
    private var applicationContext: Context? = null
    private var fragmentActivity: FragmentActivity? = null

    fun initialize(context: Context, FragmentActivity: FragmentActivity) {
        applicationContext = context
        fragmentActivity = FragmentActivity
    }

    fun getContext(): Context? {
        return applicationContext
    }
    fun getFragmentActivity(): FragmentActivity? {
        return fragmentActivity
    }
}
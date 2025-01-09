package org.quickness.utils

import android.content.Context
import androidx.fragment.app.FragmentActivity

object ContextProvider {
    private var applicationContext: Context? = null
    private var fragmentActivity: FragmentActivity? = null

    fun initialize(context: Context, activity: FragmentActivity) {
        applicationContext = context
        fragmentActivity = activity
    }

    fun getContext(): Context? {
        return applicationContext
    }

    fun getFragmentActivity(): FragmentActivity? {
        return fragmentActivity
    }
}
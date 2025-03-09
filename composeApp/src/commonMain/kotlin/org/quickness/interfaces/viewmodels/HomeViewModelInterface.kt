package org.quickness.interfaces.viewmodels

interface HomeViewModelInterface {
    fun getTokens()
    fun shouldRequestTokens(currentTime: Long, lastRequestTime: Long): Boolean
}
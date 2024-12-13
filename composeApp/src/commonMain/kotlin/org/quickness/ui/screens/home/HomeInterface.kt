package org.quickness.ui.screens.home

import kotlinx.datetime.LocalDateTime

interface HomeInterface {
    fun getTokens()
    fun shouldRequestTokens(current: LocalDateTime, lastRequest: LocalDateTime): Boolean
}
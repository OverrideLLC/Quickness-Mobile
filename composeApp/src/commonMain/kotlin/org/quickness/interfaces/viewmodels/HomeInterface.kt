package org.quickness.interfaces.viewmodels

import kotlinx.datetime.LocalDateTime

interface HomeInterface {
    fun getTokens()
    fun shouldRequestTokens(currentTime: Long, lastRequestTime: Long): Boolean
}
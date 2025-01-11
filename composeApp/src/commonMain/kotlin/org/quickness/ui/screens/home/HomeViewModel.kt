package org.quickness.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.quickness.interfaces.repository.DataStoreRepository
import org.quickness.interfaces.repository.TokenDatabaseRepository
import org.quickness.interfaces.repository.TokensRepository
import org.quickness.interfaces.viewmodels.HomeInterface
import org.quickness.utils.`object`.KeysCache.JWT_KEY
import org.quickness.utils.`object`.KeysCache.LAST_REQUEST_KEY

class HomeViewModel(
    private val tokensRepository: TokensRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val tokensDatabaseRepository: TokenDatabaseRepository
) : ViewModel(), HomeInterface {
    override fun getTokens() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentTime = Clock.System.now().toEpochMilliseconds()
            val lastRequestDate = dataStoreRepository.getString(LAST_REQUEST_KEY, "")?.toLongOrNull()
            var jwt = dataStoreRepository.getString(JWT_KEY, "") ?: ""

            if (lastRequestDate == null || shouldRequestTokens(currentTime, lastRequestDate)) {
                try {
                    val tokensResponse = tokensRepository.getTokens(jwt)
                    val sortedTokens = tokensResponse.tokens
                        .toList()
                        .sortedBy { it.first.toIntOrNull() ?: Int.MAX_VALUE }
                        .associate { (key, value) -> key.toInt() to value }
                        .also {
                            println("Tokens fetched successfully: \n$it")
                        }

                    tokensDatabaseRepository.saveTokens(sortedTokens, currentTime)
                    dataStoreRepository.saveString(mapOf(LAST_REQUEST_KEY to currentTime.toString()))
                    println("Tokens fetched and saved successfully.")
                } catch (e: Exception) {
                    println("Error fetching tokens: ${e.message}")
                }
            } else {
                println("Tokens already fetched today.")
            }
        }
    }

    override fun shouldRequestTokens(currentTime: Long, lastRequestTime: Long): Boolean {
        val timeZone = TimeZone.of("UTC-6")
        val currentDate =
            Instant.fromEpochMilliseconds(currentTime).toLocalDateTime(timeZone).date
        val lastRequestDate =
            Instant.fromEpochMilliseconds(lastRequestTime).toLocalDateTime(timeZone).date
        return currentDate > lastRequestDate
    }
}
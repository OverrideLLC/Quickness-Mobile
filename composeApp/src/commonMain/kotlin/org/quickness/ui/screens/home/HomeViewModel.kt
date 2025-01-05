package org.quickness.ui.screens.home

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.quickness.SharedPreference
import org.quickness.data.repository.TokensRepositoryImpl
import org.quickness.interfaces.viewmodels.HomeInterface
import org.quickness.utils.`object`.KeysCache.LAST_REQUEST_KEY
import org.quickness.utils.`object`.KeysCache.MIN_REQUEST_HOUR
import org.quickness.utils.`object`.KeysCache.TOKENS_KEY
import org.quickness.utils.`object`.KeysCache.UID_KEY

class HomeViewModel(
    private val tokensRepository: TokensRepositoryImpl,
    private val sharedPreference: SharedPreference,
    private val dataStore: DataStore<Preferences>,
) : ViewModel(), HomeInterface {

    override fun getTokens() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            val lastRequestDateString = sharedPreference.getString(LAST_REQUEST_KEY, null)
            val lastRequestDate = parseLocalDateTime(lastRequestDateString)
            val tokensMap = sharedPreference.getMap(TOKENS_KEY)
            var uid: String? = null
            dataStore.data.map {
                it[UID_KEY] ?: ""
            }.collectLatest {
                uid = it
            }

            if (lastRequestDate == null || shouldRequestTokens(
                    currentTime,
                    lastRequestDate
                ) || tokensMap.isNullOrEmpty()
            ) {
                try {
                    val tokensResponse = tokensRepository.getTokens(uid ?: "")

                    val sortedTokens = tokensResponse.tokens
                        .toList()
                        .sortedBy { it.first.toIntOrNull() ?: Int.MAX_VALUE }
                        .toMap()

                    sharedPreference.setMap(TOKENS_KEY, sortedTokens)
                    sharedPreference.setString(LAST_REQUEST_KEY, currentTime.toString())
                    println("Tokens fetched and saved successfully.")
                } catch (e: Exception) {
                    println("Error fetching tokens: ${e.message}")
                }
            } else {
                println("Tokens already fetched today.")
            }
        }
    }

    private fun parseLocalDateTime(dateString: String?): LocalDateTime? {
        return try {
            dateString?.let { LocalDateTime.parse(it) }
        } catch (e: Exception) {
            println("Error parsing date: ${e.message}")
            null
        }
    }

    override fun shouldRequestTokens(current: LocalDateTime, lastRequest: LocalDateTime): Boolean {
        val isNewDay = current.date > lastRequest.date
        val isAfterMinHour = current.hour >= MIN_REQUEST_HOUR
        return isNewDay || (isNewDay && isAfterMinHour)
    }
}
package org.quickness.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.quickness.SharedPreference
import org.quickness.data.repository.TokensRepository
import org.quickness.utils.`object`.KeysCache.LAST_REQUEST_KEY
import org.quickness.utils.`object`.KeysCache.MIN_REQUEST_HOUR
import org.quickness.utils.`object`.KeysCache.TOKENS_KEY
import org.quickness.utils.`object`.KeysCache.UID_KEY

class HomeViewModel(
    private val tokensRepository: TokensRepository,
    private val sharedPreference: SharedPreference
) : ViewModel(), HomeInterface {

    override fun getTokens() {
        val uid = sharedPreference.getString(UID_KEY, "")
        viewModelScope.launch(Dispatchers.IO) {
            val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

            // Obtener la última solicitud
            val lastRequestDateString = sharedPreference.getString(LAST_REQUEST_KEY, null)
            val lastRequestDate = parseLocalDateTime(lastRequestDateString)

            // Comprobar si es necesario obtener nuevos tokens
            val tokensMap = sharedPreference.getMap(TOKENS_KEY)
            if (lastRequestDate == null || shouldRequestTokens(currentTime, lastRequestDate) || tokensMap.isNullOrEmpty()) {
                try {
                    // Obtener tokens del repositorio
                    val tokensResponse = tokensRepository.getTokens(uid)

                    // Asegurar que las claves están ordenadas antes de almacenarlas
                    val sortedTokens = tokensResponse.tokens
                        .toList()
                        .sortedBy { it.first.toIntOrNull() ?: Int.MAX_VALUE }
                        .toMap()

                    // Guardar tokens ordenados en SharedPreference
                    sharedPreference.setMap(TOKENS_KEY, sortedTokens)
                    sharedPreference.setString(LAST_REQUEST_KEY, currentTime.toString())
                    println("Tokens fetched and saved successfully.")
                } catch (e: Exception) {
                    println("Error fetching tokens: ${e.message}")
                }
            } else {
                println("Tokens already fetched and up to date.")
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
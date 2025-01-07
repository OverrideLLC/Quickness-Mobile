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
import org.quickness.utils.`object`.KeysCache.LAST_REQUEST_KEY

class HomeViewModel(
    private val tokensRepository: TokensRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val tokensDatabaseRepository: TokenDatabaseRepository
) : ViewModel(), HomeInterface {
    override fun getTokens() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentTime = Clock.System.now().toEpochMilliseconds()
            val lastRequestDate =
                dataStoreRepository.getString(LAST_REQUEST_KEY, "")?.toLongOrNull()
            var jwt =
                "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhT3Z0STFDbEV5YWhDYlczaVBpUUc4VUxLSk4yIiwiY2xhaW1zIjp7ImRldmljZSI6InF3ZXJ0eTEyMyIsInJvbGUiOiJhNGRmMjNmMGYwYWNjYzE1ZjRhZmQ2MTkwNThlNzYwNTg1ZjFhNGQwYjM2NTkxNjVlOTNkZGM2YjVhM2YxNjJhIn0sImlzcyI6Imh0dHBzOi8vb3ZlcnJpZGUuY29tLm14IiwiYXVkIjoiTW9iaWxlIiwiaWF0IjoxNzM2MTIyODAwLCJleHAiOjE3MzY5ODY4MDB9.MbmjKM1Y_WD4cWLm6fOgKh2AEwlAMLMjOEKTMqvbZD4lRVNs-Ki0zn_Yjg_03-i5IoMckFIIfdNy_cp7BkTFIRLurPjbdiTwqtrdNKmsdSePFqBJDxCB1wPiIcHgPVd_2Ju28fR3YRA7cXWco0FQinZVmbZ2jgyH5dKwgaxmRD6N7hgtRw99voI9TQo42APtv7lS6DW1Fmw2vHTDSBa6viPft6ArqLf_JdGgh9lvkezgt7aBdX3SETaKs2K2nlEc9PnIv0Z9Og08Hte66FlzvCuh7Rb_ywQYb9EJfw536efL98awR-94rzBl04pesq9sqVnUimwl4rljte50NzF2qQ"

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
        val currentDate =
            Instant.fromEpochMilliseconds(currentTime).toLocalDateTime(TimeZone.UTC).date
        val lastRequestDate =
            Instant.fromEpochMilliseconds(lastRequestTime).toLocalDateTime(TimeZone.UTC).date
        return currentDate > lastRequestDate
    }
}
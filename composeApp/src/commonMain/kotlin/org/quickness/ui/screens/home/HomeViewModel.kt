package org.quickness.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quickness.shared.utils.objects.KeysCache
import com.shared.resources.Resources
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.DrawableResource
import org.koin.core.logger.EmptyLogger
import org.quickness.interfaces.helpers.CheckPermissions
import org.quickness.interfaces.repository.data.DataStoreRepository
import org.quickness.interfaces.repository.data.TokenDatabaseRepository
import org.quickness.interfaces.repository.network.TokensRepository
import org.quickness.interfaces.viewmodels.HomeInterface

class HomeViewModel(
    private val tokensRepository: TokensRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val tokensDatabaseRepository: TokenDatabaseRepository,
    private val resources: Resources
) : ViewModel(), HomeInterface, CheckPermissions {

    init { getTokens() }

    override suspend fun checkPermissions(
        permissions: Permission,
        controller: PermissionsController,
    ) {
        if (!controller.isPermissionGranted(permissions)) {
            runCatching {
                controller.providePermission(permissions)
            }.onFailure { e -> println("Permission denied " + e.message) }
        }
    }

    override fun getTokens() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentTime = Clock.System.now().toEpochMilliseconds()
            val lastRequestDate =
                dataStoreRepository.getString(KeysCache.LAST_REQUEST_KEY, "")?.toLongOrNull()
            val jwt = dataStoreRepository.getString(KeysCache.JWT_KEY, "")
                ?: return@launch EmptyLogger().info("JWT not found")

            if (lastRequestDate == null || shouldRequestTokens(currentTime, lastRequestDate)) {
                try {
                    val tokensResponse = tokensRepository.getTokens(jwt)
                    val sortedTokens = tokensResponse.tokens
                        .toList()
                        .sortedBy { it.first.toIntOrNull() ?: Int.MAX_VALUE }
                        .associate { (key, value) -> key.toInt() to value }
                        .also { println("Tokens fetched successfully") }

                    tokensDatabaseRepository.saveTokens(sortedTokens, currentTime)
                    dataStoreRepository.saveString(mapOf(KeysCache.LAST_REQUEST_KEY to currentTime.toString()))
                } catch (e: Exception) {
                    println("Error fetching tokens: ${e.message}")
                }
            } else {
                println("No need to request tokens")
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

    fun getDrawable(drawableRes: String): DrawableResource {
        return resources.getDrawable(drawableRes)
    }
}
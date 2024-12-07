package org.quickness.ui.screens.home.qr

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.quickness.SharedPreference
import org.quickness.utils.`object`.KeysCache.QR_TAG_KEY
import org.quickness.utils.`object`.KeysCache.TOKENS_KEY
import qrgenerator.generateQrCode

class QrViewModel(
    private val sharedPreference: SharedPreference,
) : ViewModel(), QrInterface {

    data class QrState(
        var qrCode: ImageBitmap? = null,
        var lastQrData: String? = null, // Último dato utilizado para el QR
        var currentInterval: String? = null // Intervalo actual en formato HH:mm-HH:mm
    )

    private val _qrState = MutableStateFlow(QrState())
    val qrState = _qrState

    private val viewModelScopeJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelScopeJob)

    init {
        println("QrViewModel initialized")
        viewModelScope.launch {
            monitorQrUpdates()
        }
    }

    override fun monitorQrUpdates() {
        viewModelScope.launch {
            while (true) {
                val currentInterval = calculateCurrentInterval()
                val token = getCurrentToken()
                if (_qrState.value.currentInterval != currentInterval || token != _qrState.value.lastQrData) {
                    updateQrCodeForToken(token, currentInterval)
                }
                delay(10 * 60 * 1000L) // Actualizar cada 10 minutos
            }
        }
    }

    private fun calculateCurrentInterval(): String {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val startMinute = (now.minute / 10) * 10
        val endMinute = startMinute + 10

        val startHour = now.hour
        val endHour = if (endMinute >= 60) (startHour + 1) % 24 else startHour

        val adjustedStartMinute = startMinute % 60
        val adjustedEndMinute = endMinute % 60

        return "${startHour.toString().padStart(2, '0')}:${adjustedStartMinute.toString().padStart(2, '0')}-${endHour.toString().padStart(2, '0')}:${adjustedEndMinute.toString().padStart(2, '0')}"
    }

    private fun getCurrentToken(): String {
        val tokensMap = sharedPreference.getMap(TOKENS_KEY) ?: emptyMap()
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val currentTokenIndex = (now.hour * 6) + (now.minute / 10) // Índice basado en bloques de 10 minutos
        return tokensMap[currentTokenIndex.toString()] ?: "default_token"
    }

    override fun updateQrCodeForToken(token: String, interval: String) {
        println("Generating QR for token: $token at interval $interval")

        // Evitar regenerar si el dato no ha cambiado
        if (token == _qrState.value.lastQrData && interval == _qrState.value.currentInterval) return

        _qrState.value = _qrState.value.copy(
            lastQrData = token,
            currentInterval = interval
        )
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScopeJob.cancel() // Cancelar tareas en segundo plano
    }
}
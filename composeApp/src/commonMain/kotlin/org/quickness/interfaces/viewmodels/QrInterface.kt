package org.quickness.interfaces.viewmodels

interface QrInterface {
    fun updateQrCodeForToken(token: String, interval: String)
    fun monitorQrUpdates() // Nueva función para monitorear actualizaciones periódicas
}
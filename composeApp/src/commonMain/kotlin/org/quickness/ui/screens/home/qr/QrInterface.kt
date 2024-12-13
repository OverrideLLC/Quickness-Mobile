package org.quickness.ui.screens.home.qr


interface QrInterface {
    fun updateQrCodeForToken(token: String, interval: String)
    fun monitorQrUpdates() // Nueva función para monitorear actualizaciones periódicas
}

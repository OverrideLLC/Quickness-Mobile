package org.quickness.ui.screens.home.qr

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.quickness.interfaces.QRCodeGenerator

class QrViewModel(private val qrCodeGenerator: QRCodeGenerator) : ViewModel() {
    private val _qrCode = MutableStateFlow<ImageBitmap?>(null)
    val qrCode: StateFlow<ImageBitmap?> = _qrCode

    fun generateQRCode(data: String) {
        val qrBytes = qrCodeGenerator.generateQRCode(data)
        _qrCode.value = qrBytes
    }
}
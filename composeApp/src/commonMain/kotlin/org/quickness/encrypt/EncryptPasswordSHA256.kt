package org.quickness.encrypt

import com.soywiz.krypto.SHA256

object EncryptPasswordSHA256 {
    fun encryptPasswordSHA256(password: String): String {
        val hashBytes = SHA256.digest(password.encodeToByteArray())
        return hashBytes.hex
    }
}
package org.quickness.ui.screens.login

interface LoginInterface {
    fun login(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

}
package org.quickness.interfaces.viewmodels

interface LoginInterface {
    fun login(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

}
package org.quickness.ui.screens.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.quickness.data.service.FirebaseAuthImpl
import org.quickness.ui.states.ForgotPasswordState

class ForgotPasswordViewModel(private val firebaseService: FirebaseAuthImpl) : ViewModel() {
    private val _state = MutableStateFlow(ForgotPasswordState())
    val state = _state.asStateFlow()

    fun update(update: ForgotPasswordState.() -> ForgotPasswordState) {
        _state.value = _state.value.update()
    }

    fun reset() {
        viewModelScope.launch {
            val result = firebaseService.forgotPassword(_state.value.email)
            if (result?.success == true) {
                update { copy(success = true) }
            } else {
                update { copy(error = true) }
            }
        }
    }
}
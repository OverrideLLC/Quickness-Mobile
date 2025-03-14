package org.quickness.interfaces.viewmodels

interface RegisterViewModelInterface {
    fun validateEmailAndPassword(): Boolean
    fun validatePersonalInfo(): Boolean
    fun isTermsAndConditionsChecked(): Boolean
    fun capitalizeWords(): String
    fun register(onSuccess: () -> Unit, onError: () -> Unit)
}
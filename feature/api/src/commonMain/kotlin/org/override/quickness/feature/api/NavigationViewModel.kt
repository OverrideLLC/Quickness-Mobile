package org.override.quickness.feature.api

import  androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.override.quickness.data.api.repository.DataStoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.override.quickness.shared.utils.objects.KeysCache.JWT_KEY

class NavigationViewModel(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    private val _session = MutableStateFlow<Boolean?>(false)
    val session: StateFlow<Boolean?> = _session.asStateFlow()
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            _isLoading.value = true
            _session.value = dataStoreRepository.getString(JWT_KEY, "")?.isNotBlank() == true
        }.invokeOnCompletion {
            _isLoading.value = false
        }
    }
}
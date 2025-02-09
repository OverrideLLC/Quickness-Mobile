package org.quickness.ui.navegation

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.quickness.utils.objects.KeysCache.JWT_KEY

class SharedNavigationViewModel(
    private val dataStore: DataStore<Preferences>,
) : ViewModel() {
    data class SharedNavigationState(
        val uid: String = "",
    )

    private val _sharedNavigationState = MutableStateFlow(SharedNavigationState())
    val sharedNavigationState: StateFlow<SharedNavigationState> =
        _sharedNavigationState.asStateFlow()

    fun update(update: SharedNavigationState.() -> SharedNavigationState) {
        _sharedNavigationState.value = _sharedNavigationState.value.update()
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val initialUid = dataStore.data.first()[stringPreferencesKey(JWT_KEY)] ?: ""
            _sharedNavigationState.value = SharedNavigationState(uid = initialUid)

            dataStore.data.collect { preferences ->
                _sharedNavigationState.value =
                    SharedNavigationState(uid = preferences[stringPreferencesKey(JWT_KEY)] ?: "")
            }
        }
    }
}
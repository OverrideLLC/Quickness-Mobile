package org.override.quickness

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.override.quickness.data.api.repository.DataStoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.override.quickness.generated.resources.Res // Asegúrate que esta sea la importación correcta para tus recursos
import org.override.quickness.shared.utils.objects.KeysCache.IS_DARK_THEME_KEY

class AppViewModel(
    private val dataStoreRepository: DataStoreRepository,
): ViewModel() {
    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.getBoolean(
                key = IS_DARK_THEME_KEY,
                defaultValue = false
            )?.collect { newValue ->
                _isDarkTheme.value = newValue
            }
        }
    }

    fun getTokens() {
        viewModelScope.launch(Dispatchers.IO) {
            // Leer el archivo y procesar los tokens
            Res.readBytes("files/tokens_validos.txt").let { byteArrayContent ->
                // 1. Convertir ByteArray a String
                val fileContentAsString = byteArrayContent.decodeToString()

                // 2. Dividir el String en una lista de tokens (asumiendo un token por línea)
                //    filter { it.isNotBlank() } es opcional, pero útil para remover líneas vacías si las hubiera.
                val listOfTokens = fileContentAsString.lines().filter { it.isNotBlank() }
                println("Tokens: $listOfTokens")

                // Ahora `listOfTokens` contiene tu lista de tokens
                // Puedes usarla para guardarla en DataStore como un Set
                dataStoreRepository.saveSet(
                    key = "DATA_TOKENS",
                    value = listOfTokens.toSet() // Convertir la lista a Set para guardarla
                )
            }
        }
    }
}
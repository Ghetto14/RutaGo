package com.ghettodev.rutago.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghettodev.rutago.data.entity.Parada
import com.ghettodev.rutago.data.entity.Ruta
import com.ghettodev.rutago.data.repository.RutasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RutaDetailUiState(
    val ruta: Ruta? = null,
    val paradas: List<Parada> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

class RutaDetailViewModel(
    private val repository: RutasRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RutaDetailUiState())
    val uiState: StateFlow<RutaDetailUiState> = _uiState.asStateFlow()

    fun loadRutaDetail(idRuta: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val ruta = repository.getRutaById(idRuta) // necesitas implementar este método en el repositorio
                val paradas = repository.obtenerParadasDeRuta(idRuta)
                _uiState.value = _uiState.value.copy(
                    ruta = ruta,
                    paradas = paradas,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }
}
package com.ghettodev.rutago.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghettodev.rutago.data.entity.Parada
import com.ghettodev.rutago.data.repository.RutasRepository
import com.ghettodev.rutago.ui.state.RutaUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.maplibre.compose.location.Location

class RutaViewModel(
    private val repository: RutasRepository
) : ViewModel() {

    // 🔹 Estado interno
    private val _uiState = MutableStateFlow(RutaUiState())
    val uiState: StateFlow<RutaUiState> = _uiState

    // 🔥 Cargar ruta desde GeoJSON
    fun cargarRuta(context: Context, rutaKey: String) {
        viewModelScope.launch {
            try {
                val idRuta = repository.cargarRutaSiNoExiste(context, rutaKey)
                val paradas = repository.obtenerParadasDeRuta(idRuta)

                _uiState.update {
                    it.copy(
                        paradasMostradas = paradas,
                        error = null
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = e.message)
                }
            }
        }
    }

    // 🔥 Alternar modo (una ruta / todas)
    fun toggleMostrarTodasLasRutas() {
        _uiState.update {
            it.copy(
                mostrarTodasLasRutas = !it.mostrarTodasLasRutas
            )
        }
    }

    // 🔥 Limpiar error
    fun limpiarError() {
        _uiState.update {
            it.copy(error = null)
        }
    }

    //funcion por implementar
    fun actualizarUbicacionUsuario(location: Location?){

    }
}
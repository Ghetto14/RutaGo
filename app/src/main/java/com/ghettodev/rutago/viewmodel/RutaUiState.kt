package com.ghettodev.rutago.ui.viewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghettodev.rutago.data.entity.Ruta
import com.ghettodev.rutago.data.entity.Parada
import com.ghettodev.rutago.data.repository.RutaRepository
import com.ghettodev.rutago.data.repository.RutaConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class RutaUiState(
    val rutaActual: Ruta? = null,
    val paradasActuales: List<Parada> = emptyList(),
    val rutasDisponibles: List<Ruta> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val userLocation: Location? = null,
    val cargandoMultiples: Boolean = false,
    val rutasCargadas: Int = 0,
    val mostrarTodasLasRutas: Boolean = false
)

class RutaViewModel(
    private val rutaRepository: RutaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RutaUiState())
    val uiState: StateFlow<RutaUiState> = _uiState

    /**
     * Cargar TODAS las rutas al iniciar
     */
    fun cargarTodasLasRutas() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                cargandoMultiples = true,
                rutasCargadas = 0,
                error = null
            )

            // Configuración de rutas a cargar
            val rutasConfig = listOf(
                RutaConfig("ra-02.geojson", "Ruta Santa María", "#FF6600"),
                RutaConfig("ra-03.geojson", "Ruta Centro", "#0066FF"),
                RutaConfig("ra-05.geojson", "Ruta Periférico", "#00CC00"),
                RutaConfig("ra-10.geojson", "Ruta Industrial", "#FF00FF")
            )

            val resultado = rutaRepository.cargarMultiplesRutas(rutasConfig)

            resultado.onSuccess { rutas ->
                _uiState.value = _uiState.value.copy(
                    rutasDisponibles = rutas,
                    cargandoMultiples = false,
                    rutasCargadas = rutas.size,
                    error = null
                )
                Log.d("RutaViewModel", "✅ ${rutas.size} rutas cargadas correctamente")
            }.onFailure { e ->
                _uiState.value = _uiState.value.copy(
                    cargandoMultiples = false,
                    error = e.message ?: "Error desconocido"
                )
                Log.e("RutaViewModel", "❌ Error: ${e.message}")
            }
        }
    }

    /**
     * Cargar una ruta específica
     */
    fun cargarRutaEspecifica(idRuta: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val ruta = rutaRepository.getRutaConParadas(idRuta)
                val paradas = rutaRepository.getParadasDeRuta(idRuta)

                _uiState.value = _uiState.value.copy(
                    rutaActual = ruta,
                    paradasActuales = paradas,
                    isLoading = false,
                    mostrarTodasLasRutas = false
                )
                Log.d("RutaViewModel", "✅ Ruta ${ruta?.nombreRuta} cargada")
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
                Log.e("RutaViewModel", "❌ Error: ${e.message}")
            }
        }
    }

    fun actualizarUbicacionUsuario(location: Location?) {
        _uiState.value = _uiState.value.copy(userLocation = location)
    }

    fun toggleMostrarTodasLasRutas() {
        _uiState.value = _uiState.value.copy(
            mostrarTodasLasRutas = !_uiState.value.mostrarTodasLasRutas
        )
    }

    fun limpiarError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
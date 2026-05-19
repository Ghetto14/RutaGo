package com.ghettodev.rutago.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghettodev.rutago.data.entity.Ruta
import com.ghettodev.rutago.data.repository.RutasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RutasPopularesViewModel(
    private val repository: RutasRepository
) : ViewModel() {

    private val _rutas = MutableStateFlow<List<Ruta>>(emptyList())
    val rutas: StateFlow<List<Ruta>> = _rutas

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadRutas() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val rutasActivas = repository.getAllRutasActivas()
                _rutas.value = rutasActivas
            } catch (e: Exception) {
                e.printStackTrace()
                _rutas.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}

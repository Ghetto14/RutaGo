package com.ghettodev.rutago.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghettodev.rutago.data.entity.Ruta
import com.ghettodev.rutago.data.repository.RutasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
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
                val todas = repository.getAllRutasActivas()
                _rutas.value = todas  // o todas.take(3) si quieres solo 3 en el home
            } catch (e: Exception) {
                _rutas.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
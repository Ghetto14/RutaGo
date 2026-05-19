package com.ghettodev.rutago.ui.state

import com.ghettodev.rutago.data.entity.Parada

data class RutaUiState(
    val paradasMostradas: List<Parada> = emptyList(),
    val mostrarTodasLasRutas: Boolean = false,
    val error: String? = null
)
package com.ghettodev.rutago.parser

import com.ghettodev.rutago.data.entity.Parada
import com.ghettodev.rutago.data.entity.Ruta

data class RutaParseResult(
    val ruta: Ruta,
    val paradas: List<Parada>
)
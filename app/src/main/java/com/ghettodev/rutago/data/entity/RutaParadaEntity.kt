package com.ghettodev.rutago.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "ruta_parada",
    primaryKeys = ["idRuta", "idParada"],
    foreignKeys = [
        ForeignKey(entity = Ruta::class, parentColumns = ["idRuta"], childColumns = ["idRuta"]),
        ForeignKey(entity = Parada::class, parentColumns = ["idParada"], childColumns = ["idParada"])
    ]
)
data class RutaParada(
    val idRuta: Int,
    val idParada: Int,
    val orden: Int,
    val tiempoEstimadoMinutos: Int? = null
)
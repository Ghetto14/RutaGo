package com.ghettodev.rutago.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "parada")
data class Parada(
    @PrimaryKey
    val idParada: Int,                      // NO autoGenerate
    val tipoParada: String,
    val nombreParada: String,
    val latitud: Double,
    val longitud: Double,
    val color: String,
    val direccion: String = "",             // IDA, REGRESO
    val secuencia: Int = 0,
    val conexiones: String = ""
)
package com.ghettodev.rutago.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "parada")
data class Parada(
    @PrimaryKey(autoGenerate = true) val idParada: Int,
    val tipoParada: String,
    val nombreParada: String,
    val latitud: Double,
    val longitud: Double,
    val color: String,
)
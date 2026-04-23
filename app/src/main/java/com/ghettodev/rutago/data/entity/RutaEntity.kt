package com.ghettodev.rutago.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ruta")
data class Ruta(
    @PrimaryKey(autoGenerate = true) val idRuta: Int,
    val nombreRuta: String,
    val nRuta: Int,
    val color: String,
    val descripcion: String,
    val activo: Boolean,
    val origen: String,
    val destino: String,
    val geoJsonPolyline: String? = null
)
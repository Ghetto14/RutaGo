package com.ghettodev.rutago.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ruta")
data class Ruta(
    @PrimaryKey(autoGenerate = true)
    val idRuta: Int = 0,
    val nombreRuta: String,
    val nRuta: Int,
    val rutaKey: String,                    // RA-02
    val color: String,
    val descripcion: String = "",
    val origen: String = "Terminal",
    val destino: String = "Terminal",
    val geoJsonPolyline: String? = null,   // GeoJSON completo
    val totalParadas: Int = 0,
    val idaCount: Int = 0,
    val regresoCount: Int = 0,
    val baseCount: Int = 0,
    val terminalCount: Int = 0,
    val activo: Boolean = true,
    val fechaCarga: Long = System.currentTimeMillis()
)
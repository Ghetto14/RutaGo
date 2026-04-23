package com.ghettodev.rutago.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "bloqueo",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["idUsuario"],
            childColumns = ["idUsuario"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Ruta::class,
            parentColumns = ["idRuta"],
            childColumns = ["idRuta"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Bloqueo(
    @PrimaryKey(autoGenerate = true) val idBloqueo: Int,
    val idUsuario: Int,
    val idRuta: Int,
    val tipo: String,
    val latitud: Double,
    val longitud: Double,
    val descripcion: String? = null,
    val horaReporte: Long = System.currentTimeMillis(),
    val resuelto: Boolean = false
)
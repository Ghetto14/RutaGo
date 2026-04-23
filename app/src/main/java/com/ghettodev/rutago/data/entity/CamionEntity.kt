package com.ghettodev.rutago.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "camion",
    foreignKeys = [
        ForeignKey(
            entity = Empresa::class,
            parentColumns = ["idEmpresa"],
            childColumns = ["idEmpresa"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Ruta::class,
            parentColumns = ["idRuta"],
            childColumns = ["idRuta"],
            onDelete = ForeignKey.RESTRICT
        )
    ]
)
data class Camion(
    @PrimaryKey (autoGenerate = true) val idCamion: Int,
    val codigo: String,
    val idEmpresa: Int,
    val idRuta: Int
)
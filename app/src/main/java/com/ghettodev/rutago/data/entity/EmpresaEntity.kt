package com.ghettodev.rutago.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "empresa")
data class Empresa(
    @PrimaryKey(autoGenerate = true) val idEmpresa: Int,
    val nombre: String,
    val nCamiones: Int
)

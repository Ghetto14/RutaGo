package com.ghettodev.rutago.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuario")
data class Usuario(

    @PrimaryKey(autoGenerate = true)
    val idUsuario: Int = 0,

    val nombreUsuario: String,
    val correo: String,
    val telefono: String,
    val password: String
)
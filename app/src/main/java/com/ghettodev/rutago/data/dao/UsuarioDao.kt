package com.ghettodev.rutago.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ghettodev.rutago.data.entity.Usuario

@Dao
interface UsuarioDao {

    @Insert
    suspend fun insertar(usuario: Usuario)

    @Query("SELECT * FROM usuario")
    suspend fun obtenerUsuarios(): List<Usuario>

    @Query("SELECT * FROM usuario WHERE correo = :correo AND password = :password LIMIT 1")
    suspend fun login(correo: String, password: String): Usuario?
}
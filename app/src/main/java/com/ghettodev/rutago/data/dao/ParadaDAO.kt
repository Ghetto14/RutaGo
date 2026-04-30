package com.ghettodev.rutago.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ghettodev.rutago.data.entity.Parada

@Dao
interface ParadaDaokajdfla {

    // ✅ INSERT - Insertar una parada
    @Insert
    suspend fun insertParada(parada: Parada): Long  // ← Cambié de insertarParada

    // ✅ INSERT MULTIPLE - Insertar muchas paradas
    @Insert
    suspend fun insertarParadas(paradas: List<Parada>): List<Long>

    // ✅ SELECT * - Obtener todas las paradas
    @Query("SELECT * FROM parada")
    suspend fun obtenerTodasParadas(): List<Parada>

    // ✅ SELECT WHERE - Obtener parada por ID
    @Query("SELECT * FROM parada WHERE idParada = :idParada")
    suspend fun obtenerParada(idParada: Int): Parada?

    // ✅ SELECT WHERE - Obtener paradas por tipo
    @Query("SELECT * FROM parada WHERE tipoParada = :tipo")
    suspend fun obtenerParadasPorTipo(tipo: String): List<Parada>

    // ✅ SELECT WHERE IN - Obtener paradas por lista de IDs
    @Query("SELECT * FROM parada WHERE idParada IN (:ids) ORDER BY idParada")
    suspend fun obtenerParadasPorIds(ids: List<Int>): List<Parada>

    // ✅ SELECT NEAR - Obtener paradas cercanas (radio)
    @Query("""
        SELECT * FROM parada 
        WHERE (
            (latitud - :latitud) * (latitud - :latitud) + 
            (longitud - :longitud) * (longitud - :longitud)
        ) < (:radio * :radio)
        ORDER BY 
            (latitud - :latitud) * (latitud - :latitud) + 
            (longitud - :longitud) * (longitud - :longitud)
        LIMIT :limite
    """)
    suspend fun obtenerParadasCercanas(
        latitud: Double,
        longitud: Double,
        radio: Double,
        limite: Int = 10
    ): List<Parada>

    // ✅ COUNT - Contar paradas
    @Query("SELECT COUNT(*) FROM parada")
    suspend fun contarParadas(): Int

    // ✅ DELETE - Eliminar parada
    @Delete
    suspend fun eliminarParada(parada: Parada)

    // ✅ DELETE WHERE - Eliminar paradas por tipo
    @Query("DELETE FROM parada WHERE tipoParada = :tipo")
    suspend fun eliminarParadasPorTipo(tipo: String)
}
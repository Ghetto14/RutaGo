package com.ghettodev.rutago.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.OnConflictStrategy
import com.ghettodev.rutago.data.entity.Ruta
import com.ghettodev.rutago.data.entity.Parada
import com.ghettodev.rutago.data.entity.RutaParada

@Dao
interface RutaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRuta(ruta: Ruta): Long

    @Query("SELECT * FROM ruta WHERE idRuta = :idRuta")
    suspend fun getRutaById(idRuta: Int): Ruta?

    @Query("SELECT * FROM ruta WHERE activo = 1 ORDER BY nRuta ASC")
    suspend fun getAllRutasActivas(): List<Ruta>

    @Query("SELECT * FROM ruta ORDER BY nRuta ASC")
    suspend fun getAllRutas(): List<Ruta>

    @Transaction
    @Query("""
        SELECT p.* FROM parada p
        INNER JOIN ruta_parada rp ON p.idParada = rp.idParada
        WHERE rp.idRuta = :idRuta
        ORDER BY rp.orden ASC
    """)
    suspend fun getParadasByRuta(idRuta: Int): List<Parada>

    @Query("DELETE FROM ruta WHERE idRuta = :idRuta")
    suspend fun deleteRuta(idRuta: Int)
}



@Dao
interface ParadaDao {

    // 🔹 Insertar una parada
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParada(parada: Parada): Long

    // 🔹 Insertar múltiples (IMPORTANTE para GeoJSON)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParadas(paradas: List<Parada>): List<Long>

    // 🔹 Obtener por ID
    @Query("SELECT * FROM parada WHERE idParada = :idParada")
    suspend fun getParadaById(idParada: Int): Parada?

    // 🔹 Obtener todas
    @Query("SELECT * FROM parada")
    suspend fun getAllParadas(): List<Parada>

    // 🔹 Obtener por tipo (base, normal, etc.)
    @Query("SELECT * FROM parada WHERE tipoParada = :tipo")
    suspend fun getParadasPorTipo(tipo: String): List<Parada>

    // 🔹 Obtener cercanas (útil para tu buscador)
    @Query("""
        SELECT * FROM parada 
        WHERE (
            (latitud - :lat) * (latitud - :lat) + 
            (longitud - :lng) * (longitud - :lng)
        ) < (:radio * :radio)
        ORDER BY 
            (latitud - :lat) * (latitud - :lat) + 
            (longitud - :lng) * (longitud - :lng)
        LIMIT :limite
    """)
    suspend fun getParadasCercanas(
        lat: Double,
        lng: Double,
        radio: Double,
        limite: Int = 10
    ): List<Parada>

    // 🔹 Eliminar (opcional)
    @Query("DELETE FROM parada WHERE idParada = :idParada")
    suspend fun deleteParada(idParada: Int)
}



@Dao
interface RutaParadaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRutaParada(rutaParada: RutaParada): Long

    @Query("SELECT * FROM ruta_parada WHERE idRuta = :idRuta ORDER BY orden ASC")
    suspend fun getRutasParadasByRuta(idRuta: Int): List<RutaParada>

    @Query("DELETE FROM ruta_parada WHERE idRuta = :idRuta")
    suspend fun deleteRutaParadas(idRuta: Int)
}


package com.ghettodev.rutago.data.repository

import android.content.Context
import android.util.Log
import com.ghettodev.rutago.data.dao.ParadaDao
import com.ghettodev.rutago.data.dao.RutaDao
import com.ghettodev.rutago.data.dao.RutaParadaDao
import com.ghettodev.rutago.data.entity.Parada
import com.ghettodev.rutago.data.entity.RutaParada
import com.ghettodev.rutago.parser.GeoJsonParser

private const val TAG = "RUTAGO_DEBUG"

class RutasRepository(
    private val rutaDao: RutaDao,
    private val paradaDao: ParadaDao,
    private val rutaParadaDao: RutaParadaDao,
    private val parser: GeoJsonParser
) {

    suspend fun cargarRutaSiNoExiste(context: Context, rutaKey: String): Int {
        val rutaKeyNormal = rutaKey.lowercase()
        Log.d(TAG, "=== INICIO cargarRuta: '$rutaKeyNormal' ===")

        try {
            // PASO 1: buscar en DB
            Log.d(TAG, "PASO 1: buscando en DB...")
            val rutas = rutaDao.getAllRutas()
            Log.d(TAG, "PASO 1: rutas en DB = ${rutas.size} -> keys: ${rutas.map { it.rutaKey }}")
            val existente = rutas.find { it.rutaKey == rutaKeyNormal }
            if (existente != null) {
                Log.d(TAG, "PASO 1: ruta ya existe, idRuta=${existente.idRuta}")
                return existente.idRuta
            }

            // PASO 2: abrir archivo
            Log.d(TAG, "PASO 2: abriendo archivo rutas/$rutaKeyNormal.geojson")
            val fileName = "rutas/$rutaKeyNormal.geojson"
            val input1 = context.assets.open(fileName)
            Log.d(TAG, "PASO 2: archivo abierto OK")

            // PASO 3: primer parseo
            Log.d(TAG, "PASO 3: parseando (primer paso)...")
            val resultPrevio = parser.parse(input1, idRuta = 0)
            Log.d(TAG, "PASO 3: ruta parseada = ${resultPrevio.ruta}")
            Log.d(TAG, "PASO 3: paradas parseadas = ${resultPrevio.paradas.size}")

            // PASO 4: insertar ruta
            Log.d(TAG, "PASO 4: insertando ruta...")
            val idRuta = rutaDao.insertRuta(resultPrevio.ruta).toInt()
            Log.d(TAG, "PASO 4: idRuta asignado = $idRuta")

            // PASO 5: segundo parseo con idRuta real
            Log.d(TAG, "PASO 5: segundo parseo con idRuta=$idRuta...")
            val input2 = context.assets.open(fileName)
            val result = parser.parse(input2, idRuta = idRuta)
            Log.d(TAG, "PASO 5: paradas con IDs únicos = ${result.paradas.size}")
            result.paradas.take(3).forEach {
                Log.d(TAG, "  parada: id=${it.idParada} lat=${it.latitud} lon=${it.longitud} nombre=${it.nombre}")
            }

            // PASO 6: insertar paradas
            Log.d(TAG, "PASO 6: insertando paradas...")
            paradaDao.insertParadas(result.paradas)
            Log.d(TAG, "PASO 6: paradas insertadas OK")

            // PASO 7: insertar relaciones
            Log.d(TAG, "PASO 7: creando relaciones...")
            val relaciones = result.paradas.map {
                RutaParada(idRuta = idRuta, idParada = it.idParada, orden = it.secuencia)
            }
            relaciones.forEach { rutaParadaDao.insertRutaParada(it) }
            Log.d(TAG, "PASO 7: relaciones insertadas OK = ${relaciones.size}")

            Log.d(TAG, "=== FIN cargarRuta OK, idRuta=$idRuta ===")
            return idRuta

        } catch (e: Exception) {
            Log.e(TAG, "=== ERROR en cargarRuta ===", e)
            Log.e(TAG, "Mensaje: ${e.message}")
            Log.e(TAG, "Causa: ${e.cause}")
            throw e
        }
    }

    suspend fun obtenerParadasDeRuta(idRuta: Int): List<Parada> {
        Log.d(TAG, "obtenerParadasDeRuta: idRuta=$idRuta")
        return try {
            val paradas = rutaDao.getParadasByRuta(idRuta)
            Log.d(TAG, "obtenerParadasDeRuta: resultado = ${paradas.size} paradas")
            paradas
        } catch (e: Exception) {
            Log.e(TAG, "ERROR en obtenerParadasDeRuta", e)
            throw e
        }
    }
}
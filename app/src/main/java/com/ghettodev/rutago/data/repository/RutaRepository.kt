package com.ghettodev.rutago.data.repository

import android.content.Context
import com.ghettodev.rutago.data.dao.ParadaDao
import com.ghettodev.rutago.data.dao.RutaDao
import com.ghettodev.rutago.data.dao.RutaParadaDao
import com.ghettodev.rutago.data.entity.Parada
import com.ghettodev.rutago.data.entity.RutaParada
import com.ghettodev.rutago.parser.GeoJsonParser


class RutasRepository(
    private val rutaDao: RutaDao,
    private val paradaDao: ParadaDao,
    private val rutaParadaDao: RutaParadaDao,
    private val parser: GeoJsonParser
) {

    suspend fun cargarRutaSiNoExiste(context: Context, rutaKey: String): Int {

        // 🔍 buscar si ya existe
        val rutas = rutaDao.getAllRutas()
        val existente = rutas.find { it.rutaKey == rutaKey }

        if (existente != null) {
            return existente.idRuta
        }

        // 📂 cargar desde assets
        val fileName = "rutas/${rutaKey.lowercase()}.geojson"
        val input = context.assets.open(fileName)

        val result = parser.parse(input)

        // 💾 guardar rutaUnresolved reference 'ruta'.
        val idRuta = rutaDao.insertRuta(result.ruta).toInt()

        // ✅ guardar TODAS las paradas (CORRECTO)
        paradaDao.insertParadas(result.paradas)

        // 🔗 relación
        val relaciones = result.paradas.map {
            RutaParada(
                idRuta = idRuta,
                idParada = it.idParada,
                orden = it.secuencia
            )
        }

        relaciones.forEach {
            rutaParadaDao.insertRutaParada(it)
        }

        return idRuta
    }

    suspend fun obtenerParadasDeRuta(idRuta: Int): List<Parada> {
        return rutaDao.getParadasByRuta(idRuta)
    }
}
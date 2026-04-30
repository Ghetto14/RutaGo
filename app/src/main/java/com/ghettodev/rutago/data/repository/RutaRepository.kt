package com.ghettodev.rutago.data.repository

import android.content.Context
import android.util.Log
import com.ghettodev.rutago.data.dao.RutaDao
import com.ghettodev.rutago.data.dao.ParadaDao
import com.ghettodev.rutago.data.dao.RutaParadaDao
import com.ghettodev.rutago.data.entity.Ruta
import com.ghettodev.rutago.data.entity.Parada
import com.ghettodev.rutago.data.entity.RutaParada
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class RutaConfig(
    val fileName: String,
    val nombreRuta: String,
    val color: String,
    val activo: Boolean = true
)

class RutaRepository(
    private val rutaDao: RutaDao,
    private val paradaDao: ParadaDao,
    private val rutaParadaDao: RutaParadaDao,
    private val context: Context
) {

    /**
     * Cargar una ruta desde GeoJSON
     */
    suspend fun loadRutaFromGeoJson(
        fileName: String,
        nombreRuta: String,
        color: String,
        activo: Boolean = true
    ): Result<Ruta> = try {
        val geoJsonString = readAssetFile("rutas/$fileName")
        val jsonObject = JSONObject(geoJsonString)

        // Extraer propiedades de FeatureCollection
        val props = jsonObject.optJSONObject("properties") ?: JSONObject()
        val rutaKey = props.optString("ruta", fileName.removeSuffix(".geojson"))
        val nRuta = props.optString("rutaNumero", "0").toIntOrNull() ?: 0
        val totalParadas = props.optInt("totalParadas", 0)
        val idaCount = props.optInt("idaCount", 0)
        val regresoCount = props.optInt("regresoCount", 0)
        val baseCount = props.optInt("baseCount", 0)
        val terminalCount = props.optInt("terminalCount", 0)

        val ruta = Ruta(
            idRuta = 0,
            nombreRuta = nombreRuta,
            nRuta = nRuta,
            rutaKey = rutaKey,
            color = color,
            geoJsonPolyline = geoJsonString,
            totalParadas = totalParadas,
            idaCount = idaCount,
            regresoCount = regresoCount,
            baseCount = baseCount,
            terminalCount = terminalCount,
            activo = activo
        )

        val rutaId = rutaDao.insertRuta(ruta).toInt()

        // Procesar paradas
        val features = jsonObject.getJSONArray("features")
        procesarParadas(features, rutaId)

        Log.d("RutaRepository", "✅ Ruta $rutaKey cargada: $totalParadas paradas")
        Result.success(ruta.copy(idRuta = rutaId))

    } catch (e: Exception) {
        Log.e("RutaRepository", "❌ Error cargando $fileName: ${e.message}", e)
        Result.failure(e)
    }

    /**
     * Cargar MÚLTIPLES rutas
     */
    suspend fun cargarMultiplesRutas(
        rutasConfig: List<RutaConfig>
    ): Result<List<Ruta>> = try {
        val rutasCargadas = mutableListOf<Ruta>()

        for (config in rutasConfig) {
            val resultado = loadRutaFromGeoJson(
                fileName = config.fileName,
                nombreRuta = config.nombreRuta,
                color = config.color,
                activo = config.activo
            )

            resultado.onSuccess { ruta ->
                rutasCargadas.add(ruta)
                Log.d("RutaRepository", "✅ Cargada: ${config.fileName}")
            }.onFailure { e ->
                Log.e("RutaRepository", "⚠️ Error en ${config.fileName}: ${e.message}")
            }
        }

        if (rutasCargadas.isNotEmpty()) {
            Result.success(rutasCargadas)
        } else {
            Result.failure(Exception("No se cargaron rutas"))
        }

    } catch (e: Exception) {
        Log.e("RutaRepository", "❌ Error cargando múltiples: ${e.message}", e)
        Result.failure(e)
    }

    /**
     * Procesar paradas del GeoJSON
     */
    private suspend fun procesarParadas(features: JSONArray, rutaId: Int) {
        var orden = 0

        for (i in 0 until features.length()) {
            try {
                val feature = features.getJSONObject(i)
                val geometry = feature.getJSONObject("geometry")
                val properties = feature.optJSONObject("properties") ?: continue

                if (geometry.getString("type") == "Point") {
                    val coordinates = geometry.getJSONArray("coordinates")
                    val longitude = coordinates.getDouble(0)
                    val latitude = coordinates.getDouble(1)

                    val parada = Parada(
                        idParada = properties.getInt("idParada"),
                        tipoParada = properties.optString("tipoParada", "normal"),
                        nombreParada = properties.optString("nombre", "Parada"),
                        latitud = latitude,
                        longitud = longitude,
                        color = properties.optString("color", "#FF6600"),
                        direccion = properties.optString("direccion", ""),
                        secuencia = properties.optInt("secuencia", i),
                        conexiones = properties.optString("conexiones", "")
                    )

                    paradaDao.insertParada(parada)

                    val rutaParada = RutaParada(
                        idRuta = rutaId,
                        idParada = parada.idParada,
                        orden = orden++,
                        tiempoEstimadoMinutos = properties.optInt("tiempoEstimado", 0)
                    )
                    rutaParadaDao.insertRutaParada(rutaParada)
                }
            } catch (e: Exception) {
                Log.e("RutaRepository", "⚠️ Error procesando parada $i: ${e.message}")
            }
        }

        Log.d("RutaRepository", "✅ Procesadas $orden paradas para ruta $rutaId")
    }

    suspend fun getRutasActivas() = rutaDao.getAllRutasActivas()

    suspend fun getRutaConParadas(idRuta: Int) = rutaDao.getRutaById(idRuta)

    suspend fun getParadasDeRuta(idRuta: Int) = rutaDao.getParadasByRuta(idRuta)

    suspend fun limpiarYCargarRutas(rutasConfig: List<RutaConfig>) {
        try {
            val rutasActuales = rutaDao.getAllRutas()
            for (ruta in rutasActuales) {
                rutaParadaDao.deleteRutaParadas(ruta.idRuta)
                rutaDao.deleteRuta(ruta.idRuta)
            }
            cargarMultiplesRutas(rutasConfig)
        } catch (e: Exception) {
            Log.e("RutaRepository", "Error limpiando: ${e.message}")
        }
    }

    private fun readAssetFile(fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }
}
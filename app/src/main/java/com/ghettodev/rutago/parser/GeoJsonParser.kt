package com.ghettodev.rutago.parser

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.ghettodev.rutago.data.entity.Parada
import com.ghettodev.rutago.data.entity.Ruta
import org.maplibre.geojson.FeatureCollection
import org.maplibre.geojson.Point
import java.io.InputStream

class GeoJsonParser {

    fun parse(inputStream: InputStream): RutaParseResult {

        // 🔹 Leer JSON completo
        val jsonString = inputStream.bufferedReader().use { it.readText() }

        // 🔹 Parsear raíz con Gson (para properties generales)
        val jsonObject = JsonParser.parseString(jsonString).asJsonObject
        val props = jsonObject.getAsJsonObject("properties")

        // 🔹 Parsear features con MapLibre
        val featureCollection = FeatureCollection.fromJson(jsonString)

        // 🔹 Crear Ruta
        val ruta = Ruta(
            nombreRuta = props?.get("ruta")?.asString ?: "",
            nRuta = safeInt(props, "rutaNumero"),
            rutaKey = props?.get("ruta")?.asString ?: "",
            color = "#FF6600",
            totalParadas = safeInt(props, "totalParadas"),
            idaCount = safeInt(props, "idaCount"),
            regresoCount = safeInt(props, "regresoCount"),
            baseCount = safeInt(props, "baseCount"),
            terminalCount = safeInt(props, "terminalCount")
        )

        // 🔹 Crear Paradas
        val paradas = featureCollection.features()?.map { feature ->

            val geometry = feature.geometry() as Point
            val p = feature.properties()

            Parada(
                idParada = safeInt(p, "idParada"),
                nombre = p?.get("nombre")?.asString ?: "",
                tipoParada = p?.get("tipoParada")?.asString ?: "",
                latitud = geometry.latitude(),
                longitud = geometry.longitude(),
                color = p?.get("color")?.asString ?: "#FF0000",
                direccion = p?.get("direccion")?.asString ?: "",
                secuencia = safeInt(p, "secuencia"),
                conexiones = p?.get("conexiones")?.asString ?: ""
            )

        } ?: emptyList()

        return RutaParseResult(ruta, paradas)
    }

    // 🔹 Función segura para Int (evita crashes por tipos mixtos)
    private fun safeInt(json: JsonObject?, key: String): Int {
        return try {
            json?.get(key)?.asInt ?: 0
        } catch (e: Exception) {
            try {
                json?.get(key)?.asString?.toInt() ?: 0
            } catch (e: Exception) {
                0
            }
        }
    }
}
package com.ghettodev.rutago.parser

import com.google.gson.JsonParser
import com.ghettodev.rutago.data.entity.Parada
import com.ghettodev.rutago.data.entity.Ruta
import java.io.InputStream

class GeoJsonParser {

    fun parse(inputStream: InputStream): RutaParseResult {
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val root = JsonParser.parseString(jsonString).asJsonObject

        val props = root.getAsJsonObject("properties")
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

        val featuresArray = root.getAsJsonArray("features")
        val paradas = mutableListOf<Parada>()

        for (featureElement in featuresArray) {
            val feature = featureElement.asJsonObject
            val geometry = feature.getAsJsonObject("geometry")
            val coordinatesArray = geometry.getAsJsonArray("coordinates")
            // Solo tomamos los dos primeros valores
            val lon = coordinatesArray[0].asDouble
            val lat = coordinatesArray[1].asDouble

            val p = feature.getAsJsonObject("properties")

            // Manejo seguro de conexiones
            val conexiones = when {
                p.has("conexiones") && p.get("conexiones").isJsonArray -> {
                    p.getAsJsonArray("conexiones").joinToString(",") { it.asString }
                }
                p.has("conexiones") && p.get("conexiones").isJsonPrimitive -> {
                    p.get("conexiones").asString
                }
                else -> ""
            }

            val parada = Parada(
                idParada = safeInt(p, "idParada"),
                nombre = p.get("nombre")?.asString ?: "",
                tipoParada = p.get("tipoParada")?.asString ?: "",
                latitud = lat,
                longitud = lon,
                color = p.get("color")?.asString ?: "#FF0000",
                direccion = p.get("direccion")?.asString ?: "",
                secuencia = safeInt(p, "secuencia"),
                conexiones = conexiones
            )
            paradas.add(parada)
        }

        return RutaParseResult(ruta, paradas)
    }

    private fun safeInt(json: com.google.gson.JsonObject?, key: String): Int {
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
package com.ghettodev.rutago.ui.components

import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.ghettodev.rutago.data.entity.Ruta
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.style.layers.CircleLayer
import org.maplibre.android.style.layers.PropertyFactory
import org.maplibre.android.style.sources.GeoJsonSource

private const val TAG = "MapItem"

@Composable
fun MapItem(
    modifier: Modifier = Modifier,
    userLocation: Location? = null,
    todasLasRutas: List<Ruta> = emptyList(),
    mostrarTodasLasRutas: Boolean = false
) {
    val context = LocalContext.current

    // 1. Crear MapView con ciclo de vida mínimo
    val mapView = remember {
        MapView(context).apply {
            // Importante: onCreate con Bundle vacío
            onCreate(Bundle())
        }
    }

    // 2. Gestionar ciclo de vida del mapa
    DisposableEffect(Unit) {
        mapView.onStart()
        mapView.onResume()
        onDispose {
            mapView.onPause()
            mapView.onStop()
            mapView.onDestroy()
        }
    }

    // 3. Referencia al objeto MapLibreMap
    var mapLibre by remember { mutableStateOf<MapLibreMap?>(null) }

    // 4. Cargar el mapa asíncronamente UNA SOLA VEZ
    LaunchedEffect(Unit) {
        mapView.getMapAsync { map ->
            Log.d(TAG, "Mapa obtenido")
            map.setStyle("https://tiles.openfreemap.org/styles/liberty") { style ->
                Log.d(TAG, "Estilo cargado")
                mapLibre = map
            }
        }
    }

    // 5. Actualizar las rutas cuando cambien los datos o el mapa esté listo
    LaunchedEffect(mapLibre, todasLasRutas, mostrarTodasLasRutas) {
        val map = mapLibre ?: return@LaunchedEffect
        val style = map.style ?: return@LaunchedEffect

        if (!mostrarTodasLasRutas) {
            // Eliminar capas y fuentes de rutas existentes
            todasLasRutas.forEach { ruta ->
                val sourceId = "source-${ruta.idRuta}"
                val layerId = "layer-${ruta.idRuta}"
                try {
                    style.removeLayer(layerId)
                    style.removeSource(sourceId)
                } catch (e: Exception) {
                    Log.e(TAG, "Error al limpiar ruta ${ruta.idRuta}", e)
                }
            }
            return@LaunchedEffect
        }

        // Agregar cada ruta
        todasLasRutas.forEach { ruta ->
            try {
                val geoJson = ruta.geoJsonPolyline
                if (geoJson.isNullOrEmpty()) {
                    Log.w(TAG, "Ruta ${ruta.idRuta} sin geoJson")
                    return@forEach
                }

                val sourceId = "source-${ruta.idRuta}"
                val layerId = "layer-${ruta.idRuta}"

                // Fuente: si ya existe la actualizamos, si no la añadimos
                val existingSource = style.getSource(sourceId)
                if (existingSource is GeoJsonSource) {
                    existingSource.setGeoJson(geoJson)
                    Log.d(TAG, "Fuente actualizada: $sourceId")
                } else {
                    if (existingSource != null) style.removeSource(sourceId)
                    val source = GeoJsonSource(sourceId, geoJson)
                    style.addSource(source)
                    Log.d(TAG, "Fuente agregada: $sourceId")
                }

                // Capa: solo si no existe ya
                if (style.getLayer(layerId) == null) {
                    val color = try {
                        android.graphics.Color.parseColor(ruta.color)
                    } catch (e: Exception) {
                        android.graphics.Color.RED
                    }
                    val layer = CircleLayer(layerId, sourceId).withProperties(
                        PropertyFactory.circleRadius(8f),
                        PropertyFactory.circleColor(color),
                        PropertyFactory.circleOpacity(0.8f)
                    )
                    style.addLayer(layer)
                    Log.d(TAG, "Capa agregada: $layerId, color ${ruta.color}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error procesando ruta ${ruta.idRuta}", e)
                e.printStackTrace()
            }
        }
    }

    // 6. Mostrar el MapView
    AndroidView(
        modifier = modifier,
        factory = { mapView }
    )
}
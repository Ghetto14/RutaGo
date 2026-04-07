package com.ghettodev.rutago.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.compose.map.MapOptions
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.map.OrnamentOptions
import org.maplibre.compose.style.BaseStyle
import org.maplibre.spatialk.geojson.Position
import kotlin.time.Duration.Companion.seconds

@Composable
fun MapItem(modifier: Modifier = Modifier) {
    // Estado de la camara
    val camera = rememberCameraState(
        firstPosition =
            CameraPosition(
                target = Position(
                    latitude = 17.076723,
                    longitude = -96.744502
                ),
                zoom = 16.0
            )
    )
    MaplibreMap (
        modifier = modifier,
        cameraState = camera,
        options = MapOptions(
            // adornos del mapa
            ornamentOptions =
                OrnamentOptions(
                    padding = PaddingValues(16.dp),
                    isAttributionEnabled = true, // Creditos de los datos del mapa
                    attributionAlignment = Alignment.BottomEnd,
                    isCompassEnabled = true, // Brujula orientada al norte
                    compassAlignment = Alignment.TopEnd,
                    isScaleBarEnabled = true, // Barra de escala
                    scaleBarAlignment = Alignment.TopStart
                )
        ),

        baseStyle = BaseStyle.Uri("https://tiles.openfreemap.org/styles/liberty")
    ){

    }
}
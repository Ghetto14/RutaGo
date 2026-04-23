package com.ghettodev.rutago.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.location.Location
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.compose.map.MapOptions
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.map.OrnamentOptions
import org.maplibre.compose.style.BaseStyle
import org.maplibre.spatialk.geojson.Position  // ← CORRECTO para 0.12.1

@Composable
fun MapItem(
    modifier: Modifier = Modifier,
    userLocation: Location? = null
) {
    // Ubicación inicial: se actualiza si `userLocation` cambia
    val initialPosition = if (userLocation != null) {
        Position(
            latitude = userLocation.latitude,
            longitude = userLocation.longitude
        )
    } else {
        // Fallback: Oaxaca centro
        Position(latitude = 17.076723, longitude = -96.744502)
    }

    val camera = rememberCameraState(
        firstPosition = CameraPosition(
            target = initialPosition,
            zoom = 16.0
        )
    )

    // Si la ubicación cambia, actualiza la cámara
    LaunchedEffect(userLocation) {
        if (userLocation != null) {
            camera.animateTo(
                CameraPosition(
                    target = Position(
                        latitude = userLocation.latitude,
                        longitude = userLocation.longitude
                    ),
                    zoom = 16.0
                )
            )
        }
    }

    MaplibreMap(
        modifier = modifier,
        cameraState = camera,
        options = MapOptions(
            ornamentOptions = OrnamentOptions(
                padding = PaddingValues(16.dp),
                isAttributionEnabled = true,
                attributionAlignment = Alignment.BottomEnd,
                isCompassEnabled = true,
                compassAlignment = Alignment.TopEnd,
                isScaleBarEnabled = true,
                scaleBarAlignment = Alignment.TopStart
            )
        ),
        baseStyle = BaseStyle.Uri("https://tiles.openfreemap.org/styles/liberty")
    ) {
        // Aquí irán los marcadores después
    }
}
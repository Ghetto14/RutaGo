package com.ghettodev.rutago.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ghettodev.rutago.data.entity.Parada
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.style.BaseStyle
import org.maplibre.spatialk.geojson.Position

@Composable
fun MapItem(
    paradas: List<Parada>,   // ← nuevo parámetro, por ahora no usado
    modifier: Modifier = Modifier
) {
    val cameraState = rememberCameraState(
        firstPosition = CameraPosition(
            target = Position(-96.744502, 17.076723),
            zoom = 14.0
        )
    )

    MaplibreMap(
        modifier = modifier.fillMaxSize(),
        cameraState = cameraState,
        baseStyle = BaseStyle.Uri("https://tiles.openfreemap.org/styles/liberty")
    )
}
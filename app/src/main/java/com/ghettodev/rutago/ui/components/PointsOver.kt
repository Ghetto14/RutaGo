package com.ghettodev.rutago.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.ghettodev.rutago.data.entity.Parada
import org.maplibre.compose.camera.CameraState
import org.maplibre.spatialk.geojson.Position

@Composable
fun PointsOverlay(
    paradas: List<Parada>,
    cameraState: CameraState,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    Canvas(modifier = modifier.fillMaxSize()) {
        // Radio y ancho del borde en píxeles (fijos)
        val radiusPx = 12f * density.density
        val strokeWidthPx = 2f * density.density

        if (paradas.isNotEmpty() && cameraState.projection != null) {
            for (parada in paradas) {
                val dpOffset = cameraState.projection?.screenLocationFromPosition(
                    Position(parada.longitud, parada.latitud)
                )
                if (dpOffset != null) {
                    // Convertir Dp a píxeles
                    val xPx = with(density) { dpOffset.x.toPx() }
                    val yPx = with(density) { dpOffset.y.toPx() }

                    // Círculo rojo
                    drawCircle(
                        color = Color.Red,
                        radius = radiusPx,
                        center = Offset(xPx, yPx)
                    )
                    // Borde blanco
                    drawCircle(
                        color = Color.White,
                        radius = radiusPx + strokeWidthPx,
                        center = Offset(xPx, yPx),
                        style = Stroke(width = strokeWidthPx)
                    )
                }
            }
        }
    }
}
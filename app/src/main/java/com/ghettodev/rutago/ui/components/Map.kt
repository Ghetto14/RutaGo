package com.ghettodev.rutago.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ghettodev.rutago.data.entity.Parada
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.compose.expressions.dsl.const
import org.maplibre.compose.layers.CircleLayer
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.sources.GeoJsonData
import org.maplibre.compose.sources.rememberGeoJsonSource
import org.maplibre.compose.style.BaseStyle
import org.maplibre.spatialk.geojson.Feature
import org.maplibre.spatialk.geojson.FeatureCollection
import org.maplibre.spatialk.geojson.Point
import org.maplibre.spatialk.geojson.Position

@Composable
fun MapItem(
    paradas: List<Parada> = emptyList(),
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
    ) {
        if (paradas.isNotEmpty()) {
            val features = paradas.mapNotNull { parada ->
                if (parada.latitud in -90.0..90.0 && parada.longitud in -180.0..180.0) {
                    Feature(
                        geometry = Point(Position(parada.longitud, parada.latitud)),
                        properties = null
                    )
                } else null
            }

            if (features.isNotEmpty()) {
                val source = rememberGeoJsonSource(
                    data = GeoJsonData.Features(FeatureCollection(features))
                )

                CircleLayer(
                    id = "paradas-layer",
                    source = source,
                    color = const(Color.Red),
                    radius = const(8.dp)
                )
            }
        }
    }
}
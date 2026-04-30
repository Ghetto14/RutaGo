package com.ghettodev.rutago.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import android.location.Location
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.compose.expressions.dsl.const
import org.maplibre.compose.layers.CircleLayer
import org.maplibre.compose.map.MapOptions
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.map.OrnamentOptions
import org.maplibre.compose.sources.rememberGeoJsonSource
import org.maplibre.compose.style.BaseStyle
import org.maplibre.compose.sources.GeoJsonData
import org.maplibre.spatialk.geojson.Position

@Composable
fun MapItem(
    modifier: Modifier = Modifier,
    userLocation: Location? = null
) {
    val initialPosition = if (userLocation != null) {
        Position(latitude = userLocation.latitude, longitude = userLocation.longitude)
    } else {
        Position(latitude = 17.076723, longitude = -96.744502)
    }

    val camera = rememberCameraState(
        firstPosition = CameraPosition(target = initialPosition, zoom = 16.0)
    )

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
        if (userLocation != null) {
            val source = rememberGeoJsonSource(
                data = GeoJsonData.JsonString(
                    """
                    {
                      "type": "FeatureCollection",
                      "features": [{
                        "type": "Feature",
                        "properties": {},
                        "geometry": {
                          "type": "Point",
                          "coordinates": [${userLocation.longitude}, ${userLocation.latitude}]
                        }
                      }]
                    }
                    """.trimIndent()
                )
            )

            CircleLayer(
                id = "user-location-layer",
                source = source,
                radius = const(10.dp),
                color = const(Color(0xFF007AFF)),
                strokeColor = const(Color.White),
                strokeWidth = const(2.dp)
            )
        }
    }

}


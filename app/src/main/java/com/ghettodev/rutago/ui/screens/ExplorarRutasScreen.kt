package com.ghettodev.rutago.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import android.Manifest
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.tooling.preview.Preview
import com.ghettodev.rutago.data.location.LocationService
import com.ghettodev.rutago.ui.components.MapItem
import kotlinx.coroutines.launch
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ERutasS(
    locationService: LocationService,
    onReporteClic: () -> Unit = {}
) {
    // Estado: ubicación actual del usuario
    var userLocation by remember { mutableStateOf<Location?>(null) }

    // Estado: si está cargando la ubicación
    var isLoading by remember { mutableStateOf(false) }

    // Scope para corrutinas
    val scope = rememberCoroutineScope()

    // Atributos de la barra de búsqueda
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    // Launcher: pide permiso al usuario
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { permissionGranted ->
        if (permissionGranted) {
            // El usuario otorgó permiso, ahora obtén la ubicación
            isLoading = true
            scope.launch {
                userLocation = locationService.getCurrentLocation()
                isLoading = false
            }
        }
    }

    // Estructura general
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Mapa con ubicación del usuario
        MapItem(
            userLocation = userLocation
        )

        // Barra de búsqueda (arriba)
        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            SearchBar(
                query = text,
                onQueryChange = { text = it },
                onSearch = {
                    active = false
                    // Ejecutar lógica de búsqueda
                },
                active = active,
                onActiveChange = { active = it },
                placeholder = { Text("Buscar destino") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Barra de búsqueda"
                    )
                },
                trailingIcon = {
                    if (active) {
                        IconButton(
                            onClick = {
                                if (text.isNotEmpty()) text = "" else active = false
                            }
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Cerrar")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                // Contenido que se muestra debajo cuando 'active' es true
            }
        }

        // Botones flotantes (abajo a la derecha)
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Botón "Mi ubicación" (arriba)
            FloatingActionButton(
                onClick = {
                    // Pide el permiso
                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                },
                modifier = Modifier.size(56.dp),
                containerColor = Color.Blue
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp,
                        color = Color.White
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Mi ubicación",
                        tint = Color.White
                    )
                }
            }

            // Botón "Reportar" (abajo)
            FloatingActionButton(
                onClick = {
                    onReporteClic()
                },
                modifier = Modifier.size(70.dp),
                containerColor = Color.Red
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Reportar",
                        tint = Color.White
                    )
                    Text(
                        text = "Reportar",
                        color = Color.White
                    )
                }
            }
        }
    }
}
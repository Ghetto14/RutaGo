package com.ghettodev.rutago.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.ghettodev.rutago.data.location.LocationService
import com.ghettodev.rutago.ui.components.MapItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ERutasS(
    locationService: LocationService,
    onReporteClic: () -> Unit = {}
) {
    var userLocation by remember { mutableStateOf<Location?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { permissionGranted ->
        if (permissionGranted) {
            isLoading = true
            scope.launch {
                val location = locationService.getCurrentLocation()
                Log.d("UBICACION", "Permiso otorgado - lat: ${location?.latitude}, lng: ${location?.longitude}")
                userLocation = location
                isLoading = false
            }
        }
    }

    Box(modifier = Modifier.fillMaxWidth()) {

        MapItem(userLocation = userLocation)

        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            SearchBar(
                query = text,
                onQueryChange = { text = it },
                onSearch = { active = false },
                active = active,
                onActiveChange = { active = it },
                placeholder = { Text("Buscar destino") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Barra de búsqueda")
                },
                trailingIcon = {
                    if (active) {
                        IconButton(onClick = {
                            if (text.isNotEmpty()) text = "" else active = false
                        }) {
                            Icon(Icons.Default.Close, contentDescription = "Cerrar")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {}
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FloatingActionButton(
                onClick = {
                    val hasPermission = ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED

                    Log.d("UBICACION", "Botón presionado - tiene permiso: $hasPermission")

                    if (hasPermission) {
                        isLoading = true
                        scope.launch {
                            val location = locationService.getCurrentLocation()
                            Log.d("UBICACION", "lat: ${location?.latitude}, lng: ${location?.longitude}")
                            userLocation = location
                            isLoading = false
                        }
                    } else {
                        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    }
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

            FloatingActionButton(
                onClick = { onReporteClic() },
                modifier = Modifier.size(70.dp),
                containerColor = Color.Red
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Reportar",
                        tint = Color.White
                    )
                    Text(text = "Reportar", color = Color.White)
                }
            }
        }
    }
}
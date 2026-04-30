package com.ghettodev.rutago.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ghettodev.rutago.data.repository.RutaRepository
import com.ghettodev.rutago.ui.viewmodel.RutaViewModel
import com.ghettodev.rutago.ui.components.MapItem
import kotlinx.coroutines.launch
import android.location.Location
import com.ghettodev.rutago.data.location.LocationService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ERutasS(
    locationService: LocationService,
    rutaRepository: RutaRepository,
    onReporteClic: () -> Unit = {}
) {
    val rutaViewModel: RutaViewModel = viewModel(
        factory = object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return RutaViewModel(rutaRepository) as T
            }
        }
    )

    var userLocation by remember { mutableStateOf<Location?>(null) }
    var isLoadingLocation by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val uiState by rutaViewModel.uiState.collectAsState()

    var searchText by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { permissionGranted ->
        if (permissionGranted) {
            isLoadingLocation = true
            scope.launch {
                val location = locationService.getCurrentLocation()
                userLocation = location
                rutaViewModel.actualizarUbicacionUsuario(location)
                isLoadingLocation = false
            }
        }
    }

    // Cargar todas las rutas al iniciar
    LaunchedEffect(Unit) {
        rutaViewModel.cargarTodasLasRutas()
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // Mapa
        MapItem(
            modifier = Modifier.fillMaxSize(),
            userLocation = userLocation,
            todasLasRutas = uiState.rutasDisponibles,
            mostrarTodasLasRutas = uiState.mostrarTodasLasRutas
        )

        // Barra de búsqueda
        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            SearchBar(
                query = searchText,
                onQueryChange = { searchText = it },
                onSearch = {
                    isSearchActive = false
                    val rutaBuscada = uiState.rutasDisponibles.find {
                        it.nombreRuta.contains(searchText, ignoreCase = true) ||
                                it.nRuta.toString() == searchText ||
                                it.rutaKey.contains(searchText, ignoreCase = true)
                    }
                    rutaBuscada?.let { ruta ->
                        rutaViewModel.cargarRutaEspecifica(ruta.idRuta)
                    }
                },
                active = isSearchActive,
                onActiveChange = { isSearchActive = it },
                placeholder = { Text("Buscar: Ruta Santa María, RA-02, 2") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                },
                trailingIcon = {
                    if (isSearchActive && searchText.isNotEmpty()) {
                        IconButton(onClick = { searchText = "" }) {
                            Icon(Icons.Default.Close, contentDescription = null)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = SearchBarDefaults.colors(
                    containerColor = Color.White.copy(alpha = 0.95f)
                )
            ) {}
        }

        // Botones flotantes
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Botón: Ver todas las rutas / Una ruta
            FloatingActionButton(
                onClick = { rutaViewModel.toggleMostrarTodasLasRutas() },
                containerColor = if (uiState.mostrarTodasLasRutas) Color.Green else Color.Cyan,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Map,
                    contentDescription = "Ver todas las rutas",
                    tint = Color.White
                )
            }

            // Botón: Mi ubicación
            FloatingActionButton(
                onClick = {
                    val hasPermission = ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED

                    if (hasPermission) {
                        isLoadingLocation = true
                        scope.launch {
                            val location = locationService.getCurrentLocation()
                            userLocation = location
                            rutaViewModel.actualizarUbicacionUsuario(location)
                            isLoadingLocation = false
                        }
                    } else {
                        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    }
                },
                containerColor = Color.Blue,
                modifier = Modifier.size(56.dp)
            ) {
                if (isLoadingLocation || uiState.isLoading) {
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

            // Botón: Reportar
            FloatingActionButton(
                onClick = { onReporteClic() },
                containerColor = Color.Red,
                modifier = Modifier.size(70.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Reportar",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Reportar",
                        color = Color.White,
                        fontSize = 9.sp
                    )
                }
            }
        }

        // Indicador de carga múltiples
        if (uiState.cargandoMultiples) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.background(
                        Color.White,
                        shape = MaterialTheme.shapes.medium
                    ).padding(24.dp)
                ) {
                    CircularProgressIndicator(color = Color.Blue)
                    Text(
                        "Cargando ${uiState.rutasCargadas} rutas...",
                        fontSize = 16.sp
                    )
                }
            }
        }

        // Mostrar errores
        if (uiState.error != null) {
            Snackbar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                action = {
                    Button(onClick = { rutaViewModel.limpiarError() }) {
                        Text("OK")
                    }
                }
            ) {
                Text("❌ ${uiState.error}")
            }
        }
    }
}
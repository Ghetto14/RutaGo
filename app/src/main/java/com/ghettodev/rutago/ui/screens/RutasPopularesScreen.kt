package com.ghettodev.rutago.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModelProvider
import com.ghettodev.rutago.data.repository.RutasRepository
import com.ghettodev.rutago.viewmodel.RutasPopularesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RutasPopularesScreen(
    rutaRepository: RutasRepository,
    onRutaClick: (Int) -> Unit,
    onBack: () -> Unit
) {
    val viewModel: RutasPopularesViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return RutasPopularesViewModel(rutaRepository) as T
            }
        }
    )

    val rutas by viewModel.rutas.collectAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.collectAsState(true)

    LaunchedEffect(Unit) {
        viewModel.loadRutas()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Rutas Populares") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { paddingValues ->
        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            rutas.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay rutas disponibles")
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.padding(paddingValues),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(rutas) { ruta ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onRutaClick(ruta.idRuta) },
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = ruta.nombreRuta,
                                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                    Text(
                                        text = "Paradas: ${ruta.totalParadas}",
                                        fontSize = 14.sp,
                                        color = androidx.compose.ui.graphics.Color.Gray
                                    )
                                }
                                Icon(Icons.Default.NavigateNext, contentDescription = "Ver detalles")
                            }
                        }
                    }
                }
            }
        }
    }
}
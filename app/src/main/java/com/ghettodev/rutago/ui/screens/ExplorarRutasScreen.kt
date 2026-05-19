package com.ghettodev.rutago.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ghettodev.rutago.data.repository.RutasRepository
import com.ghettodev.rutago.ui.components.MapItem
import com.ghettodev.rutago.viewmodel.RutaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ERutasS(
    rutaRepository: RutasRepository,
    onReporteClic: () -> Unit = {}
) {
    var searchText by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    val rutaViewModel: RutaViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RutaViewModel(rutaRepository) as T
            }
        }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Mapa (funcional)
        MapItem(modifier = Modifier.fillMaxSize())

        // Barra de búsqueda superior (solo UI, sin lógica)
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            SearchBar(
                query = searchText,
                onQueryChange = { searchText = it },
                onSearch = { /* vacío por ahora */ },
                active = isSearchActive,
                onActiveChange = { isSearchActive = it },
                placeholder = { Text("Buscar ruta...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                trailingIcon = {
                    if (isSearchActive && searchText.isNotEmpty()) {
                        IconButton(onClick = { searchText = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "Limpiar")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = SearchBarDefaults.colors(
                    containerColor = Color.White.copy(alpha = 0.95f)
                )
            ) {
                // Aquí no hay sugerencias todavía
            }
        }

        // Botón de reporte (abajo derecha)
        FloatingActionButton(
            onClick = onReporteClic,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Color.Red
        ) {
            Icon(Icons.Default.Warning, contentDescription = "Reportar", tint = Color.White)
        }
    }
}
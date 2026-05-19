package com.ghettodev.rutago.ui.screens

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ghettodev.rutago.data.repository.RutasRepository
import com.ghettodev.rutago.ui.components.MapItem
import com.ghettodev.rutago.viewmodel.RutaViewModel

fun formatRutaKey(input: String): String {
    val text = input.trim()
    return when {
        text.all { it.isDigit() } -> "ra-" + text.padStart(2, '0')
        text.startsWith("ra", ignoreCase = true) -> text.lowercase()
        else -> text.lowercase()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ERutasS(
    rutaRepository: RutasRepository,
    onReporteClic: () -> Unit = {}
) {
    val context = LocalContext.current

    val rutaViewModel: RutaViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return RutaViewModel(rutaRepository) as T
            }
        }
    )

    val uiState by rutaViewModel.uiState.collectAsState()
    var searchText by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }

    // Mostrar Toast cuando se cargan paradas exitosamente
    LaunchedEffect(uiState.paradasMostradas) {
        if (uiState.paradasMostradas.isNotEmpty()) {
            Toast.makeText(
                context,
                "✅ Ruta cargada: ${uiState.paradasMostradas.size} paradas",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Mostrar Toast cuando hay error (además del Card)
    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            Toast.makeText(
                context,
                "❌ Error: ${uiState.error}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Mapa (aún sin puntos)
        MapItem(
            paradas = emptyList(),
            modifier = Modifier.fillMaxSize()
        )

        // Barra de búsqueda
        Column(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
            SearchBar(
                query = searchText,
                onQueryChange = { searchText = it },
                onSearch = {
                    isSearchActive = false
                    if (searchText.isNotBlank()) {
                        val rutaKey = formatRutaKey(searchText)
                        // Toast de inicio de búsqueda
                        Toast.makeText(context, "🔍 Buscando ruta: $rutaKey", Toast.LENGTH_SHORT).show()
                        rutaViewModel.cargarRuta(context, rutaKey)
                    }
                },
                active = isSearchActive,
                onActiveChange = { isSearchActive = it },
                placeholder = { Text("Ej: RA-02, 2, 03") },
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
            ) {}
        }

        // Botón de reporte
        FloatingActionButton(
            onClick = onReporteClic,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Color.Red
        ) {
            Icon(Icons.Default.Warning, contentDescription = "Reportar", tint = Color.White)
        }

        // Mensaje de error (card)
        if (uiState.error != null) {
            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                Text(
                    text = "❌ ${uiState.error}",
                    modifier = Modifier.padding(12.dp),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
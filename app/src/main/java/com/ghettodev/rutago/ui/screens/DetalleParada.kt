package com.ghettodev.rutago.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModelProvider
import com.ghettodev.rutago.data.repository.RutasRepository
import com.ghettodev.rutago.ui.components.ItemParadas
import com.ghettodev.rutago.ui.components.ProximoBus
import com.ghettodev.rutago.ui.theme.PrimaryGreen
import com.ghettodev.rutago.ui.theme.blur
import com.ghettodev.rutago.viewmodel.RutaDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleParada(
    idRuta: Int,  // ← recibimos el ID de la ruta
    rutaRepository: RutasRepository,
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current

    val viewModel: RutaDetailViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return RutaDetailViewModel(rutaRepository) as T
            }
        }
    )

    val uiState by viewModel.uiState.collectAsState()

    // Cargar datos cuando se abre la pantalla
    LaunchedEffect(idRuta) {
        viewModel.loadRutaDetail(idRuta)
    }

    // Mientras carga, mostramos un indicador
    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    // Si hay error, mostramos mensaje
    if (uiState.error != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Error: ${uiState.error}")
        }
        return
    }

    val ruta = uiState.ruta ?: return
    val paradas = uiState.paradas

    // Contenido principal
    Box {
        Column(modifier = Modifier.background(Color.White)) {
            // Header con información de la ruta
            Box(
                modifier = Modifier
                    .background(PrimaryGreen)
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 25.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = onBack,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(blur)
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                        IconButton(
                            onClick = { /* favoritos */ },
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(blur)
                        ) {
                            Icon(Icons.Default.Favorite, contentDescription = "Favorite", tint = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = ruta.nombreRuta,
                        fontSize = 15.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .clip(RoundedCornerShape(15.dp))
                            .background(blur)
                            .padding(10.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "${ruta.nombreRuta} - ${if (ruta.idaCount > 0) "IDA" else "REGRESO"}",
                        fontSize = 28.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Schedule, contentDescription = "Duración", tint = Color.White, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "45 min", fontSize = 18.sp, color = Color.White)  // puedes calcular duración si la tienes

                        Spacer(modifier = Modifier.width(25.dp))

                        Icon(Icons.Default.LocationOn, contentDescription = "Paradas", tint = Color.White, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "${ruta.totalParadas} Paradas", fontSize = 18.sp, color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

            // Lista de paradas
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .wrapContentHeight()
                    .padding(5.dp)
            ) {
                LazyColumn {
                    items(paradas) { parada ->
                        ItemParadas(
                            nombre = parada.nombre,
                            direccion = parada.direccion,
                            tipo = parada.tipoParada
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }

        // Tarjeta flotante (próximo bus) – puedes personalizar con datos reales
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 230.dp)
                .padding(horizontal = 15.dp)
        ) {
            ProximoBus()
        }
    }
}
package com.ghettodev.rutago.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModelProvider
import com.ghettodev.rutago.R
import com.ghettodev.rutago.data.entity.Ruta
import com.ghettodev.rutago.data.repository.RutasRepository
import com.ghettodev.rutago.ui.components.CardRutasPopulares
import com.ghettodev.rutago.ui.theme.PrimaryGreen
import com.ghettodev.rutago.ui.theme.TextGray
import com.ghettodev.rutago.ui.theme.backgroundC
import com.ghettodev.rutago.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    rutaRepository: RutasRepository,
    onExplorarRutas: () -> Unit = {},
    onVerTodas: () -> Unit = {},
    onRutaClick: (Int) -> Unit = {}
) {
    val context = LocalContext.current

    val viewModel: HomeViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(rutaRepository) as T
            }
        }
    )

    val rutas by viewModel.rutas.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Cargar rutas al iniciar la pantalla
    LaunchedEffect(Unit) {
        viewModel.loadRutas()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundC)
            .padding(20.dp)
    ) {
        // Saludo
        Saludo()

        Spacer(modifier = Modifier.height(24.dp))

        // Botones de acción
        ButtonAction(onExplorarRutas = onExplorarRutas)

        Spacer(modifier = Modifier.height(35.dp))

        // Título "Rutas populares" y botón "Ver todas"
        RutasTexto(onVerTodas = onVerTodas)

        Spacer(modifier = Modifier.height(20.dp))

        // Lista de rutas (solo 3 en el home, pero puedes mostrar todas con rutas.take(3))
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (rutas.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay rutas disponibles.\nBusca una en el mapa primero.")
            }
        } else {
            LazyColumn {
                items(rutas.take(3)) { ruta ->  // Muestra solo las primeras 3 en el home
                    CardRutasPopulares(
                        ruta = ruta,
                        onCardClick = { onRutaClick(ruta.idRuta) }
                    )
                }
            }
        }
    }
}

@Composable
fun Saludo() {
    Text(
        text = "Hola Usuario",
        fontSize = 29.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "¿A dónde te diriges hoy?",
        fontSize = 16.sp,
        color = TextGray
    )
}

@Composable
fun ButtonAction(
    onExplorarRutas: () -> Unit = {}
) {
    Button(
        onClick = onExplorarRutas,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryGreen
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.location_on),
            contentDescription = "logo ubicacion",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Explorar rutas",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
fun RutasTexto(
    onVerTodas: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Rutas populares",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        Text(
            modifier = Modifier.clickable { onVerTodas() },
            text = "Ver todas",
            fontSize = 14.sp,
            color = PrimaryGreen
        )
    }
}
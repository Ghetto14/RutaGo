package com.ghettodev.rutago.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ghettodev.rutago.ui.components.MapItem

@Composable
fun ERutasS(
    onReporteClic: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // SOLO EL MAPA
        MapItem(modifier = Modifier.fillMaxSize())

        // BOTÓN DE REPORTE (opcional, pero lo dejamos)
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
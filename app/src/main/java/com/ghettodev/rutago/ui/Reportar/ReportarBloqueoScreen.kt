package com.ghettodev.rutago.ui.Reportar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ghettodev.rutago.ui.theme.RutaGoTheme

data class TipoBloqueo(
    val emoji: String,
    val nombre: String
)

@Composable
fun ReportarBloqueoScreen(
    onCancelar: () -> Unit = {},
    onEnviar: (String, String, String) -> Unit = { _, _, _ -> }
) {
    var tipoSeleccionado by remember { mutableStateOf("") }
    var ubicacion by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    val tipos = listOf(
        TipoBloqueo("🚧", "Obra en construcción"),
        TipoBloqueo("🚨", "Accidente"),
        TipoBloqueo("🎭", "Manifestación/Evento"),
        TipoBloqueo("🚦", "Tráfico intenso"),
        TipoBloqueo("❓", "Otro")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "⚠️", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Reportar Bloqueo",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            Text(
                text = "✕",
                fontSize = 20.sp,
                color = Color.Gray,
                modifier = Modifier.clickable { onCancelar() }
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFEBEB), RoundedCornerShape(12.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "ℹ️", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Tu reporte ayudará a otros usuarios a encontrar rutas alternativas",
                    fontSize = 13.sp,
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Tipo de bloqueo",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            tipos.forEach { tipo ->
                val seleccionado = tipoSeleccionado == tipo.nombre
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .background(
                            if (seleccionado) Color(0xFFE8F5E9) else Color.White,
                            RoundedCornerShape(12.dp)
                        )
                        .border(
                            width = if (seleccionado) 2.dp else 1.dp,
                            color = if (seleccionado) Color(0xFF34A853) else Color(0xFFE0E0E0),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { tipoSeleccionado = tipo.nombre }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = tipo.emoji, fontSize = 22.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = tipo.nombre,
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Ubicación",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = ubicacion,
                onValueChange = { ubicacion = it },
                placeholder = { Text("Ej: Av. Independencia esquina con 5 de Mayo") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF34A853),
                    unfocusedBorderColor = Color(0xFFE0E0E0)
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Descripción (opcional)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                placeholder = { Text("Agrega detalles adicionales...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF34A853),
                    unfocusedBorderColor = Color(0xFFE0E0E0)
                )
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Button(
                onClick = { onEnviar(tipoSeleccionado, ubicacion, descripcion) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF34A853)
                ),
                enabled = tipoSeleccionado.isNotEmpty() && ubicacion.isNotEmpty()
            ) {
                Text(
                    text = "Enviar Reporte",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onCancelar,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.Gray
                )
            ) {
                Text(text = "Cancelar", fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReportarBloqueoScreenPreview() {
    RutaGoTheme {
        ReportarBloqueoScreen()
    }
}
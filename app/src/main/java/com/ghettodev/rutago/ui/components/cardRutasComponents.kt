package com.ghettodev.rutago.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirportShuttle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ghettodev.rutago.data.entity.Ruta
import com.ghettodev.rutago.ui.theme.PrimaryGreen
import com.ghettodev.rutago.ui.theme.TextGray
import com.ghettodev.rutago.ui.theme.backgroundAltaDemanda
import com.ghettodev.rutago.ui.theme.fondoVerde
import com.ghettodev.rutago.ui.theme.textAltaDemanda

@Composable
fun CardRutasPopulares(
    ruta: Ruta,
    onCardClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onCardClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = ruta.nombreRuta,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "Icono estrella",
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "4.5",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "${ruta.totalParadas} paradas",
                    color = TextGray
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Schedule,
                        contentDescription = "Icono de reloj",
                        tint = TextGray
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "≈ ${ruta.totalParadas * 2} min",
                        fontSize = 15.sp,
                        color = TextGray
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    val demanda = if (ruta.totalParadas > 80) "Alta demanda" else "Demanda normal"
                    Text(
                        text = demanda,
                        color = textAltaDemanda,
                        modifier = Modifier
                            .background(
                                color = backgroundAltaDemanda,
                                shape = RoundedCornerShape(15.dp)
                            )
                            .padding(7.dp),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            Surface(
                modifier = Modifier.size(50.dp),
                color = fondoVerde,
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    Icons.Default.AirportShuttle,
                    contentDescription = "Logo de autobus",
                    tint = PrimaryGreen,
                    modifier = Modifier.padding(9.dp)
                )
            }
        }
    }
}
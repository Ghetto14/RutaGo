package com.ghettodev.rutago.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirportShuttle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ghettodev.rutago.ui.theme.PrimaryGreen
import com.ghettodev.rutago.ui.theme.TextGray
import com.ghettodev.rutago.ui.theme.backgroundAltaDemanda
import com.ghettodev.rutago.ui.theme.fondoVerde
import com.ghettodev.rutago.ui.theme.textAltaDemanda

@Preview
@Composable
fun cardRutasPopulares(
    //atributos
) {
    //hijos
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        //fila para mover los componentes a las orillas
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            //columna de izquierda
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(//lleva variable
                        text = "Ruta 5",//routName
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "Icono estrella",
                        tint = Color(0xFFFFC107),
                        modifier = Modifier
                            .size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(//lleva variable
                        text = "4.5",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(//aca va a llevar variable

                    text = "Centro Historico",//destination
                    color = TextGray
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Schedule,
                        contentDescription = "Icono de reloj",
                        tint = TextGray
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(//aca va variable
                        text = "12 min",
                        fontSize = 15.sp,
                        color = TextGray
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(//aqui va una variable

                        text = "Alta demanda",
                        color = textAltaDemanda,
                        modifier = Modifier
                            .background(
                                color = backgroundAltaDemanda,
                                shape = RoundedCornerShape(15.dp),

                                )
                            .padding(7.dp),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                }
            }
            //logo autobus
            Surface (
                modifier = Modifier
                    .size(50.dp),
                color = fondoVerde,
                shape = RoundedCornerShape(12.dp),


            ){
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
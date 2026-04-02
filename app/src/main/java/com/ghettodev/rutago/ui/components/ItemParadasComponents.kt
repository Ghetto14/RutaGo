package com.ghettodev.rutago.ui.components

import android.graphics.fonts.Font
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
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
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
//import com.ghettodev.rutago.ui.theme.fondoVerde

val isStart = true
/*
@Preview
@Composable
fun ItemParadas() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Columnas para dividir en derecha e izquierda
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                if (isStart) Icons.Default.Adjust else Icons.Default.Circle,

                tint = if (isStart) PrimaryGreen else Color.White,

                contentDescription = "Icono circular",
                modifier = Modifier.size(24.dp)
            )

        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.elevatedCardElevation(2.dp)
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {

                Text(
                    text = "Terminal de Autobuses",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(//aca va texto
                        text = "Parada 1",
                        color = TextGray,
                        fontSize = 10.sp
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    if (isStart) {
                        Text(
                            text = "Inicio",
                            color = PrimaryGreen,
                            fontSize = 10.sp,
                            modifier = Modifier
                                .background(
                                    color = fondoVerde,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 2.dp),


                            )
                    }
                }
            }
        }
    }
}*/
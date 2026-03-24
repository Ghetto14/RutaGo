package com.ghettodev.rutago.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ghettodev.rutago.ui.theme.PrimaryGreen
import com.ghettodev.rutago.ui.theme.blur

@Preview
@Composable
fun paradasPopulares(

) {
    Column() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryGreen)
                .wrapContentHeight()
                .padding(
                    horizontal = 15.dp,
                    vertical = 15.dp
                )
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(blur)

                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Arrow back",
                            tint = Color.White
                        )
                    }
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(blur)

                    ) {
                        Icon(//variable para indicar si es agregada a favoritas o no
                            Icons.Default.Favorite,
                            contentDescription = "favorite",
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Ruta 5",
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
                    text = "Centro Historico - Terminal",
                    fontSize = 28.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold

                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Schedule,
                        contentDescription = "Icono reloj",
                        tint = Color.White,
                        modifier = Modifier
                            .size(18.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "45 min",
                        fontSize = 18.sp,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.width(25.dp))

                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = Color.White,
                        modifier = Modifier
                            .size(18.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "10 Paradas",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }

        }
    }
}

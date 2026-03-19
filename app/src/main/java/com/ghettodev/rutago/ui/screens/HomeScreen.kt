package com.ghettodev.rutago.ui.screens

import com.ghettodev.rutago.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ghettodev.rutago.ui.theme.PrimaryGreen
import com.ghettodev.rutago.ui.theme.TextGray
import com.ghettodev.rutago.ui.theme.backgroundC


@Preview
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundC)
            .padding(20.dp)
    ) {
        Saludo()
        Spacer(modifier = Modifier.height(24.dp))
        ButtonAction()
        Spacer(modifier = Modifier.height(35.dp))
        RutasTexto()
        Spacer(modifier = Modifier.height(20.dp))
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
fun ButtonAction() {
    //boton explorar rutas
    Button(
        onClick = {},
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
    OutlinedButton(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = PrimaryGreen,
        ),
        border = androidx.compose.foundation.BorderStroke(2.dp, PrimaryGreen),
        shape = RoundedCornerShape(12.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.directions_bus),
            contentDescription = "Icono autobus",
            modifier = Modifier
                .height(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))  // Espacio entre icono y texto
        Text(
            text = "Ver rutas cercanas",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }


}

@Composable
fun RutasTexto() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
        //.padding(16.dp)
    ) {
        Text(
            text = "Rutas populares",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )

        Text(
            modifier = Modifier
                .clickable {},
            text = "Ver todas",
            fontSize = 14.sp,
            color = PrimaryGreen
        )

    }
}


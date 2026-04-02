package com.ghettodev.rutago

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding  // ← IMPORTAR ESTO

import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.ghettodev.rutago.ui.components.MapItem
import com.ghettodev.rutago.ui.screens.HomeScreen
import com.ghettodev.rutago.ui.screens.ParadasCercanas
import com.ghettodev.rutago.ui.theme.RutaGoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            RutaGoTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding()
                ) {
                    //aqui llamar a la funcon composable
                    //MapItem(modifier = Modifier.fillMaxSize())//ocupa todo el espacion de la surface
                    ParadasCercanas()
                }
            }
        }
    }

}
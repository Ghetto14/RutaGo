package com.ghettodev.rutago

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.ghettodev.rutago.navigation.NavigationWrapper
import com.ghettodev.rutago.ui.screens.HomeScreen
import com.ghettodev.rutago.ui.screens.LoginScreen
import com.ghettodev.rutago.ui.theme.RutaGoTheme
import com.ghettodev.rutago.ui.screens.RegisterScreen
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
                    RegisterScreen(
                        onRegisterClick = { nombre, email, telefono, pass ->

                            println("Registrando a: $nombre con tel: $telefono")
                        },
                        onLoginClick = {
                            // Aquí programarías que regrese al Login
                            println("Navegar al Login")
                        }
                    )
                }
            }
        }
    }
}



